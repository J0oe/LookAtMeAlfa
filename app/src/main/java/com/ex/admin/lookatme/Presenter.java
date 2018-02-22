package com.ex.admin.lookatme;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.ex.admin.lookatme.Worker.MY_NAME_SAVE;

/**
 * Created by Admin on 11.02.2018.
 */

public class Presenter implements IPresenter {


    Worker worker = new Worker() {
    };
    GoogleMap googleMap;

    public Presenter(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public Presenter() {
    }

    @Override
    public void showMyLocation(GoogleMap googleMap, Context context, LocationManager locationManager) {
        worker.showMyLocation(context, googleMap, locationManager);
    }

    @Override
    public void saveMyLocation(Context context, LocationManager locationManager) {
        worker.saveMyLocation(context, locationManager);
    }


    @Override
    public void addNewPerson(String name, String login, String password, Context context, FileOutputStream nameStream, FileOutputStream loginStream, FileOutputStream passwordStream) {

        worker.addNewPerson(name, login, password, context, nameStream, loginStream, passwordStream);

    }

    @Override
    public void refreshMap(GoogleMap googleMap) {
        worker.refreshAllLocation(googleMap);
    }

    @Override
    public void saveDatFaveAct(String name, String id, FileOutputStream namewForSteam, FileOutputStream idForSteam) {
        worker.saveMyLoginPasss(name, id, namewForSteam, idForSteam);
    }

    @Override
    public void getDatFromAct(FileInputStream idFromSteam, FileInputStream nameFromSteam) {
        worker.getMyLoginPass(idFromSteam, nameFromSteam);
    }


    @Override
    public void makePhoto() {
    }

    @Override
    public void lookingPosNow(Context context, String name,GoogleMap googleMap) {
        worker.showMyPosNow(context, googleMap, name);
    }




}


