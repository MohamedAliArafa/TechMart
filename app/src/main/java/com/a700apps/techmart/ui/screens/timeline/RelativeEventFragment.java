package com.a700apps.techmart.ui.screens.timeline;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.EventAdapter;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.DialogCreator;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.PreferenceHelper;

import java.util.List;

/**
 * Created by samir.salah on 9/28/2017.
 */

public class RelativeEventFragment extends Fragment implements TimeLineView {

    private TimeLinePresenter presenter;

    public RelativeEventFragment() {
        // Required empty public constructor
    }

    View view;
    EmptyRecyclerView rv;
    String relativeId ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_relative_events, container, false);
        presenter = new TimeLinePresenter();
        presenter.attachView(this);

        rv = (EmptyRecyclerView) view.findViewById(R.id.recyclerView);

        relativeId = getArguments().getString("RelativId");

        view.findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((HomeActivity)getActivity()).openDrawer();
                getActivity().onBackPressed();
            }
        });

        view.findViewById(R.id.new_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.openActivity(getActivity() , NotificationActivity.class , false);
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        presenter.GetRelativeEventByUserID(relativeId, PreferenceHelper.getUserId(getActivity()) , getActivity());

    }

    @Override
    public void showLoadingProgress() {
//        indicatorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoadingProgress() {
//        indicatorView.setVisibility(View.GONE);
    }

    @Override
    public void updateUi(List<TimeLineData.ResultEntity> TimelineList) {
        if (TimelineList.size() == 0) {
            rv.setEmptyView(view.findViewById(R.id.tv_nodata));
        }
        rv.setAdapter(new EventAdapter(getActivity(), TimelineList));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void showErrorDialog(int error) {
        DialogCreator.showOneButtonDialog(getActivity() , error, "Error", null);

    }

    @Override
    public void showLoadMoreProgress() {

    }

    @Override
    public void hideLoadMoreProgress() {

    }

    @Override
    public void removeLoadMoreListener() {

    }

    @Override
    public void changeLoadMoreRequestsStatus(boolean isLoading) {

    }


}
