package com.a700apps.techmart.ui.screens.setting;

import android.util.Log;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.TechMartApp;
import com.a700apps.techmart.data.model.ChangePasswordData;
import com.a700apps.techmart.data.model.ChangePhotoData;
import com.a700apps.techmart.data.model.ChangeReciveNotifcationData;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.network.ApiInterface;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.MainPresenter;
import com.a700apps.techmart.utils.ApiClient;
import com.a700apps.techmart.utils.PreferenceHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by samir salah on 8/16/2017.
 */

public class SettingPresenter extends MainPresenter<SettingView> implements NetworkResponseListener<ChangeReciveNotifcationData> {


    boolean enabled = false ;
    String pathServer ;

    void ChangeNotificationStatus(String userId, boolean enabled){
        try {
            view.showLoadingProgress();
            JSONObject registerBody = MainApiHelper.ChangeRecieveNotification(userId, enabled);
            MainApi.ChangeReciveNotifcationSetting(registerBody, this);
            this.enabled = enabled;
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }
    }

    void changePassword(String userId , String oldPassword , String newPassword){
        try {
            view.showLoadingProgress();
            JSONObject registerBody = MainApiHelper.ChangeLoginPassword(userId, oldPassword , newPassword);
            MainApi.ChangeLoginPassword(registerBody, new NetworkResponseListener<ChangeReciveNotifcationData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<ChangeReciveNotifcationData> networkResponse) {
                    view.dismissLoadingProgress();
                    if ( !networkResponse.data.getResult().getSuccess()){
                        networkOperationFail(new Throwable(networkResponse.data.getResult().getMessage()));
//                        view.showErrorDialog(R.string.error_happened); //networkResponse.data.getResult().getMessage()
                    }else {
                        view.showToast("Password changed successfully");
                        view.emptyViews();
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    view.showToast(""+throwable.getMessage());
                    view.dismissLoadingProgress();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            view.dismissLoadingProgress();
        }
    }

    void ChangeProfilePicture(String userId , String imageName){
        try {
            view.showLoadingProgress();
            pathServer = imageName;

            JSONObject registerBody = MainApiHelper.ChangeProfilePicture(userId, imageName);
            MainApi.ChangeProfilePhoto(registerBody, new NetworkResponseListener<ChangeReciveNotifcationData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<ChangeReciveNotifcationData> networkResponse) {
                    view.dismissLoadingProgress();
                    if ( !networkResponse.data.getResult().getSuccess()){
                        networkOperationFail(new Throwable(networkResponse.data.getResult().getMessage()));
                        view.showErrorDialog(R.string.error_happened); //networkResponse.data.getResult().getMessage()
                    }else {
                        view.showToast("Image changed successfully");
                        view.saveNewPic("/UploadedImages/"+pathServer);
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

    @Override
    public void networkOperationSuccess(NetworkResponse<ChangeReciveNotifcationData> networkResponse) {
        view.dismissLoadingProgress();

        if ( !networkResponse.data.getResult().getSuccess()){
            networkOperationFail(new Throwable(networkResponse.data.getResult().getMessage()));
            view.showErrorDialog(R.string.error_happened); //networkResponse.data.getResult().getMessage()
        }else {
            view.addNotificationStatus(enabled);
        }
    }

    @Override
    public void networkOperationFail(Throwable throwable) {
        view.showToast(throwable.getMessage());
        view.dismissLoadingProgress();
    }
}
