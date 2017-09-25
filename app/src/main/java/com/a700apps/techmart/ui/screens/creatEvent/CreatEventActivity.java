package com.a700apps.techmart.ui.screens.creatEvent;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
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
import com.a700apps.techmart.data.model.InnerModle;
import com.a700apps.techmart.data.model.OneToOneModel;
import com.a700apps.techmart.data.model.ServerResponse;
import com.a700apps.techmart.data.model.post;
import com.a700apps.techmart.data.network.ApiInterface;
import com.a700apps.techmart.ui.screens.creatpost.PostActivity;
import com.a700apps.techmart.ui.screens.groupmemberdetails.GroupActivity;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupsTimLineActivity;
import com.a700apps.techmart.ui.screens.meetingone.MeetingonetooneActivity;
import com.a700apps.techmart.utils.ApiClient;
import com.a700apps.techmart.utils.AppConst;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.MapDialogActivity;
import com.a700apps.techmart.utils.PermissionTool;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.URLS;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
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

public class CreatEventActivity extends AppCompatActivity implements EventView, View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    ImageView mUploadImageView, mLocationImageView, mDateImageView, mBack;
    LinearLayout mLinearContainer, linearLayout_select;
    String date, mStartDate, mEndDate, mStartTime, mEndTime;
    public static EventPresenter presenter;
    Dialog dialog;
    public AVLoadingIndicatorView indicatorView;


    TextView tv_location, tv_upload_image, tv_date;
    int desired_string;
    EditText editTextTitle, editTextDesc;
    private long selectedImageSize;
    private static final int SELECT_PICTURE = 1;
    private static final int PICK_LOCATION_REQUEST = 2;
    InnerModle innerModle;
    ProgressDialog progressDialog;
    private String selectedImagePath, mImagePath;
    LinearLayout linearLayout1;
    public static OneToOneModel model;
    private static final int PERMISSION_REQUEST_CODE = 786;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_metting);
        presenter = new EventPresenter();
        presenter.attachView(this);
        indicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait Uploading Image...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        innerModle = new InnerModle();

        desired_string = getIntent().getIntExtra("string_key", 0);
        findView();
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
        tv_date = (TextView) findViewById(R.id.tv_location);

        editTextTitle = (EditText) findViewById(R.id.editText2);
        editTextDesc = (EditText) findViewById(R.id.editText4);
        linearLayout_select = (LinearLayout) findViewById(R.id.ll_container);
        mBack.setOnClickListener(this);
        mUploadImageView.setOnClickListener(this);
        mLocationImageView.setOnClickListener(this);
        mDateImageView.setOnClickListener(this);
        mLinearContainer.setOnClickListener(this);
        linearLayout_select.setOnClickListener(this);
        tv_date.setOnClickListener(this);

        linearLayout1.setOnClickListener(this);

        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    PermissionTool.checkPermission(CreatEventActivity.this, PermissionTool.PERMISSION_LOCATION);
                    PermissionTool.checkPermission(CreatEventActivity.this, PermissionTool.PERMISSION_location_COARSE);

                    Intent intent1 = new Intent(CreatEventActivity.this, MapDialogActivity.class);
                    startActivityForResult(intent1, PICK_LOCATION_REQUEST);
                } catch (Exception e) {

                }

            }
        });

        tv_upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImagePath = null;
                selectedImageSize = 0;
                // select a file
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if (dpd != null) dpd.setOnDateSetListener(this);
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
    public void UpdateUi(post post) {
        dialog.hide();
        finish();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_upload_image:
                selectImage();
                break;
            case R.id.linearLayout1:


                if (editTextTitle.getText().toString().isEmpty()) {
                    Toast.makeText(CreatEventActivity.this, R.string.select_title, Toast.LENGTH_LONG).show();
                    return;
                }

                if (editTextDesc.getText().toString().isEmpty()) {
                    Toast.makeText(CreatEventActivity.this, R.string.select_title, Toast.LENGTH_LONG).show();
                    return;
                }

                if ((innerModle.getLongitude()) == null) {
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
                try {
                    PermissionTool.checkPermission(CreatEventActivity.this, PermissionTool.PERMISSION_LOCATION);
                    PermissionTool.checkPermission(CreatEventActivity.this, PermissionTool.PERMISSION_location_COARSE);

                    Intent intent1 = new Intent(CreatEventActivity.this, MapDialogActivity.class);
                    startActivityForResult(intent1, PICK_LOCATION_REQUEST);
                } catch (Exception e) {

                }
                break;
            case R.id.ll_container:
//                selectImage();
                break;
            case R.id.imageView:
                finish();
                break;
            case R.id.ll_comment_container:
                openDialog(editTextTitle, editTextDesc);
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
                dpd2.show(getFragmentManager(), "Datepickerdialog");
                break;
        }


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {

        String date = "You picked the following date: From- " + dayOfMonth + "/" + (++monthOfYear) + "/" + year + " To " + dayOfMonthEnd + "/" + (++monthOfYearEnd) + "/" + yearEnd;

        mStartDate = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        mEndDate = dayOfMonthEnd + "/" + (++monthOfYearEnd) + "/" + yearEnd;

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
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String hourStringEnd = hourOfDayEnd < 10 ? "0" + hourOfDayEnd : "" + hourOfDayEnd;
        String minuteStringEnd = minuteEnd < 10 ? "0" + minuteEnd : "" + minuteEnd;
        String time = "You picked the following time: From - " + hourString + "h" + minuteString + " To - " + hourStringEnd + "h" + minuteStringEnd;
        mStartTime = hourString + "h" + minuteString;
        mEndTime = hourStringEnd + "h" + minuteStringEnd;
    }

    void openDialog(final EditText title, final EditText Desc) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.my_dialog);
        dialog.show();


        TextView tv_public = (TextView) dialog.findViewById(R.id.tv_public);
        TextView tv_group = (TextView) dialog.findViewById(R.id.tv_group);

        tv_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (title.getText().toString().isEmpty()) {
                    dialog.dismiss();
                    Toast.makeText(CreatEventActivity.this, R.string.select_title, Toast.LENGTH_LONG).show();
                    return;
                }

                if (Desc.getText().toString().isEmpty()) {
                    dialog.dismiss();
                    Toast.makeText(CreatEventActivity.this, R.string.select_title, Toast.LENGTH_LONG).show();
                }
                if (Double.isNaN(innerModle.getLongitude()) || Double.isNaN((innerModle.getLatitude()))) {
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
                    Toast.makeText(CreatEventActivity.this, R.string.select_title, Toast.LENGTH_LONG).show();
                    return;
                }

                if (Desc.getText().toString().isEmpty()) {
                    dialog.dismiss();
                    Toast.makeText(CreatEventActivity.this, R.string.select_title, Toast.LENGTH_LONG).show();
                    return;
                }

                if (Double.isNaN(innerModle.getLongitude()) || Double.isNaN((innerModle.getLatitude()))) {
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

    //
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                int dataSize = 0;
                File f = null;
                Uri selectedImageUri = data.getData();
                String scheme = selectedImageUri.getScheme();
                selectedImagePath = getPathFromURI(CreatEventActivity.this,selectedImageUri);

                ImageView imageView = (ImageView) findViewById(R.id.iv_post);
                imageView.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
                imageView.setVisibility(View.VISIBLE);
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
                    Log.e("PATH", path);
                    try {
                        f = new File(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    selectedImageSize = f.length();
                }
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


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(CreatEventActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(",");
                }
                strAdd = strReturnedAddress.toString();
                Log.e("Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.e("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    // Uploading Image/Video
    private void uploadFile(final EditText title, final EditText Desc, final boolean check) {
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
                        if (check) {
                            presenter.sendEvent(innerModle.getLongitude(), innerModle.getLatitude(), getCompleteAddressString(innerModle.getLongitude(), innerModle.getLatitude()), desired_string, PreferenceHelper.getUserId(CreatEventActivity.this),
                                    title.getText().toString(), Desc.getText().toString(), mStartDate, mEndDate, "", false, mImagePath, "", "", true, CreatEventActivity.this);
                            dialog.dismiss();
                        } else {

                            presenter.sendEvent(innerModle.getLongitude(), innerModle.getLatitude(), getCompleteAddressString(innerModle.getLongitude(), innerModle.getLatitude()), desired_string, PreferenceHelper.getUserId(CreatEventActivity.this),
                                    title.getText().toString(), Desc.getText().toString(), mStartDate, mEndDate, "", false, mImagePath, "", "", false, CreatEventActivity.this);
                            dialog.dismiss();
                        }

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
                Toast.makeText(CreatEventActivity.this, getString(R.string.check_internet), Toast.LENGTH_LONG).show();
            }
        });
    }
    //

    //    onetoone
    private void uploadFileOnetoOne(final EditText title, final EditText Desc) {
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


                        model = new OneToOneModel();
                        model.setLongtud(innerModle.getLongitude());
                        model.setLatitude(innerModle.getLatitude());
                        model.setGroupId(desired_string);
                        model.setCreatedby(PreferenceHelper.getUserId(CreatEventActivity.this));
                        model.setTitle(title.getText().toString());
                        model.setDescr(Desc.getText().toString());
                        model.setStartDate(mStartDate);
                        model.setEndDate(mEndDate);
                        model.setImage(mImagePath);

                        Intent intentonetoone = new Intent(CreatEventActivity.this, MeetingonetooneActivity.class);
                        intentonetoone.putExtra("string_key", desired_string);
                        intentonetoone.putExtra("MyClass", model);
                        startActivity(intentonetoone);
//                        presenter.sendEvent(innerModle.getLongitude(), innerModle.getLatitude(), getCompleteAddressString(innerModle.getLongitude(), innerModle.getLatitude()), desired_string, PreferenceHelper.getUserId(CreatEventActivity.this),
//                                title.getText().toString(), Desc.getText().toString(), mStartDate, mEndDate, "", true, mImagePath, "", "", false, CreatEventActivity.this);
//                        dialog.dismiss();


                    }

                } else {
                    assert serverResponse != null;
                    Log.e("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CreatEventActivity.this, getString(R.string.check_internet), Toast.LENGTH_LONG).show();
            }
        });
    }

    void selectImage() {
        if (Build.VERSION.SDK_INT >= 21) {
            if (AppConst.checkPermission(CreatEventActivity.this)) {
                selectedImagePath = null;
                selectedImageSize = 0;
                // select a file
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);

            } else {
                AppConst.requestPermission(CreatEventActivity.this, PERMISSION_REQUEST_CODE);
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
    }

}