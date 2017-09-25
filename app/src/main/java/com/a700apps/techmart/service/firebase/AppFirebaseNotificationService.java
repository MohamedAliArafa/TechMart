package com.a700apps.techmart.service.firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Service to get messages from fire base
 */

public abstract class AppFirebaseNotificationService extends FirebaseMessagingService {


    @Override
    public abstract void onMessageReceived(RemoteMessage remoteMessage) ;

}
