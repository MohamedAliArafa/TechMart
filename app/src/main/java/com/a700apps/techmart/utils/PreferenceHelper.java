package com.a700apps.techmart.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.a700apps.techmart.data.model.User;
import com.google.gson.Gson;


/**
 * Created by ahmed agamy on 03/04/2017.
 */

public class PreferenceHelper {


    public static User getSavedUser(Context context) {
        return getUser(context);
    }


    interface PrefsKeys {
        String USER_TOKEN = "user_token";
        String USER_DATA = "user_data";
        String RECEIVE_NOTIFICATIONS = "receive_notifications";
        String LANGUAGE = "language";
        String RECOMMENDED = "recomended";
        String FEEDBACK_KEY = "feedback_key";
        String NOTIFICATION_KEY = "NOTIFICATION_KEY";
    }

    interface Languages {
        String AR = "ar", EN = "en";
    }
//





    public static String getUserId(Context context) {
        User user = getUser(context);
        if (user != null) {
            return user.UserID;
        }
        return null;
    }

    private static User getUser(Context context) {
        Object o = getValue(context, PrefsKeys.USER_DATA);
        if (o != null) {
            return new Gson().fromJson(String.valueOf(o), User.class);
        }
        return null;
    }


    public static void saveUser(Context context, User user) {
        saveValue(context, PrefsKeys.USER_DATA, new Gson().toJson(user));
    }


    public static void test(){

    }


    public static boolean isVerifyMail(Context context) {
        User user = getUser(context);
        return user != null ;
    }


    private static void saveValue(Context context, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        key = Encryption.encrypt(key);
        value = Encryption.encrypt(value);
        sharedPreferences.edit().putString(key, value).apply();
    }

    private static String getValue(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        key = Encryption.encrypt(key);
        String savedValue = sharedPreferences.getString(key, null);
        if (savedValue != null) {
            return Encryption.decrypt(savedValue);
        }
        return null;
    }

    public static void setNotificationStatus(Context context , boolean enabled){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean(PrefsKeys.NOTIFICATION_KEY, enabled).apply();
    }

    public static boolean getNotificationStatus(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(PrefsKeys.NOTIFICATION_KEY , false);
    }




}
