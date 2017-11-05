package com.a700apps.techmart.service.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.a700apps.techmart.R;
import com.a700apps.techmart.TechMartApp;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.home.NotificationIntentService;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.utils.Config;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
//            handleNotification(remoteMessage.getNotification().getBody());
//        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            Map<String, String> map = remoteMessage.getData();
            String jsonOnject = map.get("obj");


            try {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                JSONObject json = new JSONObject(jsonOnject);
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception11: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
//        if (!NotificationUtils.isAppIsInBackground(TechMartApp.getAppContext())) {
        // app is in foreground, broadcast the push message
        Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
        pushNotification.putExtra("message", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

        Log.e("Message", message);
        Intent intent = new Intent(TechMartApp.getAppContext(), NotificationActivity.class);
        NotificationUtils notificationUtils = new NotificationUtils(TechMartApp.getAppContext());
        notificationUtils.showNotificationMessage("TechMart", message, "", intent);
    }


//    private void sendNotification() {
//
//        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.expanded_notification);
//        expandedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
//        expandedView.setTextViewText(R.id.notification_message, "Message");
//        // adding action to left button
//        Intent leftIntent = new Intent(this, NotificationIntentService.class);
//        leftIntent.setAction("left");
//        expandedView.setOnClickPendingIntent(R.id.left_button, PendingIntent.getService(this, 0, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT));
//        // adding action to right button
//        Intent rightIntent = new Intent(this, NotificationIntentService.class);
//        rightIntent.setAction("right");
//        expandedView.setOnClickPendingIntent(R.id.right_button, PendingIntent.getService(this, 1, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT));
//
//        RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.collapsed_notification);
//        collapsedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                // these are the three things a NotificationCompat.Builder object requires at a minimum
//                .setSmallIcon(R.drawable.profile_pic)
//                .setContentTitle("Title")
//                .setContentText("Context title text")
//                // notification will be dismissed when tapped
//                .setAutoCancel(true)
//                // tapping notification will open MainActivity
//                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), 0))
//                // setting the custom collapsed and expanded views
//                .setCustomContentView(collapsedView)
//                .setCustomBigContentView(expandedView)
//                // setting style to DecoratedCustomViewStyle() is necessary for custom views to display
//                .setStyle(new android.support.v7.app.NotificationCompat.DecoratedCustomViewStyle());
//
//        // retrieves android.app.NotificationManager
//        NotificationManager notificationManager = (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(0, builder.build());
//    }


    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            String message = json.getString("Message");
            String UserID = json.getString("UserID");
            String RelativeUserID = json.getString("RelativeUserID");
            int GroupID = json.getInt("GroupID");
            String RelativeUserName = json.getString("RelativeUserName");
            String UserName = json.getString("UserName");
            String descr = json.getString("descr");
            int TypeID = json.getInt("TypeID");
            int ItemID = json.getInt("ItemID");
            String Icon = json.getString("Image");


            Intent resultIntent = new Intent(TechMartApp.getAppContext(), HomeActivity.class);
            resultIntent.putExtra("message", message);
            resultIntent.putExtra("fromPush", true);

            resultIntent.putExtra("TypeID", TypeID);
            resultIntent.putExtra("RelativId", RelativeUserID);
            resultIntent.putExtra("GroupId", GroupID);
            resultIntent.putExtra("userid", UserID);
            resultIntent.putExtra("itemid", ItemID);
            resultIntent.putExtra("icon", Icon);

            showNotificationMessage(TechMartApp.getAppContext(), "TechMart Notification", message, "", resultIntent);

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception 22: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}