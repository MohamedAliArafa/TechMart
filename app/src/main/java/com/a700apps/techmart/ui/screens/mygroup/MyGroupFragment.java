package com.a700apps.techmart.ui.screens.mygroup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.GroupsAdapter;
import com.a700apps.techmart.adapter.MyGroupDetailAdapter;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.model.UserGroup;
import com.a700apps.techmart.data.model.UserGroupData;
import com.a700apps.techmart.ui.screens.category.CategoryPresenter;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by samir salah on 8/16/2017.
 */

public class MyGroupFragment extends Fragment implements GroupView {

    ImageView imageView4;
    ImageView mNotificationImageView;
    @BindView(R.id.avi)
    public AVLoadingIndicatorView indicatorView;
    private MyGroupPresenter mPresenter;
    RecyclerView rv;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_my_groub_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mPresenter = new MyGroupPresenter();
        mPresenter.attachView(this);
        indicatorView= (AVLoadingIndicatorView)view. findViewById(R.id.avi);
        rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        mPresenter.getMyGroup(getActivity());



        mNotificationImageView = (ImageView)view. findViewById(R.id.new_profile);

        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(getActivity(), NotificationActivity.class, false);


            }
        });
        imageView4 = (ImageView) view.findViewById(R.id.imageView4);
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).openDrawer();
            }
        });
    }

    @Override
    public void showProgress() {
        indicatorView.setVisibility(View.VISIBLE);
        indicatorView.show();
    }

    @Override
    public void dismissProgress() {
        indicatorView.hide();
    }

    @Override
    public void updateUi(UserGroupData data) {
        rv.setAdapter(new MyGroupDetailAdapter(getContext(),data.userGroup));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void updateRelativeUi(UserGroupData data) {

    }


}
