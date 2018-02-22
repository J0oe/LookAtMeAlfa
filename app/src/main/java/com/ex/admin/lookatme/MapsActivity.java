package com.ex.admin.lookatme;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    static Presenter presenter;
    GoogleMap googleMap;
    LocationManager locationManager;
    ListView listView;
    ArrayAdapter adapter;
    int c = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        presenter = new Presenter();
        listView = findViewById(R.id.idListMapActyName);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.nameList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.lookingPosNow(getApplicationContext(), MainActivity.nameList.get(position), googleMap);
                listView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }


    public void onClickAllLocation(View view) {
        presenter.refreshMap(googleMap);

    }

    public void onClickShowMyLocation(View view) {
        presenter.showMyLocation(googleMap, this, locationManager);
    }

    public void onClickSaveMyLocation(View view) {
        presenter.saveMyLocation(this, locationManager);

    }

    public void onCLickGetVisible(View view) {
        listView = findViewById(R.id.idListMapActyName);
        if (c%2==0){
            listView.setVisibility(View.INVISIBLE);
        }else {
            listView.setVisibility(View.VISIBLE);
        }
        c++;
    }


}
