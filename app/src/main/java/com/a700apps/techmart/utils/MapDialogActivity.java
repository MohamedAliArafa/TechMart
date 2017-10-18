package com.a700apps.techmart.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.InnerModle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapDialogActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleMap.OnMapClickListener {
    EditText addressTextView;
    Button okButton;
    SupportMapFragment mapFragment;
    private static final String TAG = "MapsActivity";
    private static final int TIME_INTERVAL = 1000;//millis
    private static final int TIME_FASTEST_INTERVAL = 1000;//millis
    private static final int MAP_ZOOM = 100;
    private static final int MAP_ZOOM_OUT = 10;
    private static final int MAP_ZOOM_TO = 16;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest mLocationRequest;
    private Marker sourceMarker;
    private InnerModle innerModle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.activity_map_dialog);


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        addressTextView = (EditText) findViewById(R.id.activity_current_address_textview_dialog);
        okButton = (Button) findViewById(R.id.activity_btn_savemap_dialog);
        innerModle = new InnerModle();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(URLS.EXTRA_PARCELABLE, innerModle);
                setResult(RESULT_OK, returnIntent);
                addressTextView.setText(getCompleteAddressString(innerModle.getLatitude(), innerModle.getLongitude()));
                finish();
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (isLocationEnabled(MapDialogActivity.this)) {
            buildGoogleApiClient();
        } else {
            openGpsDialog();
        }
    }

    protected void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        googleApiClient = new GoogleApiClient.Builder(MapDialogActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();

    }

    private void openGpsDialog() {
        if (googleApiClient == null) {
            if (checkGooglePlayServicesAvailable()) {
                if (!isLocationEnabled(MapDialogActivity.this)) {
                    googleApiClient = new GoogleApiClient.Builder(MapDialogActivity.this).addApi(LocationServices.API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this).build();
                    googleApiClient.connect();

                    LocationRequest locationRequest = LocationRequest.create();
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    locationRequest.setInterval(10 * 1000);
                    locationRequest.setFastestInterval(3 * 1000);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

                    //**************************
                    builder.setAlwaysShow(true); //this is the key ingredient
                    //**************************

                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            final LocationSettingsStates state = result.getLocationSettingsStates();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied. The client can initialize location
                                    // requests here.
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied. But could be fixed by showing the user
                                    // a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        status.startResolutionForResult(MapDialogActivity.this, 1000);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied. However, we have no way to fix the
                                    // settings so we won't show the dialog.
                                    break;
                            }
                        }
                    });
                }
                Log.e(TAG, "Google Play ");
            }
        }
    }

    @Override
    public void onBackPressed() {
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra(URLS.EXTRA_PARCELABLE, innerModle);
//        setResult(RESULT_OK, returnIntent);
        finish();
//        super.onBackPressed();
    }

    private boolean checkGooglePlayServicesAvailable() {
        final int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(MapDialogActivity.this);
        if (status == ConnectionResult.SUCCESS) {
            return true;
        }

        Log.e(TAG, "Google Play Services not available: " + GooglePlayServicesUtil.getErrorString(status));

        if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
            final Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(status, MapDialogActivity.this, 1);
            if (errorDialog != null) {
                errorDialog.show();
            }
        }

        return false;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected");
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(TIME_INTERVAL);
        mLocationRequest.setFastestInterval(TIME_FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        startLocationUpdates();


    }

    protected void startLocationUpdates() {

        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, mLocationRequest, this);
        if (PermissionTool.checkAllPermission(MapDialogActivity.this, new String[]{PermissionTool.PERMISSION_LOCATION, PermissionTool.PERMISSION_location_COARSE})) {

        } else {
            PermissionTool.checkAllPermission(MapDialogActivity.this, new String[]{PermissionTool.PERMISSION_LOCATION, PermissionTool.PERMISSION_location_COARSE});
        }
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed");


    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged");
//        Log.d("last step ", location.getLatitude() + "");
//        Log.d("last step ", location.getLongitude() + "");
        Log.d(TAG, "no bundle found");
        Geocoder geocoder = new Geocoder(MapDialogActivity.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),
                    // In this sample, get just a single address.
                    1);
            Log.e("laststep", location.getLatitude() + "");
            Log.e("laststep", location.getLongitude() + "");
            Log.e("getAddress", addresses.get(0) + "");
          //  Toast.makeText(this,getCompleteAddressString(location.getLongitude(), location.getLatitude()),Toast.LENGTH_LONG).show();

//            String strAdd = "";
//            StringBuilder strReturnedAddress = new StringBuilder("");


        } catch (IOException ioException) {
            // Catch network or other I/O problems.
//            errorMessage = "service not available";

        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
//            errorMessage = "invalid lat long used";
        }
        MarkerOptions optionFirstLocation = new MarkerOptions();
        optionFirstLocation.position(new LatLng(location.getLatitude(), location.getLongitude()));
        optionFirstLocation.icon(BitmapDescriptorFactory.defaultMarker());
        sourceMarker = mMap.addMarker(optionFirstLocation);
       // if (SharedPrefUserDataUtils.getLocationLatitude(this) != "") {
          //  double lat = Double.parseDouble(SharedPrefUserDataUtils.getLocationLatitude(this));
            //double longt = Double.parseDouble(SharedPrefUserDataUtils.getLocationLongtude(this));
            //addressTextView.setText(getCompleteAddressString(lat, longt));

            //initCamera(lat, longt);


      //  } else {
        addressTextView.setText(getCompleteAddressString(location.getLatitude(), location.getLongitude()));
         //   Toast.makeText(this,getCompleteAddressString(location.getLatitude(), location.getLongitude()),Toast.LENGTH_LONG).show();
            initCamera(location.getLatitude(), location.getLongitude());
       // }

//            moveMapTo(location.getLatitude(), location.getLongitude());
        stopLocationUpdates();
        mMap.setOnMapClickListener(this);
//        locationConnected(new LatLng(location.getLatitude(), location.getLongitude()));

        innerModle = new InnerModle(location.getLatitude(), location.getLongitude(), "");

    }



    @Override
    public void onMapClick(LatLng latLng) {
        if (sourceMarker != null) {
            sourceMarker.remove();
        }
        sourceMarker = mMap.addMarker(new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker()));
//        locationConnected(latLng);
        Log.e("latitude", latLng.latitude + "");
        Log.e("longitude", latLng.longitude + "");
        innerModle = new InnerModle(latLng.latitude, latLng.longitude, "");
      //  SharedPrefUserDataUtils.setLocationLatitude(this, latLng.latitude + "");
        //SharedPrefUserDataUtils.setLocationLongtude(this, latLng.longitude + "");
     //   Toast.makeText(this,getCompleteAddressString(innerModle.getLongitude(), innerModle.getLatitude()),Toast.LENGTH_LONG).show();

        addressTextView.setText(getCompleteAddressString(innerModle.getLatitude(), innerModle.getLongitude()));

    }

    private void initCamera(double lat, double lng) {

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,
                lng), MAP_ZOOM_OUT));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(MAP_ZOOM_TO));
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

//                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                if (returnedAddress.getAddressLine(0) != null && !returnedAddress.getAddressLine(0).isEmpty()) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(0)).append(",");
                }
//                }
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

}
