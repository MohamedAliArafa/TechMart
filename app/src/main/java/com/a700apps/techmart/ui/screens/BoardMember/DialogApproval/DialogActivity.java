package com.a700apps.techmart.ui.screens.BoardMember.DialogApproval;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;

import java.util.List;

public class DialogActivity extends AppCompatActivity implements approvalView {

    Dialog dialogsLoading;
    int postId = 108, type = 2;
    ApprovalPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        presenter = new ApprovalPresenter();
        presenter.attachView(this);

        findViewById(R.id.dialog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialog();
            }
        });
    }

    private void showConfirmDialog() {

        // Create custom dialog object
        final Dialog dialog = new Dialog(DialogActivity.this);
        dialog.setContentView(R.layout.dialog_approval);
        dialog.show();

        ImageView approveImageView = dialog.findViewById(R.id.iv_approve);
        ImageView rejectImageView = dialog.findViewById(R.id.iv_reject);
        ImageView deferImageView = dialog.findViewById(R.id.iv_defer);
        ImageView iconClose = dialog.findViewById(R.id.ic_icon_close);

        TextView approveTextView = dialog.findViewById(R.id.tv_approve);
        TextView rejectTextView = dialog.findViewById(R.id.tv_reject);
        TextView deferTextView = dialog.findViewById(R.id.tv_defer);


        iconClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        approveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(DialogActivity.this), 1);
                dialog.dismiss();
            }
        });

        approveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(DialogActivity.this), 1);
                dialog.dismiss();
            }
        });

        rejectImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(DialogActivity.this), 2);
                dialog.dismiss();
            }
        });

        rejectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(DialogActivity.this), 2);
                dialog.dismiss();
            }
        });

        deferImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(DialogActivity.this), 3);
                dialog.dismiss();
            }
        });

        deferTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.manageTimeLineItem(postId, type, PreferenceHelper.getUserId(DialogActivity.this), 3);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void showLoadingProgress() {
        dialogsLoading = new loadingDialog().showDialog(this);
    }

    @Override
    public void dismissLoadingProgress() {
        dialogsLoading.dismiss();
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateUi(List<GroupTimeLineData.ResultEntity> TimelineList) {
        
    }
}
