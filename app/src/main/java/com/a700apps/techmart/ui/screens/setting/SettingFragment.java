package com.a700apps.techmart.ui.screens.setting;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.a700apps.techmart.utils.ApiClient;
import com.a700apps.techmart.utils.AppConst;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.Validator;
import com.bumptech.glide.Glide;
import com.suke.widget.SwitchButton;

import java.io.File;

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

    String selectedImagePath;
    private final int SELECT_PICTURE_CHANGE = 19;
    private final int PERMISSION_REQUEST_CODE = 10;
    File selectedFile;
    EditText oldPasswordEditText, newPasswordEditText, repeatPasswordEditText;
    Button save;
    TextView tv_change;

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

        changeRecieveNotification = (SwitchButton) view.findViewById(R.id.switch_button);
        oldPasswordEditText = view.findViewById(R.id.edt_change_pass);
        newPasswordEditText = view.findViewById(R.id.edt_change_new_pass);
        repeatPasswordEditText = view.findViewById(R.id.edt_repeat_pass);
        save = view.findViewById(R.id.bt_save);

        presenter = new SettingPresenter();
        presenter.attachView(this);
        Glide.with(getActivity())
                .load(MainApi.IMAGE_IP + PreferenceHelper.getSavedUser(getActivity()).Photo).placeholder(R.drawable.ic_profile)
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
        mProfileImageView.setOnClickListener(this);
    }

    @Override
    public void showLoadingProgress() {
//        Toast.makeText(getActivity(), "show loading view", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dismissLoadingProgress() {
//        Toast.makeText(getActivity(), "dismiss loading view", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorDialog(int error) {
//        Toast.makeText(getActivity(), "show error dialog", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeNotificationStatus(String userId, boolean enabled) {

        if (AppUtils.isInternetAvailable(getActivity())) {
            Toast.makeText(getActivity(), "change to : " + enabled, Toast.LENGTH_SHORT).show();
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
        }else if (!Validator.validPasswordLength(newPasswordEditText.getText().toString().trim())){
            newPasswordEditText.setError(getString(R.string.invalid_password));
            return;
        }

        if (repeatPasswordEditText.getText() == null || repeatPasswordEditText.getText().toString().trim().equals("")) {
            repeatPasswordEditText.setError("plaese repeat password");
            return;
        }

        if (!repeatPasswordEditText.getText().toString().trim().equals(newPasswordEditText.getText().toString().trim())) {
            repeatPasswordEditText.setError("password don't match");
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
        Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveNewPic(String name) {
        User user = PreferenceHelper.getSavedUser(getActivity());
        user.Photo = name;
        PreferenceHelper.saveUser(getActivity() , user);
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
                changeLoginPassword();
                break;

            case R.id.iv_user_nav_image:
                openSelectIntent();
                break;

            case R.id.tv_change:
//                presenter.ChangeProfilePicture(PreferenceHelper.getUserId(getActivity()), selectedFile);

                uploadFile(selectedImagePath);
                break;
        }
    }


    void openSelectIntent() {
        if (Build.VERSION.SDK_INT >= 21) {
            if (AppConst.checkPermission(getActivity())) {
                selectedImagePath = null;
                // select a file
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE_CHANGE);

            } else {
                AppConst.requestPermission(getActivity(), PERMISSION_REQUEST_CODE);
            }
        } else {
            selectedImagePath = null;
            // select a file
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE_CHANGE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE_CHANGE) {

                Uri selectedImageUri = data.getData();
                selectedImagePath = getPathFromURI(getActivity(), selectedImageUri);
                mProfileImageView.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
                try {
                    selectedFile = new File(selectedImagePath);
                } catch (Exception e) {
                    e.printStackTrace();
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

    private void uploadFile(String filePath) {
        // Map is used to multipart the file using okhttp3.RequestBody
        if (filePath!=null && !filePath.equals("")){
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

}
