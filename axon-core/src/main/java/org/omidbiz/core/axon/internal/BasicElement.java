package org.omidbiz.core.axon.internal;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;

public class BasicElement implements Element
{
    Object value;

    Element elementConvertor;
    
    public BasicElement(Object value)
    {
        if (value == null)
            throw new RuntimeException("null value");
        this.value = value;
    }

    @Override
    public void process(String path, SerializationContext ctx)
    {
        elementConvertor = ctx.applyTypeConverters(value);
        if (elementConvertor != null)
        {
            elementConvertor.process(path, ctx);
        }
    }

    @Override
    public String toJson(SerializationContext ctx)
    {
        if (elementConvertor != null)
        {
            return elementConvertor.toJson(ctx);
        }
        if (value instanceof Character || value instanceof String)
        {
            return JSONObject.quote(String.valueOf(value));
        }
        if (value instanceof Date 
                || value instanceof java.sql.Date
                || value instanceof Timestamp)
        {
            return JSONObject.quote(String.valueOf(value));
        }
        else
        {
            return value.toString();
        }
    }

}
