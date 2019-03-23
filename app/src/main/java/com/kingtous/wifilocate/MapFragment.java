package com.kingtous.wifilocate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;

public class MapFragment extends SupportMapFragment {

    public MapView mapView;
    public AMap aMap;
    public Marker currentMarker;
    public LatLng currentLatLng;

    int OPEN_WIDE_MAP_CODE=1;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        if (aMap==null){
            aMap=mapView.getMap();
        }
        aMap.setOnMapLongClickListener(new AMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Intent intent=new Intent(getContext(),BaseMapFragmentActivity.class);
                intent.putExtra("Latitude",latLng.latitude);
                intent.putExtra("Longitude",latLng.longitude);
                startActivityForResult(intent,OPEN_WIDE_MAP_CODE);
            }
        });

    }

    public void setMark(LatLng latLng){
        if (currentMarker!=null){
            currentMarker.remove();
            currentLatLng=latLng;
        }
        //将位置标在地图上
        MarkerOptions options=new MarkerOptions();
        options.draggable(false);
        options.visible(true);
        currentMarker=aMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(false)
        );
        CameraUpdate update= CameraUpdateFactory.newLatLngZoom(latLng,17);
        aMap.animateCamera(update);
    }

    public void removeMark(){
        if (currentMarker!=null){
            currentMarker.remove();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.map_show,container,false);
        mapView=view.findViewById(R.id.map);
        return view;
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
