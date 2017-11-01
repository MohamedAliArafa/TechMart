package com.a700apps.techmart.ui.screens.managegroupboardmember;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.AutoCompleteGroupAdapter;
import com.a700apps.techmart.adapter.GroupsAdapter;
import com.a700apps.techmart.adapter.ManageGroupAdapter;
import com.a700apps.techmart.data.model.UserGroup;
import com.a700apps.techmart.data.model.UserGroupData;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupsTimeLineFragment;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.mygroup.GroupView;
import com.a700apps.techmart.ui.screens.mygroup.MyGroupPresenter;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationFragment;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileFragment;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.Globals;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class
ManageBoardGroupFragment extends Fragment implements manageGroupView {
    View view;
    private ManageGroupPresenter mPresenter;
    EmptyRecyclerView rv;
    EditText searchCompleteTextView;
    ImageView mProfileImageView, mNotificationImageView, mSideMenuImageView;
    public AVLoadingIndicatorView indicatorView;
    private List<UserGroup> suggestions = new ArrayList<>();
    UserGroupData data;

    TextView empty;

    public ManageBoardGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_manage_group, container, false);
        indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        mPresenter = new ManageGroupPresenter();
        mPresenter.attachView(this);
        mProfileImageView = (ImageView) view.findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) view.findViewById(R.id.new_profile);
        mSideMenuImageView = (ImageView) view.findViewById(R.id.imageView4);
        searchCompleteTextView = view.findViewById(R.id.edt_search);
//        searchCompleteTextView.setThreshold(1);
        empty = (TextView) view.findViewById(R.id.empty);
        mSideMenuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityUtils.openActivity(getActivity(), HomeActivity.class, true);
                //open drawer here
                ((HomeActivity) getActivity()).openDrawer();

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

        mPresenter.getMyGroup(getActivity());
        rv = (EmptyRecyclerView) view.findViewById(R.id.recyclerView);


        return view;
    }


    @Override
    public void showProgress() {
//        indicatorView.setVisibility(View.VISIBLE);
//        indicatorView.show();
    }

    @Override
    public void dismissProgress() {
//        indicatorView.hide();
    }

    @Override
    public void updateUi(final UserGroupData data) {
        this.data = data;

        if (data.userGroup.size() == 0) {
            rv.setEmptyView(view.findViewById(R.id.tv_nodata));
        }
        rv.setAdapter(new ManageGroupAdapter(getActivity(), data.userGroup));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                suggestions.clear();
                for (int i = 0; i < data.userGroup.size(); i++) {
                    if (data.userGroup.get(i).Name.toLowerCase().startsWith(s.toString().toLowerCase())) {
                        suggestions.add(data.userGroup.get(i));
                    }
                }
                if (suggestions.size() > 0){
                    empty.setVisibility(View.GONE);
                }else {
                    empty.setVisibility(View.VISIBLE);
                }
                rv.setAdapter(new ManageGroupAdapter(getActivity(), suggestions));
            }
        });

    }



}
