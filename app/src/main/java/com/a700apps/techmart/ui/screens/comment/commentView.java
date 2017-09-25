package com.a700apps.techmart.ui.screens.comment;

import com.a700apps.techmart.data.model.Comment;
import com.a700apps.techmart.data.model.CommentData;
import com.a700apps.techmart.data.model.TimeLineData;

import java.util.List;

/**
 * Created by samir salah on 9/13/2017.
 */

public interface commentView {
    void showLoadingProgress();

    void dismissLoadingProgress();

    void UpdateUi(List<Comment> TimelineList);
    void Update();

}
