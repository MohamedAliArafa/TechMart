package com.a700apps.techmart.ui.screens.profile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.MyProfile;
import com.a700apps.techmart.data.model.post;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.message.ChatActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.ImageDetailsActivity;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by samir salah on 9/13/2017.
 */

public class MemberProfile extends AppCompatActivity implements ProfileView, View.OnClickListener {
    ImageView mProfileImageView, mNotificationImageView, mProfileUserImageView;
    ImageView imageView4;
    TextView mName, mFriend, mFollowers, mPosts, mCompany, mGroupCount, mEventCount, mEmail;
    private MemberPresenter presenter;
    public AVLoadingIndicatorView indicatorView;
    String mRelId;
    int GroupId;
    LinearLayout mContainerLinearLayout;
    String ImageLink;
    Button mFollowButton, mConnectButton, mMessageButton;
    boolean isFollowing, isConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);

        presenter = new MemberPresenter();
        presenter.attachView(this);

        init();
        mRelId = getIntent().getStringExtra("RelativId");
        GroupId = getIntent().getIntExtra("GroupId", 0);

        imageView4 = (ImageView) findViewById(R.id.imageView4);
        indicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);

        presenter.profileData(PreferenceHelper.getUserId(this), mRelId, GroupId, MemberProfile.this);
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mProfileImageView = (ImageView) findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) findViewById(R.id.new_profile);

        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(MemberProfile.this, EditProfileActivity.class, false);
            }
        });

        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(MemberProfile.this, NotificationActivity.class, false);


            }
        });

        mProfileUserImageView.setOnClickListener(this);


    }

    void init() {

        mProfileUserImageView = (ImageView) findViewById(R.id.iv_user);
        mName = (TextView) findViewById(R.id.tv_name);
        mPosts = (TextView) findViewById(R.id.tv_post);
        mFollowers = (TextView) findViewById(R.id.tv_followers);
        mFriend = (TextView) findViewById(R.id.tv_friend);
        mCompany = (TextView) findViewById(R.id.tv_company);

        mGroupCount = (TextView) findViewById(R.id.tv_group);
        mEventCount = (TextView) findViewById(R.id.tv_event);

        mFollowButton = (Button) findViewById(R.id.btn_follow);
        mConnectButton = (Button) findViewById(R.id.btn_edit);
        mMessageButton = (Button) findViewById(R.id.btn_message);
        mEmail = (TextView) findViewById(R.id.textView28);
        mFollowButton.setOnClickListener(this);
        mConnectButton.setOnClickListener(this);
        mMessageButton.setOnClickListener(this);
        mContainerLinearLayout = (LinearLayout) findViewById(R.id.line3);

    }

    @Override
    public void showLoadingProgress() {
        indicatorView.setVisibility(View.VISIBLE);
        indicatorView.show();
    }

    @Override
    public void dismissLoadingProgress() {
        indicatorView.hide();
    }

    @Override
    public void updateUi(MyProfile MyProfile) {
        ImageLink = MainApi.IMAGE_IP + MyProfile.Photo;
        Glide.with(this)
                .load(MainApi.IMAGE_IP + MyProfile.Photo)
                .placeholder(R.drawable.ic_profile)
                .into(mProfileUserImageView);
        mName.setText(MyProfile.Name);
        mPosts.setText(String.valueOf(MyProfile.PostsCount));
        mFollowers.setText(String.valueOf(MyProfile.FollowersCount));
        mFriend.setText(String.valueOf(MyProfile.FriendsCount));
        mEmail.setText(MyProfile.Email);
        mCompany.setText(MyProfile.Company);
        if (MyProfile.IsFollowed) {
            isFollowing = true;
//            isConnect = true;
            mMessageButton.setEnabled(true);
            mFollowButton.setText(R.string.unfollow);
//            mConnectButton.setText(R.string.disconnect);
            mContainerLinearLayout.setVisibility(View.VISIBLE);
        } else {
            isFollowing = false;
//            isConnect = false;
            mMessageButton.setEnabled(false);
            mFollowButton.setText(R.string.follow);
//            mConnectButton.setText(R.string.connect);
            mContainerLinearLayout.setVisibility(View.GONE);
        }


        if (MyProfile.IsConntected) {
            isFollowing = true;
            isConnect = true;
            mMessageButton.setEnabled(true);
            mFollowButton.setText(R.string.unfollow);
            mConnectButton.setText(R.string.disconnect);
            mContainerLinearLayout.setVisibility(View.VISIBLE);
        } else {
//            isFollowing = false;
            isConnect = false;
            mMessageButton.setEnabled(false);
//            mFollowButton.setText(R.string.follow);
            mConnectButton.setText(R.string.connect);
            mContainerLinearLayout.setVisibility(View.GONE);
        }


    }

    @Override
    public void updateUiFollow(post success) {

        mMessageButton.setEnabled(true);
        mFollowButton.setText(R.string.unfollow);
        mContainerLinearLayout.setVisibility(View.VISIBLE);
//


    }

    @Override
    public void updateUiUnFollow(post success) {
        mMessageButton.setEnabled(false);
        mFollowButton.setText(R.string.follow);
        if (isConnect) {
            mContainerLinearLayout.setVisibility(View.VISIBLE);
        } else {
            mContainerLinearLayout.setVisibility(View.GONE);

        }
    }

    @Override
    public void updateUiConnect(post success) {
        mMessageButton.setEnabled(true);
        mFollowButton.setText(R.string.unfollow);
        mConnectButton.setText(R.string.disconnect);
        mContainerLinearLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void updateUiDisConnect(post success) {
        mFollowButton.setText(R.string.follow);
        mMessageButton.setEnabled(false);
        mConnectButton.setText(R.string.connect);
        mContainerLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void updateUiUpdate(post success) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_follow:
                if (isFollowing) {
                    presenter.sendFollow(mRelId, PreferenceHelper.getUserId(MemberProfile.this), "false");
                    isFollowing = false;
                } else {
                    isFollowing = true;
                    presenter.sendFollow(mRelId, PreferenceHelper.getUserId(MemberProfile.this), "true");

                }

                break;
            case R.id.btn_edit:
                if (isConnect) {
                    isConnect = false;
                    presenter.sendConnect(mRelId, PreferenceHelper.getUserId(MemberProfile.this), "false");
                } else {
                    isConnect = true;
                    presenter.sendConnect(mRelId, PreferenceHelper.getUserId(MemberProfile.this), "true");

                }
                break;
            case R.id.btn_message:
                Intent intent = new Intent(MemberProfile.this, ChatActivity.class);
                intent.putExtra("RelativeID", mRelId);
                intent.putExtra("ReciverName", mName.getText().toString());
                intent.putExtra("ReciverPhoto", ImageLink);
                startActivity(intent);
                break;

            case R.id.iv_user:
                Intent intentDetails = new Intent(MemberProfile.this, ImageDetailsActivity.class);
                intentDetails.putExtra("ImageUrl", ImageLink);
                startActivity(intentDetails);
                break;
        }
    }
}
