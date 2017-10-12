package com.a700apps.techmart.utils;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.InnerModle;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.creatEvent.EventPresenter;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by samir.salah on 9/20/2017.
 */

public class ImageDetailsActivity extends AppCompatActivity {
    ImageView mDetailsImageView, mCloseImageView;
    String ImageLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        findview();
        ImageLink = getIntent().getStringExtra("ImageUrl");
        Log.e("pathinmdkdk", ""+ImageLink);
        Glide.with(ImageDetailsActivity.this)
                .load( ImageLink).placeholder(R.drawable.placeholder)
                .into(mDetailsImageView);
    }

    void findview() {
        mDetailsImageView = (ImageView) findViewById(R.id.iv_bg);
        mCloseImageView = (ImageView) findViewById(R.id.iv_close);
        mCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
