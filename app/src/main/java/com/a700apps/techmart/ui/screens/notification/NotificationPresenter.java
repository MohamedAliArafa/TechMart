package com.a700apps.techmart.ui.screens.notification;

import com.a700apps.techmart.data.model.NoficationData;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by khaled.badawy on 9/27/2017.
 */

public class NotificationPresenter extends MainPresenter<NotificationView> implements NetworkResponseListener<NoficationData> {

    void loadData(String userId, int pageNumber, int pageCount) {

        view.showLoading();
        try {
            JSONObject body = MainApiHelper.GetNotification(userId, pageNumber, pageCount);
            MainApi.GetNotifications(body, this);
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoad();
        }
    }

    void sendConnect(String RelativeID, String UserID, final String connect) {

        view.showLoading();
        try {
            JSONObject registerBody = MainApiHelper.connect(RelativeID, UserID, connect);
            MainApi.sendConnect(registerBody, new NetworkResponseListener<PostData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {
                    if (isDetachView()) return;

                    PostData userNetworkData = (PostData) networkResponse.data;
                    if (userNetworkData.postData.success) {
                        view.showToast("You are now connected");
                        view.afterConnectSuccess();
                    } else {
                        view.afterFail();
                    }
                    view.dismissLoad();
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    view.afterFail();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void deleteNotification(int id) {

        view.showLoading();
        try {
            JSONObject registerBody = MainApiHelper.deleteNotification(id);
            MainApi.deleteNotification(registerBody, new NetworkResponseListener<ServerResponse>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<ServerResponse> networkResponse) {
                    if (isDetachView()) return;

                    ServerResponse userNetworkData = (ServerResponse) networkResponse.data;
                    if (userNetworkData.postData.success) {
                        view.afterConnectSuccess();
                    } else {
                        view.afterFail();
                    }
                    view.dismissLoad();
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    view.afterFail();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void networkOperationSuccess(NetworkResponse<NoficationData> networkResponse) {
        if (networkResponse.data.getResult() != null) {
            view.loadData(networkResponse.data.getResult());
            view.dismissLoad();
        }
    }

    @Override
    public void networkOperationFail(Throwable throwable) {
        view.showToast("Data not loaded");
        view.dismissLoad();
    }
}
