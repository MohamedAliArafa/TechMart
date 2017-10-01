package com.a700apps.techmart.ui.screens.profile;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.a700apps.techmart.data.model.MyProfileData;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.utils.loadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir salah on 9/13/2017.
 */

public class MemberPresenter extends MainPresenter<ProfileView>  {


    Context mContext;

    void profileData(String UserId,String RelId,int GroupId, Context context) {

        mContext = context;
//        view.showLoadingProgress();
        final Dialog dialogsLoading = new loadingDialog().showDialog(context);


        try {
            JSONObject registerBody = MainApiHelper.getMemberProfile(UserId,RelId,GroupId);
            MainApi.getMyProfileMember(registerBody, new NetworkResponseListener<MyProfileData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<MyProfileData> networkResponse) {
                    if (isDetachView()) return;
                    dialogsLoading.dismiss();
//                    view.dismissLoadingProgress();
                    MyProfileData userNetworkData = (MyProfileData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;

                    if (errorCode == 1) {
                        view.updateUi(userNetworkData.user);
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

    void sendFollow(String RelativeID, String UserID,final String follow){
        view.showLoadingProgress();

        try {
            JSONObject registerBody = MainApiHelper.follow(RelativeID,UserID,follow);
            MainApi.sendFollow(registerBody, new NetworkResponseListener<PostData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {
                    if (isDetachView()) return;
                    view.dismissLoadingProgress();
                    PostData userNetworkData = (PostData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;

                    if (follow.equals("true")) {
                        view.updateUiFollow(userNetworkData.postData);
                    }else {
                        view.updateUiUnFollow(userNetworkData.postData);
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    Log.e("error",throwable.getMessage());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }
    }

    void sendConnect(String RelativeID, String UserID, final String connect){
        view.showLoadingProgress();

        try {
            JSONObject registerBody = MainApiHelper.connect(RelativeID,UserID,connect);
            MainApi.sendConnect(registerBody, new NetworkResponseListener<PostData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {
                    if (isDetachView()) return;
                    view.dismissLoadingProgress();
                    PostData userNetworkData = (PostData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;

                    if (connect .equals("true")) {
                        view.updateUiConnect(userNetworkData.postData);
                    }else {
                        view.updateUiDisConnect(userNetworkData.postData);
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
