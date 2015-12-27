package org.axon.model;

import java.util.ArrayList;
import java.util.List;


public class VmModel
{

    private String name;

    private List<AlertModel> alerts = new ArrayList<AlertModel>();

    private HypModel hypervisor;
    
    

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<AlertModel> getAlerts()
    {
        return alerts;
    }

    public void setAlerts(List<AlertModel> alerts)
    {
        this.alerts = alerts;
    }

    public HypModel getHypervisor()
    {
        return hypervisor;
    }

    public void setHypervisor(HypModel hypervisor)
    {
        this.hypervisor = hypervisor;
    }

}
