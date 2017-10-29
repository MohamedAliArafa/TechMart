package com.a700apps.techmart.ui.screens.PredefinedMessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.PredifinedAdapter;
import com.a700apps.techmart.data.model.PredifinedData;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;

import java.util.List;

public class PredifinedMessageActivity extends AppCompatActivity implements PredifinedView {

    RecyclerView recyclerView;
    PredifiednPresenter presenter;
    Button send_predifined;

    TextView memberName, tvSendTo;
    ImageView memberImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predifined_message);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        send_predifined = (Button) findViewById(R.id.send_predifined);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        memberImage = (ImageView) findViewById(R.id.memberPhoto);
        tvSendTo = (TextView) findViewById(R.id.tv_predifined);
        memberName = (TextView) findViewById(R.id.memberName);

        presenter = new PredifiednPresenter();
        presenter.attachView(this);


        final String relativeId = getIntent().getStringExtra("RelativeID");
        final String name = getIntent().getStringExtra("ReciverName");
        final String image = getIntent().getStringExtra("ReciverPhoto");


        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Glide.with(this).load(image).placeholder(R.drawable.profile_pic).into(memberImage);
        memberName.setText("" + name);
        tvSendTo.setText("Send " + name + " a contact request");
        presenter.LoadData();
        Log.e("RelativeID", relativeId);

        send_predifined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(PredifinedMessageActivity.this))
                    presenter.sendMessage(PreferenceHelper.getUserId(PredifinedMessageActivity.this), relativeId, Globals.MESSAGE);
                else
                    Toast.makeText(PredifinedMessageActivity.this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void dismissLoadingProgress() {

    }

    @Override
    public void showErrorDialog(int error) {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateUi(List<PredifinedData.Result> list) {
        recyclerView.setAdapter(new PredifinedAdapter(this, list));
    }

    @Override
    public void back() {
        onBackPressed();
    }
}
