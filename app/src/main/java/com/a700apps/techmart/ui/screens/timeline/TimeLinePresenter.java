package com.a700apps.techmart.ui.screens.timeline;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.TimeLineData;
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
 * Created by samir salah on 9/10/2017.
 */

public class TimeLinePresenter extends MainPresenter<TimeLineView>  {
    Dialog dialogsLoading;
    Context mContext;
    void getTimeline(String userId, String type, Context context) {
        mContext = context;

     dialogsLoading = new loadingDialog().showDialog(context);
        try {
            JSONObject registerBody = MainApiHelper.getTimeLine(userId, type);
            MainApi.getTimeLine(registerBody, new NetworkResponseListener<TimeLineData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<TimeLineData> networkResponse) {
                    dialogsLoading.dismiss();
                    if (isDetachView()) return;
                    TimeLineData userNetworkData = (TimeLineData) networkResponse.data;
                    int errorCode = userNetworkData.getISResultHasData();
                    view.updateUi(userNetworkData.getResult());
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    dialogsLoading.dismiss();
//                    view.showErrorDialog(R.string.check_internet);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }

    }

    void GetRelativeEventByUserID(String RelativeID, String UserID , Context context){
        dialogsLoading = new loadingDialog().showDialog(context);
        try {
            JSONObject registerBody = MainApiHelper.GetRelativeEventByUserID(RelativeID, UserID);
            MainApi.GetRelativeEventByUserID(registerBody, new NetworkResponseListener<TimeLineData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<TimeLineData> networkResponse) {
                    dialogsLoading.dismiss();
                    if (isDetachView()) return;

                    TimeLineData userNetworkData = (TimeLineData) networkResponse.data;
                    int errorCode = userNetworkData.getISResultHasData();
                    view.updateUi(userNetworkData.getResult());
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    view.showErrorDialog(R.string.error_happened);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }
    }




}
