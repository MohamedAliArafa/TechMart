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
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.utils.DialogCreator;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

/**
 * Created by samir salah on 9/11/2017.
 */

public class EventFragment extends Fragment implements TimeLineView {

    private TimeLinePresenter presenter;
    AVLoadingIndicatorView indicatorView;

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
        indicatorView = view.findViewById(R.id.avi);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.getTimeline(PreferenceHelper.getUserId(getActivity()), "1", getActivity(),1,1);
    }

    @Override
    public void showLoadingProgress() {
        indicatorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoadingProgress() {
        indicatorView.setVisibility(View.GONE);
    }

    @Override
    public void updateUi(List<TimeLineData.ResultEntity> TimelineList) {

        rv.setAdapter(new EventAdapter(getActivity(), TimelineList));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);
        rv.scrollToPosition(Globals.R_Index);
    }

    @Override
    public void showErrorDialog(int error) {
        DialogCreator.showOneButtonDialog(getActivity(), R.string.check_internet, "Error", null);

    }


}
