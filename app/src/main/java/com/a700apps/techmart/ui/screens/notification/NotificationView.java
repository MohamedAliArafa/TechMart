package com.a700apps.techmart.ui.screens.notification;

import com.a700apps.techmart.data.model.NoficationData;

import java.util.List;

/**
 * Created by khaled.badawy on 9/27/2017.
 */

public interface NotificationView {

    void loadData(List<NoficationData.Result> list);
    void afterConnectSuccess();
    void afterFail();
    void showToast(String msg);

    void showLoading();
    void dismissLoad();
//    void deletePost(String id);
//    void addToCalender();

}
