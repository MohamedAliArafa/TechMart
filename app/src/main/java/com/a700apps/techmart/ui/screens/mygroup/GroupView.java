package com.a700apps.techmart.ui.screens.mygroup;

import com.a700apps.techmart.data.model.CategoryData;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.model.UserGroupData;

/**
 * Created by samir salah on 9/11/2017.
 */

public interface GroupView {

    void showProgress();

    void dismissProgress();

    void updateUi(UserGroupData data);
    void updateRelativeUi(UserGroupData data);
}
