package com.honzar.adtutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.util.Patterns;

import java.io.File;

/**
 * Created by Honza Rychnovský on 19.4.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class IntentUtils {


    public static boolean openEmailInExternalApp(Context context, String emailAddress, String subject, String text, String chooserTitle)
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

    public static boolean openUrlInExternalApp(Context context, String url)
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

    public static boolean openPdfInExternalApp(Context context, String documentPath, String chooserTitle)
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
                Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(Uri.fromFile(file), "application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                Intent intent = Intent.createChooser(target, chooserTitle);
                context.startActivity(intent);
                return true;

            } catch (Exception ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean openNavigationInExternalApp(Context context, String intentString)
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

    public static boolean openPhotoExternalApp(Context context, int requestCode, boolean frontCamera, File outputFile)
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







    public static void openImageInExternalApp(Context c, String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse(url);
        intent.setDataAndType(data, "image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        if (canDisplayImage(c)) {
            try {
                c.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(c, c.getString(R.string.err_file_cannot_be_opened), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(c, c.getString(R.string.err_file_cannot_be_opened), Toast.LENGTH_SHORT).show();
        }
    }

    public static void openImageInExternalApp(Context c, Uri data)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(data, "image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        if (canDisplayImage(c)) {
            try {
                c.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(c, c.getString(R.string.err_file_cannot_be_opened), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(c, c.getString(R.string.err_file_cannot_be_opened), Toast.LENGTH_SHORT).show();
        }
    }

    public static void openImageInExternalApp(Context c, File file)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("file://" + file.getAbsolutePath());
        intent.setDataAndType(data, "image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        if (canDisplayImage(c)) {
            try {
                c.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(c, c.getString(R.string.err_file_cannot_be_opened), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(c, c.getString(R.string.err_file_cannot_be_opened), Toast.LENGTH_SHORT).show();
        }
    }
}
