package com.fci.shamim.fci;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Result extends AppCompatActivity implements View.OnClickListener {
    CardView AcademicResult, BoardResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        AcademicResult = findViewById(R.id.academicResultId);
        BoardResult = findViewById(R.id.boardResultId);

        AcademicResult.setOnClickListener(this);
        BoardResult.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.academicResultId) {
            Intent intent = new Intent(Result.this, AcademicResultView.class);
            startActivity(intent);
        } else if (view.getId() == R.id.boardResultId) {
            Intent intent = new Intent(Result.this, BoardResultView.class);
            startActivity(intent);
        }
    }
}