package com.a700apps.techmart.ui.screens.register;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.a700apps.techmart.R;
import com.a700apps.techmart.TechMartApp;
import com.a700apps.techmart.data.model.User;
import com.a700apps.techmart.data.model.UserData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.utils.ATCPrefManager;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.Validator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir salah on 8/16/2017.
 */

public class RegisterPresenter extends MainPresenter<RegisterView> {
Context mContext;

    void register(String fullName, String password, String email, String mobile, String photo, String company, String position, Context context) {

        mContext = context;
        view.showLoadingProgress();

        String deviceId = AppUtils.getDeviceId();
        String firebaseToken = AppUtils.getFirebaseToken();
        try {
            JSONObject registerBody = MainApiHelper.createRegisterBody(fullName, password, email, mobile, photo, company, position,
                    deviceId, firebaseToken);
            MainApi.registerUser(registerBody, new NetworkResponseListener<UserData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<UserData> networkResponse) {
                    if (isDetachView()) return;
                    view.dismissLoadingProgress();
                    UserData userNetworkData = (UserData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;

                    saveUserData(userNetworkData.user);
                    ATCPrefManager.setIsUserLoggedIn(mContext, true);
                    if (errorCode == 1) {
                        view.openCouncilActivity();
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    view.dismissLoadingProgress();
                    view.showErrorDialog(R.string.check_internet);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }

    }


    private void saveUserData(User user) {

        PreferenceHelper.saveUser(mContext, user);
    }




    void registerLinkedin(String fullName, String email,String LinkedinId,String company,
                          String position,String photo, Context context) {

        mContext = context;
        view.showLoadingProgress();

        String deviceId = AppUtils.getDeviceId();
        String firebaseToken = AppUtils.getFirebaseToken();
        try {
            JSONObject registerBody = MainApiHelper.createRegisterLinked( fullName,  email, LinkedinId,company,
                     position,
                     deviceId,  firebaseToken, photo);
            MainApi.registerLinkedUser(registerBody, new NetworkResponseListener<UserData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<UserData> networkResponse) {
                    if (isDetachView()) return;
                    view.dismissLoadingProgress();
                    UserData userNetworkData = (UserData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;


                    if (errorCode == 1) {
                        saveUserData(userNetworkData.user);
                        ATCPrefManager.setIsUserLoggedIn(mContext, true);
                        view.openCouncilActivity();
                    }else {
                        view.showErrorDialog(R.string.error_happened_login);
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    view.dismissLoadingProgress();
                    view.showErrorDialog(R.string.check_internet);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }

    }

}
