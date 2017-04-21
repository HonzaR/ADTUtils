package com.honzar.adtutils.library;

public class ColorUtils {
    public static String getHtmlColor(int androidColor) {
        androidColor = androidColor & 0x00FFFFFF;
        return "#" + String.format("%06X", androidColor);
    }
}
