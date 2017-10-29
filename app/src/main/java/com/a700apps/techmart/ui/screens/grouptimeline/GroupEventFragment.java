package com.a700apps.techmart.ui.screens.grouptimeline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.EventAdapter;
import com.a700apps.techmart.adapter.GroupEventAdapter;
import com.a700apps.techmart.data.model.GroupTimeLine;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.ui.screens.creatEvent.CreatEventActivity;
import com.a700apps.techmart.ui.screens.creatpost.PostActivity;
import com.a700apps.techmart.ui.screens.timeline.TimeLinePresenter;
import com.a700apps.techmart.ui.screens.timeline.TimeLineView;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;

import java.util.List;

/**
 * Created by samir salah on 9/13/2017.
 */

public class GroupEventFragment extends Fragment implements GroupTimlineView {
    LinearLayout linContain;
    private GroupTimeLinePresenter presenter;
    public GroupEventFragment() {
        // Required empty public constructor
    }
    View view;
    EmptyRecyclerView rv;
    int desired_string;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_group_event, container, false);
        presenter = new GroupTimeLinePresenter();
        presenter.attachView(this);
        linContain = (LinearLayout)view.findViewById(R.id.post);
        Bundle arguments = getArguments();
         desired_string = arguments.getInt("string_key");

        rv = (EmptyRecyclerView) view.findViewById(R.id.recyclerView);
        presenter.getTimeline(PreferenceHelper.getUserId(getActivity()),desired_string,"1");


        linContain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ActivityUtils.openActivity(getActivity(), CreatEventActivity.class, false);

                Intent intent = new Intent(getActivity(), CreatEventActivity.class);
                intent.putExtra("string_key" , desired_string);
                startActivity(intent);

            }
        });
        return view;
    }

    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void dismissLoadingProgress() {

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getTimeline(PreferenceHelper.getUserId(getActivity()),desired_string,"1");

    }

    @Override
    public void updateUi(List<TimeLineData.ResultEntity> TimelineList) {
        if (TimelineList.size() == 0) {
            rv.setEmptyView(view.findViewById(R.id.tv_nodata));
        }
        rv.setAdapter(new GroupEventAdapter(getActivity(),TimelineList));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);
        rv.scrollToPosition(Globals.R_Index_group);
    }


}
