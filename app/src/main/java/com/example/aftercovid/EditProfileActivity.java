package com.example.aftercovid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    EditText editName;
    EditText editDescription;
    EditText editAge;
    EditText editPreference;
    EditText editGender;
    Button buttonConfirm;
    List<User> users;
    DatabaseReference databaseUsers;
    private ImageView profilePic;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private StorageReference storageReferenceImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editPreference=(EditText)findViewById(R.id.editPreference);
        editName=(EditText)findViewById(R.id.editName);
        editDescription=(EditText)findViewById(R.id.editDescription);
        editAge=(EditText)findViewById(R.id.editAge);
        editGender=(EditText)findViewById(R.id.editGender);
        buttonConfirm=(Button)findViewById(R.id.buttonConfirm);
        users = new ArrayList<User>();
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = fuser.getUid();

        profilePic = findViewById(R.id.imageAdd);



        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        storageReferenceImage = storage.getReference("images/"+uid);

        storageReferenceImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                getPicture(uri);
            }
        });
        //String url = "https://firebasestorage.googleapis.com/v0/b/aftercovid-e18dd.appspot.com/o/images%2FTwepbxiznrSaj8MsM0hsAhltfkt2?alt=media&token=f12b38a4-47cd-4272-a6ec-5eb486b4259d";
        //Glide.with(this).load(url).into(profilePic);











        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

            databaseUsers = FirebaseDatabase.getInstance().getReference("users");
            String name = editName.getText().toString();
            String description = editDescription.getText().toString();
            String preference = editPreference.getText().toString();
            String gender = editGender.getText().toString();
            String sage = editAge.getText().toString();
            int age = Integer.parseInt(sage);

            User user = new User(name,description,age,preference,gender);

            databaseUsers.child(uid).setValue(user);
            }
        });

//        //it causes crashes when record does not exist
//        databaseUsers = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
//        databaseUsers.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String name = snapshot.child("firstName").getValue().toString();
//                String description = snapshot.child("description").getValue().toString();
//                //String preference = snapshot.child("preference").getValue().toString();
//                String age = snapshot.child("age").getValue().toString();
//                editName.setText(name);
//                editDescription.setText(description);
//                editAge.setText(age);
//                //editPreference.setText(preference);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
    }

    private void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture(){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading image...");
        pd.show();

        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = fuser.getUid();
        StorageReference riversRef = storageReference.child("images/" + uid);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        pd.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        pd.dismiss();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        pd.setMessage("Percentage: " + (int)progressPercent + "%");
                    }
                });
    }
    private void getPicture(final Uri uri){
        String url = uri.toString();
        Glide.with(this).load(url).into(profilePic);
    }
}