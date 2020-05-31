package com.example.mp_termproject.mycloset;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mp_termproject.R;
import com.example.mp_termproject.mycloset.add.MyClosetAddActivity;
import com.example.mp_termproject.mycloset.filter.MyClosetFilterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;


public class MyClosetFragment extends Fragment {

    private static final String TAG = "MyClosetFragment";

    static final int REQUEST_FILTER = 1;
    static final int NORMAL = 1;
    static final int SEARCH = 2;
    static final int FILTER = 3;

    static int check = NORMAL;

    ArrayList<ImageDTO> dtoList;
    ArrayList<StorageReference> imageList;
    ArrayList<ImageDTO> imageDTOList;
    HashSet<ImageDTO> filterList;

    FirebaseUser user;
    FirebaseFirestore db;
    DocumentReference docRefUserInfo;
    FirebaseStorage storage;
    StorageReference storageRef;

    Double[] imgnum;

    EditText searchText;
    ImageView searchImage;

    LinearLayout imageContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("MY CLOSET");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_closet,
                container, false);
        setHasOptionsMenu(true);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        docRefUserInfo = db.collection("users").document(user.getUid());
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        dtoList = new ArrayList<>();
        imageList = new ArrayList<>();
        imageDTOList = new ArrayList<>();
        filterList = new HashSet<>();

        imgnum = new Double[1];


        imageContainer = rootView.findViewById(R.id.imageContainer);

        searchText = rootView.findViewById(R.id.search);
        searchText.setText(null);
        searchImage = rootView.findViewById(R.id.search_image);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!searchText.getText().toString().equals("")) {
//                  edit text에 있는 string값과 같은 상품명을 확인해서 보여줌
                    check = SEARCH;
                }
                onStart();
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        accessDBInfo();
    }

    private void accessDBInfo() {
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

                        db.collection("images")
                                .document(user.getUid())
                                .collection("image")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            int i = 0;
                                            dtoList.clear();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
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

                                                int count = addPathReference(check);
                                                // 화면에 이미지 띄우기
                                                floatTotalImages(count);
                                            }
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

    // Action Bar에 메뉴옵션 띄우기
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.actionbar, menu);
    }

    // Action Bar 메뉴옵션 선택 시
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();
        Intent intent;

        switch (curId) {
            case R.id.actionbar_add:
//              추가 메뉴 옵션 선택
//              카메라 권한 얻은 후 사진을 얻어 변수에 저장 -> 저장한 이미지 grabCut으로 배경 제거
//              배경제거 된 image를 번들에 태워 인텐트로 MyClosetAddActivity로 이동
//                myStartActivity(CameraActivity.class);
                intent = new Intent(getContext(), MyClosetAddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("imgNum", imgnum[0]);
                intent.putExtras(bundle);
                startActivity(intent);

                break;

            case R.id.actionbar_filter:
//                필터 옵션 메뉴 선택
//                필터 선택 후 My Closet 화면에 조건에 맞는 아이템을 보여줌

                intent = new Intent(getContext(), MyClosetFilterActivity.class);
                startActivityForResult(intent, REQUEST_FILTER);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FILTER) {
            if (resultCode == -1) {
                Bundle bundle = data.getExtras();

                ArrayList<String> categoryItemList = (ArrayList<String>) bundle.getStringArrayList("category");
                ArrayList<String> colorItemList = (ArrayList<String>) bundle.getStringArrayList("color");
                ArrayList<String> seasonItemList = (ArrayList<String>) bundle.getStringArrayList("season");
                String sharedItem = bundle.getString("share");

                filterList.clear();
                filterList.addAll(filterCategory(dtoList, categoryItemList));
                filterList.addAll(filterColor(filterList, colorItemList));
                filterList.addAll(filterSeason(filterList, seasonItemList));
                filterList.addAll(filterShared(filterList, sharedItem));

                if (filterList.size() == 0) {
                    check = NORMAL;
                    Toast.makeText(getContext(), "해당하는 값이 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    check = FILTER;
                }
            }
        }
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(getContext(), c);

        startActivity(intent);
    }

    //        데이터베이스에서 내 옷장에 있는 옷 읽어와서 뿌려주는거 구현
    private void floatTotalImages(int count) {
        LinearLayout linearLayout = null;
        imageContainer.removeAllViews();
        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                180, getResources().getDisplayMetrics());

        int i = 0;
        while (i < count) {
            StorageReference pathReference = imageList.get(i);

            ImageDTO imageDTO = imageDTOList.get(i);


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

            final ImageView imageView = new ImageView(linearLayout.getContext());
            imageView.setLayoutParams(imageParams);

            Glide.with(linearLayout)
                    .load(pathReference)
                    .into(imageView);
            linearLayout.addView(imageView);

            final ImageDTO temp = imageDTO;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 수정 & 삭제
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    String[] option = {"수정", "삭제", "취소"};
                    builder.setItems(option, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int pos) {
                            // 수정, 삭제, 취소

                            switch (pos){
                                case 0:
                                    // 수정
                                    break;
                                case 1:
                                    // Create a reference to the file to delete
                                    StorageReference desertRef = storageRef.child(temp.getImgURL());
                                    desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // File deleted successfully
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Uh-oh, an error occurred!
                                        }
                                    });

                                    final CollectionReference itemsRef = db.collection("images").document(user.getUid()).collection("image");
                                    Query query = itemsRef.whereEqualTo("imgURL", temp.getImgURL());
                                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (DocumentSnapshot document : task.getResult()) {
                                                    itemsRef.document(document.getId()).delete();
                                                }
                                            } else {
                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                            }
                                        }
                                    });
                                    Log.d("test","test "+temp.getImgURL());
                                    // 삭제
                                    break;
                                case 2:
                                    // 취소
                            }
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCancelable(false); //화면 밖에 선택 시 팝업 꺼지는거
                    alertDialog.show();
                }
            });


            i++;
        }
    }

    private int addPathReference(int flag) {
        imageList.clear();
        imageDTOList.clear();
        int count = 0;

        switch (flag) {
            case NORMAL:
                for (int i = 0; i < dtoList.size(); i++) {
                    count++;
                    imageList.add(storageRef.child(dtoList.get(i).imgURL));
                    imageDTOList.add(dtoList.get(i));
                }

                break;

            case SEARCH:
                String sText = searchText.getText().toString();
                for (int i = 0; i < dtoList.size(); i++) {
                    if (sText.equals(dtoList.get(i).getBrand()) ||
                            sText.equals(dtoList.get(i).getItemName())) {
                        count++;
                        imageList.add(storageRef.child(dtoList.get(i).getImgURL()));
                        imageDTOList.add(dtoList.get(i));
                    }
                }

                break;

            case FILTER:
                for (ImageDTO dto : filterList) {
                    count++;
                    imageList.add(storageRef.child(dto.getImgURL()));
                    imageDTOList.add(dto);
                }

                break;
        }

        return count;
    }

    private ArrayList<ImageDTO> filterCategory(ArrayList<ImageDTO> list, ArrayList<String> arrayList) {
        ArrayList<ImageDTO> temp = new ArrayList<>();

        if (arrayList.size() == 0) {
            return list;
        } else {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < arrayList.size(); j++) {
                    if (list.get(i).getCategory().equals(arrayList.get(j))) {
                        temp.add(list.get((i)));
                        break;
                    }
                }
            }
        }

        return temp;
    }

    private HashSet<ImageDTO> filterColor(HashSet<ImageDTO> list, ArrayList<String> arrayList) {
        HashSet<ImageDTO> temp = new HashSet<>();

        if (arrayList.size() == 0) {
            return list;
        } else {
            for (ImageDTO dto : list) {
                String[] tempColor = dto.getColor().split(" ");
                for (int k = 0; k < tempColor.length; k++) {
                    int flag = 0;
                    for (int j = 0; j < arrayList.size(); j++) {
                        if (tempColor[k].equals(arrayList.get(j))) {
                            temp.add(dto);
                            flag = 1;
                            break;
                        }
                    }

                    if (flag == 1) {
                        break;
                    }
                }
            }
        }

        return temp;
    }

    private HashSet<ImageDTO> filterSeason(HashSet<ImageDTO> list, ArrayList<String> arrayList) {
        HashSet<ImageDTO> temp = new HashSet<>();

        if (arrayList.size() == 0) {
            return list;
        } else {
            for (ImageDTO dto : list) {
                String[] temSeason = dto.getColor().split(" ");
                for (int k = 0; k < temSeason.length; k++) {
                    int flag = 0;
                    for (int j = 0; j < arrayList.size(); j++) {
                        if (temSeason[k].equals(arrayList.get(j))) {
                            temp.add(dto);
                            flag = 1;
                            break;
                        }
                    }

                    if (flag == 1) {
                        break;
                    }
                }
            }
        }

        return temp;
    }

    private HashSet<ImageDTO> filterShared(HashSet<ImageDTO> list, String shared) {
        HashSet<ImageDTO> temp = new HashSet<>();

        if (shared == null) {
            return list;
        } else {
            for (ImageDTO dto : list) {
                if (dto.getShared().equals(shared)) {
                    temp.add(dto);
                }
            }
        }

        return temp;
    }
}
