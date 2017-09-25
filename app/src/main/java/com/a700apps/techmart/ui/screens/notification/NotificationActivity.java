package com.a700apps.techmart.ui.screens.notification;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a700apps.techmart.R;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setAdapter(new NotificationAdapter(this));
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private static class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

        Context context;
        private static final int NOTIF_TYPE_ALARM = 0;
        private static final int NOTIF_TYPE_CONNECT = 1;
        private static final int NOTIF_TYPE_FOLLOW = 2;
        private static final int NOTIF_TYPE_POST = 3;
        private static final int NOTIF_TYPE_VIDEO = 4;


        public NotificationAdapter(Context context) {
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView;
            switch (viewType){
                case NOTIF_TYPE_ALARM:
                    noteView = inflater.inflate(R.layout.notif_item_alarm, parent, false);
                    break;
                case NOTIF_TYPE_CONNECT:
                    noteView = inflater.inflate(R.layout.notif_item_connect, parent, false);
                    break;
                case NOTIF_TYPE_FOLLOW:
                    noteView = inflater.inflate(R.layout.notif_item_follow, parent, false);
                    break;
                case NOTIF_TYPE_POST:
                    noteView = inflater.inflate(R.layout.notif_item_post, parent, false);
                    break;
                case NOTIF_TYPE_VIDEO:
                    noteView = inflater.inflate(R.layout.notif_item_video, parent, false);
                    break;
                default:
                    noteView = inflater.inflate(R.layout.notif_item_connect, parent, false);
                    break;
            }
            return new ViewHolder(noteView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            final int itemType = getItemViewType(position);
            switch (itemType){
                case NOTIF_TYPE_ALARM:
                case NOTIF_TYPE_CONNECT:
                case NOTIF_TYPE_FOLLOW:
                case NOTIF_TYPE_POST:
                case NOTIF_TYPE_VIDEO:
            }
        }

        @Override
        public int getItemViewType(int position) {
            int type = position;
            switch (type) {
                case 0:
                    return NOTIF_TYPE_ALARM;
                case 1:
                    return NOTIF_TYPE_CONNECT;
                case 2:
                    return NOTIF_TYPE_FOLLOW;
                case 3:
                    return NOTIF_TYPE_POST;
                case 4:
                    return NOTIF_TYPE_VIDEO;
                default:
            }
            return super.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
            }
        }
    }
}
