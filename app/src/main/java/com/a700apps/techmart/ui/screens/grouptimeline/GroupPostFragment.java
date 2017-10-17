package com.a700apps.techmart.ui.screens.grouptimeline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.GroupPostAdapter;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.ui.screens.creatpost.PostActivity;
import com.a700apps.techmart.utils.EmptyRecyclerView;
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

    View view;
    EmptyRecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_group_post, container, false);
        presenter = new GroupTimeLinePresenter();
        presenter.attachView(this);
        linContain = (LinearLayout) view.findViewById(R.id.post);

        Bundle arguments = getArguments();

        desired_string = arguments.getInt("string_key");
        rv = (EmptyRecyclerView) view.findViewById(R.id.recyclerView);
        presenter.getTimeline(PreferenceHelper.getUserId(getActivity()), desired_string, "2");

        linContain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ActivityUtils.openActivity(getActivity(), PostActivity.class, false);
                Intent intent = new Intent(getActivity(), PostActivity.class);
                intent.putExtra("string_key", desired_string);
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
        presenter.getTimeline(PreferenceHelper.getUserId(getActivity()), desired_string, "2");

    }

    @Override
    public void updateUi(List<GroupTimeLineData.ResultEntity> TimelineList) {
        if (TimelineList.size() == 0) {
            rv.setEmptyView(view.findViewById(R.id.tv_nodata));
        }
        rv.setAdapter(new GroupPostAdapter(getActivity(), TimelineList));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

//    @Override
//    public void updateUi(List<TimeLineData.ResultEntity> TimelineList) {
//
//        rv.setAdapter(new PostAdapter(getActivity(),TimelineList));
//        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//    }


}

