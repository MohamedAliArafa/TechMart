package com.a700apps.techmart.ui.screens.register;

/**
 * Created by samir salah on 8/16/2017.
 */

 public  interface RegisterView {

 void openCouncilActivity();
 void showLoadingProgress();

 void dismissLoadingProgress();
 void showErrorDialog(int error);

}
