package com.a700apps.techmart.ui.screens.message;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.AllMessageList;
import com.a700apps.techmart.data.model.FriendMessage;
import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.register.RegisterActivity;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;

import java.util.List;

public class ChatActivity extends AppCompatActivity implements MessageView {

    private MessagePresenter presenter;
    private RecyclerView messageList;
    private ImageView backImageView, chatImageView, sendImageView;
    private TextView chatNameTextView;
    public EditText editComment;
    private String ReciverName, ReciverPhoto, RelativeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        presenter = new MessagePresenter();
        presenter.attachView(this);
        findViews();


    }

    private void findViews() {
        ImageView newBtn = (ImageView) findViewById(R.id.new_message);
        editComment = (EditText) findViewById(R.id.message_edit_text);
        messageList = (RecyclerView) findViewById(R.id.recyclerView);
        backImageView = (ImageView) findViewById(R.id.back_Image_view);
        sendImageView = (ImageView) findViewById(R.id.send_Image_view);
        chatImageView = (ImageView) findViewById(R.id.image_chat);
        chatNameTextView = (TextView) findViewById(R.id.chat_name_text_view);
        messageList.setLayoutManager(new LinearLayoutManager(this));

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (getIntent().hasExtra("RelativeID")) {
            RelativeID = getIntent().getStringExtra("RelativeID");
            ReciverName = getIntent().getStringExtra("ReciverName");
            ReciverPhoto = getIntent().getStringExtra("ReciverPhoto");
            chatNameTextView.setText(ReciverName);
            Log.e("photrrro", MainApi.IMAGE_IP + ReciverPhoto);
            Glide.with(ChatActivity.this).load(MainApi.IMAGE_IP + ReciverPhoto).placeholder(R.drawable.placeholder)
                    .into(chatImageView);
            Log.e("RelativeID", RelativeID);
            if (RelativeID != null) {
                presenter.getFriendMessage(ChatActivity.this, PreferenceHelper.getUserId(ChatActivity.this), RelativeID);
            }
            sendImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!AppUtils.isInternetAvailable(ChatActivity.this)) {
                        Snackbar snackbar1 = Snackbar.make(view, R.string.no_internet_connection, Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    } else {
                        if (!editComment.getText().toString().isEmpty()) {
                            presenter.sendMessage(ChatActivity.this, PreferenceHelper.getUserId(ChatActivity.this),
                                    RelativeID, editComment.getText().toString());
                        } else {
                            Toast.makeText(ChatActivity.this, "please insert text", Toast.LENGTH_SHORT).show();
                        }

                    }


                }

            });
        }

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
    public void updateUi() { // clear edittext and call service again

        editComment.setText("");
        presenter.getFriendMessage(ChatActivity.this, PreferenceHelper.getUserId(ChatActivity.this), RelativeID);


    }

    @Override
    public void fillMessagesList(List<AllMessageList.ResultEntity> responser) {

    }

    @Override
    public void fillConectionList(List<MyConnectionList.ResultEntity> responser) {

    }

    @Override
    public void fillFriendChatList(List<FriendMessage.ResultEntity> responser) {
        ChatAdapter chatAdapter = new ChatAdapter(this, responser);
        messageList.setAdapter(chatAdapter);
        messageList.scrollToPosition(responser.size() - 1); // to scroll to last item
    }


    @Override
    public void openChatActivity() {

    }

    private class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context context;
        private static final int CHAT_TYPE_ME = 0;
        private static final int CHAT_TYPE_OTHER = 1;
        private int ITEM_COUNT = 25;
        List<FriendMessage.ResultEntity> responser;

        public ChatAdapter(Context context) {
            this.context = context;
        }

        public ChatAdapter(Context context, List<FriendMessage.ResultEntity> responser) {
            this.context = context;
            this.responser = responser;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView;
            switch (viewType) {
                case CHAT_TYPE_ME:
                    noteView = inflater.inflate(R.layout.chat_you_item, parent, false);
                    return new ViewHolderMe(noteView);

                case CHAT_TYPE_OTHER:
                    noteView = inflater.inflate(R.layout.chat_other_list, parent, false);
                    return new ViewHolderOther(noteView);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final int itemType = getItemViewType(position);
            switch (itemType) {
                case CHAT_TYPE_ME:
                    ViewHolderMe viewHolderMe = (ViewHolderMe) holder;
                    viewHolderMe.ivDate.setText(responser.get(position).getReadingDateTimeST());
                    viewHolderMe.tvText.setText(responser.get(position).getMessage());
                    Glide.with(context).load(MainApi.IMAGE_IP + responser.get(position).getSenderPhoto()).placeholder(R.drawable.placeholder)
                            .into(viewHolderMe.myImage);
                    break;
                case CHAT_TYPE_OTHER:
                    ViewHolderOther viewHolderOther = (ViewHolderOther) holder;
                    viewHolderOther.tvDate.setText(responser.get(position).getReadingDateTimeST());
                    viewHolderOther.tvText.setText(responser.get(position).getMessage());
                    Glide.with(context).load(MainApi.IMAGE_IP + responser.get(position).getReciverPhoto()).placeholder(R.drawable.placeholder)
                            .into(viewHolderOther.otherImage);
                    break;
            }
        }

//        void addChatItem() {
//            ITEM_COUNT++;
//            notifyDataSetChanged();
//        }

        @Override
        public int getItemViewType(int position) {
            if (responser.get(position).getSenderUserID().equals(PreferenceHelper.getUserId(context))) {
                return CHAT_TYPE_ME;
            } else {
                return CHAT_TYPE_OTHER;
            }
        }

        @Override
        public int getItemCount() {
            return responser.size();
        }

        public class ViewHolderOther extends RecyclerView.ViewHolder {
            private ImageView otherImage;
            private TextView tvText;
            private TextView tvDate;

            ViewHolderOther(View view) {
                super(view);
                otherImage = (ImageView) view.findViewById(R.id.other_image);
                tvText = (TextView) view.findViewById(R.id.tv_text);
                tvDate = (TextView) view.findViewById(R.id.tv_date);
            }
        }

        public class ViewHolderMe extends RecyclerView.ViewHolder {

            private ImageView myImage;
            private TextView tvText;
            private TextView ivDeliver;
            private ImageView showText;
            private TextView ivDate;

            ViewHolderMe(View view) {
                super(view);
                myImage = (ImageView) view.findViewById(R.id.my_image);
                tvText = (TextView) view.findViewById(R.id.tv_text);
                ivDeliver = (TextView) view.findViewById(R.id.iv_deliver);
                showText = (ImageView) view.findViewById(R.id.show_text);
                ivDate = (TextView) view.findViewById(R.id.iv_date);


            }
        }
    }
}
