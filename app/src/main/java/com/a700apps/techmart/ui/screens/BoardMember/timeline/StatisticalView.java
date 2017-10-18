package com.a700apps.techmart.ui.screens.BoardMember.timeline;

import com.a700apps.techmart.data.model.StatisticModel;

/**
 * Created by khaled.badawy on 10/10/2017.
 */

public interface StatisticalView {

    void showLoadingProgress();

    void dismissLoadingProgress();

    void  showToast(String message);

    void updateUi(StatisticModel model);
}
