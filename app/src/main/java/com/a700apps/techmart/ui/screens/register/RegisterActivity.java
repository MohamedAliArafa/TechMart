package com.a700apps.techmart.ui.screens.register;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.network.ApiInterface;
import com.a700apps.techmart.ui.screens.category.CategoryActivity;
import com.a700apps.techmart.ui.screens.creatpost.PostActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.ApiClient;
import com.a700apps.techmart.utils.AppConst;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.DialogCreator;
import com.a700apps.techmart.utils.Validator;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.data;


/**
 * Created by samir salah on 8/16/2017.
 */

public class RegisterActivity extends Activity implements RegisterView, View.OnClickListener {

    private static final int PERMISSION_REQUEST_CODE = 786;
    EditText mFullNameEditText, mPhoneNumberEditText, mEmailEditText, mPasswordEditText, mCompanyEditText, mPositionEditText;
    private static final int SELECT_PICTURE = 1;
    private long selectedImageSize;
    //    private String selectedImagePath;
    private RegisterPresenter presenter;
    public AVLoadingIndicatorView indicatorView;
    Button mLinkedInButton;
    private String selectedImagePath, mImagePath;
    ProgressDialog progressDialog;
    String fullName, password, email, mobile, company, position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        presenter = new RegisterPresenter();
        presenter.attachView(this);
//        ActivityUtils.hideKeyboard(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait Uploading Image...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void findViews() {
        Button SignButton = ActivityUtils.findView(this, R.id.bt_register, Button.class);
        Button attachButton = ActivityUtils.findView(this, R.id.bt_upload, Button.class);
        mFullNameEditText = ActivityUtils.findView(this, R.id.et_name, EditText.class);
        mPhoneNumberEditText = ActivityUtils.findView(this, R.id.et_mobile, EditText.class);
        mEmailEditText = ActivityUtils.findView(this, R.id.et_email, EditText.class);
        mPasswordEditText = ActivityUtils.findView(this, R.id.et_pass, EditText.class);
        mCompanyEditText = ActivityUtils.findView(this, R.id.et_company_name, EditText.class);
        mPositionEditText = ActivityUtils.findView(this, R.id.et_company_position, EditText.class);
        mLinkedInButton = ActivityUtils.findView(this, R.id.btn_register, Button.class);
        indicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        SignButton.setOnClickListener(this);
        attachButton.setOnClickListener(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                int dataSize = 0;
                File f = null;
                Uri selectedImageUri = data.getData();
                String scheme = selectedImageUri.getScheme();
                selectedImagePath = getPathFromURI(RegisterActivity.this, selectedImageUri);
                ImageView imageView = (ImageView) findViewById(R.id.iv_post);
                imageView.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
                imageView.setVisibility(View.VISIBLE);
                Log.e("ImagePath", selectedImagePath);

                if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                    try {
                        InputStream fileInputStream = getApplicationContext()
                                .getContentResolver().openInputStream(selectedImageUri);
                        dataSize = fileInputStream.available();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    selectedImageSize = dataSize;

                } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {
                    String path = selectedImagePath;
                    try {
                        f = new File(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    selectedImageSize = f.length();
                }
            }
        }
    }


    public static String getPathFromURI(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }


        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.bt_upload:
                if (Build.VERSION.SDK_INT >= 21) {
                    if (checkPermission()) {
                        selectedImagePath = null;
                        selectedImageSize = 0;
                        // select a file
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,
                                "Select Picture"), SELECT_PICTURE);

                    } else {
                        requestPermission();
                    }
                } else {
                    selectedImagePath = null;
                    selectedImageSize = 0;
                    // select a file
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,
                            "Select Picture"), SELECT_PICTURE);
                }

                break;
            case R.id.bt_register:
                fullName = ActivityUtils.getViewTextValue(mFullNameEditText);
                password = ActivityUtils.getViewTextValue(mPasswordEditText);
                email = ActivityUtils.getViewTextValue(mEmailEditText);
                mobile = ActivityUtils.getViewTextValue(mPhoneNumberEditText);
                company = ActivityUtils.getViewTextValue(mCompanyEditText);
                position = ActivityUtils.getViewTextValue(mPositionEditText);


                boolean emptyName = Validator.isTextEmpty(fullName);
                if (emptyName) {
                    mFullNameEditText.setError(getResources().getString(R.string.name_length_not_valid));
                    return;
                }

                boolean validMobileNumber = Validator.validMobileNumber(mobile.replaceFirst("\\+", ""));
                if (!validMobileNumber) {
                    mPhoneNumberEditText.setError(getResources().getString(R.string.invalid_mobile_number));
                    return;
                }

                boolean validEmail = Validator.validEmail(email);
                if (!validEmail) {
                    mEmailEditText.setError(getResources().getString(R.string.invalid_email));
                    return;
                }

                boolean validPassword = Validator.validPasswordLength(password);
                if (!validPassword) {
                    mPasswordEditText.setError(getResources().getString(R.string.invalid_password));
                    return;
                }

                boolean validCompanyName = Validator.isTextEmpty(company);
                if (validCompanyName) {
                    mCompanyEditText.setError(getResources().getString(R.string.company_length_not_valid));
                    return;
                }

                boolean validCompanyPosition = Validator.isTextEmpty(position);
                if (validCompanyPosition) {
                    mPositionEditText.setError(getResources().getString(R.string.position_length_not_valid));
                    return;
                }

                if (selectedImagePath == null) {
                    Snackbar snackbar1 = Snackbar.make(v, R.string.not_image, Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                    return;
                }
//


                if (!AppUtils.isInternetAvailable(RegisterActivity.this)) {
                    Snackbar snackbar1 = Snackbar.make(v, R.string.no_internet_connection, Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                } else {
                    uploadFile();

                }


                break;
            default:
                break;
        }
    }


    private final String[] okFileExtensions = new String[]{"jpg", "jpeg"};

    public boolean accept(String file) {
        for (String extension : okFileExtensions) {
            if (file.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void openCouncilActivity() {
        ActivityUtils.openActivity(RegisterActivity.this, CategoryActivity.class, false);
    }

    @Override
    public void showLoadingProgress() {
        indicatorView.setVisibility(View.VISIBLE);
        indicatorView.show();

    }

    @Override
    public void dismissLoadingProgress() {
        indicatorView.hide();

    }

    @Override
    public void showErrorDialog(int error) {
        DialogCreator.showOneButtonDialog(RegisterActivity.this, R.string.check_internet, error, null);

    }

    // Uploading Image/Video
    private void uploadFile() {
        progressDialog.show();

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(selectedImagePath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        ApiInterface getResponse = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = getResponse.uploadFile(fileToUpload, filename);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.postData.success) {
                        mImagePath = serverResponse.postData.message;
//                        presenter.sendPost(desired_string, PreferenceHelper.getUserId(PostActivity.this),
//                                    title.getText().toString(), Desc.getText().toString(), mImagePath, "", "", false, PostActivity.this);
//                            dialog.dismiss();
                        presenter.register(fullName, password, email, mobile, selectedImagePath, company, position, RegisterActivity.this);

                    }

                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, getString(R.string.check_internet), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(RegisterActivity.this, "Write External Storage permission allows us to access images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

}