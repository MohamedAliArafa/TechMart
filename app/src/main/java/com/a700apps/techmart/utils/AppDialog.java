package com.a700apps.techmart.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.a700apps.techmart.R;


/**
 * Created by ahmed agamy on 05/04/2017.
 */

public class AppDialog {



//

    interface LeftButtonClickListener {

        void onLeftButtonClick(AlertDialog alertDialog);
    }

    public interface RightButtonClickListener {

        void onRightButtonClick(AlertDialog alertDialog);
    }

    public interface EditTextDataListener {
        void getEditText();
    }

    static class SmallDialog {

        private Context context;
        private int dialogTitle, dialogQuestion;
        String dialogText;
        private int rightButtonText, leftButtonText;
        private boolean showLeftButton, showQuestion, showText, showEditText;
        private TextView titleTextView, textTextView, questionTextView;
        private TextView leftButton, rightButton;
        private EditText mRecommendCode;
        private View dialogView;
        private LeftButtonClickListener leftButtonClickListener;
        private RightButtonClickListener rightButtonClickListener;
        private EditTextDataListener edittextDataListener;
        private AlertDialog dialog;
        private int dialogEditText, mRecommendedText;

        SmallDialog(Context context) {
            this.context = context;
        }


        SmallDialog dialogTitle(int title) {
            this.dialogTitle = title;
            return this;
        }

        SmallDialog dialogText(String text) {
            this.dialogText = text;
            return this;
        }

        SmallDialog dialogEditText(int text) {
            this.dialogEditText = text;
            return this;
        }


        public SmallDialog dialogQuestion(int question) {
            this.dialogQuestion = question;
            return this;
        }


        public SmallDialog leftButtonText(int text) {
            this.leftButtonText = text;
            return this;
        }

        public SmallDialog rightButtonText(int text) {
            this.rightButtonText = text;
            return this;
        }


        public SmallDialog showText(boolean showText) {
            this.showText = showText;
            return this;
        }

        public SmallDialog showQuestion(boolean showQuestion) {
            this.showQuestion = showQuestion;
            return this;
        }

        public SmallDialog showLeftButton(boolean showLeftButton) {
            this.showLeftButton = showLeftButton;
            return this;
        }


        public SmallDialog showEditText(boolean showEditText) {
            this.showEditText = showEditText;
            return this;
        }

        public SmallDialog leftButtonClickListener(LeftButtonClickListener leftButtonClickListener) {
            this.leftButtonClickListener = leftButtonClickListener;
            return this;
        }

        public SmallDialog rightButtonClickListener(RightButtonClickListener rightButtonClickListener) {
            this.rightButtonClickListener = rightButtonClickListener;
            return this;
        }

        public SmallDialog editTextListener(EditTextDataListener edittextlistener) {
            this.edittextDataListener = edittextlistener;
            return this;
        }


        public void show() {
            findViews();
            showDialog();
        }

        private void showDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(dialogView);
            dialog = builder.create();
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id
            dialog.show();
        }

        private void findViews() {
            dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_small_layout, null);
//            ActivityUtils.applyFont(dialogView);
            titleTextView = ActivityUtils.findView(dialogView, R.id.tv_dialog_title, TextView.class);
            textTextView = ActivityUtils.findView(dialogView, R.id.tv_dialog_text, TextView.class);
            leftButton = ActivityUtils.findView(dialogView, R.id.tv_dialog_cancel, TextView.class);
            rightButton = ActivityUtils.findView(dialogView, R.id.tv_dialog_ok, TextView.class);
//
            textTextView.setText(dialogTitle);
            if (dialogTitle != 0) {
                titleTextView.setText(dialogText);
            }

//
//            if (showQuestion) {
//                questionTextView.setVisibility(View.VISIBLE);
//                if (dialogQuestion != 0) {
//                    questionTextView.setText(dialogQuestion);
//                }
//            } else {
//                questionTextView.setVisibility(View.GONE);
//            }

            if (showLeftButton) {
                leftButton.setVisibility(View.VISIBLE);
                if (leftButtonText != 0) {
                    leftButton.setText(leftButtonText);
                }

                leftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (leftButtonClickListener != null) {
                            leftButtonClickListener.onLeftButtonClick(dialog);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });

            } else {
                leftButton.setVisibility(View.GONE);
            }

            // right button.
            if (rightButtonText != 0) {
                rightButton.setText(rightButtonText);
            }

            rightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rightButtonClickListener != null) {
                        rightButtonClickListener.onRightButtonClick(dialog);
                    } else {
                        dialog.dismiss();
                    }
                }
            });

        }
    }



}
