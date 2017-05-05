package com.honzar.adtutils.library;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import java.util.Random;

/**
 * Created by Honza Rychnovský on 4.5.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class ColorUtils extends Utils {


    /**
     * Returns html color string or null in case of error
     *
     * @param color
     *
     * @return html color string or null in case of error
     */
    public static String getHtmlColor(int color)
    {
        if (color > 0) {
            return null;
        }
        color = color & 0x00FFFFFF;
        return "#" + String.format("%06X", color);
    }

    /**
     * Returns tinted drawable icon or null in case of failure
     *
     * @param context
     * @param iconResourceId
     * @param colorResourceId
     *
     * @return tinted drawable icon or null in case of failure
     */
    public static Drawable getTintedDrawableIcon(Context context, int iconResourceId, int colorResourceId)
    {
        if (!checkNull(context) && iconResourceId > 0 && colorResourceId > 0) {
            return null;
        }

        int color = ContextCompat.getColor(context, colorResourceId);
        Drawable drawable = ContextCompat.getDrawable(context, iconResourceId);
        DrawableCompat.setTint(drawable, color);
        return drawable;
    }

    /**
     * Returns random color or -1 in case of error
     *
     * @param context
     *
     * @return random color or -1 in case of error
     */
    public static int getRandomColor(Context context)
    {
        if (!checkNull(context)) {
            return 0;
        }
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    /**
     * Returns transparency color
     *
     * @param color to be processed
     * @param transparency transparency level in range 0.0 - 1.0
     *
     * @return transparency color
     */
    public static int makeColorTransparent(int color, float transparency)
    {
        if (transparency >= 0f && transparency <= 1f) {
            int alpha = (int) (transparency * 255);
            return (color & 0x00ffffff) | (alpha << 24);
        }

        return color;
    }

}
