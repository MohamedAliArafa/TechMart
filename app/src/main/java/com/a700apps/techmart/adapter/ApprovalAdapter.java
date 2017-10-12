package com.a700apps.techmart.adapter;


import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.JoinGroupRequestsData;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.screens.BoardMember.DialogApproval.DialogActivity;
import com.a700apps.techmart.ui.screens.BoardMember.JoinRequests.RequestsPresenter;
import com.a700apps.techmart.ui.screens.BoardMember.JoinRequests.RequestsView;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by khaled.badawy on 10/10/2017.
 */

public class ApprovalAdapter extends RecyclerView.Adapter<ApprovalAdapter.ViewHolder> implements RequestsView{
    private List<JoinGroupRequestsData.Result> mGroupUsersList;
    Context context;

    RequestsPresenter presenter;

    public ApprovalAdapter(Context context, List<JoinGroupRequestsData.Result> TimeLineList) {
        this.context = context;
        this.mGroupUsersList = TimeLineList;
        presenter = new RequestsPresenter();
        presenter.attachView(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView = inflater.inflate(R.layout.approve_layout_item, parent, false);
        return new ViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final JoinGroupRequestsData.Result singleItem = mGroupUsersList.get(position);
        viewHolder.mName.setText(singleItem.getUserName());
        viewHolder.mTime.setText(singleItem.getRequestDateST());

        Glide.with(context).load(MainApi.IMAGE_IP + singleItem.getImage()).placeholder(R.drawable.ic_profile).into(viewHolder.profile);
    /*
          Pending = 0,
          Accepted = 1,
          Rejected = 2,
          Defered = 3,
     */
        if (singleItem.getRequestStatus()==0){
            viewHolder.mStatus.setText("Pending");
            viewHolder.mStatus.setBackgroundColor(context.getResources().getColor(R.color.btn_approve));
        }else if (singleItem.getRequestStatus()==1){
            viewHolder.mStatus.setText("Accepted");
            viewHolder.mStatus.setBackgroundColor(context.getResources().getColor(R.color.btn_approve));
        }else if (singleItem.getRequestStatus()==2){
            viewHolder.mStatus.setText("Rejected");
            viewHolder.mStatus.setBackgroundColor(context.getResources().getColor(R.color.btn_reject));
        }else if (singleItem.getRequestStatus()==3){
            viewHolder.mStatus.setText("Defered");
            viewHolder.mStatus.setBackgroundColor(context.getResources().getColor(R.color.btn_defer));
        }

        viewHolder.manageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                presenter.manageRequestItem(singleItem.getID() , singleItem.getRequestedRole() , PreferenceHelper.getUserId(context) , 1);
                showConfirmDialog(singleItem.getID());
            }
        });



    }

    private void showConfirmDialog(final int postId ) {

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
                presenter.manageRequestItem(postId, 1,1, PreferenceHelper.getUserId(context));
                dialog.dismiss();
            }
        });

        approveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.manageRequestItem(postId, 1,1, PreferenceHelper.getUserId(context));
                dialog.dismiss();
            }
        });

        rejectImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.manageRequestItem(postId, 2,1, PreferenceHelper.getUserId(context));
                dialog.dismiss();
            }
        });

        rejectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.manageRequestItem(postId, 2,1, PreferenceHelper.getUserId(context));
                dialog.dismiss();
            }
        });

        deferImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.manageRequestItem(postId, 3,1, PreferenceHelper.getUserId(context));
                dialog.dismiss();
            }
        });

        deferTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.manageRequestItem(postId, 3,1, PreferenceHelper.getUserId(context));
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGroupUsersList.size();
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

    @Override
    public void updateData(List<JoinGroupRequestsData.Result> list) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView profile;
        TextView mName, mTime, mStatus;
        View manageLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            profile = (ImageView) itemView.findViewById(R.id.iv_approve_profile);
            mName = (TextView) itemView.findViewById(R.id.tv_name);
            mTime = (TextView) itemView.findViewById(R.id.tv_approve_time);
            mStatus = (TextView) itemView.findViewById(R.id.tv_approve_status);
            manageLayout = itemView.findViewById(R.id.manageLayout);
        }

        @Override
        public void onClick(View v) {
//            int position = getAdapterPosition();
//            context.startActivity(new Intent(context, MemberProfile.class));
        }
    }
}

