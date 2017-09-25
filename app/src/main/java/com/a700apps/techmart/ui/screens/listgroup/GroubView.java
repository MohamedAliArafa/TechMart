package com.a700apps.techmart.ui.screens.listgroup;

import com.a700apps.techmart.data.model.CategoryGroups;
import com.a700apps.techmart.data.model.CategoryGroupsData;

import java.util.List;

/**
 * Created by samir salah on 9/9/2017.
 */

public interface GroubView {

    void showProgress();

    void dismissProgress();
    void updateUi(List<CategoryGroups> CategoryGroups);

}
