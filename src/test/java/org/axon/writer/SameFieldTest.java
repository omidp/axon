package org.axon.writer;

import org.axon.model.SameField;
import org.axon.model.SameFieldWithName;
import org.junit.Test;
import org.omidbiz.core.axon.Axon;
import org.omidbiz.core.axon.AxonBuilder;


public class SameFieldTest
{

    
    @Test
    public void testSameFieldName()
    {
        SameFieldWithName swf = new SameFieldWithName();
        swf.setName("fp");
        SameField sf = new SameField();
        sf.setName("f1");
        sf.setSameFieldWithName(swf);
        Axon axon = new AxonBuilder().create();
        System.out.println(axon.toJson(sf));
    }
    
}
