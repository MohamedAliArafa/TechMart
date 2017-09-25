package com.a700apps.techmart.ui.screens.message;

import com.a700apps.techmart.data.model.AllMessageList;
import com.a700apps.techmart.data.model.FriendMessage;
import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.model.UserData;
import com.a700apps.techmart.data.network.NetworkResponse;

import java.util.List;

/**
 * Created by samir.salah on 9/14/2017.
 */

public interface MessageView {
    void showProgress();

    void dismissProgress();

    void openNewChatActivity();

    void updateUi();

    void fillMessagesList(List<AllMessageList.ResultEntity> responser);

    void fillConectionList(List<MyConnectionList.ResultEntity> responser);

    void fillFriendChatList(List<FriendMessage.ResultEntity> responser);

    void openChatActivity();
}
