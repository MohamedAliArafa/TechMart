package com.a700apps.techmart.ui.screens.BoardMember.JoinRequests;

import com.a700apps.techmart.data.model.JoinGroupRequestsData;

import java.util.List;

/**
 * Created by khaled.badawy on 10/10/2017.
 */

public interface RequestsView {

    void showLoadingProgress();

    void dismissLoadingProgress();

    void  showToast(String message);

    void updateData(List<JoinGroupRequestsData.Result> list);
}
