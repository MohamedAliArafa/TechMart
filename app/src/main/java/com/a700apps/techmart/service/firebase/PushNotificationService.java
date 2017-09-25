package com.a700apps.techmart.service.firebase;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Service to parse and show notifications
 */

public class PushNotificationService extends IntentService {


    public PushNotificationService() {
        super(PushNotificationService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle b = intent.getBundleExtra("data");
    }
}
