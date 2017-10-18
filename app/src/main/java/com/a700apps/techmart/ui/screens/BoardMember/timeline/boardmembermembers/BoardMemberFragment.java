package com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembermembers;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.BoardGroupUsersAdapter;
import com.a700apps.techmart.adapter.BoardMemberAdapter;
import com.a700apps.techmart.data.model.AllGroupUsers;
import com.a700apps.techmart.data.model.UserData;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembertimeline.BoardTimelinePresenter;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembertimeline.BoardTimlineView;
import com.a700apps.techmart.ui.screens.mygroup.MyGroupPresenter;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.PreferenceHelper;

import static com.a700apps.techmart.ui.screens.creatEvent.CreatEventActivity.presenter;

public class BoardMemberFragment extends Fragment implements BoardMemberView {

    View view;
    private BoardMemberPresenter mPresenter;
    EmptyRecyclerView rv;

    Dialog dialogsLoading;
    int mGroupId;

    public BoardMemberFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_board_member, container, false);

        mPresenter = new BoardMemberPresenter();
        mPresenter.attachView(this);
        Bundle arguments = getArguments();
        mGroupId = arguments.getInt("string_key");
        rv = (EmptyRecyclerView) view.findViewById(R.id.recyclerView);

        return view;
    }

    @Override
    public void onResume() {
        mPresenter.getMyGroupUsers(String.valueOf(mGroupId), getActivity());
        super.onResume();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void updateUi(AllGroupUsers data) {
        if (data.userGroup.size() == 0) {
            rv.setEmptyView(view.findViewById(R.id.tv_nodata));
        }
        rv.setAdapter(new BoardGroupUsersAdapter(getActivity(),data.userGroup,mGroupId));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}
