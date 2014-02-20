package org.omidbiz.core.axon.convertors;

import java.util.Map;

import org.omidbiz.core.axon.AxonBeanHelper;
import org.omidbiz.core.axon.internal.Element;
import org.omidbiz.core.axon.internal.MapElement;
import org.omidbiz.core.axon.internal.SerializationContext;
import org.omidbiz.core.axon.internal.TypeConverter;


/**
 * @author : Omid Pourhadi omidpourhadi [AT] gmail [DOT] com
 * @author Saber
 * @version 0.2
 */
public class MapConvertor implements TypeConverter<Map>
{

    @Override
    public boolean applies(Object instance)
    {
        return instance != null && AxonBeanHelper.isSubclass(instance.getClass(), Map.class);
    }

    @Override
    public Element write(Map instance, SerializationContext ctx)
    {
        return new MapElement(instance);
    }

}