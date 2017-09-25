package com.a700apps.techmart.ui.screens.creatpost;

import com.a700apps.techmart.data.model.Comment;
import com.a700apps.techmart.data.model.post;

import java.util.List;

/**
 * Created by samir salah on 9/13/2017.
 */

public interface PostView {

    void showLoadingProgress();

    void dismissLoadingProgress();

    void UpdateUi(post post);
}
