package com.a700apps.techmart.ui.screens.login;

import android.app.Dialog;
import android.content.Context;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.UserData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.utils.ATCPrefManager;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir salah on 8/14/2017.
 */

public class LoginPresenter extends MainPresenter<LoginView> implements NetworkResponseListener<UserData> {

    private Context mContext;
    Dialog dialogsLoading;

    void login(String email, String password, Context context) {

        mContext = context;
        // check internet connection.
//        context = TechMartApp.getAppContext();
        dialogsLoading = new loadingDialog().showDialog(context);

        String deviceId = AppUtils.getDeviceId();
        String firebaseToken = AppUtils.getFirebaseToken();
        view.showProgress();
        login(email, password, deviceId, firebaseToken);
    }

    private void login(String email, String password, String deviceId, String pnToken) {
        try {
            JSONObject body = MainApiHelper.createLoginBody(email, password, deviceId, pnToken);
            MainApi.loginUser(body, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void networkOperationSuccess(NetworkResponse<UserData> networkResponse) {
        if (isDetachView()) return;
        dialogsLoading.dismiss();
        UserData userNetworkData = networkResponse.data;
        int errorCode = userNetworkData.ISResultHasData;

        if (errorCode == 1) {
            PreferenceHelper.saveUser(mContext, userNetworkData.user);
            ATCPrefManager.setIsUserLoggedIn(mContext, true);
            view.openHomeActivity();
        } else {

            view.showErrorDialog(R.string.error_login);

        }
    }

    @Override
    public void networkOperationFail(Throwable throwable) {
        dialogsLoading.dismiss();
        view.showErrorDialog(R.string.check_internet);

    }
}
