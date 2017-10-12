package com.a700apps.techmart.ui.screens.mygroup;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.a700apps.techmart.data.model.UserGroupData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir salah on 9/11/2017.
 */

public class MyGroupPresenter extends MainPresenter<GroupView> {
    Context mContext;
    Dialog dialogsLoading;

    void getMyGroup(Context context) {
        mContext = context;
        dialogsLoading = new loadingDialog().showDialog(context);
//        view.showProgress();

        try {
            JSONObject registerBody = MainApiHelper.getUserGroup(PreferenceHelper.getUserId(mContext));
            Log.e("DATA", registerBody.toString());
            MainApi.getUserGroup(registerBody, new NetworkResponseListener<UserGroupData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<UserGroupData> networkResponse) {
                    if (isDetachView()) return;
                    dialogsLoading.dismiss();
                    UserGroupData userNetworkData = networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;
                    if (errorCode == 1) {
                        view.updateUi(userNetworkData);
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    dialogsLoading.dismiss();
                    Log.e("error", throwable.toString());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
//            view.dismissProgress();
            dialogsLoading.dismiss();
        }

    }


    void GetRelativeGroupByUserID(Context context, String relativeId) {
        mContext = context;
//        view.showProgress();
        dialogsLoading = new loadingDialog().showDialog(context);
        try {
            JSONObject registerBody = MainApiHelper.GetRelativeGroupByUserID(relativeId, PreferenceHelper.getUserId(mContext));
            Log.e("DATA", registerBody.toString());
            MainApi.GetRelativeGroupByUserID(registerBody, new NetworkResponseListener<UserGroupData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<UserGroupData> networkResponse) {
                    if (isDetachView()) return;
//                    view.dismissProgress();
                    dialogsLoading.dismiss();
                    UserGroupData userNetworkData = networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;
                    if (errorCode == 1) {
                        view.updateRelativeUi(userNetworkData);
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    Log.e("error", throwable.toString());

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissProgress();
        }

    }


}
