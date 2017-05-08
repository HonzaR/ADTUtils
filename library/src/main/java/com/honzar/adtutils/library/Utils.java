package com.honzar.adtutils.library;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.location.Location;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Honza Rychnovský on 5.5.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class Utils {


    // CHECK METHODS

    /**
     * Checks if is wifi module turned on
     *
     * @param context
     *
     * @return true/false
     */
    public static boolean checkWifiEnabled(Context context)
    {
        if (checkNull(context)) {
            return false;
        }
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }

    /**
     * Checks if is device connected to internet
     *
     * @param context
     *
     * @return true/false
     */
    public static boolean checkInternetConnection(Context context)
    {
        if (checkNull(context)) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Checks if is device connected to internet through 3g module
     *
     * @param context
     *
     * @return true/false
     */
    public static boolean checkMobileDataConnectionEnabled(Context context)
    {
        if (checkNull(context)) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() && (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Checks if is bluetooth module turned on
     *
     * @param context
     *
     * @return true/false
     */
    public static boolean checkBluetoothEnabled(Context context)
    {
        if (checkNull(context)) {
            return false;
        }
        if ((ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)) {
            return false;
        }

        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (bluetoothAdapter != null && bluetoothAdapter.isEnabled());
    }

    /**
     * Checks if is location module turned on
     *
     * @param context
     *
     * @return true/false
     */
    public static boolean checkLocationEnabled(Context context)
    {
        if (checkNull(context)) {
            return false;
        }

        int locationMode;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    /**
     * Checks if device has back camera
     *
     * @param context
     *
     * @return true/false
     */
    public static boolean checkDeviceHasBackCamera(Context context)
    {
        if (checkNull(context)) {
            return false;
        }

        PackageManager pm = context.getPackageManager();

        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }

        return false;
    }

    /**
     * Checks if device has any camera
     *
     * @param context
     *
     * @return true/false
     */
    public static boolean checkDeviceHasAnyCamera(Context context)
    {
        if (checkNull(context)) {
            return false;
        }

        if (AppPropertyUtils.getDeviceSDKVersion() >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
        } else {
            return Camera.getNumberOfCameras() > 0;
        }
    }

    /**
     * Checks if device supports NFC
     *
     * @param context
     *
     * @return true/false
     */
    public static boolean checkNfcSupported(Context context)
    {
        if (checkNull(context)) {
            return false;
        }

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);

        if (nfcAdapter != null) {
            return true;
        }

        return false;
    }

    /**
     * Checks if NFC is turned on
     *
     * @param context
     *
     * @return true/false
     */
    public static boolean checkNfcEnabled(Context context)
    {
        if (checkNull(context)) {
            return false;
        }

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        boolean enabled = false;

        if (nfcAdapter != null) {
            enabled = nfcAdapter.isEnabled();
        }

        return enabled;
    }

    /**
     * Checks if pdf file can be displayed in external app
     *
     * @param context
     *
     * @return true/false
     */
    public static boolean checkCanDisplayPdf(Context context)
    {
        if (context == null) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        return (packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0);
    }

    /**
     * Checks if image can be displayed in external app
     *
     * @param context
     *
     * @return true/false
     */
    public static boolean checkCanDisplayImage(Context context)
    {
        if (checkNull(context)) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("image/*");
        return packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }


    // VALIDATION

    /**
     * Validates email address
     *
     * @param email
     *
     * @return true/false
     */
    public static boolean isEmailAddressValid(String email)
    {
        if (StringUtils.checkEmptyString(email)) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Validates web url address
     *
     * @param webUrl
     *
     * @return true/false
     */
    public static boolean isWebUrlValid(String webUrl)
    {
        if (StringUtils.checkEmptyString(webUrl)) {
            return false;
        }
        return Patterns.WEB_URL.matcher(webUrl).matches();
    }

    /**
     * Validates phone number address
     *
     * @param phoneNumber
     *
     * @return true/false
     */
    public static boolean isPhoneNumberValid(String phoneNumber)
    {
        if (StringUtils.checkEmptyString(phoneNumber)) {
            return false;
        }
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }

    /**
     * Validates domain name address
     *
     * @param domainName
     *
     * @return true/false
     */
    public static boolean isDomainNameValid(String domainName)
    {
        if (StringUtils.checkEmptyString(domainName)) {
            return false;
        }
        return Patterns.DOMAIN_NAME.matcher(domainName).matches();
    }

    /**
     * Validates ip address address
     *
     * @param ipAddress
     *
     * @return true/false
     */
    public static boolean isIpAddressValid(String ipAddress)
    {
        if (StringUtils.checkEmptyString(ipAddress)) {
            return false;
        }
        return Patterns.IP_ADDRESS.matcher(ipAddress).matches();
    }


    // OTHERS

    /**
     * Returns file prefix according to SDK version
     *
     * @return files prefix
     */
    public static String getFilesPrefix()
    {
        return AppPropertyUtils.isThisDeviceNougatAndHigher() ? "content://" : "file://";
    }


    // PRIVATE METHODS

    static boolean checkNull(Object o)
    {
        return (o == null);
    }
}