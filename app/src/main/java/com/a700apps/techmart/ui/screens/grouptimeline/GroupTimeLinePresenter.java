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


    void getTimeline(String userId, int GroupId,String type , int PageNumber, int PageSize) {

        view.showLoadingProgress();

        try {
            JSONObject registerBody = MainApiHelper.getGroupTimeLine(userId, GroupId,type ,PageNumber,PageSize);
            MainApi.getGroupTimeLine(registerBody, this);
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }

//        try {
//            JSONObject registerBody = MainApiHelper.getTimeLine(userId, type , PageNumber,PageSize);
//            MainApi.getTimeLine(registerBody, new NetworkResponseListener<TimeLineData>() {
//                @Override
//                public void networkOperationSuccess(NetworkResponse<TimeLineData> networkResponse) {
//                    if (isDetachView()) return;
//                    TimeLineData userNetworkData = (TimeLineData) networkResponse.data;
//                    int errorCode = userNetworkData.getISResultHasData();
//                    view.updateUi(userNetworkData.getResult());
//                    view.dismissLoadingProgress();
//                }
//
//                @Override
//                public void networkOperationFail(Throwable throwable) {
////                    view.showErrorDialog(R.string.check_internet);
//                }
//            });
//        } catch (JSONException e) {
//            e.printStackTrace();
//            view.dismissLoadingProgress();
//        }
    }

    void getMoreTimeline(String userId, int GroupId,String type , int PageNumber, int PageSize) {

//        try {
//            JSONObject registerBody = MainApiHelper.getTimeLine(userId, type,PageNumber,PageSize);
//            MainApi.getTimeLine(registerBody, new NetworkResponseListener<TimeLineData>() {
//                @Override
//                public void networkOperationSuccess(NetworkResponse<TimeLineData> networkResponse) {
//                    if (isDetachView()) return;
//                    TimeLineData userNetworkData = (TimeLineData) networkResponse.data;
//                    int errorCode = userNetworkData.getISResultHasData();
//                    view.updateUiMore(userNetworkData.getResult());
//                }
//
//                @Override
//                public void networkOperationFail(Throwable throwable) {
////                    view.showErrorDialog(R.string.check_internet);
//                }
//            });
//        } catch (JSONException e) {
//            e.printStackTrace();
//            view.dismissLoadingProgress();
//        }
        try {
            JSONObject registerBody = MainApiHelper.getGroupTimeLine(userId, GroupId,type ,PageNumber,PageSize);
            MainApi.getGroupTimeLine(registerBody, new NetworkResponseListener<TimeLineData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<TimeLineData> networkResponse) {
                    if (isDetachView()) return;
                    TimeLineData userNetworkData = (TimeLineData) networkResponse.data;
                    int errorCode = userNetworkData.getISResultHasData();
//        if (!userNetworkData.getResult().isEmpty())
                    view.updateUiMore(userNetworkData.getResult());
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
//            view.dismissLoadingProgress();
        }
    }

    @Override
    public void networkOperationSuccess(NetworkResponse<TimeLineData> networkResponse) {
        view.dismissLoadingProgress();
        if (isDetachView()) return;
        TimeLineData userNetworkData = (TimeLineData) networkResponse.data;
        int errorCode = userNetworkData.getISResultHasData();
//        if (!userNetworkData.getResult().isEmpty())
        if (errorCode ==1){
            view.updateUi(userNetworkData.getResult());
        }else {
            networkOperationFail(new Throwable());
        }
    }

    @Override
    public void networkOperationFail(Throwable throwable) {
        view.dismissLoadingProgress();
    }


}