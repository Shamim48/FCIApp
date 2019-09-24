package com.fci.shamim.fci;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fci.shamim.fci.Model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Add_Teacher extends AppCompatActivity implements View.OnClickListener {


    private Button registerBtn;
    private Button clearBtn;
    private Button updateBtn;
    private ProgressBar progressBar;
    private CircleImageView profileIV;
    private ImageView imageViewProfile;
    private EditText adminPasswordEt;
    private EditText teachernameEt;
    private EditText addressEt;
    private EditText dateOfBirth;
    private RadioGroup genderGroup;
    private AutoCompleteTextView designationAt;
    private AutoCompleteTextView nationality;
    private EditText nid;
    private Spinner selectDepSp;
    private Spinner selectUserType;
    private EditText emailEt;
    private EditText phoneEt;

    private TextView createAccountTv;
    private String adminPassword="";
    //Intent for receive Update Data
    Intent updateIntent;
    String updateKey="uploadKey";
    String userId;
    Uri  imageUri;


    public static final int IMAGE_REQUEST=1;
    public static final int IMAGE_REQUEST2=2;

    // private Uri imageUri;
    private Uri cropImageUri;
    Bitmap bitmap;
    private String currentImage;
    private String[] country;
    private String[] designationList;
    private ProgressDialog loadingBar;

    //getData from view
    String teacherName;
    String designation;
    String selectDep;
    String userType;
    String email;
    String phone;
    String password;
    String teacherId;


    //Fire Base

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    FirebaseStorage firebaseStorage;
    DatabaseReference rootRef;
    DatabaseReference teacherRef;
    StorageReference storageReference;
    DatabaseReference updateRef;

    //Adapter
    ArrayAdapter<String> depAdapter;



    //List
    ArrayList<String> depList=new ArrayList<>();
    //  ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__teacher);
        findId();
        depList.add("Chose Department ..");
        depList.add("CST");
        depList.add("DTNT");
        depList.add("TCT");
        depList.add("RS");
        designationList= new String[]{"Department Head", "Instructor","Junior Instructor","CI"};




         depAdapter=new ArrayAdapter<String>(this,R.layout.spinnersamplelayout,R.id.showTestSpinnerId,depList);
        selectDepSp.setAdapter(depAdapter);
        ArrayAdapter<String> designationAdapter=new ArrayAdapter<>(this,R.layout.spinnersamplelayout,R.id.showTestSpinnerId,designationList);
        designationAt.setAdapter(designationAdapter);
        loadingBar=new ProgressDialog(this);
        rootRef=FirebaseDatabase.getInstance().getReference();
        teacherRef=rootRef.child("Teacher");
        storageReference=FirebaseStorage.getInstance().getReference();

       profileIV.setOnClickListener(this);
       registerBtn.setOnClickListener(this);
       clearBtn.setOnClickListener(this);

    }

    private void selectImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }


    public void findId(){
        profileIV=findViewById(R.id.profile_imageId);
        // dateOfBirth=findViewById(R.id.dateOfBirthId);
        //  genderGroup=findViewById(R.id.genderGroupId);
        selectDepSp =findViewById(R.id.selectDepartmentId);
        teachernameEt =findViewById(R.id.UsernameSignUpId);
        //addressEt=findViewById(R.id.addressSignUpEtId);
        designationAt=findViewById(R.id.designationATId);
        // nationality=findViewById(R.id.nationalityAtId);
        // nid=findViewById(R.id.nidId);
        emailEt=findViewById(R.id.EmailSignUpId);
        phoneEt=findViewById(R.id.phoneId);
        registerBtn=findViewById(R.id.registerBtnId);
        createAccountTv=findViewById(R.id.createAccountTvId);
        clearBtn=findViewById(R.id.ClearBtnId);
        updateBtn=findViewById(R.id.updateBtnId);
        //  imageViewProfile=findViewById(R.id.imageViewId);
        //  progressDialog=new ProgressDialog(Sign_Up.this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null /*&& data.getData() != null*/) {

              imageUri = data.getData();

            // Picasso.get().load(imageUri).rotate(270).into(profileIV);
            //this is file compress method
            // setPic();

           /* CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(2, 2)
                    .setAutoZoomEnabled(true)
                    .start(this);
*/
            Glide.with(profileIV).load(imageUri)
                    .placeholder(R.drawable.ic_touch_app_black_24dp)
                    .into(profileIV);
        }
/*
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                loadingBar.setTitle("Set Profile Image");
                loadingBar.setMessage("Please wait, your profile image is updating...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                cropImageUri = result.getUri();
                Glide.with(profileIV).load(cropImageUri)
                        .placeholder(R.drawable.ic_touch_app_black_24dp)
                        .into(profileIV);
                loadingBar.dismiss();

            }

        }
        */


    }

    public void toast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }


    public String getFileExtension(Uri imageUri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }


    private void uploadData() {
        teacherName=teachernameEt.getText().toString();
        designation=designationAt.getText().toString();
        selectDep=selectDepSp.getSelectedItem().toString();
        email=emailEt.getText().toString();
        phone=phoneEt.getText().toString();
        registerBtn.setEnabled(false);

       loadingBar.setTitle("Set Profile Image");
        loadingBar.setMessage("Please wait, your profile image is updating...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


       // final String currentUserId=auth.getCurrentUser().getUid();
        teacherId=teacherRef.push().getKey();

        StorageReference ref=storageReference.child("Teacher").child(teacherId);

        ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //  Uri downloadUrl = taskSnapshot.getDownloadUrl();


                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri=uriTask.getResult();

                        //  Upload upload=new Upload(imageName,downloadUri.toString());

                        // String userId=databaseReference.push().getKey();
                        Teacher teacher=new Teacher(teacherId, teacherName,downloadUri.toString(),selectDep,designation,phone,email);

                        teacherRef.child(teacherId).setValue(teacher).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    toast("Data Upload Success");
                                    registerBtn.setEnabled(true);
                                    progressBar.setVisibility(View.GONE);
                                    loadingBar.dismiss();
                                    imageUri=null;

                                    Intent intent=new Intent(getApplicationContext(),Home.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });





    //    loadingBar.dismiss();
    }


    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.registerBtnId:
                uploadData();
                break;

            case R.id.profile_imageId:
                selectImage();
                break;
            case R.id.ClearBtnId:
                clearAll();
                break;
        }
    }

    public void clearAll(){
        imageUri=null;
        teachernameEt.setText("");
        designationAt.setText("");
        phoneEt.setText("");
        emailEt.setText("");
        registerBtn.setEnabled(true);

    }
}
