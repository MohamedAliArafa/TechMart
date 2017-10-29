package com.a700apps.techmart.ui.screens.BoardMember.DialogApproval;

import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.model.NotificationDataLike;

import java.util.List;

/**
 * Created by khaled.badawy on 10/10/2017.
 */

public interface approvalView {

    void showLoadingProgress();

    void dismissLoadingProgress();

    void  showToast(String message);

    void updateUi(List<GroupTimeLineData.ResultEntity> TimelineList);
}
