package com.a700apps.techmart.ui.screens.profile;

import com.a700apps.techmart.data.model.MyProfile;
import com.a700apps.techmart.data.model.MyProfileData;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.model.post;

import java.util.List;

/**
 * Created by samir salah on 9/13/2017.
 */

public interface ProfileView {

    void showLoadingProgress();
    void dismissLoadingProgress();
    void updateUi(MyProfile MyProfile);
    void updateUiFollow(post success);
    void updateUiUnFollow(post success);
    void updateUiConnect(post success);
    void updateUiDisConnect(post success);
    void updateUiUpdate(post success);

}
