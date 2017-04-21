package com.honzar.adtutils.library;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.TextView;

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

    public static void resumeWebView(View view)
    {
        if (view instanceof WebView)
        {
            ((WebView)view).resumeTimers();
        } else if (view instanceof ViewGroup)
        {
            ViewGroup viewGroup = (ViewGroup)view;
            for (int i = 0; i < viewGroup.getChildCount(); i++)
            {
                View child = viewGroup.getChildAt(i);
                resumeWebView(child);
            }
        }
    }

    public static void pauseWebView(View view)
    {
        if (view instanceof WebView)
        {
            ((WebView)view).pauseTimers();
        } else if (view instanceof ViewGroup)
        {
            ViewGroup viewGroup = (ViewGroup)view;
            for (int i = 0; i < viewGroup.getChildCount(); i++)
            {
                View child = viewGroup.getChildAt(i);
                pauseWebView(child);
            }
        }
    }

    public static void clearFocusOnEditText(View v, Context context)
    {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
        View focused = ((Activity)context).getCurrentFocus();
        if (focused != null) {
            focused.clearFocus();
        }
    }

    public static void requestFocusOnEditText(View v, Context context)
    {
        v.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    public static int getTextViewHeight(TextView t, int screenWidth)
    {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        t.measure(widthMeasureSpec, heightMeasureSpec);
        return t.getMeasuredHeight();
    }

    public static int getTextViewWidth(TextView t, int screenWidth)
    {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        t.measure(widthMeasureSpec, heightMeasureSpec);
        return t.getMeasuredWidth();
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
    }
}
