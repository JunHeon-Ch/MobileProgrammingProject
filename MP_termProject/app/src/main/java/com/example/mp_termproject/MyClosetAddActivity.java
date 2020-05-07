package com.example.mp_termproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

public class MyClosetAddActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_my_closet_add);

        // color text 클릭시 color popup 띄우기
        itemName = findViewById(R.id.my_closet_add_name);
        itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());

                alert.setTitle("Input your name");
                alert.setMessage("Plz, input yourname");

                final EditText name = new EditText(getApplicationContext());
                alert.setView(name);

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String username = name.getText().toString();

                    }
                });

                alert.setNegativeButton("no",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();
            }
        });

        // category text 클릭시 category popup 띄우기
        category = findViewById(R.id.my_closet_add_category);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), v);
                popup.getMenuInflater().inflate(R.menu.popup_category, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        category.setText(item.getTitle());
                        category.setTextColor(Color.parseColor("#000000"));
                        return true;
                    }
                });
                popup.show();
            }
        });

        // color text 클릭시 color popup 띄우기
        color = findViewById(R.id.my_closet_add_color);
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // brand text 클릭시 brand popup 띄우기
        brand = findViewById(R.id.my_closet_add_brand);
        brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // season text 클릭시 season popup 띄우기
        season = findViewById(R.id.my_closet_add_season);
        season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // size text 클릭시 size popup 띄우기
        size = findViewById(R.id.my_closet_add_size);
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // shared text 클릭시 shared popup 띄우기
        shared = findViewById(R.id.my_closet_add_sheared);
        shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
