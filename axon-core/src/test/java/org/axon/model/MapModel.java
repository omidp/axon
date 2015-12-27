package org.axon.model;

import java.util.Map;

public class MapModel
{

    public MapModel()
    {
    }

    public MapModel(Map<String, Object> context)
    {
        this.context = context;
    }

    Map<String, Object> context;

    public Map<String, Object> getContext()
    {
        return context;
    }

    public void setContext(Map<String, Object> context)
    {
        this.context = context;
    }

}
