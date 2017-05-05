package com.honzar.adtutils.library;

import android.content.Context;

import java.text.Normalizer;
import java.util.List;
import java.util.UUID;

/**
 * Created by Honza Rychnovský on 20.9.2016.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class StringUtils extends Utils {


    // STRING FORMATTING

    /**
     * Checks string to null and emptiness
     *
     * @param value
     *
     * @return true/false
     */
    public static boolean checkEmptyString(String value)
    {
        return (value == null || value.isEmpty());
    }

    /**
     * Makes first letter of string upper
     *
     * @param value
     *
     * @return processed string or empty string
     */
    public static String getStringFirstUpper(String value)
    {
        if (checkEmptyString(value)) {
            return "";
        } else if (value.length() == 1) {
            return value.toUpperCase();
        } else {
            return value.substring(0, 1).toUpperCase() + value.substring(1);
        }
    }

    /**
     * Makes all letters of string upper
     *
     * @param value
     *
     * @return processed string or empty string
     */
    public static String getStringAllUpper(String value)
    {
        if (checkEmptyString(value))
            return "";
        else if (value.length() == 1)
            return value.toUpperCase();
        else
            return value.toUpperCase();
    }

    /**
     * Makes all letters of string lower
     *
     * @param value
     *
     * @return processed string or empty string
     */
    public static String getStringAllLower(String value)
    {
        if (checkEmptyString(value))
            return "";
        else if (value.length() == 1)
            return value.toUpperCase();
        else
            return value.toLowerCase();
    }

    /**
     * Capitalizes all words in string.
     * Warn: this method changes any white space to regular space " ".
     *
     * @param value
     *
     * @return processed string or empty string
     */
    private static String capitalize(String value)
    {
        if (checkEmptyString(value)) {
            return "";
        }

        String[] wordsArray = value.split("\\s+");
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < wordsArray.length; i++) {
            String s = wordsArray[i];

            if (!checkEmptyString(s)) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                builder.append(cap);

                if (i < wordsArray.length)
                    builder.append(" ");
            }
        }
        return builder.toString();
    }

    /**
     * Removes accents
     *
     * @param value
     *
     * @return string without accents or empty string in case of failure
     */
    public static String removeAccents(String value)
    {
        if (checkEmptyString(value)) {
            return "";
        }

        value = Normalizer.normalize(value, Normalizer.Form.NFD);
        value = value.replaceAll("[^\\p{ASCII}]", "");
        return value;
    }

    /**
     * Removes diacritic marks
     *
     * @param value
     *
     * @return string without diacritics marks or empty string in case of failure
     */
    public static String removeDiacriticalMarks(String value)
    {
        if (checkEmptyString(value)) {
            return "";
        }

        return Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    /**
     * Removes leading and trailing white spaces from string
     *
     * @param value
     *
     * @return string without leading and trailing white spaces or empty string in case of failure
     */
    public static String removeLeadingAndTrailingWhiteSpaces(String value)
    {
        if (checkEmptyString(value)) {
            return "";
        }
        return value.trim();
    }


    // STRING COLLECTIONS

    /**
     * Joins items from list to string separated by delimiter
     *
     * @param delimiter
     * @param items
     *
     * @return string consisting of items separated by delimiter or empty string in case of failure
     */
    public static String joinToStringWithDelimiters(String delimiter, List<Object> items)
    {
        if (delimiter == null) {
            return "";
        }
        if (items == null || items.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < items.size(); i++) {
            builder.append(items.get(i).toString());

            if (i < items.size())
                builder.append(delimiter);
        }

        return builder.toString();
    }

    /**
     * Splits string by delimiter
     *
     * @param value
     * @param delimiter
     *
     * @return String array of words from string
     */
    public static String[] getStringSplitedByAnyWhiteSpace(String value, String delimiter)
    {
        if (checkEmptyString(value) || delimiter == null) {
            return new String[]{};
        }
        return value.split(delimiter);
    }


    // GENERAL

    /**
     * Generates random UUID
     *
     * @return random UUID
     */
    public static String generateRandomGIUD()
    {
        return UUID.randomUUID().toString().toUpperCase();
    }

    /**
     * Returns value of string by its resource name
     *
     * @param context
     * @param resName
     *
     * @return value of string by its resource name or resource name in case of failure
     */
    public static String getStringValueByItsResourceName(Context context, String resName)
    {
        if (checkNull(context) || checkEmptyString(resName)) {
            return resName;
        }

        int resId = 0;

        try {
            String packageName = context.getPackageName();
            resId = context.getResources().getIdentifier(resName, "string", packageName);
        } catch (NullPointerException npe) {
            // nothing to do
        }

        if (resId == 0) {
            return resName;
        } else {
            return context.getResources().getString(resId);
        }
    }

}
