package com.honzar.adtutils.library;

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

    public static boolean checkWifiEnabled(Context context)
    {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }

    public static boolean checkInternetConnection(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean checkMobileDataConnectionEnabled(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() && (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public static boolean checkBluetoothEnabled()
    {
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (bluetoothAdapter != null && bluetoothAdapter.isEnabled());
    }

    public static boolean checkLocationEnabled(Context context)
    {
        int locationMode;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public static boolean checkDeviceHasBackCamera(Context context)
    {
        PackageManager pm = context.getPackageManager();

        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }

        return false;
    }

    public static boolean checkDeviceHasAnyCamera(Context context)
    {
        if (Build.VERSION.SDK_INT < 21) {
            return Camera.getNumberOfCameras() > 0;
        } else {
            return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
        }
    }

    public static boolean checkNfcSupported(Context context)
    {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);

        if (nfcAdapter != null) {
            return true;
        }

        return false;
    }

    public static boolean checkNfcEnabled(Context context)
    {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        boolean enabled = false;

        if (nfcAdapter != null) {
            enabled = nfcAdapter.isEnabled();
        }

        return enabled;
    }

    public static boolean checkCanDisplayPdf(Context context)
    {
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        return (packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0);
    }

    public static boolean checkCanDisplayImage(Context context)
    {
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("image/*");
        if (packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    // VALIDATION

    public static boolean isEmailAddressValid(String email)
    {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isWebUrlValid(String webUrl)
    {
        return Patterns.WEB_URL.matcher(webUrl).matches();
    }

    public static boolean isPhoneNumberValid(String phoneNumber)
    {
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }

    public static boolean isDomainNameValid(String domainName)
    {
        return Patterns.DOMAIN_NAME.matcher(domainName).matches();
    }

    public static boolean isIpAddressValid(String ipAddress)
    {
        return Patterns.IP_ADDRESS.matcher(ipAddress).matches();
    }



    //

    public static String getFilesPrefix()
    {
        return isThisDeviceNougatAndHigher() ? "content://" : "file://";
    }


    public static boolean resolveFahrenheiteOrCelsia(Context context)
    {
        String localeCode = context.getResources().getConfiguration().locale.getISO3Country();
        boolean isInFahrenheite =   localeCode.equals("USA") ||    // USA
                localeCode.equals("BHS") ||    // Bahamas
                localeCode.equals("BLZ") ||    // Belize
                localeCode.equals("CYM") ||    // Cayman Islands
                localeCode.equals("PLW");      // Palau
        return isInFahrenheite;
    }





    // PRIVATE METHODS

    static boolean checkNull(Object o)
    {
        return (o == null);
    }
}