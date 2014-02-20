package org.axon.writer;

import org.axon.model.CommentModel;
import org.junit.Test;
import org.omidbiz.core.axon.Axon;
import org.omidbiz.core.axon.AxonBuilder;


/**
 * @author : Omid Pourhadi omidpourhadi [AT] gmail [DOT] com
 * @version 0.2
 */
public class NestedClassTest
{

    @Test
    public void testNestedClass(){
        CommentModel cm = new CommentModel();
        org.axon.model.CommentModel.Test t = cm.new Test();
        t.setName("Axon ROCKS!");
        Axon ax = new AxonBuilder().create();
        System.out.println(ax.toJson(t));
    }
    
    
}
