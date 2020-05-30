package com.example.mp_termproject.ourcloset;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mp_termproject.R;
import com.example.mp_termproject.mycloset.ImageDTO;
import com.example.mp_termproject.ourcloset.filter.OurClosetFilterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;


public class OurClosetFragment extends Fragment {

    private static final String TAG = "MyClosetFragment";

    static final int REQUEST_FILTER = 1;

    EditText searchText;
    ImageView searchImage;
    LinearLayout imageContainer;

    FirebaseUser user;
    FirebaseFirestore db;
    DocumentReference docRefUserInfo;
    FirebaseStorage storage;
    StorageReference storageRef;

    ArrayList<ImageDTO> dtoList;

    Double[] imgnum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("OUR CLOSET");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_our_closet,
                container,
                false);

        setHasOptionsMenu(true);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        docRefUserInfo = db.collection("users").document(user.getUid());
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        dtoList = new ArrayList<>();
        imgnum = new Double[1];

        imageContainer = rootView.findViewById(R.id.closet_image_container);

        searchText = rootView.findViewById(R.id.search);
        searchImage = rootView.findViewById(R.id.search_image);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!searchText.getText().toString().equals(getResources().getString(R.string.search))) {
//                  edit text에 있는 string값과 같은 상품명을 확인해서 보여줌
                    Toast.makeText(getContext(), searchText.getText().toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });

        return rootView;
    }

    // Action Bar에 메뉴옵션 띄우기
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.actionbar_ourcloset, menu);
    }

    // Action Bar 메뉴옵션 선택 시
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();
        Intent intent;

        switch (curId) {
            case R.id.actionbar_filter:
//                필터 옵션 메뉴 선택
//                필터 선택 후 My Closet 화면에 조건에 맞는 아이템을 보여줌

                intent = new Intent(getContext(), OurClosetFilterActivity.class);
                startActivityForResult(intent, REQUEST_FILTER);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 유저 정보접근
        docRefUserInfo.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // imgNum 받아옴
                        Map<String, Object> temp = document.getData();
                        imgnum[0] = (Double) temp.get("imgNum");

                        db.collection("images").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Map<String, Object> data = document.getData();
                                            }

                                            // 화면에 이미지 띄우기
                                            floatTotalImages();
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });

                        db.collection("images")
                                .document(user.getUid())
                                .collection("image")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            dtoList.clear();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Map<String, Object> temp = document.getData();

                                                String id = (String) temp.get("userID");
                                                String url = (String) temp.get("imgURL");
                                                String category = (String) temp.get("category");
                                                String name = (String) temp.get("itemName");
                                                String color = (String) temp.get("color");
                                                String brand = (String) temp.get("brand");
                                                String season = (String) temp.get("season");
                                                String size = (String) temp.get("size");
                                                String shared = (String) temp.get("shared");
                                                ImageDTO dto = new ImageDTO(id, url, category, name,
                                                        color, brand, season, size, shared);
                                                dtoList.add(dto);
                                            }

                                            // 화면에 이미지 띄우기
                                            floatTotalImages();
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

            }
        });

    }

    private void floatTotalImages() {
        LinearLayout linearLayout = null;
        imageContainer.removeAllViews();

        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                180, getResources().getDisplayMetrics());
        int count = 0;

        ArrayList<StorageReference> imageList = new ArrayList<>();

        for (int i = 0; i < imgnum[0]; i++) {
            if (dtoList.get(i).getShared().equals("공유")) {
                count++;
                imageList.add(storageRef.child("closet/" + user.getUid() + "/" + (i + 1) + ".0.jpg"));
            }
        }

        int i = 0;
        while (i < count) {
            StorageReference pathReference = imageList.get(i);

            if(i % 3 == 0){
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, height);

                linearLayout = new LinearLayout(imageContainer.getContext());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setLayoutParams(layoutParams);

                imageContainer.addView(linearLayout);
                Log.d("test", i + " " + pathReference.toString());
            }

            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageParams.setMargins(5, 5, 5, 5);
            imageParams.weight = 1;
            imageParams.gravity = Gravity.LEFT;

            ImageView imageView = new ImageView(linearLayout.getContext());
            imageView.setLayoutParams(imageParams);

            Glide.with(linearLayout)
                    .load(pathReference)
                    .into(imageView);
            linearLayout.addView(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 수정 & 삭제
                    Toast.makeText(getContext(), "클릭", Toast.LENGTH_SHORT).show();
                }
            });

            i++;
        }
    }
}
