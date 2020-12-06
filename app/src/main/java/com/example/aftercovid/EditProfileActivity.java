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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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

    //choose gender and preferences radio buttons
    RadioGroup gender;
    RadioGroup preference;
    RadioButton userGender;
    RadioButton userPreference;

    //get user info/edit it
    EditText editName;
    EditText editDescription;
    EditText editAge;
    Button buttonConfirm;
    Button buttonInterests;
    //List<User> users;
    DatabaseReference databaseUsers;

    //to add pictures
    private ImageView profilePic;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private StorageReference storageReferenceImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        gender = (RadioGroup) findViewById(R.id.radioGender);
        preference = (RadioGroup) findViewById(R.id.radioPreference);
        editName=(EditText)findViewById(R.id.editName);
        editDescription=(EditText)findViewById(R.id.editDescription);
        editAge=(EditText)findViewById(R.id.editAge);
        buttonConfirm=(Button)findViewById(R.id.buttonConfirm);
        buttonInterests=(Button)findViewById(R.id.buttonInterests);
        //users = new ArrayList<User>();
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = fuser.getUid();
        profilePic = findViewById(R.id.imageAdd);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        storageReferenceImage = storage.getReference("images/"+uid);

        //jesli klikniemy na zdj mozemy je zmienic
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        //czeka az sie zaznaczy jakiegos radiobuttona z plci
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                userGender = (RadioButton) findViewById(checkedId);
            }
        });

        //czeka az sie zaznaczy jakiegos radiobuttona z preferencja
        preference.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                userPreference = (RadioButton) findViewById(checkedId);
            }
        });

        //some pic logic
        storageReferenceImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                getPicture(uri);
            }
        });

        //redirect to interests activity
        buttonInterests.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(EditProfileActivity.this, InterestsActivity.class);
                startActivity(intent);
            }
        });

        //dodaje zedytowanego uzytkownika
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                databaseUsers = FirebaseDatabase.getInstance().getReference("users");
                //sprawdzenie czy wszystkei pola sa wyplenione
                if((!TextUtils.isEmpty(editName.getText().toString()))&&(!TextUtils.isEmpty(editDescription.getText().toString()))&&(!TextUtils.isEmpty(editAge.getText().toString()))&&(!TextUtils.isEmpty(userPreference.getText().toString()))&&(!TextUtils.isEmpty(userGender.getText().toString()))) {
                    String name = editName.getText().toString();
                    String description = editDescription.getText().toString();
                    String sage = editAge.getText().toString();
                    int age = Integer.parseInt(sage);
                    String gender = userGender.getText().toString();
                    String preference = userPreference.getText().toString();
                    User user = new User(name, description, age, preference, gender);
                    databaseUsers.child(uid).setValue(user);
                }
                else{
                    Toast.makeText(EditProfileActivity.this,"Fill in all textboxes in correct format!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //sprawdza aktualne dane o uzytkowniku
        databaseUsers = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("firstName").exists()) {
                    String name = snapshot.child("firstName").getValue().toString();
                    editName.setText(name);
                }
                if(snapshot.child("description").exists()){
                    String description = snapshot.child("description").getValue().toString();
                    editDescription.setText(description);
                }
                if(snapshot.child("preference").exists()){
                    String preference = snapshot.child("preference").getValue().toString();
                    if(preference.equals("Females")){
                        RadioButton check = (RadioButton) findViewById(R.id.radioPreferenceFemales);
                        check.toggle();
                    }
                    if(preference.equals("Males")){
                        RadioButton check = (RadioButton) findViewById(R.id.radioPreferenceMales);
                        check.toggle();
                    }
                }
                if(snapshot.child("age").exists()){
                    String age = snapshot.child("age").getValue().toString();
                    editAge.setText(age);
                }
                if(snapshot.child("gender").exists()){
                    String gender = snapshot.child("gender").getValue().toString();
                    if(gender.equals("Female")){
                        RadioButton check = (RadioButton) findViewById(R.id.radioGenderFemale);
                        check.toggle();
                    }
                    if(gender.equals("Male")){
                        RadioButton check = (RadioButton) findViewById(R.id.radioGenderMale);
                        check.toggle();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //wlacza galerie na telefonie
    private void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    //cd, do zdjec
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