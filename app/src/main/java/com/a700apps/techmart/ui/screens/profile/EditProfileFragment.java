package com.a700apps.techmart.ui.screens.profile;

import android.app.Dialog;
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
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.MyProfile;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.model.User;
import com.a700apps.techmart.data.model.post;
import com.a700apps.techmart.data.network.ApiInterface;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationFragment;
import com.a700apps.techmart.ui.screens.register.RegisterActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.ApiClient;
import com.a700apps.techmart.utils.AppConst;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.Validator;
import com.a700apps.techmart.utils.loadingDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.linkedin.platform.LISessionManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment implements ProfileView, View.OnClickListener {
    ImageView mNotificationImageView, mProfileUserImageView, mAddImageView;//mProfileImageView
    ImageView imageView4;
    TextView mFriend, mFollowers, mPosts, mEmail;
    private ProfilePresenter presenter;
    //    public AVLoadingIndicatorView indicatorView;
    EditText mCompany, mPhone, mPosition, mLinkedin, mName;
    Button btn_edit, btn_save;
    String selectedImagePath;
    boolean isEnabled = false;
    Dialog dialogsLoading;

    String returnedImage = "/UploadedImages/";
    changeSideMenuData changeSideMenuData;


    private static final int PERMISSION_REQUEST_CODE = 786;
    private static final int Permission_storage_code = 787;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int SELECT_PICTURE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = new ProfilePresenter();
        presenter.attachView(this);

        init(view);

        imageView4 = (ImageView) view.findViewById(R.id.imageView4);
//        indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);

        presenter.profileData(PreferenceHelper.getUserId(getActivity()), getActivity());
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().onBackPressed();
                ((HomeActivity) getActivity()).openDrawer();
            }
        });

//        mProfileImageView = (ImageView) view.findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) view.findViewById(R.id.new_profile);


        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(getActivity(), NotificationActivity.class, false);
//                ((HomeActivity) getActivity()).openFragment(NotificationFragment.class, null);
            }
        });

        mProfileUserImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEnabled)
                    openChooseMethodDialog();
            }
        });

        mAddImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEnabled)
                    selectImage();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    void init(View view) {

        mProfileUserImageView = (ImageView) view.findViewById(R.id.iv_user);
        mAddImageView = (ImageView) view.findViewById(R.id.iv_add);
        changeSideMenuData = (changeSideMenuData) getActivity();
        mName = (EditText) view.findViewById(R.id.tv_name);
        mPosts = (TextView) view.findViewById(R.id.tv_post);
        mEmail = (TextView) view.findViewById(R.id.textView28);
        mFollowers = (TextView) view.findViewById(R.id.tv_followers);
        mFriend = (TextView) view.findViewById(R.id.tv_friend);
        mCompany = (EditText) view.findViewById(R.id.tv_company);
        mLinkedin = (EditText) view.findViewById(R.id.textView30);
        mPhone = (EditText) view.findViewById(R.id.tv_phone);
        mPosition = (EditText) view.findViewById(R.id.tv_position);
        btn_edit = (Button) view.findViewById(R.id.btn_edit);
        btn_save = (Button) view.findViewById(R.id.btn_save);

        btn_save.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
        mName.setEnabled(false);
        mCompany.setEnabled(false);
        mPosition.setEnabled(false);
        mPhone.setEnabled(false);
    }

    @Override
    public void showLoadingProgress() {
        dialogsLoading = new loadingDialog().showDialog(getActivity());
//        indicatorView.setVisibility(View.VISIBLE);
//        indicatorView.show();
    }

    @Override
    public void dismissLoadingProgress() {
//        indicatorView.hide();
        dialogsLoading.dismiss();
    }


    @Override
    public void updateUi(MyProfile MyProfile) {

        Glide.with(getActivity())
                .load(MainApi.IMAGE_IP + MyProfile.Photo)
                .placeholder(R.drawable.ic_profile)
                .listener(new LoggingListener<String, GlideDrawable>())
                .into(mProfileUserImageView);

        mName.setText(MyProfile.Name);
        mPosts.setText(String.valueOf(MyProfile.PostsCount));
        mFollowers.setText(String.valueOf(MyProfile.FollowersCount));
        mFriend.setText(String.valueOf(MyProfile.FriendsCount));
        mCompany.setText(MyProfile.Company);
        mPhone.setText(MyProfile.Phone);
        mPosition.setText(MyProfile.Position);
        mEmail.setText(MyProfile.Email);
        mLinkedin.setText(MyProfile.LinkedInProfile);
    }

    @Override
    public void updateUiFollow(post success) {

    }

    @Override
    public void updateUiUnFollow(post success) {

    }

    @Override
    public void updateUiConnect(post success) {

    }

    @Override
    public void updateUiApprove() {

    }

    @Override
    public void updateUiCancelApprove() {

    }

    @Override
    public void updateUiDisConnect(post success) {

    }

    @Override
    public void updateUiUpdate(post success) {
        User user = PreferenceHelper.getSavedUser(getActivity());
        user.Name = mName.getText().toString();
        if (!returnedImage.equals("/UploadedImages/")) {
            user.Photo = returnedImage;
        }
        PreferenceHelper.saveUser(getActivity(), user);
        Toast.makeText(getActivity(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
        changeSideMenuData.changeData();
    }

    private byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        LISessionManager.getInstance(getActivity()).onActivityResult(getActivity(),
                requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
//                Uri selectedImageUri = data.getData();
//                if (selectedImageUri == null) {
//                    Toast.makeText(getActivity(), "Sorry .. please select another image", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                selectedImagePath = getPathFromURI(getActivity(), selectedImageUri);
//                Glide.with(this).load(bitmapToByte(BitmapFactory.decodeFile(selectedImagePath))).
//                      asBitmap().into(mProfileUserImageView);
//
//                Log.e("path", "-->" + selectedImagePath);
//            }


            if (requestCode == SELECT_PICTURE) {
//                Uri selectedImageUri = data.getData();
//                if (selectedImageUri == null) {
//                    Toast.makeText(this, "Sorry .. please select another image", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                String scheme = selectedImageUri.getScheme();
//                selectedImagePath = getPathFromURI(getActivity(), selectedImageUri);
//
//                imageView = (ImageView) findViewById(R.id.iv_post);
//                imageView.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
//                imageView.setVisibility(View.VISIBLE);
//                Log.e("ImagePath", "-->" + selectedImagePath);

                // Get selected gallery image
//                Uri selectedPicture = data.getData();
//                if (selectedPicture == null) {
//                    Toast.makeText(getActivity(), "Sorry .. please select another image", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                selectedImagePath = getPathFromURI(getActivity(), selectedPicture);
//                // Get and resize profile image
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = getActivity().getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                selectedImagePath = cursor.getString(columnIndex);
//                cursor.close();
//
//                Bitmap loadedBitmap = BitmapFactory.decodeFile(selectedImagePath);
//
//                ExifInterface exif = null;
//                try {
//                    File pictureFile = new File(selectedImagePath);
//                    exif = new ExifInterface(pictureFile.getAbsolutePath());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                int orientation = ExifInterface.ORIENTATION_NORMAL;
//
//                if (exif != null)
//                    orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//
//                switch (orientation) {
//                    case ExifInterface.ORIENTATION_ROTATE_90:
//                        loadedBitmap = rotateBitmap(loadedBitmap, 90);
//                        break;
//                    case ExifInterface.ORIENTATION_ROTATE_180:
//                        loadedBitmap = rotateBitmap(loadedBitmap, 180);
//                        break;
//
//                    case ExifInterface.ORIENTATION_ROTATE_270:
//                        loadedBitmap = rotateBitmap(loadedBitmap, 270);
//                        break;
//                }
////                Glide.with(this).load(bitmapToByte(BitmapFactory.decodeFile(selectedImagePath)))
////                        .asBitmap().into(mProfileUserImageView);
//                Glide.with(this).load(loadedBitmap)
//                        .asBitmap().into(mProfileUserImageView);

                Uri selectedImageUri = data.getData();
                if (selectedImageUri == null) {
                    Toast.makeText(getActivity(), "Sorry .. please select another image", Toast.LENGTH_SHORT).show();
                    return;
                }
                selectedImagePath = getPathFromURI(getActivity(), selectedImageUri);
                Glide.with(this).load(bitmapToByte(BitmapFactory.decodeFile(selectedImagePath))).
                      asBitmap().into(mProfileUserImageView);

            } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");


                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getActivity(), imageBitmap);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                selectedImagePath = getRealPathFromURI(tempUri);
                Glide.with(this).load(bitmapToByte(BitmapFactory.decodeFile(selectedImagePath)))
                        .asBitmap().into(mProfileUserImageView);

            }

        }
    }


    private void uploadFile(String filePath) {
        // Map is used to multipart the file using okhttp3.RequestBody
        if (filePath != null && !filePath.equals("")) {
            dialogsLoading = new loadingDialog().showDialog(getActivity());
            File file = new File(filePath);

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
                    Log.e("Responce", serverResponse.postData.message);

                    if (serverResponse != null) {
                        if (serverResponse.postData.success) {
                            String mImagePath = serverResponse.postData.message;
//                            User user = PreferenceHelper.getSavedUser(getActivity());
//                            user.Photo = mImagePath;
//                            PreferenceHelper.saveUser(getActivity() , user);
                            dialogsLoading.dismiss();
                            returnedImage += serverResponse.postData.message;
                            presenter.updateProfileData(getActivity(), PreferenceHelper.getUserId(getActivity()),
                                    mName.getText().toString(), mLinkedin.getText().toString(), mImagePath, mCompany.getText().toString(),
                                    mPosition.getText().toString(), mPhone.getText().toString());

                        }
                    } else {
                        dialogsLoading.dismiss();
                        assert serverResponse != null;
                        Log.v("Response", serverResponse.toString());
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    dialogsLoading.dismiss();
                    Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_edit:
                isEnabled = true;
                mName.setEnabled(true);
                mCompany.setEnabled(true);
                mPhone.setEnabled(true);
                mPosition.setEnabled(true);
                mLinkedin.setEnabled(true);
//                mProfileImageView.setEnabled(true);
                mProfileUserImageView.setEnabled(true);

                mName.requestFocus();
                mName.setSelection(mName.getText().length());
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mName, InputMethodManager.SHOW_IMPLICIT);
                break;

            case R.id.btn_save:
                if (isEnabled) {
                    if (AppUtils.isInternetAvailable(getActivity())) {

                        if (selectedImagePath != null) {
                            if (validate(true)) {
                                uploadFile(selectedImagePath);
                            }
                        } else {
                            if (validate(false)) {
                                presenter.updateProfileData(getActivity(), PreferenceHelper.getUserId(getActivity()),
                                        mName.getText().toString(), mLinkedin.getText().toString(), "", mCompany.getText().toString(),
                                        mPosition.getText().toString(), mPhone.getText().toString());
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "choose to update first", Toast.LENGTH_SHORT).show();
                }

//                presenter.updateProfileData(PreferenceHelper.getUserId(getActivity()),
//                        mName.getText().toString(), mLinkedin.getText().toString(), "", mCompany.getText().toString(),
//                        mPosition.getText().toString(), mPhone.getText().toString());

                break;
        }
    }

    private boolean validate(boolean checkImage) {
        boolean isValid = true;

        boolean emptyName = Validator.isTextEmpty(mName.getText().toString().trim());
        if (emptyName) {
            mName.setError(getResources().getString(R.string.name_length_not_valid));
            isValid = false;
        }

        String mobile = mPhone.getText().toString().trim();
//        boolean validMobileNumber = Validator.validMobileNumberNew(mobile);
//        if (!validMobileNumber) {
//            mPhone.setError(getResources().getString(R.string.invalid_mobile_number));
//            isValid = false;
//        }


        if (mobile.startsWith("01") || mobile.startsWith("096") || mobile.startsWith("0097")) {
            boolean validMobileNumber = Validator.validMobileNumber(mobile);
            if (!validMobileNumber) {
                mPhone.setError(getResources().getString(R.string.invalid_mobile_number));
                isValid = false;
            }
        } else {
            mPhone.setError(getResources().getString(R.string.invalid_mobile_number));
            isValid = false;
        }

//        if (!validMobileNumber) {
//            mPhone.setError(getResources().getString(R.string.invalid_mobile_number));
//            isValid = false;
//        } else if (!mobile.startsWith("97")) {
//            mPhone.setError(getResources().getString(R.string.invalid_mobile_number_97));
//            isValid = false;
//        } else if (mobile.length() != 14) {
//            mPhone.setError(getResources().getString(R.string.invalid_mobile_number_size));
//            isValid = false;
//        }

        boolean validCompanyName = Validator.isTextEmpty(mCompany.getText().toString().trim());
        if (validCompanyName) {
            mCompany.setError(getResources().getString(R.string.company_length_not_valid));
            isValid = false;
        }

        if (mLinkedin.getText() != null && !mLinkedin.getText().toString().trim()
                .matches("((http(s?)://)*([a-zA-Z0-9\\-])*\\.|[linkedin])[linkedin/~\\-]+\\.[a-zA-Z0-9/~\\-_,&=\\?\\.;]+[^\\.,\\s<]")) {
            mLinkedin.setError(getResources().getString(R.string.invalid_url));
            isValid = false;
        }

        boolean validCompanyPosition = Validator.isTextEmpty(mPosition.getText().toString().trim());
        if (validCompanyPosition) {
            mPosition.setError(getResources().getString(R.string.position_length_not_valid));
            isValid = false;
        }

        if (checkImage) {
            if (selectedImagePath == null) {
                Toast.makeText(getActivity(), "Choose Image please", Toast.LENGTH_SHORT).show();
                isValid = false;
            }
        }

        return isValid;
    }

    public interface changeSideMenuData {
        void changeData();
    }


    public class LoggingListener<T, R> implements RequestListener<T, R> {
        @Override
        public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
            android.util.Log.d("GLIDE3", String.format(Locale.ROOT,
                    "onException(%s, %s, %s, %s)", e, model, target, isFirstResource), e);
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache,
                                       boolean isFirstResource) {
            android.util.Log.d("GLIDE4", String.format(Locale.ROOT,
                    "onResourceReady(%s, %s, %s, %s, %s)", resource, model, target, isFromMemoryCache, isFirstResource));
            return false;
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
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
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
        final Dialog dialog = new Dialog(getActivity());
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

    void selectImage() {
        if (AppConst.checkPermission(getActivity())) {
            selectedImagePath = null;
            // select a file
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE);
        } else {
            AppConst.requestPermission(getActivity(), PERMISSION_REQUEST_CODE);
        }
    }

    void captureImage() {
        if (checkPermission(android.Manifest.permission.CAMERA)) {
//            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(cameraIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            if (AppConst.checkPermission(getActivity())) {
                dispatchTakePictureIntent();
            } else {
                AppConst.requestPermission(getActivity(), Permission_storage_code);
            }

        } else {
            requestPermission(android.Manifest.permission.CAMERA);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
    }

    private boolean checkPermission(String permission) {//android.Manifest.permission.CAMERA
        int result = ContextCompat.checkSelfPermission(getActivity(), permission);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission(String permission) {//android.Manifest.permission.CAMERA
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
            Toast.makeText(getActivity(), "Camera permission allows us take images throught camera. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
    }
}

