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
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeLineMainFragment extends Fragment implements TimeLineView {

    private TimeLinePresenter presenter;
    public TimeLineMainFragment() {
        // Required empty public constructor
    }

    RecyclerView rv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_line_main, container, false);
        presenter = new TimeLinePresenter();
        presenter.attachView(this);

         rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        presenter.getTimeline(PreferenceHelper.getUserId(getActivity()),"0");

        return view;
    }

    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void dismissLoadingProgress() {

    }

    @Override
    public void updateUi(List<TimeLineData.ResultEntity> TimelineList) {

        rv.setAdapter(new TimelineAdapter(getActivity(),TimelineList));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void showErrorDialog(int error) {

    }


}
