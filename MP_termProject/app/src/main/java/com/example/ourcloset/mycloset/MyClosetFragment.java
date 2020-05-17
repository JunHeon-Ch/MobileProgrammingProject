package com.example.ourcloset.mycloset;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mp_termproject.R;
<<<<<<< Updated upstream:MP_termProject/app/src/main/java/com/example/ourcloset/mycloset/MyClosetFragment.java
import com.example.ourcloset.mycloset.add.MyClosetAddActivity;
import com.example.ourcloset.mycloset.filter.MyClosetFilterActivity;
=======
import com.example.mp_termproject.mycloset.camera.CameraActivity;
import com.example.mp_termproject.mycloset.filter.MyClosetFilterActivity;
>>>>>>> Stashed changes:MP_termProject/app/src/main/java/com/example/mp_termproject/mycloset/MyClosetFragment.java

import java.util.ArrayList;


public class MyClosetFragment extends Fragment {

    static final int REQUEST_FILTER = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("MY CLOSET");

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_closet,
                container, false);
        setHasOptionsMenu(true);



//        상운 구현부
//        데이터베이스에서 내 옷장에 있는 옷 읽어와서 뿌려주는거 구현





        return rootView;
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
            case R.id.actionbar_search:
//              검색 메뉴 옵션 선택
//              검색 구현

                break;
            case R.id.actionbar_add:
//              추가 메뉴 옵션 선택
//              카메라 권한 얻은 후 사진을 얻어 변수에 저장 -> 저장한 이미지 grabCut으로 배경 제거
//              배경제거 된 image를 번들에 태워 인텐트로 MyClosetAddActivity로 이동
                myStartActivity(CameraActivity.class);

//                intent = new Intent(getContext(), MyClosetAddActivity.class);
//                startActivity(intent);
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

        if(requestCode == REQUEST_FILTER){
            if(resultCode == -1){
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
