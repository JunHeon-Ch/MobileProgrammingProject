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

    private static final String TAG = "OurClosetFragment";

    static final int REQUEST_FILTER = 1;
    static final int NORMAL = 1;
    static final int SEARCH = 2;

    EditText searchText;
    ImageView searchImage;
    LinearLayout imageContainer;

    FirebaseUser user;
    FirebaseFirestore db;
    DocumentReference docRefUserInfo;
    FirebaseStorage storage;
    StorageReference storageRef;

    ArrayList<ImageDTO> dtoList;
    ArrayList<StorageReference> imageList;

    ArrayList<String> imgUrl = new ArrayList<>();

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
        imageList = new ArrayList<>();

        imgnum = new Double[1];

        imageContainer = rootView.findViewById(R.id.closet_image_container);

        searchText = rootView.findViewById(R.id.search);
        searchImage = rootView.findViewById(R.id.search_image);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!searchText.getText().toString().equals(getResources().getString(R.string.search))) {
//                  edit text에 있는 string값과 같은 상품명을 확인해서 보여줌
                    int count = addPathReference(SEARCH);
                    floatTotalImages(count);
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
//        DocumentReference tmep = db.collection("images").document();
        Log.d("test", "test");

        db.collection("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Log.d(TAG,user.getUid()+"");
                                if (!user.getUid().equals(document.getId())) {
                                    db.collection("images").document(document.getId()).collection("image").whereEqualTo("shared", "공유").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            String temp = document.getString("imgURL");
                                                            Log.d("image information", document.getId() + " => " + temp);

                                                            ImageDTO dto = new ImageDTO();
                                                            dto.setBrand(document.getString("brand"));
                                                            dto.setItemName(document.getString("itemName"));
                                                            dto.setImgURL(temp);
                                                            dtoList.add(dto);
                                                            imgUrl.add(temp);
                                                        }
                                                        int count = addPathReference(NORMAL);
                                                        floatTotalImages(count);
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

    private int addPathReference(int flag) {
        imageList.clear();
        int count = 0;

        switch (flag) {
            case NORMAL:
                for (int i = 0; i < imgUrl.size(); i++) {
                    count++;
                    imageList.add(storageRef.child(imgUrl.get(i)));
                }

                break;

            case SEARCH:
                String sText = searchText.getText().toString();
                for (int i = 0; i < dtoList.size(); i++) {
                    if (sText.equals(dtoList.get(i).getBrand()) ||
                            sText.equals(dtoList.get(i).getItemName())) {
                        count++;
                        imageList.add(storageRef.child(dtoList.get(i).getImgURL()));
                    }
                }
        }

        return count;
    }

    private void floatTotalImages(int count) {
        LinearLayout linearLayout = null;
        imageContainer.removeAllViews();

        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                180, getResources().getDisplayMetrics());

        int i = 0;
        while (i < count) {
            StorageReference pathReference = imageList.get(i);

            if (i % 3 == 0) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, height);

                linearLayout = new LinearLayout(imageContainer.getContext());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setLayoutParams(layoutParams);

                imageContainer.addView(linearLayout);
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
