package org.axon.writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.axon.model.CommentModel;
import org.axon.model.DocumentModel;
import org.junit.Before;
import org.junit.Test;
import org.omidbiz.core.axon.Axon;
import org.omidbiz.core.axon.AxonBuilder;
import org.omidbiz.core.axon.filters.RecursionControlFilter;


/**
 * @author : Omid Pourhadi omidpourhadi [AT] gmail [DOT] com
 * @version 0.2
 */
public class ConvertRecursiveModelToJson
{

    CommentModel comment;
    DocumentModel rootDocument;
    List<DocumentModel> children;
    DocumentModel child1;
    Axon ax;
    
    
    @Before
    public void setup(){
        rootDocument = new DocumentModel(1, "root", null);
        //
        children = new ArrayList<DocumentModel>();
        child1 = new DocumentModel(1, "child 1", rootDocument);
        children.add(child1);
        child1.setChildren(Arrays.asList(new DocumentModel(2, "child 2", child1)));
        rootDocument.setChildren(children);
        //
        comment = new CommentModel(new Long(1), rootDocument, "Comment for Root");
        child1.setComments(Arrays.asList(comment));
        //
        ax = new AxonBuilder().addFilter(new RecursionControlFilter()).create();
        
    }
    
    @Test
    public void testAxonJson(){
       String child1ToString = ax.toJson(child1);
       System.out.println(child1ToString);
       //
    }
    
    
}
