package com.ex.admin.lookatme;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Admin on 11.02.2018.
 */


public abstract class Worker implements IWorker {


    FileOutputStream nameStream;
    FileOutputStream loginStream;
    FileOutputStream passwordStream;


    FileOutputStream nameDubl;
    FileOutputStream idDubl;

    FileInputStream nameFromSteam;
    FileInputStream idFromSteam;

    String getMyName;
    String getMyLogin;
    String getMyPassword;


    static String MY_ID_SAVE;
    static String MY_NAME_SAVE;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<String> list;

    @Override
    public void showMyLocation(Context context, GoogleMap googleMap, LocationManager locationManager) {


        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(myLocation));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));


    }

    @Override
    public void showMyPosNow(Context context, final GoogleMap googleMap, final String _name) {
        googleMap.clear();
        DatabaseReference maxIdPerson = database.getReference("Persons/idPersonMax");
        maxIdPerson.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int g = 1; g <=Integer.valueOf(dataSnapshot.getValue().toString()); g++) {
                    final DatabaseReference name = database.getReference("Persons/Person/" + String.valueOf(g) + "/name");
                    final int finalG = g;
                    name.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue().toString().equals(_name)) {
                                final DatabaseReference name = database.getReference("mapAllLocation/idLocation/" + String.valueOf(Integer.valueOf(String.valueOf(finalG))-1) + "/myLocNow");
                                name.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        showMyLocationNow(dataSnapshot.getValue().toString(), googleMap);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void saveMyName(String name, String password) {

    }

    @Override
    public void saveMyLocation(final Context context, final LocationManager locationManager) {
        final String[] myIdLocation = new String[1];

        DatabaseReference myLoc = database.getReference("mapAllLocation/idLocation/" + MY_ID_SAVE + "/idMyLocationMax");
        myLoc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myIdLocation[0] = dataSnapshot.getValue().toString();

                DatabaseReference myLocForMap = database.getReference("mapAllLocation/idLocation/" + MY_ID_SAVE + "/idMyLocation/" + myIdLocation[0] + "/locForMap");
                DatabaseReference myNameDB = database.getReference("mapAllLocation/idLocation/" + MY_ID_SAVE + "/idMyLocation/" + myIdLocation[0] + "/name");
                DatabaseReference myLocationLongDB = database.getReference("mapAllLocation/idLocation/" + MY_ID_SAVE + "/idMyLocation/" + myIdLocation[0] + "/location/long");
                DatabaseReference myLocationLatDB = database.getReference("mapAllLocation/idLocation/" + MY_ID_SAVE + "/idMyLocation/" + myIdLocation[0] + "/location/lat");
                DatabaseReference myTimeDB = database.getReference("mapAllLocation/idLocation/" + MY_ID_SAVE + "/idMyLocation/" + myIdLocation[0] + "/time");


                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                String myTime = df.format(Calendar.getInstance().getTime());


                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                String locForMap = MY_NAME_SAVE + "/" + location.getLongitude() + "/" + location.getLatitude() + "/" + myTime;
                myLocForMap.setValue(locForMap);
                myNameDB.setValue(MY_NAME_SAVE);
                myTimeDB.setValue(myTime);
                myLocationLatDB.setValue(location.getLatitude());
                myLocationLongDB.setValue(location.getLongitude());
                Toast.makeText(context, "saved", Toast.LENGTH_LONG).show();

                addIdMyLocationMax();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void refreshAllLocation(final GoogleMap googleMap) {
        list = new ArrayList<>();
        googleMap.clear();

        DatabaseReference binDate = database.getReference("mapAllLocation/idLocationMax");
        binDate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int w = 0; w < Integer.valueOf(dataSnapshot.getValue().toString()); w++) {
                    DatabaseReference myLoc = database.getReference("mapAllLocation/idLocation/" + w + "/idMyLocationMax");

                    final int finalW = w;
                    myLoc.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int a = Integer.valueOf(dataSnapshot.getValue().toString());
                            for (int s = 1; s < a; s++) {
                                DatabaseReference myLocForMap = database.getReference("mapAllLocation/idLocation/" + finalW + "/idMyLocation/" + s + "/locForMap");
                                myLocForMap.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        showAllLocation(dataSnapshot.getValue().toString(), googleMap);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void getMyLoginPass(FileInputStream idFromSteam, FileInputStream nameFromSteam) {
        this.nameFromSteam = nameFromSteam;
        this.idFromSteam = idFromSteam;


        byte[] buffer1 = new byte[0];
        try {
            buffer1 = new byte[nameFromSteam.available()];
        } catch (Throwable throwable) {
        }
        try {
            nameFromSteam.read(buffer1);
        } catch (Throwable e) {
        }

        Worker.MY_NAME_SAVE = (new String(buffer1));

        byte[] buffer2 = new byte[0];
        try {
            buffer2 = new byte[idFromSteam.available()];
        } catch (Throwable throwable) {
        }
        try {
            idFromSteam.read(buffer2);
        } catch (Throwable e) {
        }

        Worker.MY_ID_SAVE = (new String(buffer2));
    }

    @Override
    public void addNewPerson(String name, String login, String password, final Context context, FileOutputStream nameStream, FileOutputStream loginStream, FileOutputStream passwordStream) {
        this.getMyLogin = login;
        this.getMyName = name;
        this.getMyPassword = password;

        this.nameStream = nameStream;
        this.loginStream = loginStream;
        this.passwordStream = passwordStream;

        DatabaseReference maxIdPerson = database.getReference("Persons/idPersonMax");
        maxIdPerson.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference name = database.getReference("Persons/Person/" + String.valueOf(Integer.valueOf(dataSnapshot.getValue().toString()) + 1) + "/name");
                DatabaseReference login = database.getReference("Persons/Person/" + String.valueOf(Integer.valueOf(dataSnapshot.getValue().toString()) + 1) + "/login");
                DatabaseReference password = database.getReference("Persons/Person/" + String.valueOf(Integer.valueOf(dataSnapshot.getValue().toString()) + 1) + "/password");


                name.setValue(getMyName);
                login.setValue(getMyLogin);
                password.setValue(getMyPassword);

                saveFileInfo(getMyName, getMyLogin, getMyPassword);

                addIdNewPerson();
                Toast.makeText(context, "SAVED", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void saveMyLoginPasss(final String name, String id, FileOutputStream namewForSteam, FileOutputStream idForSteam) {

        nameDubl = namewForSteam;
        idDubl = idForSteam;

        try

        {
            nameDubl.write(name.getBytes());
        } catch (
                IOException e)

        {

        } finally

        {
            try {
                nameDubl.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try

        {
            idDubl.write(id.getBytes());
        } catch (
                IOException e)

        {

        } finally

        {
            try {
                idDubl.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void addIdNewPerson() {
        final DatabaseReference maxIdPerson = database.getReference("Persons/idPersonMax");

        maxIdPerson.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                maxIdPerson.setValue(String.valueOf(Integer.valueOf(dataSnapshot.getValue().toString()) + 1));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference maxIdPostPerson = database.getReference("mapAllLocation/idLocationMax");

        maxIdPostPerson.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                DatabaseReference myLocForMap = database.getReference("mapAllLocation/idLocation/" + dataSnapshot.getValue().toString() + "/idMyLocation/1/locForMap");
                DatabaseReference myNameDB = database.getReference("mapAllLocation/idLocation/" + dataSnapshot.getValue().toString() + "/idMyLocation/1/name");
                DatabaseReference myLocationLongDB = database.getReference("mapAllLocation/idLocation/" + dataSnapshot.getValue().toString() + "/idMyLocation/1/location/long");
                DatabaseReference myLocationLatDB = database.getReference("mapAllLocation/idLocation/" + dataSnapshot.getValue().toString() + "/idMyLocation/1/location/lat");
                DatabaseReference myTimeDB = database.getReference("mapAllLocation/idLocation/" + dataSnapshot.getValue().toString() + "/idMyLocation/1/time");
                DatabaseReference idMyLocationMax = database.getReference("mapAllLocation/idLocation/" + dataSnapshot.getValue().toString() + "/idMyLocationMax");

                idMyLocationMax.setValue("1");
                myLocationLatDB.setValue("0");
                myTimeDB.setValue("0");
                myLocForMap.setValue("0");
                myNameDB.setValue("0");
                myLocationLongDB.setValue("0");


                maxIdPostPerson.setValue(String.valueOf(Integer.valueOf(dataSnapshot.getValue().toString()) + 1));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void addIdMyLocationMax() {
        final DatabaseReference myLoc = database.getReference("mapAllLocation/idLocation/" + MY_ID_SAVE + "/idMyLocationMax");

        myLoc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myLoc.setValue(String.valueOf(Integer.valueOf(dataSnapshot.getValue().toString()) + 1));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void showAllLocation(String locations, GoogleMap googleMap) {


        int a = locations.indexOf('/');
        String name = locations.substring(0, a);
        String dateName = locations.substring(a + 1);
        int b = dateName.indexOf('/');
        String longtitude = dateName.substring(0, b);
        String dateNameLong = dateName.substring(b + 1);
        int c = dateNameLong.indexOf('/');
        String latitude = dateNameLong.substring(0, c);
        String time = dateNameLong.substring(c + 1);

        String dateForTittle = name + "(" + time + ")";

        LatLng myLocation = new LatLng(Double.valueOf(latitude), Double.valueOf(longtitude));
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(myLocation).title(dateForTittle));

    }




    public void showMyLocationNow(String locations, GoogleMap googleMap) {


        int a = locations.indexOf('/');
        String name = locations.substring(0, a);
        String dateName = locations.substring(a + 1);
        int b = dateName.indexOf('/');
        String longtitude = dateName.substring(0, b);
        String latitude = dateName.substring(b + 1);

        String dateForTittle = name;

        LatLng myLocation = new LatLng(Double.valueOf(latitude), Double.valueOf(longtitude));
        googleMap.addMarker(new MarkerOptions().position(myLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title(dateForTittle));

    }

    public void saveFileInfo(String name, String login, String password) {


        try

        {
            nameStream.write(name.getBytes());
        } catch (
                IOException e)

        {

        } finally

        {
            try {
                nameStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try

        {
            loginStream.write(login.getBytes());
        } catch (
                IOException e)

        {

        } finally

        {
            try {
                loginStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try

        {
            passwordStream.write(password.getBytes());
        } catch (
                IOException e)

        {

        } finally

        {
            try {
                passwordStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
