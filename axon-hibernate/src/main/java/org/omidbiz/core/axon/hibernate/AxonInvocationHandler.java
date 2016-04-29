package org.omidbiz.core.axon.hibernate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.impl.SessionImpl;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer;
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

    static List<Class<?>> excludedList = Collections.synchronizedList(new ArrayList<Class<?>>());

    static
    {
        excludedList.add(LazyInitializer.class);
        excludedList.add(Object.class);
        excludedList.add(Class.class);
        excludedList.add(SessionImplementor.class);
    }

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
                    if (property == null)
                        return method.invoke(delegate, args[0], targetEntity, property, value);
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
                        if (Hibernate.isPropertyInitialized(targetEntity, property.getName()))
                        {
                            if (excludedList.contains(property.getType()))
                                return true;
                            if (property.getType().getName().equals("javassist.util.proxy.MethodHandler"))
                                return true;
                            return false;
                        }
                        else
                        {
                            // exclude element
                            return true;
                        }
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
