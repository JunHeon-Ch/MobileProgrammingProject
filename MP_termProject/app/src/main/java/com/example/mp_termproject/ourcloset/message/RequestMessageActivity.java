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

public class RequestMessageActivity extends AppCompatActivity {

    private static final String TAG = "RequestMessageActivity";

    FirebaseUser user;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;

    LinearLayout messageLayout;
    LinearLayout layout;
    LinearLayout image;
    LinearLayout text;

    ArrayList<RequestDTO> requestDTOArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Request message");
        setContentView(R.layout.activity_request_message);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        layout = findViewById(R.id.layout);
        image = findViewById(R.id.image);
        text = findViewById(R.id.text);

        messageLayout = findViewById(R.id.messageLayout);

        requestDTOArrayList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestDTOArrayList.clear();

        db
                .collection("requests")
                .document(user.getUid())
                .collection("request")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();

                                String targetName = (String) data.get("targetName");
                                String targetAge = (String) data.get("targetAge");
                                String targetPhoneNumber = (String) data.get("targetPhoneNumber");
                                String targetAddress = (String) data.get("targetAddress");
                                Double targetLatitude = (Double) data.get("targetLatitude");
                                Double targetLongitude = (Double) data.get("targetLongitude");
                                String url = (String) data.get("url");

                                RequestDTO requestDTO = new RequestDTO(targetName, targetAge, targetAddress, targetPhoneNumber, targetLatitude, targetLongitude, url);

                                requestDTOArrayList.add(requestDTO);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                        floatTotalMessage();
                    }
                });
    }

    private void floatTotalMessage() {
        LinearLayout linearLayout = null;
        messageLayout.removeAllViews();

        int i = 0;
        while (i < requestDTOArrayList.size()) {
            int height = layout.getHeight();

            final StorageReference pathReference = storageRef.child(requestDTOArrayList.get(i).getUrl());

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, height);

            linearLayout = new LinearLayout(messageLayout.getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(layoutParams);

            messageLayout.addView(linearLayout);

            int width = image.getWidth();
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    width, height);
            imageParams.setMargins(5, 5, 5, 5);

            ImageView imageView = new ImageView(linearLayout.getContext());
            imageView.setLayoutParams(imageParams);

            Glide.with(linearLayout)
                    .load(pathReference)
                    .into(imageView);
            linearLayout.addView(imageView);

            width = text.getWidth();
            layoutParams = new LinearLayout.LayoutParams(
                    width, height);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

            LinearLayout textLayout = new LinearLayout(linearLayout.getContext());
            textLayout.setOrientation(LinearLayout.VERTICAL);
            textLayout.setLayoutParams(layoutParams);

            linearLayout.addView(textLayout);

            layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 30, 20, 10);

            TextView name = new TextView(textLayout.getContext());
            name.setLayoutParams(layoutParams);
            name.setText(requestDTOArrayList.get(i).getTargetName());
            textLayout.addView(name);

            layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 10, 20, 10);

            TextView age = new TextView(textLayout.getContext());
            age.setLayoutParams(layoutParams);
            age.setText(requestDTOArrayList.get(i).getTargetAge());
            textLayout.addView(age);

            TextView phone = new TextView(textLayout.getContext());
            phone.setLayoutParams(layoutParams);
            phone.setText(requestDTOArrayList.get(i).getTargetPhoneNumber());
            textLayout.addView(phone);

            TextView address = new TextView(textLayout.getContext());
            address.setLayoutParams(layoutParams);
            address.setText(requestDTOArrayList.get(i).getTargetAddress());
            textLayout.addView(address);

            final int index = i;
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(RequestMessageActivity.this);

                    String[] option = {"전화 걸기", "지도로 보기"};
                    builder.setItems(option, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int pos) {
                            Intent intent;
                            Bundle bundle = new Bundle();
                            switch (pos) {
                                case 0:
                                    intent = new Intent(Intent.ACTION_DIAL,
                                            Uri.parse("tel:" + requestDTOArrayList.get(index).getTargetPhoneNumber()));
                                    startActivity(intent);

                                    break;

                                case 1:
                                    double latitude = requestDTOArrayList.get(index).getTargetLatitude();
                                    double longitude = requestDTOArrayList.get(index).getTargetLongitude();
                                    String name = requestDTOArrayList.get(index).getTargetName();
                                    String address = requestDTOArrayList.get(index).getTargetAddress();

                                    intent = new Intent(RequestMessageActivity.this, ShowMapWithDistanceActivity.class);
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

            i++;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        messageLayout.removeAllViews();
    }
}
