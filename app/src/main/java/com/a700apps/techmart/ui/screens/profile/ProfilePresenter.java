package com.a700apps.techmart.ui.screens.profile;

import android.app.Dialog;
import android.content.Context;

import com.a700apps.techmart.data.model.MyProfileData;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.model.UserData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.ui.screens.register.RegisterView;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.loadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir salah on 9/13/2017.
 */

public class ProfilePresenter   extends MainPresenter<ProfileView> {


    Context mContext;
    Dialog dialogsLoading;

    void profileData(String UserId, Context context) {
        mContext = context;
        view.showLoadingProgress();

        try {
            JSONObject registerBody = MainApiHelper.getMyProfile(UserId);
            MainApi.getMyProfile(registerBody, new NetworkResponseListener<MyProfileData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<MyProfileData> networkResponse) {
                    if (isDetachView()) return;
                    view.dismissLoadingProgress();
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
    //

    void updateProfileData(Context context , String UserID, String Name,String LinkedinProfile,
                           String photo,String company,String position,String phone) {
//        mContext = context;
        view.showLoadingProgress();
        dialogsLoading = new loadingDialog().showDialog(context);
        try {
            JSONObject registerBody = MainApiHelper.saveProfileData( UserID,  Name, LinkedinProfile,
                     photo, company, position, phone);
            MainApi.saveProfile(registerBody, new NetworkResponseListener<PostData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {
                    if (isDetachView()) return;
                    view.dismissLoadingProgress();
                    PostData userNetworkData = (PostData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;

                    if (errorCode == 1) {
                        dialogsLoading.dismiss();
                        view.updateUiUpdate(userNetworkData.postData);
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    dialogsLoading.dismiss();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            dialogsLoading.dismiss();
//            view.dismissLoadingProgress();
        }
    }

}
