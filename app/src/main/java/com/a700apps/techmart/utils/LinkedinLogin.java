package com.a700apps.techmart.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONException;

/**
 * Created by samir.salah on 5/12/2016.
 */
public class LinkedinLogin {
    public final String TAG = getClass().getSimpleName();
    public static Social mLinkedInModel;
    private static LinkedinLogin mInstance = new LinkedinLogin();
    String country;

    public static LinkedinLogin getInstance() {
        mLinkedInModel = new Social();
        return mInstance;
    }

    private LinkedinLogin() {
    }

    private LinkedInLoginListener listener;

    public void setlistenr(LinkedInLoginListener listenr) {
        this.listener = listenr;
    }

    public interface LinkedInLoginListener {
        public void success(ApiResponse result);

        public void failure(LIApiError error);
    }

    public void loginUsingLinkedIn(final Context context) {
        LISessionManager.getInstance(context).init((Activity) context, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {

                Log.i(TAG, LISessionManager.getInstance(context).getSession().getAccessToken().toString());
                getUserData(context);

            }

            @Override
            public void onAuthError(LIAuthError error) {
                Log.e(TAG, error.toString());
            }
        }, true);
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

    public static String getCountry(String address) {
        if (!address.contains(",")) {
            return address;
        } else {
            return address.substring(address.lastIndexOf(",") + 1).trim();
        }

    }


    public void getUserData(final Context context) {
        APIHelper apiHelper = APIHelper.getInstance(context);
        apiHelper.getRequest(context,AppConst.mTopCardUrl, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse result) {
                Log.i(TAG, "Retrieved data: " + result.getResponseDataAsJson().toString());
                try {
                    mLinkedInModel.id= result.getResponseDataAsJson().get("id").toString();
                    mLinkedInModel.name = result.getResponseDataAsJson().get("formattedName").toString();
                    mLinkedInModel.email = result.getResponseDataAsJson().get("emailAddress").toString();
                    mLinkedInModel.link = result.getResponseDataAsJson().getString("publicProfileUrl");

                    if (result.getResponseDataAsJson().getJSONObject("positions").has("values")) {
                        mLinkedInModel.work = result.getResponseDataAsJson().getJSONObject("positions").getJSONArray("values").getJSONObject(0).getJSONObject("company").getString("name");

                    } else {
                        mLinkedInModel.work = "";
                    }
                    if (result.getResponseDataAsJson().has("summary")) {
                        mLinkedInModel.about = result.getResponseDataAsJson().get("summary").toString();
                    } else {
                        mLinkedInModel.about = "";
                    }

                    if(result.getResponseDataAsJson().has("pictureUrl")){
                        mLinkedInModel.photo = result.getResponseDataAsJson().getString("pictureUrl");
                    }else {
                        mLinkedInModel.photo = "";
                    }

//                    if (result.getResponseDataAsJson().getJSONObject("location").has("name")){

//                    if (result.getResponseDataAsJson().getJSONObject("location").has("name")) {
                        country = result.getResponseDataAsJson().getJSONObject("location").getString("name");
                        mLinkedInModel.currentCity = getCountry(country);

//                            Logger.info("COUNTRY",  mLinkedInModel.currentCity );


//                    } else {
//                        try {
//                            Logger.error("COUNTRY","NOOOO");
//                        }catch (Exception e){
//
//                        }
//                        mLinkedInModel.currentCity = null;
//                    }

//                    }else {
//                        try {
//                            Logger.error("location","error" );
//                        }catch (Exception e){
//
//                        }
//                    }

                    if (listener != null) {
                        listener.success(result);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//
            }

            @Override
            public void onApiError(LIApiError error) {
                Log.e(TAG, error.getMessage().toString());

                if (listener != null) {
                    listener.failure(error);
                }
            }
        });
    }

}
