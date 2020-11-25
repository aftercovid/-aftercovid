package com.example.aftercovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Button buttonInterests = (Button)findViewById(R.id.buttonInterests);
        buttonInterests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInterests();
            }
        });

        Button buttonPictures = (Button)findViewById(R.id.buttonPictures);
        buttonPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPictures();
            }
        });

    }
    public void openPictures(){
        Intent intent = new Intent(this, Pictures.class);
        startActivity(intent);
    }
    public void openInterests(){
        Intent intent = new Intent(this, Interests.class);
        startActivity(intent);
    }
}