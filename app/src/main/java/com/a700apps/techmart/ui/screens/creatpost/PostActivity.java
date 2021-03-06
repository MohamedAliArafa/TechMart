package com.a700apps.techmart.ui.screens.creatpost;

import android.Manifest;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.model.post;
import com.a700apps.techmart.data.network.ApiInterface;
import com.a700apps.techmart.ui.screens.creatEvent.CreatEventActivity;
import com.a700apps.techmart.ui.screens.register.RegisterActivity;
import com.a700apps.techmart.utils.ApiClient;
import com.a700apps.techmart.utils.AppConst;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.CustomeEditText;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;
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
 * Created by samir salah on 9/13/2017.
 */

public class PostActivity extends AppCompatActivity implements PostView {
    private PostPresenter presenter;
    CustomeEditText editTextTitle, editTextDesc;
    LinearLayout linearLayout_post, linearLayout_select;
    private static final int SELECT_PICTURE = 1;
    private long selectedImageSize;
    private String selectedImagePath, mImagePath;
    Dialog dialog;
    int desired_string;
    File file;
    private static final int multy_permission_request = 101;

    public AVLoadingIndicatorView indicatorView;
    private static final int PERMISSION_REQUEST_CODE = 786;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    ImageView imageView;
    private static final int Permission_storage_code = 787;
    ProgressDialog progressDialog;

    Dialog dialogsLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        presenter = new PostPresenter();
        presenter.attachView(this);
        desired_string = getIntent().getIntExtra("string_key", 0);
        editTextTitle = (CustomeEditText) findViewById(R.id.editText2);
        editTextDesc = (CustomeEditText) findViewById(R.id.editText4);
        linearLayout_post = (LinearLayout) findViewById(R.id.linearLayout_post);
        linearLayout_select = (LinearLayout) findViewById(R.id.linearLayout);
        indicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//
        linearLayout_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(Build.VERSION.SDK_INT >=23){
                openChooseMethodDialog();


            }
        });
        linearLayout_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate(editTextTitle, editTextDesc)){
                    openDialog(editTextTitle, editTextDesc);
                }
            }
        });

    }

    private boolean validate(EditText title , EditText Desc){
        boolean isValid = true;
        if (title.getText().toString().isEmpty()) {
            title.setError(getString(R.string.select_title));
            isValid = false;
        }

        if (Desc.getText().toString().isEmpty()) {
            Desc.setError(getString(R.string.enter_description));
            isValid = false;
        }


        if (selectedImagePath == null) {
            Toast.makeText(PostActivity.this, R.string.not_image, Toast.LENGTH_LONG).show();
            isValid = false;
        }

        return isValid;
    }
    

    void openDialog(final EditText title, final EditText Desc) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.my_dialog);
        dialog.show();

        LinearLayout lin_close = (LinearLayout) dialog.findViewById(R.id.lin_close);
        TextView tv_public = (TextView) dialog.findViewById(R.id.tv_public);
        TextView tv_group = (TextView) dialog.findViewById(R.id.tv_group);

        lin_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (title.getText().toString().isEmpty()) {
                    dialog.dismiss();
                    Toast.makeText(PostActivity.this, R.string.select_title, Toast.LENGTH_LONG).show();

                    return;
                }

                if (Desc.getText().toString().isEmpty()) {
                    dialog.dismiss();
                    Toast.makeText(PostActivity.this, R.string.select_title, Toast.LENGTH_LONG).show();
                    return;
                }


                if (selectedImagePath == null) {
                    dialog.dismiss();
                    Toast.makeText(PostActivity.this, R.string.not_image, Toast.LENGTH_LONG).show();
                    return;
                }

                if (!AppUtils.isInternetAvailable(PostActivity.this)) {
                    Snackbar snackbar1 = Snackbar.make(v, R.string.no_internet_connection, Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                } else {
                    uploadFile(title, Desc, true);
                }


            }
        });
        tv_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (title.getText().toString().isEmpty()) {
                    dialog.dismiss();
                    Toast.makeText(PostActivity.this, R.string.select_title, Toast.LENGTH_LONG).show();

                    return;
                }

                if (Desc.getText().toString().isEmpty()) {
                    dialog.dismiss();
                    Toast.makeText(PostActivity.this, R.string.select_Post, Toast.LENGTH_LONG).show();
                    return;
                }
                if (selectedImagePath == null) {
                    dialog.dismiss();
                    Toast.makeText(PostActivity.this, R.string.not_image, Toast.LENGTH_LONG).show();
                    return;
                }
                if (!AppUtils.isInternetAvailable(PostActivity.this)) {
                    Snackbar snackbar1 = Snackbar.make(v, R.string.no_internet_connection, Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                } else {
                    uploadFile(title, Desc, false);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void showLoadingProgress() {
//        indicatorView.setVisibility(View.VISIBLE);
//        indicatorView.show();
        dialogsLoading = new loadingDialog().showDialog(this);
    }

    @Override
    public void dismissLoadingProgress() {
        dialogsLoading.dismiss();
//        indicatorView.hide();
    }

    @Override
    public void UpdateUi(post post) {
        Toast.makeText(PostActivity.this, getString(R.string.add_post), Toast.LENGTH_LONG).show();
        dialog.hide();
        finish();
        Globals.mIndex = 1;

    }

    //
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
//                int dataSize = 0;
//                file = null;
//                Uri selectedImageUri = data.getData();
//                String scheme = selectedImageUri.getScheme();
//                selectedImagePath = getPathFromURI(PostActivity.this, selectedImageUri);
//                imageView = (ImageView) findViewById(R.id.iv_post);
//                imageView.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
//                imageView.setVisibility(View.VISIBLE);
//                if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
//                    try {
//                        InputStream fileInputStream = getApplicationContext()
//                                .getContentResolver().openInputStream(selectedImageUri);
//                        dataSize = fileInputStream.available();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    selectedImageSize = dataSize;
//
//                } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {
//                    String path = selectedImagePath;
//                    Log.e("PATH", path);
//                    try {
//                        file = new File(path);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    selectedImageSize = file.length();
//                }

                Uri selectedPicture = data.getData();
                if (selectedPicture == null) {
                    Toast.makeText(this, "Sorry .. please select another image", Toast.LENGTH_SHORT).show();
                    return;
                }
                selectedImagePath = getPathFromURI(PostActivity.this, selectedPicture);
                // Get and resize profile image
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
//                cursor.moveToFirst();

//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                selectedImagePath = cursor.getString(columnIndex);
//                cursor.close();

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
//                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView = (ImageView) findViewById(R.id.iv_post);
//                selectedImagePath = getPathFromURI(PostActivity.this,data.getData());


                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                selectedImagePath = getRealPathFromURI(tempUri);

                imageView.setImageBitmap(imageBitmap);
                imageView.setVisibility(View.VISIBLE);
            }
        }
    }
    
    

    // Uploading Image/Video
    private void uploadFile(final EditText title, final EditText Desc, final boolean check) {
//        progressDialog.show();
        dialogsLoading = new loadingDialog().showDialog(this);
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
                        if (check) {
                            presenter.sendPost(desired_string, PreferenceHelper.getUserId(PostActivity.this),
                                    title.getText().toString(), Desc.getText().toString(), mImagePath, "", "", true, PostActivity.this);

                        } else {
                            presenter.sendPost(desired_string, PreferenceHelper.getUserId(PostActivity.this),
                                    title.getText().toString(), Desc.getText().toString(), mImagePath, "", "", false, PostActivity.this);
                        }


                    }

                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
//                progressDialog.dismiss();
//                dialogsLoading.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                progressDialog.dismiss();
                dialogsLoading.dismiss();
                Toast.makeText(PostActivity.this, getString(R.string.check_internet), Toast.LENGTH_LONG).show();
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




    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
