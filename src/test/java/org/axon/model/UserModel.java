package org.axon.model;

import java.util.ArrayList;
import java.util.List;


public class UserModel
{

    private String username;
    private List<ProfileModel> profiles = new ArrayList<ProfileModel>();
    private String firstName;
    private HypModel hypervisor;

    public HypModel getHypervisor()
    {
        return hypervisor;
    }

    public void setHypervisor(HypModel hypervisor)
    {
        this.hypervisor = hypervisor;
    }

    public UserModel(String username)
    {
        this.username = username;
    }

    public UserModel(String username, List<ProfileModel> profiles)
    {
        this.username = username;
        this.profiles = profiles;
    }

    public UserModel()
    {
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public List<ProfileModel> getProfiles()
    {
        return profiles;
    }

    public void setProfiles(List<ProfileModel> profiles)
    {
        this.profiles = profiles;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

}
