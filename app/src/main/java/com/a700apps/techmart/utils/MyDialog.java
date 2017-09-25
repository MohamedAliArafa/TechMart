package com.a700apps.techmart.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.a700apps.techmart.R;

/**
 * Created by samir salah on 9/14/2017.
 */

public class MyDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity activity;

    public MyDialog() {
        super(null);
    }
    public MyDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_dialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
        dismiss();
    }

}