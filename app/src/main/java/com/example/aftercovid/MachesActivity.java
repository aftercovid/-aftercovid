package com.example.aftercovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MachesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maches);

        Button buttonMessage = (Button)findViewById(R.id.buttonMessage);
        buttonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMessageActivity();
            }

        });
    }

    private void openMessageActivity() {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }
}