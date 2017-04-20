package com.honzar.adtutils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.util.Patterns;
import android.widget.Toast;

import java.io.File;

import javax.xml.validation.Schema;

/**
 * Created by Honza Rychnovský on 19.4.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class IntentUtils {


    //
    // OPEN EXTERNAL APP
    //

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

    public static boolean openPdfApp(Context context, String documentPath, String chooserTitle)
    {
        if (context == null || documentPath == null || documentPath.isEmpty()) {
            return false;
        }

        if (Utils.canDisplayPdf(context)) {

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

    public static boolean openGalleryPickImageApp(Context context, String chooserTitle, int resultCode)
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

    public static boolean openGalleryApp(Context context, Uri uri)
    {
        if (context == null || uri == null || uri.toString().isEmpty()) {
            return false;
        }

        if (Utils.canDisplayImage(context)) {

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

    public static boolean openGalleryApp(Context context, String url)
    {
        if (context == null || url == null || url.isEmpty()) {
            return false;
        }

        return openGalleryApp(context, Uri.parse(url));
    }

    public static boolean openImageInExternalApp(Context context, File file)
    {
        if (context == null || file == null) {
            return false;
        }

        if (!file.canRead() || !file.exists() || !file.isFile()) {
            return false;
        }

        return openGalleryApp(context, Uri.parse("file://" + file.getAbsolutePath()));
    }



    //
    // TURN DEVICE ACCESSORIES ON
    //

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

    public static boolean turnBluetoothOn(Context context, int requestCode)
    {
        if (context == null) {
            return false;
        }

        if (Utils.checkBluetoothEnabled()) {
            return true;
        }

        try {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            ((Activity)context).startActivityForResult(enableBtIntent, requestCode);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
