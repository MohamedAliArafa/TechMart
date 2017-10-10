package com.a700apps.techmart.ui.screens.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.register.RegisterActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.DialogCreator;
import com.a700apps.techmart.utils.Validator;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by samir salah on 8/14/2017.
 */

public class LoginActivity extends Activity implements LoginView, View.OnClickListener {
    //GUI References.
    EditText emailEditText, passwordEditText;
    TextView mEmailTextView, mPasswordTextView;
    //Objects.
    private LoginPresenter presenter;
    private ProgressDialog progressDialog;
    public AVLoadingIndicatorView indicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        presenter = new LoginPresenter();
        presenter.attachView(this);
    }

    private void findViews() {

        emailEditText = ActivityUtils.findView(this, R.id.et_name, EditText.class);
        passwordEditText = ActivityUtils.findView(this, R.id.et_pass, EditText.class);
        mEmailTextView = ActivityUtils.findView(this, R.id.tv_email, TextView.class);
        mPasswordTextView = ActivityUtils.findView(this, R.id.tv_pass, TextView.class);
        Button loginButton = ActivityUtils.findView(this, R.id.btn_sign_in, Button.class);
        Button SignButton = ActivityUtils.findView(this, R.id.btn_register, Button.class);
        indicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        loginButton.setOnClickListener(this);
        SignButton.setOnClickListener(this);
        ActivityUtils.applyLightFont(emailEditText);
        ActivityUtils.applyLightFont(passwordEditText);
        ActivityUtils.applyBoldFont(mEmailTextView);
        ActivityUtils.applyBoldFont(mPasswordTextView);
        ActivityUtils.applyBoldFont(loginButton);
        ActivityUtils.applyBoldFont(SignButton);

    }

    @Override
    public void showProgress() {
//        indicatorView.setVisibility(View.VISIBLE);
//        indicatorView.show();

    }

    @Override
    public void dismissProgress() {
//        indicatorView.hide();

    }

    @Override
    public void showErrorDialog(int error) {
        DialogCreator.showOneButtonDialog(LoginActivity.this, error, "Error", null);

    }

    @Override
    public void openHomeActivity() {
        ActivityUtils.openActivity(LoginActivity.this, HomeActivity.class, true);
    }

    @Override
    public void openRegisterActivity() {
        ActivityUtils.openActivity(LoginActivity.this, RegisterActivity.class, false);

    }

    @Override
    public void openForgetPassword(String email) {

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        boolean isValid = true;
        if (viewId == R.id.btn_sign_in) {
            String email = ActivityUtils.getViewTextValue(emailEditText);
            String password = ActivityUtils.getViewTextValue(passwordEditText);
            boolean validPassword = Validator.validPasswordLength(password);
            boolean validEmail = Validator.validEmail(email);
            if (email.isEmpty()) {
                emailEditText.setError(getResources().getString(R.string.invalid_email));
                isValid = false;
            }
            if (!validEmail) {
                emailEditText.setError(getResources().getString(R.string.invalid_email_format));
                isValid = false;
            }
            if (password.isEmpty()) {
                passwordEditText.setError(getResources().getString(R.string.enter_your_password));
                isValid = false;
            }

            if (!validPassword) {
                passwordEditText.setError(getResources().getString(R.string.invalid_password));
                isValid = false;
            }
            if (!AppUtils.isInternetAvailable(LoginActivity.this)) {
                Snackbar snackbar1 = Snackbar.make(v, R.string.no_internet_connection, Snackbar.LENGTH_SHORT);
                snackbar1.setActionTextColor(Color.WHITE);
                snackbar1.show();
                return;
            }

            // login click.
            if (isValid)
                presenter.login(email, password, LoginActivity.this);
        } else if (viewId == R.id.btn_register) {
            openRegisterActivity();
        }

    }
}
