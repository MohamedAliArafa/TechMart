package com.a700apps.techmart.ui.screens.BoardMember.EditTimeLine;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.model.InnerModle;
import com.a700apps.techmart.data.model.NoficationData;
import com.a700apps.techmart.data.model.NotificationDataLike;
import com.a700apps.techmart.data.model.PostData;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.model.post;
import com.a700apps.techmart.data.network.ApiInterface;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.creatEvent.CreatEventActivity;
import com.a700apps.techmart.ui.screens.register.RegisterActivity;
import com.a700apps.techmart.utils.ApiClient;
import com.a700apps.techmart.utils.AppConst;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.MapDialogActivity;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.URLS;
import com.a700apps.techmart.utils.Validator;
import com.a700apps.techmart.utils.loadingDialog;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.bumptech.glide.Glide;
import com.linkedin.platform.LISessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTimeLineActivity extends AppCompatActivity implements EditView, View.OnClickListener
        , DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    EdittPresenter presenter;
    String mStartDate, mEndDate, mStartTime, mEndTime;
    EditText headerEditText;
    EditText descriptionEditText;
    LinearLayout saveLinearLayout;
    LinearLayout uploadLinearLayout;
//    LinearLayout removeLinearLayout;

    LinearLayout postLayout, eventLayout;

    ImageView selectedImageView;

    InnerModle innerModle;

    Dialog dialogsLoading;
    private static final int PERMISSION_REQUEST_CODE = 786;
    private static final int Permission_storage_code = 787;
    private static final int SELECT_PICTURE = 10;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private String selectedImagePath;
    int postId, type;
    String imageName;
    NotificationDataLike.Result result;
    ImageView mLocationImageView, mDateImageView;
    TextView tv_location, tv_date;
    private static final int PICK_LOCATION_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_time_line);

        headerEditText = findViewById(R.id.et_editHeader);
        descriptionEditText = findViewById(R.id.et_editDescription);

        saveLinearLayout = findViewById(R.id.save_layout);
        uploadLinearLayout = findViewById(R.id.upload_media);
//        removeLinearLayout = findViewById(R.id.remove_media);
        selectedImageView = findViewById(R.id.iv_selectedImageForEdit);
        innerModle = new InnerModle();

        postLayout = findViewById(R.id.bottomBar);
        eventLayout = findViewById(R.id.ll_container);

        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_date = (TextView) findViewById(R.id.tv_date);
        mLocationImageView = (ImageView) findViewById(R.id.iv_location);
        mDateImageView = (ImageView) findViewById(R.id.iv_date);

        saveLinearLayout.setOnClickListener(this);
        uploadLinearLayout.setOnClickListener(this);
//        removeLinearLayout .setOnClickListener(this);
        selectedImageView.setOnClickListener(this);
        presenter = new EdittPresenter();
        presenter.attachView(this);

//        postId= 108;
//        type= 2;
        postId = getIntent().getIntExtra("postId", 0);
        type = getIntent().getIntExtra("type", 0);

        if (type == 1) {
            eventLayout.setVisibility(View.VISIBLE);
            postLayout.setVisibility(View.INVISIBLE);
        } else {
            eventLayout.setVisibility(View.INVISIBLE);
            postLayout.setVisibility(View.VISIBLE);
        }
        presenter.getTimelineItem(postId, type, PreferenceHelper.getUserId(this));

        tv_date.setOnClickListener(this);
        mLocationImageView.setOnClickListener(this);
        mDateImageView.setOnClickListener(this);
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tv_location.setEnabled(false);
                if (ActivityCompat.checkSelfPermission(EditTimeLineActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(EditTimeLineActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EditTimeLineActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PICK_LOCATION_REQUEST);
                    return;
                } else {
                    // Write you code here if permission already given.
                    Intent intent1 = new Intent(EditTimeLineActivity.this, MapDialogActivity.class);
                    Globals.CAMETOEDIT = true;
                    Globals.lat = result.getLatitude() == null ? 0 : result.getLatitude();
                    Globals.lng = result.getLongtude() == null ? 0 : result.getLongtude();
                    Globals.placename = result.getLocationName() == null ? " " : result.getLocationName().toString();
//                    intent1.putExtra("lat" , result.getLatitude() == null ? 0:result.getLatitude());
//                    intent1.putExtra("lng" , result.getLongtude() == null ? 0:result.getLongtude());
//                    intent1.putExtra("place" , result.getLocationName() == null ? " ":result.getLocationName().toString());
                    startActivityForResult(intent1, PICK_LOCATION_REQUEST);
                }
            }
        });
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.close1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void showLoadingProgress() {
        dialogsLoading = new loadingDialog().showDialog(this);
    }

    @Override
    public void dismissLoadingProgress() {
        dialogsLoading.dismiss();
    }

    @Override
    public void UpdateUi(NotificationDataLike postData) {
        result = postData.getResult();

        headerEditText.setText(result.getTitle());
        descriptionEditText.setText(result.getDescr());
        selectedImageView.setVisibility(View.VISIBLE);
        imageName = result.getImage().replace("/UploadedImages/", "");
        Glide.with(this).load(MainApi.IMAGE_IP + result.getImage()).into(selectedImageView);//.placeholder(R.drawable.ratring_pic)

        dismissLoadingProgress();
    }

    @Override
    public void onImageSelected() {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("data", postId);
        setResult(RESULT_OK, returnIntent);
        finish();
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


    void selectImage() {
        if (AppConst.checkPermission(EditTimeLineActivity.this)) {
            selectedImagePath = null;
            // select a file
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE);
        } else {
            AppConst.requestPermission(EditTimeLineActivity.this, PERMISSION_REQUEST_CODE);
        }
    }

    void captureImage() {
        if (checkPermission(android.Manifest.permission.CAMERA)) {
//            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(cameraIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            if (AppConst.checkPermission(EditTimeLineActivity.this)) {
                dispatchTakePictureIntent();
            } else {
                AppConst.requestPermission(EditTimeLineActivity.this, Permission_storage_code);
            }

        } else {
            requestPermission(android.Manifest.permission.CAMERA);
        }
    }

    private boolean checkPermission(String permission) {//android.Manifest.permission.CAMERA
        int result = ContextCompat.checkSelfPermission(EditTimeLineActivity.this, permission);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission(String permission) {//android.Manifest.permission.CAMERA
        if (ActivityCompat.shouldShowRequestPermissionRationale(EditTimeLineActivity.this, permission)) {
            Toast.makeText(EditTimeLineActivity.this, "Camera permission allows us take images throught camera. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(EditTimeLineActivity.this, new String[]{permission}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
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

    private byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this,
                requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageUri = data.getData();
                if (selectedImageUri == null) {
                    Toast.makeText(this, "Sorry .. please select another image", Toast.LENGTH_SHORT).show();
                    return;
                }
                selectedImagePath = getPathFromURI(EditTimeLineActivity.this, selectedImageUri);
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
//                if (bitmap==null){
//                    Log.e("asdad","adasda");
//                }else {
//                    selectedImageView.setImageBitmap(bitmap);
//                }
                Glide.with(this).load(bitmapToByte(bitmap)).asBitmap().into(selectedImageView);
                Log.e("ImagePath111", "-->" + selectedImagePath);


            } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                selectedImagePath = getPathFromURI(RegisterActivity.this,data.getData());


                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                selectedImagePath = getRealPathFromURI(tempUri);

                selectedImageView.setImageBitmap(imageBitmap);


            } else if (requestCode == PICK_LOCATION_REQUEST) {
                if (data.hasExtra(URLS.EXTRA_PARCELABLE)) {
                    innerModle = data.getParcelableExtra(URLS.EXTRA_PARCELABLE);
//                    userAddressEditText.setText(getCompleteAddressString(innerModle.getLongitude(), innerModle.getLatitude()));
                    Log.d("Latitude", innerModle.getLatitude() + "");
                    Log.d("Longitude", innerModle.getLongitude() + "");
                }
            }
        }
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

        dialogsLoading = new loadingDialog().showDialog(EditTimeLineActivity.this);
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
                        String mImagePath = serverResponse.postData.message;

                        dialogsLoading.dismiss();

                        if (result.getType() == 1) {

                            double lat, lng;
                            String placeName;
                            if (innerModle == null) {
                                lat = result.getLatitude();
                                lng = result.getLongtude();
                                placeName = result.getLocationName().toString();
                            } else {
                                lat = innerModle.getLatitude();
                                lng = innerModle.getLongitude();
                                placeName = getCompleteAddressString(lat, lng);
                            }

                            String localStartTime = mStartTime.equals("")||mStartTime==null ? result.getStartTime() : mStartTime ;
                            String localEndTime = mEndTime.equals("")||mEndTime==null ? result.getEndTime() : mEndTime ;
                            String localStartDate = mStartDate.equals("")||mStartDate==null ? result.getStartDate() : mStartDate ;
                            String localEndDate = mEndDate.equals("")||mEndDate==null ? result.getEndDate() : mEndDate ;

                            presenter.editTimeLineItemEvent("" + result.getGroupID(), result.getID(), result.getIsPublic(),
                                    headerEditText.getText().toString().trim(), descriptionEditText.getText().toString().trim(),
                                    mImagePath, "", PreferenceHelper.getUserId(EditTimeLineActivity.this), result.getType(), lat, lng, localStartDate, localEndDate, localStartTime, localEndTime,
                                    placeName);
                        } else {
                            presenter.editTimeLineItem("" + result.getGroupID(), postId, result.getIsPublic(),
                                    headerEditText.getText().toString(),
                                    descriptionEditText.getText().toString(), mImagePath, "",
                                    PreferenceHelper.getUserId(EditTimeLineActivity.this), result.getType());
                        }
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                dialogsLoading.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                progressDialog.dismiss();
                dialogsLoading.dismiss();
                Toast.makeText(EditTimeLineActivity.this, getString(R.string.check_internet), Toast.LENGTH_LONG).show();
            }
        });
    }


    private boolean validate() {

        boolean isValid = true;
        if (Validator.isEditTextEmpty(headerEditText)) {
            headerEditText.setError("title can not be empty");
            isValid = false;
        }


        if (Validator.isEditTextEmpty(descriptionEditText)) {
            descriptionEditText.setError("description can not be empty");
            isValid = false;
        }

        return isValid;

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();


        switch (id) {
            case R.id.upload_media:
                openChooseMethodDialog();
                break;
            case R.id.iv_selectedImageForEdit:
                openChooseMethodDialog();
                break;

            case R.id.iv_location:
                if (ActivityCompat.checkSelfPermission(EditTimeLineActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(EditTimeLineActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EditTimeLineActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PICK_LOCATION_REQUEST);
                    return;
                } else {
                    // Write you code here if permission already given.
                    Intent intent1 = new Intent(EditTimeLineActivity.this, MapDialogActivity.class);
                    Globals.CAMETOEDIT = true;
                    Globals.lat = result.getLatitude() == null ? 0 : result.getLatitude();
                    Globals.lng = result.getLongtude() == null ? 0 : result.getLongtude();
                    Globals.placename = result.getLocationName() == null ? " " : result.getLocationName().toString();
//                    intent1.putExtra("lat" , result.getLatitude() == null ? 0:result.getLatitude());
//                    intent1.putExtra("lng" , result.getLongtude() == null ? 0:result.getLongtude());
//                    intent1.putExtra("place" , result.getLocationName() == null ? " ":result.getLocationName().toString());
                    startActivityForResult(intent1, PICK_LOCATION_REQUEST);
                }
                break;
            case R.id.tv_date:
                mStartDate = result.getStartDate().substring(0, result.getStartDate().indexOf("T"));
                mStartTime = result.getStartDate().substring(result.getStartDate().indexOf("T") + 1, result.getStartDate().length());

                mEndDate = result.getEndDate().substring(0, result.getEndDate().indexOf("T"));
                mEndTime = result.getEndDate().substring(result.getEndDate().indexOf("T") + 1, result.getEndDate().length());

                Log.e("start date", mStartDate);
                Log.e("start date", mStartTime);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null, dateEnd = null;
                try {
                    date = sdf.parse(mStartDate);
                    dateEnd = sdf.parse(mEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                Calendar calEnd = Calendar.getInstance();
                cal.setTime(date);
                calEnd.setTime(dateEnd);

                DatePickerDialog dpd2 = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        EditTimeLineActivity.this,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH),
                        calEnd.get(Calendar.YEAR),
                        calEnd.get(Calendar.MONTH),
                        calEnd.get(Calendar.DAY_OF_MONTH)
                );
                break;
            case R.id.iv_date:

                mStartDate = result.getStartDate().substring(0, result.getStartDate().indexOf("T"));
                mStartTime = result.getStartTime();

                mEndDate = result.getEndDate().substring(0, result.getEndDate().indexOf("T"));
                mEndTime = result.getEndTime();

                Log.e("start date", mStartDate);
                Log.e("start date", mStartTime);

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = null, dateEnd1 = null;
                try {
                    date1 = sdf1.parse(mStartDate);
                    dateEnd1 = sdf1.parse(mEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal1 = Calendar.getInstance();
                Calendar calEnd1 = Calendar.getInstance();
                cal1.setTime(date1);
                calEnd1.setTime(dateEnd1);
                DatePickerDialog dpd21 = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        EditTimeLineActivity.this,
                        cal1.get(Calendar.YEAR),
                        cal1.get(Calendar.MONTH),
                        cal1.get(Calendar.DAY_OF_MONTH),
                        calEnd1.get(Calendar.YEAR),
                        calEnd1.get(Calendar.MONTH),
                        calEnd1.get(Calendar.DAY_OF_MONTH)
                );

                dpd21.setAutoHighlight(true);
                dpd21.isThemeDark();
                dpd21.setAccentColor(getResources().getColor(R.color.blackGreenColor));

                dpd21.show(getFragmentManager(), "Datepickerdialog");
                break;
//            case R.id.remove_media:
//                selectedImagePath = null;
//                selectedImageView.setImageBitmap(null);
//                break;
            case R.id.save_layout:
                Log.e("boolean", validate() + "");


                if (validate()) {
                    if (selectedImagePath == null) {
                        if (type == 2) {
                            presenter.editTimeLineItem("" + result.getGroupID(), postId, result.getIsPublic(),
                                    headerEditText.getText().toString().trim(),
                                    descriptionEditText.getText().toString().trim(), imageName, "",
                                    PreferenceHelper.getUserId(this), result.getType());
                        } else if (type == 1) {

                            String image = result.getImage().replace("/UploadedImages/", "");
                            double lat, lng;
                            String placeName ;
                            if (innerModle.getLongitude() == null) {
                                lat = result.getLatitude();
                                lng = result.getLongtude();
                                placeName = result.getLocationName().toString();
                            } else {
                                lat = innerModle.getLatitude();
                                lng = innerModle.getLongitude();
                                placeName = getCompleteAddressString(lat, lng);
                            }

                            String localStartTime = mStartTime.equals("")||mStartTime==null ? result.getStartTime() : mStartTime ;
                            String localEndTime = mEndTime.equals("")||mEndTime==null ? result.getEndTime() : mEndTime ;
                            String localStartDate = mStartDate.equals("")||mStartDate==null ? result.getStartDate() : mStartDate ;
                            String localEndDate = mEndDate.equals("")||mEndDate==null ? result.getEndDate() : mEndDate ;




                            presenter.editTimeLineItemEvent("" + result.getGroupID(), result.getID(), result.getIsPublic(),
                                    headerEditText.getText().toString().trim(), descriptionEditText.getText().toString().trim(),
                                    image, "", PreferenceHelper.getUserId(this), result.getType(), lat, lng, localStartDate, localEndDate, localStartTime, localEndTime,
                                    placeName);
                        }
                    } else {
                        uploadFile();
                    }
                }
                break;
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                if (returnedAddress.getAddressLine(0) != null && !returnedAddress.getAddressLine(0).isEmpty()) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(0)).append(",");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.w("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current loction address", "Canont get Address!");
        }
        return strAdd;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {

        String date = "You picked the following date: From- " + dayOfMonth + "/" + (++monthOfYear) + "/" + year + " To " + dayOfMonthEnd + "/" + (++monthOfYearEnd) + "/" + yearEnd;

        mStartDate = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
        mEndDate = yearEnd + "-" + (++monthOfYearEnd) + "-" + dayOfMonthEnd;

        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                EditTimeLineActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.isThemeDark();
        tpd.setAccentColor(getResources().getColor(R.color.blackGreenColor));
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String hourStringEnd = hourOfDayEnd < 10 ? "0" + hourOfDayEnd : "" + hourOfDayEnd;
        String minuteStringEnd = minuteEnd < 10 ? "0" + minuteEnd : "" + minuteEnd;
        String time = "You picked the following time: From - " + hourString + "h" + minuteString + " To - " + hourStringEnd + "h" + minuteStringEnd;
//        mStartTime = hourString + "h" + minuteString;
//        mEndTime = hourStringEnd + "h" + minuteStringEnd;

        mStartTime = hourString + ":" + minuteString + ":00";
        mEndTime = hourStringEnd + ":" + minuteStringEnd + ":00";
    }


}
