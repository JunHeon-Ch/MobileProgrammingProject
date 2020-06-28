package com.example.mp_termproject.ourcloset.request;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mp_termproject.R;
import com.example.mp_termproject.mycloset.add.MyClosetAddActivity;
import com.example.mp_termproject.ourcloset.dto.RequestDTO;
import com.example.mp_termproject.ourcloset.dto.ResponseDTO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Map;

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

    Double latitude2;
    Double longitude2;
    String url2;
    String name2;
    String age2;
    String address2;
    String phone2;
    String UIDB;


    Double requestNum;
    Double responseNum;

    ResponseDTO responseDTO;
    RequestDTO requestDTO;

    FirebaseUser user;
    FirebaseFirestore db;
    DocumentReference docRefUserInfo;
    FirebaseStorage storage;
    StorageReference storageRef;
    DocumentReference docRef;

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
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        targetUserId = extras.getString("target");
        latitude = extras.getDouble("latitude");
        longitude = extras.getDouble("longitude");
        requestNum = extras.getDouble("request");
        name = extras.getString("name");
        phone = extras.getString("phone");
        address = extras.getString("address");
        url = extras.getString("url");
        age = extras.getString("age").substring(0, 4);
        age = (Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(age) + 1) + " 세";

        latitude2 = extras.getDouble("latitudeB");
        longitude2 = extras.getDouble("longitudeB");
        responseNum = extras.getDouble("response");
        name2 = extras.getString("nameB");
        phone2 = extras.getString("phoneB");
        address2 = extras.getString("addressB");
        age2 = extras.getString("ageB").substring(0, 4);
        age2 = (Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(age2) + 1) + " 세";
        UIDB = extras.getString("UIDB");
        requestDTO = new RequestDTO(name2, age2, address2, phone2, latitude2, longitude2, url);
        responseDTO = new ResponseDTO(name, age, address, phone, latitude, longitude, url);

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // request DTO 저장
                db.collection("requests").document(user.getUid()).collection("request").document(requestNum.toString()).set(requestDTO)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                // response DTO 저장
                db.collection("responses").document(user.getUid()).collection("response").document(responseNum.toString()).set(responseDTO)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                requestNum++;
                docRef = db.collection("users").document(user.getUid());
                docRef
                        .update("requestNum", requestNum)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                docRef = db.collection("users").document(UIDB);
                docRef
                        .update("responseNum", responseNum)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                finish();
            }
        });





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
