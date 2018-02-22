package com.ex.admin.lookatme;

import android.*;
import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

import static com.ex.admin.lookatme.Worker.MY_ID_SAVE;
import static com.ex.admin.lookatme.Worker.MY_NAME_SAVE;

public class ServiceLooking extends Service {

    private static final int NOTIFY_ID = 101;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public ServiceLooking() {
    }

    @Override
    public void onCreate() {
        super.onCreate();


        final Context context = getApplicationContext();
        Intent notificationIntent = new Intent(context, MainActivity.class);


        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);


        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Android App")
                .setContentText("getting an address");

        Notification notification = builder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFY_ID, notification);
        notification.flags = notification.FLAG_NO_CLEAR;

        startForeground(NOTIFY_ID, notification);

        final DatabaseReference myLoc = database.getReference("mapAllLocation/idLocation/" + String.valueOf(Integer.valueOf(MY_ID_SAVE)-1) + "/myLocNow");


        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                myLoc.setValue(MY_NAME_SAVE + "/" + location.getLongitude() + "/" + location.getLatitude());

            }
        };


        timer.schedule(timerTask, 0, 5 * 1000);


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
