package com.example.aftercovid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.List;

public class InterestsActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    List<String> array = new ArrayList<String>();
    ArrayList<String> arrayList = new ArrayList<>();
    DatabaseReference db;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        listView = findViewById(R.id.listView_data);
        //arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,array);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,arrayList);
        listView.setAdapter(arrayAdapter);
        db = FirebaseDatabase.getInstance().getReference();
        //get all interests from db
        db.child("interests").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String interest = snapshot.child("interest").getValue().toString();
                arrayList.add(interest);
                arrayAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.item_done){
            String itemSelected = "Selected items: \n";
            for(int i=0;i<listView.getCount();i++){
                if(listView.isItemChecked(i)){
                    itemSelected+=listView.getItemAtPosition(i)+ "\n";
                    //array.add(listView.getItemAtPosition(i).toString());
                }
            }
            Toast.makeText(this,itemSelected,Toast.LENGTH_SHORT).show();
//            databaseReference = FirebaseDatabase.getInstance().getReference("user_interests");
//            FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
//            final String uid = fuser.getUid();
//            for(int i=0;i<array.size();i++){
//                String interest = array.get(i);
//                UserInterest userInterest = new UserInterest(uid,interest);
//                String newId = databaseReference.push().getKey();
//                databaseReference.child(newId).setValue(userInterest);
//            }
            //array.clear();
        }
        return super.onOptionsItemSelected(item);
    }
}