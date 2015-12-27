package org.omidbiz.core.axon.hibernate;

import java.lang.reflect.Proxy;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.jboss.seam.core.Expressions;
import org.omidbiz.core.axon.Filter;

/**
 * @author Omid Pourhadi
 *
 */
public class AxonBuilder extends org.omidbiz.core.axon.AxonBuilder
{

    private static final boolean isSeamAvailable;

    private static final boolean isHibernateAvailable;

    static
    {
        boolean available;
        try
        {
            Class.forName("org.jboss.seam.Seam");
            available = true;
        }
        catch (ClassNotFoundException cnfe)
        {
            available = false;
        }
        isSeamAvailable = available;
    }

    static
    {
        boolean available;
        try
        {
            Class.forName("org.hibernate.Session");
            available = true;
        }
        catch (ClassNotFoundException cnfe)
        {
            available = false;
        }
        isHibernateAvailable = available;
    }

    private Session session;

    public void addSession(Session session)
    {
        this.session = session;
    }

    @Override
    public org.omidbiz.core.axon.AxonBuilder addFilter(Filter filter)
    {
        //Don't autodetect session
        if (session == null && isSeamAvailable)
        {
            EntityManager em = (EntityManager) Expressions.instance().createValueExpression("#{entityManager}", EntityManager.class)
                    .getValue();
            Session sess = (Session) em.getDelegate();
            session = sess;
        }

        if (session == null)
            throw new IllegalArgumentException("session is not added");

        if (isHibernateAvailable)
        {
            AxonInvocationHandler sih = new AxonInvocationHandler(filter, session);
            ClassLoader classLoader = Filter.class.getClassLoader();
            Filter proxyInstance = (Filter) Proxy.newProxyInstance(classLoader, new Class[] { Filter.class }, sih);
            return super.addFilter(proxyInstance);
        }
        else
        {
            return super.addFilter(filter);
        }
    }

}
