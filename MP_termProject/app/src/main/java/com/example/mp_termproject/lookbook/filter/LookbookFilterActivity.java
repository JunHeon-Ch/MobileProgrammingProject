package com.example.mp_termproject.lookbook.filter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mp_termproject.R;

import java.util.ArrayList;

public class LookbookFilterActivity extends AppCompatActivity {

    TextView occasion;
    TextView season;

    ListView occasionList;
    ListView seasonList;

    String[] occasions;
    String[] seasons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Filter");
        setContentView(R.layout.activity_lookbook_filter);

        // listview 생성 및 adapter 지정.
        occasionList = findViewById(R.id.lookbook_filter_list_occasion);
        occasions = getResources().getStringArray(R.array.occation);
        ArrayAdapter<String> occasionAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, occasions);
        occasionList.setAdapter(occasionAdapter);

        seasonList = findViewById(R.id.lookbook_filter_list_season);
        seasons = getResources().getStringArray(R.array.season);
        ArrayAdapter<String> seasonAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, seasons);
        seasonList.setAdapter(seasonAdapter);

        occasion = findViewById(R.id.lookbook_filter_type_occasion);
        occasion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                occasion.setTextColor(Color.parseColor("#000000"));
                season.setTextColor(Color.parseColor("#eeeeee"));
                occasionList.setVisibility(View.VISIBLE);
                seasonList.setVisibility(View.INVISIBLE);
            }
        });

        season = findViewById(R.id.lookbook_filter_type_season);
        season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                occasion.setTextColor(Color.parseColor("#eeeeee"));
                season.setTextColor(Color.parseColor("#000000"));
                occasionList.setVisibility(View.INVISIBLE);
                seasonList.setVisibility(View.VISIBLE);
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
        ArrayList<String> occasionSelectedList = new ArrayList<>();
        ArrayList<String> seasonSelectedList = new ArrayList<>();

        SparseBooleanArray occasionSelectedPositions = occasionList.getCheckedItemPositions();
        SparseBooleanArray seasonSelectePositions = seasonList.getCheckedItemPositions();

        int curId = item.getItemId();

        int i;
        switch (curId) {
            case R.id.actionbar_reset:
                // 리셋되면 처음 상태로 돌린다

                for (i = 0; i < occasions.length; i++) {
                    if (occasionSelectedPositions.get(i)) {
                        occasionList.setItemChecked(i, false);
                    }
                }

                for (i = 0; i < seasons.length; i++) {
                    if (seasonSelectePositions.get(i)) {
                        seasonList.setItemChecked(i, false);
                    }
                }

                break;

            case R.id.actionbar_apply:
                // 적용하면 해당 기준에 맞는 옷을 데이터베이스에 가져와 My Closet 화면에 뿌려줌

                for (i = 0; i < occasions.length; i++) {
                    if (occasionSelectedPositions.get(i)) {
                        occasionSelectedList.add(occasions[i]);
                    }
                }

                for (i = 0; i < seasons.length; i++) {
                    if (seasonSelectePositions.get(i)) {
                        seasonSelectedList.add(seasons[i]);
                    }
                }

                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("occasion", occasionSelectedList);
                bundle.putStringArrayList("season", seasonSelectedList);
                intent.putExtras(bundle);

                setResult(RESULT_OK, intent);
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}