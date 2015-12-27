package org.omidbiz.core.axon.hibernate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.proxy.HibernateProxy;
import org.omidbiz.core.axon.AxonBeanHelper;
import org.omidbiz.core.axon.Property;
import org.omidbiz.core.axon.filters.RecursionControlFilter;

/**
 * @author Omid Pourhadi
 *         <p>
 *         expand hibernate sessoin persistence context to prevent lazy
 *         initializing exception while serializing
 *         </p>
 */
public class AxonInvocationHandler implements InvocationHandler
{

    private Object delegate;
    private Session session;
    private Object persistenceObject;

    public AxonInvocationHandler(Object delegate, Session session)
    {
        this.delegate = delegate;
        this.session = session;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        try
        {
            if (RecursionControlFilter.class.equals(delegate.getClass()))
            {
                if ("beforeFilter".equals(method.getName()))
                {
                    return method.invoke(delegate, args);
                }
                // exclude
                if ("exclude".equals(method.getName()))
                {
                    Object targetEntity = args[1];
                    Property property = (Property) args[2];
                    Object value = args[3];
                    Object invoke = null;
                    if (AxonBeanHelper.isPrimitiveOrWrapper(property.getGetter().getReturnType()) == false)
                    {
                        // no way to prevent closing session, we have to hit db
                        // due to preventing from closing session we only
                        // serialized primitive not object
                        // exclude non-primitive
                        // session = session.getSessionFactory().openSession();
                        // Object initializeAndUnproxy =
                        // initializeAndUnproxy(targetEntity);
                        // session.refresh(targetEntity);
                        // session.close();
                        return true;
                    }
                    invoke = method.invoke(delegate, args[0], targetEntity, property, value);
                    return invoke;
                }
                // afterFilter
                if ("afterFilter".equals(method.getName()))
                {
                    return method.invoke(delegate, args);
                }
            }
            //
            return method.invoke(delegate, args);
        }
        catch (InvocationTargetException e)
        {
            throw e.getTargetException();
        }
    }

    public static Object initializeAndUnproxy(Object entity)
    {
        if (entity == null)
        {
            throw new NullPointerException("Entity passed for initialization is null");
        }

        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy)
        {
            entity = (Object) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }
        return entity;
    }

}
