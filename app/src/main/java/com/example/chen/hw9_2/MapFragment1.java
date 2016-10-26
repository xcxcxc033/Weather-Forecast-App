package com.example.chen.hw9_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.AerisMapView.AerisMapType;

import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.maps.interfaces.OnAerisMapLongClickListener;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.tiles.AerisTile;

public class MapFragment1 extends MapViewFragment implements OnAerisMapLongClickListener, AerisCallback
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        AerisEngine.initWithKeys(this.getString(R.string.aeris_client_id), this.getString(R.string.aeris_client_secret), "com.example.chen.hw9_2");

        View view = inflater.inflate(R.layout.fragment_interactive_maps, container, false);
        mapView = (AerisMapView)view.findViewById(R.id.aerisfragment_map);
        mapView.init(savedInstanceState, AerisMapType.GOOGLE);


//        String lat_s = "34";
//        String lont_s = "-118";
//        Double lat = Double.parseDouble(lat_s);
//        Double lont = Double.parseDouble(lont_s);

        Bundle bundle = getArguments();
        String lat_s = bundle.getString("lat");
        String lont_s = bundle.getString("lont");


        Double lat = Double.parseDouble(lat_s);
        Double lont = Double.parseDouble(lont_s);


        LatLng location = new LatLng(lat, lont);
        mapView.moveToLocation(location, 10);
//        mapView.setOnAerisMapLongClickListener();

        mapView.addLayer(AerisTile.RADSAT);

        //mapView.addLayer(AerisTile.CURRENT_HUMIDITY);

        return view;
    }


    @Override
    public void onResult(EndpointType endpointType, AerisResponse aerisResponse) {

    }

    @Override
    public void onMapLongClick(double v, double v1) {

    }
}