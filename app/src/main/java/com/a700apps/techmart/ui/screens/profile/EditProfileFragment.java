package com.a700apps.techmart.ui.screens.profile;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.MyProfile;
import com.a700apps.techmart.data.model.post;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.register.RegisterPresenter;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

public class EditProfileFragment extends Fragment implements ProfileView, View.OnClickListener {
    ImageView mProfileImageView, mNotificationImageView, mProfileUserImageView;
    ImageView imageView4;
    TextView mFriend, mFollowers, mPosts, mEmail;
    private ProfilePresenter presenter;
    public AVLoadingIndicatorView indicatorView;
    EditText mCompany, mPhone, mPosition, mLinkedin, mName;
    Button btn_edit, btn_save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        presenter = new ProfilePresenter();
        presenter.attachView(this);

        init(view);

        imageView4 = (ImageView) view.findViewById(R.id.imageView4);
        indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);

        presenter.profileData(PreferenceHelper.getUserId(getActivity()), getActivity());
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().onBackPressed();
                ((HomeActivity)getActivity()).openDrawer();
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
        return view;
    }

    void init(View view) {

        mProfileUserImageView = (ImageView) view.findViewById(R.id.iv_user);
        mName = (EditText) view.findViewById(R.id.tv_name);
        mPosts = (TextView) view.findViewById(R.id.tv_post);
        mEmail = (TextView) view.findViewById(R.id.textView28);
        mFollowers = (TextView) view.findViewById(R.id.tv_followers);
        mFriend = (TextView) view.findViewById(R.id.tv_friend);
        mCompany = (EditText) view.findViewById(R.id.tv_company);
        mPhone = (EditText) view.findViewById(R.id.tv_phone);
        mPosition = (EditText) view.findViewById(R.id.tv_position);
        btn_edit = (Button) view.findViewById(R.id.btn_edit);
        btn_save = (Button) view.findViewById(R.id.btn_save);

        btn_save.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
        mName.setEnabled(false);
        mCompany.setEnabled(false);
        mPosition.setEnabled(false);
        mPhone.setEnabled(false);
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

        Glide.with(getActivity())
                .load(MainApi.IMAGE_IP + MyProfile.Photo)
                .placeholder(R.drawable.ic_profile)
                .into(mProfileUserImageView);

        mName.setText(MyProfile.Name);
        mPosts.setText(String.valueOf(MyProfile.PostsCount));
        mFollowers.setText(String.valueOf(MyProfile.FollowersCount));
        mFriend.setText(String.valueOf(MyProfile.FriendsCount));
        mCompany.setText(MyProfile.Company);
        mPhone.setText(MyProfile.Phone);
        mPosition.setText(MyProfile.Position);
        mEmail.setText(MyProfile.Email);
    }

    @Override
    public void updateUiFollow(post success) {

    }

    @Override
    public void updateUiUnFollow(post success) {

    }

    @Override
    public void updateUiConnect(post success) {

    }

    @Override
    public void updateUiDisConnect(post success) {

    }

    @Override
    public void updateUiUpdate(post success) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_edit:
                mName.setEnabled(true);
                mCompany.setEnabled(true);
                mPhone.setEnabled(true);
                mPosition.setEnabled(true);
                break;

            case R.id.btn_save:
                presenter.updateProfileData(PreferenceHelper.getUserId(getActivity()),
                        mName.getText().toString(), "", "", mCompany.getText().toString(),
                        mPosition.getText().toString(), mPhone.getText().toString());

                break;
        }
    }
}

