package com.a700apps.techmart.ui.screens.BoardMember.EditTimeLine;

import android.content.Context;

import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.model.NotificationDataLike;
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

public class EdittPresenter extends MainPresenter<EditView> {


    void getTimelineItem(int itemId, int type , String userId) {

        view.showLoadingProgress();

        try {
            JSONObject jsonObject = MainApiHelper.getTimeLineById(itemId, type, userId);

            MainApi.getTimeLineById(jsonObject, new NetworkResponseListener<NotificationDataLike>() {

                @Override
                public void networkOperationSuccess(NetworkResponse<NotificationDataLike> networkResponse) {
                    if (isDetachView()) return;
                    view.dismissLoadingProgress();
                    NotificationDataLike userNetworkData = (NotificationDataLike) networkResponse.data;
                    int errorCode = userNetworkData.getISResultHasData();

                    if (errorCode == 1) {
                        view.UpdateUi(userNetworkData);
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




    void editTimeLineItem(String groupId, int itemId , boolean ispublic  , String title , String description ,
                          String image , String mediaFile, String userId , final int type ) {

        view.showLoadingProgress();

        try {
            JSONObject jsonObject = MainApiHelper.editTimelineItem(groupId , itemId,ispublic ,title , description,
                    image,mediaFile, userId, type);

            MainApi.editTimeLineItem(jsonObject, new NetworkResponseListener<PostData>() {

                @Override
                public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {
                    if (isDetachView()) return;

                    view.dismissLoadingProgress();
                    PostData userNetworkData = (PostData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;

                    if (errorCode == 1) {
                        view.dismissLoadingProgress();
                        if (type ==1){
                            view.showToast(" Event updated successfully ");
                        }else {
                            view.showToast(" Post updated successfully ");
                        }
                        view.finishActivity();
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
