package com.a700apps.techmart.ui.screens.timeline;

import android.content.Context;
import android.util.Log;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.model.UserData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.ui.screens.register.RegisterView;
import com.a700apps.techmart.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir salah on 9/10/2017.
 */

public class TimeLinePresenter extends MainPresenter<TimeLineView> implements NetworkResponseListener<TimeLineData> {


    void getTimeline(String userId, String type) {

        view.showLoadingProgress();

        try {
            JSONObject registerBody = MainApiHelper.getTimeLine(userId, type);
            MainApi.getTimeLine(registerBody, this);
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }

    }

    @Override
    public void networkOperationSuccess(NetworkResponse<TimeLineData> networkResponse) {
        view.dismissLoadingProgress();
        if (isDetachView()) return;
        TimeLineData userNetworkData = (TimeLineData) networkResponse.data;
        int errorCode = userNetworkData.getISResultHasData();
        view.updateUi(userNetworkData.getResult());
    }

    @Override
    public void networkOperationFail(Throwable throwable) {
        view.dismissLoadingProgress();
        view.showErrorDialog(R.string.check_internet);
    }

}
