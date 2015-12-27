package org.omidbiz.core.axon;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;


import org.omidbiz.core.axon.filters.RecursionControlFilter;

import org.omidbiz.core.axon.internal.SerializationContext;
import org.omidbiz.core.axon.internal.TypeConverter;

/**
 * @author Omid Pourhadi
 *
 */
public class AxonBuilder
{

    

    private List<Filter> filters = new ArrayList<Filter>();
    private List<TypeConverter<?>> converters = new ArrayList<TypeConverter<?>>();
    private boolean serializeNull;
    private boolean prettyPrint;
    

    public AxonBuilder()
    {
    }

    public Axon create()
    {
        SerializationContext c = new SerializationContext();
        c.filters.addAll(filters);
        c.converters.addAll(converters);
        c.serializeNull = serializeNull;
        c.prettyPrint = prettyPrint;
        Axon ax = new Axon();
        ax.serializationContext = c;
        return ax;
    }

    public AxonBuilder addFilter(Filter filter)
    {
        if (filter == null)
            throw new RuntimeException("null filter");

        
            filters.add(filter);
        
        return this;
    }

   

    public AxonBuilder addTypeConverter(TypeConverter<?> typeConverter)
    {
        if (typeConverter == null)
            throw new RuntimeException("null typeConverter");

        converters.add(typeConverter);
        return this;
    }

    public AxonBuilder serializeNulls(boolean serialize)
    {
        serializeNull = serialize;
        return this;
    }

    public AxonBuilder useWithPrettyWriter()
    {
        prettyPrint = true;
        return this;
    }

    public AxonBuilder preventRecursion()
    {
        filters.add(new RecursionControlFilter());
        return this;
    }
}
