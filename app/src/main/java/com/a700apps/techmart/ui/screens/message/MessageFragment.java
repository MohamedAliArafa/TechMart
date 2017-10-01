package com.a700apps.techmart.ui.screens.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.AllMessageList;
import com.a700apps.techmart.data.model.FriendMessage;
import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.newchat.*;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by samir salah on 8/16/2017.
 */

public class MessageFragment extends Fragment implements MessageView  {
    private MessagePresenter presenter;
    private RecyclerView messageList;
    private ImageView backImageView, newMessageImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_messages, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        presenter = new MessagePresenter();
        presenter.attachView(this);
        findViews(view);
    }

    private void findViews(View view) {
        newMessageImageView = (ImageView)view. findViewById(R.id.new_message);
        newMessageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NewChatActivity.class));
            }
        });
        messageList = (RecyclerView)view. findViewById(R.id.recyclerView);
        backImageView = (ImageView) view.findViewById(R.id.back_Image_view);
        messageList.setLayoutManager(new LinearLayoutManager(getActivity()));

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).openDrawer();
            }
        });
        presenter.userInbox(getActivity(), PreferenceHelper.getUserId(getActivity()));
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
        MessagesAdapter messagesAdapter = new MessagesAdapter(getActivity(), responser);
        messageList.setAdapter(messagesAdapter);

    }

    @Override
    public void fillConectionList(List<MyConnectionList.ResultEntity> responser) {

    }

    @Override
    public void fillFriendChatList(List<FriendMessage.ResultEntity> responser) {

    }

    @Override
    public void openChatActivity() {

    }
    private class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

        Context context;
        List<AllMessageList.ResultEntity> responser;

        public MessagesAdapter(Context context) {
            this.context = context;
        }

        public MessagesAdapter(Context context, List<AllMessageList.ResultEntity> responser) {
            this.context = context;
            this.responser = responser;
        }

        @Override
        public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.message_list_item, parent, false);
            return new MessagesAdapter.ViewHolder(noteView);
        }

        @Override
        public void onBindViewHolder(MessagesAdapter.ViewHolder viewHolder, final int position) {
            viewHolder.nameTextView.setText(responser.get(position).getReciverName());
            viewHolder.chatTextTextView.setText(responser.get(position).getMessage());
            viewHolder.lastSeenTextView.setText(responser.get(position).getReadingDateTimeST());
            Glide.with(context).load(MainApi.IMAGE_IP + responser.get(position).getReciverPhoto()).placeholder(R.drawable.placeholder)
                    .into(viewHolder.userImageView);
            viewHolder.messageItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("RelativeID", responser.get(position).getReciverUserID());
                    intent.putExtra("ReciverName", responser.get(position).getReciverName());
                    intent.putExtra("ReciverPhoto", responser.get(position).getReciverPhoto());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return responser.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView userImageView;
            private TextView nameTextView;
            private ImageView seenImage;
            private TextView chatTextTextView;
            private TextView lastSeenTextView;
            private ConstraintLayout messageItem;

            public ViewHolder(View view) {
                super(view);
                userImageView = (ImageView) view.findViewById(R.id.user_image_view);
                nameTextView = (TextView) view.findViewById(R.id.name_text_view);
                seenImage = (ImageView) view.findViewById(R.id.seen_image);
                chatTextTextView = (TextView) view.findViewById(R.id.chat_text_text_view);
                lastSeenTextView = (TextView) view.findViewById(R.id.last_seen_text_view);
                messageItem = (ConstraintLayout) view.findViewById(R.id.message_item);

            }
        }

    }
}
