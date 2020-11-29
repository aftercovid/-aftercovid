package com.example.aftercovid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    //stores potential partner's id
    ArrayList<String> arrayList = new ArrayList<>();
    //idk if i need 2 of them
    DatabaseReference users;
    DatabaseReference database;
    //stores shown partner's it
    String userId;
    TextView textName;
    //array index
    Integer i;
    //user gender preference
    String myPreference;
    //to add user decision to db
    List<Relations> relations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        relations = new ArrayList<Relations>();
        i=-1;

        textName = (TextView) findViewById(R.id.textName);

        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        final String myId = fuser.getUid();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, arrayList);

        users = FirebaseDatabase.getInstance().getReference().child("users");

        //get current user gender preference from db
        users.child(myId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myPreference = snapshot.child("preference").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //main match function that shows potential partner
        users.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String id = snapshot.getKey();
                String gender = snapshot.child("gender").getValue().toString();

                //ADD FILTER HERE
                //database = FirebaseDatabase.getInstance().getReference().child("relations").child("uid");

                //check if potential partner gender matches the user's preference
                if((myPreference.equals(gender))&&(!myId.equals(id))){
                    //if true, add id or potential partner to an array
                    arrayList.add(id);
                    arrayAdapter.notifyDataSetChanged();

                    //show first candidate on the screen
                    if(arrayAdapter.getCount()==1) {
                        nextUser();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //idk what does it do
                arrayAdapter.notifyDataSetChanged();
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

        //next button
        Button buttonNext = (Button)findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i!=arrayAdapter.getCount()-1) {
                    nextUser();
                }
            }
        });


        //yes button that adds a relation to a db
        Button buttonYes = (Button)findViewById(R.id.buttonYes);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId = arrayList.get(i);
                Relations relations = new Relations(myId, userId, true);
                database = FirebaseDatabase.getInstance().getReference("relations");
                String newId = database.push().getKey();
                database.child(newId).setValue(relations);
                if(i!=arrayAdapter.getCount()-1) {
                    nextUser();
                }
            }
        });

        //no button that adds a relation to a db
        Button buttonNo = (Button)findViewById(R.id.buttonNo);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId = arrayList.get(i);
                Relations relations = new Relations(myId, userId, false);
                database = FirebaseDatabase.getInstance().getReference("relations");
                String newId = database.push().getKey();
                database.child(newId).setValue(relations);
                if(i!=arrayAdapter.getCount()-1) {
                    nextUser();
                }
            }
        });
    }

    //iterate counter and show next user in the array
    public void nextUser(){
        i++;
        userId = arrayList.get(i);
        //gets user name by his id
        database = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("firstName").getValue().toString();
                textName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}