package org.axon.writer;

import org.axon.model.DocumentModel;
import org.junit.Test;
import org.omidbiz.core.axon.Axon;
import org.omidbiz.core.axon.AxonBuilder;
import org.omidbiz.core.axon.internal.BasicElement;
import org.omidbiz.core.axon.internal.Element;
import org.omidbiz.core.axon.internal.SerializationContext;
import org.omidbiz.core.axon.internal.TypeConverter;


/**
 * @author : Omid Pourhadi omidpourhadi [AT] gmail [DOT] com
 * @version 0.2
 */
public class ConvertorTest
{

    @Test
    public void testConvertor(){
        DocumentModel parent  =new DocumentModel(1, "parentName", null);
        DocumentModel dm = new DocumentModel(2, "omidp", parent);
        Axon ax = new AxonBuilder().addTypeConverter(new FieldConvertor()).create();
        System.out.println(ax.toJson(dm));
        
    }
    
    
    class FieldConvertor implements TypeConverter<DocumentModel>{

        @Override
        public boolean applies(Object instance)
        {
            if(instance instanceof DocumentModel)
            {
                DocumentModel m = (DocumentModel) instance;
                if(m.getParent() == null)
                    return true;
                else
                    return false;
                
            }
            return false;
        }

        @Override
        public Element write(DocumentModel instance, SerializationContext ctx)
        {
            return new BasicElement(2345);
        }
        
    }
    
}
