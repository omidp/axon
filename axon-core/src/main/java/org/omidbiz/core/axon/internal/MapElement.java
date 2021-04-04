package org.omidbiz.core.axon.internal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Omid Pourhadi omidpourhadi [AT] gmail [DOT] com
 * @version 0.2
 */
public class MapElement implements Element
{

    private Map<String, Object> map;
    Map<Object, Element> elements;
    private String[] paths;

    public MapElement(Object map)
    {
        try {
            this.map = (Map<String, Object>) map;
        } catch (ClassCastException e) {
            throw e;
        }
    }

    @Override
    public void process(String path, SerializationContext ctx)
    {
        this.paths = PathProcessor.splitPath(path);
        elements = new HashMap<Object, Element>();
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            Object entryValue = entry.getValue();
            Element el = ctx.getElement(path, map, null, entryValue);

            elements.put(entry.getKey(), el);

        }

        for (Map.Entry<Object, Element> entry : elements.entrySet())
            entry.getValue().process(path, ctx);
    }

    @Override
    public String toJson(SerializationContext ctx)
    {
        Printer p = ctx.newPrinter();
        if(paths != null)
            p.openObject(PathProcessor.getIndentation(paths));
        int i = 0;
        for (Object key : elements.keySet())
        {
            Element el = elements.get(key);
            if (el instanceof NullElement && !ctx.serializeNull)
                continue;

            if (i++ > 0)
                p.separator();
//            Class<? extends Object> class1 = key.getClass();
            p.appendObjElement(""+key, el.toJson(ctx));
        }
        p.closeObject();
        return p.content();
    }

}
