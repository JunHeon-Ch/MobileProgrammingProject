package com.example.mp_termproject.ourcloset.request;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mp_termproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class RequestActivity extends AppCompatActivity {

    ImageView imageView;
    TextView nameText;
    TextView ageText;
    TextView phoneText;
    TextView addressText;
    Button requestButton;

    String targetUserId;
    Double latitude;
    Double longitude;
    String url;
    String name;
    String age;
    String address;
    String phone;
    Double[] requestNum;
    Double[] responseNum;

    FirebaseUser user;
    FirebaseFirestore db;
    DocumentReference docRefUserInfo;
    FirebaseStorage storage;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Request");
        setContentView(R.layout.activity_request);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        docRefUserInfo = db.collection("users").document(user.getUid());
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        imageView = findViewById(R.id.image);
        nameText = findViewById(R.id.name);
        ageText = findViewById(R.id.age);
        phoneText = findViewById(R.id.phonNumber);
        addressText = findViewById(R.id.address);
        requestButton = findViewById(R.id.requestButton);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        targetUserId = extras.getString("target");
        latitude = extras.getDouble("latitude");
        longitude = extras.getDouble("longitude");
        name = extras.getString("name");
        phone = extras.getString("phone");
        address = extras.getString("address");
        url = extras.getString("url");
        age = extras.getString("age").substring(0, 4);
        age = (Calendar.getInstance().get(Calendar.YEAR)- Integer.parseInt(age) + 1) + " 세";

        nameText.setText(name);
        ageText.setText(age);
        phoneText.setText(phone);
        addressText.setText(address);

        StorageReference pathReference = storageRef.child(url);

        Glide.with(this)
                .load(pathReference)
                .into(imageView);


    }
}
