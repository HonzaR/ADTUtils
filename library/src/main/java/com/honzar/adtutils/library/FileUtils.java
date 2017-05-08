package com.honzar.adtutils.library;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileUtils extends Utils {


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

    /**
     * Returns file prefix according to SDK version
     *
     * @return files prefix
     */
    public static String getFilesPrefix()
    {
        return AppPropertyUtils.isThisDeviceNougatAndHigher() ? "content://" : "file://";
    }
}
