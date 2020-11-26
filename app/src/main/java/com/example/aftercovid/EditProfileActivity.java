package com.example.aftercovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Button buttonInterests = (Button)findViewById(R.id.buttonInterests);
        buttonInterests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInterestsActivity();
            }
        });

        Button buttonPictures = (Button)findViewById(R.id.buttonPictures);
        buttonPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPicturesActivity();
            }
        });

    }
    public void openPicturesActivity(){
        Intent intent = new Intent(this, PicturesActivity.class);
        startActivity(intent);
    }
    public void openInterestsActivity(){
        Intent intent = new Intent(this, InterestsActivity.class);
        startActivity(intent);
    }
}