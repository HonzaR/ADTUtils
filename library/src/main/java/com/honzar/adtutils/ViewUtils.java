package com.honzar.adtutils;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Honza Rychnovský on 19.4.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class ViewUtils {

    public static void addOnGlobalLayoutCalledOnce(final View view, final ViewTreeObserver.OnGlobalLayoutListener l)
    {

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver vto = view.getViewTreeObserver();
                if (vto != null && vto.isAlive()) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                        vto.removeGlobalOnLayoutListener(this);
                    else
                        vto.removeOnGlobalLayoutListener(this);
                }
                l.onGlobalLayout();
            }
        });
    }

    public static int getPixelsFromDp(Context context, float dp)
    {
        return getPixelsFromDp(context, dp, 1f);
    }

    public static int getPixelsFromDp(Context context, float dp, float baseScale)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((dp * scale * baseScale) + 0.5f);
    }
}
