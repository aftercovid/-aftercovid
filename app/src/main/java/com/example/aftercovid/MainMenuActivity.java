package com.example.aftercovid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    DatabaseReference database;
    ArrayList<String> existingRelationsList =  new ArrayList<>();
    ArrayList<String> likeList =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        final String myId = fuser.getUid();
        relationsFilter(myId);

        //Login button listener
        Button buttonLogin = (Button)findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

        //Register button listener
        Button buttonRegister = (Button)findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

        //Profile button listener
        Button buttonProfile = (Button)findViewById(R.id.buttonProfile);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfile();
            }
        });

        //Edit profile button listener
        Button buttonEdit = (Button)findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditProfile();
            }
        });

        //Search matches button listener
        Button buttonStart = (Button)findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch();
            }
        });

        //Matches button listener
        Button buttonMatch = (Button)findViewById(R.id.buttonMatch);
        buttonMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMatch();
            }
        });
    }

    //Login activity redirect
    public void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //Register activity redirect
    public void openRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //Profile activity redirect
    public void openProfile(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    //Edit profile activity redirect
    public void openEditProfile(){
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    //Search matches activity redirect
    public void openSearch(){
        //pass an array to search activity
        Bundle b = new Bundle();
        b.putStringArrayList("relation_list", existingRelationsList);
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    //Matches activity redirect
    public void openMatch(){
        //pass an array to search activity
        Bundle b = new Bundle();
        b.putStringArrayList("like_list", likeList);
        Intent intent = new Intent(this, MatchActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    //due to firebase asynchronous nature it gets search relations here
    public void relationsFilter(final String myId){
        database = FirebaseDatabase.getInstance().getReference().child("relations");
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String uid = snapshot.child("uid").getValue().toString();
                String suid = snapshot.child("suid").getValue().toString();
                if(myId.equals(uid)) {
                    if(!existingRelationsList.contains(suid)) {
                        String like = snapshot.child("liked").getValue().toString();
                        Boolean isLiked = Boolean.parseBoolean(like);
                        if(isLiked=true){
                            likeList.add(suid);
                        }
                        existingRelationsList.add(suid);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}