package com.a700apps.techmart.ui.screens.mygroup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.AutoCompleteGroupAdapter;
import com.a700apps.techmart.adapter.GroupsAdapter;
import com.a700apps.techmart.data.model.UserGroup;
import com.a700apps.techmart.data.model.UserGroupData;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupsTimeLineFragment;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.Globals;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyGroupsListFragment extends Fragment implements GroupView {
    View view;
    private MyGroupPresenter mPresenter;
    EmptyRecyclerView rv;
    AutoCompleteTextView searchCompleteTextView;
    ImageView mProfileImageView, mNotificationImageView, mSideMenuImageView;
    public AVLoadingIndicatorView indicatorView;

    public MyGroupsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_groups_list, container, false);
        indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        mPresenter = new MyGroupPresenter();
        mPresenter.attachView(this);
        mProfileImageView = (ImageView) view.findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) view.findViewById(R.id.new_profile);
        mSideMenuImageView = (ImageView) view.findViewById(R.id.imageView4);
        searchCompleteTextView = view.findViewById(R.id.edt_search);
        searchCompleteTextView.setThreshold(1);

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
                ActivityUtils.openActivity(getActivity(), EditProfileActivity.class, false);
            }
        });

        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(getActivity(), NotificationActivity.class, false);
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
    public void updateUi(UserGroupData data) {
        if (data.userGroup.size() == 0) {
            rv.setEmptyView(view.findViewById(R.id.tv_nodata));
        }
        rv.setAdapter(new GroupsAdapter(getActivity(), data.userGroup));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchCompleteTextView.setAdapter(new AutoCompleteGroupAdapter(getActivity() ,R.layout.custom_text_view, data.userGroup ));

        searchCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        /*
        Bundle bundle = new Bundle();
            bundle.putInt("selectedCategory" ,mUserGroupList.get(position).ID );
            Globals.GROUP_ID = mUserGroupList.get(position).ID;
            ((HomeActivity )context).openFragment(GroupsTimeLineFragment.class , bundle);
         */
    }

    @Override
    public void updateRelativeUi(UserGroupData data) {

    }

}
