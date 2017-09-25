package com.a700apps.techmart.ui;

import android.content.Context;

import com.a700apps.techmart.TechMartApp;
import com.a700apps.techmart.utils.AppUtils;

/**
 * Created by samir salah on 8/13/2017.
 */

public class MainPresenter<T> {


    public T view;

    public void attachView(T view){
        this.view = view;
    }


    public boolean isDetachView(){
        return view ==null;
    }


    public void detachView(){
        view = null;
    }

    public boolean isInternetAvailable(){
        return AppUtils.isInternetAvailable(TechMartApp.getAppContext());
    }

    public Context getContext(){
        return TechMartApp.getAppContext();
    }
}

