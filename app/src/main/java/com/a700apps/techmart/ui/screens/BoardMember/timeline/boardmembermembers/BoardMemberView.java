package com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembermembers;

import com.a700apps.techmart.data.model.AllGroupUsers;
import com.a700apps.techmart.data.model.UserData;
import com.a700apps.techmart.data.model.UserGroupData;

/**
 * Created by samir.salah on 10/10/2017.
 */

public interface BoardMemberView {


    void showProgress();

    void dismissProgress();

    void updateUi(AllGroupUsers data);
}
