package com.a700apps.techmart.ui.screens.BoardMember.timeline;

import com.a700apps.techmart.data.model.StatisticModel;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by khaled.badawy on 10/10/2017.
 */

public class StatisticPresenter extends MainPresenter<StatisticalView> {


    public void getStatistic(String userId, int groupid, int days) {

        view.showLoadingProgress();

        try {
            JSONObject jsonObject = MainApiHelper.getStatistics(userId, groupid, days);

            MainApi.getGroupStatistics(jsonObject, new NetworkResponseListener<StatisticModel>() {

                @Override
                public void networkOperationSuccess(NetworkResponse<StatisticModel> networkResponse) {
                    if (isDetachView()) return;
                    view.dismissLoadingProgress();
                    StatisticModel userNetworkData = (StatisticModel) networkResponse.data;
                    int errorCode = userNetworkData.getISResultHasData();

                    if (errorCode == 1) {
                        view.updateUi(userNetworkData);
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    view.dismissLoadingProgress();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }

    }
}
