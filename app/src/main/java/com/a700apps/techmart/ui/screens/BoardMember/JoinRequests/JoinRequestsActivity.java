package com.a700apps.techmart.ui.screens.BoardMember.JoinRequests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.ApprovalAdapter;
import com.a700apps.techmart.data.model.JoinGroupRequestsData;
import com.a700apps.techmart.utils.PreferenceHelper;

import java.util.List;

public class JoinRequestsActivity extends AppCompatActivity implements RequestsView{

    RecyclerView rv;
    RequestsPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_requests);

        presenter = new RequestsPresenter();
        presenter.attachView(this);

        rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        presenter.getGroupRequests("38" , PreferenceHelper.getUserId(this));
    }

    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void dismissLoadingProgress() {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void updateData(List<JoinGroupRequestsData.Result> list) {
        rv.setAdapter(new ApprovalAdapter(this , list));
    }
}
