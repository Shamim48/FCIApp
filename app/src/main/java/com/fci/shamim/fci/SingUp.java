package com.fci.shamim.fci;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

public class SingUp extends AppCompatActivity {

    EditText nameEt, emailEt, phoneEt, presAddressEt, permaAddressEt, skillEt, universityEt, subjectEt, designationEt, officeEt, locationEt, jobSectorEt;

    Spinner departmentSp, batchSp, shiftsp, bloodSp, genderSp, degreeSp, passingYearSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);


        /* ---------------Edit Text ID Find---------------------------*/

        nameEt = findViewById(R.id.UsernameSignUpId);
        emailEt = findViewById(R.id.emailSignUpId);
        phoneEt = findViewById(R.id.phoneId);
        presAddressEt = findViewById(R.id.presentAddressId);
        permaAddressEt = findViewById(R.id.permanenttAddressId);
        skillEt = findViewById(R.id.skillId);
        universityEt = findViewById(R.id.universityId);
        subjectEt = findViewById(R.id.subjectId);
        designationEt = findViewById(R.id.designationId);
        officeEt = findViewById(R.id.officeNameId);
        locationEt = findViewById(R.id.officeLocationId);
        jobSectorEt = findViewById(R.id.jobSectorId);

        /* ---------------Spinner ID Find---------------------------*/

        departmentSp = findViewById(R.id.selectDepartmentId);
        batchSp = findViewById(R.id.selectBatchId);
        shiftsp = findViewById(R.id.selectShiftId);
        bloodSp = findViewById(R.id.selectBloodId);
        genderSp = findViewById(R.id.selectGenderId);
        degreeSp = findViewById(R.id.selectDegreeId);
        passingYearSp = findViewById(R.id.selectPassingYearId);


    }
}
