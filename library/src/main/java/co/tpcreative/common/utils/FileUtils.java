package co.tpcreative.common.utils;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dimorinny on 24.10.15.
 */
public class FileUtils {

    public static final String TAG = FileUtils.class.getSimpleName();
    private static final String ENCODING = "windows-1252";
    private static FileUtils instance ;

    public static FileUtils getInstance(){
        if (instance==null){
           synchronized (FileUtils.class){
               if (instance==null){
                   instance = new FileUtils();
               }
           }
        }
        return instance;
    }


    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void changeColorStatusBar(Activity activity, int color, boolean on) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = activity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(color);
    }

    public static String getYearCard(int year) {
        int last = year % 100;
        if (last < 10)
            return String.format("0%d", last);
        else
            return String.valueOf(last);
    }


    public static float scalePixel(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / 320;
    }

    public static float scaleDensity(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / displayMetrics.density / 320;
    }

    public static int pxToSp(final Context context, final float px) {
        return Math.round(px / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int spToPx(final Context context, final float sp) {
        return Math.round(sp * context.getResources().getDisplayMetrics().scaledDensity);
    }


    public static boolean validateEmail(String data) {
        Pattern emailPattern = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}");
        Matcher emailMatcher = emailPattern.matcher(data);
        return emailMatcher.matches();
    }

    public static boolean validateUsername(String data) {

        Pattern emailPattern = Pattern.compile("(?=^.{3,20}$)^[a-zA-Z][a-zA-Z0-9]*[._-]?[a-zA-Z0-9]+$");
        Matcher emailMatcher = emailPattern.matcher(data);
        return emailMatcher.matches();
    }

    public static long getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        String datetime = dateformat.format(c.getTime());
        return (c.getTimeInMillis() / 1000);
    }

    /* Convert String to Currency */

    public static HashMap hashToCurrency(String s) {
        try {
            HashMap<String, String> hash = new HashMap<>();
            Locale dutch = new Locale("en", "AU");
            NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(dutch);
            String c = numberFormatDutch.format(new BigDecimal(s.replace(",","")));
            hash.put("currency", replaceString(c));
//            System.out.println("Currency Format: " + c);
            Number d = numberFormatDutch.parse(c);
            BigDecimal bd = new BigDecimal(d.toString());
            hash.put("number", bd.toString());
            return hash;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String stringToDouble(String s) {
        if (!s.isEmpty()) {
            Log.d("action", "Show value : " + s);
            Locale dutch = new Locale("en", "AU");
            NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(dutch);
            try {
                Number d = numberFormatDutch.parse(s);
                BigDecimal bd = new BigDecimal(d.toString());
                Log.d("action", bd.toString());
                return bd.toString();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }


    public static String replaceString(String s) {
        try {
            return s.replace("$", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public void convertToCurrency() {
        String s = "100000";
        Locale dutch = new Locale("nl", "NL");
        NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(dutch);

        String c = numberFormatDutch.format(new BigDecimal(s.toString()));
        System.out.println("Currency Format: " + c);
        try {
            Number d = numberFormatDutch.parse(c);
            BigDecimal bd = new BigDecimal(d.toString());
            System.out.println(bd);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static boolean onSaveFileToStore(String path_folder_name, String fileName, String responseJson, boolean append, int separateLines) {
        final String newLine = System.getProperty("line.separator");
        try{
            new File(path_folder_name).mkdirs();
            File root = new File(path_folder_name+ "/" + fileName);
            if (!root.exists()){
                root.createNewFile();
            }
            FileWriter file = new FileWriter(root,append);
            file.write(responseJson);
            for (int i = 0;i<=separateLines;i++){
                file.append("\r\n");
            }
            file.flush();
            file.close();
            return true ;
        } catch (IOException e) {
            e.printStackTrace();
            return false ;
        }
    }

    public static boolean onSaveFileToStore(String path_folder_name, String fileName, String responseJson, boolean append) {
        FileOutputStream outputStream;
        try{
            new File(path_folder_name).mkdirs();
            File root = new File(path_folder_name+ "/" + fileName);
            if (!root.exists()){
                root.createNewFile();
            }
            outputStream = new FileOutputStream(root, append);
            Writer out = new BufferedWriter(new OutputStreamWriter(outputStream, ENCODING));
            out.append(responseJson);
            out.append("\r\n");
            out.flush();
            out.close();
            outputStream.close();
            return true ;
        } catch (IOException e) {
            e.printStackTrace();
            return false ;
        }
    }

    public static String mReadJsonDataFile(String fileName, String path_folder_name){
        try {
            File f = new File(path_folder_name+"/"+ fileName);
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);
            return mResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<File> getFileListByDirPath(String path, FileFilter filter) {
        File directory = new File(path);
        File[] files = directory.listFiles(filter);
        if (files == null) {
            return new ArrayList<>();
        }
        List<File> result = Arrays.asList(files);
        Collections.sort(result, new FileComparator());
        return result;
    }

    public static String cutLastSegmentOfPath(String path) {
        if (path.length() - path.replace("/", "").length() <= 1)
            return "/";
        String newPath = path.substring(0, path.lastIndexOf("/"));
        // We don't need to list the content of /storage/emulated
        if (newPath.equals("/storage/emulated"))
            newPath = "/storage";
        return newPath;
    }

    public static String getReadableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static String getRandomUUID(){
        try {
            UUID uuid = UUID.randomUUID();
            return uuid.toString();
        }
        catch (Exception e){
            return ""+ System.currentTimeMillis();
        }
    }

    public static boolean onConvertBase64ToXML(String path, String fileName, String pathFileName){
        try{
            File file = new File(pathFileName);
            if (!file.isFile()){
                return false;
            }
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(pathFileName));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            }
            catch (IOException e) {
                //You'll need to add proper error handling here
            }
            byte[] data = Base64.decode(text.toString().getBytes(), Base64.DEFAULT);
            String result = new String(data, Charset.defaultCharset());
            return onSaveFileToStore(fileName,path,result , false,1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static String createUniqueId() throws Exception {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public static String getCurrentDate(String format){
        Date time = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String timezoneID = TimeZone.getDefault().getID();
        formatter.setTimeZone(TimeZone.getTimeZone(timezoneID));
        String formatted = formatter.format(time);
        return formatted;
    }

    public static String getMilliSecond(){
        SimpleDateFormat f2 = new SimpleDateFormat("S");
        String mString = f2.format(new Date()).toString();
        return mString;
    }

    public long getSpecificTime(String time){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String timezoneID = TimeZone.getDefault().getID();
            sdf.setTimeZone(TimeZone.getTimeZone(timezoneID));
            String dateInString = time;
            Date dates = sdf.parse(dateInString);
            return dates.getTime();
        }
        catch(Exception e){
        }
        return 0;
    }


    public static String getSpecificDate(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
            String timezoneID = TimeZone.getDefault().getID();
            sdf.setTimeZone(TimeZone.getTimeZone(timezoneID));
            String dateInString = sdf.format(new Date()).toString();
            return dateInString;
        }
        catch(Exception e){

        }
        return "20-12-2017";
    }

    public static String getSpecificDateFormat(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd");
            String timezoneID = TimeZone.getDefault().getID();
            sdf.setTimeZone(TimeZone.getTimeZone(timezoneID));
            String dateInString = sdf.format(new Date()).toString();
            return dateInString;
        }
        catch(Exception e){

        }
        return "20171220";
    }

    public static String getSpecificDateTimeFormat(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd.HHmmss");
            String timezoneID = TimeZone.getDefault().getID();
            sdf.setTimeZone(TimeZone.getTimeZone(timezoneID));
            String dateInString = sdf.format(new Date()).toString();
            Log.d(TAG,"show time format : "+dateInString);
            return dateInString;
        }
        catch(Exception e){

        }
        return "20171220";
    }


    public static String getFormatMillisToDate(long dateInMillis) {
        try{
            Date date = new Date(dateInMillis);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd.HHmmss");
            String timezoneID = TimeZone.getDefault().getID();
            sdf.setTimeZone(TimeZone.getTimeZone(timezoneID));
            String dateInString = sdf.format(date).toString();
            Log.d(TAG,"show time format : "+dateInString);
            return dateInString;
        }
        catch(Exception e){

        }
        return null;
    }

    public static long getAllDateTime(String date, String time){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
            String timezoneID = TimeZone.getDefault().getID();
            sdf.setTimeZone(TimeZone.getTimeZone(timezoneID));
            String dateInString = date +" "+time;
            Date dates = sdf.parse(dateInString);
            return dates.getTime();
        }
        catch(Exception e){

        }
        return 0;
    }


    public static void scheduleAlarm(Context context, long startTime, Class<?> receiverClass, Bundle data) {
        try {
            Intent intentAlarm = new Intent(context, receiverClass);
            if(data != null){
                intentAlarm.putExtras(data);
            }
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, startTime, PendingIntent.getBroadcast(context, 100, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, PendingIntent.getBroadcast(context, 200, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
            }

        } catch (Exception e) {

        }
    }


}
