package com.a700apps.techmart.ui.screens.listgroup;

import com.a700apps.techmart.data.model.CategoryData;
import com.a700apps.techmart.data.model.CategoryGroups;
import com.a700apps.techmart.data.model.CategoryGroupsData;
import com.a700apps.techmart.data.model.JoinGroupData;
import com.a700apps.techmart.data.model.UserData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.ui.screens.category.CategoryView;
import com.a700apps.techmart.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samir salah on 9/9/2017.
 */

public class GroupPresenter extends MainPresenter<GroubView> implements NetworkResponseListener<CategoryGroupsData>{



    public void getGroupCategory(String ID,String UserId) {

        view.showProgress();

        try {
            JSONObject registerBody = MainApiHelper.getCategoryGroups(ID,UserId);
            MainApi.getGroupCategory(registerBody, this);
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissProgress();
        }
    }



    @Override
    public void networkOperationSuccess(NetworkResponse<CategoryGroupsData> networkResponse) {

        if (isDetachView()) return;
        view.dismissProgress();
        CategoryGroupsData userNetworkData = (CategoryGroupsData) networkResponse.data;
        int errorCode = userNetworkData.ISResultHasData;
        view.updateUi(userNetworkData.CategoryGroups);

    }

    @Override
    public void networkOperationFail(Throwable throwable) {

    }
}
