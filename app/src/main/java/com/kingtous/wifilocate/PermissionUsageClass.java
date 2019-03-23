package com.kingtous.wifilocate;

import android.Manifest;

public class PermissionUsageClass {

    public static int REQUEST_CODE_AMAP=0;

    public static String[] permission={Manifest.permission.ACCESS_NETWORK_STATE,
    Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.CHANGE_WIFI_STATE,Manifest.permission.INTERNET,
    Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN
    };

}
