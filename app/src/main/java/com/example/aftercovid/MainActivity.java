package com.example.aftercovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonMenu = (Button)findViewById(R.id.buttonMenu);
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenuActivity();
            }});

        Button buttonEditProfile = (Button)findViewById(R.id.buttonEditProfile);
        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditProfileActivity();
            }
        });

        Button buttonTest = (Button)findViewById(R.id.buttonTest);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTestActivity();
            }

        });

        Button buttonSwipe = (Button)findViewById(R.id.Swipebutton);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSwipeActivity();
            }

        });

        Button buttonMaches = (Button)findViewById(R.id.Matchesbutton);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMatchesActivity();
            }

        });
    }

    private void openMainMenuActivity() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    public void openTestActivity(){
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void openSwipeActivity(){
        Intent intent = new Intent(this, SwipeActivity.class);
        startActivity(intent);
    }
    public void openMatchesActivity(){
        Intent intent = new Intent(this, MachesActivity.class);
        startActivity(intent);
    }

<<<<<<< HEAD

    public void openEditProfile(){
        Intent intent = new Intent(this, EditProfile.class);
||||||| ff702c6
=======
    public void openEditProfile(){
        Intent intent = new Intent(this, EditProfile.class);
=======
    public void openEditProfileActivity(){
        Intent intent = new Intent(this, EditProfileActivity.class);
>>>>>>> f5379baeecdaac77f45bf6e2a6b4559e564eb330
        startActivity(intent);
    }

}
