package com.a700apps.techmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.LikeData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.screens.comment.CommentActivity;
import com.a700apps.techmart.ui.screens.groupmemberdetails.GroupActivity;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupsTimLineActivity;
import com.a700apps.techmart.ui.screens.message.ChatActivity;
import com.a700apps.techmart.ui.screens.mygroup.MyGroubListActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.ui.screens.profile.MemberProfile;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by samir salah on 9/11/2017.
 */

public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements NetworkResponseListener<LikeData> {
    public List<TimeLineData.ResultEntity> mTimeLineList;
    Context context;
    private static final int NOTIF_TYPE_EVENT = 1;
    private static final int NOTIF_TYPE_POST = 2;
    private int mType;
    String isLike;
    View noteView;


    int positionItem;

    public TimelineAdapter(Context context, List<TimeLineData.ResultEntity> TimeLineList) {
        this.context = context;
        this.mTimeLineList = TimeLineList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
//        View noteView;
        switch (viewType) {
            case NOTIF_TYPE_EVENT:
                noteView = inflater.inflate(R.layout.timeline_first_item, parent, false);
                return new ViewHolder(noteView);

            case NOTIF_TYPE_POST:
                noteView = inflater.inflate(R.layout.timeline_item_post, parent, false);
                return new ViewHolderPost(noteView);


        }
        return null;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        positionItem = position;
        final TimeLineData.ResultEntity timeLineItem = mTimeLineList.get(positionItem);
        final int itemType = getItemViewType(positionItem);

        switch (itemType) {
            case NOTIF_TYPE_EVENT:
                ViewHolder viewHolderEvent = (ViewHolder) viewHolder;
                viewHolderEvent.mDateTextView.setText(timeLineItem.getCreationDate());
                viewHolderEvent.mDescribtionTextView.setText(timeLineItem.getDescr());
                viewHolderEvent.mTitleTextView.setText(timeLineItem.getTitle());

                viewHolderEvent.contain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openDetails(context, "Event", mTimeLineList, position);
                    }
                });
                viewHolderEvent.shareBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                        sendIntent.setType("text/plain");
                        context.startActivity(sendIntent);
                    }
                });
                   viewHolderEvent.tv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                    sendIntent.setType("text/plain");
                    context.startActivity(sendIntent);
                }
            });

                viewHolderEvent.addCalenderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        intent.setType("vnd.android.cursor.item/event");
                        intent.putExtra(CalendarContract.Events.TITLE, "Event");
                        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                cal.getTime().getTime());
                        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                cal.getTime().getTime());
                        intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
                        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Tech Mart Event");
                        context.startActivity(intent);
                    }
                });

                viewHolderEvent. tv_add_calender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        intent.setType("vnd.android.cursor.item/event");
                        intent.putExtra(CalendarContract.Events.TITLE, "Event");
                        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                cal.getTime().getTime());
                        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                cal.getTime().getTime());
                        intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
                        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Tech Mart Event");
                        context.startActivity(intent);
                    }
                });
                Glide.with(context)
                        .load(MainApi.IMAGE_IP + timeLineItem.getImage()).placeholder(R.drawable.placeholder)
                        .into(viewHolderEvent.mEventImageView);
                break;
            case NOTIF_TYPE_POST:
                final ViewHolderPost viewHolderPost = (ViewHolderPost) viewHolder;
                viewHolderPost.mDescribtionTextView.setText(timeLineItem.getDescr());
                viewHolderPost.mPostedByTextView.setText(timeLineItem.getPostedByName());
                viewHolderPost.mTitleTextView.setText(timeLineItem.getTitle());
                viewHolderPost.mGroupNameTextView.setText(timeLineItem.getGroupName());


                viewHolderPost.moreImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openDetails(context, "post", mTimeLineList, position);
                    }
                });
                viewHolderPost.contain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openDetails(context, "post", mTimeLineList, position);
                    }
                });

                viewHolderPost.mPostedByTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityUtils.openActivity(context, EditProfileActivity.class, false);
                    }
                });

                viewHolderPost.mGroupNameTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityUtils.openActivity(context, MyGroubListActivity.class, false);

                    }
                });

                viewHolderPost.shareBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                        sendIntent.setType("text/plain");
                        context.startActivity(sendIntent);
                    }
                });

                viewHolderPost.tv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                        sendIntent.setType("text/plain");
                        context.startActivity(sendIntent);
                    }
                });
                viewHolderPost.tv_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (timeLineItem.getIsLike()) {
                            timeLineItem.setIsLike(false);
                            isLike = "false";
                            viewHolderPost.mLikeImageView.setImageResource(R.drawable.ic_like);
                        } else {
                            isLike = "true";
                            timeLineItem.setIsLike(true);
                            viewHolderPost.mLikeImageView.setImageResource(R.drawable.ic_like_active);
                        }
                        getLike(timeLineItem);

                    }
                });
                Glide.with(context)
                        .load(MainApi.IMAGE_IP + timeLineItem.getImage()).placeholder(R.drawable.placeholder)
                        .into(viewHolderPost.mPostImageView);

                viewHolderPost.mComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, CommentActivity.class);
                        intent.putExtra("string_key", timeLineItem.getID());
                        intent.putExtra("likes_number", timeLineItem.getLikeCount());
                        context.startActivity(intent);
                    }
                });

                viewHolderPost.tv_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, CommentActivity.class);
                        intent.putExtra("string_key", timeLineItem.getID());
                        intent.putExtra("likes_number", timeLineItem.getLikeCount());
                        context.startActivity(intent);
                    }
                });

                if (timeLineItem.getIsLike()) {
                    viewHolderPost.mLikeImageView.setImageResource(R.drawable.ic_like_active);
                } else {
                    viewHolderPost.mLikeImageView.setImageResource(R.drawable.ic_like);

                }

                viewHolderPost.mLikeImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (timeLineItem.getIsLike()) {
                            timeLineItem.setIsLike(false);
                            isLike = "false";
                            viewHolderPost.mLikeImageView.setImageResource(R.drawable.ic_like);
                        } else {
                            isLike = "true";
                            timeLineItem.setIsLike(true);
                            viewHolderPost.mLikeImageView.setImageResource(R.drawable.ic_like_active);
                        }
                        getLike(timeLineItem);

                    }
                });

                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = position;
        switch (mTimeLineList.get(position).getType()) {
            case 1:
                return NOTIF_TYPE_EVENT;
            case 2:
                return NOTIF_TYPE_POST;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mTimeLineList.size();
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


    public class ViewHolderPost extends RecyclerView.ViewHolder {
        ImageView mPostImageView, mLikeImageView, mComment, shareBtn, moreImageView, mMoreImageView;
        TextView mTitleTextView, mDescribtionTextView, mPostedByTextView, mGroupNameTextView, tv_comment, tv_like, tv_share;
        ConstraintLayout contain;

        public ViewHolderPost(View itemView) {
            super(itemView);
            contain = (ConstraintLayout) itemView.findViewById(R.id.contain);
            mLikeImageView = (ImageView) itemView.findViewById(R.id.iv_like);
            mMoreImageView = (ImageView) itemView.findViewById(R.id.iv_more);
            moreImageView = (ImageView) itemView.findViewById(R.id.imageView34);
            mComment = (ImageView) itemView.findViewById(R.id.iv_comment);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_like = (TextView) itemView.findViewById(R.id.tv_like);
            tv_share = (TextView) itemView.findViewById(R.id.tv_share);
            mPostImageView = (ImageView) itemView.findViewById(R.id.iv_post);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tv_title);
            mDescribtionTextView = (TextView) itemView.findViewById(R.id.tv_desc);
            mPostedByTextView = (TextView) itemView.findViewById(R.id.tv_postedby);
            mGroupNameTextView = (TextView) itemView.findViewById(R.id.tv_group_name);
            shareBtn = (ImageView) itemView.findViewById(R.id.iv_share);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mEventImageView, shareBtn, addCalenderBtn;
        TextView mTitleTextView, mDescribtionTextView, mDateTextView, mGroupNameTextView,tv_share,tv_add_calender;
        RelativeLayout contain;
        public ViewHolder(View itemView) {
            super(itemView);
            contain = (RelativeLayout) itemView.findViewById(R.id.contain);

            mEventImageView = (ImageView) itemView.findViewById(R.id.iv_event);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tv_title);
            tv_add_calender = (TextView) itemView.findViewById(R.id.tv_add_calender);
            mDescribtionTextView = (TextView) itemView.findViewById(R.id.tv_description);
            mDateTextView = (TextView) itemView.findViewById(R.id.tv_date);
            tv_share = (TextView) itemView.findViewById(R.id.tv_share);

            shareBtn = (ImageView) itemView.findViewById(R.id.iv_share);
            addCalenderBtn = (ImageView) itemView.findViewById(R.id.iv_add_calender);
            itemView.setOnClickListener(this);
            shareBtn.setOnClickListener(this);
            addCalenderBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            int viewId = v.getId();
//
        }
    }

    static void openDetails(Context context, String type, List<TimeLineData.ResultEntity> mTimeLineList, int index) {
//        ActivityUtils.openActivity(context, DetailsActivity.class, false);
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("Type", type);
        intent.putExtra("Index", index);
        intent.putParcelableArrayListExtra("Timeline", (ArrayList<? extends Parcelable>) mTimeLineList);
        context.startActivity(intent);
    }

    void changeLike() {
        setValues(new ViewHolderPost(noteView), noteView);

    }

    void setValues(ViewHolderPost viewholder, View item) {
        if (mTimeLineList.get(positionItem).getIsLike()) {
            mTimeLineList.get(positionItem).setIsLike(false);
            notifyDataSetChanged();
        } else {
            mTimeLineList.get(positionItem).setIsLike(true);
            notifyDataSetChanged();
        }
    }

    void getLike(TimeLineData.ResultEntity timeLine) {
        try {
            JSONObject registerBody = MainApiHelper.getUserLike(PreferenceHelper.getUserId(context), timeLine.getID(), isLike
            );
            MainApi.getLikeTimeLine(registerBody, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


