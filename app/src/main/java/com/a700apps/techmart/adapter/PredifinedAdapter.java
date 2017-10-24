package com.a700apps.techmart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.LikeData;
import com.a700apps.techmart.data.model.PredifinedData;
import com.a700apps.techmart.data.network.NetworkResponse;
import com.a700apps.techmart.data.network.NetworkResponseListener;
import com.a700apps.techmart.utils.Globals;

import java.util.List;

/**
 * Created by khaled.badawy on 9/26/2017.
 */

public class PredifinedAdapter extends RecyclerView.Adapter<PredifinedAdapter.ViewHolderPost> implements NetworkResponseListener<LikeData> {
    Context context;
    private List<PredifinedData.Result> mTimeLineList;
    private int lastCheckedPosition = 0;

    public PredifinedAdapter(Context context, List<PredifinedData.Result> TimeLineList) {
        this.context = context;
        this.mTimeLineList = TimeLineList;
    }


    @Override
    public ViewHolderPost onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.predified_item, parent, false);
        return new ViewHolderPost(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderPost viewHolder, final int position) {
        final PredifinedData.Result item = mTimeLineList.get(position);
        viewHolder.message.setText(item.getText());
        if (lastCheckedPosition  == position){
            viewHolder.indicator.setBackground(context.getResources().getDrawable(R.drawable.round_red_dot_predifined));
        }else {
            viewHolder.indicator.setBackground(context.getResources().getDrawable(R.drawable.round_white_shape));
        }
//        viewHolder.radioButton.setChecked(position == lastCheckedPosition);
    }

    @Override
    public int getItemCount() {
        if (mTimeLineList == null) {
            return 0;
        }
        return mTimeLineList.size();
    }

    @Override
    public void networkOperationSuccess(NetworkResponse<LikeData> networkResponse) {

    }

    @Override
    public void networkOperationFail(Throwable throwable) {

    }

    public class ViewHolderPost extends RecyclerView.ViewHolder {

//        RadioButton radioButton;

        TextView message , indicator ;
        RelativeLayout layout;

        public ViewHolderPost(View itemView) {
            super(itemView);

//            radioButton = itemView.findViewById(R.id.cb_predifined);
            message = itemView.findViewById(R.id.predifined_message);
            indicator = itemView.findViewById(R.id.predifined_indicator);
            layout = itemView.findViewById(R.id.predifined_layout);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastCheckedPosition = getAdapterPosition();
                    Globals.MESSAGE = mTimeLineList.get(getAdapterPosition()).getText();
                    //because of this blinking problem occurs so
                    //i have a suggestion to add notifyDataSetChanged();
                    //   notifyItemRangeChanged(0, list.length);//blink list problem
                    notifyDataSetChanged();

                }
            });
        }
    }
}


