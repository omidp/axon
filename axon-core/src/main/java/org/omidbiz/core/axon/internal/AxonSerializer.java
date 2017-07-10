package org.omidbiz.core.axon.internal;

import org.json.JSONObject;
import org.omidbiz.core.axon.Property;

/**
 * @author omidp
 *
 */
public interface AxonSerializer
{

    public Element serialize(Object target, Property property, Object propertyValue);

    public Object deserialize(Object bean, Property property, JSONObject jsonObject);

}
