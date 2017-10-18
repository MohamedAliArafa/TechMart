package com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembermembers;

import com.a700apps.techmart.data.model.AllGroupUsers;

/**
 * Created by samir.salah on 10/11/2017.
 */

public interface BoardRemoveView {

    void showProgress();

    void dismissProgress();
void update();
    void updateUi(AllGroupUsers data);
}
