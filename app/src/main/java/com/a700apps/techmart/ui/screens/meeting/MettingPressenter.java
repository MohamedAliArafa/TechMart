package com.a700apps.techmart.ui.screens.meeting;

import android.content.Context;

import com.a700apps.techmart.data.model.AllSchedualList;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dev_halima on 9/16/2017.
 */

public class MettingPressenter extends MainPresenter<MettingView> {

    private Context mContext;


    void getSchedual(Context context, String userID, String StartDate, String EndDate) {
        mContext = context;
        view.showProgress();
        try {
            JSONObject body = MainApiHelper.getSchedual(userID, StartDate, EndDate);
            MainApi.getSchedual(body, new NetworkResponseListener<AllSchedualList>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<AllSchedualList> networkResponse) {
                    view.dismissProgress();
                    AllSchedualList userNetworkData = networkResponse.data;
                    int errorCode = userNetworkData.getISResultHasData();
                    if (errorCode == 1) {
                        if (userNetworkData.getResult().size() >= 1) {
                            view.drawUiData(userNetworkData.getResult());
                        } else {
                            view.noEvent();
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
