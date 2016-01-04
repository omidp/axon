package org.omidbiz.core.axon.internal;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;

public class BasicElement implements Element
{
    Object value;

    public BasicElement(Object value)
    {
        if (value == null)
            throw new RuntimeException("null value");
        this.value = value;
    }

    @Override
    public void process(String path, SerializationContext ctx)
    {
    }

    @Override
    public String toJson(SerializationContext ctx)
    {
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
