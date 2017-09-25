package com.a700apps.techmart.ui.screens.timeline;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.EventAdapter;
import com.a700apps.techmart.adapter.PostAdapter;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.utils.DialogCreator;
import com.a700apps.techmart.utils.PreferenceHelper;

import java.util.List;

/**
 * Created by samir salah on 9/11/2017.
 */

public class EventFragment extends Fragment implements TimeLineView {

    private TimeLinePresenter presenter;
    public EventFragment() {
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
        presenter.getTimeline(PreferenceHelper.getUserId(getActivity()),"1");

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

        rv.setAdapter(new EventAdapter(getActivity(),TimelineList));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void showErrorDialog(int error) {
        DialogCreator.showOneButtonDialog(getActivity(), R.string.check_internet, error, null);

    }


}
