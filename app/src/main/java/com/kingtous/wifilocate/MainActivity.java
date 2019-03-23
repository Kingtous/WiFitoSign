package com.kingtous.wifilocate;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    FrameLayout frame_wifi=null;
    TextView textView_position=null;
    Button btn_pos=null;
    FrameLayout frame_map;
    MapFragment mapFragment;
    WiFiStateFragment wiFiStateFragment;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            //回调
            if (aMapLocation!=null){
                if (aMapLocation.getErrorCode()==0){
                    String pos=aMapLocation.getProvince()+aMapLocation.getCity()+
                            aMapLocation.getDistrict()+aMapLocation.getStreet();
                    textView_position.setText(pos);
                    //更新地图
                    mapFragment.setMark(new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude()));
                }
                else {
                    log(aMapLocation.getErrorInfo());
                }
            }
        }
    };
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_position=(TextView) findViewById(R.id.text_position);
        btn_pos=(Button) findViewById(R.id.btn_pos);
        frame_wifi=findViewById(R.id.frame_state);
        frame_map=findViewById(R.id.frame_map);
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()){
                    startLocate();
                }
            }
        });
        mapFragment=new MapFragment();
        wiFiStateFragment=new WiFiStateFragment();
        //载入WiFi状态
        setWiFiState();
        if (checkPermission())
            startLocate();
    }

    boolean checkPermission(){
        List<String> permission_list=new ArrayList<>();
        for (int i = 0; i< PermissionUsageClass.permission.length; i++){
            if (ContextCompat.checkSelfPermission(this, PermissionUsageClass.permission[i])!= PackageManager.PERMISSION_GRANTED){
                permission_list.add(PermissionUsageClass.permission[i]);
            }
        }
        if (permission_list.isEmpty()) {// 全部允许
            return true;
        } else {//存在未允许的权限
            String[] permissionsArr = permission_list.toArray(new String[permission_list.size()]);
            ActivityCompat.requestPermissions(this, permissionsArr, 101);
            return false;
        }
    }

    void setMap() {
        if (checkPermission()) {
            //将map加入进Fragment中
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!mapFragment.isAdded()) {
                transaction.add(R.id.frame_map, mapFragment).commit();
            }
        }
    }

    void setWiFiState(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!wiFiStateFragment.isAdded()) {
            transaction.add(R.id.frame_state, wiFiStateFragment).commit();
        }

    }

    void startLocate(){
        init();
        getLocation();
    }

    void init(){
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        mLocationOption = new AMapLocationClientOption();

        AMapLocationClientOption option = new AMapLocationClientOption();
        /*
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if(null != mLocationClient){
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
        //低能耗模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //单次定位
        mLocationOption.setOnceLocation(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        setMap();
    }

    void getLocation(){
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }



    private void log(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }



    //权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                        if (showRequestPermission) {
                            checkPermission();
                            return;
                        }
                    }
                }
                break;
        }
    }


}
