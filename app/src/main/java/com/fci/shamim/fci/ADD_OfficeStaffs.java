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
import com.fci.shamim.fci.Model.Staff;
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

public class ADD_OfficeStaffs extends AppCompatActivity implements View.OnClickListener {

    private Button registerBtn;
    private Button clearBtn;
    private Button updateBtn;
    private ProgressBar progressBar;
    private CircleImageView profileIV;
    private ImageView imageViewProfile;
    private EditText adminPasswordEt;
    private EditText staffnameEt;
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
    Uri imageUri;


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
    String staffName;
    String designation;
    String selectDep;
    String userType;
    String email;
    String phone;
    String password;
    String staffId;


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
        setContentView(R.layout.activity_add__office_staffs);


        findId();

        designationList= new String[]{"Pharmacist", "Assistant-Librarian","Cashier","Laboratory-Assistant","cruft Instructor","Office Assistant","Ps of principal","Cook","Book Sorter","Cleaner","Dining Cleaner","Security Guard"};


        ArrayAdapter<String> designationAdapter=new ArrayAdapter<String>(this,R.layout.spinnersamplelayout,R.id.showTestSpinnerId,designationList);
        designationAt.setAdapter(designationAdapter);
        loadingBar=new ProgressDialog(this);
        rootRef=FirebaseDatabase.getInstance().getReference();
        teacherRef=rootRef.child("Staffs");
        storageReference=FirebaseStorage.getInstance().getReference();

        profileIV.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);

    }


    public void findId(){
        profileIV=findViewById(R.id.profile_imageSfId);
        // dateOfBirth=findViewById(R.id.dateOfBirthId);
        //  genderGroup=findViewById(R.id.genderGroupId);
       // selectDepSp =findViewById(R.id.selectDepartmentId);
        staffnameEt =findViewById(R.id.staffNameSignUpId);
        //addressEt=findViewById(R.id.addressSignUpEtId);
        designationAt=findViewById(R.id.designationATSfId);
        // nationality=findViewById(R.id.nationalityAtId);
        // nid=findViewById(R.id.nidId);
        emailEt=findViewById(R.id.EmailSignUpSfId);
        phoneEt=findViewById(R.id.phoneSfId);
        registerBtn=findViewById(R.id.registerBtnSfId);
        createAccountTv=findViewById(R.id.createAccountTvId);
        clearBtn=findViewById(R.id.ClearBtnSfId);
      //  updateBtn=findViewById(R.id.updateBtnId);
        //  imageViewProfile=findViewById(R.id.imageViewId);
        //  progressDialog=new ProgressDialog(Sign_Up.this);


    }
    private void selectImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
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
        staffName = staffnameEt.getText().toString();
        designation=designationAt.getText().toString();
       // selectDep=selectDepSp.getSelectedItem().toString();
        email=emailEt.getText().toString();
        phone=phoneEt.getText().toString();
        registerBtn.setEnabled(false);

        loadingBar.setTitle("Set Profile Image");
        loadingBar.setMessage("Please wait, your profile image is updating...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        // final String currentUserId=auth.getCurrentUser().getUid();
        staffId =teacherRef.push().getKey();

        StorageReference ref=storageReference.child("Staffs").child(staffId);

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
                       // Teacher teacher=new Teacher(noticeId, staffName,downloadUri.toString(),selectDep,designation,phone,email);
                        Staff staff=new Staff(staffId,downloadUri.toString(), staffName,designation,email,phone);

                        teacherRef.child(staffId).setValue(staff).addOnCompleteListener(new OnCompleteListener<Void>() {
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
            case R.id.registerBtnSfId:
                toast("Click register Btn");
                uploadData();
                break;

            case R.id.profile_imageSfId:
                selectImage();
                break;
            case R.id.ClearBtnSfId:
                clearAll();
                break;
        }
    }

    public void clearAll(){
        imageUri=null;
        staffnameEt.setText("");
        designationAt.setText("");
        phoneEt.setText("");
        emailEt.setText("");
        registerBtn.setEnabled(true);

    }
}
