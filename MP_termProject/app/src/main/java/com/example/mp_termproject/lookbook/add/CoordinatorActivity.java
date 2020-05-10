package com.example.mp_termproject.lookbook.add;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mp_termproject.R;

public class CoordinatorActivity extends AppCompatActivity {

    ImageView hatImage;
    ImageView topImage;
    ImageView bottomImage;
    ImageView shoesImage;
    ImageView outerImage;
    ImageView bagImage;
    ImageView accessoryImage;
    Button resetButton;
    Button applyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Coordinator");
        setContentView(R.layout.activity_coordinate);

        hatImage = findViewById(R.id.hat_image);
        hatImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                상운 구현부
//                db에서 카테고리가 모자인 이미지를 전부 읽어온다.

//                준헌 구현부
//                가져온 이미지를 팝업 메시지로 띄워준다.
//                한 이미지를 선택하면 그 이미지들 hatImage에 set 한다.
            }
        });

        topImage = findViewById(R.id.top_image);
        topImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                상운 구현부
//                db에서 카테고리가 상의, 원피스인 이미지를 전부 읽어온다.

//                준헌 구현부
//                가져온 이미지를 팝업 메시지로 띄워준다.
//                한 이미지를 선택하면 그 이미지들 topImage에 set 한다.
            }
        });

        bottomImage = findViewById(R.id.bottom_image);
        bottomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                상운 구현부
//                db에서 카테고리가 스커트, 바지인 이미지를 전부 읽어온다.

//                준헌 구현부
//                가져온 이미지를 팝업 메시지로 띄워준다.
//                한 이미지를 선택하면 그 이미지들 bottomImage에 set 한다.
            }
        });

        shoesImage = findViewById(R.id.shoes_image);
        shoesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                상운 구현부
//                db에서 카테고리가 신발인 이미지를 전부 읽어온다.

//                준헌 구현부
//                가져온 이미지를 팝업 메시지로 띄워준다.
//                한 이미지를 선택하면 그 이미지들 shoesImage에 set 한다.
            }
        });

        outerImage = findViewById(R.id.outer_image);
        outerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                상운 구현부
//                db에서 카테고리가 아우터인 이미지를 전부 읽어온다.

//                준헌 구현부
//                가져온 이미지를 팝업 메시지로 띄워준다.
//                한 이미지를 선택하면 그 이미지들 hatImage에 set 한다.
            }
        });

        bagImage = findViewById(R.id.bag_image);
        bagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                상운 구현부
//                db에서 카테고리가 가방인 이미지를 전부 읽어온다.

//                준헌 구현부
//                가져온 이미지를 팝업 메시지로 띄워준다.
//                한 이미지를 선택하면 그 이미지들 bagImage에 set 한다.
            }
        });

        accessoryImage = findViewById(R.id.accessory_image);
        accessoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                상운 구현부
//                db에서 카테고리가 안경, 시계, 악세서리인 이미지를 전부 읽어온다.

//                준헌 구현부
//                가져온 이미지를 팝업 메시지로 띄워준다.
//                한 이미지를 선택하면 그 이미지들 accessoryImage에 set 한다.
            }
        });

        resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hatImage.setImageResource(R.drawable.hat);
                topImage.setImageResource(R.drawable.top);
                bottomImage.setImageResource(R.drawable.bottom);
                shoesImage.setImageResource(R.drawable.shoes);
                outerImage.setImageResource(R.drawable.outer);
                bagImage.setImageResource(R.drawable.bag);
                accessoryImage.setImageResource(R.drawable.accessory);
            }
        });

        applyButton = findViewById(R.id.apply_button);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
