package com.a700apps.techmart.ui.screens.userlikes;

import android.content.Context;

import com.a700apps.techmart.data.model.UserData;
import com.a700apps.techmart.data.model.UserLikeData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.ui.screens.register.RegisterView;
import com.a700apps.techmart.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir salah on 9/15/2017.
 */

public class UserLikesPresenter extends MainPresenter<UserLikeView> implements NetworkResponseListener<UserLikeData> {

    Context mContext;

    void getUserLikes(int postId, Context context) {


        mContext = context;
        view.showLoadingProgress();

        try {
            JSONObject registerBody = MainApiHelper.getUserLikePost(postId);
            MainApi.getUserLike(registerBody, this);
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }

    }

    @Override
    public void networkOperationSuccess(NetworkResponse<UserLikeData> networkResponse) {
        if (isDetachView()) return;
        view.dismissLoadingProgress();
        UserLikeData userNetworkData = (UserLikeData) networkResponse.data;
        int errorCode = userNetworkData.ISResultHasData;

        if (errorCode == 1) {
            view.update(userNetworkData.userLikes);
        }

    }

    @Override
    public void networkOperationFail(Throwable throwable) {

    }
}
