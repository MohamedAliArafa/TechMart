package com.a700apps.techmart.adapter;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.JoinGroupData;
import com.a700apps.techmart.data.model.UserGroup;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupTimeLineActivity;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupsTimLineActivity;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.mygroup.MyGroubListActivity;
import com.a700apps.techmart.ui.screens.mygroup.MyGroupFragment;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.AppConst;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by khaled.badawy on 9/26/2017.
 */


public class RelativeGroupAadpter extends RecyclerView.Adapter<RelativeGroupAadpter.ViewHolder> implements NetworkResponseListener<JoinGroupData> {

    private List<UserGroup> mUserGroupList;
    private final Context context;

    public RelativeGroupAadpter(Context context, List<UserGroup> UserGroupList) {
        this.context = context;
        this.mUserGroupList = UserGroupList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView = inflater.inflate(R.layout.my_groub_list_item, parent, false);

        return new ViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        UserGroup mUserGroupItem = mUserGroupList.get(position);
        viewHolder.mNameTextView.setText(mUserGroupItem.Name);
        viewHolder.mCreateDate.setText(mUserGroupItem.CreationDate);
        viewHolder.mNameTextView.setText(mUserGroupItem.Name);
        viewHolder.mNumber.setText(String.valueOf(mUserGroupItem.MemberCount));
        Glide.with(context)
                .load(MainApi.IMAGE_IP + mUserGroupItem.Icon).placeholder(R.drawable.placeholder)
                .into(viewHolder.mGroupImageView);

        if (mUserGroupItem.IsJoinRequestPending) {
            viewHolder.view_detail_btn.setText("Pending");
        } else {
            if (mUserGroupItem.RoleinGroup == 0) {
                viewHolder.view_detail_btn.setText("Enrol As");
            } else {
                viewHolder.view_detail_btn.setText("View details");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mUserGroupList.size();
    }

    @Override
    public void networkOperationSuccess(NetworkResponse<JoinGroupData> networkResponse) {
        JoinGroupData userNetworkData = (JoinGroupData) networkResponse.data;
        int errorCode = userNetworkData.ISResultHasData;
        if (errorCode == 1) {
            ActivityUtils.openActivity(context, HomeActivity.class, true);
        }
    }

    @Override
    public void networkOperationFail(Throwable throwable) {

    }

    public void getJoinGroup(String ID, String UserId, String role) {
        try {
            JSONObject registerBody = MainApiHelper.joinGroups(ID, UserId, role);
            MainApi.joinGroup(registerBody, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mGroupImageView;
        TextView mNameTextView, mCreateDate, mNumber;
        Button view_detail_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            mGroupImageView = (ImageView) itemView.findViewById(R.id.iv_group);
            mNameTextView = (TextView) itemView.findViewById(R.id.tv_name);
            mCreateDate = (TextView) itemView.findViewById(R.id.tv_creat_date);
            mNumber = (TextView) itemView.findViewById(R.id.tv_member_number);
            view_detail_btn = (Button) itemView.findViewById(R.id.view_detail_btn);

            view_detail_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (mUserGroupList.get(getAdapterPosition()).IsJoinRequestPending) {
                Toast.makeText(context, "request pending", Toast.LENGTH_SHORT).show();
            } else {
                if (mUserGroupList.get(getAdapterPosition()).RoleinGroup == 0) {

                    // create an instance of Dialog
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    //inflate a layout
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                    View root = inflater.inflate(R.layout.group_join_dialog, null);
                    LinearLayout indLinear = (LinearLayout) root.findViewById(R.id.lin_board);
                    LinearLayout individualLinear = (LinearLayout) root.findViewById(R.id.lin_individual);

                    indLinear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getJoinGroup(String.valueOf(mUserGroupList.get(getAdapterPosition()).ID), PreferenceHelper.getUserId(context), AppConst.BoardMember);
                            dialog.dismiss();
                        }
                    });

                    individualLinear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getJoinGroup(String.valueOf(mUserGroupList.get(getAdapterPosition()).ID), PreferenceHelper.getUserId(context), AppConst.InDividual);
                            dialog.dismiss();
                        }
                    });

                    // set the layout for the Dialogr
                    dialog.setContentView(root);
                    dialog.show();

                } else {
                    Intent myIntent = new Intent(context, GroupsTimLineActivity.class);
                    myIntent.putExtra("selectedCategory", mUserGroupList.get(getAdapterPosition()).ID);
                    context.startActivity(myIntent);
//                    ((HomeActivity) context).finish();
                }
            }


//            ActivityUtils.openActivity(context, GroupTimeLineActivity.class, false);
        }


    }
}
