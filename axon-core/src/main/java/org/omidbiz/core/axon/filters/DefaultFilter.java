package org.omidbiz.core.axon.filters;

import org.omidbiz.core.axon.Filter;
import org.omidbiz.core.axon.Property;
import org.omidbiz.core.axon.internal.SerializationContext;


public class DefaultFilter implements Filter
{

    @Override
    public void beforeFilter(SerializationContext ctx)
    {
    }

    @Override
    public void afterFilter()
    {
    }

    @Override
    public boolean exclude(String path, Object target, Property property, Object propertyValue)
    {
        return false;
    }

}
