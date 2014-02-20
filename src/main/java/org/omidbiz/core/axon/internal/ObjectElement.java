package org.omidbiz.core.axon.internal;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.omidbiz.core.axon.AxonBeanHelper;
import org.omidbiz.core.axon.Property;


public class ObjectElement implements Element
{

    Object value;
    Element elementConvertor;
    Map<Property, Element> elements;

    String[] paths;

    public ObjectElement(Object value)
    {
        elementConvertor = null;
        if (value == null)
            throw new RuntimeException("null value");
        elements = new LinkedHashMap<Property, Element>();

        this.value = value;
    }

    @Override
    public void process(String path, SerializationContext ctx)
    {
        this.paths = PathProcessor.splitPath(path);
        elementConvertor = ctx.applyTypeConverters(value);
        if (elementConvertor != null)
            return;
        else
        {
            List<Property> props = AxonBeanHelper.getProperties(value.getClass(), true);
            // FIXME : when property is enum this loop never ends
            for (Property property : props)
            {
                if (property.getGetter() == null)
                    continue;

                // Object propValue = AxonBeanHelper.getPropertyValue(value, property);

                Element el = ctx.getElement(path, value, property, null);

                if (el == null)
                    continue;
                elements.put(property, el);
            }

            for (Map.Entry<Property, Element> entry : elements.entrySet())
            {
                entry.getValue().process(PathProcessor.appendPath(path, entry.getKey().getName()), ctx);
            }
        }

    }

    @Override
    public String toJson(SerializationContext ctx)
    {

        if (elementConvertor != null)
        {
            return elementConvertor.toJson(ctx);
        }
        else
        {
            Printer p = ctx.newPrinter();
            p.openObject(PathProcessor.getIndentation(paths));
            int i = 0;
            for (Property key : elements.keySet())
            {
                Element el = elements.get(key);
                if (el instanceof NullElement && !ctx.serializeNull)
                    continue;

                if (i++ > 0)
                {
                    p.separator();
                }
                p.appendObjElement(key.getName(), el.toJson(ctx));
            }
            p.closeObject();
            return p.content();
        }
    }


}
