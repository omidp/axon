package org.omidbiz.core.axon.internal;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArrayElement implements Element
{
    Object value;
    List<Element> elements;
    private String[] paths;

    public ArrayElement(Object value)
    {
        if (value == null)
            throw new RuntimeException("null value");
        this.value = value;
        elements = new ArrayList<Element>();
    }

    @Override
    public void process(String path, SerializationContext ctx)
    {
        this.paths = PathProcessor.splitPath(path);
        Class<?> clazz = value.getClass();
        if (clazz.isArray())
        {
            int length = Array.getLength(value);
            for (int i = 0; i < length; i++)
            {
                Object elemValue = Array.get(value, i);

                Element el = ctx.getElement(path, value, null, elemValue);

                if (el == null)
                    continue;

                elements.add(el);
            }
        }
        else
        {
            Collection<?> collection = (Collection<?>) value;
            for (Object elemValue : collection)
            {

                Element el = ctx.getElement(path, value, null, elemValue);

                if (el == null)
                    continue;

                elements.add(el);
            }
        }

        for (Element el : elements)
        {
            el.process(path + "[]", ctx);
        }
    }

    @Override
    public String toJson(SerializationContext ctx)
    {
        Printer p = ctx.newPrinter();
        p.openArray(PathProcessor.getIndentation(paths));
        int i = 0;
        for (Element el : elements)
        {
            if (el instanceof NullElement && !ctx.serializeNull)
                continue;
            if (i++ > 0)
                p.separator();
            p.appendArrayElement(el.toJson(ctx));

        }
        p.closeArray();
        return p.content();
    }

}
