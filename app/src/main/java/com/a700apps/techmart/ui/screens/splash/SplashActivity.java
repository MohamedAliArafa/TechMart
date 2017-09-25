package com.a700apps.techmart.ui.screens.splash;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.login.LoginActivity;
import com.a700apps.techmart.utils.ATCPrefManager;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.AppUtils;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;

import io.fabric.sdk.android.Fabric;

/**
 * Created by samir salah on 8/13/2017.
 */

public class SplashActivity extends Activity implements SplashView {

    private SplashPresenter mPresenter;
    private Handler handler;
    private Runnable runnable;
    ImageView imgVSplash;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        FirebaseApp.initializeApp(this);

        try {
            AppUtils.getFirebaseToken();
            Log.e("Token", AppUtils.getFirebaseToken());
        }catch (Exception e){

        }

        mPresenter = new SplashPresenter();
        mPresenter.attachView(this);
        mPresenter.setSplashContentView();
        initializeTimer();
    }

    public boolean isUserLoggedIn() {
        return ATCPrefManager.isUserLoggedIn(this);
    }


    private void initializeTimer() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                if (isUserLoggedIn()) {
                    openHomeActivity();
                } else {
//                    startLoginActivity();
                    openLoginRegisterOptionsActivity();
                }


            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.activityStarted();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.activityStopped();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    //------------------- Implemented methods-------------------------------------//

    @Override
    public void openLoginRegisterOptionsActivity() {
        ActivityUtils.openActivity(this, LoginActivity.class, null, false);
    }

    @Override
    public void openHomeActivity() {
        ActivityUtils.openActivity(SplashActivity.this, HomeActivity.class, true);
    }

    @Override
    public void startSplashViewTimer() {

        handler.postDelayed(runnable, 2000);

    }

    @Override
    public void stopSplashViewTimer() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void showWrongUSer() {

    }

    @Override
    public void setSplashViewLayout(@LayoutRes int layout) {
        setContentView(layout);
        imgVSplash = (ImageView) findViewById(R.id.iv_login_logo);
        animateFadeIN(imgVSplash);
    }

    @Override
    public void openLoginRegisterActivity() {

    }


    private void animateFadeIN(ImageView imgv) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setStartOffset(500);
        fadeIn.setDuration(3000);

        AnimationSet animation = new AnimationSet(false); //change to false

        animation.addAnimation(fadeIn);
        imgv.setAnimation(animation);
    }
}
