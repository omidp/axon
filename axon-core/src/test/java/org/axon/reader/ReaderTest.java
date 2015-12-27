package org.axon.reader;

import java.util.Arrays;

import org.axon.model.ProfileModel;
import org.axon.model.UserModel;
import org.junit.Test;
import org.omidbiz.core.axon.Axon;
import org.omidbiz.core.axon.AxonBuilder;


public class ReaderTest
{

    // {"username" : "omidp", "profiles" : [{"type" : "Interest"}]}
    @Test
    public void testToObject()
    {
        // String json =
        // "{\"username\" : \"omidp\", \"profiles\" : [{\"type\" : \"Interest\"}]}";
        SimpleClass sl1 = new SimpleClass("test", new UserModel("omidp", Arrays.asList(new ProfileModel(null, "interest"))));
        Axon ax = new AxonBuilder().create();
        SimpleClass sl2 = ax.toObject(ax.toJson(sl1), SimpleClass.class, null);
        System.out.println(sl2);
        System.out.println(sl2.getName());
        System.out.println(ax.toJson(sl2));
    }

    public static class SimpleClass
    {
        private String name;
        private UserModel um;

        public SimpleClass()
        {
        }
        
        

        public SimpleClass(String name, UserModel um)
        {
            this.name = name;
            this.um = um;
        }



        public SimpleClass(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public UserModel getUm()
        {
            return um;
        }

        public void setUm(UserModel um)
        {
            this.um = um;
        }
        
        

    }

}
