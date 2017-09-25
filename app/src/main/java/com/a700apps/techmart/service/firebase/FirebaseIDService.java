package com.a700apps.techmart.service.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Mohamed Mo'men on 12/13/2016.
 */

public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

    }

   /* *//**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     *//*
    private void sendRegistrationToServer(String token , int userID) {
        try{

            //get mobile imei "serial number"
            TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();

            //create json data to send request
            JSONObject json = new JSONObject();
            json.put("serial_num" , imei);
            json.put("user_id" , userID);
            json.put("device_token" , token);

            //send token
            new PostRequest(new ServerResponseListener() {
                @Override
                public void onServerSuccess(String responseBody) {
                    try{
                        JSONObject json = new JSONObject(responseBody);
                        int status = json.getInt("status");
                        if (status == 1) {
                            Log.d("TOKEN_UPDATED" , "TOKEN_UPDATED");
                        }else{
                            Log.d("TOKEN_UPDATED" , "TOKEN_NOT_UPDATED");
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onServerFail(ServerException exception) {
                    Log.d("TOKEN_UPDATED" , "TOKEN_NOT_UPDATED");
                }
            }, json.toString()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MainApiHelper.ApiLinks.UPDATE_TOKEN_LINK);

        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
}
