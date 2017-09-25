package com.a700apps.techmart;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.a700apps.techmart.dagger.Application.component.ApplicationComponent;
import com.a700apps.techmart.dagger.Application.component.DaggerApplicationComponent;
import com.a700apps.techmart.dagger.Application.module.ContextModule;
import com.a700apps.techmart.service.ApiService;

import timber.log.Timber;

/**
 * Created by samir salah on 8/14/2017.
 */

public class TechMartApp extends Application {

    private static Context appContext;


    public static TechMartApp get(Context context) {
        return (TechMartApp) context.getApplicationContext();
    }


    private ApiService mApiService;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        // initialize debug.
        Timber.plant(new Timber.DebugTree());


        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        mApiService = applicationComponent.getService();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //  AppUtils.setAppLanguage(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }


    public static Context getAppContext() {
        return appContext;
    }

    public ApiService getApiService(){
        return mApiService;
    }


}
