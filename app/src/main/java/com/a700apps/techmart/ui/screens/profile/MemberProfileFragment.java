package com.a700apps.techmart.ui.screens.profile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.a700apps.techmart.ui.screens.notification.NotificationFragment;
import com.a700apps.techmart.ui.screens.timeline.RelativeEventFragment;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.ImageDetailsActivity;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.RoundedCornersTransformation;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by samir salah on 9/13/2017.
 */

public class MemberProfileFragment extends Fragment implements ProfileView, View.OnClickListener {
    ImageView mNotificationImageView, mProfileUserImageView;//mProfileImageView
    ImageView imageView4;
    TextView mName, mFriend, mFollowers, mPosts, mCompany, mGroupCount, mEventCount, mEmail, mPhone, mlinkedin, mPosition;
    private MemberPresenter presenter;
    public AVLoadingIndicatorView indicatorView;
    String mRelId;
    int GroupId;
    LinearLayout mContainerLinearLayout;
    String ImageLink;
    Button mFollowButton, mConnectButton, mMessageButton;
    boolean isFollowing, isConnect, isConnectPending , isApprove;
    View eventLayout, groupLayout;

    MyProfile myProfile;

    Dialog dialogsLoading;
    public static int sCorner = 10;
    public static int sMargin = 2;
    public static int sBorder = 5;
    public static String sColor = "#ffffff";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_member_profile, container, false);
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
                ((HomeActivity) getActivity()).openDrawer();
            }
        });

//        mProfileImageView = (ImageView) view.findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) view.findViewById(R.id.new_profile);

//        mProfileImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (AppUtils.isInternetAvailable(getActivity())){
//                    ((HomeActivity)getActivity()).openFragment(EditProfileFragment.class , null);
////                    ActivityUtils.openActivity(getActivity(), EditProfileActivity.class, false);
//                }else {
//                    Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtils.isInternetAvailable(getActivity())) {
//                    ActivityUtils.openActivity(getActivity(), NotificationActivity.class, false);
                    ((HomeActivity) getActivity()).openFragment(NotificationFragment.class, null);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }


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
        mPhone = (TextView) view.findViewById(R.id.textView26);
        mlinkedin = (TextView) view.findViewById(R.id.textView30);

        mGroupCount = (TextView) view.findViewById(R.id.tv_group);
        mEventCount = (TextView) view.findViewById(R.id.tv_event);
        eventLayout = view.findViewById(R.id.eventLayout);
        groupLayout = view.findViewById(R.id.groupLayout);
        mFollowButton = (Button) view.findViewById(R.id.btn_follow);
        mConnectButton = (Button) view.findViewById(R.id.btn_edit);
        mMessageButton = (Button) view.findViewById(R.id.btn_message);
        mEmail = (TextView) view.findViewById(R.id.textView28);
        mPosition = (TextView) view.findViewById(R.id.tv_position);


        mFollowButton.setOnClickListener(this);
        mConnectButton.setOnClickListener(this);
        mMessageButton.setOnClickListener(this);
        mContainerLinearLayout = (LinearLayout) view.findViewById(R.id.line3);
        eventLayout.setOnClickListener(this);
        groupLayout.setOnClickListener(this);

    }

    @Override
    public void showLoadingProgress() {
//        indicatorView.setVisibility(View.VISIBLE);
//        indicatorView.show();
    }

    @Override
    public void dismissLoadingProgress() {
//        indicatorView.hide();
    }

    @Override
    public void updateUi(MyProfile MyProfile) {
        myProfile = MyProfile;
        ImageLink = MainApi.IMAGE_IP + MyProfile.Photo;

        Glide.with(this).load(MainApi.IMAGE_IP + MyProfile.Photo)
                .bitmapTransform(new RoundedCornersTransformation(getActivity(), sCorner, sMargin, sColor, sBorder))
                .into(mProfileUserImageView);

        mName.setText(MyProfile.Name);
        mPosts.setText(String.valueOf(MyProfile.PostsCount));
        mFollowers.setText(String.valueOf(MyProfile.FollowersCount));
        mFriend.setText(String.valueOf(MyProfile.FriendsCount));
        mEmail.setText(MyProfile.Email);
        mCompany.setText(MyProfile.Company);
        mGroupCount.setText(String.valueOf(MyProfile.GroupCount));
        mEventCount.setText(String.valueOf(MyProfile.EventCount));
        mPhone.setText(MyProfile.Phone);
        mlinkedin.setText(MyProfile.LinkedInProfile);
        mPosition.setText(MyProfile.Position);

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
        } else if (MyProfile.IsConntectionRequestPending == 1) {
            isConnectPending = true;
            mConnectButton.setText(getString(R.string.canecl_connect));
        } else if (MyProfile.IsConntectionRequestPending == 2) {
            isConnectPending = true;
            isApprove = true;
            mConnectButton.setText(getString(R.string.approve_connect));
        } else {
            isConnect = false;
            mMessageButton.setVisibility(View.GONE);
            mConnectButton.setText(R.string.connect);
            mContainerLinearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateUiFollow(post success) {

        mMessageButton.setVisibility(View.VISIBLE);
        mFollowButton.setText(R.string.unfollow);
        mContainerLinearLayout.setVisibility(View.VISIBLE);
        int count = Integer.parseInt(mFollowers.getText().toString());
        mFollowers.setText(String.valueOf(++count));

    }

    @Override
    public void updateUiUnFollow(post success) {
        mMessageButton.setVisibility(View.GONE);
        mFollowButton.setText(R.string.follow);
        int count = Integer.parseInt(mFollowers.getText().toString());
        mFollowers.setText(String.valueOf(--count));
        isFollowing = false;

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
        mConnectButton.setText(R.string.canecl_connect);
        mConnectButton.setText(R.string.canecl_connect);
//        mContainerLinearLayout.setVisibility(View.VISIBLE);

        int count = Integer.parseInt(mFriend.getText().toString());
        mFriend.setText(String.valueOf(++count));
    }

    @Override
    public void updateUiDisConnect(post success) {
//        mFollowButton.setText(R.string.follow);
        mMessageButton.setVisibility(View.GONE);
        mConnectButton.setText(R.string.connect);
        mContainerLinearLayout.setVisibility(View.GONE);

        int count = Integer.parseInt(mFriend.getText().toString());
        mFriend.setText(String.valueOf(--count));

    }

    @Override
    public void updateUiUpdate(post success) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_follow:
                if (AppUtils.isInternetAvailable(getActivity())) {
                    if (isFollowing) {
                        presenter.sendFollow(mRelId, PreferenceHelper.getUserId(getActivity()), "false");
                        isFollowing = false;
                    } else {
                        isFollowing = true;
                        presenter.sendFollow(mRelId, PreferenceHelper.getUserId(getActivity()), "true");

                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.btn_edit:

                if (AppUtils.isInternetAvailable(getActivity())) {
                    if (isApprove){
                        presenter.sendConnect(mRelId, PreferenceHelper.getUserId(getActivity()), "true");
                    }else {
                        if (isConnect) {
                            isConnect = false;
                            presenter.sendConnect(mRelId, PreferenceHelper.getUserId(getActivity()), "false");
                        } else if (isConnectPending) {
                            isConnectPending = false;
                            presenter.sendConnect(mRelId, PreferenceHelper.getUserId(getActivity()), "false");
                        } else {
//                    isConnect = true;
                            presenter.sendConnect(mRelId, PreferenceHelper.getUserId(getActivity()), "true");
                        }
                    }

                } else {
                    Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.btn_message:
//                if (isConnect && myProfile.HaveSharedEventWithMe){
//                    openPredifinedActivity();
//                }else
                if (AppUtils.isInternetAvailable(getActivity())) {
                    if (isFollowing && myProfile.HaveSharedEventWithMe) {
                        openPredifinedActivity();
                    } else if (isConnect) {
                        openChatActivity();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.iv_user:
                if (AppUtils.isInternetAvailable(getActivity())) {
                    Intent intentDetails = new Intent(getActivity(), ImageDetailsActivity.class);
                    intentDetails.putExtra("ImageUrl", ImageLink);
                    startActivity(intentDetails);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.eventLayout:
                if (AppUtils.isInternetAvailable(getActivity())) {
                    Bundle bundle = new Bundle();
                    bundle.putString("RelativId", mRelId);
                    Globals.groupId = GroupId;
                    Globals.relativeId = mRelId;

                    ((HomeActivity) getActivity()).openFragment(RelativeEventFragment.class, bundle);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }

//                ((HomeActivity) getActivity()).addFragmentToBackStack(getFragmentManager(), R.id.fragment_container, fragment, false
//                        , false);
                break;
            case R.id.groupLayout:
//                Toast.makeText(getActivity(), "123", Toast.LENGTH_SHORT).show();
//                RelativeGroupsFragment relativeGroupsFragment = new RelativeGroupsFragment();

                if (AppUtils.isInternetAvailable(getActivity())) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("RelativId", mRelId);
                    Globals.groupId = GroupId;
                    Globals.relativeId = mRelId;
//                relativeGroupsFragment.setArguments(bundle2);
                    ((HomeActivity) getActivity()).openFragment(RelativeGroupsFragment.class, bundle2);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }

//                ((HomeActivity) getActivity()).addFragmentToBackStack(getFragmentManager(), R.id.fragment_container, relativeGroupsFragment, false
//                        , false);
                break;
        }
    }

    private void openPredifinedActivity() {
        Intent intent = new Intent(getActivity(), PredifinedMessageActivity.class);
        intent.putExtra("RelativeID", mRelId);
        intent.putExtra("ReciverName", mName.getText().toString());
        intent.putExtra("ReciverPhoto", ImageLink);
        startActivity(intent);
    }

    private void openChatActivity() {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("RelativeID", mRelId);
        intent.putExtra("ReciverName", mName.getText().toString());
        intent.putExtra("ReciverPhoto", ImageLink);
        startActivity(intent);
    }
}

