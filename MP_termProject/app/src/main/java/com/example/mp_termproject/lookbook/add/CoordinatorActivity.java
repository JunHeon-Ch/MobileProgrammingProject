package com.example.mp_termproject.lookbook.add;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mp_termproject.R;

import java.io.ByteArrayOutputStream;

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
        topImage = findViewById(R.id.hat_image);
        bottomImage = findViewById(R.id.hat_image);
        shoesImage = findViewById(R.id.hat_image);
        outerImage = findViewById(R.id.hat_image);
        bagImage = findViewById(R.id.hat_image);
        accessoryImage = findViewById(R.id.hat_image);

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

//                프래그먼트를 사진으로 저장

                LinearLayout coordinatorLayout = findViewById(R.id.coordinator_layout);

                byte[] bytes = viewToBitmap(coordinatorLayout);

                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putByteArray("bytes", bytes);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }

    public static byte[] viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        if (view instanceof SurfaceView) {
            SurfaceView surfaceView = (SurfaceView) view;
            surfaceView.setZOrderOnTop(true);
            surfaceView.draw(canvas);
            surfaceView.setZOrderOnTop(false);
        } else {
            //For ViewGroup & View
            view.draw(canvas);
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();

        return bytes;
    }



//    고정하고자 하는 width와 height 사이즈가 있다면 그에 맞게 scale을 해주는 함수
    public Bitmap readImageWithSampling(String imagePath, int targetWidth, int targetHeight) {
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);

        int photoWidth = bmOptions.outWidth;
        int photoHeight = bmOptions.outHeight;

        if (targetHeight <= 0) {
            targetHeight = (targetWidth * photoHeight) / photoWidth;
        }

        // Determine how much to scale down the image
        int scaleFactor = 1;
        if (photoWidth > targetWidth) {
            scaleFactor = Math.min(photoWidth / targetWidth, photoHeight / targetHeight);
        }

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(imagePath, bmOptions);
    }
}
