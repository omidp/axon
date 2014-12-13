package org.omidbiz.core.axon;

public class WordUtils
{
    public static String uncapitalize(String str)
    {
        return uncapitalize(str, null);
    }

    public static String capitalizeFirstWord(String str)
    {
        if(str == null)
            return null;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String uncapitalize(String str, char... delimiters)
    {
        int delimLen = delimiters == null ? -1 : delimiters.length;
        if (isEmpty(str) || delimLen == 0)
        {
            return str;
        }
        char[] buffer = str.toCharArray();
        boolean uncapitalizeNext = true;
        for (int i = 0; i < buffer.length; i++)
        {
            char ch = buffer[i];
            if (isDelimiter(ch, delimiters))
            {
                uncapitalizeNext = true;
            }
            else if (uncapitalizeNext)
            {
                buffer[i] = Character.toLowerCase(ch);
                uncapitalizeNext = false;
            }
        }
        return new String(buffer);
    }

    private static boolean isDelimiter(char ch, char[] delimiters)
    {
        if (delimiters == null)
        {
            return Character.isWhitespace(ch);
        }
        for (char delimiter : delimiters)
        {
            if (ch == delimiter)
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(CharSequence cs)
    {
        return cs == null || cs.length() == 0;
    }
}