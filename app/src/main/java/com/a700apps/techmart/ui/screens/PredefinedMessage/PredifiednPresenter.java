package com.a700apps.techmart.ui.screens.PredefinedMessage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.ChangeReciveNotifcationData;
import com.a700apps.techmart.data.model.PredifinedData;
import com.a700apps.techmart.data.model.SendMessageResponse;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir salah on 8/16/2017.
 */

public class PredifiednPresenter extends MainPresenter<PredifinedView> implements NetworkResponseListener<PredifinedData> {



    void LoadData(){
            view.showLoadingProgress();
            MainApi.GetPreDefindMessage(this);
    }


    void sendMessage(String userID, String relativeID, String Message) {
        Log.e("User" , userID);
        Log.e("relativeID" , relativeID);
        Log.e("Message" , Message);

        view.showLoadingProgress();
        try {
            JSONObject body = MainApiHelper.sendMessage(userID, relativeID, Message);
            MainApi.sendMessage(body, new NetworkResponseListener<SendMessageResponse>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<SendMessageResponse> networkResponse) {
                    view.dismissLoadingProgress();
                    SendMessageResponse userNetworkData = networkResponse.data;
                    int errorCode = userNetworkData.getISResultHasData();
                    if (errorCode == 1) {
                        if (userNetworkData.getResult().getSuccess()) {
                            view.showToast("Message Sent+ ");
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

    @Override
    public void networkOperationSuccess(NetworkResponse<PredifinedData> networkResponse) {
        if (networkResponse.data.getResult()!=null){
            view.updateUi(networkResponse.data.getResult());
        }else {
            view.showToast("list is null");
        }
    }

    @Override
    public void networkOperationFail(Throwable throwable) {
        view.showToast(throwable.getMessage());
    }
}
