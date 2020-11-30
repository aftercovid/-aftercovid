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
import com.google.firebase.database.ValueEventListener;

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
                if(likeList.contains(uid)){
                    String suid = snapshot.child("suid").getValue().toString();
                    if(myId.equals(suid)){
                        String like = snapshot.child("liked").getValue().toString();
                        if(like=="true"){
                            arrayList.add(uid);
                            arrayAdapter.notifyDataSetChanged();
                            handleMatch(myId,uid);
                            Log.i("KOMUNIKAT","PIERWSZY");
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

    public void handleMatch(final String myId, final String suid){

        Log.i("KOMUNIKAT","DRUGI");
        Log.i("KOMUNIKAT",myId + "          "+suid);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//        database.child("matches").orderByChild("uid").equalTo(myId).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                String test = snapshot.child("uid").getValue().toString();
//                Log.i("KOMUNIKAT",test);
//                if(snapshot.exists()){
//                    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
//                    db.child("matches").orderByChild("suid").equalTo(suid).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
////                if(test==myId){
////                    Log.i("KOMUNIKAT","NO KURWAAAAAAAAAAAAA");
////                    Boolean test1 = snapshot.child("uid").equalTo(myId);
////                    String test2 = test1.toString();
////                    Log.i("KOMUNIKAT", test2);
////                }
//
//
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
        database.child("matches").orderByChild("uid").equalTo(myId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    Log.i("LEWO","Pierwszy, MID ISTNIEJE");

                    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                    db.child("matches").orderByChild("suid").equalTo(suid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){

                                Log.i("KOMUNIKAT","TID TEZ NIE ISTIEJE, TWORZYMY");

                                addMatch(myId,suid);
                            }

                            Log.i("KOMUNIKAT","TID TEZ ISTNIEJE, PASS");

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    Log.i("KOMUNIKAT","SZOSTY");

                }
                else{

                    Log.i("PRAWO"," MID NIE ISTNIEJE");

                    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                    db.child("matches").orderByChild("suid").equalTo(myId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){

                                Log.i("KOMUNIKAT","DRUGI MID ISTNIEJE");

                                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                                db.child("matches").orderByChild("uid").equalTo(suid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(!snapshot.exists()){

                                            Log.i("KOMUNIKAT","TID NIE ISTNIEJE,TWORZYMY");

                                            addMatch(myId,suid);
                                        }

                                        Log.i("KOMUNIKAT","TID ISTNIEJE, PASS");

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            else{
                                addMatch(myId,suid);
                                Log.i("KOMUNIKAT","DRUGI MID NIE ISTNIEJE, TWORZYMY");
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addMatch(final String myId, final String suid){
        Log.i("KOMUNIKAT","DWUNASTY");

        DatabaseReference databaseMatches;
        databaseMatches = FirebaseDatabase.getInstance().getReference("matches");
        Match match = new Match(myId,suid);
        String newId = database.push().getKey();
        databaseMatches.child(newId).setValue(match);
    }
}