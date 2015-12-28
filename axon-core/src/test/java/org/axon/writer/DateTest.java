package org.axon.writer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.axon.model.DateModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.omidbiz.core.axon.Axon;
import org.omidbiz.core.axon.AxonBuilder;


public class DateTest
{

    @Test
    public void testDate() throws ParseException, JSONException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println("Date Without Format : " + new Date());
        System.out.println("Date With Format : " +sdf.format(new Date()));
        DateModel dm = new DateModel(sdf.parse(sdf.format(new Date())));
        dm.setSqlDate(new java.sql.Date(System.currentTimeMillis()));
        Axon axon = new AxonBuilder().create();
        System.out.println("Serialize java.util.Date and sql date : " + axon.toJson(dm));
        //
        DateModel object = axon.toObject(axon.toJson(dm), DateModel.class, null);
        
        System.out.println(object.getStartDate());
    }
    
}
