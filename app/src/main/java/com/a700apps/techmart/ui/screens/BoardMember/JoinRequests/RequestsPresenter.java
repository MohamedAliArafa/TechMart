package com.a700apps.techmart.ui.screens.BoardMember.JoinRequests;

import com.a700apps.techmart.data.model.JoinGroupRequestsData;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by khaled.badawy on 10/10/2017.
 */

public class RequestsPresenter extends MainPresenter<RequestsView> {


    public void getGroupRequests(String groupId, String userId) {

        view.showLoadingProgress();

        try {
            JSONObject jsonObject = MainApiHelper.getJoinGroupRequests(groupId, userId);

            MainApi.getJoinGroupRequests(jsonObject, new NetworkResponseListener<JoinGroupRequestsData>() {

                @Override
                public void networkOperationSuccess(NetworkResponse<JoinGroupRequestsData> networkResponse) {
                    if (isDetachView()) return;
                    view.dismissLoadingProgress();
                    JoinGroupRequestsData userNetworkData = (JoinGroupRequestsData) networkResponse.data;
                    int errorCode = userNetworkData.getISResultHasData();


                    if (errorCode == 1) {
                        view.updateData(userNetworkData.getResult());
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

    public void manageTimeLineItem(int itemId, int type , String userId , final int status) {

        view.showLoadingProgress();

        try {
            JSONObject jsonObject = MainApiHelper.manageTimelineItem(itemId, status , userId ,type);

            MainApi.manageTimelineItem(jsonObject, new NetworkResponseListener<PostData>() {

                @Override
                public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {
                    if (isDetachView()) return;
                    view.dismissLoadingProgress();
                    PostData userNetworkData = (PostData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;

                    if (errorCode == 1) {
                        switch (status){
                            case 1:
                                view.showToast("Item Approved");
                                break;
                            case 2:
                                view.showToast("Item Rejected");
                                break;
                            case 3:
                                view.showToast("Item Defered");
                                break;


                        }
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


    public void manageRequestItem(int itemId, final int requestStatus , int requestRole, String userId ) {

        view.showLoadingProgress();

        try {
            JSONObject jsonObject = MainApiHelper.changeRequestStatus(itemId, requestStatus ,requestRole , userId );

            MainApi.changeRequestStatus(jsonObject, new NetworkResponseListener<PostData>() {

                @Override
                public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {
                    if (isDetachView()) return;
                    view.dismissLoadingProgress();
                    PostData userNetworkData = (PostData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;

                    if (errorCode == 1) {
                        switch (requestStatus){
                            case 1:
                                view.showToast("Request Approved");
                                break;
                            case 2:
                                view.showToast("Request Rejected");
                                break;
                            case 3:
                                view.showToast("Request Defered");
                                break;
                        }
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
}
