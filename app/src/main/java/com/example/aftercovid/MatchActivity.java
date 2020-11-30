package com.example.aftercovid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchActivity extends AppCompatActivity {

    ArrayList<String> likeList;
    ListView listViewMatch;
    ArrayList<String> arrayList = new ArrayList<>();
    DatabaseReference database;
    List<Match> matches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        likeList = getIntent().getStringArrayListExtra("like_list");
        Log.i("ciekawe", Arrays.toString(likeList.toArray()));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MatchActivity.this, android.R.layout.simple_list_item_1,arrayList);
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        final String myId = fuser.getUid();
        listViewMatch = (ListView) findViewById(R.id.listviewmatches);
        listViewMatch.setAdapter(arrayAdapter);
        database = FirebaseDatabase.getInstance().getReference();
        matches = new ArrayList<Match>();

        database.child("relations").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String uid = snapshot.child("uid").getValue().toString();
                Log.i("JEDEN", Arrays.toString(likeList.toArray()));
                if(likeList.contains(uid)){
                    Log.i("DWA", Arrays.toString(likeList.toArray()));
                    String suid = snapshot.child("suid").getValue().toString();
                    if(myId.equals(suid)){
                        String like = snapshot.child("liked").getValue().toString();
                        Log.i("TRZY", like);
                        if(like=="true"){
                            Log.i("CZTERY", like);
                            arrayList.add(uid);
                            arrayAdapter.notifyDataSetChanged();
                        }
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


    }
}