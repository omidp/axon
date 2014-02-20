package org.axon.writer;

import java.util.Arrays;

import org.axon.model.ProfileModel;
import org.axon.model.UserModel;
import org.junit.Before;
import org.junit.Test;
import org.omidbiz.core.axon.Axon;
import org.omidbiz.core.axon.AxonBuilder;


/**
 * @author : Omid Pourhadi omidpourhadi [AT] gmail [DOT] com
 * @version 0.2
 */
public class ConvertSimpleModel
{

    UserModel user;
    ProfileModel profile;
    
    @Before
    public void setup(){
        user = new UserModel("omidp");
        profile = new ProfileModel(user, "Interest");
        user.setProfiles(Arrays.asList(profile));
    }
    
    @Test
    public void testUserJson() {
        Axon ax = new AxonBuilder().create();
        System.out.println(ax.toJson(user));
    }
    
    @Test
    public void testProfileJson() {
        Axon ax = new AxonBuilder().create();
        System.out.println(ax.toJson(profile));
    }
    
}
