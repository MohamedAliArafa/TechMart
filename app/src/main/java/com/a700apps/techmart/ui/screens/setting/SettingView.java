package com.a700apps.techmart.ui.screens.setting;

import java.io.File;

/**
 * Created by samir salah on 8/16/2017.
 */

public interface SettingView {

    void showLoadingProgress();
    void dismissLoadingProgress();
    void showErrorDialog(int error);
    void changeNotificationStatus(String userId, boolean enabled);
    void changeLoginPassword();
    void ChangeProfilePhoto(String userId, String photo);
    void addNotificationStatus(boolean eanbled);
    void showToast(String msg);
    void saveNewPic(String name);
    void emptyViews();
}
