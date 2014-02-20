package org.omidbiz.core.axon;

import org.omidbiz.core.axon.internal.SerializationContext;

public interface Filter
{
    void beforeFilter(SerializationContext ctx);
    boolean exclude(String path, Object target, Property property, Object propertyValue);
    void afterFilter();
}
