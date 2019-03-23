package com.kingtous.wifilocate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;

public class BaseMapFragmentActivity extends FragmentActivity {
    private AMap mMap;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basemap_fragment_activity);
        setUpMapIfNeeded();
        Intent intent=getIntent();
        double lat=intent.getDoubleExtra("Latitude",1);
        double lon=intent.getDoubleExtra("Longitude",1);
        latLng=new LatLng(lat,lon);

        Marker marker=mMap.addMarker(new MarkerOptions()
        .draggable(false).position(latLng));
        CameraUpdate update= CameraUpdateFactory.newLatLngZoom(latLng,17);
        mMap.animateCamera(update);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map)).getMap();
        }
    }

}
