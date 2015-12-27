package org.omidbiz.core.axon.internal;

public class NullElement implements Element
{

    @Override
    public void process(String path, SerializationContext ctx)
    {
    }

    @Override
    public String toJson(SerializationContext ctx)
    {
        return "null";
    }
}
