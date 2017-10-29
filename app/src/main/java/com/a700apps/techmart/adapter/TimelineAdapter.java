package com.a700apps.techmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.LikeData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.screens.comment.CommentActivity;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.mygroup.MyGroupsListFragment;
import com.a700apps.techmart.ui.screens.profile.MemberProfile;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsActivity;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.Globals;
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
                viewHolderEvent.mDateTextView.setText(timeLineItem.getPostedByName());
                viewHolderEvent.mDescribtionTextView.setText(timeLineItem.getDescr());
                viewHolderEvent.mTitleTextView.setText(timeLineItem.getTitle());

                viewHolderEvent.contain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Globals.R_Index = position;
                        openDetails(context, "Event", mTimeLineList, position);
                    }
                });
                viewHolderEvent.shareBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, Globals.ShareLink);

                        sendIntent.setType("text/plain");
                        context.startActivity(sendIntent);
                    }
                });
                viewHolderEvent.tv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, Globals.ShareLink);
                        sendIntent.setType("text/plain");
                        context.startActivity(Intent.createChooser(sendIntent, "Select"));
                    }
                });

                viewHolderEvent.addCalenderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        intent.setType("vnd.android.cursor.item/event");
                        intent.putExtra(CalendarContract.Events.TITLE, timeLineItem.getTitle());
                        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                timeLineItem.getStartDate());
                        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                timeLineItem.getEndDate());
                        intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
                        intent.putExtra(CalendarContract.Events.DESCRIPTION, timeLineItem.getDescr());
                        context.startActivity(intent);
                    }
                });

                viewHolderEvent.tv_add_calender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        intent.setType("vnd.android.cursor.item/event");
                        intent.putExtra(CalendarContract.Events.TITLE, timeLineItem.getTitle());
                        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                timeLineItem.getStartDate());
                        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                timeLineItem.getEndDate());
                        intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
                        intent.putExtra(CalendarContract.Events.DESCRIPTION, timeLineItem.getDescr());
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

                if (timeLineItem.getLikeCount()==0){
                    viewHolderPost.tv_like.setText("Like");
                }else if (timeLineItem.getLikeCount()==1){
                    viewHolderPost.tv_like.setText("1 Like");
                }else {
                    viewHolderPost.tv_like.setText(timeLineItem.getLikeCount() + " Likes");
                }


                if (timeLineItem.getCommentCount()==0){
                    viewHolderPost.tv_comment.setText("Comment");
                }else if (timeLineItem.getCommentCount()==1){
                    viewHolderPost.tv_comment.setText("1 Comment");
                }else {
                    viewHolderPost.tv_comment.setText(timeLineItem.getCommentCount() + " Comments");
                }


                viewHolderPost.moreImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openDetails(context, "post", mTimeLineList, position);
                    }
                });
                viewHolderPost.contain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Globals.R_Index = position;
                        openDetails(context, "post", mTimeLineList, position);
                    }
                });

                viewHolderPost.mPostedByTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ActivityUtils.openActivity(context, EditProfileActivity.class, false);
                        Intent intent = new Intent(context, MemberProfile.class);
                        intent.putExtra("RelativId", String.valueOf(timeLineItem.getCreatedBY()));
                        intent.putExtra("GroupId", timeLineItem.getGroupID());
                        context.startActivity(intent);
                    }
                });

                viewHolderPost.mGroupNameTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        ActivityUtils.openActivity(context, MyGroubListActivity.class, false);
                        ((HomeActivity) context).addFragmentToBackStack(((HomeActivity) context).getSupportFragmentManager(), R.id.fragment_container, new MyGroupsListFragment(), false, false);

                    }
                });

                viewHolderPost.shareBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, Globals.ShareLink);
                        sendIntent.setType("text/plain");
                        context.startActivity(Intent.createChooser(sendIntent, "Select"));

                    }
                });

                viewHolderPost.tv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, Globals.ShareLink);
                        sendIntent.setType("text/plain");
                        context.startActivity(Intent.createChooser(sendIntent, "Select"));

                    }
                });
                viewHolderPost.tv_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (AppUtils.isInternetAvailable(context)) {
                            positionItem = position;

                            if (timeLineItem.getIsLike()) {
                                isLike = "false";
                            } else {
                                isLike = "true";
                            }
                            getLike(timeLineItem);
                        } else {
                            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                Glide.with(context)
                        .load(MainApi.IMAGE_IP + timeLineItem.getImage()).placeholder(R.drawable.placeholder)
                        .into(viewHolderPost.mPostImageView);

                viewHolderPost.mComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, CommentActivity.class);
                        intent.putExtra("string_key", mTimeLineList.get(position).getID());
                        intent.putExtra("likes_number", mTimeLineList.get(position).getLikeCount());
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

                        if (AppUtils.isInternetAvailable(context)) {
                            positionItem = position;
                            if (timeLineItem.getIsLike()) {
                                isLike = "false";
                            } else {
                                isLike = "true";
                            }
                            getLike(timeLineItem);

                        } else {
                            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                        }
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
        boolean success = userNetworkData.likeData.success;

        if (!success) {
            networkOperationFail(new Throwable(userNetworkData.likeData.message));
        } else {
            changeLike();
        }

    }


    @Override
    public void networkOperationFail(Throwable throwable) {

        Toast.makeText(context, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
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
        TextView mTitleTextView, mDescribtionTextView, mDateTextView, mGroupNameTextView, tv_share, tv_add_calender;
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
        setValues();
    }

    void setValues() {
        int count = mTimeLineList.get(positionItem).getLikeCount();

        if (mTimeLineList.get(positionItem).getIsLike()) {
            mTimeLineList.get(positionItem).setIsLike(false);
            mTimeLineList.get(positionItem).setLikeCount(--count);
            notifyDataSetChanged();
        } else {
            mTimeLineList.get(positionItem).setIsLike(true);
            mTimeLineList.get(positionItem).setLikeCount(++count);
            notifyDataSetChanged();
        }
    }

    void getLike(TimeLineData.ResultEntity timeLine) {
        if (timeLine.getIsLike()) {
            isLike = "false";
        } else {
            isLike = "true";
        }
        try {
            JSONObject registerBody = MainApiHelper.getUserLike(PreferenceHelper.getUserId(context), timeLine.getID(), isLike
            );
            MainApi.getLikeTimeLine(registerBody, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


