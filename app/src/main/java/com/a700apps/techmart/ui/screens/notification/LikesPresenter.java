package com.a700apps.techmart.ui.screens.notification;

import com.a700apps.techmart.data.model.NoficationData;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;

/**
 * Created by khaled.badawy on 9/28/2017.
 */

public class LikesPresenter extends MainPresenter<LikesView> implements NetworkResponseListener<NoficationData> {



    private void loadData(){

    }
    @Override
    public void networkOperationSuccess(NetworkResponse<NoficationData> networkResponse) {

    }

    @Override
    public void networkOperationFail(Throwable throwable) {

    }
}
