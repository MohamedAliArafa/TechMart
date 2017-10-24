package com.a700apps.techmart.ui.screens.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.Comment;
import com.a700apps.techmart.data.model.CommentData;
import com.a700apps.techmart.data.model.GroupUsersData;
import com.a700apps.techmart.data.model.LikeData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.data.network.MainApiHelper;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.ui.screens.groupmemberdetails.GroupActivity;
import com.a700apps.techmart.ui.screens.groupmemberdetails.GroupMemberPresenter;
import com.a700apps.techmart.ui.screens.profile.MemberProfile;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsActivity;
import com.a700apps.techmart.ui.screens.userlikes.UserLikesActivity;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by samir salah on 9/13/2017.
 */

public class CommentActivity extends AppCompatActivity implements commentView {
    private CommentPresenter presenter;
    EditText editText;
    RecyclerView rv;
    ImageView mSend, mLikeImageView;
    TextView mLikes;

    int mId, mNumberLikes;
    ImageView mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);

        presenter = new CommentPresenter();
        presenter.attachView(this);
        mId = getIntent().getIntExtra("string_key", 0);
        mNumberLikes = getIntent().getIntExtra("likes_number", 0);
        presenter.comment(mId, this);
        rv = (RecyclerView) findViewById(R.id.recyclerView);
        editText = (EditText) findViewById(R.id.editText);
        mSend = (ImageView) findViewById(R.id.imageView5);
        mLikes = (TextView) findViewById(R.id.textView2);
        mLikeImageView = (ImageView) findViewById(R.id.imageView3);
        mBackButton = (ImageView) findViewById(R.id.imageView2);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (mNumberLikes == 0) {
            mLikes.setText("Be first to like this");
        } else {
            mLikes.setText(mNumberLikes + " " + "Users like this post");
        }
        mLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mNumberLikes == 0) {
                    JSONObject registerBody = null;
                    try {
                        registerBody = MainApiHelper.getUserLike(PreferenceHelper.getUserId(CommentActivity.this), mId, "true");
                        MainApi.getLikeTimeLine(registerBody, new NetworkResponseListener<LikeData>() {
                            @Override
                            public void networkOperationSuccess(NetworkResponse<LikeData> networkResponse) {
                                if (networkResponse.data.ISResultHasData == 1 && networkResponse.data.likeData.success){
                                    mNumberLikes = 1;
                                    mLikes.setText(1 + " " + "Users like this post");
                                }
                            }

                            @Override
                            public void networkOperationFail(Throwable throwable) {
                                Toast.makeText(CommentActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Intent intent = new Intent(CommentActivity.this, UserLikesActivity.class);
                    intent.putExtra("string_key", mId);
                    startActivity(intent);
                }

            }
        });
//        mLikeImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CommentActivity.this, UserLikesActivity.class);
//                intent.putExtra("string_key", mId);
//                startActivity(intent);
//            }
//        });
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(CommentActivity.this)){
                    presenter.postComment(PreferenceHelper.getUserId(CommentActivity.this), mId, editText.getText().toString());
                }else {
                    Toast.makeText(CommentActivity.this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }
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
    public void UpdateUi(List<Comment> TimelineList) {
        rv.setAdapter(new AdminAdapter(this, TimelineList));
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void Update() {

        presenter.comment(mId, this);
        editText.setText("");

    }

    private static class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
        private List<Comment> mGroupUsersList;

        Context context;

        public AdminAdapter(Context context, List<Comment> TimeLineList) {
            this.context = context;
            this.mGroupUsersList = TimeLineList;

        }

        @Override
        public AdminAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.list_item_comment, parent, false);
            return new AdminAdapter.ViewHolder(noteView);
        }

        @Override
        public void onBindViewHolder(AdminAdapter.ViewHolder viewHolder, int position) {

            Comment timeLineItem = mGroupUsersList.get(position);
//            viewHolder.profile_pic.
            Glide.with(context)
                    .load(MainApi.IMAGE_IP + timeLineItem.UserImage).placeholder(R.drawable.ic_profile)
                    .into(viewHolder.profile);
            viewHolder.mName.setText(timeLineItem.Comment);
            viewHolder.mFrom.setText(timeLineItem.UserName);

            String DateWithoutTime = String.valueOf(AppUtils.getDate(timeLineItem.CreationDate)).substring(0, 10);
            viewHolder.mDate.setText(DateWithoutTime);
        }

        @Override
        public int getItemCount() {
            return mGroupUsersList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView profile;
            TextView mName, mFrom, mDate;

            public ViewHolder(View itemView) {
                super(itemView);
                profile = (ImageView) itemView.findViewById(R.id.imageView6);
                mName = (TextView) itemView.findViewById(R.id.textView3);
                mFrom = (TextView) itemView.findViewById(R.id.textView4);
                mDate = (TextView) itemView.findViewById(R.id.textView5);
//                itemView.findViewById(R.id.button8).setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                context.startActivity(new Intent(context, MemberProfile.class));
            }
        }
    }
}
