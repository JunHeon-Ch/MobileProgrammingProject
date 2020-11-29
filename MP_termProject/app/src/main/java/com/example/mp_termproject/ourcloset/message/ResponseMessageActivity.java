package com.example.mp_termproject.ourcloset.message;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mp_termproject.R;
import com.example.mp_termproject.ourcloset.dto.RequestDTO;
import com.example.mp_termproject.ourcloset.dto.ResponseDTO;
import com.example.mp_termproject.ourcloset.gps.ShowMapWithDistanceActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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
    LinearLayout layout;
    LinearLayout image;
    LinearLayout text;

    ArrayList<ResponseDTO> responseDTOArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Response message");
        setContentView(R.layout.activity_response_message);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        layout = findViewById(R.id.layout);
        image = findViewById(R.id.image);
        text = findViewById(R.id.text);

        messageLayout = findViewById(R.id.messageLayout);

        responseDTOArrayList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        responseDTOArrayList.clear();

        db
                .collection("responses")
                .document(user.getUid())
                .collection("response")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();

                                String senderName = (String) data.get("senderName");
                                String senderAge = (String) data.get("senderAge");
                                String senderPhoneNumber = (String) data.get("senderPhoneNumber");
                                String senderAddress = (String) data.get("senderAddress");
                                Double senderLatitude = (Double) data.get("senderLatitude");
                                Double senderLongitude = (Double) data.get("senderLongitude");
                                String url = (String) data.get("url");

                                ResponseDTO responseDTO = new ResponseDTO(senderName, senderAge, senderAddress, senderPhoneNumber, senderLatitude, senderLongitude, url);

                                responseDTOArrayList.add(responseDTO);
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
        while (i < responseDTOArrayList.size()) {
            int height = layout.getHeight();

            final StorageReference pathReference = storageRef.child(responseDTOArrayList.get(i).getUrl());

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
            name.setText(responseDTOArrayList.get(i).getSenderName());
            textLayout.addView(name);

            layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 10, 20, 10);

            TextView age = new TextView(textLayout.getContext());
            age.setLayoutParams(layoutParams);
            age.setText(responseDTOArrayList.get(i).getSenderAge());
            textLayout.addView(age);

            TextView phone = new TextView(textLayout.getContext());
            phone.setLayoutParams(layoutParams);
            phone.setText(responseDTOArrayList.get(i).getSenderPhoneNumber());
            textLayout.addView(phone);

            TextView address = new TextView(textLayout.getContext());
            address.setLayoutParams(layoutParams);
            address.setText(responseDTOArrayList.get(i).getSenderAddress());
            textLayout.addView(address);

            final int index = i;
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ResponseMessageActivity.this);

                    String[] option = {"전화 걸기", "지도로 보기"};
                    builder.setItems(option, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int pos) {
                            Intent intent;
                            Bundle bundle = new Bundle();
                            switch (pos) {
                                case 0:
                                    intent = new Intent(Intent.ACTION_DIAL,
                                            Uri.parse("tel:" + responseDTOArrayList.get(index).getSenderPhoneNumber()));
                                    startActivity(intent);

                                    break;

                                case 1:
                                    double latitude = responseDTOArrayList.get(index).getSenderLatitude();
                                    double longitude = responseDTOArrayList.get(index).getSenderLongitude();
                                    String name = responseDTOArrayList.get(index).getSenderName();
                                    String address = responseDTOArrayList.get(index).getSenderAddress();

                                    intent = new Intent(ResponseMessageActivity.this, ShowMapWithDistanceActivity.class);
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

            final ResponseDTO responseDTO = responseDTOArrayList.get(i);
            final int index2 = i;
            linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ResponseMessageActivity.this);
                    alert.setMessage("메시지를 삭제하겠습니까?");


                    alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            final CollectionReference itemsRef = db.collection("responses").document(user.getUid()).collection("response");
                            Query query = itemsRef.whereEqualTo("url", responseDTO.getUrl());
                            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (DocumentSnapshot document : task.getResult()) {
                                            itemsRef.document(document.getId()).delete();
                                            responseDTOArrayList.remove(index2);
                                            floatTotalMessage();
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                            Log.d("test", "test " + responseDTO.getUrl());
                            onStart();
                            Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            // 삭제
                        }
                    });

                    alert.show();

                    return true;
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
