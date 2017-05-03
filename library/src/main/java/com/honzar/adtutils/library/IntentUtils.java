package com.honzar.adtutils.library;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Patterns;

import java.io.File;

/**
 * Created by Honza Rychnovský on 19.4.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class IntentUtils {


    //
    // OPEN EXTERNAL APP
    //

    /**
     * Opens email app or client and fills parameters
     *
     * @param context
     * @param emailAddress
     * @param subject
     * @param text
     * @param chooserTitle
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openEmailApp(Context context, String emailAddress, String subject, String text, String chooserTitle)
    {
        if (context == null || emailAddress == null || emailAddress.isEmpty()) {
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            return false;
        }

        try {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailAddress, null));

            if (subject != null && !subject.isEmpty())
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);

            if (text != null && !text.isEmpty())
                emailIntent.putExtra(Intent.EXTRA_TEXT, text);

            Intent i = Intent.createChooser(emailIntent, chooserTitle);
            context.startActivity(i);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Opens internet browser app with url
     *
     * @param context
     * @param url
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openBrowserApp(Context context, String url)
    {
        if (context == null || url == null || !url.isEmpty()) {
            return false;
        }

        if (!Patterns.WEB_URL.matcher(url).matches()) {
            return false;
        }

        try {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(i);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Opens PDF browser with document
     *
     * @param context
     * @param documentPath
     * @param chooserTitle
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openPdfApp(Context context, String documentPath, String chooserTitle)
    {
        if (context == null || documentPath == null || documentPath.isEmpty()) {
            return false;
        }

        if (Utils.checkCanDisplayPdf(context)) {

            File file = new File(documentPath);
            if (!file.isAbsolute()) {
                file = new File(context.getExternalFilesDir(null) + "/" + documentPath);
            }

            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                intent = Intent.createChooser(intent, chooserTitle);
                context.startActivity(intent);
                return true;

            } catch (Exception ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Opens navigation/map app with predefined location and pin title
     *
     * @param context
     * @param intentString
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openNavigationApp(Context context, String intentString)
    {
        if (context == null || intentString == null || intentString.isEmpty()) {
            return false;
        }

        try {
            Uri gmmIntentUri = Uri.parse(intentString);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            context.startActivity(mapIntent);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Opens camera app and returns taken photo file in onActivityResult method
     *
     * @param context
     * @param requestCode
     * @param frontCamera
     * @param outputFile
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openCameraApp(Context context, int requestCode, boolean frontCamera, File outputFile)
    {
        if (context == null || outputFile == null) {
            return false;
        }

        if (!outputFile.isFile() || outputFile.isDirectory() || !outputFile.exists() || !outputFile.canWrite()) {
            return false;
        }

        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (frontCamera) {  // try to open front camera

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    takePictureIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
                } else {
                    takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                }
            }

            if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {

                if (Utils.isThisDeviceNougatAndHigher()) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", outputFile));
                } else {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
                }
                ((Activity)context).startActivityForResult(takePictureIntent, requestCode);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * Opens calendar app and add new event
     *
     * @param context
     * @param startsAt
     * @param endsAt
     * @param title
     * @param description
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openCalendarApp(Context context, long startsAt, long endsAt, String title, String description)
    {
        if (context == null) {
            return false;
        }

        if (startsAt < 0 && endsAt < 0) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startsAt)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endsAt)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        if (title != null)
            intent.putExtra(CalendarContract.Events.TITLE, title);
        if (description != null)
            intent.putExtra(CalendarContract.Events.DESCRIPTION, description);

        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Opens phone call app with predefined phone number
     *
     * @param context
     * @param phoneNumber
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openPhoneCallApp(Context context, String phoneNumber)
    {
        if (context == null || phoneNumber == null || phoneNumber.isEmpty()) {
            return  false;
        }

        if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            return false;
        }

        try {
            final Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_DIAL);
            sendIntent.setData(Uri.fromParts("tel", phoneNumber, null));
            context.startActivity(sendIntent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Opens image picker app and returns taken photo file in onActivityResult method
     *
     * @param context
     * @param chooserTitle
     * @param resultCode
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openimagePickApp(Context context, String chooserTitle, int resultCode)
    {
        if (context == null) {
            return false;
        }

        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent = Intent.createChooser(intent, chooserTitle);
            ((Activity) context).startActivityForResult(intent, resultCode);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Opens image in gallery app
     *
     * @param context
     * @param uri
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openImageInGalleryApp(Context context, Uri uri)
    {
        if (context == null || uri == null || uri.toString().isEmpty()) {
            return false;
        }

        if (Utils.checkCanDisplayImage(context)) {

            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                context.startActivity(intent);
                return true;

            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Opens image in gallery app
     *
     * @param context
     * @param url
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openImageInGalleryApp(Context context, String url)
    {
        if (context == null || url == null || url.isEmpty()) {
            return false;
        }

        return openImageInGalleryApp(context, Uri.parse(url));
    }

    /**
     * Opens image in gallery app
     *
     * @param context
     * @param file
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openImageInGalleryApp(Context context, File file)
    {
        if (context == null || file == null) {
            return false;
        }

        if (!file.canRead() || !file.exists() || !file.isFile()) {
            return false;
        }

        return openImageInGalleryApp(context, Uri.parse("file://" + file.getAbsolutePath()));
    }



    //
    // TURN DEVICE ACCESSORY ON
    //

    /**
     * Turns NFC accessory on by opening NFC system settings
     *
     * @param context
     *
     * @return true if succeed, false otherwise
     */
    public static boolean turnNfcOn(Context context)
    {
        if (context == null) {
            return false;
        }

        if (!Utils.checkNfcSupported(context)) {
            return false;
        }

        if (!Utils.checkNfcEnabled(context)) {
            return false;
        }

        try {
            final Intent intent = new Intent();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                if (NfcAdapter.getDefaultAdapter(context).isEnabled()) {
                    intent.setAction(Settings.ACTION_NFCSHARING_SETTINGS);
                } else {
                    intent.setAction(Settings.ACTION_NFC_SETTINGS);
                }
            } else {
                intent.setAction(Settings.ACTION_NFCSHARING_SETTINGS);
            }
            context.startActivity(intent);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Turns Bluetooth accessory on and returns result in onActivityResult method
     *
     * @param context
     * @param requestCode
     *
     * @return true if succeed, false otherwise
     */
    public static boolean turnBluetoothOn(Context context, int requestCode)
    {
        if (context == null) {
            return false;
        }

        if (Utils.checkBluetoothEnabled()) {
            return true;
        }

        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

        try {
            ((Activity)context).startActivityForResult(enableBtIntent, requestCode);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Turns WIFI accessory on
     *
     * @param context
     *
     * @return true if succeed, false otherwise
     */
    public static boolean turnWifiConnectionOn(Context context)
    {
        if (context == null) {
            return false;
        }

        if (Utils.checkWifiEnabled(context)) {
            return true;
        }

        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifi == null) {
            return false;
        }

        if ((ContextCompat.checkSelfPermission(context, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)) {
            return false;
        }

        return wifi.setWifiEnabled(true);
    }

    /**
     * Turns mobile data accessory on by opening system settings
     *
     * @param context
     *
     * @return true if succeed, false otherwise
     */
    public static boolean turnMobileDataConnectionOn(Context context)
    {
        if (context == null) {
            return false;
        }

        if (Utils.checkMobileDataConnectionEnabled(context)) {
            return true;
        }

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");

        try {
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    /**
     * Turns GPS accessory on by opening system settings
     *
     * @param context
     *
     * @return true if succeed, false otherwise
     */
    public static boolean turnGpsOn(Context context)
    {
        if (context == null) {
            return false;
        }

        if (Utils.checkLocationEnabled(context)) {
            return true;
        }

        Intent i = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);

        try {
            context.startActivity(i);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }
}
