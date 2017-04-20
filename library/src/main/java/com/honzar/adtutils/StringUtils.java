package com.honzar.adtutils;

import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;

import java.util.List;

/**
 * Created by Honza Rychnovský on 20.9.2016.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class StringUtils {

    public static String joinToStringWithDelimiters(String delimiter, List<Long> members)
    {
        String result = "";
        if (members != null && !members.isEmpty()) {
            for (Long member : members) {
                result += member + delimiter;
            }
            if (result.length() > 0) result = result.substring(0, result.length() - delimiter.length());
        }
        return result;
    }

    public static String getStringFirstUpper(String s)
    {
        if (TextUtils.isEmpty(s))
            return "";
        else if (s.length() == 1)
            return s.toUpperCase();
        else
            return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static String getStringAllUpper(String s)
    {
        if (TextUtils.isEmpty(s))
            return "";
        else if (s.length() == 1)
            return s.toUpperCase();
        else
            return s.toUpperCase();
    }

    public static String getStringAllLower(String s)
    {
        if (TextUtils.isEmpty(s))
            return "";
        else if (s.length() == 1)
            return s.toUpperCase();
        else
            return s.toLowerCase();
    }

    public static String getStringWithUnitNotNull(float value, String unit)
    {
        if (value == Float.MIN_VALUE) {
            return "-";
        } else if (unit == null || unit.isEmpty()) {
            return NumberUtils.removeUnnecessaryDecimalZeros(value);
        } else {
            return NumberUtils.removeUnnecessaryDecimalZeros(value) + " " + unit;
        }
    }

    public static String checkEmptyString(String item)
    {
        return item == null || item.isEmpty() ? null : item;
    }

    public static SpannableString getStringUnderlined(String text)
    {
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;
    }
}
