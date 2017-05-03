package com.honzar.adtutils.library;

import android.location.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EqualsUtils {
	public static int HASHCODE_INVALID = 654123456;
	public static int HASHCODE_INVALID_REPLACEMENT = 654123457;
	
    public static <T> int hashCodeCheckNull(T o) {
        return o == null ? 0 : o.hashCode();
    }
    
    public static <T> boolean equalsCheckNull(T lhs, T rhs) {
        return lhs == null ? rhs == null : lhs.equals(rhs);
    }
    
    public static int itemsHashCode(List<?> items) {
        int result = 17; 
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                result = 31 * result + (items.get(i) == null ? 0 : items.get(i).hashCode());
            }
        }
        return result;
    }

    public static int itemsHashCode(int[] items) {
        int result = 17;
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                result = 31 * result + items[i];
            }
        }
        return result;
    }

    
    public static boolean itemsEqual(List<?> lhs, List<?> rhs) {
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
    
    public static int itemsOfItemsHashCode(List<? extends List<?>> items) {
        int result = 17; 
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                result = 31 * result + itemsHashCode(items.get(i));
            }
        }
        return result;
    }
    
    public static boolean itemsOfItemsEqual(List<? extends List<?>> lhs, List<? extends List<?>> rhs) {
        if (lhs == rhs)
            return true;
        if (lhs == null || rhs == null)
            return false;           
        if (lhs.size() != rhs.size())
            return false;
                
        for (int i = 0; i < lhs.size(); i++) {
            if (!itemsEqual(lhs.get(i), rhs.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean stringEquality(String s1, String s2) {
        if (s1 == null && s2 == null) return true;
        if (s1 == null) return false;
        if (s2 == null) return false;

        return s1.equals(s2);
    }

    public static boolean listEquals(ArrayList<?> first, ArrayList<?> second)
    {
        if (first == second) return true;
        if (first == null) return false;
        if (second == null) return false;

        if (first.size() != second.size()) {
            return false;
        }
        for (int i = 0; i < first.size(); i++) {
            if (!first.get(i).equals(second.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean integerEquality(Integer i1, Integer i2) {
        if (i1 == null && i2 == null) return true;
        if (i1 == null) return false;
        if (i2 == null) return false;

        return i1.intValue() == i2.intValue();
    }

    public static boolean longEquality(Long l1, Long l2) {
        if (l1 == null && l2 == null) return true;
        if (l1 == null) return false;
        if (l2 == null) return false;

        return l1.longValue() == l2.longValue();
    }

    public static boolean integesrEquality(Integer[] is1, Integer[] is2) {
        if (is1 == null && is2 == null) return true;
        if (is1 == null) return false;
        if (is2 == null) return false;

        return Arrays.equals(is1, is2);
    }

    public static boolean floatEquality(Float i1, Float i2) {
        if (i1 == null && i2 == null) return true;
        if (i1 == null) return false;
        if (i2 == null) return false;

        return i1.floatValue() == i2.floatValue();
    }

//    public static boolean boundsEquality(Object o1, Object o2) {
//        if (o1 == o2) return true;
//        if (o1 == null) return false;
//        if (o2 == null) return false;
//
//        LatLngBounds bounds1 = (LatLngBounds) o1;
//        LatLngBounds bounds2 = (LatLngBounds) o2;
//
//        if (bounds1.southwest.latitude != bounds2.southwest.latitude)
//            return false;
//        if (bounds1.southwest.longitude != bounds2.southwest.longitude)
//            return false;
//        if (bounds1.northeast.latitude != bounds2.northeast.latitude)
//            return false;
//        if (bounds1.northeast.longitude != bounds2.northeast.longitude)
//            return false;
//        return true;
//
//    }

    public static boolean locationEquality(Location first, Location second)
    {
        if (first == second) return true;
        if (first == null) return false;
        if (second == null) return false;

        return first.getLatitude() == second.getLatitude() && first.getLongitude() == second.getLongitude();
    }

    public static boolean currLocaleEquality(String s1, String s2)
    {
        if (s1 == null && s2 == null) return true;
        if (s1 == null) return false;
        if (s2 == null) return false;

        return s1.equals(s2);
    }
}

