package org.omidbiz.core.axon.convertors;

import org.omidbiz.core.axon.internal.BasicElement;
import org.omidbiz.core.axon.internal.Element;
import org.omidbiz.core.axon.internal.SerializationContext;
import org.omidbiz.core.axon.internal.TypeConverter;

/**
 * @author : Omid Pourhadi omidpourhadi [AT] gmail [DOT] com
 * @author : Saber
 * @version 0.2
 */
public class EnumConvertor implements TypeConverter<Enum>
{

    @Override
    public boolean applies(Object instance)
    {
        return instance != null && instance.getClass().isEnum();
    }

    @Override
    public Element write(Enum instance, SerializationContext ctx)
    {
        return new BasicElement(instance.toString());
    }

}
