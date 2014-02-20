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
        Axon axon = new AxonBuilder().create();
        System.out.println(axon.toJson(em));
    }
    
    
}
