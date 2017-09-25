package com.a700apps.techmart.ui.screens.mygroup;

import android.content.Context;
import android.util.Log;

import com.a700apps.techmart.data.model.CategoryData;
import com.a700apps.techmart.data.model.UserGroupData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.ui.screens.category.CategoryView;
import com.a700apps.techmart.utils.PreferenceHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir salah on 9/11/2017.
 */

public class MyGroupPresenter extends MainPresenter<GroupView> implements NetworkResponseListener<UserGroupData> {
    Context mContext;

    void getMyGroup(Context context) {
        mContext = context;
        view.showProgress();

        try {
            JSONObject registerBody = MainApiHelper.getUserGroup(PreferenceHelper.getUserId(mContext));
            MainApi.getUserGroup(registerBody, this);
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissProgress();
        }

    }

    @Override
    public void networkOperationSuccess(NetworkResponse<UserGroupData> networkResponse) {

        if(isDetachView()) return;
        view.dismissProgress();
        UserGroupData userNetworkData = networkResponse.data;
        int errorCode = userNetworkData.ISResultHasData;
        if (errorCode==1 ){
            view.updateUi(userNetworkData);
        }
    }

    @Override
    public void networkOperationFail(Throwable throwable) {

        Log.e("error",throwable.toString());
    }
}
