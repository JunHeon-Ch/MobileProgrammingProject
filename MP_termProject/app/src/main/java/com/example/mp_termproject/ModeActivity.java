package com.example.mp_termproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mp_termproject.lookbook.LookbookFragment;
import com.example.mp_termproject.mycloset.MyClosetFragment;
import com.example.mp_termproject.ourcloset.OurClosetFragment;
import com.example.mp_termproject.signup.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;


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

        LinearLayout myClosetButton = findViewById(R.id.myClosetButton);
        myClosetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, myClosetFragment).commit();

            }
        });

        LinearLayout lookBookButton = findViewById(R.id.lookbookButton);
        lookBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, lookBookFragment).commit();
            }
        });

        LinearLayout ourClosetButton = findViewById(R.id.ourClosetButton);
        ourClosetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, ourClosetFragment).commit();
            }
        });

        LinearLayout logOutButton = findViewById(R.id.logoutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    // 뒤로 가기 버튼을 누르면 main activity 로 넘어가는 것을 막아줌
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}