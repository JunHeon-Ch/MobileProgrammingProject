package com.example.mp_termproject.mycloset.add;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mp_termproject.R;

import java.util.ArrayList;

public class MyClosetAddActivity extends AppCompatActivity {

    ImageView image;
    TextView itemName;
    TextView category;
    TextView color;
    TextView brand;
    TextView season;
    TextView size;
    TextView shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit Info");
        setContentView(R.layout.activity_my_closet_add);

        // 번들로 받은 배경제거된 이미지 image 변수에 저장


        // itemName text 클릭시 item name 입력 popup 띄우기
        itemName = findViewById(R.id.my_closet_add_name);
        itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MyClosetAddActivity.this);
                alert.setMessage("상품명");

                final EditText name = new EditText(MyClosetAddActivity.this);
                alert.setView(name);

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (name.getText() != null) {
                            itemName.setText(name.getText().toString());
                            itemName.setTextColor(Color.parseColor("#000000"));
                        }
                    }
                });

                alert.show();
            }
        });


        // category text 클릭시 category popup menu 띄우기 (단일 선택)
        category = findViewById(R.id.my_closet_add_category);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyClosetAddActivity.this);

                builder.setItems(R.array.category, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        String[] items = getResources().getStringArray(R.array.category);
                        category.setText(items[pos]);
                        category.setTextColor(Color.parseColor("#000000"));
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false); //화면 밖에 선택 시 팝업 꺼지는거
                alertDialog.show();

            }
        });

        // color text 클릭시 color popup menu 띄우기 (다중 선택)
        final ArrayList<String> selectedColorList = new ArrayList<>();

        color = findViewById(R.id.my_closet_add_color);
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyClosetAddActivity.this);
                final ArrayList<String> selectedItems = new ArrayList<>();
                final String[] items = getResources().getStringArray(R.array.colors);

                builder.setMultiChoiceItems(R.array.colors, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos, boolean isChecked) {
                        if (isChecked == true) {
                            selectedItems.add(items[pos]);
                        } else {
                            selectedItems.remove(pos);
                        }
                    }
                });

                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        String select = "";
                        for (String item : selectedItems) {
                            select += item + " ";
                        }
                        color.setText(select);
                        color.setTextColor(Color.parseColor("#000000"));
                    }
                });

                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        // brand text 클릭시 brand 입력 popup 띄우기
        brand = findViewById(R.id.my_closet_add_brand);
        brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MyClosetAddActivity.this);
                alert.setMessage("브랜드");

                final EditText brandName = new EditText(MyClosetAddActivity.this);
                alert.setView(brandName);

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (brandName.getText() != null) {
                            brand.setText(brandName.getText().toString());
                            brand.setTextColor(Color.parseColor("#000000"));
                        }
                    }
                });

                alert.setCancelable(false);
                alert.show();
            }
        });

        // season text 클릭시 season popup menu 띄우기 (다중 선택)
        season = findViewById(R.id.my_closet_add_season);
        season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyClosetAddActivity.this);
                final ArrayList<String> selectedItems = new ArrayList<>();
                final String[] items = getResources().getStringArray(R.array.season);

                builder.setMultiChoiceItems(R.array.season, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos, boolean isChecked) {
                        if (isChecked == true) {
                            selectedItems.add(items[pos]);
                        } else {
                            selectedItems.remove(pos);
                        }
                    }
                });

                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        String select = "";
                        for (String item : selectedItems) {
                            select += item + " ";
                        }
                        season.setText(select);
                        season.setTextColor(Color.parseColor("#000000"));
                    }
                });

                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        // size text 클릭시 size popup 띄우기
        size = findViewById(R.id.my_closet_add_size);
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MyClosetAddActivity.this);
                alert.setMessage("사이즈");

                final EditText sizeText = new EditText(MyClosetAddActivity.this);
                alert.setView(sizeText);

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (sizeText.getText() != null) {
                            size.setText(sizeText.getText().toString());
                            size.setTextColor(Color.parseColor("#000000"));
                        }
                    }
                });

                alert.setCancelable(false);
                alert.show();
            }
        });

        // shared text 클릭시 shared popup 띄우기 (양자택일)
        shared = findViewById(R.id.my_closet_add_sheared);
        shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyClosetAddActivity.this);
                final String[] items = getResources().getStringArray(R.array.shared);
                final ArrayList<String> selectedItem  = new ArrayList<>();
                selectedItem.add(items[0]);

                builder.setSingleChoiceItems(R.array.shared, 0, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int pos)
                    {
                        selectedItem.clear();
                        selectedItem.add(items[pos]);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        shared.setText(selectedItem.get(0));
                        shared.setTextColor(Color.parseColor("#000000"));
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

    }

    // 옵션메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_edit_info, menu);

        return super.onCreateOptionsMenu(menu);
    }


    // 저장 옵션 메뉴 눌렀을 때 db에 저장하고 팝업메시지 띄운다.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();

        switch (curId) {
            case R.id.actionbar_store:

                // db에 저장

                AlertDialog.Builder alert = new AlertDialog.Builder(MyClosetAddActivity.this);
                alert.setMessage("저장되었습니다");

                alert.setPositiveButton("ok",   new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(MyClosetAddActivity.this,
                                itemName.getText() + "\n"
                                + category.getText() +"\n"
                                + color.getText() +"\n"
                                + brand.getText() +"\n"
                                + season.getText() +"\n"
                                + size.getText() +"\n"
                                + shared.getText() +"\n",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

                alert.show();

        }

        return super.onOptionsItemSelected(item);
    }


}