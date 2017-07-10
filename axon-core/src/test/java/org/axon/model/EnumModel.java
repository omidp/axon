package org.axon.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.omidbiz.core.axon.Property;
import org.omidbiz.core.axon.internal.AxonSerializer;
import org.omidbiz.core.axon.internal.BasicElement;
import org.omidbiz.core.axon.internal.CustomSerializer;
import org.omidbiz.core.axon.internal.Element;

public class EnumModel
{

    public enum EnumHelper
    {
        HI, BYE;
    }

    private EnumHelper enumHelper;

    private EnumHelper enumAnnot;

    @CustomSerializer(interceptor = EnumCustomSerializer.class)
    public EnumHelper getEnumAnnot()
    {
        return enumAnnot;
    }

    public void setEnumAnnot(EnumHelper enumAnnot)
    {
        this.enumAnnot = enumAnnot;
    }

    public EnumHelper getEnumHelper()
    {
        return enumHelper;
    }

    public void setEnumHelper(EnumHelper enumHelper)
    {
        this.enumHelper = enumHelper;
    }

    public static class EnumCustomSerializer implements AxonSerializer
    {

        @Override
        public Element serialize(Object target, Property property, Object propertyValue)
        {
            //property value is BYE we print salam
            return new BasicElement("salam");
        }

        @Override
        public Object deserialize(Object bean, Property property, JSONObject jsonObject)
        {
            try
            {
                EnumModel em = (EnumModel) bean;
                String salam = (String) jsonObject.get("enumAnnot");
                if ("salam".equals(salam))
                    return EnumHelper.BYE;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

    }

}
