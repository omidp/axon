package org.axon.writer;



import org.junit.Test;
import org.omidbiz.core.axon.Axon;
import org.omidbiz.core.axon.AxonBuilder;


public class OnlyGetterModelTest
{

    public class OnlyGetterModel
    {
        public int getInt()
        {
            return 10;
        }

        
        public String getString()
        {
            return "omid";
        }
    }

    public class GetterSetterModel
    {
        int i;
        String s;

        public int getInt()
        {
            return i;
        }

        public void setInt(int i)
        {
            this.i = i;
        }

        public String getString()
        {
            return s;
        }

        public void setString(String s)
        {
            this.s = s;
        }
    }
    
    @Test
    public void testSameFieldName()
    {
        Axon axon = new AxonBuilder().create();

        GetterSetterModel gsm = new GetterSetterModel();
        gsm.setInt(1);
        gsm.setString("saber");
        
        OnlyGetterModel gm = new OnlyGetterModel();
        
        System.out.println(axon.toJson(gsm));

        System.out.println(axon.toJson(gm));
    }

}
