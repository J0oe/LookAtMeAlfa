package com.ex.admin.lookatme;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> nameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, ServiceLooking.class));

        nameList = new ArrayList<>();
        LoockingActivity.DBHelper dbHelper = new LoockingActivity.DBHelper(this);

        SQLiteDatabase db1 = dbHelper.getWritableDatabase();
        try {
            Cursor c = db1.query("tableFinal", null, null, null, null, null, null);
            if (c.moveToFirst()) {
                int name = c.getColumnIndex("name");
                do {
                    nameList.add(c.getString(name));
                } while (c.moveToNext());

            }
        } catch (Throwable throwable) {
        }

    }

    public void onClickMoveToMap(View view) {
        startActivity(new Intent(this, MapsActivity.class));

    }

    public void onClickMoveToLoocking(View view) {
        startActivity(new Intent(this, LoockingActivity.class));

    }


}
