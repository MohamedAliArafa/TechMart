package com.a700apps.techmart.ui.screens.login;

/**
 * Created by samir salah on 8/14/2017.
 */

interface LoginView {


    void showProgress();

    void dismissProgress();

    void showErrorDialog(int error);

    void openHomeActivity();

    void openRegisterActivity();

    void openForgetPassword(String email);


}
