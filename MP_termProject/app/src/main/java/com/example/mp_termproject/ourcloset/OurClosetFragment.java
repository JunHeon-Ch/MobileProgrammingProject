package com.example.mp_termproject.ourcloset;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.mp_termproject.R;
import com.example.mp_termproject.ourcloset.filter.OurClosetFilterActivity;


public class OurClosetFragment extends Fragment {

    static final int REQUEST_FILTER = 1;
    EditText searchText;
    ImageView searchImage;

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

        searchText = rootView.findViewById(R.id.search);
        searchImage = rootView.findViewById(R.id.search_image);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!searchText.getText().toString().equals(getResources().getString(R.string.search))) {
//                  상운 구현부
//                  edit text에 있는 string값과 같은 상품명을 확인해서 보여줌
                    Toast.makeText(getContext(), searchText.getText().toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });


        // db에서 다른 사람의 공유된 제품 읽어와서 뿌려주는 거 구현


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
            case R.id.actionbar_search:
//              검색 메뉴 옵션 선택
//              검색 구현

                break;

            case R.id.actionbar_filter:
//                필터 옵션 메뉴 선택
//                필터 선택 후 My Closet 화면에 조건에 맞는 아이템을 보여줌

                intent = new Intent(getContext(), OurClosetFilterActivity.class);
                startActivityForResult(intent, REQUEST_FILTER);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
