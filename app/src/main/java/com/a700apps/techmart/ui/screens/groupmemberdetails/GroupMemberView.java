package com.a700apps.techmart.ui.screens.groupmemberdetails;

import com.a700apps.techmart.data.model.GroupUsersData;
import com.a700apps.techmart.data.model.MyProfile;

/**
 * Created by samir salah on 9/13/2017.
 */

public interface GroupMemberView {
    void showLoadingProgress();

    void dismissLoadingProgress();
    void updateUi(GroupUsersData.ResultEntity groupusers);
}
