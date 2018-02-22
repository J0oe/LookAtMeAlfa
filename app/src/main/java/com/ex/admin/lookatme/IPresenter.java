package com.ex.admin.lookatme;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.GoogleMap;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Admin on 11.02.2018.
 */

public interface IPresenter {
    void showMyLocation(GoogleMap googleMap, Context context, LocationManager locationManager);

    void saveMyLocation(Context context, LocationManager locationManager);

    void addNewPerson(String name, String login, String password, Context context, FileOutputStream nameStream, FileOutputStream loginStream, FileOutputStream passwordStream);

    void refreshMap(GoogleMap googleMap);

    void saveDatFaveAct(String name, String id, FileOutputStream namewForSteam, FileOutputStream idForSteam);

    void getDatFromAct(FileInputStream idFromSteam, FileInputStream nameFromSteam);

    void makePhoto();

    void lookingPosNow(Context context, String name, GoogleMap googleMap);

}
