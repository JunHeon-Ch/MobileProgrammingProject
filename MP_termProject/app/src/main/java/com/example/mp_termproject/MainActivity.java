package com.example.mp_termproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // 유저가 firebase에 로그인이 안되어있을 때 sign activity로 이동
//        if (FirebaseAuth.getInstance().getCurrentUser()==null){
//            Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
//            startActivity(intent);
//        }

        // 로그아웃 버튼
        findViewById(R.id.mainLogoutButton).setOnClickListener(onClickListener);

        // 모드 Activity 이동
        Button modeButton = findViewById(R.id.modeButton);
        modeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModeActivity.class);
                startActivity(intent);
            }
        });
    }

    View.OnClickListener onClickListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mainLogoutButton:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

}
