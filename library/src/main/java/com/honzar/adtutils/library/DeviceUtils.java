package com.honzar.adtutils.library;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

/**
 * Created by Honza Rychnovský on 20.4.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class DeviceUtils extends Utils {


    // CHECK DEVICE FEATURE METHODS

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

        if (VersionUtils.getDeviceSDKVersion() >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
        } else {
            return Camera.getNumberOfCameras() > 0;
        }
    }

    /**
     * Checks if device has software navigation bar
     *
     * @param context
     *
     * @return true/false
     */
    public static boolean checkDeviceHasSoftwareNavBar(Context context)
    {
        if (checkNull(context)) {
            return false;
        }
        int resId = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (resId > 0) {
            return resId > 0 && context.getResources().getBoolean(resId);
        }
        return false;
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


    // DEVICE TYPE

    /**
     * Returns true if this device is tablet
     *
     * @return true/false
     */
    public static boolean isTablet(Context context)
    {
        if (checkNull(context)) {
            return false;
        }
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * Returns true if this device is smartphone
     *
     * @return true/false
     */
    public static boolean isSmartphone(Context context)
    {
        if (checkNull(context)) {
            return false;
        }
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) < Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
