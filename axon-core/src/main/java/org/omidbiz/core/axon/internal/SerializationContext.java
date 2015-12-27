package org.omidbiz.core.axon.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.omidbiz.core.axon.AxonBeanHelper;
import org.omidbiz.core.axon.Filter;
import org.omidbiz.core.axon.Property;
import org.omidbiz.core.axon.convertors.EnumConvertor;
import org.omidbiz.core.axon.convertors.EpochDateConverter;
import org.omidbiz.core.axon.convertors.MapConvertor;
import org.omidbiz.core.axon.filters.IgnoreFilter;


public class SerializationContext
{
    public boolean serializeNull = false;

    public List<Filter> filters;
    public List<TypeConverter<?>> converters;
    public boolean prettyPrint; 

    public SerializationContext()
    {
        filters = new ArrayList<Filter>();
        converters = new ArrayList<TypeConverter<?>>();
        
        filters.add(new IgnoreFilter());

        converters.add(new EpochDateConverter());
        converters.add(new EnumConvertor());
        converters.add(new MapConvertor());
        
        prettyPrint = false;
    }

    public boolean shouldExclude(String path, Object target, Property property, Object propertyValue)
    {
        boolean exclude = false;
        for (Filter f : filters)
        {
            exclude |= f.exclude(path, target, property, propertyValue);
            if (exclude)
                break;
        }
        return exclude;
    }

    public Element applyTypeConverters(Object obj)
    {
        Element element = null;
        for (TypeConverter c : converters)
        {
            if (c.applies(obj))
            {
                element = c.write(obj, this);
                break;
            }
        }
        return element;
    }

    public Element getElement(String path, Object target, Property property, Object propertyValue)
    {
        if (shouldExclude(path, target, property, propertyValue))
            return null;

        if (target != null && property != null)
            propertyValue = AxonBeanHelper.getPropertyValue(target, property);

        if (propertyValue == null)
        {
            return new NullElement();
        }
        else if (AxonBeanHelper.isPrimitiveOrWrapper(propertyValue))
        {
            return new BasicElement(propertyValue);
        }
        else if (AxonBeanHelper.isArrayOrCollection(propertyValue))
        {
            return new ArrayElement(propertyValue);
        }
        else if (propertyValue instanceof Map)
        {
            return new MapElement(propertyValue);
        }
        else 
        {
            return new ObjectElement(propertyValue);
        }
    }

    public Printer newPrinter()
    {
        if (prettyPrint)
            return new PrettyPrinter();
        else 
            return new CompactPrinter();
    }
}
