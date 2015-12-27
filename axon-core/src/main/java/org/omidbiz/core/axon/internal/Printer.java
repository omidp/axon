package org.omidbiz.core.axon.internal;

public interface Printer
{
    static final String OBJ_OPEN = "{";
    static final String OBJ_CLOSE = "}";
    static final String ARR_OPEN = "[";
    static final String ARR_CLOSE = "]";
    static final String SEPARATOR = ",";
    
    void openObject(int indent);
    void closeObject();
    
    void openArray(int indent);
    void closeArray();
    
    void separator();
    
    void appendObjElement(String name, String value);
    void appendArrayElement(String value);
    
    String content();
    
}
