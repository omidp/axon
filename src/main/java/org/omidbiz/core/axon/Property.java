package org.omidbiz.core.axon;

import java.lang.reflect.Method;

public class Property
{
    String name;
    Method getter;
    Method setter;

    public Property()
    {
    }

    public Property(String name, Method getter, Method setter)
    {
        this.name = name;
        this.getter = getter;
        this.setter = setter;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Method getGetter()
    {
        return getter;
    }

    public void setGetter(Method getter)
    {
        this.getter = getter;
    }

    public Method getSetter()
    {
        return setter;
    }

    public void setSetter(Method setter)
    {
        this.setter = setter;
    }

    public Class<?> getType()
    {
        if (getter != null)
            return getter.getReturnType();
        else if (setter != null)
        {
            return setter.getParameterTypes()[0];
        }
        else
            return Object.class;
    }

    @Override
    public String toString()
    {
        return "Property(" + name + ", " + getter + ", " + setter + ")";
    }

}
