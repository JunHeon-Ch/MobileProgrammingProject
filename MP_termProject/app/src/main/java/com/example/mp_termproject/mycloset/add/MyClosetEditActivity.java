package com.example.mp_termproject.mycloset.add;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mp_termproject.R;
import com.example.mp_termproject.mycloset.ImageDTO;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyClosetEditActivity extends AppCompatActivity {

    private static final String TAG = "MyClosetEditActivity";

    final static int REQUEST_IMAGE_CAPTURE = 1;

    Double[] imgnum;
    FirebaseUser user;
    FirebaseFirestore db;
    DocumentReference docRef;

    ImageView image;
    TextView itemName;
    TextView category;
    TextView color;
    TextView brand;
    TextView season;
    TextView size;
    TextView shared;

    byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit Info");
        setContentView(R.layout.activity_my_closet_add);

        imgnum = new Double[1];
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        docRef = db.collection("users").document(user.getUid());

        Intent intent = getIntent();
        imgnum[0] = intent.getDoubleExtra("imgNum1", 0.0);
        bytes = intent.getByteArrayExtra("image");

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        // 번들로 받은 배경제거된 이미지 image 변수에 저장
        Log.d("activity123 test", "" + imgnum[0]);
        image = findViewById(R.id.my_closet_add_image);
        image.setImageBitmap(bitmap);

        // itemName text 클릭시 item name 입력 popup 띄우기
        itemName = findViewById(R.id.my_closet_add_name);
        itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MyClosetEditActivity.this);
                alert.setMessage("상품명");

                final EditText name = new EditText(MyClosetEditActivity.this);
                alert.setView(name);

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (name.getText() != null) {
                            itemName.setText(name.getText().toString());
                            itemName.setTextColor(Color.parseColor("#000000"));
                        }
                    }
                });

                alert.setCancelable(false);
                alert.show();
            }
        });


        // category text 클릭시 category popup menu 띄우기 (단일 선택)
        category = findViewById(R.id.my_closet_add_category);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyClosetEditActivity.this);

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
        color = findViewById(R.id.my_closet_add_color);
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyClosetEditActivity.this);
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
                AlertDialog.Builder alert = new AlertDialog.Builder(MyClosetEditActivity.this);
                alert.setMessage("브랜드");

                final EditText brandName = new EditText(MyClosetEditActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MyClosetEditActivity.this);
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
                AlertDialog.Builder alert = new AlertDialog.Builder(MyClosetEditActivity.this);
                alert.setMessage("사이즈");

                final EditText sizeText = new EditText(MyClosetEditActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MyClosetEditActivity.this);
                final String[] items = getResources().getStringArray(R.array.shared);
                final ArrayList<String> selectedItem = new ArrayList<>();
                selectedItem.add(items[0]);

                builder.setSingleChoiceItems(R.array.shared, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        selectedItem.clear();
                        selectedItem.add(items[pos]);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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


//                상운 구현부
//                image, itemName, category, col    or, brand, season, shared 값 데이터베이스에 저장
                String userID = user.getUid();
                String imgURL;
                String categoryText = category.getText().toString();
                String imgNameText = itemName.getText().toString();
                String colorText = color.getText().toString();
                String brandText = brand.getText().toString();
                String seasonText = season.getText().toString();
                String sizeText = size.getText().toString();
                String sharedText = shared.getText().toString();

                // 문서 갖고오기

                Log.d("CheckTest", "1");



                Log.d(TAG, "new3 data: " + imgnum[0]);
                Log.d(TAG, "new1 data: " + imgnum[0]);
                // storage에 저장할 값들 저장해두기
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();

                final StorageReference mountainImagesRef =
                        storageRef.child("closet/" + user.getUid() + "/" + imgnum[0] + ".jpg");

                // img url 저장
                imgURL = "closet/" + user.getUid() + "/" + imgnum[0] + ".jpg";

                // 데이터베이스에 저장

                Log.d(TAG, "new2 data: " + imgnum[0]);
                ImageDTO imgDto = new ImageDTO(userID, imgURL, categoryText, imgNameText,
                        colorText, brandText, seasonText, sizeText, sharedText,imgnum[0]);
                //Log.d("test1", imgnum[0].toString());

                db.collection("images")
                        .document(user.getUid())
                        .collection("image")
                        .document(imgnum[0].toString()).set(imgDto)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                AlertDialog.Builder alert =
                                        new AlertDialog.Builder(MyClosetEditActivity.this);
                                alert.setMessage("저장되었습니다");

                                docRef
                                        .update("imgNum", imgnum[0])
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
                                        Toast.makeText(MyClosetEditActivity.this,
                                                itemName.getText() + "\n"
                                                        + category.getText() + "\n"
                                                        + color.getText() + "\n"
                                                        + brand.getText() + "\n"
                                                        + season.getText() + "\n"
                                                        + size.getText() + "\n"
                                                        + shared.getText() + "\n",
                                                Toast.LENGTH_SHORT).show();

                                        finish();
                                    }
                                });

                                alert.show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MyClosetEditActivity.this,
                                        "사진 업로드가 실패했습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                break;


        }

        return super.onOptionsItemSelected(item);
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


}
