package org.omidbiz.core.axon.filters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.omidbiz.core.axon.AxonBeanHelper;
import org.omidbiz.core.axon.Filter;
import org.omidbiz.core.axon.Property;
import org.omidbiz.core.axon.internal.SerializationContext;


public class RecursionControlFilter implements Filter
{
    Set<List<Object>> objectPaths = new HashSet<List<Object>>();

    @Override
    public void beforeFilter(SerializationContext ctx)
    {
        this.objectPaths = new HashSet<List<Object>>();
    }

    @Override
    public void afterFilter()
    {
        this.objectPaths.clear();
    }

    @Override
    public boolean exclude(String path, Object target, Property property, Object propertyValue)
    {
        if (target != null && property != null && propertyValue == null)
        {
            propertyValue = AxonBeanHelper.getPropertyValue(target, property);
        }

        if (target == null || propertyValue == null || AxonBeanHelper.isPrimitiveOrWrapper(target)
                || AxonBeanHelper.isPrimitiveOrWrapper(propertyValue))
        {
            return false;
        }

        Set<List<Object>> lastElementIsTarget = new HashSet<List<Object>>();

        Object parentCode = target;
        Object propCode = propertyValue;

        // Simple recursions are handled directly
        if (parentCode == propCode)
            return true;

        if (objectPaths.isEmpty())
        {
            objectPaths.add(AxonBeanHelper.newArrayList(parentCode));
            objectPaths.add(AxonBeanHelper.newArrayList(parentCode, propCode));
            return false;
        }

        for (List<Object> objectPath : objectPaths)
        {
            if (objectPath.get(objectPath.size() - 1) == parentCode)
            {
                lastElementIsTarget.add(objectPath);
            }
        }

        for (List<Object> objectPath : lastElementIsTarget)
        {
            if (objectPath.contains(propCode))
                return true;
        }

        for (List<Object> objectPath : lastElementIsTarget)
        {
            ArrayList<Object> newPath = AxonBeanHelper.newArrayList(objectPath.toArray(new Object[objectPath.size()]));
            newPath.add(propCode);
            objectPaths.add(newPath);
        }

        return false;
    }

}
