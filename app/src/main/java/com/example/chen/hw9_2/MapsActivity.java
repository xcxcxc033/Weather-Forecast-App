package com.example.chen.hw9_2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hamweather.aeris.communication.Action;
import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.AerisCommunicationTask;
import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.communication.AerisProgressListener;
import com.hamweather.aeris.communication.AerisRequest;
import com.hamweather.aeris.communication.BatchBuilder;
import com.hamweather.aeris.communication.BatchCallback;
import com.hamweather.aeris.communication.BatchCommunicationTask;
import com.hamweather.aeris.communication.Endpoint;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.communication.fields.ObservationFields;
import com.hamweather.aeris.communication.loaders.ObservationsTask;
import com.hamweather.aeris.communication.loaders.ObservationsTaskCallback;
import com.hamweather.aeris.communication.parameter.FieldsParameter;
import com.hamweather.aeris.communication.parameter.LimitParameter;
import com.hamweather.aeris.communication.parameter.ParameterBuilder;
import com.hamweather.aeris.communication.parameter.PlaceParameter;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.maps.interfaces.OnAerisMapLongClickListener;
import com.hamweather.aeris.maps.interfaces.OnAerisMarkerInfoWindowClickListener;
import com.hamweather.aeris.model.AerisBatchResponse;
import com.hamweather.aeris.model.AerisError;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.tiles.AerisPointData;
import com.hamweather.aeris.tiles.AerisTile;


import java.util.List;


public class MapsActivity extends Activity { //implements OnMapReadyCallback //FragmentActivity

   private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        String lat_s = getIntent().getStringExtra("lat");
        String lont_s = getIntent().getStringExtra("lont");

        Double lat = Double.parseDouble(lat_s);
        Double lont = Double.parseDouble(lont_s);
        Bundle postLL = new Bundle();
        postLL.putString("lat", lat_s);
        postLL.putString("lont", lont_s);



        MapFragment1 fragment = new MapFragment1();

        fragment.setArguments(postLL);


        fragmentTransaction.add(R.id.map, fragment);
        fragmentTransaction.commit();

    }


}




