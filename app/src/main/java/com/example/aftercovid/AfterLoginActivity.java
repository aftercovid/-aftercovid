package com.example.aftercovid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AfterLoginActivity extends AppCompatActivity {

    DatabaseReference database;
    ArrayList<String> existingRelationsList =  new ArrayList<>();
    ArrayList<String> likeList =  new ArrayList<>();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        //get user id
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        final String myId = fuser.getUid();
        relationsFilter(myId);
        mAuth = FirebaseAuth.getInstance();

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
        //SignOut button listener
        Button buttonSignOut = (Button)findViewById(R.id.buttonSignOut);
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
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
    //przekazuje liste polubionych uzytkownikow z relationsFilter
    public void openMatch(){
        //pass an array to search activity
        Bundle b = new Bundle();
        b.putStringArrayList("like_list", likeList);
        Intent intent = new Intent(this, MatchActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    //due to firebase asynchronous nature it gets search relations here
    //z bazy relacji szuka rekordow zalogowanego uzytkownika i dodaje kazdego polubionego do listy, przekazuje ja do innego activity
    public void relationsFilter(final String myId){
        database = FirebaseDatabase.getInstance().getReference().child("relations");
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String uid = snapshot.child("uid").getValue().toString();
                String suid = snapshot.child("suid").getValue().toString();
                if(myId.equals(uid)) {
                    //sprawdz czy lista juz nie zawiera danego usera
                    if(!existingRelationsList.contains(suid)) {
                        String like = snapshot.child("liked").getValue().toString();
                        Boolean isLiked = Boolean.parseBoolean(like);
                        //jesli user jest polubiony dodaj do osobnej listy z samymi likami
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

    //sign out, redirect to welcome screen
    private void signOut() {
        mAuth.signOut();
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

}