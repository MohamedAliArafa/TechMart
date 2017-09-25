package com.a700apps.techmart.ui.screens.timelinedetails;

import android.content.Context;

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
 * Created by samir.salah on 9/17/2017.
 */

public class DetailsPresenter extends MainPresenter<DetailsView>  {
    Context mContext;

    void attendee(String EventID, String UserID, String IsGoin, Context context) {

        mContext = context;
        view.showProgress();

        try {
            JSONObject registerBody = MainApiHelper.sendAttendee(EventID, UserID, IsGoin );
            MainApi.sendAttendee(registerBody, new NetworkResponseListener<PostData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {
                    view.hideProgress();
                    if (isDetachView()) return;
                    PostData userNetworkData = (PostData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;

                    if (errorCode == 1) {
                        view.updateUi();
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            view.hideProgress();
        }

    }




}
