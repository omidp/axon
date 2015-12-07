package org.axon.writer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.axon.model.ProfileModel;
import org.junit.Test;
import org.omidbiz.core.axon.Axon;
import org.omidbiz.core.axon.AxonBuilder;

public class PrimitiveTest
{

    @Test
    public void testSerialize()
    {
        Classroom klass = new Classroom();
        klass.setName("math");
        klass.setStudents(Arrays.asList("ali", "reza"));
        ProfileModel pm1 = new ProfileModel();
        pm1.setType("TYPE1");
        klass.setProfiles(Arrays.asList(pm1));
        Axon ax = new AxonBuilder().create();
        String json = ax.toJson(klass);
        System.out.println(json);
        Classroom classroom = ax.toObject(json, Classroom.class, null);
        System.out.println(classroom.getStudents().size());
        System.out.println(classroom.getName());
        System.out.println(classroom.getProfiles().size());

    }

    public static class Classroom implements Serializable
    {
        private List<String> students = new ArrayList<String>();
        private List<ProfileModel> profiles = new ArrayList<ProfileModel>();
        private String name;

        public List<ProfileModel> getProfiles()
        {
            return profiles;
        }

        public void setProfiles(List<ProfileModel> profiles)
        {
            this.profiles = profiles;
        }

        public List<String> getStudents()
        {
            return students;
        }

        public void setStudents(List<String> students)
        {
            this.students = students;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

    }
}
