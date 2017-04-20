package com.honzar.adtutils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Honza Rychnovský on 19.4.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class JsonUtils {


    private static boolean checkParams(JsonObject json, String key)
    {
        return (json != null && key != null && !json.isJsonNull() && json.get(key) != null);
    }

    //
    // NOT NULL METHODS
    //

    public static JsonObject optJsonObjectNotNull(JsonObject json, String key, JsonObject fallback) {
        try {
            return checkParams(json, key) ? json.get(key).getAsJsonObject() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    public static JsonArray optJsonArrayNotNull(JsonObject json, String key, JsonArray fallback) {
        try {
            return checkParams(json, key) ? json.get(key).getAsJsonArray() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    public static String optStringNotNull(JsonObject json, String key, String fallback) {
        try {
            return checkParams(json, key) ? json.get(key).getAsString() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    public static char optCharNotNull(JsonObject json, String key, char fallback) {
        try {
            return checkParams(json, key) ? json.get(key).getAsCharacter() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    public static boolean optBooleanNotNull(JsonObject json, String key, boolean fallback) {
        try {
            return checkParams(json, key) ? json.get(key).getAsBoolean() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    public static BigDecimal optBigDecimalNotNull(JsonObject json, String key, BigDecimal fallback) {
        try {
            return checkParams(json, key) ? json.get(key).getAsBigDecimal() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    public static BigInteger optBigIntegerNotNull(JsonObject json, String key, BigInteger fallback) {
        try {
            return checkParams(json, key) ? json.get(key).getAsBigInteger() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    public static byte optByteNotNull(JsonObject json, String key, byte fallback) {
        try {
            return checkParams(json, key) ? json.get(key).getAsByte() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    public static short optShortNotNull(JsonObject json, String key, short fallback) {
        try {
            return checkParams(json, key) ? json.get(key).getAsShort() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    public static int optIntNotNull(JsonObject json, String key, int fallback) {
        try {
            return checkParams(json, key) ? json.get(key).getAsInt() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    public static long optLongNotNull(JsonObject json, String key, long fallback) {
        try {
            return checkParams(json, key) ? json.get(key).getAsLong() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    public static float optFloatNotNull(JsonObject json, String key, float fallback) {
        try {
            return checkParams(json, key) ? json.get(key).getAsFloat() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    public static double optDoubleNotNull(JsonObject json, String key, double fallback) {
        try {
            return checkParams(json, key) ? json.get(key).getAsDouble() : fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    //
    // NULLABLE METHODS
    //

    public static JsonObject getJsonObject(JsonObject json, String key) {
        try {
            return json.get(key) == null || json.get(key).isJsonNull() ? null : json.get(key).getAsJsonObject();
        } catch (Exception e) {
            return null;
        }
    }

    public static JsonArray getJsonArray(JsonObject json, String key) {
        try {
            return json.get(key) == null ? null : json.get(key).getAsJsonArray();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getString(JsonObject json, String key) {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsString());
        } catch (Exception e) {
            return null;
        }
    }

    public static Character getChar(JsonObject json, String key) {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsCharacter());
        } catch (Exception e) {
            return null;
        }
    }

    public static Boolean getBoolean(JsonObject json, String key) {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsBoolean());
        } catch (Exception e) {
            return null;
        }
    }

    public static BigDecimal getBigDecimal(JsonObject json, String key) {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsBigDecimal());
        } catch (Exception e) {
            return null;
        }
    }

    public static BigInteger getBigInteger(JsonObject json, String key) {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsBigInteger());
        } catch (Exception e) {
            return null;
        }
    }

    public static Byte getByte(JsonObject json, String key) {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsByte());
        } catch (Exception e) {
            return null;
        }
    }

    public static Short getShort(JsonObject json, String key) {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsShort());
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer getInt(JsonObject json, String key) {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsInt());
        } catch (Exception e) {
            return null;
        }
    }

    public static Long getLong(JsonObject json, String key) {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsLong());
        } catch (Exception e) {
            return null;
        }
    }

    public static Float getFloat(JsonObject json, String key) {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsFloat());
        } catch (Exception e) {
            return null;
        }
    }

    public static Double getDouble(JsonObject json, String key) {
        try {
            return json.isJsonNull() ? null : (json.get(key) == null ? null : json.get(key).getAsDouble());
        } catch (Exception e) {
            return null;
        }
    }


}
