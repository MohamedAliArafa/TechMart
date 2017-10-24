package com.a700apps.techmart.ui.screens.timelinedetails;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.TimelineAdapter;
import com.a700apps.techmart.data.model.LikeData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.screens.comment.CommentActivity;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.mygroup.MyGroubListActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.CustomTextView;
import com.a700apps.techmart.utils.DateTimePicker.CustomLightTextView;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by samir salah on 8/17/2017.
 */

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener, NetworkResponseListener<LikeData>, DetailsView {
    ImageView imageView4;
    ImageView iv_slider, mProfileImageView, mNotificationImageView, iv_comment, iv_share, mLikeImageView, iv_share_event, iv_invite, iv_calender;
    LinearLayout mLikeLinearContainer;
    RelativeLayout mEventLinearContainer;
    TextView mTitle, mDescTextView, mEventTitle, tv_comment, tv_share, tv_like,
            tv_going, tv_calender, tv_tie, tvTime;
    CustomTextView mSlidertype, mSlideTitle;
    String Type;
    int mTypeFromPager;
    int index;
    List<TimeLineData.ResultEntity> mList;
    CustomTextView next, back;
    String isLike;
    DetailsPresenter mPresenter;
    Dialog dialogsLoading;

    TextView mGoing;
    TextView mLikeCount, mCommentCount, mShareCount;

    LinearLayout ll_events_time_container;
    TextView tvTmieSlider;

//    TextView sliderNumGoing;
//    LinearLayout linearCount;
//    Button going , notGoing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_details);

        mPresenter = new DetailsPresenter();
        mPresenter.attachView(this);

        index = getIntent().getIntExtra("Index", 0);
        mList = getIntent().getParcelableArrayListExtra("Timeline");


//        going = findViewById(R.id.btn_go);
//        notGoing = findViewById(R.id.btn_not_go);
//        sliderNumGoing = findViewById(R.id.slider_num_attend);
//        linearCount = findViewById(R.id.linearCount);

        tvTmieSlider = findViewById(R.id.tv_time);
        ll_events_time_container = findViewById(R.id.ll_events_time_container);

//        indicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        mGoing = (TextView) findViewById(R.id.textView53);
        iv_invite = (ImageView) findViewById(R.id.iv_invite);
        mProfileImageView = (ImageView) findViewById(R.id.new_message);
        iv_slider = (ImageView) findViewById(R.id.iv_slider);
        mNotificationImageView = (ImageView) findViewById(R.id.new_profile);
        mEventTitle = (CustomLightTextView) findViewById(R.id.tv_event_title);
        mLikeCount = (CustomLightTextView) findViewById(R.id.tv_like_count);
        mCommentCount = (CustomLightTextView) findViewById(R.id.tv_comment_count);
        mShareCount = (CustomLightTextView) findViewById(R.id.tv_share_count);
        mEventTitle = (CustomLightTextView) findViewById(R.id.tv_event_title);
        mLikeCount = (TextView) findViewById(R.id.tv_like_count);
        mCommentCount = (TextView) findViewById(R.id.tv_comment_count);
        mShareCount = (TextView) findViewById(R.id.tv_share_count);
        tv_tie = (TextView) findViewById(R.id.tv_tie);
        tvTime = (TextView) findViewById(R.id.tv_3);

        next = (CustomTextView) findViewById(R.id.next);
        back = (CustomTextView) findViewById(R.id.back);
        tv_comment = (CustomLightTextView) findViewById(R.id.tv_comment);
        tv_share = (CustomLightTextView) findViewById(R.id.tv_share);
        tv_like = (CustomLightTextView) findViewById(R.id.tv_like);
        tv_going = (TextView) findViewById(R.id.textView53);
        tv_calender = (TextView) findViewById(R.id.tv_calender);

        imageView4 = (ImageView) findViewById(R.id.imageView4);
        iv_comment = (ImageView) findViewById(R.id.iv_comment);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_share_event = (ImageView) findViewById(R.id.iv_share_event);
        iv_invite = (ImageView) findViewById(R.id.iv_invite);
        iv_calender = (ImageView) findViewById(R.id.iv_calender);

        mLikeImageView = (ImageView) findViewById(R.id.iv_like);


        mGoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(DetailsActivity.this)) {
                    mGoing.setClickable(false);
//                    going.setClickable(false);
                    mGoing.setText("Joined");
                    mPresenter.attendee(String.valueOf(mList.get(index).getID()), PreferenceHelper.getUserId(DetailsActivity.this), "true", DetailsActivity.this);
                } else {
                    Toast.makeText(DetailsActivity.this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        iv_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(DetailsActivity.this)) {
                    mGoing.setClickable(false);
                    mGoing.setText("Joined");
                    mPresenter.attendee(String.valueOf(mList.get(index).getID()), PreferenceHelper.getUserId(DetailsActivity.this), "true", DetailsActivity.this);

                } else {
                    Toast.makeText(DetailsActivity.this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mTitle = (CustomLightTextView) findViewById(R.id.tv_event_name);
        mDescTextView = (CustomLightTextView) findViewById(R.id.tv_events_desc);
        mDescTextView.setMovementMethod(new ScrollingMovementMethod());
        mSlideTitle = (CustomTextView) findViewById(R.id.tv_events_title);
        mSlidertype = (CustomTextView) findViewById(R.id.tv_events);

        mLikeLinearContainer = (LinearLayout) findViewById(R.id.container);
        mEventLinearContainer = (RelativeLayout) findViewById(R.id.container_event);

        iv_comment.setOnClickListener(this);
        tv_comment.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        tv_comment.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_calender.setOnClickListener(this);
        iv_share_event.setOnClickListener(this);
        iv_invite.setOnClickListener(this);


        tv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mList.get(index).getIsLike()) {
                    isLike = "false";
                } else {
                    isLike = "true";
                }
                getLike(mList.get(index));

            }
        });

        mLikeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mList.get(index).getIsLike()) {
                    isLike = "false";
                } else {
                    isLike = "true";
                }

                getLike(mList.get(index));

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (++index < mList.size()) {
//                    finish();
//                    Intent intent = new Intent(DetailsActivity.this, DetailsActivity.class);
//                    intent.putExtra("Type", Type);
//                    intent.putExtra("Index", index);
//                    intent.putParcelableArrayListExtra("Timeline", (ArrayList<? extends Parcelable>) mList);
//                    startActivity(intent);




                    fillData();
                } else {
                    finish();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (--index >= 0) {
//                    finish();
//                    Intent intent = new Intent(DetailsActivity.this, DetailsActivity.class);
//                    intent.putExtra("Type", Type);
//                    intent.putExtra("Index", index - 1);
//                    intent.putParcelableArrayListExtra("Timeline", (ArrayList<? extends Parcelable>) mList);
//                    startActivity(intent);

                    fillData();
                } else {
                    finish();
                }
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ActivityUtils.openActivity(DetailsActivity.this, HomeActivity.class, true);
                onBackPressed();
            }
        });


        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityUtils.openActivity(DetailsActivity.this, EditProfileActivity.class, false);
            }
        });

        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(DetailsActivity.this, NotificationActivity.class, false);
            }
        });

//        sliderNumGoing.setText(""+mList.get(index).getAttendantCount());
        fillData();

    }

    private void fillData(){
        if (mList.get(index).getType() == 1) {
            Type = "Event";
        } else {
            Type = "post";
        }

        Glide.with(DetailsActivity.this)
                .load(MainApi.IMAGE_IP + mList.get(index).getImage()).placeholder(R.drawable.placeholder)
                .into(iv_slider);


        if (mList.get(index).getIsGoing()) {
            mGoing.setText("Joined");
            mGoing.setEnabled(false);
            mGoing.setClickable(false);
        }

        if (mList.get(index).getIsLike()) {
            mLikeImageView.setImageResource(R.drawable.ic_like_active);
        } else {
            mLikeImageView.setImageResource(R.drawable.ic_like);
        }

        tvTmieSlider.setText(mList.get(index).getStartDate());
        mTitle.setText(mList.get(index).getPostedByName());
        mDescTextView.setText(mList.get(index).getDescr());
        mEventTitle.setText(mList.get(index).getTitle());
        tvTime.setText("Starts: " + mList.get(index).getStartDate());
        mSlideTitle.setText(mList.get(index).getTitle());
        Log.e("StartDate", "Date : " + mList.get(index).getStartDate());
        mLikeCount.setText("" + mList.get(index).getLikeCount());
        mCommentCount.setText("" + mList.get(index).getCommentCount());
        tv_tie.setText("Location: " + getCompleteAddressString(mList.get(index).getLatitude(), mList.get(index).getLongtude()));

        if (Type.equals("post")) {
            mLikeLinearContainer.setVisibility(View.VISIBLE);
            mEventLinearContainer.setVisibility(View.GONE);
//            going.setVisibility(View.GONE);
//            notGoing .setVisibility(View.GONE);
//            sliderNumGoing .setVisibility(View.GONE);
//            linearCount.setVisibility(View.GONE);
            mSlidertype.setText("Post");
        } else {
            mLikeLinearContainer.setVisibility(View.GONE);
            mEventLinearContainer.setVisibility(View.VISIBLE);
            ll_events_time_container.setVisibility(View.VISIBLE);
//            going.setVisibility(View.VISIBLE);
//            notGoing .setVisibility(View.VISIBLE);
//            sliderNumGoing .setVisibility(View.VISIBLE);
//            linearCount.setVisibility(View.VISIBLE);
            mSlidertype.setText("Event");
            if (mList.get(index).getIsGoing()) {
                tv_going.setText("Joined");
//                going.setBackground(getResources().getDrawable(R.drawable.bg_blueish));
//                notGoing.setBackground(getResources().getDrawable(R.drawable.bg_transparent_rounded_small));
            } else {
//                notGoing.setBackground(getResources().getDrawable(R.drawable.bg_blueish));
//                going.setBackground(getResources().getDrawable(R.drawable.bg_transparent_rounded_small));
            }
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {

        Log.e("Cordinates", "" + LATITUDE + "    " + LONGITUDE);
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

//                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                if (returnedAddress.getAddressLine(0) != null && !returnedAddress.getAddressLine(0).isEmpty()) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(0)).append(",");
                }
//                }
                strAdd = strReturnedAddress.toString();
                Log.w("Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.w("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    void setValues() {
        int count = Integer.parseInt(mLikeCount.getText().toString());

        if (mList.get(index).getIsLike()) {
            mList.get(index).setIsLike(false);
            mLikeImageView.setImageResource(R.drawable.ic_like);
            mLikeCount.setText(--count + "");
        } else {
            mList.get(index).setIsLike(true);
            mLikeImageView.setImageResource(R.drawable.ic_like_active);
            mLikeCount.setText(++count + "");
        }
        mList.get(index).setLikeCount(count);
    }

    void getLike(TimeLineData.ResultEntity timeLine) {
        try {
            JSONObject registerBody = MainApiHelper.getUserLike(PreferenceHelper.getUserId(this), timeLine.getID(), isLike
            );
            MainApi.getLikeTimeLine(registerBody, DetailsActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_comment:
                if (AppUtils.isInternetAvailable(DetailsActivity.this)) {
                    Intent intent2 = new Intent(DetailsActivity.this, CommentActivity.class);
                    intent2.putExtra("string_key", mList.get(index).getID());
                    intent2.putExtra("likes_number", mList.get(index).getLikeCount());
                    startActivity(intent2);
                } else {
                    Toast.makeText(DetailsActivity.this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.iv_comment:
                if (AppUtils.isInternetAvailable(DetailsActivity.this)) {
                    Intent intent = new Intent(DetailsActivity.this, CommentActivity.class);
                    intent.putExtra("string_key", mList.get(index).getID());
                    intent.putExtra("likes_number", mList.get(index).getLikeCount());
                    startActivity(intent);
                } else {
                    Toast.makeText(DetailsActivity.this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_share:
            case R.id.iv_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, Globals.ShareLink);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Select"));
                break;
            case R.id.iv_share_event:
                Intent seIntent = new Intent();
                seIntent.setAction(Intent.ACTION_SEND);
                seIntent.putExtra(Intent.EXTRA_TEXT, Globals.ShareLink);

                seIntent.setType("text/plain");
                startActivity(Intent.createChooser(seIntent, "Select"));
                break;
            case R.id.tv_calender:
            case R.id.iv_calender:

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
                if (AppUtils.isInternetAvailable(DetailsActivity.this)) {
                    mPresenter.attendee(String.valueOf(mList.get(index).getID()),
                            PreferenceHelper.getUserId(DetailsActivity.this), "true", DetailsActivity.this);

                } else {
                    Toast.makeText(DetailsActivity.this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void networkOperationSuccess(NetworkResponse<LikeData> networkResponse) {
        LikeData userNetworkData = (LikeData) networkResponse.data;
        boolean success = userNetworkData.likeData.success;

        if (!success) {
            networkOperationFail(new Throwable(userNetworkData.likeData.message));
        } else {
            changeLike();
        }

    }


    @Override
    public void networkOperationFail(Throwable throwable) {
        Toast.makeText(this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    void changeLike() {
        setValues();

    }

    @Override
    public void showProgress() {
        dialogsLoading = new loadingDialog().showDialog(this);
    }

    @Override
    public void hideProgress() {
        dialogsLoading.dismiss();
    }

    @Override
    public void updateUi() {
        if (mList.get(index).getIsGoing()) {
            mList.get(index).setIsGoing(false);

        } else {
            mList.get(index).setIsGoing(true);

        }

        Toast.makeText(this, "You joined event  " + mList.get(index).getTitle() + " Successfully", Toast.LENGTH_SHORT).show();
        mGoing.setText("Joined");
        mGoing.setClickable(false);
        mGoing.setEnabled(false);
    }
}