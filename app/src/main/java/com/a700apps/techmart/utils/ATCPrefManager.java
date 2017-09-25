package com.a700apps.techmart.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by m.aboelazm on 11/16/2015.
 * mohamed.aboelazm@apptcom.com
 * Mohamed Abulazm: mhabulazm@gmail.com
 */
public final class ATCPrefManager {
    //Change The preference name according to project
    private static final String PREF_NAME = "atc_pref";
    private static final String PREF_USER_PHOTO = "user_photo";
    private static final String PREF_USER_NAME = "user_name";
    private static final String PREF_USER_MOBILE = "user_mobile";
    private static final String PREF_TOKEN = "token";
    private static final String PREF_LANG_CODE = "lang_code";
    private static final String PREF_LOGIN_TYPE = "login_type";

    private static final String PREF_IS_USER_First_IN = "is_user_logged_in";
    private static final String PREF_USER_ID = "user_id";
    private static final String PREF_IS_DATABASE_LOADED = "is_data_loaded";
    private static final String PREF_LANGUAGE_CODE = "language_code";
    private static final String PREF_IS_FIRST_ENTER_FEEDS = "is_first_feeds";
    private static final String PREF_AUTH_MSG = "auth_msg";
    private static final String PENDING_POST = "pending_post";
    private static final String PREF_IS_ABOUT = "is_about";
    private static final String NOTIFICATION_COUNT = "notification_count";
    private static final String TRAINING_PROGRAM_ID = "training_id";
    public static final String SHOULD_RECEIVE = "should_receive";
    public static final String ABOUT_BACK = "about_back";

    private static final String PREF_REG_ID = "reg_id";
    public static final String PRE_IS_REGISTERED = "is_reg";


//    public ATCPrefManager() throws Exception {
//        throw new StaticCallException("Can't make instance, user static methods instead!");
//    }

    private static String getPrefName() {
        return PREF_NAME;
    }

    public static SharedPreferences getATCPreference(final Context context) {
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences(getPrefName(), Context.MODE_PRIVATE);
    }

    public static boolean clearPref(Context context) {
        return getATCPreference(context).edit().clear().commit();
    }

    public static boolean setRegId(Context context, String reg_id) {
        return getATCPreference(context).edit().putString(PREF_REG_ID, reg_id).commit();
    }

    public static String getRegId(Context context) {
        return getATCPreference(context).getString(PREF_REG_ID, "");
    }

    public static boolean setIsRegistered(Context context, boolean isReg) {
        return getATCPreference(context).edit().putBoolean(PRE_IS_REGISTERED, isReg).commit();
    }

    public static boolean getIsRegistered(Context context) {
        return getATCPreference(context).getBoolean(PRE_IS_REGISTERED, false);
    }

    public static boolean setToken(Context context, String token) {
        return getATCPreference(context).edit().putString(PREF_TOKEN, token).commit();
    }

    public static String getToken(Context context) {
        return getATCPreference(context).getString(PREF_TOKEN, "");
    }

    public static boolean setAuthMsg(Context context, String auth_message) {
        return getATCPreference(context).edit().putString(PREF_AUTH_MSG, auth_message).commit();
    }

    public static String getAuthMsg(Context context) {
        return getATCPreference(context).getString(PREF_AUTH_MSG, "");
    }

    public static boolean setCountriesFilter(Context context, int filter) {
        return getATCPreference(context).edit().putInt("ntfy_cntry_fltr", filter).commit();
    }

    public static int getCountriesFilter(Context context) {
        return getATCPreference(context).getInt("ntfy_cntry_fltr", -1);
    }

    public static boolean setLoginType(Context context, String type) {
        return getATCPreference(context).edit().putString(PREF_LOGIN_TYPE, type).commit();
    }

    public static String getLoginType(Context context) {
        return getATCPreference(context).getString(PREF_LOGIN_TYPE, "");
    }

    public static boolean setLanguageCode(Context context, String langCode) {
        return getATCPreference(context).edit().putString(PREF_LANG_CODE, langCode).commit();
    }

    public static String getLanguageCode(Context context) {
        return getATCPreference(context).getString(PREF_LANG_CODE, "");
    }

    public static boolean setIsUserLoggedIn(Context context, boolean isFirstIn) {
        return getATCPreference(context).edit().putBoolean(PREF_IS_USER_First_IN, isFirstIn).commit();
    }

    public static boolean isUserLoggedIn(Context context) {
        return getATCPreference(context).getBoolean(PREF_IS_USER_First_IN, false);
    }

    public static boolean setIsDataLoaded(Context context, boolean isDataLoad) {
        return getATCPreference(context).edit().putBoolean(PREF_IS_DATABASE_LOADED, isDataLoad).commit();
    }

    public static boolean isDataLoad(Context context) {
        return getATCPreference(context).getBoolean(PREF_IS_DATABASE_LOADED, false);
    }

    public static boolean setIFirstFeeds(Context context, boolean isFirstFeeds) {
        return getATCPreference(context).edit().putBoolean(PREF_IS_FIRST_ENTER_FEEDS, isFirstFeeds).commit();
    }

    public static boolean getFirstFeeds(Context context) {
        return getATCPreference(context).getBoolean(PREF_IS_FIRST_ENTER_FEEDS, false);
    }

    public static boolean setIsAbout(Context context, boolean isAbout) {
        return getATCPreference(context).edit().putBoolean(PREF_IS_ABOUT, isAbout).commit();
    }

    public static boolean getIsAbout(Context context) {
        return getATCPreference(context).getBoolean(PREF_IS_ABOUT, false);
    }


    // COLORS
    private static final String PREF_FOR_GROUND_COLOR = "for_ground_color";
    private static final String PREF_BACK_GROUND_COLOR = "background_color";

    private static final String PREF_TEXT_FOR_GROUND_COLOR = "text_for_ground_color";
    private static final String PREF_TEXT_BACK_GROUND_COLOR = "text_background_color";

    private static final String PREF_TOOLBAR_COLOR = "toolbar_color";
    private static final String PREF_ICON_COLOR = "icon_color";
    private static final String PREF_CARD_COLOR = "card_color";

    public static boolean setForegroundColor(Context context, int color) {
        return getATCPreference(context).edit().putInt(PREF_FOR_GROUND_COLOR, color).commit();
    }

    public static boolean setBackgroundColor(Context context, int color) {
        return getATCPreference(context).edit().putInt(PREF_BACK_GROUND_COLOR, color).commit();
    }

    public static boolean setTextForegroundColor(Context context, int color) {
        return getATCPreference(context).edit().putInt(PREF_TEXT_FOR_GROUND_COLOR, color).commit();
    }

    public static boolean setTextBackgroundColor(Context context, int color) {
        return getATCPreference(context).edit().putInt(PREF_TEXT_BACK_GROUND_COLOR, color).commit();
    }

    public static boolean setToolbarColor(Context context, int color) {
        return getATCPreference(context).edit().putInt(PREF_TOOLBAR_COLOR, color).commit();
    }

    public static boolean setIconColor(Context context, int color) {
        return getATCPreference(context).edit().putInt(PREF_ICON_COLOR, color).commit();
    }

    public static boolean setCardColor(Context context, int color) {
        return getATCPreference(context).edit().putInt(PREF_CARD_COLOR, color).commit();
    }

    //
    public static int getForegroundColor(Context context) {
        return getATCPreference(context).getInt(PREF_FOR_GROUND_COLOR, 0);
    }

    public static int getBackgroundColor(Context context) {
        return getATCPreference(context).getInt(PREF_BACK_GROUND_COLOR, 0);
    }

    //
    public static int getTextForegroundColor(Context context) {
        return getATCPreference(context).getInt(PREF_TEXT_FOR_GROUND_COLOR, 0);
    }

    public static int getTextBackgroundColor(Context context) {
        return getATCPreference(context).getInt(PREF_TEXT_BACK_GROUND_COLOR, 0);
    }

    public static int getToolbarColor(Context context) {
        return getATCPreference(context).getInt(PREF_TOOLBAR_COLOR, 0);
    }

    public static int getIconColor(Context context) {
        return getATCPreference(context).getInt(PREF_ICON_COLOR, 0);
    }

    public static int getCardColor(Context context) {
        return getATCPreference(context).getInt(PREF_CARD_COLOR, 0);
    }

    //---------Hamdy functions---------
    public static boolean setPendingPost(Context context, String post) {
        return getATCPreference(context).edit().putString(PENDING_POST, post).commit();
    }

    public static String getPendingPost(Context context) {
        return getATCPreference(context).getString(PENDING_POST, "");
    }

    public static void setPendingPost2(Context context, String post) {
        getATCPreference(context).edit().putString(PENDING_POST, post).commit();
    }
    //---------Hamdy functions---------

    public static boolean shouldReceive(Context context) {
        return getATCPreference(context).getBoolean(SHOULD_RECEIVE, true);
    }

    public static boolean toggleShouldReceive(Context context) {
        return getATCPreference(context).edit().putBoolean(SHOULD_RECEIVE, false).commit();
    }

    public static void setNotificationNumber(Context context, String count) {
        getATCPreference(context).edit().putString(NOTIFICATION_COUNT, count).apply();
    }

    public static String getNotificationNumber(Context context) {
        return getATCPreference(context).getString(NOTIFICATION_COUNT, "");
    }

    public static void addTrainingProgramToCalendar(Context context, String idKey, int idValue) {
        getATCPreference(context).edit().putInt(idKey, idValue).apply();
    }

    public static int getTrainingProgramId(Context context, String idKey) {
        return getATCPreference(context).getInt(idKey, -1);
    }

    public static boolean setUserId(Context context, int userId) {
        return getATCPreference(context).edit().putInt(PREF_USER_ID, userId).commit();
    }

    public static int getUserId(Context context) {
        return getATCPreference(context).getInt(PREF_USER_ID, 1);
    }

}
