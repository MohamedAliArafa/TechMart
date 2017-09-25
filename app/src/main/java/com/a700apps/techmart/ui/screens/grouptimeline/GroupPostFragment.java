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
import com.a700apps.techmart.adapter.GroupPostAdapter;
import com.a700apps.techmart.adapter.PostAdapter;
import com.a700apps.techmart.data.model.GroupTimeLine;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.ui.screens.creatEvent.CreatEventActivity;
import com.a700apps.techmart.ui.screens.creatpost.PostActivity;
import com.a700apps.techmart.ui.screens.timeline.TimeLinePresenter;
import com.a700apps.techmart.ui.screens.timeline.TimeLineView;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.PreferenceHelper;

import java.util.List;

/**
 * Created by samir salah on 9/13/2017.
 */

public class GroupPostFragment extends Fragment implements GroupTimlineView {
    LinearLayout linContain;
    private GroupTimeLinePresenter presenter;
    int desired_string;
    public GroupPostFragment() {
        // Required empty public
        //
        // constructor
    }

    RecyclerView rv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_post, container, false);
        presenter = new GroupTimeLinePresenter();
        presenter.attachView(this);
        linContain = (LinearLayout)view.findViewById(R.id.post);

        Bundle arguments = getArguments();

         desired_string = arguments.getInt("string_key");
        rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        presenter.getTimeline(PreferenceHelper.getUserId(getActivity()),desired_string,"2");

        linContain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ActivityUtils.openActivity(getActivity(), PostActivity.class, false);
                Intent intent = new Intent(getActivity(), PostActivity.class);
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
    public void updateUi(List<GroupTimeLineData.ResultEntity> TimelineList) {

        rv.setAdapter(new GroupPostAdapter(getActivity(),TimelineList));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

//    @Override
//    public void updateUi(List<TimeLineData.ResultEntity> TimelineList) {
//
//        rv.setAdapter(new PostAdapter(getActivity(),TimelineList));
//        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//    }


}

