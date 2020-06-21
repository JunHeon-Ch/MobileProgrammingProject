package com.example.mp_termproject.lookbook;

import android.content.DialogInterface;
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
import com.example.mp_termproject.lookbook.add.CoordinatorActivity;
import com.example.mp_termproject.lookbook.add.CoordinatorEditActivity;
import com.example.mp_termproject.lookbook.dto.LookbookDTO;
import com.example.mp_termproject.lookbook.filter.LookbookFilterActivity;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class LookbookFragment extends Fragment {

    private static final String TAG = "LookbookFragment";

    static final int REQUEST_FILTER = 1;
    static final int NORMAL = 1;
    static final int FILTER = 2;

    static int check = NORMAL;

    ArrayList<LookbookDTO> dtoList;
    ArrayList<StorageReference> imageList;
    ArrayList<LookbookDTO> imageDTOList;
    HashSet<LookbookDTO> filterList;

    FirebaseUser user;
    FirebaseFirestore db;
    DocumentReference docRefUserInfo;

    FirebaseStorage storage;
    StorageReference storageRef;

    Double[] imgnum;

    LinearLayout imageContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Look Book");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_lookbook,
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
        imageDTOList = new ArrayList<>();
        filterList = new HashSet<>();

        imgnum = new Double[1];

        imageContainer = rootView.findViewById(R.id.imageContainer);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        accessDBInfo();
    }

    @Override
    public void onPause() {
        super.onPause();

        check = NORMAL;
    }

    private void accessDBInfo() {
        if (check == NORMAL) {
            // 유저 정보접근
            docRefUserInfo.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // imgNum 받아옴
                            Map<String, Object> temp = document.getData();
                            imgnum[0] = (Double) temp.get("lookNum");

                            db.collection("lookbook")
                                    .document(user.getUid())
                                    .collection("looks")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                dtoList.clear();
                                                int i = 0;

                                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                    Map<String, Object> temp = document.getData();

                                                    String id = (String) temp.get("userID");
                                                    String url = (String) temp.get("imgURL");
                                                    String occasion = (String) temp.get("occasion");
                                                    String season = (String) temp.get("season");
                                                    Double imgNum = (Double)temp.get("lookNum");
                                                    LookbookDTO dto = new LookbookDTO(id, url, occasion, season,imgNum);
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
        } else {
            int count = addPathReference(check);
            // 화면에 이미지 띄우기
            floatTotalImages(count);
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
                    imageList.add(storageRef.child(dtoList.get(i).getImgURL()));
                    imageDTOList.add(dtoList.get(i));
                }

                break;

            case FILTER:
                for (LookbookDTO dto : filterList) {
                    count++;
                    imageList.add(storageRef.child(dto.getImgURL()));
                    imageDTOList.add(dto);
                }

                break;
        }

        return count;
    }

    private void floatTotalImages(int count) {
        imageContainer.removeAllViews();

        int i = 0;
        while (i < count) {
            StorageReference pathReference = imageList.get(i);
            LookbookDTO lookbookDTO = imageDTOList.get(i);

            final int imageHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    900, getResources().getDisplayMetrics());
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageParams.setMargins(5, 5, 5, 5);
            imageParams.weight = 1;

            ImageView imageView = new ImageView(imageContainer.getContext());
            imageView.setLayoutParams(imageParams);

            Glide.with(imageContainer)
                    .load(pathReference)
                    .into(imageView);
            imageContainer.addView(imageView);

            i++;
            final LookbookDTO temp = lookbookDTO;
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

                            switch (pos) {
                                case 0:
                                    // 수정
                                    Intent intent = new Intent(getContext(), CoordinatorEditActivity.class);
                                    Bundle bundle = new Bundle();
                                    Log.d("look number",temp.getLookNum()+"");
                                    bundle.putDouble("lookNum", temp.getLookNum());
                                    bundle.putString("occasion",temp.getOccasion());
                                    bundle.putString("url",temp.getImgURL());
                                    bundle.putString("season",temp.getSeason());
                                    bundle.putString("userID",temp.getUserID());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
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

                                    final CollectionReference itemsRef = db.collection("lookbook").document(user.getUid()).collection("looks");
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
                                    onStart();
                                    Toast.makeText(getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    // 삭제
                                    break;
                                case 2:
                                    // 취소
                            }
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }
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

                intent = new Intent(getContext(), CoordinatorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("lookNum", imgnum[0]);
                intent.putExtras(bundle);

                startActivity(intent);
                break;

            case R.id.actionbar_filter:
//                필터 옵션 메뉴 선택
//                필터 선택 후 My Closet 화면에 조건에 맞는 아이템을 보여줌

                intent = new Intent(getContext(), LookbookFilterActivity.class);
                startActivityForResult(intent, REQUEST_FILTER);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FILTER) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();

                ArrayList<String> occasionItemList = bundle.getStringArrayList("occasion");
                ArrayList<String> seasonItemList = bundle.getStringArrayList("season");

                filterList.clear();
                filterList.addAll(filterOccasion(dtoList, occasionItemList));
                filterList.addAll(filterSeason(filterList, seasonItemList));

                if (filterList.size() == 0) {
                    check = NORMAL;
                    Toast.makeText(getContext(), "해당하는 값이 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    check = FILTER;
                }
            }
        }
    }

    private ArrayList<LookbookDTO> filterOccasion(ArrayList<LookbookDTO> list, ArrayList<String> arrayList) {
        ArrayList<LookbookDTO> temp = new ArrayList<>();

        if (arrayList.size() == 0) {
            return list;
        } else {
            for (LookbookDTO dto : list) {
                String[] tempOccasion = dto.getOccasion().split(" ");
                for (int k = 0; k < tempOccasion.length; k++) {
                    int flag = 0;
                    Log.d("test", tempOccasion[k]);
                    for (int j = 0; j < arrayList.size(); j++) {
                        if (tempOccasion[k].equals(arrayList.get(j))) {
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

    private HashSet<LookbookDTO> filterSeason(HashSet<LookbookDTO> list, ArrayList<String> arrayList) {
        HashSet<LookbookDTO> temp = new HashSet<>();

        if (arrayList.size() == 0) {
            return list;
        } else {
            for (LookbookDTO dto : list) {
                String[] tempColor = dto.getSeason().split(" ");
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
}
