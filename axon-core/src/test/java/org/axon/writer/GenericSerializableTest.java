package org.axon.writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
public class GenericSerializableTest
{

    @Test
    public void testConvertor()
    {
        GenericModel<GenericEntity> m = new GenericModel<>();
        List<GenericEntity> asList = Arrays.asList(new GenericEntity(new Date(), "m1"), new GenericEntity(new Date(), "m1"));
        m.setResultList(new ArrayList<>(asList));
        Axon ax = new AxonBuilder().create();
        System.out.println(ax.toJson(m));

    }

    public static class GenericModel<T>
    {
        private ArrayList<T> resultList;

        public ArrayList<T> getResultList()
        {
            return resultList;
        }

        public void setResultList(ArrayList<T> resultList)
        {
            this.resultList = resultList;
        }

    }

    public static class GenericEntity
    {
        private Date startDate;
        private String name;

        public GenericEntity()
        {
        }

        public GenericEntity(Date startDate, String name)
        {
            this.startDate = startDate;
            this.name = name;
        }

        public Date getStartDate()
        {
            return startDate;
        }

        public void setStartDate(Date startDate)
        {
            this.startDate = startDate;
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
