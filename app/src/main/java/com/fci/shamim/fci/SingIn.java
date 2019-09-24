package com.fci.shamim.fci;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SingIn extends AppCompatActivity {
    private TextView createAccountTv;
    private Button loginBtn;
    private EditText userNameEt;
    private EditText emailEt;
    private EditText passwordEt;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference rootRef;

    public static  String selectedUT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        rootRef=FirebaseDatabase.getInstance().getReference();
       // rootRef.keepSynced(true);
        findId();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();




        createAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SingUp.class);
                startActivity(intent);
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //selectedUT=choseUserSpinner.getSelectedItem().toString();
                //  final String userName=userNameEt.getText().toString();
              /*
                String email=emailEt.getText().toString();
                String password=passwordEt.getText().toString();
                if(email.isEmpty()){
                    emailEt.setError("Please Enter Email..");
                    emailEt.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    passwordEt.setError("Please Enter Password ");
                    passwordEt.requestFocus();
                    return;
                }
                if (password.length()<6){
                    passwordEt.setError("Password Minimum 6 Character...");
                    passwordEt.requestFocus();
                    return;
                }
              *//* if(!Patterns.EMAIL_ADDRESS.matcher(emai).matches()){
                   emailET.setError("Enter a valid email Address.");
                   emailET.requestFocus();
                   return;
               }*//*

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // progressBar.setVisibility(View.GONE);

                        if(task.isSuccessful()){
                            finish();
                            Toast.makeText(getApplicationContext(),"Sing in Successful",Toast.LENGTH_LONG).show();
                            emailEt.setText("");
                            passwordEt.setText("");
                            Intent intent=new Intent(getApplicationContext(),Home.class);

                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("userType",selectedUT);
                            startActivity(intent);
                        }else {

                            emailEt.setError("Please enter Correct email..");
                            emailEt.requestFocus();

                            passwordEt.setError("Please enter Correct password..");
                            passwordEt.requestFocus();
                            return;

                        }
                    }
                });

              *//* Intent i=new Intent(getApplicationContext(),Home.class);
               i.putExtra("userType",selectedUT);
               startActivity(i);*//*

                 */

                Intent intent=new Intent(getApplicationContext(),Home.class);
                startActivity(intent);
                finish();
            }


        });

    }

    @Override
    protected void onStart() {
        super.onStart();
       /* if(user!=null){
            Intent intent=new Intent(MainActivity.this,Home.class);
            startActivity(intent);
            finish();
        }*/
    }

    public void findId(){
        createAccountTv=findViewById(R.id.createAccountTvId);
        loginBtn=findViewById(R.id.loginBtnId);
        //choseUserSpinner=findViewById(R.id.choseUserSignInId);
        //  userNameEt=findViewById(R.id.UsernameSignInId);
        emailEt=findViewById(R.id.EmailSignInId);
        passwordEt=findViewById(R.id.PasswordSignInId);


    }
}
