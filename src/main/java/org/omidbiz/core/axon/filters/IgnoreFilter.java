package org.omidbiz.core.axon.filters;

import org.omidbiz.core.axon.Filter;
import org.omidbiz.core.axon.Property;
import org.omidbiz.core.axon.internal.IgnoreElement;
import org.omidbiz.core.axon.internal.SerializationContext;


public class IgnoreFilter implements Filter
{

    @Override
    public void beforeFilter(SerializationContext ctx)
    {

    }

    @Override
    public boolean exclude(String path, Object target, Property property, Object propertyValue)
    {
        if (property != null && property.getGetter() != null && property.getGetter().isAnnotationPresent(IgnoreElement.class))
        {
            return true;
        }
        return false;
    }

    @Override
    public void afterFilter()
    {

    }

}
