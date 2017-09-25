package com.a700apps.techmart.ui.screens.splash;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.utils.PreferenceHelper;

/**
 * Created by samir salah on 8/13/2017.
 */

public class SplashPresenter extends MainPresenter<SplashView> {
    private boolean verifyUser;
    void activityStarted() {
        view.startSplashViewTimer();
    }


    void activityStopped() {
        view.stopSplashViewTimer();
    }

    // define which content view
    void setSplashContentView() {
//        verifyUser = PreferenceHelper.isVerifyMail(getContext());
        // user is logged in app and is active.
        view.setSplashViewLayout(R.layout.activity_splash);

    }

    void activityFinishedSplashTimer() {
        if (isDetachView()) return;
        view.openLoginRegisterOptionsActivity();
    }


}
