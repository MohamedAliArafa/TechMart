package com.a700apps.techmart.ui.screens.joingroup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.Category;
import com.a700apps.techmart.data.model.CategoryData;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.category.CategoryActivity;
import com.a700apps.techmart.ui.screens.category.CategoryPresenter;
import com.a700apps.techmart.ui.screens.category.CategoryView;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.listgroup.GroubListActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.DateTimePicker.CustomLightTextView;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.loadingDialog;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by samir salah on 8/16/2017.
 */

public class JoinGroupFragment extends Fragment implements CategoryView, View.OnClickListener {
    //
//    ImageView notiBtn, editBtn, messageBtn;
    ImageView imageView4;
    ImageView mProfileImageView, mNotificationImageView;
    Dialog dialogsLoading;

    boolean isCountry = false, isBussines = false, isGroup = false;
    private static final int STATE_IS_COUNTRY = 1;
    private static final int STATE_IS_BUSINESS = 2;
    private static final int STATE_IS_GROUP = 3;

    ImageView countryBtn, businessBtn, groupBtn;
    TextView mFirstTextView, mSecondTextView, mThirdTextView;
    private CategoryPresenter presenter;
    public AVLoadingIndicatorView indicatorView;
    int intSelectedId;
    View view;
    MemberAdapter memberAdapter;


    EmptyRecyclerView rv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    void findView(View view) {

        Button nextBtn = (Button) view.findViewById(R.id.nextBtn);


        rv = (EmptyRecyclerView) view.findViewById(R.id.recyclerView);
        int numberOfColumns = 3;
        rv.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        presenter = new CategoryPresenter();
        presenter.attachView(this);

        presenter.getCategory();

        nextBtn.setOnClickListener(this);


        imageView4 = (ImageView) view.findViewById(R.id.imageView4);

        mNotificationImageView = (ImageView) view.findViewById(R.id.new_profile);

        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(getActivity(), NotificationActivity.class, false);


            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).openDrawer();
            }
        });

    }

    private void updateIcons(int state) {
        switch (state) {
            case STATE_IS_COUNTRY:
                isCountry = true;
                isBussines = isGroup = false;
                intSelectedId = countryBtn.getId();
                Log.e("STATE_IS_COUNTRY", String.valueOf(countryBtn.getId()));
                countryBtn.setImageResource(R.drawable.ic_1_active);
                businessBtn.setImageResource(R.drawable.ic_2);
                groupBtn.setImageResource(R.drawable.ic_3);
                break;
            case STATE_IS_BUSINESS:
                isBussines = true;
                isCountry = isGroup = false;
                intSelectedId = businessBtn.getId();
                Log.e("STATE_IS_business", String.valueOf(businessBtn.getId()));
                countryBtn.setImageResource(R.drawable.ic_1);
                businessBtn.setImageResource(R.drawable.ic_2_active);
                groupBtn.setImageResource(R.drawable.ic_3);
                break;
            case STATE_IS_GROUP:
                isGroup = true;
                isBussines = isCountry = false;
                intSelectedId = groupBtn.getId();
                Log.e("STATE_IS_GROUP", String.valueOf(groupBtn.getId()));

                countryBtn.setImageResource(R.drawable.ic_1);
                businessBtn.setImageResource(R.drawable.ic_2);
                groupBtn.setImageResource(R.drawable.ic_3_active);
                break;
            default:
                isCountry = isBussines = isGroup = false;
                countryBtn.setImageResource(R.drawable.ic_1);
                businessBtn.setImageResource(R.drawable.ic_2);
                groupBtn.setImageResource(R.drawable.ic_3);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_join_group, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        findView(view);
    }

    @Override
    public void showProgress() {
//
        dialogsLoading = new loadingDialog().showDialog(getActivity());

    }

    @Override
    public void dismissProgress() {

        dialogsLoading.dismiss();
    }

    @Override
    public void updateUi(CategoryData data) {

        memberAdapter = new MemberAdapter(getActivity(), data.category);
        if (data.category.size() == 0) {
            rv.setEmptyView(view.findViewById(R.id.tv_nodata));
        }
        rv.setAdapter(memberAdapter);

    }


    @Override
    public void onResume() {
        super.onResume();
        if (memberAdapter!=null){
            memberAdapter.SelectedItem=-1;
            memberAdapter.notifyDataSetChanged();
            Globals.SELECTED_ID=-1;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nextBtn:
                if (!AppUtils.isInternetAvailable(getActivity())) {
                    Snackbar snackbar1 = Snackbar.make(view, R.string.no_internet_connection, Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                }else {

                if (Globals.SELECTED_ID==-1) {
                    Snackbar snackbar1 = Snackbar.make(view, R.string.select_group, Snackbar.LENGTH_SHORT);
                    snackbar1.setActionTextColor(Color.WHITE);
                    snackbar1.show();
                } else {
                    Log.e("intSelectedId", Globals.SELECTED_ID + "");
                    Intent myIntent = new Intent(getActivity(), GroubListActivity.class);
                    myIntent.putExtra("selectedCategory", Globals.SELECTED_ID);
                    startActivity(myIntent);
                    Globals.SELECTED_ID=-1;
                }

                }
                break;
        }
    }

    //

    static class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {
        private List<Category> mCategoryList;
        Context context;
        int SelectedItem = -1;


        public MemberAdapter(Context context, List<Category> categoryList) {
            this.context = context;
            this.mCategoryList = categoryList;
        }

        @Override
        public MemberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.join_group_item, parent, false);
            return new MemberAdapter.ViewHolder(noteView);
        }

        @Override
        public void onBindViewHolder(final MemberAdapter.ViewHolder viewHolder, final int position) {
            final Category timeLineItem = mCategoryList.get(position);

            Glide.with(context)
                    .load(MainApi.IMAGE_IP + timeLineItem.Icon).placeholder(R.drawable.ic_profile)
                    .into(viewHolder.profile_pic);

            viewHolder.mGroupName.setText(timeLineItem.Name);
            viewHolder.mContainerLinearLyaout.setBackgroundColor(context.getResources().getColor(R.color.transparent));

            viewHolder.mContainerLinearLyaout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectedItem = position;
                    Globals.SELECTED_ID = timeLineItem.ID;
                    notifyDataSetChanged();
                }
            });

            if (position == SelectedItem){
                viewHolder.mContainerLinearLyaout.setBackgroundColor(context.getResources().getColor(R.color.red_join_dialog));
            }else {
                viewHolder.mContainerLinearLyaout.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            }
        }

        @Override
        public int getItemCount() {
            return mCategoryList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView profile_pic;
            LinearLayout mContainerLinearLyaout;
            CustomLightTextView mGroupName;

            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                profile_pic = (ImageView) itemView.findViewById(R.id.iv_group);
                mGroupName = (CustomLightTextView) itemView.findViewById(R.id.tv_group_name);
                mContainerLinearLyaout = (LinearLayout) itemView.findViewById(R.id.container);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
            }
        }
    }

}
