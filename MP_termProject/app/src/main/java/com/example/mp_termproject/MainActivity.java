package com.example.mp_termproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myClosetButton = findViewById(R.id.myClosetButton);
        myClosetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // GpsActivity.class -> 나의옷장클래스로 변경
                Intent intent = new Intent(getApplicationContext(), ShareColsetActivity.class);
                startActivity(intent);
            }
        });

        Button sharedClosetButton = findViewById(R.id.sharedClosetButton);
        sharedClosetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShareColsetActivity.class);

                startActivity(intent);
            }
        });
    }
}
