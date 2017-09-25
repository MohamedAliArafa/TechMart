package com.a700apps.techmart.ui.screens.category;

import com.a700apps.techmart.data.model.Category;
import com.a700apps.techmart.data.model.CategoryData;
import com.a700apps.techmart.data.model.UserData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir salah on 9/8/2017.
 */

public class CategoryPresenter extends MainPresenter<CategoryView> implements NetworkResponseListener<CategoryData> {
  public   void getCategory() {

        view.showProgress();
        category();
    }


    private void category() {
        MainApi.getCategoryGroup(this);
    }


    @Override
    public void networkOperationSuccess(NetworkResponse<CategoryData> networkResponse) {
        if(isDetachView()) return;
        view.dismissProgress();
        CategoryData userNetworkData = networkResponse.data;
        int errorCode = userNetworkData.ISResultHasData;
        if (errorCode==1 ){

                view.updateUi(userNetworkData);

        }

    }

    @Override
    public void networkOperationFail(Throwable throwable) {

    }
}
