package com.fci.shamim.fci.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fci.shamim.fci.Model.Teacher;
import com.fci.shamim.fci.R;
import com.fci.shamim.fci.TeacherShow;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class TeacherAdepter extends RecyclerView.Adapter<TeacherAdepter.EmployeeHolder> {

    private Context context;
   private List<Teacher> employeeList;
   // CustomItemClickListener listener;
   private FirebaseUser currentUser;
   private DatabaseReference databaseReference;
   FirebaseUser getCurrentUser;
   StorageReference storageReference;

    private UserListener userListener;
    String userEmail;

    // Current User Information / user info variable
    private String teacherId;
    private String imageUrl;
    private String name;
    private String department;
    private String designation;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;
    private String userType;


    public TeacherAdepter(Context context, List<Teacher> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
       // userListener= (UserListener) context;
    }

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
       View view=LayoutInflater.from(context).inflate(R.layout.teachersample_layout,parent,false);
       final EmployeeHolder employeeHolder=new EmployeeHolder(view);


        return employeeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final EmployeeHolder holder, final int position) {

        final Teacher signUp=employeeList.get(position);

        // get current User data from Employee List
        teacherId =employeeList.get(position).getTeacherId();
        imageUrl=employeeList.get(position).getImageUri();
        name=employeeList.get(position).getName();
        department=employeeList.get(position).getDepartment();
        designation=employeeList.get(position).getDesignation();
        phone=employeeList.get(position).getPhone();
        email=employeeList.get(position).getEmail();


        holder.nameTv.setText(signUp.getName());
        holder.designation.setText(signUp.getDesignation());
        Glide.with(holder.employeeImage).load(signUp.getImageUri())
                .placeholder(R.drawable.ic_photo_size_select_actual_black_24dp)
                .into(holder.employeeImage);



        if(!(userEmail.equals("solver.apps.bd@gmail.com"))){
            holder.menuTv.setText(null);
        }else {

            holder.menuTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu=new PopupMenu(context,view);
                    MenuInflater inflater=popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.user_row_popup,popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.editMenuItmId:

                               // Toast.makeText(context,"Coming son",Toast.LENGTH_SHORT).show();
                                editUserInfo();
                                break;
                            case R.id.deleteMenuItmId:

                                getCurrentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(context,"User Delete Successful",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context,"Delete Fail and Exception: "+e,Toast.LENGTH_SHORT).show();
                                   }
                                });
                               databaseReference.child(signUp.getTeacherId()).removeValue();
                               storageReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       Toast.makeText(context,"Image Delete Successful",Toast.LENGTH_SHORT).show();

                                   }
                               }).addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       Toast.makeText(context,"Delete Fail and Exception: "+e,Toast.LENGTH_SHORT).show();

                                   }
                               });
                                break;
                        }
                            return false;
                        }
                    });
                }
            });
        }

        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri="tel:"+signUp.getPhone();
                Intent callIntent=new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(uri));
                context.startActivity(callIntent);


            }
        });
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, TeacherShow.class);
                intent.putExtra("image",employeeList.get(position).getImageUri());
                intent.putExtra("name",employeeList.get(position).getName());
                intent.putExtra("department",employeeList.get(position).getDepartment());
                intent.putExtra("designation",employeeList.get(position).getDesignation());
                intent.putExtra("email",employeeList.get(position).getEmail());
                intent.putExtra("phone",employeeList.get(position).getPhone());
                context.startActivity(intent);
            }
        });
    }

    private void editUserInfo() {
        Intent intent=new Intent(context, Teacher.class);
        intent.putExtra("teacherId", teacherId);
        intent.putExtra("image",imageUrl);
        intent.putExtra("name",name);
        intent.putExtra("department",department);
        intent.putExtra("designation",designation);
        intent.putExtra("email",email);
        intent.putExtra("phone",phone);
        intent.putExtra("password",password);
        intent.putExtra("confirmPass",confirmPassword);
        intent.putExtra("userType",userType);
        intent.putExtra("update","update");
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    class EmployeeHolder extends RecyclerView.ViewHolder{

        private ImageView employeeImage;
        private TextView nameTv;
        private TextView designation;
        private ImageButton callBtn;
        LinearLayout parentLayout;
        TextView menuTv;
       public EmployeeHolder(@NonNull View itemView) {
           super(itemView);

           employeeImage=itemView.findViewById(R.id.userRltImageId);
           nameTv=itemView.findViewById(R.id.nameTvId);
           designation=itemView.findViewById(R.id.designationTvId);
           callBtn=(ImageButton)itemView.findViewById(R.id.callBtnTbId);
           parentLayout=(LinearLayout) itemView.findViewById(R.id.parentLayoutId);
           menuTv=itemView.findViewById(R.id.row_menu);
           try {
               currentUser= FirebaseAuth.getInstance().getCurrentUser();

               userEmail=currentUser.getEmail();
               databaseReference= FirebaseDatabase.getInstance().getReference("Solver");
               getCurrentUser=FirebaseAuth.getInstance().getCurrentUser();
               storageReference= FirebaseStorage.getInstance().getReference("Solver").child(imageUrl);
           }catch (Exception e){
               Toast.makeText(context,"Exception: "+e,Toast.LENGTH_SHORT).show();
           }
       }
   }

  public interface UserListener {
        public void onUserDelete(String id);
        public void onUserEdit(String id);
   }
}
