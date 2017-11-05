package com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembertimeline;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.BoardMemberAdapter;
import com.a700apps.techmart.adapter.TimelineAdapter;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.model.NotificationDataLike;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.ui.screens.timeline.TimeLinePresenter;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PaginationScrollListener;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.URLS;
import com.a700apps.techmart.utils.loadingDialog;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineBoardMemberFragment extends Fragment implements BoardTimlineView {

    View view;
    EmptyRecyclerView rv;
    private BoardTimelinePresenter presenter;
    Dialog dialogsLoading;
    int mGroupId;
    NotificationDataLike.Result result;
    BoardMemberAdapter adapter;

    // Index from which pagination should start (0 is 1st page in our case)
    int PageNumber = 1;
    private boolean isLoading = false;
    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;

    public TimelineBoardMemberFragment() {
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
        dialogsLoading = new loadingDialog().showDialog(getActivity());
    }

    @Override
    public void dismissLoadingProgress() {
        dialogsLoading.dismiss();
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.getTimeline(mGroupId, PreferenceHelper.getUserId(getActivity()), 0, 1, Globals.PAGE_SIZE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 123) {
                int pos = data.getExtras().getInt("data", 0);
//                Toast.makeText(getActivity(), "->"+pos, Toast.LENGTH_SHORT).show();
                rv.scrollToPosition(pos);
            }
        }
    }

    @Override
    public void updateUi(List<TimeLineData.ResultEntity> TimelineList) {
        if (TimelineList.size() == 0) {
            rv.setEmptyView(view.findViewById(R.id.tv_nodata));
            isLastPage = true;
        } else {
            isLastPage = false;
        }
        adapter = new BoardMemberAdapter(getActivity(), TimelineList, rv);
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
