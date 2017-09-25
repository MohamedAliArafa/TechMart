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
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.Validator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir salah on 8/16/2017.
 */

public class RegisterPresenter extends MainPresenter<RegisterView> implements NetworkResponseListener<UserData> {
Context mContext;

    void register(String fullName, String password, String email, String mobile, String photo, String company, String position, Context context) {


        mContext = context;
        view.showLoadingProgress();

        String deviceId = AppUtils.getDeviceId();
        String firebaseToken = AppUtils.getFirebaseToken();
        try {
            JSONObject registerBody = MainApiHelper.createRegisterBody(fullName, password, email, mobile, photo, company, position,
                    deviceId, firebaseToken);
            MainApi.registerUser(registerBody, this);
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }

    }

    @Override
    public void networkOperationSuccess(NetworkResponse<UserData> networkResponse) {
        if (isDetachView()) return;
        view.dismissLoadingProgress();
        UserData userNetworkData = (UserData) networkResponse.data;
        int errorCode = userNetworkData.ISResultHasData;

        saveUserData(userNetworkData.user);
        if (errorCode == 1) {
            view.openCouncilActivity();
        }

    }

    private void saveUserData(User user) {

        PreferenceHelper.saveUser(mContext, user);
    }

    @Override
    public void networkOperationFail(Throwable throwable) {
        view.dismissLoadingProgress();
        view.showErrorDialog(R.string.check_internet);
    }
}
