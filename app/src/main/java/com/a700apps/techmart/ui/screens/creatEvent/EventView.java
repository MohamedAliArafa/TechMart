package com.a700apps.techmart.ui.screens.creatEvent;

import com.a700apps.techmart.data.model.post;

/**
 * Created by samir salah on 9/13/2017.
 */

public interface EventView {

    void showLoadingProgress();

    void dismissLoadingProgress();

    void UpdateUi(post post);
}
