package com.example.aftercovid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    //stores potential partner's id
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> existingRelationsList;
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

    ImageView imageViewUser;

    private FirebaseStorage storage;
    private StorageReference storageReferenceImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        existingRelationsList = getIntent().getStringArrayListExtra("relation_list");
        Log.i("ciekawe", Arrays.toString(existingRelationsList.toArray()));
        relations = new ArrayList<Relations>();
        i=-1;
        textName = (TextView) findViewById(R.id.textName);
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        final String myId = fuser.getUid();
        //relationsFilter(myId);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, arrayList);
        users = FirebaseDatabase.getInstance().getReference().child("users");

        imageViewUser = findViewById(R.id.imageViewUser);

        storage = FirebaseStorage.getInstance();



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

                //Log.i("CCCCCCC", Arrays.toString(existingRelationsList.toArray()));

                //check if potential partner gender matches the user's preference
                if((myPreference.equals(gender))&&(!myId.equals(id))&&(!existingRelationsList.contains(id))){
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

        storageReferenceImage = storage.getReference("images/"+userId);

        storageReferenceImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                getPicture(uri);
            }
        });

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

//    public void relationsFilter(final String myId){
//        database = FirebaseDatabase.getInstance().getReference().child("relations");
//        database.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                String uid = snapshot.child("uid").getValue().toString();
//                String suid = snapshot.child("suid").getValue().toString();
//                if(myId.equals(uid)) {
//                    existingRelationsList.add(suid);
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void getPicture(final Uri uri){
        String url = uri.toString();
        Glide.with(this).load(url).into(imageViewUser);
    }

}