package com.a700apps.techmart.ui.screens.meetingone;

import android.content.Context;

import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.model.UserLikeData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.ui.screens.userlikes.UserLikeView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir.salah on 9/17/2017.
 */

public class OnetoOnePresenter extends MainPresenter<MeetingoneView>  {

    Context mContext;

    void getOneToOne(String UserID,int GroupID, Context context) {


        mContext = context;
        view.showLoadingProgress();

        try {
            JSONObject registerBody = MainApiHelper.getMyConnectinGroup(UserID,GroupID);
            MainApi.getMyConnectionListGroup(registerBody, new NetworkResponseListener<MyConnectionList>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<MyConnectionList> networkResponse) {
                    if (isDetachView()) return;
                    view.dismissLoadingProgress();
                    MyConnectionList userNetworkData = (MyConnectionList) networkResponse.data;
                    int errorCode = userNetworkData.getISResultHasData();

                    if (errorCode == 1) {
                        view.update(userNetworkData.getResult());
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }

    }


    void sendEvent(String StartTime,String EndTime,double longtude,double lat,String location,int GroupID, String CreatedBY, String Title, String Descr,String StartDate, String EndDate,
                   String OneToOnPartener,boolean IsOneToOneMeeting,  String Image, String MediaFile, String CreationDate, boolean IsPublic, Context context) {

        mContext = context;
        view.showLoadingProgress();

        try {
            JSONObject registerBody = MainApiHelper.addEvent(StartTime, EndTime,longtude,lat,location,GroupID, CreatedBY, Title, Descr,StartDate,EndDate,OneToOnPartener,IsOneToOneMeeting, Image, MediaFile, CreationDate,
                    IsPublic);
            MainApi.sendEvent(registerBody, new NetworkResponseListener<PostData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {
                    if (isDetachView()) return;
                    view.dismissLoadingProgress();
                    PostData userNetworkData = (PostData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;

                    if (errorCode == 1) {
//                        view.UpdateUi(userNetworkData.postData);
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }

    }
}
