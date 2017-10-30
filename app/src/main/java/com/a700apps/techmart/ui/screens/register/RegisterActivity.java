package com.a700apps.techmart.ui.screens.register;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.network.ApiInterface;
import com.a700apps.techmart.ui.screens.category.CategoryActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.ApiClient;
import com.a700apps.techmart.utils.AppConst;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.DialogCreator;
import com.a700apps.techmart.utils.LinkedinLogin;
import com.a700apps.techmart.utils.Social;
import com.a700apps.techmart.utils.Validator;
import com.a700apps.techmart.utils.loadingDialog;
import com.bumptech.glide.Glide;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiResponse;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by samir salah on 8/16/2017.
 */

public class RegisterActivity extends Activity implements RegisterView, View.OnClickListener {

    private static final int PERMISSION_REQUEST_CODE = 786;
    private static final int Permission_storage_code = 787;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int multy_permission_request = 101;
    private static final int SELECT_PICTURE = 1;

    boolean mIsLinkedIn;
    EditText mFullNameEditText, mPhoneNumberEditText, mEmailEditText, mPasswordEditText, mCompanyEditText, mPositionEditText;
    private long selectedImageSize;
    //    private String selectedImagePath;
    private RegisterPresenter presenter;
    public AVLoadingIndicatorView indicatorView;
    Button mLinkedInButton;
    private String selectedImagePath, mImagePath;
    ProgressDialog progressDialog;
    String fullName, password, email, mobile, company, position;
    Social mLinkedInModel = null;
    private int mRequestCode;
    private static final int SIGN_IN_CODE = 0;
    ImageView mLikedinImageView, mSignInImageView;

    Dialog dialogsLoading;
    Button SignButton;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        presenter = new RegisterPresenter();
        presenter.attachView(this);


//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please Wait Uploading Image...");
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void findViews() {
        SignButton = ActivityUtils.findView(this, R.id.bt_register, Button.class);
        Button attachButton = ActivityUtils.findView(this, R.id.bt_upload, Button.class);
        mFullNameEditText = ActivityUtils.findView(this, R.id.et_name, EditText.class);
        mPhoneNumberEditText = ActivityUtils.findView(this, R.id.et_mobile, EditText.class);
        mEmailEditText = ActivityUtils.findView(this, R.id.et_email, EditText.class);
        mPasswordEditText = ActivityUtils.findView(this, R.id.et_pass, EditText.class);
        mCompanyEditText = ActivityUtils.findView(this, R.id.et_company_name, EditText.class);
        mPositionEditText = ActivityUtils.findView(this, R.id.et_company_position, EditText.class);
        mLinkedInButton = ActivityUtils.findView(this, R.id.btn_register, Button.class);
        indicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        mLikedinImageView = (ImageView) findViewById(R.id.iv_linkedin);
        mSignInImageView = (ImageView) findViewById(R.id.iv_signin);
        SignButton.setOnClickListener(this);
        attachButton.setOnClickListener(this);
        mLinkedInButton.setOnClickListener(this);
        ActivityUtils.applyLightFont(mFullNameEditText);
        ActivityUtils.applyLightFont(mPhoneNumberEditText);
        ActivityUtils.applyLightFont(mEmailEditText);
        ActivityUtils.applyLightFont(mPasswordEditText);
        ActivityUtils.applyLightFont(mCompanyEditText);
        ActivityUtils.applyLightFont(mPositionEditText);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this,
                requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
//                Uri selectedImageUri = data.getData();
//                if (selectedImageUri == null) {
//                    Toast.makeText(this, "Sorry .. please select another image", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                String scheme = selectedImageUri.getScheme();
//                selectedImagePath = getPathFromURI(RegisterActivity.this, selectedImageUri);
//
//                imageView = (ImageView) findViewById(R.id.iv_post);
//                imageView.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
//                imageView.setVisibility(View.VISIBLE);
//                Log.e("ImagePath", "-->" + selectedImagePath);

                // Get selected gallery image
                Uri selectedPicture = data.getData();
                if (selectedPicture == null) {
                    Toast.makeText(this, "Sorry .. please select another image", Toast.LENGTH_SHORT).show();
                    return;
                }
                selectedImagePath = getPathFromURI(RegisterActivity.this, selectedPicture);
                // Get and resize profile image
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                selectedImagePath = cursor.getString(columnIndex);
                cursor.close();

                Log.e("Tag", "-->" + selectedImagePath);

                Bitmap loadedBitmap = BitmapFactory.decodeFile(selectedImagePath);

                ExifInterface exif = null;
                try {
                    File pictureFile = new File(selectedImagePath);
                    exif = new ExifInterface(pictureFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int orientation = ExifInterface.ORIENTATION_NORMAL;

                if (exif != null)
                    orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        loadedBitmap = rotateBitmap(loadedBitmap, 90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        loadedBitmap = rotateBitmap(loadedBitmap, 180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        loadedBitmap = rotateBitmap(loadedBitmap, 270);
                        break;
                }
                imageView = (ImageView) findViewById(R.id.iv_post);
                imageView.setImageBitmap(loadedBitmap);
                imageView.setVisibility(View.VISIBLE);


            } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ImageView imageView = (ImageView) findViewById(R.id.iv_post);

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                selectedImagePath = getRealPathFromURI(tempUri);

                ExifInterface ei = null;
                try {
                    ei = new ExifInterface(selectedImagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                Bitmap rotatedBitmap = null;
                switch(orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotatedBitmap = rotateBitmap(imageBitmap, 90);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotatedBitmap = rotateBitmap(imageBitmap, 180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotatedBitmap = rotateBitmap(imageBitmap, 270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotatedBitmap = imageBitmap;
                }

                imageView.setImageBitmap(rotatedBitmap);
                imageView.setVisibility(View.VISIBLE);
            }
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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


    private void openChooseMethodDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_choose_media_file);
        dialog.findViewById(R.id.tv_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private boolean checkStoragePermissions() {

        boolean havePermission = true;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            havePermission = false;

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, getString(R.string.storage_rationale), Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Permission_storage_code);
            }
        }
        return havePermission;
    }

    private boolean justCheckStoragePermissions() {

        boolean havePermission = true;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            havePermission = false;
        }
        return havePermission;
    }

    private boolean checkCameraPermissions() {
        boolean havePermission = true;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            havePermission = false;
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(this, getString(R.string.camera_rationale), Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        }
        return havePermission;
    }

    private boolean justCheckCameraPermissions() {
        boolean havePermission = true;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            havePermission = false;
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(this, getString(R.string.camera_rationale), Toast.LENGTH_SHORT).show();
            }
        }
        return havePermission;
    }

    private boolean checkMutlyPermissions() {
        List<String> permissionsNeeded = new ArrayList<String>();
        boolean havePermission = true;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.CAMERA);
            havePermission = false;
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            havePermission = false;
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                ) {
            Toast.makeText(this, getString(R.string.camera_rationale), Toast.LENGTH_SHORT).show();
        } else {
            if (permissionsNeeded.size() > 0)
                ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), multy_permission_request);
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, multy_permission_request);
        }

        return havePermission;
    }

    void selectImage() {
        if (checkStoragePermissions()) {
            selectedImagePath = null;
            selectedImageSize = 0;
            // select a file
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE);
        }
    }

    void captureImage() {
        if (checkMutlyPermissions()) {
            dispatchTakePictureIntent();
        }

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {

            case R.id.btn_register:
                mLikedinImageView.setVisibility(View.VISIBLE);
                mSignInImageView.setVisibility(View.GONE);
                loginWithLinkedin();
                break;
            case R.id.bt_upload:
//                if (Build.VERSION.SDK_INT >= 21) {
//                    if (checkPermission()) {

                openChooseMethodDialog();

//                        selectedImagePath = null;
//                        selectedImageSize = 0;
//                        // select a file
//                        Intent intent = new Intent();
//                        intent.setType("image/*");
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
//                        startActivityForResult(Intent.createChooser(intent,
//                                "Select Picture"), SELECT_PICTURE);

//                    } else {
//                        requestPermission();
//                    }


                break;
            case R.id.bt_register:
                fullName = ActivityUtils.getViewTextValue(mFullNameEditText);
                password = ActivityUtils.getViewTextValue(mPasswordEditText);
                email = ActivityUtils.getViewTextValue(mEmailEditText);
                mobile = ActivityUtils.getViewTextValue(mPhoneNumberEditText);
                company = ActivityUtils.getViewTextValue(mCompanyEditText);
                position = ActivityUtils.getViewTextValue(mPositionEditText);

                boolean isValid = true;

                boolean emptyName = Validator.isTextEmpty(fullName);
                if (emptyName) {
                    mFullNameEditText.setError(getResources().getString(R.string.name_length_not_valid));
                    isValid = false;
                }

//                boolean validMobileNumber = Validator.validMobileNumber(mobile.replaceFirst("\\+", ""));
//                if (!validMobileNumber) {
//                    mPhoneNumberEditText.setError(getResources().getString(R.string.invalid_mobile_number));
//                    isValid = false;
//                } else if (!mobile.startsWith("97")) {
//                    mPhoneNumberEditText.setError(getResources().getString(R.string.invalid_mobile_number_97));
//                    isValid = false;
//                }else if (mobile.length()!=14){
//                    mPhoneNumberEditText.setError(getResources().getString(R.string.invalid_mobile_number_size));
//                    isValid = false;
//                }
//

                if (mobile.startsWith("01") || mobile.startsWith("096") || mobile.startsWith("+97")) {
                    boolean validMobileNumber = Validator.validMobileNumber(mobile);
                    if (!validMobileNumber) {
                        mPhoneNumberEditText.setError(getResources().getString(R.string.invalid_mobile_number));
                        isValid = false;
                    }
                } else {
                    mPhoneNumberEditText.setError(getResources().getString(R.string.invalid_mobile_number));
                    isValid = false;
                }


                boolean validEmail = Validator.validEmail(email);
                if (!validEmail) {
                    mEmailEditText.setError(getResources().getString(R.string.invalid_email));
                    isValid = false;
                }

                boolean validPassword = Validator.validPasswordLength(password);
                if (!validPassword) {
                    mPasswordEditText.setError(getResources().getString(R.string.invalid_password));
                    isValid = false;
                }

                boolean validCompanyName = Validator.isTextEmpty(company);
                if (validCompanyName) {
                    mCompanyEditText.setError(getResources().getString(R.string.company_length_not_valid));
                    isValid = false;
                }

                boolean validCompanyPosition = Validator.isTextEmpty(position);
                if (validCompanyPosition) {
                    mPositionEditText.setError(getResources().getString(R.string.position_length_not_valid));
                    isValid = false;
                }

                if (selectedImagePath == null) {
                    Snackbar snackbar1 = Snackbar.make(v, R.string.not_image, Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                    isValid = false;
                }
//


                if (!AppUtils.isInternetAvailable(RegisterActivity.this)) {
                    Snackbar snackbar1 = Snackbar.make(v, R.string.no_internet_connection, Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                } else {
                    if (isValid) {
                        if (mIsLinkedIn) {
                            presenter.register(fullName, password, email, mobile, selectedImagePath, company, position, RegisterActivity.this);

                        } else {
                            uploadFile();
                        }
                    }
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
        ActivityUtils.openActivity(RegisterActivity.this, CategoryActivity.class, true);
    }

    @Override
    public void showLoadingProgress() {
//        indicatorView.setVisibility(View.VISIBLE);
//        indicatorView.show();

    }

    @Override
    public void dismissLoadingProgress() {
//        indicatorView.hide();

    }

    @Override
    public void showErrorDialog(int error) {
        DialogCreator.showOneButtonDialog(RegisterActivity.this, R.string.email_exist, "Error", null);
    }

    // Uploading Image/Video
    private void uploadFile() {
//        progressDialog.show();


        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(selectedImagePath);
        Log.e("size in kb ", " size in KB -> " + file.length() / 1024);

//        if (file.length() / 1024 > 1024) {
//            Snackbar snackbar1 = Snackbar.make(SignButton, R.string.image_size_exceed, Snackbar.LENGTH_SHORT);
//            snackbar1.show();
//            return;
//        }

        dialogsLoading = new loadingDialog().showDialog(RegisterActivity.this);
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

//                            dialog.dismiss();
                        presenter.register(fullName, password, email, mobile, mImagePath, company, position, RegisterActivity.this);

                    }

                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
//                progressDialog.dismiss();
                dialogsLoading.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                progressDialog.dismiss();
                dialogsLoading.dismiss();
                Toast.makeText(RegisterActivity.this, getString(R.string.check_internet), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean checkPermission(String permission) {//android.Manifest.permission.CAMERA
        int result = ContextCompat.checkSelfPermission(RegisterActivity.this, permission);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission(String permission) {//android.Manifest.permission.CAMERA
        if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, permission)) {
            Toast.makeText(RegisterActivity.this, "Camera permission allows us take images throught camera. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{permission}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
    }

    private void loginWithLinkedin() {
        LinkedinLogin.getInstance().loginUsingLinkedIn(RegisterActivity.this);
        LinkedinLogin.getInstance().setlistenr(new LinkedinLogin.LinkedInLoginListener() {

            @Override
            public void success(ApiResponse result) {
                if (mLinkedInModel == null)
                    mLinkedInModel = new Social();
                mLinkedInModel = LinkedinLogin.mLinkedInModel;
                Log.e("email", mLinkedInModel.email);
                Log.e("name", mLinkedInModel.name);
                Log.e("photo", mLinkedInModel.photo);
                Log.e("id", mLinkedInModel.id);
//                presenter.registerLinkedin(mLinkedInModel.name, mLinkedInModel.email,
//                        mLinkedInModel.id, mLinkedInModel.work, mLinkedInModel.work, mLinkedInModel.photo, RegisterActivity.this);
                mIsLinkedIn = true;
                mFullNameEditText.setText(mLinkedInModel.name);
                mEmailEditText.setText(mLinkedInModel.email);
                mCompanyEditText.setText(mLinkedInModel.work);
                mPositionEditText.setText(mLinkedInModel.work);
                selectedImagePath = mLinkedInModel.photo;
                imageView = (ImageView) findViewById(R.id.iv_post);
                Glide.with(RegisterActivity.this)
                        .load(selectedImagePath).placeholder(R.drawable.placeholder)
                        .into(imageView);
                imageView.setVisibility(View.VISIBLE);

            }

            @Override
            public void failure(LIApiError error) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    captureImage();
                }
                break;
            case Permission_storage_code:
                selectImage();
                break;
            case multy_permission_request:
                if (justCheckCameraPermissions() && justCheckStoragePermissions())
                    captureImage();
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}