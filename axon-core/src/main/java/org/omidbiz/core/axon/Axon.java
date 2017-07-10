package org.omidbiz.core.axon;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omidbiz.core.axon.internal.ArrayElement;
import org.omidbiz.core.axon.internal.AxonSerializer;
import org.omidbiz.core.axon.internal.BasicElement;
import org.omidbiz.core.axon.internal.CustomSerializer;
import org.omidbiz.core.axon.internal.ObjectElement;
import org.omidbiz.core.axon.internal.SerializationContext;

public class Axon
{

    SerializationContext serializationContext;

    public Axon()
    {
        serializationContext = new SerializationContext();
    }

    public synchronized String toJson(Object obj)
    {
        beforeFilters();

        String s = null;

        if (obj == null)
            s = nullToJson();
        else if (AxonBeanHelper.isPrimitiveOrWrapper(obj))
            s = primitiveToJson(obj);
        else if (AxonBeanHelper.isArrayOrCollection(obj))
            s = arrayToJson(obj);
        else
            s = objectToJson(obj);

        afterFilters();

        return s;
    }

    private void beforeFilters()
    {
        for (Filter f : serializationContext.filters)
            f.beforeFilter(serializationContext);
    }

    private void afterFilters()
    {
        for (Filter f : serializationContext.filters)
            f.afterFilter();
    }

    private String nullToJson()
    {
        if (serializationContext.serializeNull)
            return "null";
        else
            return "";
    }

    private String primitiveToJson(Object obj)
    {
        return new BasicElement(obj).toJson(serializationContext);
    }

    private String arrayToJson(Object obj)
    {
        ArrayElement el = new ArrayElement(obj);
        el.process("", serializationContext);
        return el.toJson(serializationContext);
    }

    private String objectToJson(Object obj)
    {
        ObjectElement el = new ObjectElement(obj);
        el.process("", serializationContext);
        return el.toJson(serializationContext);
    }

    /**
     * @param jsonContent
     *            : json String for building object
     * @param clz
     *            : base class to instantiate
     * @param prototype
     *            : if there is preloaded object pass it as prototype otherwise
     *            pass null
     * @return
     */
    public <T> T toObject(String jsonContent, Class<? extends T> clz, T prototype)
    {

        try
        {
            if (prototype == null)
            {
                prototype = clz.newInstance();
            }
            JSONObject jsonObject = new JSONObject(jsonContent);
            parseJsonObject(prototype, jsonObject);
        }
        catch (InstantiationException e)
        {
            throw new RuntimeException("could not instantiate from class " + clz, e);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("could not instantiate from class " + clz, e);
        }
        catch (JSONException e)
        {
            throw new RuntimeException("invalid json" + clz, e);
        }
        catch (NoSuchFieldException e)
        {
            throw new RuntimeException("could not find such a field " + clz, e);
        }
        catch (SecurityException e)
        {
            // DO NOTHING
        }

        return prototype;
    }

    public void parseJsonObject(Object bean, JSONObject jsonObject) throws JSONException, InstantiationException, IllegalAccessException,
            NoSuchFieldException, SecurityException
    {
        List<Property> props = AxonBeanHelper.getProperties(bean.getClass(), true);
        for (Property p : props)
        {
            if (p.getSetter() == null || !jsonObject.has(p.getName()))
                continue;

            Class<?> propertyTargetClass = p.getType();

            if (AxonBeanHelper.isPrimitiveOrWrapper(propertyTargetClass))
            {
                Object val = jsonObject.get(p.getName());
                AxonBeanHelper.setPropertyValue(bean, p, val);
            }
            else if (AxonBeanHelper.isArrayOrCollection(propertyTargetClass))
            {
                parseJsonArray(p, jsonObject, bean);
            }
            //handled in Axonbeanhelper method returnDateValeByType as primitive 
//            else if (Date.class.equals(propertyTargetClass))
//            {
//                Object val = jsonObject.get(p.getName());
//                Date date = new Date((Long) val);
//                AxonBeanHelper.setPropertyValue(bean, p, date);
//
//            }

            else
            {
                parseJsonObject(bean, p, jsonObject);

            }

        }

    }

    private void parseJsonObject(Object bean, Property property, JSONObject jsonObject) throws InstantiationException,
            IllegalAccessException, NoSuchFieldException, SecurityException, JSONException
    {
        if (typeConverterFound(property))
        {
            CustomSerializer annotation = property.getGetter().getAnnotation(CustomSerializer.class);
            AxonSerializer as = (AxonSerializer) annotation.interceptor().newInstance();
            Object val = as.deserialize(bean, property, jsonObject);
            AxonBeanHelper.setPropertyValue(bean, property, val);
        }
        else if (AxonBeanHelper.isEnum(property.getType()))
        {
            String enumValue = jsonObject.getString(property.getName());
            if (enumValue != null)
            {
                Enum val = Enum.valueOf((Class<Enum>) property.getType(), enumValue);
                AxonBeanHelper.setPropertyValue(bean, property, val);
            }
        }
        else
        {
            Object propVal = AxonBeanHelper.getPropertyValue(bean, property);
            if (propVal == null)
                propVal = property.getType().newInstance();
            AxonBeanHelper.setPropertyValue(bean, property, propVal);
            parseJsonObject(propVal, jsonObject.getJSONObject(property.getName()));
        }

    }

    @SuppressWarnings("unchecked")
    private void parseJsonArray(Property property, JSONObject jsonObject, Object bean) throws JSONException, InstantiationException,
            IllegalAccessException, NoSuchFieldException, SecurityException
    {
        if (property.getType().isArray())
        {

            JSONArray arr = jsonObject.getJSONArray(property.getName());
            Class<?> elementType = property.getType().getComponentType();
            Object c = Array.newInstance(elementType, arr.length());
            AxonBeanHelper.setPropertyValue(bean, property, c);

            for (int i = 0; i < arr.length(); i++)
            {
                Object item = arr.get(i);
                if (AxonBeanHelper.isPrimitiveOrWrapper(item))
                {
                    Array.set(c, i, item);
                }
                if (item instanceof JSONObject)
                {
                    JSONObject o = arr.getJSONObject(i);
                    Object element = elementType.newInstance();
                    Array.set(c, i, element);
                    parseJsonObject(element, o);
                }
            }
        }
        else
        {
            Class<?> elementType = AxonBeanHelper.getGenericFieldClassType(bean.getClass(), property.getName());
            Collection c =  AxonBeanHelper.instantiateCollection(property, elementType);
            AxonBeanHelper.setPropertyValue(bean, property, c);
            JSONArray arr = jsonObject.getJSONArray(property.getName());

            for (int i = 0; i < arr.length(); i++)
            {
                Object item = arr.get(i);
                if (AxonBeanHelper.isPrimitiveOrWrapper(item))
                {
                    c.add(AxonBeanHelper.toObject(elementType, item));
                }
                if (item instanceof JSONObject)
                {
                    JSONObject o = arr.getJSONObject(i);
                    Object element = elementType.newInstance();
                    c.add(element);
                    parseJsonObject(element, o);
                }
            }

        }
    }

    private boolean typeConverterFound(Property property)
    {
        return property != null && property.getGetter().isAnnotationPresent(CustomSerializer.class);
    }

}
