package org.omidbiz.core.axon.internal;

public class PathProcessor
{
    public static int getIndentation(String[] path)
    {
        int a = path.length;
        for (int i = 0; i < path.length; i++)
        {
            if (path[i].endsWith("[]") && !path[i].startsWith("[]"))
                a++;
        }
        return a;
    }

    public static String[] splitPath(String path)
    {
        if (path.length() > 0)
            return path.split("\\.");
        else
            return new String[0];
    }
    
    public static String appendPath(String path, String p) {
        if (path.length() > 0)
            return path + "." + p;
        else 
            return p;
        
    }

}
