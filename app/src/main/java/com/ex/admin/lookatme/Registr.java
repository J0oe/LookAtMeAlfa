package com.ex.admin.lookatme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 20.02.2018.
 */

public class Registr extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Integer dateCheckNickname;
    Integer dateForCheckScrool;

    FileOutputStream fileNumber;
    FileOutputStream fileNumber2;
    FileOutputStream fileNumber3;
    @BindView(R.id.nameTableReg)
    EditText name;

    @BindView(R.id.nickTableReg)
    EditText nickname;

    @BindView(R.id.passTableReg)
    EditText password;

    Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registr_table);
        ButterKnife.bind(this);

        presenter = new Presenter();
    }


    public void onClickAddMe(View view) throws FileNotFoundException {
        name = findViewById(R.id.nameTableReg);
        nickname = findViewById(R.id.nickTableReg);
        password = findViewById(R.id.passTableReg);

        FileOutputStream nameStream = openFileOutput("nameReg.txt", MODE_PRIVATE);

        FileOutputStream loginStream = openFileOutput("loginReg.txt", MODE_PRIVATE);
        FileOutputStream passwordStream = openFileOutput("passwordReg.txt", MODE_PRIVATE);

        presenter.addNewPerson(name.getText().toString(), nickname.getText().toString(), password.getText().toString(), this, nameStream, loginStream, passwordStream);
        startActivity(new Intent(this, FaceActivity.class));


    }
}
