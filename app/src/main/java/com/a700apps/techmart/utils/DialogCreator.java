package com.a700apps.techmart.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;


/**
 * Created by ahmed agamy on 04/04/2017.
 */

public class DialogCreator {



    public static void showOneButtonDialog(Context context, int tittle, int text,
                                           AppDialog.RightButtonClickListener buttonClickListener) {
        AppDialog.SmallDialog dialog = new AppDialog.SmallDialog(context);
        dialog.dialogTitle(tittle);
        dialog.dialogText(text);
        dialog.showText(true);
        dialog.showEditText(false);
        dialog.showLeftButton(false);
        dialog.showQuestion(false);

        if (buttonClickListener != null) {
            dialog.rightButtonClickListener(buttonClickListener);
        }

        dialog.show();
    }


    public static void showTwoButtonDialog(Context context, int tittle, int text,
                                           AppDialog.RightButtonClickListener buttonClickListener,
                                           AppDialog.LeftButtonClickListener leftButtonClickListener) {
        AppDialog.SmallDialog dialog = new AppDialog.SmallDialog(context);
        dialog.dialogTitle(tittle);
        dialog.showLeftButton(true);
        dialog.showEditText(false);
        dialog.showQuestion(true);
        dialog.dialogQuestion(text);
        if (buttonClickListener != null) {
            dialog.rightButtonClickListener(buttonClickListener);
        }

        if (leftButtonClickListener != null) {
            dialog.leftButtonClickListener(leftButtonClickListener);
        }

        dialog.show();
    }


    public static ProgressDialog createProgressDialog(Context context, int text) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(context.getString(text));
        return progressDialog;
    }



}
