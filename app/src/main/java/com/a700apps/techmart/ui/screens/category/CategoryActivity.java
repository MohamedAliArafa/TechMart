package com.a700apps.techmart.ui.screens.category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.CategoryData;
import com.a700apps.techmart.ui.screens.listgroup.GroubListActivity;
import com.a700apps.techmart.ui.screens.register.RegisterPresenter;
import com.wang.avi.AVLoadingIndicatorView;

public class CategoryActivity extends AppCompatActivity  implements CategoryView,View.OnClickListener{

    boolean isCountry = false, isBussines = false, isGroup = false;
    private static final int STATE_IS_COUNTRY = 1;
    private static final int STATE_IS_BUSINESS = 2;
    private static final int STATE_IS_GROUP = 3;
    ImageView countryBtn, businessBtn, groupBtn;
    TextView mFirstTextView,mSecondTextView,mThirdTextView;
    private CategoryPresenter presenter;
    public AVLoadingIndicatorView indicatorView;
   int   intSelectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Button nextBtn = (Button) findViewById(R.id.nextBtn);
        countryBtn = (ImageView) findViewById(R.id.iv_country_council);
        businessBtn = (ImageView) findViewById(R.id.iv_business_associates);
        groupBtn = (ImageView) findViewById(R.id.iv_groups);
        mFirstTextView=(TextView)findViewById(R.id.textView);
        mSecondTextView=(TextView)findViewById(R.id.textView2);
        mThirdTextView=(TextView)findViewById(R.id.textView3);
        indicatorView= (AVLoadingIndicatorView) findViewById(R.id.avi);

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
    }

    private void updateIcons(int state) {
        switch (state) {
            case STATE_IS_COUNTRY:
                isCountry = true;
                isBussines = isGroup = false;
                intSelectedId =countryBtn.getId();
                Log.e("STATE_IS_COUNTRY",String.valueOf(countryBtn.getId()));
                countryBtn.setImageResource(R.drawable.ic_1_active);
                businessBtn.setImageResource(R.drawable.ic_2);
                groupBtn.setImageResource(R.drawable.ic_3);
                break;
            case STATE_IS_BUSINESS:
                isBussines = true;
                isCountry = isGroup = false;
                intSelectedId =businessBtn.getId();
                Log.e("STATE_IS_business",String.valueOf(businessBtn.getId()));
                countryBtn.setImageResource(R.drawable.ic_1);
                businessBtn.setImageResource(R.drawable.ic_2_active);
                groupBtn.setImageResource(R.drawable.ic_3);
                break;
            case STATE_IS_GROUP:
                isGroup = true;
                isBussines = isCountry = false;
                intSelectedId =groupBtn.getId();
                Log.e("STATE_IS_GROUP",String.valueOf(groupBtn.getId()));

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
        for (int i=0;i<data.category.size();i++){{

            mFirstTextView.setText(data.category.get(0).Name);
            countryBtn.setId(data.category.get(0).ID);
            mSecondTextView.setText(data.category.get(1).Name);
            businessBtn.setId(data.category.get(1).ID);
            mThirdTextView.setText(data.category.get(2).Name);
            groupBtn.setId(data.category.get(2).ID);

        }}


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nextBtn:
                Log.e("intSelectedId",intSelectedId+"");
                Intent myIntent = new Intent(CategoryActivity.this, GroubListActivity.class);
                myIntent.putExtra("selectedCategory", intSelectedId);
                startActivity(myIntent);

                break;
        }
    }
}
