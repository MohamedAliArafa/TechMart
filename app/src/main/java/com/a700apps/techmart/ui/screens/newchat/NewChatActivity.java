package com.a700apps.techmart.ui.screens.newchat;

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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.AutoCompleteMessagesAdapter;
import com.a700apps.techmart.data.model.AllMessageList;
import com.a700apps.techmart.data.model.FriendMessage;
import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.message.ChatActivity;
import com.a700apps.techmart.ui.screens.message.MessagePresenter;
import com.a700apps.techmart.ui.screens.message.MessageView;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class NewChatActivity extends AppCompatActivity implements MessageView {
    private MessagePresenter presenter;
    private RecyclerView connectionList;
    private AutoCompleteTextView autoCompleteText;
    private ArrayList<String> namesArrayList;
    private List<MyConnectionList.ResultEntity> connectionListResult;
    private ArrayList<String> UserIDsArrayList;
    private ImageView mBackImageView;
    private TextView mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
        presenter = new MessagePresenter();
        presenter.attachView(this);
        findViews();

    }

    private void findViews() {
        connectionListResult = new ArrayList<>();
        namesArrayList = new ArrayList<>();
        UserIDsArrayList = new ArrayList<>();
        connectionList = (RecyclerView) findViewById(R.id.recyclerView);
        autoCompleteText = (AutoCompleteTextView) findViewById(R.id.auto_complete_text);
        autoCompleteText.setThreshold(1);

        mCancel = (TextView) findViewById(R.id.tv_cancel);
        mBackImageView = (ImageView) findViewById(R.id.iv_back);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        connectionList.setLayoutManager(new LinearLayoutManager(this));
        presenter.getMyConnectionList(NewChatActivity.this, PreferenceHelper.getUserId(NewChatActivity.this));
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void openNewChatActivity() {

    }

    @Override
    public void updateUi() {

    }

    @Override
    public void fillMessagesList(List<AllMessageList.ResultEntity> responser) {

    }

    @Override
    public void fillConectionList(List<MyConnectionList.ResultEntity> responser) {
        MessagesAdapter messagesAdapter = new MessagesAdapter(this, responser);
        connectionListResult = responser;
        connectionList.setAdapter(messagesAdapter);
        for (int i = 0; i < responser.size(); i++) {
            namesArrayList.add(responser.get(i).getName());
            UserIDsArrayList.add(responser.get(i).getUserID());
        }
        autoCompleteText.setAdapter(new AutoCompleteMessagesAdapter(this, R.layout.notif_item_group_approved, responser));
        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MyConnectionList.ResultEntity resultEntity = (MyConnectionList.ResultEntity) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(NewChatActivity.this, ChatActivity.class);
                intent.putExtra("RelativeID", resultEntity.getUserID());
                intent.putExtra("ReciverName", resultEntity.getName());
                intent.putExtra("ReciverPhoto", resultEntity.getPhoto());
                startActivity(intent);
            }
        });
    }


    @Override
    public void fillFriendChatList(List<FriendMessage.ResultEntity> responser) {

    }

    @Override
    public void openChatActivity() {

    }

    private static class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
        private List<MyConnectionList.ResultEntity> responser;
        Context context;

        public MessagesAdapter(Context context) {
            this.context = context;
        }

        public MessagesAdapter(Context context, List<MyConnectionList.ResultEntity> responser) {
            this.context = context;
            this.responser = responser;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.message_list_item, parent, false);
            return new ViewHolder(noteView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Glide.with(context).load(MainApi.IMAGE_IP + responser.get(position).getPhoto()).placeholder(R.drawable.placeholder)
                    .into(viewHolder.userImageView);
            viewHolder.nameTextView.setText(responser.get(position).getName());
            viewHolder.seenImage.setVisibility(View.GONE);
            viewHolder.messageItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("RelativeID", responser.get(position).getUserID());
                    intent.putExtra("ReciverName", responser.get(position).getName());
                    intent.putExtra("ReciverPhoto", responser.get(position).getPhoto());
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return responser.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ConstraintLayout messageItem;
            private ImageView userImageView;
            private TextView nameTextView;
            private ImageView seenImage;
            private TextView chatTextTextView;
            private TextView lastSeenTextView;

            public ViewHolder(View view) {
                super(view);
                messageItem = (ConstraintLayout) view.findViewById(R.id.message_item);
                userImageView = (ImageView) view.findViewById(R.id.user_image_view);
                nameTextView = (TextView) view.findViewById(R.id.name_text_view);
                seenImage = (ImageView) view.findViewById(R.id.seen_image);
                chatTextTextView = (TextView) view.findViewById(R.id.chat_text_text_view);
                lastSeenTextView = (TextView) view.findViewById(R.id.last_seen_text_view);
            }

        }
    }
}
