package com.example.mp_termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mp_termproject.lookbook.LookbookFragment;
import com.example.mp_termproject.mycloset.MyClosetFragment;
import com.example.mp_termproject.ourcloset.OurClosetFragment;

public class ModeActivity extends AppCompatActivity {
    MyClosetFragment myClosetFragment;
    LookbookFragment lookBookFragment;
    OurClosetFragment ourClosetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        myClosetFragment = new MyClosetFragment();
        lookBookFragment = new LookbookFragment();
        ourClosetFragment = new OurClosetFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, myClosetFragment).commit();

        Button myClosetButton = findViewById(R.id.myClosetButton);
        myClosetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, myClosetFragment).commit();

            }
        });

        Button lookBookButton = findViewById(R.id.lookBookButton);
        lookBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, lookBookFragment).commit();
            }
        });

        Button ourClosetButton = findViewById(R.id.ourClosetButton);
        ourClosetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, ourClosetFragment).commit();
            }
        });
    }
}