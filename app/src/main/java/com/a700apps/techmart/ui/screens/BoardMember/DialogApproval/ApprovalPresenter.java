package com.a700apps.techmart.ui.screens.BoardMember.DialogApproval;

import com.a700apps.techmart.data.model.NotificationDataLike;
import com.a700apps.techmart.data.model.PostData;
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

public class ApprovalPresenter extends MainPresenter<approvalView> {


    void manageTimeLineItem(int itemId, int type , String userId , final int status) {

        view.showLoadingProgress();

        try {
            JSONObject jsonObject = MainApiHelper.manageTimelineItem(itemId, status , userId ,type);

            MainApi.manageTimelineItem(jsonObject, new NetworkResponseListener<PostData>() {

                @Override
                public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {
                    if (isDetachView()) return;
                    view.dismissLoadingProgress();
                    PostData userNetworkData = (PostData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;

                    if (errorCode == 1) {
                        switch (status){
                            case 1:
                                view.showToast("Item Approved");
                                break;
                            case 2:
                                view.showToast("Item Rejected");
                                break;
                            case 3:
                                view.showToast("Item Defered");
                                break;


                        }
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
