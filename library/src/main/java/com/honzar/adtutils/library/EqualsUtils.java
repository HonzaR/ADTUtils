package com.honzar.adtutils.library;

import android.location.Location;

import java.util.Arrays;
import java.util.List;

public class EqualsUtils extends Utils {

    /**
     * Returns objects hashcode
     *
     * @param object
     *
     * @return objects hashcode
     */
    public static <T> int hashCodeCheckNull(T object)
    {
        return object == null ? 0 : object.hashCode();
    }

    /**
     * Compare two objects for equality
     *
     * @param lhs
     * @param rhs
     *
     * @return true in case of objects equality, false otherwise
     */
    public static <T> boolean equalsCheckNull(T lhs, T rhs)
    {
        return lhs == null ? rhs == null : lhs.equals(rhs);
    }

    /**
     * Returns hashcode of list of items
     *
     * @param items
     *
     * @return items hashcode
     */
    public static int itemsHashCode(List<?> items)
    {
        int result = 17; 
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                result = 31 * result + (items.get(i) == null ? 0 : items.get(i).hashCode());
            }
        }
        return result;
    }

    /**
     * Returns hashcode of array of integers
     *
     * @param items
     *
     * @return integers hashcode
     */
    public static int itemsHashCode(int[] items)
    {
        int result = 17;
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                result = 31 * result + items[i];
            }
        }
        return result;
    }

    /**
     * Compares two lists for equality
     *
     * @param lhs
     * @param rhs
     *
     * @return true in case of lists equality, false otherwise
     */
    public static boolean itemsEqual(List<?> lhs, List<?> rhs)
    {
        if (lhs == rhs)
            return true;
        if (lhs == null || rhs == null)
            return false;           
        if (lhs.size() != rhs.size())
            return false;
                
        for (int i = 0; i < lhs.size(); i++) {
            if (lhs.get(i) == null) {
                if (rhs.get(i) != null)
                    return false;
            }
            else if (!lhs.get(i).equals(rhs.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compares two arrays of integers for equality
     *
     * @param lhs
     * @param rhs
     *
     * @return true in case of arrays equality, false otherwise
     */
    public static boolean itemsEqual(int[] lhs, int[] rhs) {
        if (lhs == rhs)
            return true;
        if (lhs == null || rhs == null || lhs.length != rhs.length)
            return false;

        for (int i = 0; i < lhs.length; i++) {
            if (lhs[i] != rhs[i])
                return false;
        }
        return true;
    }

    /**
     * Compares two strings for equality
     *
     * @param s1
     * @param s2
     *
     * @return true in case of strings equality, false otherwise
     */
    public static boolean stringEquality(String s1, String s2)
    {
        if (s1 == null && s2 == null)
            return true;
        if (s1 == null)
            return false;
        if (s2 == null)
            return false;

        return s1.equals(s2);
    }

    /**
     * Compares two integers for equality
     *
     * @param i1
     * @param i2
     *
     * @return true in case of integers equality, false otherwise
     */
    public static boolean integerEquality(Integer i1, Integer i2)
    {
        if (i1 == null && i2 == null)
            return true;
        if (i1 == null)
            return false;
        if (i2 == null)
            return false;

        return i1.intValue() == i2.intValue();
    }

    /**
     * Compares two longs for equality
     *
     * @param l1
     * @param l2
     *
     * @return true in case of longs equality, false otherwise
     */
    public static boolean longEquality(Long l1, Long l2)
    {
        if (l1 == null && l2 == null)
            return true;
        if (l1 == null)
            return false;
        if (l2 == null)
            return false;

        return l1.longValue() == l2.longValue();
    }

    /**
     * Compares two floats for equality
     *
     * @param f1
     * @param f2
     *
     * @return true in case of longs equality, false otherwise
     */
    public static boolean floatEquality(Float f1, Float f2)
    {
        if (f1 == null && f2 == null)
            return true;
        if (f1 == null)
            return false;
        if (f2 == null)
            return false;

        return f1.floatValue() == f2.floatValue();
    }

    /**
     * Compares two lists of integers for equality
     *
     * @param is1
     * @param is2
     *
     * @return true in case of arrays equality, false otherwise
     */
    public static boolean integesrEquality(Integer[] is1, Integer[] is2)
    {
        if (is1 == null && is2 == null)
            return true;
        if (is1 == null)
            return false;
        if (is2 == null)
            return false;

        return Arrays.equals(is1, is2);
    }

    /**
     * Compares two locations for equality
     *
     * @param loc1
     * @param loc2
     *
     * @return true in case of locations equality, false otherwise
     */
    public static boolean locationEquality(Location loc1, Location loc2)
    {
        if (loc1 == loc2)
            return true;
        if (loc1 == null)
            return false;
        if (loc2 == null)
            return false;

        return loc1.getLatitude() == loc2.getLatitude() && loc1.getLongitude() == loc2.getLongitude();
    }

}

