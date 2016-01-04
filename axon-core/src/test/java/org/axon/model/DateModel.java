package org.axon.model;

import java.sql.Timestamp;
import java.util.Date;

public class DateModel extends HypModel
{

    public DateModel()
    {
    }

    public DateModel(Date startDate)
    {
        this.startDate = startDate;
    }

    private Date startDate;
    private java.sql.Date sqlDate;
    private Timestamp timestamp;

    public Timestamp getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp)
    {
        this.timestamp = timestamp;
    }

    public java.sql.Date getSqlDate()
    {
        return sqlDate;
    }

    public void setSqlDate(java.sql.Date sqlDate)
    {
        this.sqlDate = sqlDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

}
