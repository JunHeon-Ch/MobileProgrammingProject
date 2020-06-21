package com.example.mp_termproject.ourcloset.viewinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.mp_termproject.R;
import com.example.mp_termproject.mycloset.dto.ImageDTO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class SenderActivity extends AppCompatActivity {
    private static final String TAG = "SenderActivity";

    FirebaseUser user;
    FirebaseFirestore db;
    DocumentReference docRefUserInfo;

    String sender;
    String receiver;
    String imgNum;
    String reqNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        docRefUserInfo = db.collection("request").document(user.getUid());

        // 유저 정보접근
        db.collection("request").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String doc = document.getId().toString();
                                String[] temp = doc.split(" ");

                                sender = temp[0];
                                receiver = temp[1];
                                imgNum = temp[2];

                                Map<String, Object> temp2 = document.getData();
                                reqNum = (String) temp2.get("reqNum");


                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}
