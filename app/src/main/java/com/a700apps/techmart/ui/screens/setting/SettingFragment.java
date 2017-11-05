package com.a700apps.techmart.ui.screens.setting;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.dagger.Application.module.NetworkModule;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.model.User;
import com.a700apps.techmart.data.network.ApiInterface;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.creatEvent.CreatEventActivity;
import com.a700apps.techmart.ui.screens.creatpost.PostActivity;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.message.ChatActivity;
import com.a700apps.techmart.ui.screens.register.RegisterActivity;
import com.a700apps.techmart.utils.ApiClient;
import com.a700apps.techmart.utils.AppConst;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.ImageDetailsActivity;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.RoundedCornersTransformation;
import com.a700apps.techmart.utils.Validator;
import com.a700apps.techmart.utils.loadingDialog;
import com.bumptech.glide.Glide;
import com.suke.widget.SwitchButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by samir salah on 8/16/2017.
 */

public class SettingFragment extends Fragment implements SettingView, SwitchButton.OnCheckedChangeListener, View.OnClickListener {

    ImageView mBackImageView, mProfileImageView;
    SwitchButton changeRecieveNotification;
    SettingPresenter presenter;
    changeHomeImage changeHomeImage;

    String selectedImagePath;
    //    private final int SELECT_PICTURE_CHANGE = 19;
//    File selectedFile;
    EditText oldPasswordEditText, newPasswordEditText, repeatPasswordEditText;
    Button save, bt_cancel;
    TextView tv_change;
    Dialog dialogsLoading;
    private static final int multy_permission_request = 101;

    private static final int PERMISSION_REQUEST_CODE = 786;
    private static final int Permission_storage_code = 787;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int SELECT_PICTURE = 1;


    public static int sCorner = 12;
    public static int sMargin = 4;
    public static int sBorder =8;
    public static String sColor = "#ffffff";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mBackImageView = (ImageView) view.findViewById(R.id.iv_back);
        mProfileImageView = view.findViewById(R.id.iv_user_nav_image);
        tv_change = view.findViewById(R.id.tv_change);


        changeHomeImage  = (SettingFragment.changeHomeImage) getActivity();

        changeRecieveNotification = (SwitchButton) view.findViewById(R.id.switch_button);
        oldPasswordEditText = view.findViewById(R.id.edt_change_pass);
        newPasswordEditText = view.findViewById(R.id.edt_change_new_pass);
        repeatPasswordEditText = view.findViewById(R.id.edt_repeat_pass);
        save = view.findViewById(R.id.bt_save);
        bt_cancel = view.findViewById(R.id.bt_cancel);

        presenter = new SettingPresenter();
        presenter.attachView(this);
        Glide.with(getActivity())
                .load(MainApi.IMAGE_IP + PreferenceHelper.getSavedUser(getActivity()).Photo).placeholder(R.drawable.ic_profile)
                .bitmapTransform(new RoundedCornersTransformation(getActivity(), sCorner, sMargin, sColor, sBorder))
                .into(mProfileImageView);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).openDrawer();
            }
        });

        //set status treu or false depending on preference
        changeRecieveNotification.setChecked(PreferenceHelper.getNotificationStatus(getActivity()));

        changeRecieveNotification.setOnCheckedChangeListener(this);
        save.setOnClickListener(this);
        tv_change.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        mProfileImageView.setOnClickListener(this);
    }

    @Override
    public void showLoadingProgress() {
        dialogsLoading = new loadingDialog().showDialog(getActivity());
//        Toast.makeText(getActivity(), "show loading view", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dismissLoadingProgress() {
        dialogsLoading.dismiss();
//        Toast.makeText(getActivity(), "dismiss loading view", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorDialog(int error) {
//        Toast.makeText(getActivity(), "show error dialog", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeNotificationStatus(String userId, boolean enabled) {

        if (AppUtils.isInternetAvailable(getActivity())) {
//            Toast.makeText(getActivity(), "change to : " + enabled, Toast.LENGTH_SHORT).show();
            presenter.ChangeNotificationStatus(userId, enabled);
        } else {
            showErrorDialog(R.string.check_internet);
        }
    }

    @Override
    public void changeLoginPassword() {

        if (oldPasswordEditText.getText() == null || oldPasswordEditText.getText().toString().trim().equals("")) {
            oldPasswordEditText.setError("old password required");
            return;
        }
        if (newPasswordEditText.getText() == null || newPasswordEditText.getText().toString().trim().equals("")) {
            newPasswordEditText.setError("new password required");
            return;
        } else if (!Validator.validPasswordLength(newPasswordEditText.getText().toString().trim())) {
            newPasswordEditText.setError(getString(R.string.invalid_password));
            return;
        }

        if (repeatPasswordEditText.getText() == null || repeatPasswordEditText.getText().toString().trim().equals("")) {
            repeatPasswordEditText.setError("plaese repeat password");
            return;
        }

        if (!repeatPasswordEditText.getText().toString().trim().equals(newPasswordEditText.getText().toString().trim())) {
            repeatPasswordEditText.setError("Passwords don't match");
            return;
        }

        presenter.changePassword(PreferenceHelper.getUserId(getActivity()), oldPasswordEditText.getText().toString().trim()
                , newPasswordEditText.getText().toString().trim());

    }

    @Override
    public void ChangeProfilePhoto(String userId, String photo) {
//        presenter.ChangeProfilePicture(userId , photo);
    }

    @Override
    public void addNotificationStatus(boolean enabled) {
        PreferenceHelper.setNotificationStatus(getActivity(), enabled);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), " " + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveNewPic(String name) {
        User user = PreferenceHelper.getSavedUser(getActivity());
        user.Photo = name;
        PreferenceHelper.saveUser(getActivity(), user);

        changeHomeImage.changeImage(name);

    }

    @Override
    public void emptyViews() {
        oldPasswordEditText.setText("");
        newPasswordEditText.setText("");
        repeatPasswordEditText.setText("");
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.switch_button:
                String userId = PreferenceHelper.getUserId(getActivity());
                changeNotificationStatus(userId, isChecked);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_save:

                if (AppUtils.isInternetAvailable(getActivity())) {
                    changeLoginPassword();
                } else {
                    showToast(getString(R.string.check_internet));
                }

                break;

            case R.id.iv_user_nav_image:
                Intent intentDetails = new Intent(getActivity(), ImageDetailsActivity.class);
                intentDetails.putExtra("ImageUrl", MainApi.IMAGE_IP + PreferenceHelper.getSavedUser(getActivity()).Photo);
                startActivity(intentDetails);

                break;
            case R.id.bt_cancel:

                if (selectedImagePath != null && !selectedImagePath.equals("")) {
                    mProfileImageView.setImageBitmap(null);
                }

                oldPasswordEditText.setText("");
                repeatPasswordEditText.setText("");
                newPasswordEditText.setText("");
                break;

            case R.id.tv_change:
//                presenter.ChangeProfilePicture(PreferenceHelper.getUserId(getActivity()), selectedFile);
//                openSelectIntent();

                openChooseMethodDialog();

                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                int dataSize = 0;
                File f = null;
                Uri selectedImageUri = data.getData();
                if (selectedImageUri == null) {
                    Toast.makeText(getActivity(), "Sorry .. please select another image", Toast.LENGTH_SHORT).show();
                    return;
                }
                String scheme = selectedImageUri.getScheme();
                selectedImagePath = getPathFromURI(getActivity(), selectedImageUri);

                Log.e("ImagePath", "-->" + selectedImagePath);

                if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                    try {
                        InputStream fileInputStream = getActivity()
                                .getContentResolver().openInputStream(selectedImageUri);
                        dataSize = fileInputStream.available();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {
                    String path = selectedImagePath;
                }

                Glide.with(this).load(bitmapToByte(BitmapFactory.decodeFile(selectedImagePath))).asBitmap().into(mProfileImageView);
                if (AppUtils.isInternetAvailable(getActivity())) {
                    uploadFile(selectedImagePath);
                } else {
                    showToast(getString(R.string.check_internet));
                }

            } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getActivity(), imageBitmap);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                selectedImagePath = getRealPathFromURI(tempUri);
                Glide.with(this).load(bitmapToByte(BitmapFactory.decodeFile(selectedImagePath))).asBitmap().into(mProfileImageView);
                if (AppUtils.isInternetAvailable(getActivity())) {
                    uploadFile(selectedImagePath);
                } else {
                    showToast(getString(R.string.check_internet));
                }

            }
        }
    }


    private byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void uploadFile(String filePath) {
        // Map is used to multipart the file using okhttp3.RequestBody
        if (filePath != null && !filePath.equals("")) {
            showLoadingProgress();
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
                            dismissLoadingProgress();
                            String mImagePath = serverResponse.postData.message;
                            presenter.ChangeProfilePicture(PreferenceHelper.getUserId(getActivity()), mImagePath);
                        }
                    } else {
                        assert serverResponse != null;
                        Log.v("Response", serverResponse.toString());
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_LONG).show();
                }
            });
        }

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


    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkStoragePermissions() {

        boolean havePermission = true;

        if (getActivity().checkSelfPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            havePermission = false;

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(getActivity(), getString(R.string.storage_rationale), Toast.LENGTH_SHORT).show();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Permission_storage_code);
            }
        }
        return havePermission;
    }

    private boolean justCheckStoragePermissions() {

        boolean havePermission = true;

        if (getActivity().checkSelfPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            havePermission = false;
        }
        return havePermission;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkCameraPermissions() {
        boolean havePermission = true;
        if (getActivity().checkSelfPermission(
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            havePermission = false;
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {
                Toast.makeText(getActivity(), getString(R.string.camera_rationale), Toast.LENGTH_SHORT).show();
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        }
        return havePermission;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean justCheckCameraPermissions() {
        boolean havePermission = true;
        if (getActivity().checkSelfPermission(
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            havePermission = false;
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {
                Toast.makeText(getActivity(), getString(R.string.camera_rationale), Toast.LENGTH_SHORT).show();
            }
        }
        return havePermission;
    }

    private boolean checkMutlyPermissions() {
        List<String> permissionsNeeded = new ArrayList<String>();
        boolean havePermission = true;
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.CAMERA);
            havePermission = false;
        }

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            havePermission = false;
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA) ||
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                ) {
            Toast.makeText(getActivity(), getString(R.string.camera_rationale), Toast.LENGTH_SHORT).show();
        } else {
            if (permissionsNeeded.size() > 0)
                requestPermissions(permissionsNeeded.toArray(new String[permissionsNeeded.size()]), multy_permission_request);
        }

        return havePermission;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void selectImage() {
        if (checkStoragePermissions()) {
            selectedImagePath = null;
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
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
    }



    //    @Override
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
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public interface changeHomeImage{
        void changeImage(String path);
    }
}
