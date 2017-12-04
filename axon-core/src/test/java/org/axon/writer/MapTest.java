package org.axon.writer;

import java.util.HashMap;
import java.util.Map;

import org.axon.model.DocumentModel;
import org.axon.model.MapModel;
import org.junit.Test;
import org.omidbiz.core.axon.Axon;
import org.omidbiz.core.axon.AxonBuilder;


public class MapTest
{

    @Test
    public void testMap()
    {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("dm", new DocumentModel(1, "doc map", null));
        maps.put("name", "anotherValue");
        MapModel mm = new MapModel();
        mm.setContext(maps);
//        Map<String, String> maps = new HashMap<>();
//        maps.put("test", "tttt");
        Axon axon = new AxonBuilder().create();
        System.out.println(axon.toJson(mm));
    }
    
    
    
}
