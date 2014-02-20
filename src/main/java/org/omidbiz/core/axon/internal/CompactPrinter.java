package org.omidbiz.core.axon.internal;

public class CompactPrinter implements Printer
{
    StringBuilder sb = new StringBuilder();

    @Override
    public void openObject(int indent)
    {
        sb.append(OBJ_OPEN);
    }

    @Override
    public void closeObject()
    {
        sb.append(OBJ_CLOSE);
    }

    @Override
    public void openArray(int indent)
    {
        sb.append(ARR_OPEN);
    }

    @Override
    public void closeArray()
    {
        sb.append(ARR_CLOSE);
    }

    @Override
    public void appendObjElement(String name, String value)
    {
        sb.append("\"").append(name).append("\"").append(":").append(value);
    }

    @Override
    public void appendArrayElement(String value)
    {
        sb.append(value);
    }

    @Override
    public void separator()
    {
        sb.append(SEPARATOR);
    }

    @Override
    public String content()
    {
        return sb.toString();
    }

    @Override
    public String toString()
    {
        return content();
    }
    
}
