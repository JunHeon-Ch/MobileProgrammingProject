package com.example.ourcloset.mycloset.filter;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mp_termproject.R;

import java.util.ArrayList;

public class MyClosetFilterActivity extends AppCompatActivity {

    TextView category;
    TextView color;
    TextView season;
    TextView shared;

    ListView categoryList;
    ListView colorList;
    ListView seasonList;
    ListView sharedList;

    String[] categories;
    String[] colors;
    String[] seasons;
    String[] share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Filter");
        setContentView(R.layout.activity_my_closet_filter);

        // listview 생성 및 adapter 지정.
        categoryList = findViewById(R.id.mycloset_filter_list_category);
        categories = getResources().getStringArray(R.array.category);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, categories);
        categoryList.setAdapter(categoryAdapter);


        colorList = findViewById(R.id.mycloset_filter_list_color);
        colors = getResources().getStringArray(R.array.colors);
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, colors);
        colorList.setAdapter(colorAdapter);


        seasonList = findViewById(R.id.mycloset_filter_list_season);
        seasons = getResources().getStringArray(R.array.season);
        ArrayAdapter<String> seasonAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, seasons);
        seasonList.setAdapter(seasonAdapter);


        sharedList = findViewById(R.id.mycloset_filter_list_shared);
        share = getResources().getStringArray(R.array.shared);
        ArrayAdapter<String> shareAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, share);
        sharedList.setAdapter(shareAdapter);


        category = findViewById(R.id.mycloset_filter_type_category);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryList.setVisibility(View.VISIBLE);
                colorList.setVisibility(View.INVISIBLE);
                seasonList.setVisibility(View.INVISIBLE);
                sharedList.setVisibility(View.INVISIBLE);
            }
        });

        color = findViewById(R.id.mycloset_filter_type_color);
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryList.setVisibility(View.INVISIBLE);
                colorList.setVisibility(View.VISIBLE);
                seasonList.setVisibility(View.INVISIBLE);
                sharedList.setVisibility(View.INVISIBLE);
            }
        });

        season = findViewById(R.id.mycloset_filter_type_season);
        season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryList.setVisibility(View.INVISIBLE);
                colorList.setVisibility(View.INVISIBLE);
                seasonList.setVisibility(View.VISIBLE);
                sharedList.setVisibility(View.INVISIBLE);
            }
        });

        shared = findViewById(R.id.mycloset_filter_type_shared);
        shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryList.setVisibility(View.INVISIBLE);
                colorList.setVisibility(View.INVISIBLE);
                seasonList.setVisibility(View.INVISIBLE);
                sharedList.setVisibility(View.VISIBLE);
            }
        });

    }

    // 옵션메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_filter, menu);

        return super.onCreateOptionsMenu(menu);
    }


    // 필터 옵션 메뉴
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // 필터 기준이 각각 List에 저장됨
        ArrayList<String> categorySelectedList = new ArrayList<>();
        ArrayList<String> colorSelectedList = new ArrayList<>();
        ArrayList<String> seasonSelectedList = new ArrayList<>();
        String shareSelected = null;

        SparseBooleanArray categorySelectedPositions = categoryList.getCheckedItemPositions();
        SparseBooleanArray colorSelectedPositions = colorList.getCheckedItemPositions();
        SparseBooleanArray seasonSelectePositions = seasonList.getCheckedItemPositions();
        int sharedSelectedPosition = sharedList.getCheckedItemPosition();

        int curId = item.getItemId();

        int i;
        switch (curId) {
            case R.id.actionbar_reset:
                // 리셋되면 처음 상태로 돌린다

                for (i = 0; i < categories.length; i++) {
                    if (categorySelectedPositions.get(i)) {
                        categoryList.setItemChecked(i, false);
                    }
                }

                for (i = 0; i < colors.length; i++) {
                    if (colorSelectedPositions.get(i)) {
                        colorList.setItemChecked(i, false);
                    }
                }

                for (i = 0; i < seasons.length; i++) {
                    if (seasonSelectePositions.get(i)) {
                        seasonList.setItemChecked(i, false);
                    }
                }

                if(sharedSelectedPosition != -1){
                    sharedList.setItemChecked(sharedSelectedPosition, false);
                }

                break;

            case R.id.actionbar_apply:
                // 적용하면 해당 기준에 맞는 옷을 데이터베이스에 가져와 My Closet 화면에 뿌려줌

                for (i = 0; i < categories.length; i++) {
                    if (categorySelectedPositions.get(i)) {
                        categorySelectedList.add(categories[i]);
                    }
                }

                for (i = 0; i < colors.length; i++) {
                    if (colorSelectedPositions.get(i)) {
                        colorSelectedList.add(colors[i]);
                    }
                }

                for (i = 0; i < seasons.length; i++) {
                    if (seasonSelectePositions.get(i)) {
                        seasonSelectedList.add(seasons[i]);
                    }
                }

                if(sharedSelectedPosition != -1){
                    shareSelected = share[sharedSelectedPosition];
                }


                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("category", categorySelectedList);
                bundle.putStringArrayList("color", colorSelectedList);
                bundle.putStringArrayList("season", seasonSelectedList);
                bundle.putString("share", shareSelected);
                intent.putExtras(bundle);

                setResult(RESULT_OK, intent);
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}