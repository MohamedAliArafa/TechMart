package com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmemberevent;

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
import com.a700apps.techmart.adapter.BoardMemberAdapter;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembertimeline.BoardTimelinePresenter;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembertimeline.BoardTimlineView;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;

import java.util.List;


public class BoardEventFragment extends Fragment implements BoardTimlineView {

    View view;
    EmptyRecyclerView rv;
    private BoardTimelinePresenter presenter;
    Dialog dialogsLoading;
    int mGroupId;
    public BoardEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_timeline_board_member, container, false);
        presenter = new BoardTimelinePresenter();
        presenter.attachView(this);
        Bundle arguments = getArguments();
        mGroupId = arguments.getInt("string_key");
        rv = (EmptyRecyclerView) view.findViewById(R.id.recyclerView);
//        presenter.getTimeline(mGroupId, PreferenceHelper.getUserId(getActivity()),1);
        return view;
    }

    @Override
    public void showLoadingProgress() {
//        dialogsLoading = new loadingDialog().showDialog(getActivity());
    }

    @Override
    public void dismissLoadingProgress() {
//        dialogsLoading.dismiss();
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.getTimeline(mGroupId, PreferenceHelper.getUserId(getActivity()),1);
    }

    @Override
    public void updateUi(List<GroupTimeLineData.ResultEntity> TimelineList) {
        if (TimelineList.size() == 0) {
            rv.setEmptyView(view.findViewById(R.id.tv_nodata));
        }
        rv.setAdapter(new BoardMemberAdapter(getActivity(),TimelineList));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}
