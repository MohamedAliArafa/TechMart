package com.a700apps.techmart.ui.screens.timelinedetails;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.GroupTimeLine;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.model.LikeData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.screens.comment.CommentActivity;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupsTimLineActivity;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.CustomTextView;
import com.a700apps.techmart.utils.DateTimePicker.CustomLightTextView;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by samir.salah on 9/18/2017.
 */

public class DetailsGroupActivity extends AppCompatActivity implements View.OnClickListener, NetworkResponseListener<LikeData>, DetailsView {
    ImageView imageView4;
    ImageView iv_slider, mProfileImageView, mNotificationImageView, iv_comment, iv_share, mLikeImageView, iv_share_event, iv_invite, iv_calender;
    LinearLayout mLikeLinearContainer, mEventLinearContainer;
    TextView mTitle, mSlidertype, mSlideTitle, mDescTextView, tv_comment, tv_share, tv_like, tv_going;
    TextView mLikeCount , mCommentCount , mShareCount ;

    String Type;
    int index;
    List<GroupTimeLineData.ResultEntity> mList;
    TextView next, back;
    String isLike;
    DetailsPresenter mPresenter;
    public AVLoadingIndicatorView indicatorView;
    TextView mGoing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_details);

        mPresenter = new DetailsPresenter();
        mPresenter.attachView(this);

//        Type = getIntent().getStringExtra("Type");
        if (mList.get(index).getType() == 1){
            Type = "Event";
        }else {
            Type = "post";
        }
        index = getIntent().getIntExtra("Index", 0);
        mList = getIntent().getParcelableArrayListExtra("Timeline");
        indicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        mGoing = (TextView) findViewById(R.id.textView53);
        iv_invite=(ImageView)findViewById(R.id.iv_invite);
        mProfileImageView = (ImageView) findViewById(R.id.new_message);
        iv_slider = (ImageView) findViewById(R.id.iv_slider);
        mNotificationImageView = (ImageView) findViewById(R.id.new_profile);
        next = (CustomTextView) findViewById(R.id.next);
        back = (CustomTextView) findViewById(R.id.back);
        tv_comment = (CustomLightTextView) findViewById(R.id.tv_comment);
        tv_share = (CustomLightTextView) findViewById(R.id.tv_share);
        tv_like = (CustomLightTextView) findViewById(R.id.tv_like);
        tv_going = (TextView) findViewById(R.id.textView53);
        mLikeCount = (TextView) findViewById(R.id.tv_like_count);
        mCommentCount = (TextView) findViewById(R.id.tv_comment_count);
        mShareCount = (TextView) findViewById(R.id.tv_share_count);

        imageView4 = (ImageView) findViewById(R.id.imageView4);
        iv_comment = (ImageView) findViewById(R.id.iv_comment);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_share_event = (ImageView) findViewById(R.id.iv_share_event);
        iv_invite = (ImageView) findViewById(R.id.iv_invite);
        iv_calender = (ImageView) findViewById(R.id.iv_calender);

        mLikeImageView = (ImageView) findViewById(R.id.iv_like);

        Glide.with(this)
                .load(MainApi.IMAGE_IP + mList.get(index).getImage()).placeholder(R.drawable.placeholder)
                .into(iv_slider);

        if (mList.get(index).getIsGoing()) {
            mGoing.setText("Attending");
            mGoing.setEnabled(false);
            mGoing.setClickable(false);
        }

        mGoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(DetailsGroupActivity.this)){
                    mGoing.setClickable(false);
                    mGoing.setText("Attending");
                    mPresenter.attendee(String.valueOf(mList.get(index).getID()), PreferenceHelper.getUserId(DetailsGroupActivity.this), "true", DetailsGroupActivity.this);

                }else {
                    Toast.makeText(DetailsGroupActivity.this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        iv_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(DetailsGroupActivity.this)){
                    mGoing.setClickable(false);
                    mGoing.setText("Attending");
                    mPresenter.attendee(String.valueOf(mList.get(index).getID()), PreferenceHelper.getUserId(DetailsGroupActivity.this), "true", DetailsGroupActivity.this);

                }else {
                    Toast.makeText(DetailsGroupActivity.this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mTitle = (CustomLightTextView) findViewById(R.id.tv_event_title);
        mDescTextView = (CustomLightTextView) findViewById(R.id.tv_events_desc);
        mDescTextView.setMovementMethod(new ScrollingMovementMethod());

        mSlideTitle = (CustomTextView) findViewById(R.id.tv_events_title);
        mSlidertype = (CustomTextView) findViewById(R.id.tv_events);

        mLikeLinearContainer = (LinearLayout) findViewById(R.id.container);
        mEventLinearContainer = (LinearLayout) findViewById(R.id.container_event);

        iv_comment.setOnClickListener(this);
        tv_comment.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        tv_comment.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_calender.setOnClickListener(this);
        iv_share_event.setOnClickListener(this);
        iv_invite.setOnClickListener(this);
        if (mList.get(index).getIsLike()) {
            mLikeImageView.setImageResource(R.drawable.ic_like_active);
        } else {
            mLikeImageView.setImageResource(R.drawable.ic_like);
        }
        tv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(DetailsGroupActivity.this)){
                    if (mList.get(index).getIsLike()) {
                        mList.get(index).setIsLike(false);
                        isLike = "false";
                        mLikeImageView.setImageResource(R.drawable.ic_like);
                    } else {
                        isLike = "true";
                        mList.get(index).setIsLike(true);
                        mLikeImageView.setImageResource(R.drawable.ic_like_active);
                    }
                    getLike(mList.get(index));

                }else {
                    Toast.makeText(DetailsGroupActivity.this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }

            }
        });

        mLikeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(DetailsGroupActivity.this)){
                    if (mList.get(index).getIsLike()) {
                        mList.get(index).setIsLike(false);
                        isLike = "false";
                        mLikeImageView.setImageResource(R.drawable.ic_like);
                    } else {
                        isLike = "true";
                        mList.get(index).setIsLike(true);
                        mLikeImageView.setImageResource(R.drawable.ic_like_active);
                    }
                    getLike(mList.get(index));

                }else {
                    Toast.makeText(DetailsGroupActivity.this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (++index < mList.size()) {
                    finish();
                    Intent intent = new Intent(DetailsGroupActivity.this, DetailsGroupActivity.class);
                    intent.putExtra("Type", Type);
                    intent.putExtra("Index", index);
                    intent.putParcelableArrayListExtra("Timeline", (ArrayList<? extends Parcelable>) mList);
                    startActivity(intent);
                } else {
                    finish();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index > 0) {
                    finish();
                    Intent intent = new Intent(DetailsGroupActivity.this, DetailsGroupActivity.class);
                    intent.putExtra("Type", Type);
                    intent.putExtra("Index", index - 1);
                    intent.putParcelableArrayListExtra("Timeline", (ArrayList<? extends Parcelable>) mList);
                    startActivity(intent);
                } else {
                    finish();
                }
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mTitle.setText(mList.get(index).getTitle());
        mLikeCount.setText(""+mList.get(index).getLikeCount());
        mCommentCount.setText(""+mList.get(index).getCommentCount());
        mDescTextView.setText(mList.get(index).getDescr());
        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityUtils.openActivity(DetailsGroupActivity.this, EditProfileActivity.class, false);
            }
        });

        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(DetailsGroupActivity.this, NotificationActivity.class, false);

            }
        });


        if (Type.equals("post")) {
            mLikeLinearContainer.setVisibility(View.VISIBLE);
            mEventLinearContainer.setVisibility(View.GONE);
            mSlidertype.setText("Post");
        } else {
            mLikeLinearContainer.setVisibility(View.GONE);
            mEventLinearContainer.setVisibility(View.VISIBLE);
            mSlidertype.setText("Event");
            if (mList.get(index).getIsGoing()) {
                tv_going.setText("Attending");
            }
        }
    }

    void setValues() {
        if (mList.get(index).getIsLike()) {
            mList.get(index).setIsLike(false);

        } else {
            mList.get(index).setIsLike(true);

        }
    }

    void getLike(GroupTimeLineData.ResultEntity timeLine) {
        try {
            JSONObject registerBody = MainApiHelper.getUserLike(PreferenceHelper.getUserId(this), timeLine.getID(), isLike
            );
            MainApi.getLikeTimeLine(registerBody, DetailsGroupActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        ActivityUtils.openActivity(DetailsGroupActivity.this, GroupsTimLineActivity.class, true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_comment:
                if (AppUtils.isInternetAvailable(this)){
                    Intent intent2 = new Intent(DetailsGroupActivity.this, CommentActivity.class);
                    intent2.putExtra("string_key", mList.get(index).getID());
                    intent2.putExtra("likes_number",  mList.get(index).getLikeCount());
                    startActivity(intent2);
                }else {
                    Toast.makeText(this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.iv_comment:
                if (AppUtils.isInternetAvailable(this)){
                    Intent intent = new Intent(DetailsGroupActivity.this, CommentActivity.class);
                    intent.putExtra("string_key", mList.get(index).getID());
                    intent.putExtra("likes_number",  mList.get(index).getLikeCount());
                    startActivity(intent);
                }else {
                    Toast.makeText(this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_share:
            case R.id.iv_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            case R.id.iv_share_event:
                Intent seIntent = new Intent();
                seIntent.setAction(Intent.ACTION_SEND);
                seIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                seIntent.setType("text/plain");
                startActivity(seIntent);
                break;

            case R.id.iv_add_calender:

                Calendar cal = Calendar.getInstance();
                Intent addcalender = new Intent(Intent.ACTION_EDIT);
                addcalender.setType("vnd.android.cursor.item/event");
                addcalender.putExtra(CalendarContract.Events.TITLE, "Event");
                addcalender.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        cal.getTime().getTime());
                addcalender.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                        cal.getTime().getTime());
                addcalender.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
                addcalender.putExtra(CalendarContract.Events.DESCRIPTION, "Tech Mart Event");
                startActivity(addcalender);
                break;
            case R.id.iv_invite:
                if (AppUtils.isInternetAvailable(this)){
                    mPresenter.attendee(String.valueOf(mList.get(index).getID()),
                            PreferenceHelper.getUserId(DetailsGroupActivity.this), "true", DetailsGroupActivity.this);
                }else {
                    Toast.makeText(this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void networkOperationSuccess(NetworkResponse<LikeData> networkResponse) {
        LikeData userNetworkData = (LikeData) networkResponse.data;
        int errorCode = userNetworkData.ISResultHasData;

        changeLike();
    }


    @Override
    public void networkOperationFail(Throwable throwable) {

    }

    void changeLike() {
        setValues();

    }

    @Override
    public void showProgress() {
        indicatorView.setVisibility(View.VISIBLE);
        indicatorView.show();
    }

    @Override
    public void hideProgress() {
        indicatorView.hide();
    }

    @Override
    public void updateUi() {
        if (mList.get(index).getIsGoing()) {
            mList.get(index).setIsGoing(false);

        } else {
            mList.get(index).setIsGoing(true);

        }

        mGoing.setText("Attending");
        mGoing.setClickable(false);
        mGoing.setEnabled(false);
    }
}
