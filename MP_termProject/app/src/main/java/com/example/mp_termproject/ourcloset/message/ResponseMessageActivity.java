package com.example.mp_termproject.ourcloset.message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mp_termproject.R;
import com.example.mp_termproject.mycloset.dto.ImageDTO;
import com.example.mp_termproject.ourcloset.dto.RequestDTO;
import com.example.mp_termproject.ourcloset.dto.ResponseDTO;
import com.example.mp_termproject.ourcloset.gps.ShowMapWithDistanceActivity;
import com.example.mp_termproject.ourcloset.request.RequestActivity;
import com.example.mp_termproject.ourcloset.viewinfo.ViewClosetInfoActivity;
import com.example.mp_termproject.signup.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

public class ResponseMessageActivity extends AppCompatActivity {

    private static final String TAG = "ResponseMessageActivity";

    FirebaseUser user;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;

    LinearLayout messageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Response message");
        setContentView(R.layout.activity_response_message);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        messageLayout = findViewById(R.id.messageLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();

        db.collection("responses").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (user.getUid().equals(document.getId())) {
                                    db
                                            .collection("responses")
                                            .document(document.getId())
                                            .collection("response")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            Map<String, Object> data = document.getData();

                                                            String targetName = (String) data.get("senderName");
                                                            String targetAge = (String) data.get("sendertAge");
                                                            String targetPhoneNumber = (String) data.get("senderPhoneNumber");
                                                            String targetAddress = (String) data.get("senderAddress");
                                                            Double targetLatitude = (Double) data.get("senderLatitude");
                                                            Double targetLongitude = (Double) data.get("senderLongitude");
                                                            String url = (String) data.get("url");

                                                            ResponseDTO responseDTO = new ResponseDTO(targetName, targetAge, targetPhoneNumber, targetAddress, targetLatitude, targetLongitude, url);

                                                            messageLayout.removeAllViews();
                                                            floatTotalMessage(responseDTO);
                                                        }
                                                    } else {
                                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                                    }
                                                }
                                            });
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void floatTotalMessage(final ResponseDTO responseDTO) {
        LinearLayout linearLayout = null;

        int height = findViewById(R.id.layout).getHeight();

        final StorageReference pathReference = storageRef.child(responseDTO.getUrl());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, height);

        linearLayout = new LinearLayout(messageLayout.getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(layoutParams);

        messageLayout.addView(linearLayout);

        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                findViewById(R.id.image).getWidth(), height);
        imageParams.setMargins(5, 5, 5, 5);

        ImageView imageView = new ImageView(linearLayout.getContext());
        imageView.setLayoutParams(imageParams);

        Glide.with(linearLayout)
                .load(pathReference)
                .into(imageView);
        linearLayout.addView(imageView);

        layoutParams = new LinearLayout.LayoutParams(
                findViewById(R.id.text).getWidth(), height);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

        LinearLayout textLayout = new LinearLayout(linearLayout.getContext());
        textLayout.setOrientation(LinearLayout.VERTICAL);
        textLayout.setLayoutParams(layoutParams);

        linearLayout.addView(textLayout);

        layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);

        TextView name = new TextView(textLayout.getContext());
        name.setLayoutParams(layoutParams);
        name.setText(responseDTO.getSenderName());
        textLayout.addView(name);

        TextView age = new TextView(textLayout.getContext());
        age.setLayoutParams(layoutParams);
        age.setText(responseDTO.getSenderAge());
        textLayout.addView(age);

        TextView phone = new TextView(textLayout.getContext());
        phone.setLayoutParams(layoutParams);
        phone.setText(responseDTO.getSenderPhoneNumber());
        textLayout.addView(phone);

        TextView address = new TextView(textLayout.getContext());
        address.setLayoutParams(layoutParams);
        address.setText(responseDTO.getSenderAddress());
        textLayout.addView(address);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                String[] option = {"전화 걸기", "지도로 보기"};
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        Intent intent;
                        Bundle bundle = new Bundle();
                        switch (pos) {
                            case 0:
                                intent = new Intent(Intent.ACTION_DIAL,
                                        Uri.parse("tel:" + responseDTO.getSenderPhoneNumber()));
                                startActivity(intent);

                                break;

                            case 1:
                                double latitude = responseDTO.getSenderLatitude();
                                double longitude = responseDTO.getSenderLongitude();
                                String name = responseDTO.getSenderName();
                                String address = responseDTO.getSenderAddress();

                                intent = new Intent(getApplicationContext(), ShowMapWithDistanceActivity.class);
                                bundle.putDouble("latitude", latitude);
                                bundle.putDouble("longitude", longitude);
                                bundle.putString("name", name);
                                bundle.putString("address", address);
                                intent.putExtras(bundle);
                                startActivity(intent);

                                break;

                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}
