package org.axon.model;



public class ProfileModel
{

    private UserModel user;
    private String type;
    private HypModel hm;
    
    
    

    public HypModel getHm()
    {
        return hm;
    }

    public void setHm(HypModel hm)
    {
        this.hm = hm;
    }

    public ProfileModel()
    {
    }

    public ProfileModel(UserModel user, String type)
    {
        this.user = user;
        this.type = type;
    }

    public UserModel getUser()
    {
        return user;
    }

    public void setUser(UserModel user)
    {
        this.user = user;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

}
