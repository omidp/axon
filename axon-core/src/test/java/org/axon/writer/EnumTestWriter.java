package org.axon.writer;

import org.axon.model.EnumModel;
import org.axon.model.EnumModel.EnumHelper;
import org.junit.Test;
import org.omidbiz.core.axon.Axon;
import org.omidbiz.core.axon.AxonBuilder;


public class EnumTestWriter
{

    @Test
    public void testEnum()
    {
        EnumModel em = new EnumModel();
        em.setEnumHelper(EnumHelper.HI);
        em.setEnumAnnot(EnumHelper.BYE);
        Axon axon = new AxonBuilder().create();
        String str = axon.toJson(em);
        System.out.println("object to json : " + str);
        //
        EnumModel object = axon.toObject(str, EnumModel.class, null);
        System.out.println("after convert json to object :" + object.getEnumAnnot());
    }
    
    
}
