package com.a700apps.techmart.ui.screens.mygroup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.AutoCompleteGroupAdapter;
import com.a700apps.techmart.adapter.GroupsAdapter;
import com.a700apps.techmart.adapter.RelativeGroupAadpter;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.model.UserGroup;
import com.a700apps.techmart.data.model.UserGroupData;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupsTimeLineFragment;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.login.LoginActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationFragment;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileFragment;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.Globals;
import com.wang.avi.AVLoadingIndicatorView;

public class RelativeGroupsFragment extends Fragment implements GroupView {
    private MyGroupPresenter mPresenter;
    RecyclerView rv;
    ImageView mProfileImageView, mNotificationImageView, mSideMenuImageView;
    public AVLoadingIndicatorView indicatorView;
    AutoCompleteTextView searchAutoCompleteTextView;



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
        searchAutoCompleteTextView = view.findViewById(R.id.edt_search);
        searchAutoCompleteTextView.setThreshold(1);

        mSideMenuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().onBackPressed();
                ((HomeActivity)getActivity()).openDrawer();
            }
        });

        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityUtils.openActivity(getActivity(), EditProfileActivity.class, false);
                ((HomeActivity) getActivity()).openFragment(EditProfileFragment.class , null);
            }
        });

        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(getActivity(), NotificationActivity.class, false);
//                ((HomeActivity)getActivity()).openFragment(NotificationFragment.class , null);
            }
        });

        mPresenter.GetRelativeGroupByUserID(getActivity() , getArguments().getString("RelativId"));
        rv = (RecyclerView) view.findViewById(R.id.recyclerView);

        searchAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserGroup group = (UserGroup) adapterView.getItemAtPosition(i);
                Log.e("CLICK" , group.Name);
                Bundle bundle = new Bundle();
                bundle.putInt("selectedCategory" ,group.ID );
                Globals.GROUP_ID = group.ID;

                ((HomeActivity )getActivity()).openFragment(GroupsTimeLineFragment.class , bundle);
            }
        });
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
        searchAutoCompleteTextView.setAdapter(new AutoCompleteGroupAdapter(getActivity() , R.layout.custom_text_view , data.userGroup , rv));
    }

    @Override
    public void updateRelativeUi(UserGroupData data) {
        rv.setAdapter(new RelativeGroupAadpter(getActivity(), data.userGroup));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchAutoCompleteTextView.setAdapter(new AutoCompleteGroupAdapter(getActivity() , R.layout.custom_text_view , data.userGroup ,rv));
    }
}

