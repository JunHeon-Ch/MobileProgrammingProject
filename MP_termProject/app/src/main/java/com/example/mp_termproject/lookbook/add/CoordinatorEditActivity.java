package com.example.mp_termproject.lookbook.add;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mp_termproject.R;
import com.example.mp_termproject.lookbook.dto.LookbookDTO;
import com.example.mp_termproject.mycloset.dto.ImageDTO;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class CoordinatorEditActivity extends AppCompatActivity {

    private static final String TAG = "CoordinatorActivity";
    private static final String IMAGEVIEW_TAG = "드래그 이미지";

    RelativeLayout mainContainer;
    RelativeLayout imageContainer;
    LinearLayout imageLayout;
    ImageView image;
    LinearLayout resetButton;
    TextView occasionText;
    TextView seasonText;
    LinearLayout coordinatorLayout;
    Button emptyImageButton;

    LookbookDTO lookbookDTO;
    FirebaseUser user;
    FirebaseFirestore db;
    DocumentReference docRefUserInfo;

    FirebaseStorage storage;
    StorageReference storageRef;

    ArrayList<ImageDTO> dtoList;
    ArrayList<String> imageUrlList;

    Double[] imgnum;
    StorageReference path;


    byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Coordinator");
        setContentView(R.layout.activity_edit_coordinate);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        imgnum = new Double[1];

        docRefUserInfo = db.collection("users").document(user.getUid());

        dtoList = new ArrayList<>();
        imageUrlList = new ArrayList<>();

        Intent intent = getIntent();
        imgnum[0] = intent.getDoubleExtra("lookNum", 0.0);
        String occasion = intent.getStringExtra("occasion");
        String url = intent.getStringExtra("url");
        String season = intent.getStringExtra("season");
        String userID = intent.getStringExtra("userID");

        lookbookDTO = new LookbookDTO(userID,url,occasion,season,imgnum[0]);
        readImageInfo();
        image = findViewById(R.id.edit_closet_image);
        path =storageRef.child(lookbookDTO.getImgURL());
        Glide.with(this)
                .load(path)
                .into(image);


        mainContainer = findViewById(R.id.mainContainer);
        imageContainer = findViewById(R.id.imageContainer);
        imageLayout = findViewById(R.id.imageLayout);

        coordinatorLayout = findViewById(R.id.coordinator_layout);


        occasionText = findViewById(R.id.coordinator_occasion);
        occasionText.setText(occasion);
        occasionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CoordinatorEditActivity.this);
                final ArrayList<String> selectedItems = new ArrayList<>();
                final String[] items = getResources().getStringArray(R.array.occation);

                builder.setMultiChoiceItems(R.array.occation, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
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
                        occasionText.setText(select);
                        occasionText.setTextColor(Color.parseColor("#000000"));
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

        seasonText = findViewById(R.id.coordinator_season);
        seasonText.setText(season);
        seasonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CoordinatorEditActivity.this);
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
                        seasonText.setText(select);
                        seasonText.setTextColor(Color.parseColor("#000000"));
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
//                코디 db에 Bitmap bitmap, TextView occasion, season에 저장된 정보 저장
                String userID = user.getUid();
                String imgURL;

                bytes = viewToBitmap(coordinatorLayout);

                // 문서 갖고오기

                // storage에 저장할 값들 저장해두기
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();

                final StorageReference mountainImagesRef =
                        storageRef.child("lookbook/" + user.getUid() + "/" + imgnum[0] + ".jpg");

                // img url 저장
                imgURL = "lookbook/" + user.getUid() + "/" + imgnum[0] + ".jpg";

                // 데이터베이스에 저장

                LookbookDTO imgDto = new LookbookDTO(userID, imgURL,
                        occasionText.getText().toString(), seasonText.getText().toString(),imgnum[0]);
                db.collection("lookbook")
                        .document(user.getUid())
                        .collection("looks")
                        .document(imgnum[0].toString()).set(imgDto)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                AlertDialog.Builder alert =
                                        new AlertDialog.Builder(CoordinatorEditActivity.this);
                                alert.setMessage("저장되었습니다");

                                docRefUserInfo
                                        .update("lookNum", imgnum[0])
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error updating document", e);
                                            }
                                        });

                                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast.makeText(CoordinatorEditActivity.this,
                                                occasionText.getText().toString() + "\n"
                                                        + seasonText.getText().toString() + "\n",
                                                Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                                alert.setCancelable(false);
                                alert.show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CoordinatorEditActivity.this,
                                        "사진 업로드가 실패했습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                break;

            case R.id.actionbar_reset:
                occasionText.setText("Occasion");
                occasionText.setTextColor(Color.parseColor("#aaaaaa"));
                seasonText.setText("Season");
                seasonText.setTextColor(Color.parseColor("#aaaaaa"));

                break;
        }

        return super.onOptionsItemSelected(item);
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

            if (i % 3 == 0) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, height);

                linearLayout = new LinearLayout(imageLayout.getContext());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setLayoutParams(layoutParams);

                imageLayout.addView(linearLayout);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 5, 5, 5);
            layoutParams.weight = 1;
            layoutParams.gravity = Gravity.TOP;

            final ImageView imageView = new ImageView(linearLayout.getContext());
            imageView.setLayoutParams(layoutParams);

            Glide.with(linearLayout)
                    .load(pathReference)
                    .into(imageView);
            linearLayout.addView(imageView);

            emptyImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    targetImageView.setImageBitmap(null);

                    mainContainer.setVisibility(View.VISIBLE);
                    imageContainer.setVisibility(View.INVISIBLE);
                }
            });

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
        for (int j = 0; j < category.length; j++) {
            for (int i = 0; i < dtoList.size(); i++) {
                if (dtoList.get(i).getCategory().equals(category[j])) {
                    imageUrlList.add(dtoList.get(i).getImgURL());
                }
            }
        }
    }

    private void readImageInfo() {
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
                                Double imgNum = (Double) temp.get("imgNum");
                                ImageDTO dto = new ImageDTO(id, url, category, name, color, brand, season, size, shared, imgNum);
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
