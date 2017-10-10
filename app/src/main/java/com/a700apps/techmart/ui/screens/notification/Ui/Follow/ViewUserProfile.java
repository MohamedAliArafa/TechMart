package com.a700apps.techmart.ui.screens.notification.Ui.Follow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.MyProfile;
import com.a700apps.techmart.data.model.post;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.PredefinedMessage.PredifinedMessageActivity;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.message.ChatActivity;
import com.a700apps.techmart.ui.screens.mygroup.RelativeGroupsFragment;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.ui.screens.profile.ProfileView;
import com.a700apps.techmart.ui.screens.timeline.EventFragment;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.ImageDetailsActivity;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

public class ViewUserProfile extends Fragment implements ProfileView, View.OnClickListener {
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
    View eventLayout , groupLayout;

    MyProfile myProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_user_profile, container, false);
        presenter = new MemberPresenter();
        presenter.attachView(this);

        init(view);
        mRelId = getArguments().getString("RelativId");
        GroupId = getArguments().getInt("GroupId", 0);

        imageView4 = (ImageView) view.findViewById(R.id.imageView4);
        indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);

        presenter.profileData(PreferenceHelper.getUserId(getActivity()), mRelId, GroupId, getActivity());
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mProfileImageView = (ImageView) view.findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) view.findViewById(R.id.new_profile);

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

        mProfileUserImageView.setOnClickListener(this);
        return view;
    }

    void init(View view) {

        mProfileUserImageView = (ImageView) view.findViewById(R.id.iv_user);
        mName = (TextView) view.findViewById(R.id.tv_name);
        mPosts = (TextView) view.findViewById(R.id.tv_post);
        mFollowers = (TextView) view.findViewById(R.id.tv_followers);
        mFriend = (TextView) view.findViewById(R.id.tv_friend);
        mCompany = (TextView) view.findViewById(R.id.tv_company);

        mGroupCount = (TextView) view.findViewById(R.id.tv_group);
        mEventCount = (TextView) view.findViewById(R.id.tv_event);
        eventLayout = view.findViewById(R.id.eventLayout);
        groupLayout = view.findViewById(R.id.groupLayout);
        mFollowButton = (Button) view.findViewById(R.id.btn_follow);
        mConnectButton = (Button) view.findViewById(R.id.btn_edit);
        mMessageButton = (Button) view.findViewById(R.id.btn_message);
        mEmail = (TextView) view.findViewById(R.id.textView28);


        mFollowButton.setOnClickListener(this);
        mConnectButton.setOnClickListener(this);
        mMessageButton.setOnClickListener(this);
        mContainerLinearLayout = (LinearLayout) view.findViewById(R.id.line3);
        eventLayout.setOnClickListener(this);
        groupLayout.setOnClickListener(this);

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
        myProfile = MyProfile;
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
            mMessageButton.setVisibility(View.VISIBLE);
            mFollowButton.setText(R.string.unfollow);
//            mConnectButton.setText(R.string.disconnect);
            mContainerLinearLayout.setVisibility(View.VISIBLE);
        } else {
            isFollowing = false;
//            isConnect = false;
            mMessageButton.setVisibility(View.GONE);
            mFollowButton.setText(R.string.follow);
//            mConnectButton.setText(R.string.connect);
            mContainerLinearLayout.setVisibility(View.GONE);
        }


        if (MyProfile.IsConntected) {
            isFollowing = true;
            isConnect = true;
            mMessageButton.setVisibility(View.VISIBLE);
            mFollowButton.setText(R.string.unfollow);
            mConnectButton.setText(R.string.disconnect);
            mContainerLinearLayout.setVisibility(View.VISIBLE);
        } else {
//            isFollowing = false;
            isConnect = false;
            mMessageButton.setVisibility(View.GONE);
//            mFollowButton.setText(R.string.follow);
            mConnectButton.setText(R.string.connect);
            mContainerLinearLayout.setVisibility(View.GONE);
        }


    }

    @Override
    public void updateUiFollow(post success) {

        mMessageButton.setVisibility(View.VISIBLE);
        mFollowButton.setText(R.string.unfollow);
        mContainerLinearLayout.setVisibility(View.VISIBLE);
//


    }

    @Override
    public void updateUiUnFollow(post success) {
        mMessageButton.setVisibility(View.GONE);
        mFollowButton.setText(R.string.follow);
        if (isConnect) {
            mContainerLinearLayout.setVisibility(View.VISIBLE);
        } else {
            mContainerLinearLayout.setVisibility(View.GONE);

        }
    }

    @Override
    public void updateUiConnect(post success) {
        mMessageButton.setVisibility(View.VISIBLE);
        mFollowButton.setText(R.string.unfollow);
        mConnectButton.setText(R.string.disconnect);
        mContainerLinearLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void updateUiDisConnect(post success) {
        mFollowButton.setText(R.string.follow);
        mMessageButton.setVisibility(View.GONE);
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
                    presenter.sendFollow(mRelId, PreferenceHelper.getUserId(getActivity()), "false");
                    isFollowing = false;
                } else {
                    isFollowing = true;
                    presenter.sendFollow(mRelId, PreferenceHelper.getUserId(getActivity()), "true");

                }

                break;
            case R.id.btn_edit:
                if (isConnect) {
                    isConnect = false;
                    presenter.sendConnect(mRelId, PreferenceHelper.getUserId(getActivity()), "false");
                } else {
                    isConnect = true;
                    presenter.sendConnect(mRelId, PreferenceHelper.getUserId(getActivity()), "true");
                }
                break;
            case R.id.btn_message:

                Log.e("MMMM" , "message");
                if (isConnect && myProfile.HaveSharedEventWithMe){
                    openPredifinedActivity();
                }else if (myProfile.IsFollowed &&  myProfile.HaveSharedEventWithMe ){
                    openPredifinedActivity();
                }else {
                    openChatActivity();
                }
                break;

            case R.id.iv_user:
                Intent intentDetails = new Intent(getActivity(), ImageDetailsActivity.class);
                intentDetails.putExtra("ImageUrl", ImageLink);
                startActivity(intentDetails);
                break;
            case R.id.eventLayout:
                EventFragment fragment = new EventFragment();
                Bundle bundle = new Bundle();
                bundle.putString("RelativId" , mRelId);
                ((HomeActivity) getActivity()).openFragment(EventFragment.class , bundle);
                break;
            case R.id.groupLayout:
//                Toast.makeText(getActivity(), "123", Toast.LENGTH_SHORT).show();
                RelativeGroupsFragment relativeGroupsFragment = new RelativeGroupsFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putBoolean("groupLayout" , true);
                bundle2.putString("RelativId" , mRelId);
                relativeGroupsFragment.setArguments(bundle2);

                ((HomeActivity) getActivity()).addFragmentToBackStack(getFragmentManager(), R.id.fragment_container, relativeGroupsFragment, false
                        , false);
                break;
        }
    }

    private void openPredifinedActivity(){
        Intent intent = new Intent(getActivity(), PredifinedMessageActivity.class);
        intent.putExtra("RelativeID", mRelId);
        intent.putExtra("ReciverName", mName.getText().toString());
        intent.putExtra("ReciverPhoto", ImageLink);
        startActivity(intent);
    }

    private void openChatActivity(){
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("RelativeID", mRelId);
        intent.putExtra("ReciverName", mName.getText().toString());
        intent.putExtra("ReciverPhoto", ImageLink);
        startActivity(intent);
    }
}





//
