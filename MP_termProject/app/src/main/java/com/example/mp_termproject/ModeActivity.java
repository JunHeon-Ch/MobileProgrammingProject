package com.example.mp_termproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        final TextView myClosetText = findViewById(R.id.mycloset_text);
        final TextView lookbookText = findViewById(R.id.lookbook_text);
        final TextView ourClosetText = findViewById(R.id.ourcloset_text);
        final TextView logoutText = findViewById(R.id.logout_text);
        myClosetText.setTextColor(Color.parseColor("#049edb"));
        myClosetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClosetText.setTextColor(Color.parseColor("#049edb"));
                lookbookText.setTextColor(Color.parseColor("#000000"));
                ourClosetText.setTextColor(Color.parseColor("#000000"));
                logoutText.setTextColor(Color.parseColor("#000000"));
                getSupportFragmentManager().beginTransaction().replace(R.id.container, myClosetFragment).commit();

            }
        });

        LinearLayout lookBookButton = findViewById(R.id.lookbookButton);
        lookBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClosetText.setTextColor(Color.parseColor("#000000"));
                lookbookText.setTextColor(Color.parseColor("#049edb"));
                ourClosetText.setTextColor(Color.parseColor("#000000"));
                logoutText.setTextColor(Color.parseColor("#000000"));
                getSupportFragmentManager().beginTransaction().replace(R.id.container, lookBookFragment).commit();
            }
        });

        LinearLayout ourClosetButton = findViewById(R.id.ourClosetButton);
        ourClosetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClosetText.setTextColor(Color.parseColor("#000000"));
                lookbookText.setTextColor(Color.parseColor("#000000"));
                ourClosetText.setTextColor(Color.parseColor("#049edb"));
                logoutText.setTextColor(Color.parseColor("#000000"));
                getSupportFragmentManager().beginTransaction().replace(R.id.container, ourClosetFragment).commit();
            }
        });

        LinearLayout logOutButton = findViewById(R.id.logoutButton);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClosetText.setTextColor(Color.parseColor("#000000"));
                lookbookText.setTextColor(Color.parseColor("#000000"));
                ourClosetText.setTextColor(Color.parseColor("#000000"));
                logoutText.setTextColor(Color.parseColor("#049edb"));
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