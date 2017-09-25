package com.a700apps.techmart.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import me.anwarshahriar.calligrapher.Calligrapher;


/**
 * Created by ahmed agamy on 03/04/2017.
 */

public class ActivityUtils {


    public static void openActivity(Context context, String actionName, boolean clearStack) {
        openNewActivity(context, actionName, null, clearStack);
    }

    public static void openActivity(Context context, Class activity, boolean clearStack) {
        openNewActivity(context, activity, null, clearStack);
    }


    public static void openActivity(Context context, String actionName, Bundle bundle, boolean clearStack) {
        openNewActivity(context, actionName, bundle, clearStack);
    }

    public static void openActivity(Context context, Class activity, Bundle bundle, boolean clearStack) {
        openNewActivity(context, activity, bundle, clearStack);
    }

    public static void openActivityForResult(Activity context, Class activity, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivityForResult(intent, requestCode);
    }


    private static void openNewActivity(Context context, String actionName, Bundle bundle, boolean clearStack) {
        Intent intent;
        try {
            intent = new Intent(context, Class.forName(actionName));
            if (clearStack) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    private static void openNewActivity(Context context, Class className, Bundle bundle, boolean clearStack) {
        Intent intent = new Intent(context, className);
        if (clearStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public static <T> T findView(View view, @IdRes int viewId, Class<T> type) {
        return type.cast(view.findViewById(viewId));
    }

    public static <T> T findView(Activity activity, @IdRes int viewId, Class<T> type) {
        return type.cast(activity.findViewById(viewId));
    }

    public static String getViewTextValue(TextView view) {
        return view.getText().toString().trim();
    }

    public static String getViewTextValue(EditText view) {
        return view.getText().toString().trim();
    }


//    public static void showUserImage(Context context, int userId, ImageView view) {
//        ActivityUtils.showImageInView(context, MainApi.getUserImageUri(userId)
//                , R.drawable.bg_user_placeholder, R.drawable.bg_user_placeholder, true, view);
//    }

//    public static void showUserImageFromNetwork(Context context, int userId, ImageView view) {
//        ActivityUtils.showImageInView(context, MainApi.getUserImageUri(userId)
//                , R.drawable.bg_user_placeholder, R.drawable.bg_user_placeholder, false, view);
//    }

//    public static void showIdentityImage(Context context, int userId, ImageView view) {
//        ActivityUtils.showImageInView(context, MainApi.getIdentityImageUri(userId)
//                , R.drawable.default_id, R.drawable.default_id, false, view);
//    }

//    private static void showImageInView(Context context, String url, @DrawableRes int placeholder,
//                                        @DrawableRes int error, boolean enableCache, final ImageView view) {
//
//        if (enableCache) {
//            Picasso.with(context).load(url)
//                    .placeholder(placeholder).error(error).into(view);
//        } else {
//            Log.d("link", url);
//            Picasso.with(context).load(url)
//                    .placeholder(placeholder).skipMemoryCache().noFade().resize(60,60)
//                    .error(error).into(view);
//        }
//    }

//    private static void showImageInView(Context context, Uri url, @DrawableRes int placeholder, @DrawableRes int error, ImageView view) {
//        Glide.with(context).load(url).centerCrop()
//                .placeholder(placeholder).error(error).into(view);
//    }

    public static float fromDpToPxl(Context context, int dp) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

//    public static void showImage(Context context, ImageView view, String imageUrl) {
//        showImageInView(context, imageUrl
//                , R.drawable.bg_user_placeholder, R.drawable.bg_user_placeholder, true, view);
//    }

//    public static void showChildImage(Context context, ImageView imageView, int childId) {
//        ActivityUtils.showImageInView(context, MainApi.getChildImageUri(childId)
//                , R.drawable.bg_user_placeholder, R.drawable.bg_user_placeholder, false, imageView);
//    }


//    public static void showImageFromFile(Context context, ImageView view, Uri imageUrl) {
//        Picasso.with(context).load(imageUrl).error(R.drawable.bg_toolbar).into(view);
//    }


//    public static void setActionBar(Context context, ActionBar actionBar, @StringRes int title, boolean showBack) {
//        Drawable drawable;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            drawable = context.getDrawable(R.drawable.bg_toolbar);
//        } else {
//            drawable = context.getResources().getDrawable(R.drawable.bg_toolbar);
//        }
//        actionBar.setBackgroundDrawable(drawable);
//        if (title != -1) {
//            actionBar.setTitle(title);
//        }
//        if (showBack) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//    }





    public static void applyCustomFont(Activity activity, String fontName) {
        applyFont(activity, fontName);
    }

    public static void applyFont(Activity activity) {
        String fontName = "Lato-Regular.ttf";
        applyFont(activity, fontName);
    }

    public static void applyBoldFont(View view) {
        String fontName = "dax-bold-59784f71222f0.ttf";
        Calligrapher calligrapher = new Calligrapher(view.getContext());
        calligrapher.setFont(view, "fonts/" + fontName);
    }


    public static void applyLightFont(View view) {
        String fontName = "dax-light-5978520ee6c5f.otf";
        Calligrapher calligrapher = new Calligrapher(view.getContext());
        calligrapher.setFont(view, "fonts/" + fontName);
    }

    private static void applyFont(Activity activity, String fontName) {
        Calligrapher calligrapher = new Calligrapher(activity);
        calligrapher.setFont(activity, "fonts/" + fontName, true);
    }

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static float getDimenValue(Context context, int id) {
        return context.getResources().getDimension(id);
    }

    public static void startService(Context context, Class aClass, Bundle bundle) {
        Intent intent = new Intent(context, aClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startService(intent);
    }
}
