package org.omidbiz.core.axon.convertors;

import java.util.Date;

import org.omidbiz.core.axon.internal.BasicElement;
import org.omidbiz.core.axon.internal.Element;
import org.omidbiz.core.axon.internal.SerializationContext;
import org.omidbiz.core.axon.internal.TypeConverter;


public class EpochDateConverter implements TypeConverter<Date>
{

    @Override
    public boolean applies(Object instance)
    {
        return instance != null && instance.getClass() == Date.class || instance.getClass() == java.sql.Date.class;
    }

    @Override
    public Element write(Date instance, SerializationContext ctx)
    {
        return new BasicElement(instance.getTime());
    }

}
