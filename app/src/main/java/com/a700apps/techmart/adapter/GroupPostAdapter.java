package com.a700apps.techmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsActivity;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsGroupActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir salah on 9/13/2017.
 */


public class GroupPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements NetworkResponseListener<LikeData> {
    private List<TimeLineData.ResultEntity> mTimeLineList;
    Context context;
    String isLike;
    View noteView;
    int positionItem;
    private static final int NOTIF_TYPE_LOAD = 3;
    private static final int NOTIF_TYPE_POST = 2;
    boolean isLoadingAdded = false;

    public GroupPostAdapter(Context context, List<TimeLineData.ResultEntity> TimeLineList) {
        this.context = context;
        this.mTimeLineList = TimeLineList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == NOTIF_TYPE_POST) {
            noteView = inflater.inflate(R.layout.timeline_item_post, parent, false);
//        setValues(new ViewHolderPost(noteView),noteView);
            return new GroupPostAdapter.ViewHolderPost(noteView);
        } else {
            noteView = inflater.inflate(R.layout.item_progress, parent, false);
//        setValues(new ViewHolderPost(noteView),noteView);
            return new GroupPostAdapter.LoadingVH(noteView);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHoldermain, final int position) {
        positionItem = position;
        final TimeLineData.ResultEntity timeLineItem = mTimeLineList.get(positionItem);
        final int itemType = getItemViewType(positionItem);
        Log.e("timeLineItem.getType()", timeLineItem.getType() + "");

        switch (itemType) {
//
            case NOTIF_TYPE_POST:
                GroupPostAdapter.ViewHolderPost viewHolder = (GroupPostAdapter.ViewHolderPost) viewHoldermain;
                viewHolder.mDescribtionTextView.setText(timeLineItem.getDescr());
                viewHolder.mPostedByTextView.setText(timeLineItem.getPostedByName());
                viewHolder.mTitleTextView.setText(timeLineItem.getTitle());
                viewHolder.mGroupNameTextView.setText(timeLineItem.getGroupName());


                if (timeLineItem.getLikeCount() == 0) {
                    viewHolder.tv_like.setText("Like");
                } else if (timeLineItem.getLikeCount() == 1) {
                    viewHolder.tv_like.setText("1 Like");
                } else {
                    viewHolder.tv_like.setText(timeLineItem.getLikeCount() + " Likes");
                }


                if (timeLineItem.getCommentCount() == 0) {
                    viewHolder.tv_comment.setText("Comment");
                } else if (timeLineItem.getCommentCount() == 1) {
                    viewHolder.tv_comment.setText("1 Comment");
                } else {
                    viewHolder.tv_comment.setText(timeLineItem.getCommentCount() + " Comments");
                }
                Glide.with(context)
                        .load(MainApi.IMAGE_IP + timeLineItem.getImage()).placeholder(R.drawable.placeholder)
                        .into(viewHolder.mPostImageView);

                if (timeLineItem.getIsLike()) {
                    viewHolder.mLikeImageView.setImageResource(R.drawable.ic_like_active);
                } else {
                    viewHolder.mLikeImageView.setImageResource(R.drawable.ic_like);

                }
                viewHolder.mComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, CommentActivity.class);
                        intent.putExtra("string_key", timeLineItem.getID());
                        intent.putExtra("likes_number", timeLineItem.getLikeCount());
                        context.startActivity(intent);
                    }
                });

                viewHolder.tv_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, CommentActivity.class);
                        intent.putExtra("string_key", timeLineItem.getID());
                        intent.putExtra("likes_number", timeLineItem.getLikeCount());
                        context.startActivity(intent);
                    }
                });

                viewHolder.contain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Globals.R_Index_group = position;
                        openDetails(context, "post", mTimeLineList, position);
                    }
                });

                viewHolder.tv_like.setOnClickListener(new View.OnClickListener() {
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

                viewHolder.mLikeImageView.setOnClickListener(new View.OnClickListener() {
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

                viewHolder.shareBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, Globals.ShareLink);
                        sendIntent.setType("text/plain");
                        context.startActivity(Intent.createChooser(sendIntent, "Select"));
                    }
                });

                viewHolder.tv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, Globals.ShareLink);
                        sendIntent.setType("text/plain");
                        context.startActivity(Intent.createChooser(sendIntent, "Select"));
                    }
                });

                break;
            case NOTIF_TYPE_LOAD:
                final GroupPostAdapter.LoadingVH loadHolder = (GroupPostAdapter.LoadingVH) viewHoldermain;
                loadHolder.progressBar.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mTimeLineList.get(position) == null) {
            return 3;
        } else {
            switch (mTimeLineList.get(position).getType()) {
                case 2:
                    return NOTIF_TYPE_POST;
                case 3:
                    return NOTIF_TYPE_LOAD;
            }
        }
        return 3;
    }

    @Override
    public int getItemCount() {
        Log.e("test", mTimeLineList.size() + "");
        return mTimeLineList == null ? 0 : mTimeLineList.size();
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
        ImageView mPostImageView, addCalenderBtn, mLikeImageView, mComment, shareBtn;
        TextView mTitleTextView, mDescribtionTextView, mPostedByTextView, mGroupNameTextView, tv_comment, tv_like, tv_share;
        ConstraintLayout contain;

        public ViewHolderPost(View itemView) {
            super(itemView);
            mPostImageView = (ImageView) itemView.findViewById(R.id.iv_post);
            mLikeImageView = (ImageView) itemView.findViewById(R.id.iv_like);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tv_title);
            mDescribtionTextView = (TextView) itemView.findViewById(R.id.tv_desc);
            mPostedByTextView = (TextView) itemView.findViewById(R.id.tv_postedby);
            mGroupNameTextView = (TextView) itemView.findViewById(R.id.tv_group_name);
            tv_like = (TextView) itemView.findViewById(R.id.tv_like);
            contain = (ConstraintLayout) itemView.findViewById(R.id.contain);
            mComment = (ImageView) itemView.findViewById(R.id.iv_comment);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_share = (TextView) itemView.findViewById(R.id.tv_share);
            shareBtn = (ImageView) itemView.findViewById(R.id.iv_share);

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


