package com.a700apps.techmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir salah on 9/13/2017.
 */

public class GroupPostAdapter extends RecyclerView.Adapter<GroupPostAdapter.ViewHolderPost> implements NetworkResponseListener<LikeData> {
    private List<GroupTimeLineData.ResultEntity> mTimeLineList;
    Context context;
    String isLike;
    View noteView;
    int positionItem;

    public GroupPostAdapter(Context context, List<GroupTimeLineData.ResultEntity> TimeLineList) {
        this.context = context;
        this.mTimeLineList = TimeLineList;
    }


    @Override
    public GroupPostAdapter.ViewHolderPost onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        noteView = inflater.inflate(R.layout.timeline_item_post, parent, false);
//        setValues(new ViewHolderPost(noteView),noteView);
        return new GroupPostAdapter.ViewHolderPost(noteView);
    }

    @Override
    public void onBindViewHolder(final GroupPostAdapter.ViewHolderPost viewHolder, final int position) {
        positionItem = position;
        final GroupTimeLineData.ResultEntity timeLineItem = mTimeLineList.get(positionItem);
        final int itemType = getItemViewType(positionItem);
        Log.e("timeLineItem.getType()", timeLineItem.getType() + "");

        switch (itemType) {
//
            case 2:
                viewHolder.mDescribtionTextView.setText(timeLineItem.getDescr());
                viewHolder.mPostedByTextView.setText(timeLineItem.getPostedByName());
                viewHolder.mTitleTextView.setText(timeLineItem.getTitle());
                viewHolder.mGroupNameTextView.setText(timeLineItem.getGroupName());
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
                        openDetails(context, "post", mTimeLineList, position);
                    }
                });

                viewHolder.mLikeImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (timeLineItem.getIsLike()) {
                            timeLineItem.setIsLike(false);
                            isLike = "false";
                            viewHolder.mLikeImageView.setImageResource(R.drawable.ic_like);
                        } else {
                            isLike = "true";
                            timeLineItem.setIsLike(true);
                            viewHolder.mLikeImageView.setImageResource(R.drawable.ic_like_active);
                        }
                        getLike(timeLineItem);

                    }
                });

                viewHolder.shareBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                        sendIntent.setType("text/plain");
                        context.startActivity(sendIntent);
                    }
                });
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = position;
        Log.e("type", mTimeLineList.get(position).getType() + "");
        switch (mTimeLineList.get(position).getType()) {
//
            case 2:
                return 2;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        Log.e("test", mTimeLineList.size() + "");
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
        ImageView mPostImageView, addCalenderBtn, mLikeImageView,mComment,shareBtn;
        TextView mTitleTextView, mDescribtionTextView, mPostedByTextView, mGroupNameTextView,tv_comment;
        ConstraintLayout contain;

        public ViewHolderPost(View itemView) {
            super(itemView);
            mPostImageView = (ImageView) itemView.findViewById(R.id.iv_post);
            mLikeImageView = (ImageView) itemView.findViewById(R.id.iv_like);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tv_title);
            mDescribtionTextView = (TextView) itemView.findViewById(R.id.tv_desc);
            mPostedByTextView = (TextView) itemView.findViewById(R.id.tv_postedby);
            mGroupNameTextView = (TextView) itemView.findViewById(R.id.tv_group_name);
            contain = (ConstraintLayout) itemView.findViewById(R.id.contain);
            mComment = (ImageView) itemView.findViewById(R.id.iv_comment);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            shareBtn = (ImageView) itemView.findViewById(R.id.iv_share);

        }
    }

    static void openDetails(Context context,String type,List<GroupTimeLineData.ResultEntity> mTimeLineList,int index) {
        Intent intent = new Intent(context, DetailsGroupActivity.class);
        intent.putExtra("Type", type);
        intent.putExtra("Index", index);
        intent.putParcelableArrayListExtra("Timeline", (ArrayList<? extends Parcelable>) mTimeLineList);
        context. startActivity(intent);
    }

    void changeLike() {
        setValues(new GroupPostAdapter.ViewHolderPost(noteView), noteView);
    }

    void setValues(GroupPostAdapter.ViewHolderPost viewholder, View item) {
        if (isLike.equals("true")) {
            mTimeLineList.get(positionItem).setIsLike(false);
            notifyDataSetChanged();
//          viewholder.mLikeImageView.setImageResource(R.drawable.ic_like_active);
//          isLike = "false";
        } else {
            mTimeLineList.get(positionItem).setIsLike(true);
            notifyDataSetChanged();
        }
    }

    void getLike(GroupTimeLineData.ResultEntity timeLine) {
        try {
            JSONObject registerBody = MainApiHelper.getUserLike(PreferenceHelper.getUserId(context), timeLine.getID(), isLike
            );
            MainApi.getLikeTimeLine(registerBody, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


