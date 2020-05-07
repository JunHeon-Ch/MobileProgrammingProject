package com.example.mp_termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModeActivity extends AppCompatActivity {
    MyClosetFragment myClosetFragment;
    LookBookFragment lookBookFragment;
    OurClosetFragment ourClosetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        myClosetFragment = new MyClosetFragment();
        lookBookFragment = new LookBookFragment();
        ourClosetFragment = new OurClosetFragment();

        Button myClosetButton = findViewById(R.id.myClosetButton);
        myClosetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.modeFragment, myClosetFragment).commit();
            }
        });

        Button lookBookButton = findViewById(R.id.lookBookButton);
        lookBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.modeFragment, lookBookFragment).commit();
            }
        });

        Button ourClosetButton = findViewById(R.id.ourClosetButton);
        ourClosetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.modeFragment, ourClosetFragment).commit();
            }
        });


    }
}