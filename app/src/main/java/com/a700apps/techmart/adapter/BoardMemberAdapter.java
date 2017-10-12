package com.a700apps.techmart.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.BoardMember.DialogApproval.ApprovalPresenter;
import com.a700apps.techmart.ui.screens.BoardMember.DialogApproval.approvalView;
import com.a700apps.techmart.ui.screens.BoardMember.EditTimeLine.EditTimeLineActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by samir.salah on 10/10/2017.
 */

public class BoardMemberAdapter extends RecyclerView.Adapter<BoardMemberAdapter.ViewHolder> {
    private List<GroupTimeLineData.ResultEntity> mTimeLineList;
    Context context;
    private static final int NOTIF_TYPE_EVENT = 2;


    public BoardMemberAdapter(Context context, List<GroupTimeLineData.ResultEntity> TimeLineList) {
        this.context = context;
        this.mTimeLineList = TimeLineList;

    }


    @Override
    public void onBindViewHolder(BoardMemberAdapter.ViewHolder viewHolder, final int position) {
        GroupTimeLineData.ResultEntity timeLineItem = mTimeLineList.get(position);
//        final int itemType = getItemViewType(position);
        Log.e("timeLineItem.getType()", timeLineItem.getType() + "");

//        switch (itemType) {
//
//            case NOTIF_TYPE_EVENT:
                BoardMemberAdapter.ViewHolder viewHolderEvent = (BoardMemberAdapter.ViewHolder) viewHolder;
                viewHolderEvent.mDescribtionTextView.setText(timeLineItem.getDescr());
                viewHolderEvent.mTitleTextView.setText(timeLineItem.getTitle());
                viewHolderEvent.mGroupNameTextView.setText(timeLineItem.getGroupName());





                if (timeLineItem.getStatus() == 0) {
                    viewHolderEvent.mApproveImageView.setImageResource(R.drawable.ic_edit);
                    viewHolderEvent.tv_approve.setTextColor(context.getResources().getColor(R.color.red_join_dialog));
                    viewHolderEvent.tv_approve.setText("Pending");
                } else if (timeLineItem.getStatus() == 1) {
                    viewHolderEvent.tv_approve.setText("Approved");
                } else if (timeLineItem.getStatus() == 2) {
                    viewHolderEvent.tv_approve.setTextColor(context.getResources().getColor(R.color.red_join_dialog));
                    viewHolderEvent.tv_approve.setText("Rejected");
                } else if (timeLineItem.getStatus() == 3) {
                    viewHolderEvent.tv_approve.setTextColor(context.getResources().getColor(R.color.red_join_dialog));
                    viewHolderEvent.tv_approve.setText("Deferred");
                }

                Glide.with(context)
                        .load(MainApi.IMAGE_IP + timeLineItem.getImage()).placeholder(R.drawable.placeholder)
                        .into(viewHolderEvent.mPostImageView);

                viewHolderEvent.contain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        openDetails(context, "Event", mTimeLineList, position);
                    }
                });
//                break;
//        }
    }

    @Override
    public BoardMemberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView;
//        switch (viewType) {
//            case NOTIF_TYPE_EVENT:
        noteView = inflater.inflate(R.layout.list_boardmember_item, parent, false);
        return new BoardMemberAdapter.ViewHolder(noteView);


//        }
//        return null;
//
    }


//    @Override
//    public int getItemViewType(int position) {
//        int type = position;
//        Log.e("type", mTimeLineList.get(position).getType() + "");
//        switch (mTimeLineList.get(position).getType()) {
//            case 2:
//                return NOTIF_TYPE_EVENT;
//        }
//        return 0;
//    }

    @Override
    public int getItemCount() {
        Log.e("test", mTimeLineList.size() + "");
        return mTimeLineList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, approvalView {

        ImageView mPostImageView, mApproveImageView, mManageImageView, mEditImageView;
        TextView mTitleTextView, mDescribtionTextView, mPostedByTextView, mGroupNameTextView, tv_approve;
        ConstraintLayout contain;
        ApprovalPresenter presenter;


        public ViewHolder(View itemView) {
            super(itemView);
            contain = (ConstraintLayout) itemView.findViewById(R.id.contain);

            mPostImageView = (ImageView) itemView.findViewById(R.id.iv_post);
            mApproveImageView = (ImageView) itemView.findViewById(R.id.iv_approve);
            mManageImageView = (ImageView) itemView.findViewById(R.id.iv_manage);
            mEditImageView = (ImageView) itemView.findViewById(R.id.iv_edit);

            tv_approve = (TextView) itemView.findViewById(R.id.tv_approve);
            mGroupNameTextView = (TextView) itemView.findViewById(R.id.tv_group_name);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tv_title);
            mDescribtionTextView = (TextView) itemView.findViewById(R.id.tv_desc);

            presenter = new ApprovalPresenter();
            presenter.attachView(this);
            mEditImageView.setOnClickListener(this);
            itemView.findViewById(R.id.tv_edit).setOnClickListener(this);

            mManageImageView.setOnClickListener(this);
            itemView.findViewById(R.id.tv_manage).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            int viewId = v.getId();
            switch (viewId) {

                case R.id.iv_edit:
                    Bundle bundle = new Bundle();
                    bundle.putInt("postId", mTimeLineList.get(getAdapterPosition()).getID());
                    bundle.putInt("type", mTimeLineList.get(getAdapterPosition()).getType());

                    ActivityUtils.openActivity(context, EditTimeLineActivity.class, bundle, false);
                    break;
                case R.id.tv_edit:
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("postId", mTimeLineList.get(getAdapterPosition()).getID());
                    bundle2.putInt("type", mTimeLineList.get(getAdapterPosition()).getType());
                    ActivityUtils.openActivity(context, EditTimeLineActivity.class, bundle2, false);
                    break;
                case R.id.tv_manage:
                    showConfirmDialog(mTimeLineList.get(getAdapterPosition()).getID(), mTimeLineList.get(getAdapterPosition()).getType());
                    break;
                case R.id.iv_manage:
                    showConfirmDialog(mTimeLineList.get(getAdapterPosition()).getID(), mTimeLineList.get(getAdapterPosition()).getType());
                    break;

            }
        }

        private void showConfirmDialog(final int postId, final int type) {

            // Create custom dialog object
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_approval);
            dialog.show();

            ImageView approveImageView = dialog.findViewById(R.id.iv_approve);
            ImageView rejectImageView = dialog.findViewById(R.id.iv_reject);
            ImageView deferImageView = dialog.findViewById(R.id.iv_defer);
            ImageView iconClose = dialog.findViewById(R.id.ic_icon_close);

            TextView approveTextView = dialog.findViewById(R.id.tv_approve);
            TextView rejectTextView = dialog.findViewById(R.id.tv_reject);
            TextView deferTextView = dialog.findViewById(R.id.tv_defer);


            iconClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            approveImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(context), 1);
                    dialog.dismiss();
                }
            });

            approveTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(context), 1);
                    dialog.dismiss();
                }
            });

            rejectImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(context), 2);
                    dialog.dismiss();
                }
            });

            rejectTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(context), 2);
                    dialog.dismiss();
                }
            });

            deferImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(context), 3);
                    dialog.dismiss();
                }
            });

            deferTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(context), 3);
                    dialog.dismiss();
                }
            });
        }

        @Override
        public void showLoadingProgress() {

        }

        @Override
        public void dismissLoadingProgress() {

        }

        @Override
        public void showToast(String message) {

        }
    }

//    static void openDetails(Context context, String type, List<TimeLineData.ResultEntity> mTimeLineList, int index) {
//
//        Intent intent = new Intent(context, DetailsActivity.class);
//        intent.putExtra("Type", type);
//        intent.putExtra("Index", index);
//        intent.putParcelableArrayListExtra("Timeline", (ArrayList<? extends Parcelable>) mTimeLineList);
//        context.startActivity(intent);
//
//    }
}



