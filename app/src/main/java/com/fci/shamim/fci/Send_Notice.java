package com.fci.shamim.fci;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fci.shamim.fci.Model.Notice;
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

public class Send_Notice extends AppCompatActivity implements View.OnClickListener {

    private ImageView uploadImage;
    private EditText descriptionEt;
    private EditText titleEt;
    private Button registerBtn;
    private Button cleanBtn;
    private Button updateBtn;

    public static final int IMAGE_REQUEST=1;
    public static final int IMAGE_REQUEST2=2;

    private ProgressDialog loadingBar;
    Uri imageUri;

    String noticeId;

    //Fire base

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    FirebaseStorage firebaseStorage;
    DatabaseReference rootRef;
    DatabaseReference photoGalleryRef;
    StorageReference storageReference;
    DatabaseReference updateRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__notice);
        findId();
        loadingBar=new ProgressDialog(this);

        rootRef=FirebaseDatabase.getInstance().getReference();
        photoGalleryRef =rootRef.child("Notice");
        storageReference=FirebaseStorage.getInstance().getReference();

        uploadImage.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        cleanBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
    }

    public void findId(){
        uploadImage=findViewById(R.id.uploadImageIvId);
        descriptionEt=findViewById(R.id.imageDesEtId);
        titleEt=findViewById(R.id.noticeTtlEtId);
        registerBtn=findViewById(R.id.registerBtnId);
        cleanBtn=findViewById(R.id.ClearBtnId);
        updateBtn=findViewById(R.id.updateBtnId);
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
            Glide.with(uploadImage).load(imageUri)
                    .placeholder(R.drawable.ic_touch_app_black_24dp)
                    .into(uploadImage);
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
        final String descriptionTx = descriptionEt.getText().toString();
        final String titleTx = titleEt.getText().toString();

        registerBtn.setEnabled(false);

        loadingBar.setTitle("Photo Uploading");
        loadingBar.setMessage("Please wait, your photo is updating...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        // final String currentUserId=auth.getCurrentUser().getUid();
        noticeId = photoGalleryRef.push().getKey();

        StorageReference ref=storageReference.child("Notice").child(noticeId);

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
                        Notice photo=new Notice(noticeId,titleTx,descriptionTx,downloadUri.toString());

                        photoGalleryRef.child(noticeId).setValue(photo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    toast("Photo Upload Success");
                                    registerBtn.setEnabled(true);
                                    //  progressBar.setVisibility(View.GONE);
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
            case R.id.uploadImageIvId:
                selectImage();
                break;
            case R.id.registerBtnId:
                uploadData();
                break;
            case R.id.ClearBtnId:
                clearAll();
                break;
            case R.id.updateBtnId:

                break;
        }
    }

    public void clearAll(){
        imageUri=null;
        descriptionEt.setText("");
        titleEt.setText("");
    }
}
