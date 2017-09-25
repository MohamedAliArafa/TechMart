package com.a700apps.techmart.ui.screens.meetingone;

import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.model.UserLike;

import java.util.List;

/**
 * Created by samir.salah on 9/17/2017.
 */

public interface MeetingoneView {
    void showLoadingProgress();
    void dismissLoadingProgress();
    void update(List<MyConnectionList.ResultEntity> userMeeting);
}
