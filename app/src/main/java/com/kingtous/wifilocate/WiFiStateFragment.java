package com.kingtous.wifilocate;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import static android.content.Context.WIFI_SERVICE;

public class WiFiStateFragment extends Fragment {

    TextView text_wifi_name;
    TextView text_wifi_mac;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.handle_wifi_state,container,false);
        text_wifi_name=view.findViewById(R.id.text_WIFI_Name);
        text_wifi_mac=view.findViewById(R.id.text_WIFI_mac);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //申请权限
        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
        }, 0);
        //注册广播
        IntentFilter filter=new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        getActivity().registerReceiver(receiver,filter);
        //载入WiFi状态
        checkWiFi();
    }

    public boolean isCorrectWiFi(){

        return true;
    }

    private void setNoneWiFiState(){
        String str="<font color='red'><small>"+"非WiFi状态或者设备未连接到指定WiFi"+"</small></font>";
        text_wifi_name.setText(Html.fromHtml(str));
        text_wifi_mac.setText(Html.fromHtml(str));
    }

    private void checkWiFi(){
        if (isWiFi()){
            text_wifi_name.setText(getWifiName());
            text_wifi_mac.setText(getWifiMacAddress());
        }
        else setNoneWiFiState();
    }

    public boolean isWiFi() {
        ConnectivityManager cm = (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Objects.requireNonNull(cm).getActiveNetworkInfo();
        if (!networkInfo.getTypeName().equals("WIFI")){
            return false;
        }
        else return true;

    }

    private String getWifiName(){
        WifiManager wm = (WifiManager) Objects.requireNonNull(getActivity()).getApplicationContext().getSystemService(WIFI_SERVICE);
        if (wm != null) {
            WifiInfo winfo = wm.getConnectionInfo();
            return winfo.getSSID();
        }
        return null;
    }

    public String getWifiMacAddress() {
        WifiManager wm = (WifiManager) Objects.requireNonNull(getActivity()).getApplicationContext().getSystemService(WIFI_SERVICE);
        if (wm != null) {
            WifiInfo winfo = wm.getConnectionInfo();
            return winfo.getBSSID();
        }
        return null;
    }

    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (null != parcelableExtra) {
                    NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                    NetworkInfo.State state = networkInfo.getState();
                    if (networkInfo.getTypeName().equals("WIFI") && state == NetworkInfo.State.CONNECTED) {
                        //连接上WiFi
                        checkWiFi();
                    }
                    else setNoneWiFiState();
                }

            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        Objects.requireNonNull(getActivity()).unregisterReceiver(receiver);
    }
}
