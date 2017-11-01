package com.a700apps.techmart.ui.screens.joingroup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.CategoryGroups;
import com.a700apps.techmart.data.model.JoinGroupData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.screens.listgroup.GroubView;
import com.a700apps.techmart.ui.screens.listgroup.GroupPresenter;
import com.a700apps.techmart.utils.AppConst;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by samir.salah on 10/28/2017.
 */

public class JoinGroupListActivity extends AppCompatActivity implements GroubView {

    private GroupPresenter presenter;
    EmptyRecyclerView rv;
    public AVLoadingIndicatorView indicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groub_list);
        presenter = new GroupPresenter();
        presenter.attachView(this);
        indicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);


        getExtra();
        rv = (EmptyRecyclerView) findViewById(R.id.recyclerView);


    }

    void getExtra() {
        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("selectedCategory", 0);

//        String.valueOf(intValue)
        presenter.getGroupCategory(String.valueOf(intValue), PreferenceHelper.getUserId(JoinGroupListActivity.this));
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
        rv.setAdapter(new JoinGroupListActivity.GroupsAdapter(JoinGroupListActivity.this, CategoryGroups));
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private static class GroupsAdapter extends RecyclerView.Adapter<JoinGroupListActivity.GroupsAdapter.ViewHolder> implements NetworkResponseListener<JoinGroupData>, JoinView {
        private List<CategoryGroups> mCategoryGroupList;
        Context context;
        TextView mEnrollTextView;
        Button enrollBtn;
        int globalPosition;

        public GroupsAdapter(Context context, List<CategoryGroups> CategoryGroups) {
            this.context = context;
            this.mCategoryGroupList = CategoryGroups;
        }

        @Override
        public JoinGroupListActivity.GroupsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.groub_list_item, parent, false);
            return new JoinGroupListActivity.GroupsAdapter.ViewHolder(noteView);
        }

        @Override
        public void onBindViewHolder(final JoinGroupListActivity.GroupsAdapter.ViewHolder viewHolder, final int position) {
            CategoryGroups categoryGroupItem = mCategoryGroupList.get(position);
            mEnrollTextView = (TextView) viewHolder.itemView.findViewById(R.id.textView13);
            enrollBtn = (Button) viewHolder.itemView.findViewById(R.id.button);
            viewHolder.mMemberNumberTextView.setText(String.valueOf(categoryGroupItem.MemberCount));
            viewHolder.mCreationDateTextView.setText(String.valueOf(AppUtils.getDate(categoryGroupItem.CreationDate)));
            viewHolder.mNameTextView.setText(categoryGroupItem.Name);
            Glide.with(context)
                    .load(MainApi.IMAGE_IP + mCategoryGroupList.get(position).Icon).placeholder(R.drawable.ic_profile)
                    .into(viewHolder.mIconImageView);
            if (categoryGroupItem.IsJoinRequestPending) {
                mEnrollTextView.setText("Request Pending");
                enrollBtn.setVisibility(View.GONE);
                viewHolder.mEnrollConstrainLayout.setEnabled(false);
            }


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
                    ImageView close = (ImageView) root.findViewById(R.id.imageView8);
                    globalPosition = mCategoryGroupList.get(position).ID;
                    indLinear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // id is uniqe key
                            getJoinGroup(String.valueOf(mCategoryGroupList.get(position).ID), PreferenceHelper.getUserId                            (context), AppConst.BoardMember);
                            dialog.dismiss();
                        }
                    });

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    individualLinear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getJoinGroup(String.valueOf(mCategoryGroupList.get(position).ID), PreferenceHelper.getUserId(context), AppConst.InDividual);
                            dialog.dismiss();
                        }
                    });


                    // set the layout for the Dialogr
                    dialog.setContentView(root);
                    dialog.show();
                }
            });

            viewHolder.mEnrollConstrainLayout.setOnClickListener(new View.OnClickListener() {
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
                    ImageView close = (ImageView) root.findViewById(R.id.imageView8);
                    indLinear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getJoinGroup(String.valueOf(mCategoryGroupList.get(position).ID), PreferenceHelper.getUserId(context), AppConst.BoardMember);
                            dialog.dismiss();
                        }
                    });

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    individualLinear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getJoinGroup(String.valueOf(mCategoryGroupList.get(position).ID), PreferenceHelper.getUserId(context), AppConst.InDividual);
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
            if (errorCode == 1) {
                Toast.makeText(context, "Your request is under progress", Toast.LENGTH_LONG).show();
                for (int i = 0; i < mCategoryGroupList.size(); i++) {
                    if (globalPosition == mCategoryGroupList.get(i).ID) {
                        mCategoryGroupList.get(i).setJoinRequestPending(true);
                        notifyDataSetChanged();
                        break;
                    }

                }
//                Globals.IS_JOIN = true;


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


        @Override
        public void updateView(boolean isJoin) {
            if (isJoin) {
                mEnrollTextView.setText("Request Pending");
                enrollBtn.setVisibility(View.GONE);

            }
        }


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView mIconImageView;
            public TextView mMemberNumberTextView, mNameTextView, mCreationDateTextView, mEnrollTextView;
            ConstraintLayout mEnrollConstrainLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                mEnrollConstrainLayout = (ConstraintLayout) itemView.findViewById(R.id.container);
                mIconImageView = (ImageView) itemView.findViewById(R.id.iv_icon);
                mMemberNumberTextView = (TextView) itemView.findViewById(R.id.tv_member_number);
                mNameTextView = (TextView) itemView.findViewById(R.id.tv_name);
                mCreationDateTextView = (TextView) itemView.findViewById(R.id.tv_creat_date);
                mEnrollTextView = (TextView) itemView.findViewById(R.id.textView13);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
            }
        }
    }
}

