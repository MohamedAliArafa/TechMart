package com.a700apps.techmart.ui.screens.meeting;

import com.a700apps.techmart.data.model.AllSchedualList;

import java.util.List;

/**
 * Created by halima.reda on 9/17/2017.
 */

public interface MettingView {

    void drawUiData(List<AllSchedualList.ResultEntity> schedualList);

    void showProgress();

    void dismissProgress();

    void noEvent();
}
