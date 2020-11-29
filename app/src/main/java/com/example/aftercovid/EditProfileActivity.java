package com.example.aftercovid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

//        //or maybe onStart()???
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
}