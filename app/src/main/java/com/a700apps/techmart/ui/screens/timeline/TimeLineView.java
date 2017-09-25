package com.a700apps.techmart.ui.screens.timeline;

import com.a700apps.techmart.data.model.CategoryGroups;
import com.a700apps.techmart.data.model.TimeLine;
import com.a700apps.techmart.data.model.TimeLineData;

import java.util.List;

/**
 * Created by samir salah on 9/10/2017.
 */

public interface TimeLineView {

    void showLoadingProgress();

    void dismissLoadingProgress();

    void updateUi(List<TimeLineData.ResultEntity> TimelineList);

    void showErrorDialog(int error);
}
