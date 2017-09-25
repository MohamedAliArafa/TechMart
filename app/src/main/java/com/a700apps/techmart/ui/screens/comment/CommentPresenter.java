package com.a700apps.techmart.ui.screens.comment;

import android.content.Context;

import com.a700apps.techmart.data.model.CommentData;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.model.UserData;
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
 * Created by samir salah on 9/13/2017.
 */

public class CommentPresenter extends MainPresenter<commentView> {

    Context mContext;

    void comment(int postId, Context context) {
        mContext = context;
//        view.showLoadingProgress();

        try {
            JSONObject registerBody = MainApiHelper.getPostComment(postId);
            MainApi.getPostComment(registerBody, new NetworkResponseListener<CommentData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<CommentData> networkResponse) {

                    view.dismissLoadingProgress();
                    CommentData userNetworkData = (CommentData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;

                    if (errorCode == 1) {
                        view.UpdateUi(userNetworkData.comment);
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
//            view.dismissLoadingProgress();
        }

    }

    void postComment(String UserID, int PostID, String Comment){

        try {
            JSONObject registerBody = MainApiHelper.postComment(UserID,PostID,Comment);
            MainApi.sendComment(registerBody, new NetworkResponseListener<PostData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {

                    view.dismissLoadingProgress();
                    PostData userNetworkData = (PostData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;

                    if (errorCode == 1) {
                        view.Update();
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
//            view.dismissLoadingProgress();
        }
    }


}
