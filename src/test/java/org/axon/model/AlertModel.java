package org.axon.model;


public class AlertModel
{

    private String aname;
    private long size;
    private VmModel vm;

    public VmModel getVm()
    {
        return vm;
    }

    public void setVm(VmModel vm)
    {
        this.vm = vm;
    }

    public long getSize()
    {
        return size;
    }

    public void setSize(long size)
    {
        this.size = size;
    }

    public String getAname()
    {
        return aname;
    }

    public void setAname(String aname)
    {
        this.aname = aname;
    }

}
