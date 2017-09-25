package com.a700apps.techmart.ui.screens.timeline;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupTimeLineActivity;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsActivity;
import com.a700apps.techmart.utils.ActivityUtils;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupTimeLineFragment extends Fragment {

    private static final int SELECT_PICTURE = 1;

    public GroupTimeLineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_time_line, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        rv.setAdapter(new GroupAdapter(getActivity()));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private static class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

        Context context;
        private static final int NOTIF_TYPE_ALARM = 0;
        private static final int NOTIF_TYPE_CONNECT = 1;
        private static final int NOTIF_TYPE_FOLLOW = 2;
        private static final int NOTIF_TYPE_POST = 3;
        private static final int NOTIF_TYPE_VIDEO = 4;


        public GroupAdapter(Context context) {
            this.context = context;
        }

        @Override
        public GroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView;
            switch (viewType) {
                case NOTIF_TYPE_ALARM:
                    noteView = inflater.inflate(R.layout.group_post, parent, false);
                    break;
                case NOTIF_TYPE_CONNECT:
                    noteView = inflater.inflate(R.layout.gouptimeline_item, parent, false);
                    break;
                case NOTIF_TYPE_FOLLOW:
                    noteView = inflater.inflate(R.layout.gouptimeline_item, parent, false);
                    break;
                case NOTIF_TYPE_POST:
                    noteView = inflater.inflate(R.layout.gouptimeline_item, parent, false);
                    break;
                case NOTIF_TYPE_VIDEO:
                    noteView = inflater.inflate(R.layout.gouptimeline_item, parent, false);
                    break;
                default:
                    noteView = inflater.inflate(R.layout.gouptimeline_item, parent, false);
                    break;
            }
            return new GroupAdapter.ViewHolder(noteView);
        }

        @Override
        public void onBindViewHolder(GroupAdapter.ViewHolder viewHolder, int position) {
            final int itemType = getItemViewType(position);
            switch (itemType) {
                case NOTIF_TYPE_ALARM:
                    viewHolder.uploadImageBtn.setOnClickListener(viewHolder);
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

            ImageView uploadImageBtn;

            public ViewHolder(View itemView) {
                super(itemView);
                uploadImageBtn = (ImageView) itemView.findViewById(R.id.iv_upload_image);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                int viewId = v.getId();
                switch (viewId) {
                    case R.id.iv_upload_image:
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        if (context instanceof Activity) {
                            ((Activity) context).startActivityForResult(Intent.createChooser(intent,
                                    "Select Picture"), SELECT_PICTURE);
                        }
                        break;
                    default:
                        //ToDo for all item click
                        break;
                }
            }
        }
    }

}
