package com.a700apps.techmart.ui.screens.home;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

/**
 * Created by samir salah on 8/15/2017.
 */

 interface HomeView {

    void replaceFragment(Fragment fragment , @StringRes int toolbarTitle);

    void showUserDataInNavigationView();

    void setToolbarTitle(@StringRes int title);
    void setNotificationsCount(String count);

    void  openHomeActivity();

    void showErrorDialog(@StringRes int error);

    void showProgress();

    void dismissProgress();

   void showManageLayout(boolean show);
}
