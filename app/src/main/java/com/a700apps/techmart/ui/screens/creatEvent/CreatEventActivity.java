package com.a700apps.techmart.ui.screens.creatEvent;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
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
import android.support.v7.widget.LinearLayoutManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.OneToneAdapter;
import com.a700apps.techmart.data.model.InnerModle;
import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.model.OneToOneModel;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.model.post;
import com.a700apps.techmart.data.network.ApiInterface;
import com.a700apps.techmart.ui.screens.meetingone.MeetingonetooneActivity;
import com.a700apps.techmart.ui.screens.register.RegisterActivity;
import com.a700apps.techmart.utils.ApiClient;
import com.a700apps.techmart.utils.AppConst;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.MapDialogActivity;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.URLS;
import com.a700apps.techmart.utils.loadingDialog;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by samir salah on 9/13/2017.
 */

public class CreatEventActivity extends AppCompatActivity implements
        EventView, View.OnClickListener, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, OneToneAdapter.onUserSelected {
    private static final int SELECT_PICTURE = 1;
    private static final int PICK_LOCATION_REQUEST = 2;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int PERMISSION_REQUEST_CODE = 786;
    private static final int Permission_storage_code = 787;
    public static EventPresenter presenter;
    public static OneToOneModel model;
    public AVLoadingIndicatorView indicatorView;
    ImageView mUploadImageView, mLocationImageView, mDateImageView, mBack;
    LinearLayout mLinearContainer, linearLayout_select;
    String date, mStartDate, mEndDate, mStartTime, mEndTime;
    Dialog dialog;
    ImageView imageView;
    TextView tv_location, tv_upload_image, tv_date, mSelectMeeting;
    int desired_string;
    EditText editTextTitle, editTextDesc;
    InnerModle innerModle;
    ProgressDialog progressDialog;
    LinearLayout linearLayout1;
    private long selectedImageSize;
    private String selectedImagePath, mImagePath;
    Date currentTime;
    EmptyRecyclerView rv;
    List<MyConnectionList.ResultEntity> mUserMeetingList;
    boolean isOpen = false;
    Dialog dialogsLoading;
    private static final int multy_permission_request = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_metting);
        presenter = new EventPresenter();
        presenter.attachView(this);
        indicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);

        innerModle = new InnerModle();
        desired_string = getIntent().getIntExtra("string_key", 0);
        findView();


        currentTime = Calendar.getInstance().getTime();
    }

    void findView() {
        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        mUploadImageView = (ImageView) findViewById(R.id.iv_upload_image);
        mLocationImageView = (ImageView) findViewById(R.id.iv_location);
        mDateImageView = (ImageView) findViewById(R.id.iv_date);
        mLinearContainer = (LinearLayout) findViewById(R.id.ll_comment_container);
        mBack = (ImageView) findViewById(R.id.imageView);
        tv_upload_image = (TextView) findViewById(R.id.tv_upload_image);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_date = (TextView) findViewById(R.id.tv_date);

        editTextTitle = (EditText) findViewById(R.id.editText2);
        editTextDesc = (EditText) findViewById(R.id.editText4);
        editTextDesc.setScroller(new Scroller(this));
        editTextDesc.setMaxLines(5);
        editTextDesc.setVerticalScrollBarEnabled(true);
        editTextDesc.setMovementMethod(new ScrollingMovementMethod());
        linearLayout_select = (LinearLayout) findViewById(R.id.ll_container);
        mSelectMeeting = (TextView) findViewById(R.id.tv_select_meeting);
        presenter.getOneToOne(PreferenceHelper.getUserId(this), desired_string, this);
        rv = (EmptyRecyclerView) findViewById(R.id.recyclerView3);

        mBack.setOnClickListener(this);
        mUploadImageView.setOnClickListener(this);
        mLocationImageView.setOnClickListener(this);
        mDateImageView.setOnClickListener(this);
        mLinearContainer.setOnClickListener(this);
        linearLayout_select.setOnClickListener(this);
        mSelectMeeting.setOnClickListener(this);
        tv_date.setOnClickListener(this);

//        linearLayout1.setOnClickListener(this);

        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_location.setEnabled(false);
                if (ActivityCompat.checkSelfPermission(CreatEventActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(CreatEventActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreatEventActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PICK_LOCATION_REQUEST);
                    return;
                } else {
                    // Write you code here if permission already given.
                    Intent intent1 = new Intent(CreatEventActivity.this, EventMapDialogActivity.class);

                    startActivityForResult(intent1, PICK_LOCATION_REQUEST);
                }


            }
        });

        tv_upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                selectImage();
                openChooseMethodDialog();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        tv_location.setEnabled(true);
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void showLoadingProgress() {
        dialogsLoading = new loadingDialog().showDialog(this);

//        indicatorView.setVisibility(View.VISIBLE);
//        indicatorView.show();
    }

    @Override
    public void dismissLoadingProgress() {
        dialogsLoading.dismiss();
    }

    @Override
    public void UpdateUi(post post) {
        if (dialog != null) {
            dialog.hide();
        }

        Toast.makeText(CreatEventActivity.this, getString(R.string.add_event), Toast.LENGTH_LONG).show();

        Globals.mIndex = 2;
        Globals.oneToOneId = null;
        finish();
    }

    @Override
    public void update(List<MyConnectionList.ResultEntity> userMeeting) {
//        if (userMeeting.size() == 0) {
//            rv.setEmptyView(findViewById(R.id.tv_memeber));
//
//        }
        mUserMeetingList = userMeeting;
        rv.setAdapter(new OneToneAdapter(this, userMeeting, desired_string, model));
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_meeting:
                if (isOpen) {
                    isOpen = false;
                    rv.setVisibility(View.GONE);
                } else {
                    isOpen = true;
                    if (mUserMeetingList.size() > 0) {
                        rv.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(CreatEventActivity.this, "There is no data in your connection", Toast.LENGTH_LONG).show();
                    }

                }

                break;
            case R.id.iv_upload_image:
//                selectImage();
                openChooseMethodDialog();
                break;
            case R.id.linearLayout1:


                if (editTextTitle.getText().toString().isEmpty()) {
                    editTextTitle.setError(getResources().getString(R.string.select_title));

                    return;
                }

                if (editTextDesc.getText().toString().isEmpty()) {
                    editTextDesc.setError(getResources().getString(R.string.select_Post));

                    return;
                }

                if ((innerModle.getLongitude() == null) || (innerModle.getLatitude() == null)) {
                    Toast.makeText(CreatEventActivity.this, R.string.select_location, Toast.LENGTH_LONG).show();
                    return;
                }

                if (mStartDate == null || mEndDate == null) {
                    Toast.makeText(CreatEventActivity.this, R.string.select_date, Toast.LENGTH_LONG).show();
                    return;
                }
                if (selectedImagePath == null) {
                    Toast.makeText(CreatEventActivity.this, R.string.not_image, Toast.LENGTH_LONG).show();
                    return;
                }
                if (!AppUtils.isInternetAvailable(CreatEventActivity.this)) {
                    Snackbar snackbar1 = Snackbar.make(view, R.string.no_internet_connection, Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                } else {

                    uploadFileOnetoOne(editTextTitle, editTextDesc);

                }


                break;

            case R.id.iv_location:
                if (ActivityCompat.checkSelfPermission(CreatEventActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CreatEventActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreatEventActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    return;
                } else {
                    // Write you code here if permission already given.
                    Intent intent1 = new Intent(CreatEventActivity.this, MapDialogActivity.class);
                    startActivityForResult(intent1, PICK_LOCATION_REQUEST);
                }


                break;
            case R.id.ll_container:
//                selectImage();
                openChooseMethodDialog();
                break;
            case R.id.imageView:
                finish();
                break;
            case R.id.ll_comment_container:
                if (Globals.oneToOneId == null) {
                    openDialog(editTextTitle, editTextDesc);
                } else {

                    if (editTextTitle.getText().toString().isEmpty()) {
//                        dialog.dismiss();
                        editTextTitle.setError(getResources().getString(R.string.select_title));
                        return;
                    }

                    if (editTextDesc.getText().toString().isEmpty()) {
//                        dialog.dismiss();
                        editTextDesc.setError(getResources().getString(R.string.select_Post));
                        return;
                    }
                    if ((innerModle.getLongitude() == null) || (innerModle.getLatitude() == null)) {
//                        dialog.dismiss();
                        Toast.makeText(CreatEventActivity.this, R.string.select_location, Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (selectedImagePath == null) {
//                        dialog.dismiss();
                        Toast.makeText(CreatEventActivity.this, R.string.not_image, Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (mStartDate == null || mEndDate == null) {
                        Toast.makeText(CreatEventActivity.this, R.string.select_date, Toast.LENGTH_LONG).show();
                        return;
                    }

                    uploadFileOnetoOne(editTextTitle, editTextDesc);
                }
                break;
            case R.id.tv_date:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        CreatEventActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAutoHighlight(true);
                dpd.isThemeDark();
                dpd.setAccentColor(getResources().getColor(R.color.blackGreenColor));
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.iv_date:

                Calendar now2 = Calendar.getInstance();
                DatePickerDialog dpd2 = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        CreatEventActivity.this,
                        now2.get(Calendar.YEAR),
                        now2.get(Calendar.MONTH),
                        now2.get(Calendar.DAY_OF_MONTH)
                );
                dpd2.setAutoHighlight(true);
                dpd2.isThemeDark();
                dpd2.setAccentColor(getResources().getColor(R.color.blackGreenColor));

                dpd2.show(getFragmentManager(), "Datepickerdialog");
                break;
        }


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {

        String date = "You picked the following date: From- " + dayOfMonth + "/" + (++monthOfYear) + "/" + year + " To " + dayOfMonthEnd + "/" + (++monthOfYearEnd) + "/" + yearEnd;

        mStartDate = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
        mEndDate = yearEnd + "-" + (++monthOfYearEnd) + "-" + dayOfMonthEnd;

        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                CreatEventActivity.this,
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

        mStartTime = hourString + ":" + minuteString+":00";
        mEndTime = hourStringEnd + ":" + minuteStringEnd+":00";
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
                    title.setError(getResources().getString(R.string.select_title));
                    return;
                }

                if (Desc.getText().toString().isEmpty()) {
                    dialog.dismiss();
                    Desc.setError(getResources().getString(R.string.select_Post));
                    return;
                }
                if ((innerModle.getLongitude() == null) || (innerModle.getLatitude() == null)) {
                    dialog.dismiss();
                    Toast.makeText(CreatEventActivity.this, R.string.select_location, Toast.LENGTH_LONG).show();
                    return;
                }

                if (selectedImagePath == null) {
                    dialog.dismiss();
                    Toast.makeText(CreatEventActivity.this, R.string.not_image, Toast.LENGTH_LONG).show();
                    return;
                }
                if (mStartDate == null || mEndDate == null) {
                    Toast.makeText(CreatEventActivity.this, R.string.select_date, Toast.LENGTH_LONG).show();
                    return;
                }
                if (!AppUtils.isInternetAvailable(CreatEventActivity.this)) {
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
                    title.setError(getResources().getString(R.string.select_title));
                    return;
                }

                if (Desc.getText().toString().isEmpty()) {
                    dialog.dismiss();
                    Desc.setError(getResources().getString(R.string.select_Post));
                    return;
                }

                if ((innerModle.getLongitude() == null) || (innerModle.getLatitude() == null)) {
                    dialog.dismiss();
                    Toast.makeText(CreatEventActivity.this, R.string.select_location, Toast.LENGTH_LONG).show();
                    return;
                }
                if (mStartDate == null || mEndDate == null) {
                    Toast.makeText(CreatEventActivity.this, R.string.select_date, Toast.LENGTH_LONG).show();
                    return;
                }
                if (selectedImagePath == null) {
                    dialog.dismiss();
                    Toast.makeText(CreatEventActivity.this, R.string.not_image, Toast.LENGTH_LONG).show();
                    return;
                }

                if (!AppUtils.isInternetAvailable(CreatEventActivity.this)) {
                    Snackbar snackbar1 = Snackbar.make(v, R.string.no_internet_connection, Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                } else {
                    uploadFile(title, Desc, false);
                }

                dialog.dismiss();
            }
        });
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

    //
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == SELECT_PICTURE) {

//                int dataSize = 0;
//                File f = null;
//                Uri selectedImageUri = data.getData();
//                String scheme = selectedImageUri.getScheme();
//                selectedImagePath = getPathFromURI(CreatEventActivity.this, selectedImageUri);
//
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
//                        f = new File(path);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    selectedImageSize = f.length();
//                }

                Uri selectedPicture = data.getData();
                if (selectedPicture == null) {
                    Toast.makeText(this, "Sorry .. please select another image", Toast.LENGTH_SHORT).show();
                    return;
                }
                selectedImagePath = getPathFromURI(CreatEventActivity.this, selectedPicture);
                // Get and resize profile image
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                selectedImagePath = cursor.getString(columnIndex);
                cursor.close();

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

            } else if (requestCode == PICK_LOCATION_REQUEST) {
                if (data.hasExtra(URLS.EXTRA_PARCELABLE)) {
                    innerModle = data.getParcelableExtra(URLS.EXTRA_PARCELABLE);
//                    userAddressEditText.setText(getCompleteAddressString(innerModle.getLongitude(), innerModle.getLatitude()));
                    Log.d("Latitude", innerModle.getLatitude() + "");
                    Log.d("Longitude", innerModle.getLongitude() + "");
                }
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
                        Log.e("date", "start" + mStartDate + "enddate" + mEndDate);
                        if (check) {

                            presenter.sendEvent(mStartTime, mEndTime, innerModle.getLongitude(), innerModle.getLatitude(), getCompleteAddressString(innerModle.getLongitude(), innerModle.getLatitude()), desired_string, PreferenceHelper.getUserId(CreatEventActivity.this),
                                    title.getText().toString(), Desc.getText().toString(), mStartDate, mEndDate, "", false, mImagePath, "", String.valueOf(currentTime), true, CreatEventActivity.this);
                            dialog.dismiss();
                        } else {

                            presenter.sendEvent(mStartTime, mEndTime, innerModle.getLongitude(), innerModle.getLatitude(), getCompleteAddressString(innerModle.getLongitude(), innerModle.getLatitude()), desired_string, PreferenceHelper.getUserId(CreatEventActivity.this),
                                    title.getText().toString(), Desc.getText().toString(), mStartDate, mEndDate, "", false, mImagePath, "", String.valueOf(currentTime), false, CreatEventActivity.this);
                            dialog.dismiss();
                        }

                    }

                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
//                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                progressDialog.dismiss();
                dialogsLoading.dismiss();
                Toast.makeText(CreatEventActivity.this, getString(R.string.check_internet), Toast.LENGTH_LONG).show();
            }
        });
    }
    //

    //    onetoone
    private void uploadFileOnetoOne(final EditText title, final EditText Desc) {
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
                        presenter.sendEvent(mStartTime, mEndTime, innerModle.getLongitude(),
                                innerModle.getLatitude(),
                                getCompleteAddressString(innerModle.getLongitude(), innerModle.getLatitude()),
                                desired_string,
                                PreferenceHelper.getUserId(CreatEventActivity.this),
                                editTextTitle.getText().toString()
                                , editTextDesc.getText().toString(),
                                mStartDate, mEndDate,
                                Globals.oneToOneId, true, mImagePath, "", String.valueOf(currentTime), false, CreatEventActivity.this);

//
                    }

                } else {
                    assert serverResponse != null;
                    Log.e("Response", serverResponse.toString());
                }
//                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                progressDialog.dismiss();
                dialogsLoading.dismiss();
                Toast.makeText(CreatEventActivity.this, getString(R.string.check_internet), Toast.LENGTH_LONG).show();
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
            case PICK_LOCATION_REQUEST:
                Intent intent1 = new Intent(CreatEventActivity.this, MapDialogActivity.class);
                startActivityForResult(intent1, PICK_LOCATION_REQUEST);
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onSelectUser(String userId, String username) {
        mSelectMeeting.setText(username);
        if (isOpen) {
            isOpen = false;
            rv.setVisibility(View.GONE);
        } else {
            isOpen = true;
            if (mUserMeetingList.size() > 0) {
                rv.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(CreatEventActivity.this, "There is no data in your connection", Toast.LENGTH_LONG).show();
            }

        }
    }


    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
