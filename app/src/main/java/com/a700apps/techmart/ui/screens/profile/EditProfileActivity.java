package com.a700apps.techmart.ui.screens.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.MyProfile;
import com.a700apps.techmart.data.model.post;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.register.RegisterPresenter;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

public class EditProfileActivity extends AppCompatActivity implements ProfileView,View.OnClickListener {
    ImageView mProfileImageView, mNotificationImageView, mProfileUserImageView;
    ImageView imageView4;
    TextView  mFriend, mFollowers, mPosts,mEmail;
    private ProfilePresenter presenter;
    public AVLoadingIndicatorView indicatorView;
    EditText mCompany, mPhone, mPosition, mLinkedin,mName;
   Button btn_edit,btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        presenter = new ProfilePresenter();
        presenter.attachView(this);

        init();

        imageView4 = (ImageView) findViewById(R.id.imageView4);
        indicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);

        presenter.profileData(PreferenceHelper.getUserId(this), EditProfileActivity.this);
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
                ActivityUtils.openActivity(EditProfileActivity.this, EditProfileActivity.class, false);
            }
        });

        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(EditProfileActivity.this, NotificationActivity.class, false);
            }
        });
    }

    void init() {

        mProfileUserImageView = (ImageView) findViewById(R.id.iv_user);
        mName = (EditText) findViewById(R.id.tv_name);
        mPosts = (TextView) findViewById(R.id.tv_post);
        mEmail = (TextView) findViewById(R.id.textView28);
        mFollowers = (TextView) findViewById(R.id.tv_followers);
        mFriend = (TextView) findViewById(R.id.tv_friend);
        mCompany = (EditText) findViewById(R.id.tv_company);
        mPhone = (EditText) findViewById(R.id.tv_phone);
        mPosition = (EditText) findViewById(R.id.tv_position);
        btn_edit=(Button)findViewById(R.id.btn_edit);
        btn_save=(Button)findViewById(R.id.btn_save);

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

        Glide.with(this)
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
    public void updateUiApprove() {

    }

    @Override
    public void updateUiCancelApprove() {

    }

    @Override
    public void updateUiDisConnect(post success) {

    }

    @Override
    public void updateUiUpdate(post success) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_edit:
                mName.setEnabled(true);
                mCompany.setEnabled(true);
                mPhone.setEnabled(true);
                mPosition.setEnabled(true);
                break;

            case R.id.btn_save:
              presenter.updateProfileData(this , PreferenceHelper.getUserId(EditProfileActivity.this),
                      mName.getText().toString(),"","",mCompany.getText().toString(),
                      mPosition.getText().toString(),mPhone.getText().toString());

                break;
        }
    }
}
