package com.honzar.adtutils.library;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Honza Rychnovský on 19.4.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class JsonUtils extends Utils {

    //
    // NOT NULL METHODS
    //

    /**
     * Returns JsonObject under the key or fallback in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched JsonObject
     * @param fallback - fallback is returned in case of any error
     *
     * @return JsonObject
     */
    public static JsonObject optJsonObjectNotNull(JsonObject json, String key, JsonObject fallback)
    {
        try {
            return checkParams(json, key) ? json.get(key).getAsJsonObject() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    /**
     * Returns JsonArray under the key or fallback in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched JsonArray
     * @param fallback - fallback is returned in case of any error
     *
     * @return JsonArray
     */
    public static JsonArray optJsonArrayNotNull(JsonObject json, String key, JsonArray fallback)
    {
        try {
            return checkParams(json, key) ? json.get(key).getAsJsonArray() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    /**
     * Returns String under the key or fallback in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched String
     * @param fallback - fallback is returned in case of any error
     *
     * @return String
     */
    public static String optStringNotNull(JsonObject json, String key, String fallback)
    {
        try {
            return checkParams(json, key) ? json.get(key).getAsString() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    /**
     * Returns char under the key or fallback in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched char
     * @param fallback - fallback is returned in case of any error
     *
     * @return char
     */
    public static char optCharNotNull(JsonObject json, String key, char fallback)
    {
        try {
            return checkParams(json, key) ? json.get(key).getAsCharacter() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    /**
     * Returns boolean under the key or fallback in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched boolean
     * @param fallback - fallback is returned in case of any error
     *
     * @return boolean
     */
    public static boolean optBooleanNotNull(JsonObject json, String key, boolean fallback)
    {
        try {
            return checkParams(json, key) ? json.get(key).getAsBoolean() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    /**
     * Returns BigDecimal under the key or fallback in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched BigDecimal
     * @param fallback - fallback is returned in case of any error
     *
     * @return BigDecimal
     */
    public static BigDecimal optBigDecimalNotNull(JsonObject json, String key, BigDecimal fallback)
    {
        try {
            return checkParams(json, key) ? json.get(key).getAsBigDecimal() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    /**
     * Returns BigInteger under the key or fallback in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched BigInteger
     * @param fallback - fallback is returned in case of any error
     *
     * @return BigInteger
     */
    public static BigInteger optBigIntegerNotNull(JsonObject json, String key, BigInteger fallback)
    {
        try {
            return checkParams(json, key) ? json.get(key).getAsBigInteger() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    /**
     * Returns byte under the key or fallback in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched byte
     * @param fallback - fallback is returned in case of any error
     *
     * @return byte
     */
    public static byte optByteNotNull(JsonObject json, String key, byte fallback)
    {
        try {
            return checkParams(json, key) ? json.get(key).getAsByte() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    /**
     * Returns short under the key or fallback in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched short
     * @param fallback - fallback is returned in case of any error
     *
     * @return short
     */
    public static short optShortNotNull(JsonObject json, String key, short fallback)
    {
        try {
            return checkParams(json, key) ? json.get(key).getAsShort() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    /**
     * Returns int under the key or fallback in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched int
     * @param fallback - fallback is returned in case of any error
     *
     * @return int
     */
    public static int optIntNotNull(JsonObject json, String key, int fallback)
    {
        try {
            return checkParams(json, key) ? json.get(key).getAsInt() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    /**
     * Returns long under the key or fallback in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched long
     * @param fallback - fallback is returned in case of any error
     *
     * @return long
     */
    public static long optLongNotNull(JsonObject json, String key, long fallback)
    {
        try {
            return checkParams(json, key) ? json.get(key).getAsLong() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    /**
     * Returns float under the key or fallback in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched float
     * @param fallback - fallback is returned in case of any error
     *
     * @return float
     */
    public static float optFloatNotNull(JsonObject json, String key, float fallback)
    {
        try {
            return checkParams(json, key) ? json.get(key).getAsFloat() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    /**
     * Returns double under the key or fallback in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched double
     * @param fallback - fallback is returned in case of any error
     *
     * @return double
     */
    public static double optDoubleNotNull(JsonObject json, String key, double fallback)
    {
        try {
            return checkParams(json, key) ? json.get(key).getAsDouble() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    /**
     * Returns Date under the key or fallback in case of error. Acceptable date format is according to ISO 8601 without millis (i.e. 2019-02-19T08:54Z).
     *
     * @param json - object to be searched for
     * @param key - key of searched double
     * @param fallback - fallback is returned in case of any error
     *
     * @return date
     */
    public static Date optDateNotNull(JsonObject json, String key, Date fallback)
    {
        try {

            if (checkParams(json, key)) {
                String dateStr = json.get(key).getAsString();
                return DateTimeUtils.decodeDateFromIsoString(dateStr);
            }
            return fallback;

        } catch (Exception e) {
            return fallback;
        }
    }

    /**
     * Returns Date with millis under the key or fallback in case of error. Acceptable date format is according to ISO 8601 without millis (i.e. 2019-02-19T08:54Z).
     *
     * @param json - object to be searched for
     * @param key - key of searched double
     * @param fallback - fallback is returned in case of any error
     *
     * @return date
     */
    public static Date optDateWithMillisNotNull(JsonObject json, String key, Date fallback)
    {
        try {

            if (checkParams(json, key)) {
                String dateStr = json.get(key).getAsString();
                return DateTimeUtils.decodeDateWithMillisFromIsoString(dateStr);
            }
            return fallback;

        } catch (Exception e) {
            return fallback;
        }
    }

    //
    // NULLABLE METHODS
    //

    /**
     * Returns JsonObject or null in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched object
     *
     * @return JsonObject
     */
    public static JsonObject getJsonObject(JsonObject json, String key)
    {
        try {
            return json.get(key) == null || json.get(key).isJsonNull() ? null : json.get(key).getAsJsonObject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns JsonArray or null in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched object
     *
     * @return JsonArray
     */
    public static JsonArray getJsonArray(JsonObject json, String key)
    {
        try {
            return json.get(key) == null ? null : json.get(key).getAsJsonArray();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns String or null in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched object
     *
     * @return JsonString
     */
    public static String getString(JsonObject json, String key)
    {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsString());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns Character or null in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched object
     *
     * @return Character
     */
    public static Character getChar(JsonObject json, String key)
    {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsCharacter());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns Boolean or null in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched object
     *
     * @return Boolean
     */
    public static Boolean getBoolean(JsonObject json, String key)
    {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsBoolean());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns BigDecimal or null in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched object
     *
     * @return BigDecimal
     */
    public static BigDecimal getBigDecimal(JsonObject json, String key)
    {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsBigDecimal());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns BigInteger or null in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched object
     *
     * @return BigInteger
     */
    public static BigInteger getBigInteger(JsonObject json, String key)
    {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsBigInteger());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns Byte or null in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched object
     *
     * @return Byte
     */
    public static Byte getByte(JsonObject json, String key)
    {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsByte());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns Short or null in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched object
     *
     * @return Short
     */
    public static Short getShort(JsonObject json, String key)
    {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsShort());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns Integer or null in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched object
     *
     * @return Integer
     */
    public static Integer getInt(JsonObject json, String key)
    {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsInt());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns Long or null in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched object
     *
     * @return Long
     */
    public static Long getLong(JsonObject json, String key)
    {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsLong());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns Float or null in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched object
     *
     * @return Float
     */
    public static Float getFloat(JsonObject json, String key)
    {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsFloat());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns Double or null in case of error.
     *
     * @param json - object to be searched for
     * @param key - key of searched object
     *
     * @return Double
     */
    public static Double getDouble(JsonObject json, String key)
    {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsDouble());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns Date under the key or null in case of error. Acceptable date format is according to ISO 8601 without millis (i.e. 2019-02-19T08:54Z).
     *
     * @param json - object to be searched for
     * @param key - key of searched double
     *
     * @return Date
     */
    public static Date getDate(JsonObject json, String key)
    {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : DateTimeUtils.decodeDateFromIsoString(json.get(key).getAsString()));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns Date with millis under the key or null in case of error. Acceptable date format is according to ISO 8601 without millis (i.e. 2019-02-19T08:54Z).
     *
     * @param json - object to be searched for
     * @param key - key of searched double
     *
     * @return Date
     */
    public static Date getDateWithMillis(JsonObject json, String key)
    {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : DateTimeUtils.decodeDateWithMillisFromIsoString(json.get(key).getAsString()));
        } catch (Exception e) {
            return null;
        }
    }

    // PRIVATE METHODS

    private static boolean checkParams(JsonObject json, String key)
    {
        return (json != null && key != null && !json.isJsonNull() && json.get(key) != null);
    }

}
