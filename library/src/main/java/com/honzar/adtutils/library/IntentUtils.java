package com.honzar.adtutils.library;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.util.Patterns;

import java.io.File;

/**
 * Created by Honza Rychnovský on 19.4.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class IntentUtils extends Utils {


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
        if (context == null || url == null || url.isEmpty()) {
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

        if (DeviceUtils.checkCanDisplayPdf(context)) {

            File file = new File(documentPath);
            if (!file.isAbsolute()) {
                file = new File(context.getExternalFilesDir(null) + "/" + documentPath);
            }

            Uri fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);

            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(fileUri, "application/pdf");
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
     * Opens camera app for photo and returns taken photo file in onActivityResult method
     *
     * @param context
     * @param requestCode
     * @param frontCamera
     * @param outputFile
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openCameraAppForPhoto(Context context, int requestCode, boolean frontCamera, File outputFile, boolean addToGallery)
    {
        return openCameraAppForPhoto(context, null, requestCode, frontCamera, outputFile, addToGallery);
    }

    /**
     * Opens camera app for photo and returns taken photo file in fragment onActivityResult method
     *
     * @param context
     * @param fragment
     * @param requestCode
     * @param frontCamera
     * @param outputFile
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openCameraAppForPhoto(Context context, Fragment fragment, int requestCode, boolean frontCamera, File outputFile, boolean addToGallery) {
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

                if (addToGallery) {
                    takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, addFileToGallery(context, outputFile, "image/*"));
                } else {
                    if (VersionUtils.isThisDeviceNougatAndHigher()) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", outputFile));
                    } else {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
                    }
                }

                if (fragment != null) {
                    fragment.startActivityForResult(takePictureIntent, requestCode);
                } else {
                    ((Activity)context).startActivityForResult(takePictureIntent, requestCode);
                }

                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * Opens camera app for video and returns taken video file in onActivityResult method
     *
     * @param context
     * @param requestCode
     * @param frontCamera
     * @param outputFile
     * @param addToGallery
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openCameraAppForVideo(Context context, int requestCode, boolean frontCamera, File outputFile, boolean addToGallery)
    {
        return openCameraAppForVideo(context, null, requestCode, frontCamera, outputFile, addToGallery);
    }

    /**
     * Opens camera app for video and returns taken video file in fragment onActivityResult method
     *
     * @param context
     * @param fragment
     * @param requestCode
     * @param frontCamera
     * @param outputFile
     * @param addToGallery
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openCameraAppForVideo(Context context, Fragment fragment, int requestCode, boolean frontCamera, File outputFile, boolean addToGallery)
    {
        if (context == null || outputFile == null) {
            return false;
        }

        if (!outputFile.isFile() || outputFile.isDirectory() || !outputFile.exists() || !outputFile.canWrite()) {
            return false;
        }

        try {
            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

            if (frontCamera) {  // try to open front camera

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    takeVideoIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
                } else {
                    takeVideoIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                }
            }

            if (takeVideoIntent.resolveActivity(context.getPackageManager()) != null) {

                if (addToGallery) {
                    takeVideoIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, addFileToGallery(context, outputFile, "video/*"));
                } else {
                    if (VersionUtils.isThisDeviceNougatAndHigher()) {
                        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", outputFile));
                    } else {
                        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
                    }
                }

                if (fragment != null) {
                    fragment.startActivityForResult(takeVideoIntent, requestCode);
                } else {
                    ((Activity)context).startActivityForResult(takeVideoIntent, requestCode);
                }

                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private static Uri addFileToGallery(Context context, File file, String mimeType)
    {
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Video.Media.TITLE, file.getName());
        values.put(MediaStore.Video.Media.MIME_TYPE, mimeType);
        values.put(MediaStore.Video.Media.DATA, file.getAbsolutePath());
        return context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
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
     * Opens phone call app with predefined phone number on dial screen
     *
     * @param context
     * @param phoneNumber
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openPhoneDialApp(Context context, String phoneNumber)
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
     * Opens phone call app with predefined phone number on dial screen
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

        if ( ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        try {
            final Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_CALL);
            sendIntent.setData(Uri.fromParts("tel", phoneNumber, null));
            context.startActivity(sendIntent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Opens file picker app and returns chosen file in onActivityResult method
     *
     * @param context
     * @param fileType
     * @param chooserTitle
     * @param resultCode
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openFilePickerComponent(Context context, String fileType, String chooserTitle, int resultCode)
    {
        return openFilePickerComponent(context, null, fileType, chooserTitle, resultCode);
    }

    /**
     * Opens file picker app and returns chosen file in fragment onActivityResult method
     *
     * @param context
     * @param fileType
     * @param chooserTitle
     * @param resultCode
     *
     * @return true if succeed, false otherwise
     */
    public static boolean openFilePickerComponent(Context context, Fragment fragment, String fileType, String chooserTitle, int resultCode)
    {
        if (context == null || fileType == null) {
            return false;
        }

        try {
            Intent intent = new Intent();
            intent.setType(fileType);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent = Intent.createChooser(intent, chooserTitle);

            if (fragment != null) {
                fragment.startActivityForResult(intent, resultCode);
            } else {
                ((Activity) context).startActivityForResult(intent, resultCode);
            }

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

        if (DeviceUtils.checkCanDisplayImage(context)) {

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

    /**
     * Opens Google Play Store page in Play Store app if installed or in browser.
     *
     * @param context
     * @param packageName package name of app developer wants to open on Play Store
     *
     * @return true/false
     */
    public static boolean openAppPlayStore(Context context, String packageName)
    {
        if (checkNull(context) || checkNull(packageName) || packageName.isEmpty())
            return false;

        Uri rateUri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, rateUri);

        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            Uri storeUri = Uri.parse("http://play.google.com/store/apps/details?id=" + packageName);
            context.startActivity(new Intent(Intent.ACTION_VIEW, storeUri));
        }
        return true;
    }

    /**
     * Opens Google Play Store on Subscription page in Play Store app if installed or in browser.
     *
     * @param context
     *
     * @return true/false
     */
    public static boolean openAppPlayStoreSubscriptions(Context context)
    {
        if (checkNull(context))
            return false;

        Uri rateUri = Uri.parse("https://play.google.com/store/account/subscriptions");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, rateUri);

        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            Uri storeUri = Uri.parse("https://play.google.com/store/account/subscriptions");
            context.startActivity(new Intent(Intent.ACTION_VIEW, storeUri));
        }
        return true;
    }

    /**
     * Opens chooser window to share app details by other app.
     *
     * @param context
     *
     * @return true/false
     */
    public static boolean shareApp(Context context, String chooserText, String subject, String body, String link)
    {
        if (checkNull(context))
            return false;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBodyText = body + "\n\n" + link;

        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);

        try {
            context.startActivity(Intent.createChooser(shareIntent, chooserText));
        } catch (Exception e) {
            return false;
        }

        return true;
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

        if (!DeviceUtils.checkNfcSupported(context)) {
            return false;
        }

        if (!DeviceUtils.checkNfcEnabled(context)) {
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
        return turnBluetoothOn(context, null, requestCode);
    }

    /**
     * Turns Bluetooth accessory on and returns result in fragment onActivityResult method
     *
     * @param context
     * @param fragment
     * @param requestCode
     *
     * @return true if succeed, false otherwise
     */
    public static boolean turnBluetoothOn(Context context, Fragment fragment, int requestCode)
    {
        if (context == null) {
            return false;
        }

        if (DeviceUtils.checkBluetoothEnabled(context)) {
            return true;
        }

        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

        try {
            if (fragment != null) {
                fragment.startActivityForResult(enableBtIntent, requestCode);
            } else {
                ((Activity)context).startActivityForResult(enableBtIntent, requestCode);
            }

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
    @SuppressLint("MissingPermission")
    public static boolean turnWifiConnectionOn(Context context)
    {
        if (context == null) {
            return false;
        }

        if (DeviceUtils.checkWifiEnabled(context)) {
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

        if (DeviceUtils.checkMobileDataConnectionEnabled(context)) {
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

        if (DeviceUtils.checkLocationEnabled(context)) {
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

    // YOUTUBE

    public static void openYoutubeVideo(Context context, String id)
    {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    public static void openYoutubeChannel(Context context, String channelId)
    {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube.com/channel/" + channelId));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/channel/" + channelId));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    public static void openYoutubeUser(Context context, String userId)
    {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube.com/user/" + userId));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/user/" + userId));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    // FACEBOOK

    /**
     * Opens native facebook app, if installed, webpage otherwise.
     *
     * Usages: "fb://page/" - does not work with newer versions of the FB app.
     *         "fb://facewebmodal/f?href=" - for newer versions.
     *
     * @param context
     * @param pageId
     */
    public static void openFacebookPage(Context context, String pageId)
    {
        String facebookWebPageUrl = "https://www.facebook.com/" + pageId;
        Intent facebookIntent;

        if (!checkAppInstalled(context, "com.facebook.katana")) {
            facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookWebPageUrl));

        } else {
            PackageManager packageManager = context.getPackageManager();

            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo("com.facebook.katana", 0);

                //newer versions of fb app
                if (applicationInfo.enabled) {
                    facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + facebookWebPageUrl));
                    //older versions of fb app
                } else {
                    facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + pageId));
                }

            } catch (PackageManager.NameNotFoundException e) {
                facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookWebPageUrl));
            }
        }
        context.startActivity(facebookIntent);
    }

    // TWITTER

    public static void openTwitterPage(Context context, String pageOrUserId)
    {
        String twitterWebPageUrl = "https://twitter.com/" + pageOrUserId;
        Intent appIntent;

        if (!checkAppInstalled(context, "com.twitter.android")) {
            appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterWebPageUrl));

        } else {
            try {
                context.getPackageManager().getPackageInfo("com.twitter.android", 0);
                appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + pageOrUserId));
                appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } catch (PackageManager.NameNotFoundException e) {
                appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterWebPageUrl));
            }
        }
        context.startActivity(appIntent);
    }

    // INSTAGRAM

    public static void openInstagramUser(Context context, String userId)
    {
        String instagramWebPageUrl = "https://instagram.com/" + userId;
        Intent appIntent;

        if (!checkAppInstalled(context, "com.instagram.android")) {
            appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramWebPageUrl));

        } else {
            try {
                context.getPackageManager().getPackageInfo("com.instagram.android", 0);
                appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/" + userId));
                appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } catch (PackageManager.NameNotFoundException e) {
                appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramWebPageUrl));
            }
        }
        context.startActivity(appIntent);
    }

    public static boolean checkAppInstalled(Context context, String uri)
    {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch(PackageManager.NameNotFoundException e) {
            Log.e("Facebook", "IntentUtils - appInstalled() - not found!");
        }
        return false;
    }

    public static void openAppSystemSettingsDetailScreen(Activity activity)
    {
        if (activity == null)
            return;

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

        activity.startActivity(intent);
    }
}
