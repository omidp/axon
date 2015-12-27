package org.omidbiz.core.axon.internal;

public interface Element
{
    void process(String path, SerializationContext ctx);
    
    String toJson(SerializationContext ctx);
}
