package com.ex.admin.lookatme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 20.02.2018.
 */

public class FaceActivity extends AppCompatActivity {

    @BindView(R.id.idNameLogin)
    EditText nameLogin;

    @BindView(R.id.idPasswordLogin)
    EditText passwordLogin;

//
//    FileOutputStream nameStream;
//    FileOutputStream loginStream;
//    FileOutputStream passwordStream;
//    FileOutputStream idStream;
//


    Presenter presenter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face_table);
        ButterKnife.bind(this);
        presenter = new Presenter();

    }

    public void onCLickMoveToRegistr(View view) {
        startActivity(new Intent(this, Registr.class));
    }

    public void onCLickMoveToMain(View view) {
        nameLogin = findViewById(R.id.idNameLogin);

        DatabaseReference myLoc = database.getReference("Persons/idPersonMax");

        myLoc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int a = 1; a <= Integer.valueOf(dataSnapshot.getValue().toString()); a++) {
                    DatabaseReference myLoc = database.getReference("Persons/Person/" + a + "/name");
                    final int finalA1 = a;
                    myLoc.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue().toString().equals(nameLogin.getText().toString())) {
                                FileOutputStream nameForSteam = null;

                                try {
                                    nameForSteam = openFileOutput("nameReg.txt", MODE_PRIVATE);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                FileOutputStream idForSteam = null;
                                try {
                                    idForSteam = openFileOutput("idReg.txt", MODE_PRIVATE);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }




                                FileInputStream nameFromSteam = null;
                                try {
                                    nameFromSteam = openFileInput("nameReg.txt");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }




                                FileInputStream idFromSteam = null;
                                try {
                                    idFromSteam = openFileInput("idReg.txt");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }


                                presenter.saveDatFaveAct(dataSnapshot.getValue().toString(), String.valueOf(finalA1), nameForSteam, idForSteam);
                                presenter.getDatFromAct(idFromSteam, nameFromSteam);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

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
}
