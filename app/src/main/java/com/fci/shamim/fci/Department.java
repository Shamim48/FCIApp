package com.fci.shamim.fci;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Department extends AppCompatActivity implements View.OnClickListener {
    private CardView CST, DTNT, TCT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);


        CST = findViewById(R.id.CSTDepID);
        DTNT = findViewById(R.id.DTNTDepId);
        TCT = findViewById(R.id.TCTDepId);

        CST.setOnClickListener(this);
        DTNT.setOnClickListener(this);
        TCT.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.CSTDepID) {
            Intent intent = new Intent(Department.this, CST_Department.class);
            startActivity(intent);
        } else if (view.getId() == R.id.DTNTDepId) {
            Intent intent = new Intent(Department.this, DTNT_Department.class);
            startActivity(intent);
        } else if (view.getId() == R.id.TCTDepId) {
            Intent intent = new Intent(Department.this, TCT_Department.class);
            startActivity(intent);
        }

    }
}
