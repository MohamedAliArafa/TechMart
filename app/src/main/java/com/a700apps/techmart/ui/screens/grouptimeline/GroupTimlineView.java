package com.a700apps.techmart.ui.screens.grouptimeline;

import com.a700apps.techmart.data.model.GroupTimeLine;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.model.TimeLineData;

import java.util.List;

/**
 * Created by samir salah on 9/13/2017.
 */

public interface GroupTimlineView {

    void showLoadingProgress();

    void dismissLoadingProgress();

    void updateUi(List<TimeLineData.ResultEntity> TimelineList);
}
