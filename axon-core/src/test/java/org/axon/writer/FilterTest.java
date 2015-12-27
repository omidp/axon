package org.axon.writer;

import junit.framework.Assert;

import org.axon.model.DocumentModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.omidbiz.core.axon.Axon;
import org.omidbiz.core.axon.AxonBuilder;
import org.omidbiz.core.axon.Filter;
import org.omidbiz.core.axon.Property;
import org.omidbiz.core.axon.internal.SerializationContext;

public class FilterTest
{

    @Test
    public void testAfterFilter() throws JSONException
    {
        DocumentModel dm = new DocumentModel(1, "Doc1", null);
        AxonBuilder ab = new AxonBuilder();
        Axon ax = ab.create();
        //
        System.out.println(" Before Filter : ");
        JSONObject json = new JSONObject(ax.toJson(dm));
        System.out.println(json);
        Assert.assertTrue(json.getString("name") != null);
        // exclude name filter
        ax = ab.addFilter(new FieldFilter()).create();
        System.out.println(" After Filter : ");
        json = new JSONObject(ax.toJson(dm));
        System.out.println(json);
    }

    class FieldFilter implements Filter
    {

        @Override
        public void beforeFilter(SerializationContext ctx)
        {
            System.out.println("BeforeFilter");
        }

        @Override
        public boolean exclude(String path, Object target, Property property, Object propertyValue)
        {
            if (property.getName().equals("name"))
                return true;
            else
                return false;
        }

        @Override
        public void afterFilter()
        {

        }

    }

}
