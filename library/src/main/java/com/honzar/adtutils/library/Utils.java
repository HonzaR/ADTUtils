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
 * Created by Honza Rychnovský on 1.1.2016.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class Utils {

    private static final int MISSING_PLAY_SERVICES_DIALOG = 1204;
    private static int versionCode = Integer.MIN_VALUE;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static Gson gson;
    public static DecimalFormat decimalFormat;

    public static Gson getGson()
    {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static DecimalFormat getDecimalFormatter()
    {
        if (decimalFormat == null) {
            decimalFormat = new DecimalFormat("#.#");
        }
        return decimalFormat;
    }





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


    public static String[] getListOfAssetFiles(Context context, String path)
    {
        String[] list;
        try {
            list = context.getAssets().list(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        for (int i = 0; i < list.length; i++) {
            list[i] = path + "/" + list[i];
        }

        return list;
    }

    public static ArrayList<File> getFilesFromDirectoryRecursively(ArrayList<File> list, File dir)
    {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; ++i) {
                File file = files[i];
                if (file.isDirectory()) {
                    getFilesFromDirectoryRecursively(list, file);
                } else {
                    list.add(file);
                }
            }
        }
        return list;
    }

    public static boolean compressFilesToZip(ArrayList<File> files, String zipFileName) {

        int BUFFER = 2048;

        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(zipFileName);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            byte data[] = new byte[BUFFER];

            for (int i = 0; i < files.size(); i++) {
                //Log.v("Compress", "Adding: " + files[i]);
                FileInputStream fi = new FileInputStream(files.get(i));
                origin = new BufferedInputStream(fi, BUFFER);

                ZipEntry entry = new ZipEntry(files.get(i).getName());
                out.putNextEntry(entry);
                int count;

                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }
            out.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    public static boolean extractArchiveFile(Context c, String path, String zipFileName, String password)
//    {
//        try {
//            if (!Utils.copyFileOrDirFromAssests(c, path))
//                return false;
//
//            File file = new File(c.getExternalFilesDir(null) + "/" + path + "/" + zipFileName);
//            String destination = c.getExternalFilesDir(null) + "/" + path;
//
//            ZipFile zipFile = new ZipFile(file);
//            if (zipFile.isEncrypted()) {
//                zipFile.setPassword(password);
//            }
//            zipFile.extractAll(destination);
//            return true;
//
//        } catch (ZipException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    static public boolean copyFileOrDirFromAssests(Context c, String path)
    {
        AssetManager assetManager = c.getAssets();
        String assets[] = null;

        try {
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(c, path);
            } else {
                String fullPath = c.getExternalFilesDir(null) + "/" + path;
                File dir = new File(fullPath);
                if (!dir.exists())
                    dir.mkdir();
                for (int i = 0; i < assets.length; ++i) {
                    copyFileOrDirFromAssests(c, path + "/" + assets[i]);
                }
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    static public void copyFile(Context c, String filename)
    {
        AssetManager assetManager = c.getAssets();
        InputStream in = null;
        OutputStream out = null;

        try {
            in = assetManager.open(filename);
            String newFileName = c.getExternalFilesDir(null) + "/" + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public boolean writeFile(String path, String filename, String content)
    {
        try {
            FileWriter file = new FileWriter(path + filename);
            file.write(content);
            file.flush();
            file.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    static public File openFileIfCan(Context context, String path, String filename)
    {
        try {
            return new File(context.getExternalFilesDir(null) + path, filename);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static public boolean checkFileExists(String path, String filename)
    {
        try {
            File file = new File(path, filename);

            if (file.exists())
                return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    static public File renameDirectory(File originalFile)
    {
        originalFile.renameTo(new File(originalFile.getPath() + "_renamed/"));
        return new File(originalFile.getPath() + "_renamed/");
    }

    static public boolean deleteFileOrDirectoryIfCan(File fileOrDirectory)
    {
        try {
            if (fileOrDirectory.isDirectory()) {
                for (File child : fileOrDirectory.listFiles()) {
                    deleteFileOrDirectoryIfCan(child);
                }
            }
            fileOrDirectory.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static File createImageFile(Context c, String fileName, String filePath)
    {
        if (!createDirectory(c.getExternalFilesDir(null) + "/" + filePath)) {   // already exists
            deleteFileOrDirectoryIfCan(renameDirectory(new File(c.getExternalFilesDir(null) + "/" + filePath)));
            createDirectory(c.getExternalFilesDir(null) + "/" + filePath);
        }

        File storage = new File(c.getExternalFilesDir(null) + "/" + filePath);

        try {
            File image = File.createTempFile(
                    fileName,           // prefix
                    ".jpg",             // suffix
                    storage             // directory
            );
            return image;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean createDirectory(String path)
    {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
            return true;
        }
        return false;
    }

    private static File renameDirectory(File originalFile)
    {
        originalFile.renameTo(new File(originalFile.getPath() + "_renamed/"));
        return new File(originalFile.getPath() + "_renamed/");
    }

    private static boolean deleteFileOrDirectoryIfCan(File fileOrDirectory)
    {
        try {
            if (fileOrDirectory.isDirectory()) {
                for (File child : fileOrDirectory.listFiles()) {
                    deleteFileOrDirectoryIfCan(child);
                }
            }
            fileOrDirectory.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean createEmptyFile(String filePath, String fileName)
    {
        try {
            File file = new File(filePath, fileName);
            file.createNewFile();
            if (file.exists())
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createDirectory(String path)
    {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
            return true;
        }
        return false;
    }



    public static File getAppDataExternalDirectory(Context c, String directory) {

        File dir = new File(c.getExternalFilesDir(null), directory);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    public static File getDexCacheDirectory(Context context)
    {
        return context.getDir("dex-cache", Context.MODE_PRIVATE);
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









//    public static boolean checkGooglePlayServicesAvailability(Context context)
//    {
//        try {
//            GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
//            int result = googleAPI.isGooglePlayServicesAvailable(context);
//
//            if(result != ConnectionResult.SUCCESS) {
//
//                if(googleAPI.isUserResolvableError(result)) {
//                    googleAPI.getErrorDialog((Activity) context, result, MISSING_PLAY_SERVICES_DIALOG).show();
//                }
//                return false;
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }



    public static boolean getExternalStorageAvailability()
    {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static long getExternalStorageAvailableSpace()
    {
        long availableSpace = -1L;

        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            stat.restat(Environment.getExternalStorageDirectory().getPath());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                availableSpace = stat.getAvailableBlocksLong() * stat.getBlockSizeLong();
            } else {
                availableSpace = stat.getAvailableBlocks() * stat.getBlockSize();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return availableSpace;
    }

    public static boolean loadAndScaleDownAndSaveImage(String localPhotoUrl, int maxSideSize)
    {
        boolean result = false;
        Bitmap photo;
        try {
            photo = BitmapFactory.decodeFile(localPhotoUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (photo != null) {
            ExifInterface exifData;
            String exifOrientation = null;
            try {
                exifData = new ExifInterface(localPhotoUrl);                            // persist exif data
                exifOrientation = exifData.getAttribute(ExifInterface.TAG_ORIENTATION);
            } catch (IOException e) {
                e.printStackTrace();
            }

            float photoHeight = photo.getHeight();                                      // scale image proportionally to chosen max side size
            float photoWidth = photo.getWidth();
            try {
                if (photoHeight > photoWidth) {     // portrait
                    photo = Bitmap.createScaledBitmap(photo, (int)(maxSideSize * (photoWidth / photoHeight)), maxSideSize, false);
                } else {                            // landscape
                    photo = Bitmap.createScaledBitmap(photo, maxSideSize, (int)(maxSideSize * (photoHeight / photoWidth)), false);
                }
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                return false;
            }

            deleteFileOrDirectoryIfCan(Utils.renameDirectory(new File(localPhotoUrl))); // delete original
            File f = new File(localPhotoUrl);

            if (exifOrientation != null && !exifOrientation.isEmpty()) {                // rotate with bitmap according to exif
                Matrix matrix = new Matrix();
                int rotation = Integer.parseInt(exifOrientation);
                if (rotation == ExifInterface.ORIENTATION_NORMAL || rotation == ExifInterface.ORIENTATION_UNDEFINED) {
                    matrix.postRotate(0);
                } else if (rotation == ExifInterface.ORIENTATION_FLIP_HORIZONTAL || rotation == ExifInterface.ORIENTATION_FLIP_VERTICAL || rotation == ExifInterface.ORIENTATION_ROTATE_180) {
                    matrix.postRotate(180);
                } else if (rotation == ExifInterface.ORIENTATION_ROTATE_90) {
                    matrix.postRotate(90);
                } else if (rotation == ExifInterface.ORIENTATION_ROTATE_270) {
                    matrix.postRotate(270);
                }
                photo = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
            }

            try {
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.JPEG, 65, fo);
                fo.flush();
                fo.close();
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static boolean preserveImageExifFile(String oldImagePath, String newImagePath)
    {
        ExifInterface oldExif = null;
        try {
            oldExif = new ExifInterface(oldImagePath);
            String exifOrientation = oldExif.getAttribute(ExifInterface.TAG_ORIENTATION);

            if (exifOrientation != null) {
                ExifInterface newExif = new ExifInterface(newImagePath);
                newExif.setAttribute(ExifInterface.TAG_ORIENTATION, exifOrientation);
                newExif.saveAttributes();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //// OPEN IMAGE



    ////

    public static File[] getAllFilesOrDirectoriesFromDirectory(File dir)
    {
        File[] files = null;
        if (dir.exists()) {
            files = dir.listFiles();
        }
        return files;
    }

    public static ArrayList<File> getAllFilesFromDirectory(File parentDir)
    {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getAllFilesFromDirectory(file));
            } else {
                inFiles.add(file);
            }
        }
        return inFiles;
    }

    public static String getBase64FromFile(File file)
    {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static boolean validateEmailAddress(String emailStr)
    {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public boolean saveBitmapToFile(File dir, String fileName, Bitmap bm, Bitmap.CompressFormat format, int quality)
    {
        File imageFile = new File(dir,fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);

            bm.compress(format,quality,fos);

            fos.close();

            return true;
        }
        catch (IOException e) {

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return false;
    }

    public static String convertStreamToString(InputStream is)
    {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            reader.close();
            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringFromFile (String filePath)
    {
        try {
            File fl = new File(filePath);
            FileInputStream fin = new FileInputStream(fl);

            String ret = convertStreamToString(fin);

            fin.close();
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringFromAsset(Context context, String fileName)
    {
        String jsonString;

        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return jsonString;
    }

    public static byte[] getByteArrayFromBitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static String getFilesPrefix()
    {
        return isThisDeviceNougatAndHigher() ? "content://" : "file://";
    }

    public static void addPhotoToGallery(Context context, String photoUrl)
    {
        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, photoUrl);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photoUrl);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public static File getAlbumStorageDir(String albumName)
    {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("Util Error", "Directory not created");
        }
        return file;
    }

    // PRIVATE METHODS

    static boolean checkNull(Object o)
    {
        return (o == null);
    }
}