package com.a700apps.techmart.ui.screens.creatpost;

import android.content.Context;

import com.a700apps.techmart.data.model.CategoryData;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.model.UserData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.ui.screens.comment.commentView;
import com.a700apps.techmart.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir salah on 9/13/2017.
 */

public class PostPresenter extends MainPresenter<PostView> implements NetworkResponseListener<PostData>{
    Context mContext;
    void sendPost(int GroupID, String CreatedBY, String Title, String Descr, String Image, String MediaFile, String CreationDate, boolean IsPublic, Context context) {

        mContext = context;
        view.showLoadingProgress();

        try {
            JSONObject registerBody = MainApiHelper.addPost(GroupID, CreatedBY, Title, Descr, Image, MediaFile, CreationDate,
                    IsPublic);
            MainApi.sendPost(registerBody, this);
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }

    }
    @Override
    public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {
        if (isDetachView()) return;
        view.dismissLoadingProgress();
        PostData userNetworkData = (PostData) networkResponse.data;
        int errorCode = userNetworkData.ISResultHasData;

        if (errorCode == 1) {
            view.UpdateUi(userNetworkData.postData);
        }


    }

    @Override
    public void networkOperationFail(Throwable throwable) {

    }
}
