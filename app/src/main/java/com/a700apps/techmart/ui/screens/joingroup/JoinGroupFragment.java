package com.a700apps.techmart.ui.screens.joingroup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.CategoryData;
import com.a700apps.techmart.ui.screens.category.CategoryActivity;
import com.a700apps.techmart.ui.screens.category.CategoryPresenter;
import com.a700apps.techmart.ui.screens.category.CategoryView;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.listgroup.GroubListActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.ButterKnife;

/**
 * Created by samir salah on 8/16/2017.
 */

public class JoinGroupFragment extends Fragment implements CategoryView, View.OnClickListener {
//
//    ImageView notiBtn, editBtn, messageBtn;
    ImageView  imageView4;
    ImageView mProfileImageView, mNotificationImageView;


    boolean isCountry = false, isBussines = false, isGroup = false;
    private static final int STATE_IS_COUNTRY = 1;
    private static final int STATE_IS_BUSINESS = 2;
    private static final int STATE_IS_GROUP = 3;
    ImageView countryBtn, businessBtn, groupBtn;
    TextView mFirstTextView, mSecondTextView, mThirdTextView;
    private CategoryPresenter presenter;
    public AVLoadingIndicatorView indicatorView;
    int intSelectedId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    void findView(View view) {

        Button nextBtn = (Button) view.findViewById(R.id.nextBtn);
        countryBtn = (ImageView) view.findViewById(R.id.iv_country_council);
        businessBtn = (ImageView) view.findViewById(R.id.iv_business_associates);
        groupBtn = (ImageView) view.findViewById(R.id.iv_groups);
        mFirstTextView = (TextView) view.findViewById(R.id.textView);
        mSecondTextView = (TextView) view.findViewById(R.id.textView2);
        mThirdTextView = (TextView) view.findViewById(R.id.textView3);
        indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);

        presenter = new CategoryPresenter();
        presenter.attachView(this);

        presenter.getCategory();

        nextBtn.setOnClickListener(this);
        countryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateIcons(STATE_IS_COUNTRY);
            }
        });
        businessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateIcons(STATE_IS_BUSINESS);
            }
        });

        groupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateIcons(STATE_IS_GROUP);
            }
        });



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
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                startActivity(new Intent(getActivity(), GroubListActivity.class));
//            }
//        });
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
        return inflater.inflate(R.layout.fragment_join_group, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        findView(view);
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
    public void updateUi(CategoryData data) {
        for (int i = 0; i < data.category.size(); i++) {
            {

                mFirstTextView.setText(data.category.get(0).Name);
                countryBtn.setId(data.category.get(0).ID);
                mSecondTextView.setText(data.category.get(1).Name);
                businessBtn.setId(data.category.get(1).ID);
                mThirdTextView.setText(data.category.get(2).Name);
                groupBtn.setId(data.category.get(2).ID);

            }
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nextBtn:
                if ((isBussines||isCountry||isGroup)==false){
                    Snackbar snackbar1 = Snackbar.make(view, R.string.select_group, Snackbar.LENGTH_SHORT);
                    snackbar1.setActionTextColor(Color.WHITE);
                    snackbar1.show();
                }else {

                Log.e("intSelectedId", intSelectedId + "");
                Intent myIntent = new Intent(getActivity(), GroubListActivity.class);
                myIntent.putExtra("selectedCategory", intSelectedId);
                startActivity(myIntent);

                }
                break;
        }
    }
}
