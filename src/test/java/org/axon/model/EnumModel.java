package org.axon.model;

public class EnumModel
{

    public enum EnumHelper
    {
        HI, BYE;
    }

    private EnumHelper enumHelper;

    public EnumHelper getEnumHelper()
    {
        return enumHelper;
    }

    public void setEnumHelper(EnumHelper enumHelper)
    {
        this.enumHelper = enumHelper;
    }

}
