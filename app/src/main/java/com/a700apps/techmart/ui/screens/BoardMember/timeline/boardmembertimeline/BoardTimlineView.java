package com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembertimeline;

import com.a700apps.techmart.data.model.GroupTimeLineData;

import java.util.List;

/**
 * Created by samir.salah on 10/10/2017.
 */

public interface BoardTimlineView {

    void showLoadingProgress();

    void dismissLoadingProgress();

    void updateUi(List<GroupTimeLineData.ResultEntity> TimelineList);

}