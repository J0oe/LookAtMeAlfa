package com.ex.admin.lookatme;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

/**
 * Created by Admin on 22.02.2018.
 */

public class LoockingActivity extends AppCompatActivity {
    EditText textNameSaveBD;
    DBHelper dbHelper;
    ListView list;
    ArrayAdapter adapter;
    SQLiteDatabase db1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loocking_layout);
        MainActivity.nameList = new ArrayList<>();
        dbHelper = new DBHelper(this);

        db1 = dbHelper.getWritableDatabase();
        list = findViewById(R.id.idFavorPerson);

        Cursor c = db1.query("tableFinal", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int name = c.getColumnIndex("name");
            do {
                MainActivity.nameList.add(c.getString(name));
            } while (c.moveToNext());

        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.nameList);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }


    public void onClickBackMain(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onClickSaveBD(View view) {
        textNameSaveBD = findViewById(R.id.idNameSaveBD);
        dbHelper = new DBHelper(this);
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put("name", textNameSaveBD.getText().toString());
        db.insert("tableFinal", null, cv);

        db.close();

        MainActivity.nameList.add(textNameSaveBD.getText().toString());


        adapter.notifyDataSetChanged();


    }


    static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table tableFinal ("
                    + "id integer primary key autoincrement,"
                    + "name text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}

