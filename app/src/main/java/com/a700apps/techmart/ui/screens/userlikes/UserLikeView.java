package com.a700apps.techmart.ui.screens.userlikes;

import com.a700apps.techmart.data.model.UserLike;

import java.util.List;

/**
 * Created by samir salah on 9/15/2017.
 */

public interface UserLikeView {
    void showLoadingProgress();
   void dismissLoadingProgress();
    void update(List<UserLike> userLikes);
}
