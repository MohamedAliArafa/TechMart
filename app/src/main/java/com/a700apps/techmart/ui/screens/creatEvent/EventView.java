package com.a700apps.techmart.ui.screens.creatEvent;

import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.model.post;

import java.util.List;

/**
 * Created by samir salah on 9/13/2017.
 */

public interface EventView {

    void showLoadingProgress();

    void dismissLoadingProgress();

    void UpdateUi(post post);

    void update(List<MyConnectionList.ResultEntity> userMeeting);
}
