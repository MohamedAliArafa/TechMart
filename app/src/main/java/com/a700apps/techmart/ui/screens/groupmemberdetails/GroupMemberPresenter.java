package com.a700apps.techmart.ui.screens.groupmemberdetails;

import android.app.Dialog;
import android.content.Context;

import com.a700apps.techmart.data.model.GroupUsersData;
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

public class GroupMemberPresenter extends MainPresenter<GroupMemberView> implements NetworkResponseListener<GroupUsersData> {

    Context mContext;
    Dialog dialogsLoading;
    void GroupUsers(int  id, String UserID, Context context) {


        mContext = context;
        dialogsLoading = new loadingDialog().showDialog(context);
//        view.showLoadingProgress();

        try {
            JSONObject registerBody = MainApiHelper.getGroupUsers(id, UserID);
            MainApi.getGroupUsers(registerBody, this);
        } catch (JSONException e) {
            e.printStackTrace();
            dialogsLoading.dismiss();
//            view.dismissLoadingProgress();
        }

    }

    @Override
    public void networkOperationSuccess(NetworkResponse<GroupUsersData> networkResponse) {

        if (isDetachView()) return;
//        view.dismissLoadingProgress();
        dialogsLoading.dismiss();
        GroupUsersData userNetworkData = (GroupUsersData) networkResponse.data;
        int errorCode = userNetworkData.getISResultHasData();

        if (errorCode == 1) {
            view.updateUi(userNetworkData.getResult());
        }
    }

    @Override
    public void networkOperationFail(Throwable throwable) {
        dialogsLoading.dismiss();
    }
}
