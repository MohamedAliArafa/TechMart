package com.a700apps.techmart.ui.screens.mygroup;

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
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.model.UserGroupData;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.login.LoginActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationFragment;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.wang.avi.AVLoadingIndicatorView;

public class MyGroubListActivity extends AppCompatActivity implements GroupView {
    private MyGroupPresenter mPresenter;
    RecyclerView rv;
    ImageView mProfileImageView, mNotificationImageView, mSideMenuImageView;
    public AVLoadingIndicatorView indicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groub_list);
        indicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        mPresenter = new MyGroupPresenter();
        mPresenter.attachView(this);
        mProfileImageView = (ImageView) findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) findViewById(R.id.new_profile);
        mSideMenuImageView = (ImageView) findViewById(R.id.imageView4);

        mSideMenuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(MyGroubListActivity.this, HomeActivity.class, true);

            }
        });

        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(MyGroubListActivity.this, EditProfileActivity.class, false);
            }
        });

        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(MyGroubListActivity.this, NotificationActivity.class, false);
//                ((HomeActivity)getActivity()).openFragment(NotificationFragment.class , null);
            }
        });


//        mPresenter.getMyGroup(this);
        rv = (RecyclerView) findViewById(R.id.recyclerView);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.openActivity(MyGroubListActivity.this, HomeActivity.class, true);

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
        rv.setAdapter(new GroupsAdapter(this, data.userGroup));
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void updateUiMore(UserGroupData data) {

    }

    @Override
    public void updateRelativeUi(UserGroupData data) {

    }

}
