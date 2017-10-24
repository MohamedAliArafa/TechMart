package com.a700apps.techmart.ui.screens.grouptimeline;

import android.util.Log;

import com.a700apps.techmart.data.model.GroupTimeLine;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.ui.screens.timeline.TimeLineView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir salah on 9/13/2017.
 */

public class GroupTimeLinePresenter extends MainPresenter<GroupTimlineView> implements NetworkResponseListener<TimeLineData> {


    void getTimeline(String userId, int GroupId,String type) {

        view.showLoadingProgress();

        try {
            JSONObject registerBody = MainApiHelper.getGroupTimeLine(userId, GroupId,type);
            MainApi.getGroupTimeLine(registerBody, this);
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
//        if (!userNetworkData.getResult().isEmpty())
        view.updateUi(userNetworkData.getResult());
    }

    @Override
    public void networkOperationFail(Throwable throwable) {

    }


}