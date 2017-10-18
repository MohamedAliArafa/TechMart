package com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembermembers;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.a700apps.techmart.data.model.AllGroupUsers;
import com.a700apps.techmart.data.model.PostData;
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
 * Created by samir.salah on 10/11/2017.
 */

public class BoardRemovePresenter extends MainPresenter<BoardRemoveView> {

    Context mContext;
    Dialog dialogsLoading;

    public void removeMember(String GroupID, String BoardMemberUserID, Context context) {
        mContext = context;
        dialogsLoading = new loadingDialog().showDialog(context);
//        view.showProgress();

        try {
            JSONObject registerBody = MainApiHelper.removeMember(GroupID, BoardMemberUserID, PreferenceHelper.getUserId(mContext));
            Log.e("DATA", registerBody.toString());
            MainApi.removeMember(registerBody, new NetworkResponseListener<PostData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {
                    if (isDetachView()) return;
                    dialogsLoading.dismiss();
                    PostData userNetworkData = networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;
                    if (errorCode == 1) {
                        view.update();
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    Log.e("error", throwable.toString());
                    dialogsLoading.dismiss();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
//            view.dismissProgress();
            dialogsLoading.dismiss();
        }

    }
    public void getMyGroupUsers(String GroupID,Context context) {
        mContext = context;
        dialogsLoading = new loadingDialog().showDialog(context);
//        view.showProgress();

        try {
            JSONObject registerBody = MainApiHelper.getAllGroupUsers(GroupID,PreferenceHelper.getUserId(mContext));
            Log.e("DATA", registerBody.toString());
            MainApi.getAllGroupUsers(registerBody, new NetworkResponseListener<AllGroupUsers>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<AllGroupUsers> networkResponse) {
                    if (isDetachView()) return;
                    dialogsLoading.dismiss();
                    AllGroupUsers userNetworkData = networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;
                    if (errorCode == 1) {
                        view.updateUi(userNetworkData);
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    Log.e("error", throwable.toString());
                    dialogsLoading.dismiss();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
//            view.dismissProgress();
            dialogsLoading.dismiss();
        }

    }
}
