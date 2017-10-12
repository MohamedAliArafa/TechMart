package com.a700apps.techmart.ui.screens.managegroupboardmember;

import com.a700apps.techmart.data.model.UserGroupData;

/**
 * Created by samir.salah on 10/10/2017.
 */

public interface manageGroupView {

    void showProgress();

    void dismissProgress();

    void updateUi(UserGroupData data);

}
