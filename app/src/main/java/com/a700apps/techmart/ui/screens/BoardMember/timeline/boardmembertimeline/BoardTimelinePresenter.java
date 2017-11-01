package com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembertimeline;

import android.util.Log;

import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir.salah on 10/10/2017.
 */

public class BoardTimelinePresenter  extends MainPresenter<BoardTimlineView> implements NetworkResponseListener<TimeLineData> {


   public void getTimeline(int GroupID, String UserID, int Type) {

        view.showLoadingProgress();

        try {
            JSONObject registerBody = MainApiHelper.getTimeLineMember( GroupID,  UserID,  Type);
            MainApi.getMemberTimeLine(registerBody, this);
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
        view.dismissLoadingProgress();
        Log.e("error",throwable.getMessage().toString());
    }
}
