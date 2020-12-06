package com.example.aftercovid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
    String sId;
    ArrayList<String> matchInfo =  new ArrayList<>();

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
        listViewMatch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //gdy klikniemy kogos z listy to nas przeniesie do wiadomosci
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //handleMatch(myId,arrayList.get(position),true);
                matchInfo.add(myId);
                matchInfo.add(arrayList.get(position));
                //Log.i("KOMUNIKAT","  BUUUU       ");
                //Toast.makeText(MatchActivity.this, "You have clicked: " + arrayList.get(position), Toast.LENGTH_SHORT).show();
                Bundle b = new Bundle();
                b.putStringArrayList("match_info", matchInfo);
                Intent intent = new Intent(MatchActivity.this, MessageActivity.class);
                intent.putExtras(b);
                startActivity(intent);
                matchInfo.clear();
            }
        });
        database = FirebaseDatabase.getInstance().getReference();
        matches = new ArrayList<Match>();

        //na podstawie likelisty z afterloginacticity szuka matchow
        database.child("relations").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String uid = snapshot.child("uid").getValue().toString();
                if(likeList.contains(uid)){
                    String suid = snapshot.child("suid").getValue().toString();
                    sId = suid;
                    if(myId.equals(suid)){
                        String like = snapshot.child("liked").getValue().toString();
                        if(like=="true"){
                            arrayList.add(uid);
                            arrayAdapter.notifyDataSetChanged();
                            //handleMatch(myId,uid,false);
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

    //reszta chyba niepotrzebna juz

    @Override
    public void onResume(){
        super.onResume();
//        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
//        final String myId = fuser.getUid();
//        Log.i("KOMUNIKAT", "WTFF1231233F");
//        handleMatch(myId,sId);
    }

//    public void handleMatch(final String myId, final String suid,final Boolean get){
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//
//        database.child("matches").orderByChild("uid").equalTo(myId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
//                    db.child("matches").orderByChild("suid").equalTo(suid).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if(!snapshot.exists()){
//                                addMatch(myId,suid);
//                            }
//                            else{
//                                if(get==true){
//                                String matchId = snapshot.getKey().toString();
//                                matchInfo.add(matchId);
//                                matchInfo.add(myId);
//                                matchInfo.add(suid);}
//                            }
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                        }
//                    });
//                }
//                else{
//                    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
//                    db.child("matches").orderByChild("suid").equalTo(myId).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if(snapshot.exists()){
//                                final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
//                                db.child("matches").orderByChild("uid").equalTo(suid).addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        if(!snapshot.exists()){
//                                            addMatch(myId,suid);
//                                        }
//                                        else{
//                                            String matchId = db.child("matches").child("matchId").getKey().toString();
//                                            matchInfo.add(matchId);
//                                            matchInfo.add(myId);
//                                            matchInfo.add(suid);
//                                        }
//                                    }
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//                                    }
//                                });
//                            }
//                            else{
//                                addMatch(myId,suid);
//                            }
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    public void handleMatchv2(final String myId, final String suid){
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//        database.child("matches").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                if(!snapshot.exists()){
//                    Log.i("KOMUNIKAT","  BUUUU       ");
//                    addMatch(myId, suid);
//                }
//                String lol = snapshot.getValue().toString();
//                Log.i("KOMUNIKAT",lol);
//                //String matchId = snapshot.child("matchId").getValue().toString();
//                String uId = snapshot.child("uid").getValue().toString();
//                String suId = snapshot.child("suid").getValue().toString();
//                if((myId.equals(uId))&&(suid.equals(suId))){
//                    matchInfo.add(lol);
//                    matchInfo.add(myId);
//                    matchInfo.add(suid);
//                    Log.i("KOMUNIKAT",uId + "     XDDDDDDDDD    " + suId + "   " + lol);
//                }
//                else if((myId.equals(suId))&&(suid.equals(uId))){
//                    matchInfo.add(lol);
//                    matchInfo.add(myId);
//                    matchInfo.add(suid);
//                    Log.i("KOMUNIKAT",uId + "     xdddddd    " + suId);
//                }
//                else {
//                    Log.i("KOMUNIKAT",uId + "         " + suId);
//                    addMatch(myId, suid);
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
//
//    public void addMatch(final String myId, final String suid){
//        DatabaseReference databaseMatches;
//        databaseMatches = FirebaseDatabase.getInstance().getReference("matches");
//        String newId = database.push().getKey();
//        Match match = new Match(newId,myId,suid);
//        databaseMatches.child(newId).setValue(match);
//    }
}