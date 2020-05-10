package com.example.mp_termproject.mycloset;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mp_termproject.R;
import com.example.mp_termproject.mycloset.add.MyClosetAddActivity;
import com.example.mp_termproject.mycloset.filter.MyClosetFilterActivity;


public class MyClosetFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("MY CLOSET");

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_closet,
                container, false);
        setHasOptionsMenu(true);

//        데이터베이스에서 내 옷장에 있는 옷 읽어와서 뿌려주는거 구현


        return rootView;
    }

    // Action Bar에 메뉴옵션 띄우기
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.actionbar_my_closet, menu);
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

                intent = new Intent(getContext(), MyClosetAddActivity.class);
                startActivity(intent);
                break;

            case R.id.actionbar_filter:
//                필터 옵션 메뉴 선택
//                필터 선택 후 My Closet 화면에 조건에 맞는 아이템을 보여줌

                intent = new Intent(getContext(), MyClosetFilterActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
