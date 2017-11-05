package com.a700apps.techmart.ui.screens.BoardMember.timeline.bordmemberpost;

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
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembertimeline.BoardTimelinePresenter;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembertimeline.BoardTimlineView;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PaginationScrollListener;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;

import java.util.List;


public class BoardPostFragment extends Fragment implements BoardTimlineView {

    View view;
    EmptyRecyclerView rv;
    private BoardTimelinePresenter presenter;
    Dialog dialogsLoading;
    BoardMemberAdapter adapter;
    // Index from which pagination should start (0 is 1st page in our case)
    int PageNumber = 1;
    private boolean isLoading = false;
    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;

    int mGroupId;
    public BoardPostFragment() {
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
        presenter.getTimeline(mGroupId,PreferenceHelper.getUserId(getActivity()),2,1, Globals.PAGE_SIZE);
    }

    @Override
    public void updateUi(List<TimeLineData.ResultEntity> TimelineList) {
        if (TimelineList.size() == 0) {
            rv.setEmptyView(view.findViewById(R.id.tv_nodata));
            isLastPage = true;
        }
        adapter = new BoardMemberAdapter(getActivity(),TimelineList,rv);
        rv.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);

        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if (!isLastPage) {
                    isLoading = true;
                    PageNumber += 1; //Increment page index to load the next one
                    adapter.addLoadingFooter();
                    loadNextPage();
                }

            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public void updateUiMore(List<TimeLineData.ResultEntity> TimelineList) {
        adapter.removeLoadingFooter();
        isLoading = false;

        if (TimelineList.size() == 0) {
            isLastPage = true;
        }
        adapter.addAll(TimelineList);
        adapter.notifyDataSetChanged();
    }

    private void loadNextPage() {
        presenter.getTimelineMore(mGroupId, PreferenceHelper.getUserId(getActivity()), 0, PageNumber, Globals.PAGE_SIZE);
    }
}
