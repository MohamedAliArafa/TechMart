package com.a700apps.techmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.a700apps.techmart.ui.screens.mygroup.MyGroubListActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileFragment;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsActivity;
import com.a700apps.techmart.utils.ActivityUtils;
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
 * Created by samir salah on 9/13/2017.
 */

public class GroupTimeLineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements NetworkResponseListener<LikeData> {
    private List<TimeLineData.ResultEntity> mTimeLineList;
    Context context;
    private static final int NOTIF_TYPE_EVENT = 1;
    private static final int NOTIF_TYPE_POST = 2;
    private static final int NOTIF_TYPE_LOAD = 3;
    private int mType;

    boolean isLoadingAdded = false;
    
    String isLike;
    View noteView;
    int positionItem;
    public GroupTimeLineAdapter(Context context, List<TimeLineData.ResultEntity> TimeLineList) {
        this.context = context;
        this.mTimeLineList = TimeLineList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView;
        switch (viewType) {
            case NOTIF_TYPE_EVENT:
                noteView = inflater.inflate(R.layout.timeline_first_item, parent, false);
                return new GroupTimeLineAdapter.ViewHolder(noteView);

            case NOTIF_TYPE_POST:
                noteView = inflater.inflate(R.layout.timeline_item_post, parent, false);
                return new GroupTimeLineAdapter.ViewHolderPost(noteView);

            case NOTIF_TYPE_LOAD:
                noteView = inflater.inflate(R.layout.item_progress, parent, false);
                return new LoadingVH(noteView);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final TimeLineData.ResultEntity timeLineItem = mTimeLineList.get(position);
        final int itemType = getItemViewType(position);

        switch (itemType) {
            case NOTIF_TYPE_EVENT:
                GroupTimeLineAdapter.ViewHolder viewHolderEvent = (GroupTimeLineAdapter.ViewHolder) viewHolder;
                viewHolderEvent.mDateTextView.setText(timeLineItem.getGroupName());
                viewHolderEvent.tv_username.setText(timeLineItem.getPostedByName());
                viewHolderEvent.mDescribtionTextView.setText(timeLineItem.getDescr());
                viewHolderEvent.mTitleTextView.setText(timeLineItem.getTitle());

                if (AppUtils.isEventInCal(context,timeLineItem.getTitle())){
                    Log.e("added to calender",AppUtils.isEventInCal(context,timeLineItem.getTitle())+"");
                    viewHolderEvent.tv_add_calender.setText("Added to calendar");
                    viewHolderEvent.tv_add_calender.setEnabled(false);
                    viewHolderEvent.tv_add_calender.setTextColor(context.getResources().getColor(R.color.light_color_text));
                }

                viewHolderEvent.contain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Globals.R_Index_group = position;
                        openDetails(context, "Event", mTimeLineList, position);
                    }
                });
                viewHolderEvent.shareBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, Globals.ShareLink);
                        shareIntent.setType("text/plain");
                        context.startActivity(Intent.createChooser(shareIntent, "Select"));
                    }
                });
                viewHolderEvent.tv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, Globals.ShareLink);
                        shareIntent.setType("text/plain");
                        context.startActivity(Intent.createChooser(shareIntent, "Select"));
                    }
                });

                viewHolderEvent.addCalenderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        intent.setType("vnd.android.cursor.item/event");
                        intent.putExtra(CalendarContract.Events.TITLE,  timeLineItem.getTitle());
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
                final GroupTimeLineAdapter.ViewHolderPost viewHolderPost = (GroupTimeLineAdapter.ViewHolderPost) viewHolder;
                viewHolderPost.mDescribtionTextView.setText(timeLineItem.getDescr());
                viewHolderPost.mPostedByTextView.setText(timeLineItem.getPostedByName());
                viewHolderPost.mTitleTextView.setText(timeLineItem.getTitle());
                viewHolderPost.mGroupNameTextView.setText(timeLineItem.getGroupName());

                if (timeLineItem.getLikeCount() == 0) {
                    viewHolderPost.tv_like.setText("Like");
                } else if (timeLineItem.getLikeCount() == 1) {
                    viewHolderPost.tv_like.setText("1 Like");
                } else {
                    viewHolderPost.tv_like.setText(timeLineItem.getLikeCount() + " Likes");
                }


                if (timeLineItem.getCommentCount() == 0) {
                    viewHolderPost.tv_comment.setText("Comment");
                } else if (timeLineItem.getCommentCount() == 1) {
                    viewHolderPost.tv_comment.setText("1 Comment");
                } else {
                    viewHolderPost.tv_comment.setText(timeLineItem.getCommentCount() + " Comments");
                }

                viewHolderPost.moreImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Globals.R_Index_group = position;
                        openDetails(context, "post", mTimeLineList, position);
                    }
                });

                viewHolderPost.contain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Globals.R_Index_group = position;
                        openDetails(context, "post", mTimeLineList, position);
                    }
                });
                viewHolderPost.mPostedByTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ActivityUtils.openActivity(context, EditProfileActivity.class, false);
                        ((HomeActivity) context).openFragment(EditProfileFragment.class, null);
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
            case NOTIF_TYPE_LOAD:
                final GroupTimeLineAdapter.LoadingVH loadHolder = (GroupTimeLineAdapter.LoadingVH) viewHolder;
                loadHolder.progressBar.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mTimeLineList.get(position) == null){
            return 3;
        }else {
            switch (mTimeLineList.get(position).getType()) {
                case 1:
                    return NOTIF_TYPE_EVENT;
                case 2:
                    return NOTIF_TYPE_POST;
            }
        }

        return 3;
    }
    @Override
    public int getItemCount() {
        return mTimeLineList==null? 0 : mTimeLineList.size();
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
    void changeLike() {
        setValues();

    }
    public class ViewHolderPost extends RecyclerView.ViewHolder {
        ImageView mPostImageView, addCalenderBtn, mLikeImageView, mComment, shareBtn, moreImageView;
        ;
        TextView mTitleTextView, mDescribtionTextView, mPostedByTextView, mGroupNameTextView, tv_comment, tv_like, tv_share;
        ConstraintLayout contain;

        public ViewHolderPost(View itemView) {
            super(itemView);
            contain = (ConstraintLayout) itemView.findViewById(R.id.contain);
            mLikeImageView = (ImageView) itemView.findViewById(R.id.iv_like);
            moreImageView = (ImageView) itemView.findViewById(R.id.imageView34);
            mComment = (ImageView) itemView.findViewById(R.id.iv_comment);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            mPostImageView = (ImageView) itemView.findViewById(R.id.iv_post);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tv_title);
            mDescribtionTextView = (TextView) itemView.findViewById(R.id.tv_desc);
            mPostedByTextView = (TextView) itemView.findViewById(R.id.tv_postedby);
            mGroupNameTextView = (TextView) itemView.findViewById(R.id.tv_group_name);
            tv_like = (TextView) itemView.findViewById(R.id.tv_like);
            shareBtn = (ImageView) itemView.findViewById(R.id.iv_share);
            tv_share = (TextView) itemView.findViewById(R.id.tv_share);

        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mEventImageView, shareBtn, addCalenderBtn;
        TextView mTitleTextView, mDescribtionTextView, mDateTextView, mGroupNameTextView, tv_add_calender, tv_share, tv_username;
        RelativeLayout contain;

        public ViewHolder(View itemView) {
            super(itemView);
            contain = (RelativeLayout) itemView.findViewById(R.id.contain);
            tv_add_calender = (TextView) itemView.findViewById(R.id.tv_add_calender);

            mEventImageView = (ImageView) itemView.findViewById(R.id.iv_event);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tv_title);
            mDescribtionTextView = (TextView) itemView.findViewById(R.id.tv_description);
            mDateTextView = (TextView) itemView.findViewById(R.id.tv_date);
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);

            shareBtn = (ImageView) itemView.findViewById(R.id.iv_share);
            tv_share = (TextView) itemView.findViewById(R.id.tv_share);

            addCalenderBtn = (ImageView) itemView.findViewById(R.id.iv_add_calender);
            itemView.setOnClickListener(this);
            shareBtn.setOnClickListener(this);
            addCalenderBtn.setOnClickListener(this);
            itemView.findViewById(R.id.tv_share).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            int viewId = v.getId();

        }
    }
    public class LoadingVH extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingVH(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.loadmore_progress);
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


    public void add(TimeLineData.ResultEntity mc) {
        mTimeLineList.add(mc);
        notifyItemInserted(mTimeLineList.size() - 1);
    }

    public void addAll(List<TimeLineData.ResultEntity> mcList) {
        for (TimeLineData.ResultEntity mc : mcList) {
            add(mc);
        }
    }

    public void remove(TimeLineData.ResultEntity city) {
        int position = mTimeLineList.indexOf(city);
        if (position > -1) {
            mTimeLineList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        TimeLineData.ResultEntity load = new TimeLineData.ResultEntity();
        load.setType(3);
        add(load);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mTimeLineList.size() - 1;
        TimeLineData.ResultEntity item = getItem(position);

        if (item != null) {
            mTimeLineList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public TimeLineData.ResultEntity getItem(int position) {
        return mTimeLineList.get(position);
    }
}


