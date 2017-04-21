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

    private static final DateTimeFormatter dateTimeFromISOStringFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
    private static final SimpleDateFormat dateTimeToISOStringFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
    private static final SimpleDateFormat dateTimeToISOStringFormatterOnlyDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final DateTimeFormatter dateTimeEnFormatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter dateTimeEnFormatterTime = DateTimeFormat.forPattern("HH:mm");
    private static final DateTimeFormatter dateTimeEnFormatterDate = DateTimeFormat.forPattern("dd/MM/yyyy");
    private static final DateTimeFormatter dateTimeCsFormatter = DateTimeFormat.forPattern("d.M.yyyy, HH:mm");
    private static final DateTimeFormatter dateTimeCsFormatterTime = DateTimeFormat.forPattern("HH:mm");
    private static final DateTimeFormatter dateTimeCsFormatterTimeLong = DateTimeFormat.forPattern("HH:mm:ss");
    private static final DateTimeFormatter dateTimeCsFormatterTimeMinutes = DateTimeFormat.forPattern("mm:ss");
    private static final DateTimeFormatter dateTimeCsFormatterDate = DateTimeFormat.forPattern("d.M.yyyy");
    private static final SimpleDateFormat dateTimeCsFormatterMonth = new SimpleDateFormat("d. MMMM", Locale.getDefault()); // napr. 10. listopadu

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

    public static boolean checkInternetConnection(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

    public static boolean canDisplayPdf(Context context)
    {
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        return (packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0);
    }

    public static boolean stringEquality(String s1, String s2) {
        if (s1 == null && s2 == null) return true;
        if (s1 == null) return false;
        if (s2 == null) return false;

        return s1.equals(s2);
    }

    public static boolean listEquals(ArrayList<?> first, ArrayList<?> second)
    {
        if (first == second) return true;
        if (first == null) return false;
        if (second == null) return false;

        if (first.size() != second.size()) {
            return false;
        }
        for (int i = 0; i < first.size(); i++) {
            if (!first.get(i).equals(second.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean integerEquality(Integer i1, Integer i2) {
        if (i1 == null && i2 == null) return true;
        if (i1 == null) return false;
        if (i2 == null) return false;

        return i1.intValue() == i2.intValue();
    }

    public static boolean longEquality(Long l1, Long l2) {
        if (l1 == null && l2 == null) return true;
        if (l1 == null) return false;
        if (l2 == null) return false;

        return l1.longValue() == l2.longValue();
    }

    public static boolean integesrEquality(Integer[] is1, Integer[] is2) {
        if (is1 == null && is2 == null) return true;
        if (is1 == null) return false;
        if (is2 == null) return false;

        return Arrays.equals(is1, is2);
    }

    public static boolean floatEquality(Float i1, Float i2) {
        if (i1 == null && i2 == null) return true;
        if (i1 == null) return false;
        if (i2 == null) return false;

        return i1.floatValue() == i2.floatValue();
    }

//    public static boolean boundsEquality(Object o1, Object o2) {
//        if (o1 == o2) return true;
//        if (o1 == null) return false;
//        if (o2 == null) return false;
//
//        LatLngBounds bounds1 = (LatLngBounds) o1;
//        LatLngBounds bounds2 = (LatLngBounds) o2;
//
//        if (bounds1.southwest.latitude != bounds2.southwest.latitude)
//            return false;
//        if (bounds1.southwest.longitude != bounds2.southwest.longitude)
//            return false;
//        if (bounds1.northeast.latitude != bounds2.northeast.latitude)
//            return false;
//        if (bounds1.northeast.longitude != bounds2.northeast.longitude)
//            return false;
//        return true;
//
//    }

    public static boolean locationEquality(Location first, Location second)
    {
        if (first == second) return true;
        if (first == null) return false;
        if (second == null) return false;

        return first.getLatitude() == second.getLatitude() && first.getLongitude() == second.getLongitude();
    }

    public static boolean currLocaleEquality(String s1, String s2)
    {
        if (s1 == null && s2 == null) return true;
        if (s1 == null) return false;
        if (s2 == null) return false;

        return s1.equals(s2);
    }

    public static int resolveUsableScreenHeight(Context context)
    {
        BaseActivityWithDrawer activity = (BaseActivityWithDrawer) context;
        final int actionBarHeight = activity.getSupportActionBar().getHeight();
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

    public static int resolveScreenWidth(Activity activity)
    {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int resolveScreenHeight(Activity activity)
    {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int resolveScreenHeightWholle(Activity activity)
    {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        final int screen = size.y;
        final int resourceNavBar = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        final int navigationBarHeight = activity.getResources().getDimensionPixelSize(resourceNavBar);

        return screen + navigationBarHeight;
    }

    public static int resolveScreenOrientation(Activity activity)
    {
        Display getOrient = activity.getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if(getOrient.getWidth() == getOrient.getHeight()){
            orientation = Configuration.ORIENTATION_SQUARE;
        } else{
            if(getOrient.getWidth() < getOrient.getHeight()){
                orientation = Configuration.ORIENTATION_PORTRAIT;
            }else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }
        return orientation;
    }

    public static void screenNotTouchable(Activity activity, boolean set) {
        if (set) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    public static int getActualDistance(Location locationA, Location locationB)
    {
        return Math.round(locationA.distanceTo(locationB));
    }

    public static String getActualDistancePrintableFormant(int distance)
    {
        float distanceF;
        if (distance > 499) {
            distanceF = (float) distance / 1000f;
            return (getDecimalFormatter().format(distanceF) + " km");
        }

        return (getDecimalFormatter().format(distance) + " m");
    }

    public static String getActualDistancePrintableFormant(double distance)
    {
        double roundOff = Math.round(distance * 100.0) / 100.0;
        return (getDecimalFormatter().format(roundOff) + " m");
    }

    public static String getWeightPrintableFormatted(int weight)
    {
        float weightF;
        if (weight > 999) {
            weightF = (float) weight / 1000f;
            return (getDecimalFormatter().format(weightF) + "kg");
        }

        return (getDecimalFormatter().format(weight) + "g");
    }

    //// DATETIME

    public static String getFormattedTimeFromMillis(long millis)
    {
        try {
            long seconds =  millis / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            seconds = seconds % 60;
            minutes = minutes % 60;

            String hours_str = (hours < 10) ? ("0" + String.valueOf(hours)) : String.valueOf(hours);
            String minutes_str = (minutes < 10) ? ("0" + String.valueOf(minutes)) : String.valueOf(minutes);
            String seconds_str = (seconds < 10) ? ("0" + String.valueOf(seconds)) : String.valueOf(seconds);

            if (hours > 0) {
                return (hours_str + ":" + minutes_str + ":" + seconds_str);
            } else {
                return (minutes_str + ":" + seconds_str);
            }
        } catch(RuntimeException rte) {
            rte.printStackTrace();
            return new DateTime(System.currentTimeMillis()).toString();
        }
    }

    public static String getFormattedTimeFromSecond(long seconds)
    {
        try {
            long minutes = seconds / 60;
            long hours = minutes / 60;
            seconds = seconds % 60;
            minutes = minutes % 60;

            String hours_str = (hours < 10) ? ("0" + String.valueOf(hours)) : String.valueOf(hours);
            String minutes_str = (minutes < 10) ? ("0" + String.valueOf(minutes)) : String.valueOf(minutes);
            String seconds_str = (seconds < 10) ? ("0" + String.valueOf(seconds)) : String.valueOf(seconds);

            if (hours > 0) {
                return (hours_str + ":" + minutes_str + ":" + seconds_str);
            } else {
                return (minutes_str + ":" + seconds_str);
            }
        } catch(RuntimeException rte) {
            rte.printStackTrace();
            return new DateTime(System.currentTimeMillis()).toString();
        }
    }

    public static DateTime decodeDateTimeFromISOString(String dateTime) {
        try {
            return DateTime.parse(dateTime, dateTimeFromISOStringFormatter);
        } catch(RuntimeException rte) {
            rte.printStackTrace();
            return new DateTime(System.currentTimeMillis());
        }
    }

    public static String decodeDateTimeToISOString(DateTime dateTime) {
        try {
            return dateTimeToISOStringFormatter.format(dateTime.toDate());
        } catch(RuntimeException rte) {
            rte.printStackTrace();
            return dateTimeToISOStringFormatter.format(new DateTime(System.currentTimeMillis()).toDate());
        }
    }

    public static String decodeDateTimeToISOStringOnlyDate(DateTime dateTime) {
        try {
            return dateTimeToISOStringFormatterOnlyDate.format(dateTime.toDate());
        } catch(RuntimeException rte) {
            rte.printStackTrace();
            return dateTimeToISOStringFormatterOnlyDate.format(new DateTime(System.currentTimeMillis()).toDate());
        }
    }

    public static String getFormattedDate(DateTime dateTime)
    {
        try {
            return dateTimeCsFormatter.print(dateTime);
        } catch(RuntimeException rte) {
            rte.printStackTrace();
            return new DateTime(System.currentTimeMillis()).toString();
        }
    }

    public static String getFormattedDateAdm(DateTime dateTime)
    {
        return dateTime.getDayOfMonth() + ". " + dateTime.getMonthOfYear() + ". "
                + dateTime.getYear() + ", " + (dateTime.getHourOfDay() < 9 ? "0" + dateTime.getHourOfDay() : dateTime.getHourOfDay()) + ":"
                + (dateTime.getMinuteOfHour() < 9 ? "0" + dateTime.getMinuteOfHour() : dateTime.getMinuteOfHour());
    }

    public static String getFormattedTwoDates(DateTime dateTimeStart, DateTime dateTimeEnd)
    {
        try {
            String start = dateTimeCsFormatter.print(dateTimeStart);
            String end = dateTimeCsFormatter.print(dateTimeEnd);
            return start + " - " + end;
        } catch(RuntimeException rte) {
            rte.printStackTrace();
            return new DateTime(System.currentTimeMillis()).toString();
        }

    }

    public static String getFormattedJustTimeMinutesAndSeconds(int seconds)
    {
        try {
            LocalTime time = new LocalTime(0, 0);    // midnight
            time = time.plusSeconds(seconds);
            return dateTimeCsFormatterTimeMinutes.print(time);
        } catch(RuntimeException rte) {
            rte.printStackTrace();
            return new DateTime(System.currentTimeMillis()).toString();
        }
    }

    public static String getFormattedJustTime(DateTime dateTime)
    {
        try {
            return dateTimeCsFormatterTime.print(dateTime);
        } catch(RuntimeException rte) {
            rte.printStackTrace();
            return new DateTime(System.currentTimeMillis()).toString();
        }
    }

    public static String getFormattedJustTimeLong(long seconds)
    {
        try {
            LocalTime time = new LocalTime(0, 0);    // midnight
            time = time.plusSeconds((int) seconds);
            return dateTimeCsFormatterTimeLong.print(time);
        } catch(RuntimeException rte) {
            rte.printStackTrace();
            return new DateTime(System.currentTimeMillis()).toString();
        }
    }

    public static String getFormattedJustDate(DateTime dateTime)
    {
        try {
            return dateTimeCsFormatterDate.print(dateTime);
        } catch(RuntimeException rte) {
            rte.printStackTrace();
            return new DateTime(System.currentTimeMillis()).toString();
        }
    }

    public static String getFormattedJustMonth(DateTime dateTime)
    {
        try {
            return dateTimeCsFormatterMonth.format(dateTime.toDate());
        } catch(RuntimeException rte) {
            rte.printStackTrace();
            return new DateTime(System.currentTimeMillis()).toString();
        }
    }

    public static String getDateDifferenceString(DateTime start, DateTime end)
    {
        if (start != null && end != null) {

            // same dates
            if (start.getYear() == end.getYear() && start.getMonthOfYear() == end.getMonthOfYear() && start.getDayOfMonth() == end.getDayOfMonth()) {
                return getFormattedJustDate(start) + " / " + getFormattedJustTime(start) + " - " + getFormattedJustTime(end);

                // different dates
            } else {
                return getFormattedTwoDates(start, end);
            }

        } else {
            return start != null ? getFormattedDate(start) : getFormattedDate(end);
        }
    }

    //

    public static float getDpForDensityFloat(int pixels, Context context)
    {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, context.getResources().getDisplayMetrics());
    }

    public static int getDpForDensityInt(int pixels, Context context)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, context.getResources().getDisplayMetrics());
    }

    public static float getDpForDensity(float pixels, Context context)
    {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, context.getResources().getDisplayMetrics());
    }

    public static float getSpForDensity(float pixels, Context context)
    {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pixels, context.getResources().getDisplayMetrics());
    }

    public static int getSpForDensityInt(int pixels, Context context)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pixels, context.getResources().getDisplayMetrics());
    }

    /**
     * Converts DP values to Pixel.
     *
     * @param dp
     * @return
     */
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static String getAndroidVersion()
    {
        return Build.VERSION.RELEASE;
    }


    public String changeAppVersionInHtml(Context context, String file) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(context.getAssets().open(file)));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String newContent = contentBuilder.toString().replace("^version_number^", pInfo.versionName);
        return newContent;
    }

    public static String getStringFromLocalHtml(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            File fl = new File(filePath);
            FileInputStream fin = new FileInputStream(fl);
            BufferedReader in = new BufferedReader(new InputStreamReader(fin));

            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }

        return contentBuilder.toString();
    }


    public static String getReadableDeviceName()
    {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return getFirstWordCapitalized(model);
        } else {
            return getFirstWordCapitalized(manufacturer) + " " + model;
        }
    }

    public static int getAppVersionBuild(Context context)
    {
        if (versionCode != Integer.MIN_VALUE)
            return versionCode;
        else {
            try {
                return versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            }
            catch (PackageManager.NameNotFoundException e) {
                return versionCode = 1;
            }
        }
    }

    public static String getAppVersionName(Context context)
    {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getAppVersionCode(Context context)
    {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        }
        catch (PackageManager.NameNotFoundException e) {
            return 1;
        }
    }

    /**
     * Funkce vrati verzi aplikace bez postfixu typu beta1, RC3 "-debug" apod. - tzn. jen napr. 2.0.0
     * @param context
     * @return
     */
    public static String getAppVersionNameWithoutPostfix(Context context) {
        try {
            String ret = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;

            int ind = ret.indexOf(' ');
            if (ind >= 0)
                ret = ret.substring(0, ind);

            ind = ret.indexOf('-');
            if (ind >= 0)
                ret = ret.substring(0, ind);

            return ret;
        }
        catch (PackageManager.NameNotFoundException e) {
            return "err";
        }
    }

    public static String getModelName() {
        try {
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            if (model.startsWith(manufacturer)) {
                return capitalize(model);
            } else {
                return capitalize(manufacturer) + " " + model;
            }
        }
        catch (Exception ex) {
            return "err";
        }
    }


    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String getFirstWordCapitalized(String s)
    {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String getCapitalized(String userName)
    {
        String withoutLeadingAndTrailing = userName.trim();

        String[] splited = getStringSplited(withoutLeadingAndTrailing);
        String firstUppers = "";

        for (int i = 0; i < splited.length; i++) {
            firstUppers += getFirstWordCapitalized(splited[i]) + " ";
        }

        return firstUppers.substring(0, firstUppers.length() - 1);
    }

    public static String[] getStringSplited(String userName)
    {
        return userName.split("\\s+");
    }

    public static String removeDiacriticalMarks(String string)
    {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static String getNameSalted(String name, String salt)
    {
        return salt + name + salt;
    }

//    public static String generatePinForName(String saltedNameString)
//    {
//        final HashCode hashCode = Hashing.sha1().hashString(saltedNameString, Charset.defaultCharset());
//        String sha1All = hashCode.toString();
//
//        String sha1JustDigits = sha1All.replaceAll("\\D+","");  // just digits
//
//        if (!(sha1JustDigits.length() < 4)) {
//            sha1JustDigits = sha1JustDigits.substring(0, 4);
//        } else {
//            int length = sha1JustDigits.length();
//            for (int i = 0; i < (4 - length); i++) {            // fill to 4 if needed
//                sha1JustDigits += "0";
//            }
//        }
//        return sha1JustDigits;
//    }
//
//    public static String generateGuidForName(String saltedNameString)
//    {
//        final HashCode hashCode = Hashing.sha1().hashString(saltedNameString, Charset.defaultCharset());
//        String sha1All = hashCode.toString();
//
//        if (sha1All.length() >= 32) {
//            String sha1_32 = sha1All.substring(0, 32);
//
//            return sha1_32.substring(0,8) + "-" + sha1_32.substring(8,12) + "-" + sha1_32.substring(12,16) + "-" + sha1_32.substring(16,20) + "-" + sha1_32.substring(20);
//        }
//
//        return null;
//    }

    public static String generateRandomGIUD()
    {
        return UUID.randomUUID().toString().toUpperCase();
    }

    public static boolean isThisDeviceMarshmallow()
    {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.M;
    }

    public static boolean isThisDeviceNougatAndHigher()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static int getDeviceSDKVersion()
    {
        return Build.VERSION.SDK_INT;
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

    public static void makeTextUnderlined(TextView column)
    {
        SpannableString content = new SpannableString(column.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        column.setText(content);
    }

    public static Drawable getDrawableTintColor(Context context, int iconResourceId, int colorResourceId)
    {
        int color = ContextCompat.getColor(context, colorResourceId);
        Drawable drawable = ContextCompat.getDrawable(context, iconResourceId);
        DrawableCompat.setTint(drawable, color);
        return drawable;
    }

    public static String getStringResourceByName(Context activity, String aString)
    {
        int resId = 0;

        try {
            String packageName = activity.getPackageName();
            resId = activity.getResources().getIdentifier(aString, "string", packageName);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        if (resId == 0) {
            return aString;
        } else {
            return activity.getResources().getString(resId);
        }
    }

    public static void showSoftKeyboard(View currentFocus)
    {
        if (currentFocus != null) {
            InputMethodManager imm = (InputMethodManager) currentFocus.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(currentFocus, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static void hideSoftKeyboard(View currentFocus)
    {
        if (currentFocus != null) {
            InputMethodManager imm = (InputMethodManager)currentFocus.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
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


    public static void enableOrDisableViewGroup(ViewGroup group, boolean state)
    {
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
    }

    public static double roundDouble(double value, int places)
    {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
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

    public static String toHex(byte[] bytes)
    {
        char[] c = new char[bytes.length * 2];

        byte b;

        for(int bx = 0, cx = 0; bx < bytes.length; ++bx, ++cx)
        {
            b = ((byte)(bytes[bx] >> 4));
            c[cx] = (char)(b > 9 ? b + 0x37 + 0x20 : b + 0x30);

            b = ((byte)(bytes[bx] & 0x0F));
            c[++cx]=(char)(b > 9 ? b + 0x37 + 0x20 : b + 0x30);
        }

        return new String(c);
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

    public static int getRandomColor(Context context, long i)
    {
        switch ((int) i) {
            case 0:
                return ContextCompat.getColor(context, R.color.colorRed);
            case 1:
                return ContextCompat.getColor(context, R.color.colorGreen);
            case 2:
                return ContextCompat.getColor(context, R.color.colorYellow);
            case 3:
                return ContextCompat.getColor(context, R.color.colorBlue);
            case 4:
                return ContextCompat.getColor(context, R.color.colorOrange);
            case 5:
                return ContextCompat.getColor(context, R.color.colorPurple);
            default:
                return ContextCompat.getColor(context, R.color.colorBlack);
        }
    }

    public static TextDrawable getColoCircle(Context context, long id, String name)
    {
        int color = Utils.getRandomColor(context, id % 6);
        return TextDrawable.builder().buildRound(name.substring(0, 1), color);
    }

    public static int kcalToKj(float kcal)
    {
        return (int) roundDouble(kcal * 4.184, 0);
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

    public static String removeLeadingAndTrailingWhiteSpaces(String string)
    {
        return string.trim();
    }

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

//    public static void showNfcNotSupportedToast(Context mContext)
//    {
//        Toast.makeText(mContext, mContext.getString(R.string.err_nfc_not_supported), Toast.LENGTH_LONG).show();
//    }

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

    public static boolean canDisplayImage(Context context)
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

    public static String generateMD5FromString(String jsonString)
    {
        byte[] thedigest;

        try {
            byte[] bytesOfMessage = jsonString.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            thedigest = md.digest(bytesOfMessage);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, thedigest);
        String hashtext = bigInt.toString(16);

        while(hashtext.length() < 32 ){
            hashtext = "0"+hashtext;
        }
        return hashtext;
        //return Base64.encodeToString(thedigest, Base64.DEFAULT);
    }

    public static boolean isTablet(Context context)
    {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static byte[] getByteArrayFromBitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static long kBToMB(long kilobytes)
    {
        return kilobytes / 1024;
    }

    public static long BToMB(long freeSpace)
    {
        return kBToMB(freeSpace) / 1024;
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

    public static String intToHex(int number)
    {
        return Integer.toString(number, 16);
    }
}