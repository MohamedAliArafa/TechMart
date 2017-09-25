package com.a700apps.techmart.ui.screens.category;

import com.a700apps.techmart.data.model.CategoryData;

/**
 * Created by samir salah on 9/8/2017.
 */

public interface CategoryView {

    void showProgress();

    void dismissProgress();

    void updateUi(CategoryData data);


}
