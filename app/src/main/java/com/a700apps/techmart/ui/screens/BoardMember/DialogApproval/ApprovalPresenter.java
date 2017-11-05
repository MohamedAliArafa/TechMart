package com.a700apps.techmart.ui.screens.BoardMember.DialogApproval;

import android.util.Log;

import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.model.NotificationDataLike;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.utils.Globals;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by khaled.badawy on 10/10/2017.
 */

public class ApprovalPresenter extends MainPresenter<approvalView> {


    public void manageTimeLineItem(int itemId, final int type, final String userId, final int status ,
                                   final int pagenumber , final int pagesize) {

        view.showLoadingProgress();

        try {
            JSONObject jsonObject = MainApiHelper.manageTimelineItem(itemId, status, userId, type);

            MainApi.manageTimelineItem(jsonObject, new NetworkResponseListener<PostData>() {

                @Override
                public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {
                    if (isDetachView()) return;
                    view.dismissLoadingProgress();
                    PostData userNetworkData = (PostData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;

                    if (errorCode == 1) {
                        switch (status) {
                            case 1:
                                if (type ==1){
                                    view.showToast("Event Approved successfully");
                                }else {
                                    view.showToast("Post Approved successfully");
                                }
                                break;
                            case 2:
                                if (type ==1){
                                    view.showToast("Event Rejected successfully");
                                }else {
                                    view.showToast("Post Rejected successfully");
                                }
                                break;
                            case 3:
                                if (type ==1){
                                    view.showToast("Event Deferred successfully");
                                }else {
                                    view.showToast("Post deferred successfully");
                                }
                                break;
                        }

                        getTimeline(Globals.GROUP_ID , userId , type , pagenumber,pagesize);
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    view.dismissLoadingProgress();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }

    }

    public void getTimeline(int GroupID, String UserID, int Type,int pagenumber , int pagesize) {

        view.showLoadingProgress();

        try {
            JSONObject registerBody = MainApiHelper.getTimeLineMember(GroupID, UserID, Type,pagenumber,pagesize);
            MainApi.getMemberTimeLine(registerBody, new NetworkResponseListener<TimeLineData>() {
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
                    Log.e("error", throwable.getMessage().toString());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }

    }
}
