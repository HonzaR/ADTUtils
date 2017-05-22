package com.honzar.adtutils.library;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
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

public class ViewUtils extends Utils {


    /**
     * Adds view fully drawn callback
     *
     * @param view
     * @param listener
     *
     * @return true if succeed, false otherwise
     */
    public static boolean addOnGlobalLayoutCalledOnce(final View view, final ViewTreeObserver.OnGlobalLayoutListener listener)
    {
        if (view == null || listener == null) {
            return false;
        }

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
                listener.onGlobalLayout();
            }
        });

        return true;
    }

    /**
     * Enables or disables view group recursively
     *
     * @param group
     * @param state
     *
     * @return true if succeed, false otherwise
     */
    public static boolean enableOrDisableViewGroup(ViewGroup group, boolean state)
    {
        if (group == null) {
            return false;
        }

        group.setEnabled(state);

        for (int i = 0; i < group.getChildCount(); i++)
        {
            View child = group.getChildAt(i);

            if (child instanceof ViewGroup)
            {
                enableOrDisableViewGroup((ViewGroup) child, state);
            } else {
                child.setEnabled(state);
            }
        }

        return true;
    }


    // PIXEL CONVERSIONS

    /**
     * Converts density pixels to pixels
     *
     * @param context
     * @param dp
     *
     * @return pixels
     */
    public static int getPixelsFromDp(Context context, float dp)
    {
        return getPixelsFromDp(context, dp, 1f);
    }

    /**
     * Converts density pixels to pixels with scale
     *
     * @param context
     * @param dp
     * @param baseScale
     *
     * @return pixels
     */
    public static int getPixelsFromDp(Context context, float dp, float baseScale)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((dp * scale * baseScale) + 0.5f);
    }

    /**
     * Converts pixels to density pixels according to screen density
     *
     * @param context
     * @param pixels
     *
     * @return density pixels
     */
    public static float getDpForDensity(Context context, int pixels)
    {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, context.getResources().getDisplayMetrics());
    }

    /**
     * Converts pixels to scaled pixels according to screen density
     *
     * @param context
     * @param pixels
     *
     * @return scaled pixels
     */
    public static float getSpForDensity(float pixels, Context context)
    {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pixels, context.getResources().getDisplayMetrics());
    }


    // WEBVIEWS

    /**
     * Resumes paused webview
     *
     * @param view
     *
     * @return true if succeed, false otherwise
     */
    public static boolean resumeWebView(View view)
    {
        if (view == null) {
            return false;
        }

        if (view instanceof WebView) {
            ((WebView)view).resumeTimers();

        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;

            for (int i = 0; i < viewGroup.getChildCount(); i++)
            {
                View child = viewGroup.getChildAt(i);
                resumeWebView(child);
            }
        }

        return  true;
    }

    /**
     * Pauses running webview
     *
     * @param view
     *
     * @return true if succeed, false otherwise
     */
    public static boolean pauseWebView(View view)
    {
        if (view == null) {
            return false;
        }

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

        return true;
    }


    // KEYBOARD

    /**
     * Shows soft keyboard
     *
     * @param currentFocusedView
     *
     * @return true if succeed, false otherwise
     */
    public static boolean showSoftKeyboard(View currentFocusedView)
    {
        if (currentFocusedView == null) {
            return false;
        }

        InputMethodManager imm = (InputMethodManager) currentFocusedView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.showSoftInput(currentFocusedView, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Hides soft keyboard
     *
     * @param currentFocusedView
     *
     * @return true if succeed, false otherwise
     */
    public static boolean hideSoftKeyboard(View currentFocusedView)
    {
        if (currentFocusedView == null) {
            return false;
        }

        InputMethodManager imm = (InputMethodManager)currentFocusedView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), 0);
    }


    // EDITTEXTS

    /**
     * Clears focus on editText and hides soft keyboard
     *
     * @param context
     * @param view
     *
     * @return true if succeed, false otherwise
     */
    public static boolean clearFocusOnEditText(Context context, View view)
    {
        if (context == null || view == null) {
            return false;
        }

        View focused = ((Activity)context).getCurrentFocus();
        if (focused != null) {
            focused.clearFocus();
        }

        return hideSoftKeyboard(view);
    }

    /**
     * Requests focus on editText and shows soft keyboard
     *
     * @param context
     * @param view
     *
     * @return true if succeed, false otherwise
     */
    public static boolean requestFocusOnEditText(Context context, View view)
    {
        if (context == null || view == null) {
            return false;
        }

        view.requestFocus();
        return showSoftKeyboard(view);
    }

    // TEXTVIEWS

    /**
     * Returns textView height
     *
     * @param textView
     * @param screenWidth
     *
     * @return textView height or -1 in case of failure
     */
    public static int getTextViewHeight(TextView textView, int screenWidth)
    {
        if (textView == null || screenWidth < 0) {
            return -1;
        }

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    /**
     * Returns textView width
     *
     * @param textView
     * @param screenWidth
     *
     * @return textView width or -1 in case of failure
     */
    public static int getTextViewWidth(TextView textView, int screenWidth)
    {
        if (textView == null || screenWidth < 0) {
            return -1;
        }

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredWidth();
    }

    /**
     * Makes text in textView underlined
     *
     * @param view
     *
     * @return true if succeed, false otherwise
     */
    public static boolean makeTextUnderlined(TextView view)
    {
        if (checkNull(view)) {
            return false;
        }

        SpannableString content = new SpannableString(view.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        view.setText(content);
        return true;
    }


    // SCREEN

    /**
     * Returns usable screen height without actionBar height, navigation bar height and status bar height
     *
     * @param context
     *
     * @return usable screen height or -1 in case of failure
     */
    public static int resolveUsableScreenHeight(Context context)
    {
        if (context == null) {
            return -1;
        }

        Activity activity = null;
        int actionBarHeight = 0;

        if (context instanceof AppCompatActivity) {
            activity = (AppCompatActivity) context;
            android.support.v7.app.ActionBar ab = ((AppCompatActivity) context).getSupportActionBar();
            if (ab != null) actionBarHeight = ab.getHeight();
        } else if (context instanceof Activity) {
            activity = (Activity) context;
            android.app.ActionBar ab = ((Activity) context).getActionBar();
            if (ab != null) actionBarHeight = ab.getHeight();
        }

        if (activity == null) {
            return -1;
        }

        final int resourceStatusBar = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        final int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceStatusBar);
        final int resourceNavBar = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        final int navigationBarHeight = activity.getResources().getDimensionPixelSize(resourceNavBar);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int screenHeight = size.y;

        return (screenHeight - actionBarHeight - statusBarHeight - navigationBarHeight);
    }

    /**
     * Returns screen height
     *
     * @param context
     *
     * @return screen height or -1 in case of failure
     */
    public static int resolveScreenHeight(Context context)
    {
        if (context == null) {
            return -1;
        }

        Activity activity = (Activity) context;

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    /**
     * Returns screen width
     *
     * @param context
     *
     * @return screen width or -1 in case of failure
     */
    public static int resolveScreenWidth(Context context)
    {
        if (context == null) {
            return -1;
        }

        Activity activity = (Activity) context;

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    /**
     * Checks landscape screen orientation
     *
     * @param context
     *
     * @return true if orientation is landscape, false otherwise
     */
    public static boolean isScreenOrientationLandscape(Context context)
    {
        if (context == null) {
            return false;
        }

        Activity activity = (Activity) context;
        return activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * Checks portrait screen orientation
     *
     * @param context
     *
     * @return true if orientation is portrait, false otherwise
     */
    public static boolean isScreenOrientationPortrtait(Context context)
    {
        if (context == null) {
            return false;
        }

        Activity activity = (Activity) context;
        return activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * Set screen touchable or not touchable according to set flag
     *
     * @param context
     * @param enable
     *
     * @return true if screen state have been changed, false otherwise
     */
    public static boolean setScreenTouchableState(Context context, boolean enable)
    {
        if (context == null) {
            return false;
        }

        Activity activity = (Activity) context;

        if (enable) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        return true;
    }


    // SYSTEM BARS

    /**
     * Get system navigation bar height
     *
     * @param context
     *
     * @return number bigger than 0 if there is some navigation bar, 0 otherwise
     */
    public static int getSystemNavigationBarHeight(Context context)
    {
        if (context == null) {
            return 0;
        }

        if (!DeviceUtils.checkDeviceHasSoftwareNavBar(context)) {
            return 0;
        }

        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * Get system status bar height
     *
     * @param context
     *
     * @return number bigger than 0 if there is some status bar, 0 otherwise
     */
    public static int getSystemStatusBarHeight(Context context)
    {
        if (context == null) {
            return 0;
        }

        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}
