package org.axon.model;


public class HypModel
{

    private String hname;
    private String abc;
    UserModel um;
    AlertModel am;

    public AlertModel getAm()
    {
        return am;
    }

    public void setAm(AlertModel am)
    {
        this.am = am;
    }

    public String getAbc()
    {
        return abc;
    }

    public void setAbc(String abc)
    {
        this.abc = abc;
    }

    public UserModel getUm()
    {
        return um;
    }

    public void setUm(UserModel um)
    {
        this.um = um;
    }

    public String getHname()
    {
        return hname;
    }

    public void setHname(String hname)
    {
        this.hname = hname;
    }

}
