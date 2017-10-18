package com.a700apps.techmart.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;


import com.a700apps.techmart.R;
import com.google.firebase.iid.FirebaseInstanceId;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by ahmed agamy on 04/04/2017.
 */

public class AppUtils {


    public static String getFirebaseToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }


    public static String getDeviceId() {
        return FirebaseInstanceId.getInstance().getId();
    }

    public static boolean isInternetAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

//    public static boolean isParentUser(Context context) {
//        return context.getResources().getBoolean(R.bool.is_parent_user);
//    }


    public static Spanned getSpannedHtml(String htmlText) {
        Spanned text;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY);
        } else {
            text = Html.fromHtml(htmlText);
        }

        return text;
    }

//    public static String getAppId(Context context) {
//        if (context.getResources().getBoolean(R.bool.is_parent_user)) {
//            return AppConstant.UsersTypes.PARENT;
//        }
//        return AppConstant.UsersTypes.SITTER;
//    }

    public static int getIntValue(String value) {
        return Integer.valueOf(value.trim());
    }

    public static String getStringValue(Object o) {
        return String.valueOf(o);
    }

    public static Float getFloatValue(String value) {
        return Float.parseFloat(value);
    }

    public static String formatRate(float rate) {
        return String.format("%.2f", rate);
    }

    public static String getTimeZone() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        Date currentLocalTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("Z");
        return date.format(currentLocalTime);
    }

    public static void copyTextToClipboard(Context context, String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
    }


    public static void shareIntent(Context context, String subject, String text) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.share_via)));
    }

    public static void printHash64(Context context) {
        // Add code to print out the key hash
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", android.util.Base64.encodeToString(md.digest(), android.util.Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

//    public static void setAppLanguage(Context context) {
//        String languageToLoad = PreferenceHelper.getPrefsLanguage(context);
//        Resources resources = context.getApplicationContext().getResources();
//        Configuration overrideConfiguration = resources.getConfiguration();
//        Locale overrideLocale = new Locale(languageToLoad);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            overrideConfiguration.setLocale(overrideLocale);
//        } else {
//            overrideConfiguration.locale = overrideLocale;
//        }
//
//        resources.updateConfiguration(overrideConfiguration, context.getResources().getDisplayMetrics());
//    }


    /**
     * start activity to crop image.
     *
     * @param activity       caller activity.
     * @param imageUri       image uri to show in activity to crop it.
     * @param requiredWidth  image required width.
     * @param requiredHeight image required height.
     */
//    public static void startCropImageActivity(Activity activity, Uri imageUri, int requiredWidth, int requiredHeight) {
//        CropImage.activity(imageUri)
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setFixAspectRatio(true).setRequestedSize(requiredWidth, requiredHeight)
//                .start(activity);
//    }

    /**
     * encode image to base 64 string.
     *
     * @param image   bitmap image.
     * @param quality image quality to change image with this quality rate.
     * @return image after conversion in string format.
     */
//    public static String encodeBitmapToBase64(Bitmap image, int quality) {
//        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.PNG, quality, byteArrayOS);
//        return Base64.encodeBytes(byteArrayOS.toByteArray());
//    }


    /**
     * Rate CompanyApp In google play
     *
     * @param context The Activity context.
     */
    public static void rateApp(Context context) {
        try {
            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    static void makeCall(Context context, String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(number.trim())));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(callIntent);
    }

    static void sendSms(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + number));
        context.startActivity(intent);
    }

    public static void registerEventBus(Object o) {
        EventBus.getDefault().register(o);
    }

    public static void unregisterEventBus(Object o) {
        EventBus.getDefault().unregister(o);
    }

    public static void sendEvent(Object o) {
        EventBus.getDefault().post(o);
    }


//    public static void changeSmsBroadcastReceiverState(Context context, boolean isEnabled) {
//
//        int flag = (isEnabled ?
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
//        ComponentName component = new ComponentName(context, SmsReceiver.class);
//
//        context.getPackageManager()
//                .setComponentEnabledSetting(component, flag,
//                        PackageManager.DONT_KILL_APP);
//    }

    /**
     * Function to compare to times.
     */
    public static boolean compareTimes(String starDate, String endDate) {

        String pattern = "yyyy-MM-dd H:m a";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        try {
            //endTime = sdf.format(endTime);
            Date date1 = sdf.parse(starDate);
            starDate = sdf.format(date1);
            date1 = sdf.parse(starDate);
            Date date2 = sdf.parse(endDate);
            endDate = sdf.format(date2);
            date2 = sdf.parse(endDate);
            return date2.before(date1);
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    /**
     * Function to compare to times.
     */
    public static boolean compareStartTime(String starDate) {

        String pattern = "yyyy-MM-dd H:m a";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        try {
            Date today = Calendar.getInstance().getTime();
            String x = sdf.format(today);
            Date date1 = sdf.parse(x);
            Date date2 = sdf.parse(starDate);
            starDate = sdf.format(date2);
            date2 = sdf.parse(starDate);
            return date2.before(date1);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    public static boolean isAppLanguageArabic(Context context) {
//        return PreferenceHelper.getPrefsLanguage(context).equals("ar");
//    }


    public static String getAppGooglePlayLink(Context context) {
        return "market://details?id=" + context.getPackageName();
    }

    public static String getAppLink(Context context) {
        return
        "http://play.google.com/store/apps/details?id=" + context.getPackageName();
    }

    public static boolean isAppInForeground(Context mContext) {
        String mPackageName = "";
        List<ActivityManager.RunningTaskInfo> tasks = null;
        List<ActivityManager.RunningAppProcessInfo> tasks1 = null;

        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > 20) {
            tasks1 = am.getRunningAppProcesses();
            if (!tasks1.isEmpty()) {
                mPackageName = tasks1.get(0).processName;
            }
        } else {
            tasks = am.getRunningTasks(1);
            if (!tasks.isEmpty()) {
                mPackageName = tasks.get(0).topActivity.getPackageName();
            }

        }
        if (!mPackageName.equals(mContext.getPackageName())) {
            return false;
        }

        return true;
    }


    /**
     * Function to convert english numbers to arabic
     * if mobile language arabic
     *
     * @param number Number to convert
     * @return Converted number
     */
    public static String convertNumberToEnglishDigits(String number) {
        try {
            return number.replaceAll("٠", "0").replaceAll("١", "1")
                    .replaceAll("٢", "2").replaceAll("٣", "3")
                    .replaceAll("٤", "4").replaceAll("٥", "5")
                    .replaceAll("٦", "6").replaceAll("٧", "7")
                    .replaceAll("٨", "8").replaceAll("٩", "9");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    public static String convertNumberToArabicDigits(String number) {
        try {
            return number.replaceAll("0", "٠").replaceAll("1", "١")
                    .replaceAll("2", "٢").replaceAll("3", "٣")
                    .replaceAll("4", "٤").replaceAll("5", "٥")
                    .replaceAll("6", "٦").replaceAll("7", "٧")
                    .replaceAll("8", "٨").replaceAll("9", "٩");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

//    public static boolean isAppLanguageAr(Context context) {
//        return PreferenceHelper.getPrefsLanguage(context).equals("ar");
//    }

    public static boolean isValidBitmapSize(Bitmap bitmap, int bitmapValidSize) {
        int byteCount = bitmap.getByteCount();
        int bitmapMegaByteSize = (byteCount/1024)/1024;
        return bitmapMegaByteSize<= bitmapValidSize ;
    }

    public static boolean isAtLeast24Api() {
        return Build.VERSION.SDK_INT>= Build.VERSION_CODES.N ;
    }

    public static boolean isAtLeast17Api() {
        return Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN_MR1 ;
    }
    // DownloadImage AsyncTask

//    public static class DownloadImage {
//        private RegisterView registerView;
//
//        public DownloadImage(String imgeUrl, RegisterView registerView) {
//            new DownloadImageTask().execute(imgeUrl);
//            this.registerView = registerView;
//        }
//
//        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//
//            @Override
//            protected Bitmap doInBackground(String... URL) {
//
//                String imageURL = URL[0];
//
//                Bitmap bitmap = null;
//                try {
//                    // Download Image from URL
//                    InputStream input = new java.net.URL(imageURL).openStream();
//                    // Decode Bitmap
//                    bitmap = BitmapFactory.decodeStream(input);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return bitmap;
//            }
//
//            @Override
//            protected void onPostExecute(Bitmap result) {
//                // Set the bitmap into ImageView
//                registerView.finishDownloadImage(result);
//            }
//        }
//
//    }


    public static String getDeviceLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static  Date getDate(String date){
//        String dateString = "03/26/2012 11:49:00 AM";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(date);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(convertedDate);
        return  convertedDate;
    }

}
