package com.example.mp_termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 유저가 firebase에 로그인이 안되어있을 때 sign activity로 이동
        if (FirebaseAuth.getInstance().getCurrentUser()==null){
            Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
            startActivity(intent);
        }
        findViewById(R.id.logoutButton).setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.logoutButton:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
