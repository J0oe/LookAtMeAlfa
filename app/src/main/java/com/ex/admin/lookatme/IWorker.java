package com.ex.admin.lookatme;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.GoogleMap;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Admin on 13.02.2018.
 */

public interface IWorker {
    void saveMyName(String name, String password);

    void showMyLocation(Context context, GoogleMap googleMap, LocationManager locationManager);

    void saveMyLocation(Context context, LocationManager locationManager);

    void refreshAllLocation(GoogleMap googleMap);

    void getMyLoginPass(FileInputStream idFromSteam, FileInputStream nameFromSteam);

    void saveMyLoginPasss(String name, String id, FileOutputStream namewForSteam, FileOutputStream idForSteam);

    void addNewPerson(String name, String login, String password, Context context, FileOutputStream nameStream, FileOutputStream loginStream, FileOutputStream passwordStream);

    void showMyPosNow(Context context, GoogleMap googleMap, String name);


}
