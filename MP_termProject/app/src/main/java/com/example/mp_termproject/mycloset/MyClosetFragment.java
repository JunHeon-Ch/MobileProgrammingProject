package com.example.mp_termproject.mycloset;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mp_termproject.R;
import com.example.mp_termproject.mycloset.add.MyClosetAddActivity;
import com.example.mp_termproject.mycloset.camera.CameraActivity;
import com.example.mp_termproject.mycloset.filter.MyClosetFilterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;


public class MyClosetFragment extends Fragment {
    private static final String TAG = "MyClosetFragment";

    final static int REQUEST_FILTER = 1;
    final static int REQUEST_ADD = 2;
    static ArrayList<ImageDTO> dtoList = new ArrayList<>();

    DocumentReference docRefImageInfo;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final DocumentReference docRefUserInfo = db.collection("users").document(user.getUid());

    Double[] imgnum = new Double[1];

    EditText searchText;
    ImageView searchImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("MY CLOSET");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_closet,
                container, false);
        setHasOptionsMenu(true);


        searchText = rootView.findViewById(R.id.search);
        searchImage = rootView.findViewById(R.id.search_image);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!searchText.getText().toString().equals(getResources().getString(R.string.search))) {
//                  상운 구현부
//                  edit text에 있는 string값과 같은 상품명을 확인해서 보여줌
                    Toast.makeText(getContext(), searchText.getText().toString(), Toast.LENGTH_SHORT).show();
                    String sText=searchText.getText().toString();
                    int i = 0;
                    Log.d("url123",imgnum[0]+"");
                    for (i = 0; i < Math.round(imgnum[0]); i++) {
                        if(sText.equals(dtoList.get(i).getBrand())||sText.equals( dtoList.get(i).getItemName())){
//                            Toast.makeText(getContext(), dtoList.get(i).getImgURL(), Toast.LENGTH_SHORT).show();
                                Log.d(" url123",dtoList.get(i).getImgURL()+"");
                        }
                    }


                }
            }
        });

//        상운 구현부
//        데이터베이스에서 내 옷장에 있는 옷 읽어와서 뿌려주는거 구현


        return rootView;
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
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

            }
        });
        db.collection("images").document(user.getUid()).collection("image")
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
                                String category = (String) temp.get("category");
                                String name = (String) temp.get("itemName");
                                String color = (String) temp.get("color");
                                String brand = (String) temp.get("brand");
                                String season = (String) temp.get("season");
                                String size = (String) temp.get("size");
                                String shared = (String) temp.get("shared");
                                ImageDTO dto = new ImageDTO(id, url, category, name, color, brand, season, size, shared);
                                dtoList.add(dto);
                                Log.d("snapshot", "" + dtoList.get(i));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                Log.d("gogogogo", "" + imgnum[0]);
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

                ArrayList<String> categoryItemList = bundle.getStringArrayList("category");
                ArrayList<String> colorItemList = bundle.getStringArrayList("color");
                ArrayList<String> seasonItemList = bundle.getStringArrayList("season");
                String sharedItem = bundle.getString("share");


//                               상운 구현부
//                categorySelectedList, colorSelectedList, seasonSelectedList, shareSelected에
//                저장된 데이터들이 필터 기준임.
//                예를들어, categorySelectedList에  상의 Top, 아우터 Outer 이렇게 저장되있으면
//                "상의, 아우터만 데이터베이스에서 가져와라" 이 뜻
//                만약 리스트가 null인 경우, 필터 기준없이 다 가져오면 됨.
//                예를 들어, 카테고리 -> 상의 / 컬러 -> null / 시즌 -> 봄 / 공유 -> 비공유 이면
//                "카테고리가 상의고, 시즌은 봄이고, 공유는 비공유이고, 컬러는 모든 컬러를 가져와라"


                Toast.makeText(getContext(),
                        categoryItemList.toString() + "\n"
                                + colorItemList.toString() + "\n"
                                + seasonItemList.toString() + "\n"
                                + sharedItem + "\n",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(getContext(), c);

        startActivity(intent);
    }
}
