package com.a700apps.techmart.ui.screens.mygroup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a700apps.techmart.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.GroupsAdapter;
import com.a700apps.techmart.adapter.RelativeGroupAadpter;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.model.UserGroupData;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.login.LoginActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.wang.avi.AVLoadingIndicatorView;

public class RelativeGroupsFragment extends Fragment implements GroupView {
    private MyGroupPresenter mPresenter;
    RecyclerView rv;
    ImageView mProfileImageView, mNotificationImageView, mSideMenuImageView;
    public AVLoadingIndicatorView indicatorView;


    public RelativeGroupsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_relative_groups, container, false);

        indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        mPresenter = new MyGroupPresenter();
        mPresenter.attachView(this);
        mProfileImageView = (ImageView) view.findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) view.findViewById(R.id.new_profile);
        mSideMenuImageView = (ImageView) view.findViewById(R.id.imageView4);

        mSideMenuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(getActivity(), EditProfileActivity.class, false);
            }
        });

        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(getActivity(), NotificationActivity.class, false);
            }
        });

        mPresenter.GetRelativeGroupByUserID(getActivity() , getArguments().getString("RelativId"));
        rv = (RecyclerView) view.findViewById(R.id.recyclerView);

        return view;
    }

//    @Override
//    public void onBackPressed() {
//        getActivity().onBackPressed();
//
//        ActivityUtils.openActivity(getActivity(), HomeActivity.class, true);
//
//    }

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
        rv.setAdapter(new GroupsAdapter(getActivity(), data.userGroup));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void updateRelativeUi(UserGroupData data) {
        rv.setAdapter(new RelativeGroupAadpter(getActivity(), data.userGroup));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

}

