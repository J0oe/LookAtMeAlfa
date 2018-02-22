package com.ex.admin.lookatme;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Admin on 19.02.2018.
 */

public class Move {
    interface Moved {
        void exempleCB();
    }

    Moved myUser;

    public void registerCallBack(Moved moved) {
        this.myUser = moved;
    }


    void run() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myNameDB = database.getReference("mapAllLocation/idLocation/0/idMyLocation/1/name");
        myNameDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("MOVED---->>", dataSnapshot.getValue().toString());
                myUser.exempleCB();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
