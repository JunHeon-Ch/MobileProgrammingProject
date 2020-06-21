//package com.example.mp_termproject.ourcloset.viewinfo;
//
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.mp_termproject.R;
//import com.example.mp_termproject.mycloset.dto.ImageDTO;
//import com.example.mp_termproject.ourcloset.dto.InfoDTO;
//import com.example.mp_termproject.ourcloset.dto.RequestStroeDTO;
//import com.example.mp_termproject.ourcloset.dto.SenderDTO;
//import com.example.mp_termproject.signup.UserInfo;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.Map;
//
//public class ReceiverActivity extends AppCompatActivity {
//    private static final String TAG = "ReceiverActivity";
//
//    FirebaseUser user;
//    FirebaseFirestore db;
//    DocumentReference docRefUserInfo;
//
//    String sender;
//    String receiver;
//    String imgNum;
//    String reqNum;
//    RequestStroeDTO requestStroeDTO;
//    SenderDTO senderDTO;
//    ImageDTO imageDTO;
//
//    // String imgUrl, String name, String address, String phone
//    String address;
//    String imgUrl;
//    String name;
//    String phoneNumber;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_receiver);
//
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        db = FirebaseFirestore.getInstance();
//        docRefUserInfo = db.collection("request").document(user.getUid());
//
//        db.collection("request").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//
//                                String doc = document.getId().toString();
//                                String[] temp = doc.split(" ");
//
//                                sender = temp[0];
//                                receiver = temp[1];
//                                imgNum = temp[2];
//
//                                Map<String, Object> temp2 = document.getData();
//                                reqNum = (String) temp2.get("reqNum");
//
//                                if (receiver.equals(user.getUid())) {
//                                    requestStroeDTO = new RequestStroeDTO(sender, receiver, imgNum, reqNum);
//                                    // 사진 url 이름 주소 핸드폰
//                                    // String imgUrl, String name, String address, String phone
//                                    docRefUserInfo = db.collection("users").document(sender);
//
//                                    docRefUserInfo.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                            if (task.isSuccessful()) {
//                                                DocumentSnapshot document = task.getResult();
//                                                if (document.exists()) {
//                                                    // imgNum 받아옴
//                                                    Map<String, Object> temp = document.getData();
//                                                    address = (String) temp.get("address");
//                                                    name = (String) temp.get("name");
//                                                    phoneNumber = (String)temp.get("phoneNumber");
//
//                                                    db.collection("images").document(sender).collection("image").where
//                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                                @Override
//                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                                    if (task.isSuccessful()) {
//                                                                        for (QueryDocumentSnapshot document : task.getResult()) {
//
//                                                                            accessUserInfoDB(document.getId());
//
//                                                                        }
//                                                                    } else {
//
//                                                                    }
//                                                                }
//                                                            });
//
//
//                                                } else {
//                                                    Log.d(TAG, "No such document");
//                                                }
//                                            } else {
//                                                Log.d(TAG, "get failed with ", task.getException());
//                                            }
//                                        }
//                                    });
//                                    senderDTO = new SenderDTO();
//
//                                }
//
//
//                                Log.d("sender", sender);
//                                Log.d("sender", receiver);
//                                Log.d("sender", imgNum);
//                                Log.d("sender", reqNum);
//
//
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//
//    }
//}
