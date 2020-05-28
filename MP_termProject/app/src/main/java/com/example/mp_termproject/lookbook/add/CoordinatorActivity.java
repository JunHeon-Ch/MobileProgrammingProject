package com.example.mp_termproject.lookbook.add;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mp_termproject.R;
import com.example.mp_termproject.mycloset.ImageDTO;
import com.example.mp_termproject.signup.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class CoordinatorActivity extends AppCompatActivity {

    RelativeLayout mainContainer;
    ScrollView imageContainer;
    LinearLayout imageLayout;

    ImageView hatImage;
    ImageView topImage;
    ImageView bottomImage;
    ImageView shoesImage;
    ImageView outerImage;
    ImageView bagImage;
    ImageView accessoryImage;
    Button resetButton;
    Button applyButton;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    static ArrayList<ImageDTO> dtoList;

    final FirebaseStorage storage = FirebaseStorage.getInstance();
    final StorageReference storageRef = storage.getReference();

    private ArrayList<String> imageUrlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Coordinator");
        setContentView(R.layout.activity_coordinate);

        dtoList = new ArrayList<>();
        imageUrlList = new ArrayList<>();

        readImageInfo();

        mainContainer = findViewById(R.id.mainContainer);
        imageContainer = findViewById(R.id.imageContainer);
        imageLayout = findViewById(R.id.imageLayout);

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
//                db에서 카테고리가 모자인 이미지를 전부 읽어온다.
//                                가져온 이미지를 팝업 메시지로 띄워준다.
//                한 이미지를 선택하면 그 이미지들 hatImage에 set 한다.
                mainContainer.setVisibility(View.INVISIBLE);
                imageContainer.setVisibility(View.VISIBLE);
                imageUrlList.clear();
                findSameCategory(new String[]{"모자 Headwear"});
                floatSameCategoryImage(hatImage);
            }
        });

        topImage = findViewById(R.id.top_image);
        topImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db에서 카테고리가 상의인 이미지를 전부 읽어온다.
//                                가져온 이미지를 팝업 메시지로 띄워준다.
//                한 이미지를 선택하면 그 이미지들 topImage에 set 한다.

                mainContainer.setVisibility(View.INVISIBLE);
                imageContainer.setVisibility(View.VISIBLE);
                imageUrlList.clear();
                findSameCategory(new String[]{"상의 Top"});
                floatSameCategoryImage(topImage);
            }
        });

        bottomImage = findViewById(R.id.bottom_image);
        bottomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db에서 카테고리가 스커트, 바지인 이미지를 전부 읽어온다.
                //                가져온 이미지를 팝업 메시지로 띄워준다.
//                한 이미지를 선택하면 그 이미지들 bottomImage에 set 한다.

                mainContainer.setVisibility(View.INVISIBLE);
                imageContainer.setVisibility(View.VISIBLE);
                imageUrlList.clear();
                findSameCategory(new String[]{"바지 Pants", "스커트 Skirt"});
                floatSameCategoryImage(bottomImage);
            }
        });

        shoesImage = findViewById(R.id.shoes_image);
        shoesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db에서 카테고리가 신발인 이미지를 전부 읽어온다.
//                                가져온 이미지를 팝업 메시지로 띄워준다.
//                한 이미지를 선택하면 그 이미지들 shoesImage에 set 한다.
                mainContainer.setVisibility(View.INVISIBLE);
                imageContainer.setVisibility(View.VISIBLE);
                imageUrlList.clear();
                findSameCategory(new String[]{"신발 Shoes"});
                floatSameCategoryImage(shoesImage);
            }
        });

        outerImage = findViewById(R.id.outer_image);
        outerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db에서 카테고리가 아우터인 이미지를 전부 읽어온다.
//                                가져온 이미지를 팝업 메시지로 띄워준다.
//                한 이미지를 선택하면 그 이미지들 hatImage에 set 한다.
                mainContainer.setVisibility(View.INVISIBLE);
                imageContainer.setVisibility(View.VISIBLE);
                imageUrlList.clear();
                findSameCategory(new String[]{"아우터 Outer"});
                floatSameCategoryImage(outerImage);

            }
        });

        bagImage = findViewById(R.id.bag_image);
        bagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db에서 카테고리가 가방인 이미지를 전부 읽어온다.
                //                가져온 이미지를 팝업 메시지로 띄워준다.
//                한 이미지를 선택하면 그 이미지들 bagImage에 set 한다.
                mainContainer.setVisibility(View.INVISIBLE);
                imageContainer.setVisibility(View.VISIBLE);
                imageUrlList.clear();
                findSameCategory(new String[]{"가방 Bag"});
                floatSameCategoryImage(bagImage);

            }
        });

        accessoryImage = findViewById(R.id.accessory_image);
        accessoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db에서 카테고리가 안경, 시계, 악세서리인 이미지를 전부 읽어온다.
                //                가져온 이미지를 팝업 메시지로 띄워준다.
//                한 이미지를 선택하면 그 이미지들 accessoryImage에 set 한다.
                mainContainer.setVisibility(View.INVISIBLE);
                imageContainer.setVisibility(View.VISIBLE);
                imageUrlList.clear();
                findSameCategory(new String[]{"시계 Watch", "안경 Eyewear", "액세서리 Accessory"});
                floatSameCategoryImage(accessoryImage);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dtoList.clear();
    }

    private void floatSameCategoryImage(final ImageView targetImageView) {
        LinearLayout linearLayout = null;
        imageLayout.removeAllViews();

        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                180, getResources().getDisplayMetrics());

        int i = 0;
        while (i < imageUrlList.size()) {
            StorageReference pathReference = storageRef.child(imageUrlList.get(i));

            if(i % 3 == 0){
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, height);
                layoutParams.gravity = Gravity.LEFT;

                linearLayout = new LinearLayout(imageLayout.getContext());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setLayoutParams(layoutParams);

                imageLayout.addView(linearLayout);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 5, 5, 5);
            layoutParams.weight = 1;

            final ImageView imageView = new ImageView(linearLayout.getContext());
            imageView.setLayoutParams(layoutParams);

            Glide.with(linearLayout)
                    .load(pathReference)
                    .into(imageView);
            linearLayout.addView(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 수정 & 삭제
                    BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                    targetImageView.setImageBitmap(drawable.getBitmap());

                    mainContainer.setVisibility(View.VISIBLE);
                    imageContainer.setVisibility(View.INVISIBLE);
                }
            });

            i++;
        }
    }

    private void findSameCategory(String[] category) {
        for(int j = 0; j < category.length; j++){
            for (int i = 0; i < dtoList.size(); i++) {
                if(dtoList.get(i).getCategory().equals(category[j])){
                    imageUrlList.add(dtoList.get(i).getImgURL());
                }
            }
        }
    }

    private void readImageInfo(){
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
                            Log.d("error", "Error getting documents: ", task.getException());
                        }
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
