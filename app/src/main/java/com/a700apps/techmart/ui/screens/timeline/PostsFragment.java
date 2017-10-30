package com.a700apps.techmart.ui.screens.timeline;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.PostAdapter;
import com.a700apps.techmart.adapter.TimelineAdapter;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.utils.DialogCreator;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;

import java.util.List;

/**
 * Created by samir salah on 9/11/2017.
 */

public class PostsFragment extends Fragment implements TimeLineView {

    private TimeLinePresenter presenter;
    public PostsFragment() {
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
         view = inflater.inflate(R.layout.fragment_time_line_main, container, false);
        presenter = new TimeLinePresenter();
        presenter.attachView(this);

        rv = (EmptyRecyclerView) view.findViewById(R.id.recyclerView);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getTimeline(PreferenceHelper.getUserId(getActivity()),"2",getActivity());
    }

    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void dismissLoadingProgress() {

    }

    @Override
    public void updateUi(List<TimeLineData.ResultEntity> TimelineList) {
        if (TimelineList.size() == 0) {
            rv.setEmptyView(view.findViewById(R.id.tv_nodata));
        }
        rv.setAdapter(new PostAdapter(getActivity(),TimelineList));
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
