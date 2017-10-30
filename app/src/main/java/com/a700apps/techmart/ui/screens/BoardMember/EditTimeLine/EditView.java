package com.a700apps.techmart.ui.screens.BoardMember.EditTimeLine;

import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.model.NotificationDataLike;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.model.post;

import java.util.List;

/**
 * Created by khaled.badawy on 10/10/2017.
 */

public interface EditView {

    void showLoadingProgress();

    void dismissLoadingProgress();

    void UpdateUi(NotificationDataLike dataLike);

    void onImageSelected();

    void  showToast(String message);
    void  finishActivity();
}
