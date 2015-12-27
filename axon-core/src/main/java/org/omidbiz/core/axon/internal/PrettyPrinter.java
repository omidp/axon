package org.omidbiz.core.axon.internal;

public class PrettyPrinter implements Printer
{
    private static String SYSTEM_LINE_SEPARATOR;

    static
    {
        String lf = null;
        try
        {
            lf = System.getProperty("line.separator");
        }
        catch (Throwable t)
        {
        } // access exception?
        SYSTEM_LINE_SEPARATOR = (lf == null) ? "\n" : lf;
    }

    StringBuilder sb = new StringBuilder();
    int indent = 0;

    boolean hasElements = false;

    private void align(int k)
    {
        for (int i = 0; i < k; i++)
            tab();
    }

    private void tab()
    {
        sb.append("    ");
    }

    private void newline()
    {
        sb.append(SYSTEM_LINE_SEPARATOR);
    }

    @Override
    public void openObject(int indent)
    {
        this.indent = indent;
        sb.append(OBJ_OPEN);
    }

    @Override
    public void appendObjElement(String name, String value)
    {
        hasElements = true;
        newline();
        align(indent + 1);
        sb.append("\"").append(name).append("\"").append(": ").append(value.trim());
    }

    @Override
    public void closeObject()
    {
        if (hasElements) 
        {
            newline();
            align(indent);
        }
        sb.append(OBJ_CLOSE);
    }

    @Override
    public void openArray(int indent)
    {
        this.indent = indent;
        sb.append(ARR_OPEN);
    }

    @Override
    public void appendArrayElement(String value)
    {
        hasElements = true;
        newline();
        align(indent + 1);
        sb.append(value.trim());
    }

    @Override
    public void closeArray()
    {
        if (hasElements) 
        {
            newline();
            align(indent);
        }
        sb.append(ARR_CLOSE);
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
