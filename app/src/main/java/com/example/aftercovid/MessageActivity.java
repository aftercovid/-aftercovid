package com.example.aftercovid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    ArrayList<String> matchInfo;
    EditText editMessage;
    Button buttonSend;
    DatabaseReference database;
    List<Messages> messages;
    ListView listViewMessages;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        editMessage = (EditText) findViewById(R.id.editMessage);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        matchInfo = getIntent().getStringArrayListExtra("match_info");
        final String[] matchArray = matchInfo.toArray(new String[0]);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MessageActivity.this, android.R.layout.simple_list_item_1, arrayList);
        listViewMessages = (ListView) findViewById(R.id.listviewmessages);
        listViewMessages.setAdapter(arrayAdapter);

        database = FirebaseDatabase.getInstance().getReference();

        database.child("messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String message = snapshot.child("message").getValue().toString();
                String uid = snapshot.child("senderId").getValue().toString();
                String suid = snapshot.child("receiverId").getValue().toString();
                //Log.i("KOMUNIKAT",matchArray[0]+"  "+matchArray[1]+" "+uid+" "+suid);
                if(((matchArray[0].equals(uid))&&(matchArray[1].equals(suid)))||((matchArray[0].equals(suid))&&(matchArray[1].equals(uid)))){
                    //Log.i("KOMUNIKAT",matchArray[0]+"  "+matchArray[1]);
                    arrayList.add(message);
                    arrayAdapter.notifyDataSetChanged();
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

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String test = "TEST";
                database = FirebaseDatabase.getInstance().getReference("messages");
                String message = editMessage.getText().toString();
                Messages messages = new Messages(test,matchArray[0],matchArray[1],message);
                Log.i("KOMUNIKAT",matchArray[0]);
                String newId = database.push().getKey();
                database.child(newId).setValue(messages);
            }
        });

    }
}