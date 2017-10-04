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
import com.a700apps.techmart.utils.DialogCreator;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

/**
 * Created by samir.salah on 9/28/2017.
 */

public class RelativeEventFragment extends Fragment implements TimeLineView {

    private TimeLinePresenter presenter;
    AVLoadingIndicatorView indicatorView;

    public RelativeEventFragment() {
        // Required empty public constructor
    }

    View view;
    EmptyRecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_relative_events, container, false);
        presenter = new TimeLinePresenter();
        presenter.attachView(this);

        rv = (EmptyRecyclerView) view.findViewById(R.id.recyclerView);
        indicatorView = view.findViewById(R.id.avi);

        String relativeId = getArguments().getString("RelativId");
        presenter.GetRelativeEventByUserID(relativeId, PreferenceHelper.getUserId(getActivity()));

        return view;
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
        DialogCreator.showOneButtonDialog(getActivity(), R.string.check_internet, "Error", null);

    }


}
