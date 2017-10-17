package com.a700apps.techmart.ui.screens.notification.Ui.PostLikes;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.LikeData;
import com.a700apps.techmart.data.model.NoficationData;
import com.a700apps.techmart.data.model.NotificationDataLike;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.screens.comment.CommentActivity;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsActivity;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsPresenter;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsView;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostDetailsNotificationFragment extends Fragment implements View.OnClickListener,NetworkResponseListener<LikeData>, DetailsView {

    ImageView imageView4;
    ImageView iv_slider, mProfileImageView, mNotificationImageView, iv_comment, iv_share, mLikeImageView, iv_share_event, iv_invite, iv_calender;
    LinearLayout mLikeLinearContainer, mEventLinearContainer;
    TextView mTitle, mSlidertype, mSlideTitle, mDescTextView, tv_comment, tv_share, tv_like, tv_going, tv_calender;
    String Type;
    int index;
    //    List<TimeLineData.ResultEntity> mList;
    String isLike;
    NotificationDataLike eventResult;

    DetailsPresenter mPresenter;
    public AVLoadingIndicatorView indicatorView;
    TextView mGoing;

    Dialog dialog;

    public PostDetailsNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_details_notification, container, false);
        indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        mGoing = (TextView) view.findViewById(R.id.textView53);
        iv_invite = (ImageView) view.findViewById(R.id.iv_invite);
        mProfileImageView = (ImageView) view.findViewById(R.id.new_message);
        iv_slider = (ImageView) view.findViewById(R.id.iv_slider);
        mNotificationImageView = (ImageView) view.findViewById(R.id.new_profile);
        tv_comment = (TextView) view.findViewById(R.id.tv_comment);
        tv_share = (TextView) view.findViewById(R.id.tv_share);
        tv_like = (TextView) view.findViewById(R.id.tv_like);
        tv_going = (TextView) view.findViewById(R.id.textView53);
        tv_calender = (TextView) view.findViewById(R.id.tv_calender);

        imageView4 = (ImageView) view.findViewById(R.id.imageView4);
        iv_comment = (ImageView) view.findViewById(R.id.iv_comment);
        iv_share = (ImageView) view.findViewById(R.id.iv_share);
        iv_share_event = (ImageView) view.findViewById(R.id.iv_share_event);
        iv_invite = (ImageView) view.findViewById(R.id.iv_invite);
        iv_calender = (ImageView) view.findViewById(R.id.iv_calender);

        mLikeImageView = (ImageView) view.findViewById(R.id.iv_like);


        mTitle = (TextView) view.findViewById(R.id.tv_event_title);
        mDescTextView = (TextView) view.findViewById(R.id.tv_events_desc);
        mSlideTitle = (TextView) view.findViewById(R.id.tv_events_title);
        mSlidertype = (TextView) view.findViewById(R.id.tv_events);

        mLikeLinearContainer = (LinearLayout) view.findViewById(R.id.container);
        mEventLinearContainer = (LinearLayout) view.findViewById(R.id.container_event);

        iv_comment.setOnClickListener(this);
        tv_comment.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        tv_comment.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_calender.setOnClickListener(this);
        iv_share_event.setOnClickListener(this);
        iv_invite.setOnClickListener(this);
        eventResult= new NotificationDataLike();


        final NoficationData.Result data = (NoficationData.Result) getArguments().getSerializable("data");
        int type = getArguments().getInt("type");


        // if event
        if (type == 1) {
            hideViewIfTypeEvent(view);
        }


        getPostData(data.getItemID(), type, data.getUserID());
        view.findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        Glide.with(this)
                .load(MainApi.IMAGE_IP + data.getIcon()).placeholder(R.drawable.placeholder)
                .into(iv_slider);




//        {"PostID":46,"Type":1,"UserID":"afc1c0a1-9af9-4a40-bdc3-3379a206bf8d"}
//        getPostData(46, 1,"afc1c0a1-9af9-4a40-bdc3-3379a206bf8d");
        return view;
    }

    private void hideViewIfTypeEvent(View view) {
        view.findViewById(R.id.container).setVisibility(View.GONE);
        view.findViewById(R.id.container_event).setVisibility(View.VISIBLE);
        mSlidertype.setText("Event");
    }

    void getPostData(int postid, int type, String userId) {
        try {
            JSONObject body = MainApiHelper.getTimeLineById(postid, type, userId);
            MainApi.getTimeLineById(body, new NetworkResponseListener<NotificationDataLike>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<NotificationDataLike> networkResponse) {
                    if (networkResponse.data.getISResultHasData() == 1) {
                        eventResult = networkResponse.data;
                        mTitle.setText(networkResponse.data.getResult().getTitle());
                        mDescTextView.setText(networkResponse.data.getResult().getDescr());

                        mSlideTitle.setText(networkResponse.data.getResult().getTitle());
                        if (eventResult.getResult().getIsGoing()) {
                            mGoing.setText("Attending");
                            mGoing.setEnabled(false);
                            mGoing.setClickable(false);
                        }

                        mGoing.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mGoing.setClickable(false);
                                mGoing.setText("Attending");
                                attendee(String.valueOf(eventResult.getResult().getID()), PreferenceHelper.getUserId(getActivity()), "true", getActivity());
                            }
                        });

                        iv_invite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mGoing.setClickable(false);
                                mGoing.setText("Attending");
                                attendee(String.valueOf(eventResult.getResult().getID()), PreferenceHelper.getUserId(getActivity()), "true", getActivity());
                            }
                        });


                        if (eventResult.getResult().getIsLike()) {
                            mLikeImageView.setImageResource(R.drawable.ic_like_active);
                        } else {
                            mLikeImageView.setImageResource(R.drawable.ic_like);

                        }
                        tv_like.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (eventResult.getResult().getIsLike()) {
                                    eventResult.getResult().setIsLike(false);
                                    isLike = "false";
                                    mLikeImageView.setImageResource(R.drawable.ic_like);
                                } else {
                                    isLike = "true";
                                    eventResult.getResult().setIsLike(true);
                                    mLikeImageView.setImageResource(R.drawable.ic_like_active);
                                }
                                getLike();

                            }
                        });

                        mLikeImageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (eventResult.getResult().getIsLike()) {
                                    eventResult.getResult().setIsLike(false);
                                    isLike = "false";
                                    mLikeImageView.setImageResource(R.drawable.ic_like);
                                } else {
                                    isLike = "true";
                                    eventResult.getResult().setIsLike(true);
                                    mLikeImageView.setImageResource(R.drawable.ic_like_active);
                                }
                                getLike();

                            }
                        });

                    } else {
                        networkOperationFail(new Throwable("Error happened while getting your data .. please try again"));
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {
                    Toast.makeText(getActivity(), "Error happened  " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    throwable.printStackTrace();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //
    void attendee(String EventID, String UserID, String IsGoin, Context context) {

//        mContext = context;
//        view.showProgress();

        dialog = new loadingDialog().showDialog(getActivity());
        try {
            JSONObject registerBody = MainApiHelper.sendAttendee(EventID, UserID, IsGoin);
            MainApi.sendAttendee(registerBody, new NetworkResponseListener<PostData>() {
                @Override
                public void networkOperationSuccess(NetworkResponse<PostData> networkResponse) {
//                    hideProgress();
                    dialog.dismiss();
//                    if (isDetachView()) return;
                    PostData userNetworkData = (PostData) networkResponse.data;
                    int errorCode = userNetworkData.ISResultHasData;

                    if (errorCode == 1) {

                        if (eventResult.getResult().getIsGoing()) {
                            eventResult.getResult().setIsGoing(false);

                        } else {
                            eventResult.getResult().setIsGoing(true);

                        }

                        mGoing.setText("Attending");
                        mGoing.setClickable(false);
                        mGoing.setEnabled(false);
                    }
                }

                @Override
                public void networkOperationFail(Throwable throwable) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
//            view.hideProgress();
            dialog.dismiss();
        }

    }
    void getLike() {
        try {
            JSONObject registerBody = MainApiHelper.getUserLike(PreferenceHelper.getUserId(getActivity()), eventResult.getResult().getID(), isLike
            );
            MainApi.getLikeTimeLine(registerBody,this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_comment:
            case R.id.iv_comment:
                Intent intent = new Intent(getActivity(), CommentActivity.class);
                intent.putExtra("string_key", eventResult.getResult().getID());
                startActivity(intent);
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

        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void updateUi() {

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

    void setValues() {
        if (eventResult.getResult().getIsLike()) {
            eventResult.getResult().setIsLike(false);

        } else {
            eventResult.getResult().setIsLike(true);

        }
    }
}
