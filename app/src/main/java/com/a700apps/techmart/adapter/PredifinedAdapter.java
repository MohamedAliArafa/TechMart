package com.a700apps.techmart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

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
        viewHolder.radioButton.setText(item.getText());
        viewHolder.radioButton.setChecked(position == lastCheckedPosition);
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

        RadioButton radioButton;

        public ViewHolderPost(View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.cb_predifined);

            radioButton.setOnClickListener(new View.OnClickListener() {
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


