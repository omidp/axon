package org.omidbiz.core.axon.hibernate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.proxy.HibernateProxy;
import org.omidbiz.core.axon.AxonBeanHelper;
import org.omidbiz.core.axon.Property;

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
            if ("exclude".equals(method.getName()))
            {
                Object targetEntity = args[1];
                Property property = (Property) args[2];
                Object value = args[3];
                //
                if (session.contains(targetEntity) == false)
                {
                    // add session to persistent collection
                    session.refresh(targetEntity);
                }
                //
                if (value != null && AxonBeanHelper.isArrayOrCollection(value.getClass()))
                {
                    // prevent lazyinitializing exception
                    boolean initialized = Hibernate.isInitialized(value);
                    boolean propertyInitialized = Hibernate.isPropertyInitialized(targetEntity, property.getName());
                    Hibernate.initialize(value);
//                    if (session.contains(value) == false)
//                    {
//                        value = initializeAndUnproxy(value);
//                        session.setReadOnly(value, true);
//                    }
                }
                //
                return method.invoke(delegate, args[0], targetEntity, property, value);
            }
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
