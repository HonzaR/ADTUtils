package com.honzar.adtutils.library;

import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
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

    public static String removeAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[^\\p{ASCII}]", "");
        return s;
    }

    public static String getStringFromLocalHtml(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            File fl = new File(filePath);
            FileInputStream fin = new FileInputStream(fl);
            BufferedReader in = new BufferedReader(new InputStreamReader(fin));

            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }

        return contentBuilder.toString();
    }


    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String getFirstLetterCapitalized(String s)
    {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String getCapitalized(String userName)
    {
        String withoutLeadingAndTrailing = userName.trim();

        String[] splited = getStringSplited(withoutLeadingAndTrailing);
        String firstUppers = "";

        for (int i = 0; i < splited.length; i++) {
            firstUppers += getFirstLetterCapitalized(splited[i]) + " ";
        }

        return firstUppers.substring(0, firstUppers.length() - 1);
    }

    public static String[] getStringSplited(String userName)
    {
        return userName.split("\\s+");
    }

    public static String removeDiacriticalMarks(String string)
    {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static void makeTextUnderlined(TextView column)
    {
        SpannableString content = new SpannableString(column.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        column.setText(content);
    }

    public static String removeLeadingAndTrailingWhiteSpaces(String string)
    {
        return string.trim();
    }
}
