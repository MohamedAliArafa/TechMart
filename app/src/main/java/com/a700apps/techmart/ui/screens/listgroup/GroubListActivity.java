package com.a700apps.techmart.ui.screens.listgroup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.CategoryGroups;
import com.a700apps.techmart.data.model.CategoryGroupsData;
import com.a700apps.techmart.data.model.JoinGroupData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.screens.category.CategoryPresenter;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.mygroup.MyGroubListActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.AppConst;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GroubListActivity extends AppCompatActivity implements GroubView {

    private GroupPresenter presenter;
    EmptyRecyclerView rv;
    public AVLoadingIndicatorView indicatorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groub_list);
        presenter = new GroupPresenter();
        presenter.attachView(this);
        indicatorView= (AVLoadingIndicatorView) findViewById(R.id.avi);


        getExtra();
        rv = (EmptyRecyclerView) findViewById(R.id.recyclerView);


    }

    void getExtra() {
        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("selectedCategory",0);

//        String.valueOf(intValue)
        presenter.getGroupCategory(String.valueOf(intValue), PreferenceHelper.getUserId(GroubListActivity.this));
    }

    @Override
    public void showProgress() {
        indicatorView.setVisibility(View.VISIBLE);
        indicatorView.show();
    }

    @Override
    public void dismissProgress() {
        indicatorView.hide();

    }

    @Override
    public void updateUi(List<CategoryGroups> CategoryGroups) {
        if (CategoryGroups.size() == 0) {
//            mNoData.setVisibility(View.VISIBLE);
            rv.setEmptyView(findViewById(R.id.tv_nodata));
        }
        rv.setAdapter(new GroupsAdapter(GroubListActivity.this,CategoryGroups));
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private static class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> implements NetworkResponseListener<JoinGroupData> {
        private List<CategoryGroups> mCategoryGroupList;
        Context context;

        public GroupsAdapter(Context context,List<CategoryGroups> CategoryGroups) {
            this.context = context;
            this.mCategoryGroupList = CategoryGroups;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.groub_list_item, parent, false);
            return new ViewHolder(noteView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            CategoryGroups categoryGroupItem = mCategoryGroupList.get(position);

            viewHolder. mMemberNumberTextView.setText(String.valueOf(categoryGroupItem.MemberCount));
            viewHolder. mCreationDateTextView.setText(String.valueOf(AppUtils.getDate(categoryGroupItem.CreationDate)));
            viewHolder. mNameTextView.setText(categoryGroupItem.Name);
            Glide.with(context)
                    .load(MainApi.IMAGE_IP + mCategoryGroupList.get(position).Icon).placeholder(R.drawable.ic_profile)
                    .into(  viewHolder.mIconImageView);

            Button enrollBtn = (Button) viewHolder.itemView.findViewById(R.id.button);
            enrollBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                            getJoinGroup(String.valueOf(mCategoryGroupList.get(position).ID),PreferenceHelper.getUserId(context), AppConst.BoardMember);
                            dialog.dismiss();
                        }
                    });

                    individualLinear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getJoinGroup(String.valueOf(mCategoryGroupList.get(position).ID),PreferenceHelper.getUserId(context),AppConst.InDividual);
                            dialog.dismiss();
                        }
                    });



                    // set the layout for the Dialogr
                    dialog.setContentView(root);
                    dialog.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCategoryGroupList.size();
        }

        @Override
        public void networkOperationSuccess(NetworkResponse<JoinGroupData> networkResponse) {
            JoinGroupData userNetworkData = (JoinGroupData) networkResponse.data;
            int errorCode = userNetworkData.ISResultHasData;
            if (errorCode==1){
                ActivityUtils.openActivity(context, HomeActivity.class, true);

            }

        }

        @Override
        public void networkOperationFail(Throwable throwable) {

        }


        public void getJoinGroup(String ID,String UserId,String role) {



            try {
                JSONObject registerBody = MainApiHelper.joinGroups(ID,UserId,role);
                MainApi.joinGroup(registerBody, this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView mIconImageView;
            public TextView mMemberNumberTextView, mNameTextView, mCreationDateTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                mIconImageView = (ImageView) itemView.findViewById(R.id.iv_icon);
                mMemberNumberTextView = (TextView) itemView.findViewById(R.id.tv_member_number);
                mNameTextView = (TextView) itemView.findViewById(R.id.tv_name);
                mCreationDateTextView = (TextView) itemView.findViewById(R.id.tv_creat_date);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
            }
        }
    }
}
