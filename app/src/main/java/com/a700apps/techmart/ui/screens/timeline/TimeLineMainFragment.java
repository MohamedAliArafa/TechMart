package com.a700apps.techmart.ui.screens.timeline;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.TimelineAdapter;
import com.a700apps.techmart.data.model.CategoryGroups;
import com.a700apps.techmart.data.model.TimeLine;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.ui.screens.category.CategoryPresenter;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeLineMainFragment extends Fragment implements TimeLineView {
    public AVLoadingIndicatorView indicatorView;
    private TimeLinePresenter presenter;
    public TimeLineMainFragment() {
        // Required empty public constructor
    }
    View view;
    EmptyRecyclerView rv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_time_line_main, container, false);
        presenter = new TimeLinePresenter();
        presenter.attachView(this);
        indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);

         rv = (EmptyRecyclerView) view.findViewById(R.id.recyclerView);



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getTimeline(PreferenceHelper.getUserId(getActivity()),"0",getActivity());
    }
    @Override
    public void showLoadingProgress() {
//        indicatorView.setVisibility(View.VISIBLE);
//        indicatorView.show();
    }

    @Override
    public void dismissLoadingProgress() {
//        indicatorView.hide();
    }

    @Override
    public void updateUi(List<TimeLineData.ResultEntity> TimelineList) {
        if (TimelineList.size() == 0) {
            rv.setEmptyView(view.findViewById(R.id.tv_nodata));
        }
        rv.setAdapter(new TimelineAdapter(getActivity(),TimelineList));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);

        rv.scrollToPosition(Globals.R_Index);
    }

    @Override
    public void showErrorDialog(int error) {

    }


}
