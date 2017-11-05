package com.a700apps.techmart.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.BoardMember.DialogApproval.ApprovalPresenter;
import com.a700apps.techmart.ui.screens.BoardMember.DialogApproval.approvalView;
import com.a700apps.techmart.ui.screens.BoardMember.EditTimeLine.EditTimeLineActivity;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.login.LoginActivity;
import com.a700apps.techmart.ui.screens.profile.MemberProfileFragment;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsActivity;
import com.a700apps.techmart.utils.ATCPrefManager;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.CustomButton;
import com.a700apps.techmart.utils.DateTimePicker.CustomLightTextView;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir.salah on 10/10/2017.
 */

public class BoardMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TimeLineData.ResultEntity> mTimeLineList;
    Activity context;
    private static final int NOTIF_TYPE_Post = 1;
    private static final int NOTIF_TYPE_EVENT = 2;
    private static final int NOTIF_TYPE_LOAD = 3;
    RecyclerView rv;

    String mType;
    boolean isLoadingAdded = false;
    Dialog dialogsLoading;

    public BoardMemberAdapter(Activity context, List<TimeLineData.ResultEntity> TimeLineList, RecyclerView rv) {
        this.context = context;
        this.mTimeLineList = TimeLineList;
        this.rv = rv;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final int itemType = getItemViewType(position);
        switch (itemType) {

            case NOTIF_TYPE_EVENT:
                final TimeLineData.ResultEntity timeLineItem = mTimeLineList.get(position);
                BoardMemberAdapter.ViewHolder viewHolderEvent = (BoardMemberAdapter.ViewHolder) viewHolder;
                viewHolderEvent.mDescribtionTextView.setText(timeLineItem.getDescr());
                viewHolderEvent.mTitleTextView.setText(timeLineItem.getTitle());
                viewHolderEvent.mGroupNameTextView.setText(timeLineItem.getGroupName());
                viewHolderEvent.tv_postedby.setText(timeLineItem.getPostedByName());


                if (timeLineItem.getStatus() == 0) {
                    viewHolderEvent.mApproveImageView.setImageResource(R.drawable.ic_pending);
                    viewHolderEvent.tv_approve.setTextColor(context.getResources().getColor(R.color.red_join_dialog));
                    viewHolderEvent.tv_approve.setText("Pending");
                } else if (timeLineItem.getStatus() == 1) {
                    viewHolderEvent.tv_approve.setText("Approved");
                    viewHolderEvent.tv_manage.setText("Delete");
                    viewHolderEvent.mManageImageView.setImageResource(R.drawable.ic_delet);

                } else if (timeLineItem.getStatus() == 2) {
                    viewHolderEvent.tv_approve.setTextColor(context.getResources().getColor(R.color.red_join_dialog));
                    viewHolderEvent.tv_approve.setText("Rejected");
                } else if (timeLineItem.getStatus() == 3) {
                    viewHolderEvent.mApproveImageView.setImageResource(R.drawable.ic_defer);
                    viewHolderEvent.tv_approve.setTextColor(context.getResources().getColor(R.color.red_join_dialog));
                    viewHolderEvent.tv_approve.setText("Deferred");
                }

                viewHolderEvent.contain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Globals.R_Index_group = position;
                        if (mTimeLineList.get(position).getType() == 1) {
                            mType = "post";
                        } else if (mTimeLineList.get(position).getType() == 2) {
                            mType = "Event";
                        }
                        openDetails(context, mType, mTimeLineList, position);
                    }
                });

                Glide.with(context)
                        .load(MainApi.IMAGE_IP + timeLineItem.getImage()).placeholder(R.drawable.placeholder)
                        .into(viewHolderEvent.mPostImageView);

                viewHolderEvent.tv_postedby.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("RelativId", String.valueOf(timeLineItem.getCreatedBY()));
                        bundle.putInt("GroupId", timeLineItem.getGroupID());
                        ((HomeActivity) context).openFragment(MemberProfileFragment.class, bundle);
                    }
                });
                break;
            case NOTIF_TYPE_LOAD:
                final BoardMemberAdapter.LoadingVH loadHolder = (BoardMemberAdapter.LoadingVH) viewHolder;
                loadHolder.progressBar.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);

                break;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView;
        switch (viewType) {
            case NOTIF_TYPE_EVENT:
                noteView = inflater.inflate(R.layout.list_boardmember_item, parent, false);
                return new BoardMemberAdapter.ViewHolder(noteView);
            case NOTIF_TYPE_LOAD:
                noteView = inflater.inflate(R.layout.item_progress, parent, false);
                return new BoardMemberAdapter.LoadingVH(noteView);
        }
        return null;
//
    }


    @Override
    public int getItemViewType(int position) {
        switch (mTimeLineList.get(position).getType()) {
            case 1:
                return NOTIF_TYPE_EVENT;
            case 2:
                return NOTIF_TYPE_EVENT;
            case 3:
                return NOTIF_TYPE_LOAD;

        }
        return NOTIF_TYPE_LOAD;
    }

    @Override
    public int getItemCount() {
        Log.e("test", mTimeLineList.size() + "");
        return mTimeLineList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, approvalView {

        ImageView mPostImageView, mApproveImageView, mManageImageView, mEditImageView;
        TextView mTitleTextView, mDescribtionTextView, mPostedByTextView, mGroupNameTextView, tv_approve, tv_manage, tv_postedby;
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
            tv_postedby = (TextView) itemView.findViewById(R.id.tv_postedby);
            mGroupNameTextView = (TextView) itemView.findViewById(R.id.tv_group_name);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tv_title);
            mDescribtionTextView = (TextView) itemView.findViewById(R.id.tv_desc);
            tv_manage = (TextView) itemView.findViewById(R.id.tv_manage);

            presenter = new ApprovalPresenter();
            presenter.attachView(this);
            mEditImageView.setOnClickListener(this);
            itemView.findViewById(R.id.tv_edit).setOnClickListener(this);

            mManageImageView.setOnClickListener(this);
            tv_manage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            boolean isdefered = mTimeLineList.get(getAdapterPosition()).getStatus() == 3 ? true : false;
            int viewId = v.getId();
            switch (viewId) {

                case R.id.iv_edit:
                    Bundle bundle = new Bundle();
                    bundle.putInt("postId", mTimeLineList.get(getAdapterPosition()).getID());
                    bundle.putInt("type", mTimeLineList.get(getAdapterPosition()).getType());

                    ActivityUtils.openActivity(context, EditTimeLineActivity.class, bundle, false);
//                    ActivityUtils.openActivityForResult(context ,EditTimeLineActivity.class , bundle , 123 );
                    break;
                case R.id.tv_edit:
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("postId", mTimeLineList.get(getAdapterPosition()).getID());
                    bundle2.putInt("type", mTimeLineList.get(getAdapterPosition()).getType());
                    ActivityUtils.openActivity(context, EditTimeLineActivity.class, bundle2, false);
                    break;
                case R.id.tv_manage:

                    if (mTimeLineList.get(getAdapterPosition()).getStatus() == 1) {
                        showConfirmDialog(mTimeLineList.get(getAdapterPosition()).getID(), mTimeLineList.get(getAdapterPosition()).getType(),
                                PreferenceHelper.getUserId(context));
                    } else {
                        showConfirmDialog(mTimeLineList.get(getAdapterPosition()).getID(), mTimeLineList.get(getAdapterPosition()).getType(), isdefered);
                    }

                    break;
                case R.id.iv_manage:

                    if (mTimeLineList.get(getAdapterPosition()).getStatus() == 1) {
                        showConfirmDialog(mTimeLineList.get(getAdapterPosition()).getID(), mTimeLineList.get(getAdapterPosition()).getType(),
                                PreferenceHelper.getUserId(context));
//                        presenter.manageTimeLineItem(mTimeLineList.get(getAdapterPosition()).getID(),
//                                mTimeLineList.get(getAdapterPosition()).getType(), PreferenceHelper.getUserId(context), 4);
                    } else {
                        showConfirmDialog(mTimeLineList.get(getAdapterPosition()).getID(),
                                mTimeLineList.get(getAdapterPosition()).getType(), isdefered);
                    }

                    break;

            }
        }

        private void showConfirmDialog(final int itemid, final int groupid, final String userid) {

            // Create custom dialog object
            final Dialog dialog = new Dialog(context);

            dialog.setContentView(R.layout.logout_confirm_dialog);

            // set values for custom dialog components - text, image and button
            Button logout = (CustomButton) dialog.findViewById(R.id.logout);
            Button cancel = (CustomButton) dialog.findViewById(R.id.cancel);
            ((CustomLightTextView) dialog.findViewById(R.id.logout_title)).setText("Delete");
            ((CustomLightTextView) dialog.findViewById(R.id.logout_desc)).setText("Are you sure you want to delete this entry ?");
            logout.setText("Delete");
            dialog.show();

            Button declineButton = (CustomButton) dialog.findViewById(R.id.cancel);
            // if decline button is clicked, close the custom dialog
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    dialog.dismiss();
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    presenter.manageTimeLineItem(itemid, groupid, userid, 4);
                    dialog.dismiss();
                }
            });
        }

        //        private void showAlertDialog(final int itemid , final int groupid , final String userid){
//            final AlertDialog.Builder builder;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
//            } else {
//                builder = new AlertDialog.Builder(context);
//            }
//            builder.setTitle("Delete confirmation")
//                    .setMessage("Are you sure you want to delete this item ?")
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // continue with delete
//                            presenter.manageTimeLineItem(itemid , groupid,userid, 4);
//                        }
//                    })
//                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    })
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();
//        }
        private void showConfirmDialog(final int postId, final int type, boolean defered) {

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

            LinearLayout deferLayout = dialog.findViewById(R.id.deferLayout);
            LinearLayout acceptLayout = dialog.findViewById(R.id.acceptLayout);
            LinearLayout rejectLayout = dialog.findViewById(R.id.rejectLayout);

            if (defered) {
                deferLayout.setVisibility(View.GONE);
                acceptLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.5f));
                rejectLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.5f));
            }

            iconClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });


//            approveImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(context), 1);
//                    dialog.dismiss();
//                }
//            });
//
//            approveTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(context), 1);
//                    dialog.dismiss();
//                }
//            });
//
//            rejectImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(context), 2);
//                    dialog.dismiss();
//                }
//            });
//
//            rejectTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(context), 2);
//                    dialog.dismiss();
//                }
//            });
//
//            deferImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(context), 3);
//                    dialog.dismiss();
//                }
//            });
//
//            deferTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(context), 3);
//                    dialog.dismiss();
//                }
//            });
        }

        @Override
        public void showLoadingProgress() {
            dialogsLoading = new loadingDialog().showDialog(context);
        }

        @Override
        public void dismissLoadingProgress() {
            dialogsLoading.dismiss();
        }

        @Override
        public void showToast(String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateUi(List<TimeLineData.ResultEntity> TimelineList) {
            rv.setAdapter(new BoardMemberAdapter(context, TimelineList, rv));
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



