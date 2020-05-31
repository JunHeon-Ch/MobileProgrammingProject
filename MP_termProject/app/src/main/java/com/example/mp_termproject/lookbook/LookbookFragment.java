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
import com.example.mp_termproject.lookbook.filter.LookbookFilterActivity;
import com.example.mp_termproject.mycloset.ImageDTO;
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

import static android.app.Activity.RESULT_OK;


public class LookbookFragment extends Fragment {

    private static final String TAG = "LookbookFragment";

    static final int REQUEST_FILTER = 1;
    static final int NORMAL = 1;

    static int check = NORMAL;

    ArrayList<LookbookDTO> dtoList;
    ArrayList<StorageReference> imageList;
    ArrayList<LookbookDTO> imageDTOList;

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("LOOKBOOK");
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

        imgnum = new Double[1];

        imageContainer = rootView.findViewById(R.id.imageContainer);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        accessDBInfo();
    }

    private void accessDBInfo(){
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
                                            int i = 0;
                                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                Map<String, Object> temp = document.getData();

                                                String id = (String) temp.get("userID");
                                                String url = (String) temp.get("imgURL");
                                                String occasion = (String) temp.get("occasion");
                                                String season = (String) temp.get("season");
                                                LookbookDTO dto = new LookbookDTO(id, url, occasion, season);
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

    private int addPathReference(int flag){
        imageList.clear();
        int count = 0;

        switch (flag){
            case NORMAL:
                for (int i = 0; i < dtoList.size(); i++) {
                    count++;
                    imageList.add(storageRef.child(dtoList.get(i).getImgURL()));
                    imageDTOList.add(dtoList.get(i));
                }

                break;
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

            if(i % 3 == 0){
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, height);
                layoutParams.gravity = Gravity.LEFT;

                linearLayout = new LinearLayout(imageContainer.getContext());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setLayoutParams(layoutParams);

                imageContainer.addView(linearLayout);
            }

            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageParams.setMargins(5, 5, 5, 5);
            imageParams.weight = 1;

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

        switch (curId){
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

        if(requestCode == REQUEST_FILTER){
            if(resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();

                ArrayList<String> occasionItemList = bundle.getStringArrayList("occasion");
                ArrayList<String> seasonItemList = bundle.getStringArrayList("season");



//                               상운 구현부
//                categorySelectedList, colorSelectedList, seasonSelectedList, shareSelected에
//                저장된 데이터들이 필터 기준임.
//                예를들어, categorySelectedList에  상의 Top, 아우터 Outer 이렇게 저장되있으면
//                "상의, 아우터만 데이터베이스에서 가져와라" 이 뜻
//                만약 리스트가 null인 경우, 필터 기준없이 다 가져오면 됨.
//                예를 들어, 카테고리 -> 상의 / 컬러 -> null / 시즌 -> 봄 / 공유 -> 비공유 이면
//                "카테고리가 상의고, 시즌은 봄이고, 공유는 비공유이고, 컬러는 모든 컬러를 가져와라"



                Toast.makeText(getContext(),
                        occasionItemList.toString() + "\n"
                                + seasonItemList.toString() + "\n",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
