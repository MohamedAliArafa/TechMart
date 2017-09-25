package com.a700apps.techmart.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by emad.mohamed on 8/29/2016.
 */


public class PermissionTool {
    public static final String PERMISSION_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION ;
    public static final String PERMISSION_CONTACTS = Manifest.permission.READ_CONTACTS ;
    public static final String PERMISSION_PHONE_STATE = Manifest.permission.READ_PHONE_STATE ;
    public static final String PERMISSION_location_COARSE = Manifest.permission.ACCESS_COARSE_LOCATION ;
    public static final String PERMISSION_WRITE_SETTINGS = Manifest.permission.WRITE_SETTINGS ;
    public static final String PERMISSION_READ_SETTINGS = Manifest.permission.READ_SYNC_SETTINGS ;

    public static boolean checkAllPermission(Activity context, String [] permissions){
        boolean allPermissionGranted = true ;
        for(int i= 0; i<permissions.length ;i++){
            if(!isPermissionGranted(context, permissions[i])){
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[i])) {
                    Toast.makeText(context, "I need this permission:"+permissions[i], Toast.LENGTH_SHORT).show();
                }
                allPermissionGranted = false ;
            }
        }
        if (!allPermissionGranted) {
            ActivityCompat.requestPermissions(context, permissions, 1);
            return false;
        }else{
            return true;
        }
    }

    public static boolean checkPermission(final Activity context, final String permission){
        if (!isPermissionGranted(context, permission)) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context) ;
                alertDialog.setTitle("Pick Image From Gallery OR Camera");
                alertDialog.setMessage("Access Permission");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(context, "I need this permission", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(context, new String[]{permission}, 1);
                        dialog.dismiss();
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert11 = alertDialog.create();
                alert11.show();
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                ActivityCompat.requestPermissions(context, new String[]{permission}, 1);
            }
            return false;
        }else{
            return true;
        }
    }

    private static boolean isPermissionGranted(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
