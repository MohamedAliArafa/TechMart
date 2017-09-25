package com.a700apps.techmart.ui.screens.message;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.a700apps.techmart.data.model.AllMessageList;
import com.a700apps.techmart.data.model.FriendMessage;
import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.model.SendMessageResponse;
import com.a700apps.techmart.data.model.UserData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.ui.screens.register.RegisterView;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir.salah on 9/14/2017.
 */

public class MessagePresenter extends MainPresenter<MessageView> {
    private Context mContext;


    void userInbox(Context context, String userID) {
        mContext = context;
//        view.showProgress();

        ;

        final Dialog dialogsLoading = new loadingDialog().showDialog(context);


        try {
            JSONObject body = MainApiHelper.getUsersInbox(userID);
            MainApi.getUsersInbox(body, new NetworkResponseListener<AllMessageList>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<AllMessageList> networkResponse) {
//                    view.dismissProgress();
                    dialogsLoading.dismiss();
                    AllMessageList userNetworkData = networkResponse.data;
                    int errorCode = userNetworkData.getISResultHasData();
                    if (errorCode == 1) {
                        view.fillMessagesList(userNetworkData.getResult());
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void getFriendMessage(Context context, String userID, String relativeID) {
        mContext = context;
        final Dialog dialogsLoading = new loadingDialog().showDialog(context);
        try {
            JSONObject body = MainApiHelper.getFriendMessage(userID, relativeID);
            MainApi.getFriendMessage(body, new NetworkResponseListener<FriendMessage>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<FriendMessage> networkResponse) {
//                    view.dismissProgress();
                    dialogsLoading.dismiss();
                    FriendMessage userNetworkData = networkResponse.data;
                    int errorCode = userNetworkData.getISResultHasData();
                    if (errorCode == 1) {
                        if (userNetworkData.getResult().size() >= 1) {
                            view.fillFriendChatList(userNetworkData.getResult());
                        }
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void sendMessage(Context context, String userID, String relativeID, String Message) {
        mContext = context;
        view.showProgress();
        try {
            JSONObject body = MainApiHelper.sendMessage(userID, relativeID, Message);
            MainApi.sendMessage(body, new NetworkResponseListener<SendMessageResponse>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<SendMessageResponse> networkResponse) {
                    view.dismissProgress();
                    SendMessageResponse userNetworkData = networkResponse.data;
                    int errorCode = userNetworkData.getISResultHasData();
                    if (errorCode == 1) {
                        if (userNetworkData.getResult().getSuccess()) {
                            view.updateUi();
                        }
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getMyConnectionList(Context context, String userID) {
        mContext = context;
        view.showProgress();
        try {
            JSONObject body = MainApiHelper.getMyConnectionList(userID);
            MainApi.getMyConnectionList(body, new NetworkResponseListener<MyConnectionList>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<MyConnectionList> networkResponse) {
                    view.dismissProgress();
                    MyConnectionList userNetworkData = networkResponse.data;
                    int errorCode = userNetworkData.getISResultHasData();
                    if (errorCode == 1) {
                        if (userNetworkData.getResult().size() >= 1) {
                            view.fillConectionList(userNetworkData.getResult());
                        }
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
