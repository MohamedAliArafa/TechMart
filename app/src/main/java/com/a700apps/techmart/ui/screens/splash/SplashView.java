package com.a700apps.techmart.ui.screens.splash;

import android.support.annotation.LayoutRes;

/**
 * Created by samir salah on 8/13/2017.
 */

interface  SplashView {

    void openLoginRegisterOptionsActivity();

    void openHomeActivity();

    void startSplashViewTimer();

    void stopSplashViewTimer();

    void showWrongUSer();

    void setSplashViewLayout(@LayoutRes int layout);

    void openLoginRegisterActivity();
}
