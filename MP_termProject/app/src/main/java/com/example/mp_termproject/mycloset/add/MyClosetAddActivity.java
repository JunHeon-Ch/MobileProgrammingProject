package com.example.mp_termproject.mycloset.add;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mp_termproject.R;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyClosetAddActivity extends AppCompatActivity {

    private static final String TAG = "MyClosetAddActivity";

    final static int REQUEST_IMAGE_CAPTURE = 1;
    final static int REQUEST_GET_GALLERY = 2;

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
    String shared = "공유";
    TextView price;
    LinearLayout layout;
    Button sharedButton;
    Button unsharedButton;
    TextView infoText;

    byte[] bytes;

    private boolean isOpenCvLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit Info");
        setContentView(R.layout.activity_my_closet_add);

        AlertDialog.Builder builder = new AlertDialog.Builder(MyClosetAddActivity.this);

        final String[] option = new String[]{"카메라", "갤러리"};

        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                if (pos == 0) {
                    int permission = ContextCompat.checkSelfPermission(MyClosetAddActivity.this, Manifest.permission.CAMERA);
                    if (permission == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(MyClosetAddActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                    } else {
                        sendTakePhotoIntent();
                    }
                } else if (pos == 1) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                    startActivityForResult(intent, REQUEST_GET_GALLERY);
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        layout = findViewById(R.id.layout);
        price = findViewById(R.id.my_closet_add_price);
        sharedButton = findViewById(R.id.shareBtn);
        unsharedButton = findViewById(R.id.unShareBtn);
        infoText = findViewById(R.id.infoText);
        unsharedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.INVISIBLE);
                shared = "비공유";
                unsharedButton.setBackgroundColor(Color.parseColor("#e6ebed"));
                sharedButton.setBackgroundColor(Color.parseColor("#ffffff"));
                infoText.setText("비공유 제품 정보");
            }
        });
        sharedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
                shared = "공유";
                unsharedButton.setBackgroundColor(Color.parseColor("#ffffff"));
                sharedButton.setBackgroundColor(Color.parseColor("#e6ebed"));
                infoText.setText("공유 제품 정보");
            }
        });

        imgnum = new Double[1];
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        docRef = db.collection("users").document(user.getUid());

        Intent intent = getIntent();
        imgnum[0] = intent.getDoubleExtra("imgNum", 0.0);

        // 번들로 받은 배경제거된 이미지 image 변수에 저장
        image = findViewById(R.id.my_closet_add_image);

        // itemName text 클릭시 item name 입력 popup 띄우기
        itemName = findViewById(R.id.my_closet_add_name);
        itemName.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MyClosetAddActivity.this);
                alert.setMessage("상품명");

                final EditText name = new EditText(MyClosetAddActivity.this);
                Typeface typeface = getResources().getFont(R.font.ourfont);
                name.setTypeface(typeface);
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
                alertDialog.show();

            }
        });

        // color text 클릭시 color popup menu 띄우기 (다중 선택)
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
                Typeface typeface = getResources().getFont(R.font.ourfont);
                brandName.setTypeface(typeface);
                alert.setView(brandName);

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (brandName.getText() != null) {
                            brand.setText(brandName.getText().toString());
                            brand.setTextColor(Color.parseColor("#000000"));
                        }
                    }
                });

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
                Typeface typeface = getResources().getFont(R.font.ourfont);
                sizeText.setTypeface(typeface);
                alert.setView(sizeText);

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (sizeText.getText() != null) {
                            size.setText(sizeText.getText().toString());
                            size.setTextColor(Color.parseColor("#000000"));
                        }
                    }
                });

                alert.show();
            }
        });

        // shared text 클릭시 shared popup 띄우기 (양자택일)
        price = findViewById(R.id.my_closet_add_price);
        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MyClosetAddActivity.this);
                alert.setMessage("가격");

                final EditText priceText = new EditText(MyClosetAddActivity.this);
                Typeface typeface = getResources().getFont(R.font.ourfont);
                priceText.setTypeface(typeface);
                alert.setView(priceText);

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (priceText.getText() != null) {
                            price.setText(priceText.getText().toString());
                            price.setTextColor(Color.parseColor("#000000"));
                        }
                    }
                });

                alert.show();
            }
        });


    }

    private void sendTakePhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults[0] == 0) {
                Toast.makeText(this, "카메라 권한 승인완료", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "카메라 권한 승인거절", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == 1) {
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MainActivity", "권한 허용 : " + permissions[i]);
                }
            }
        }
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
                String sharedText = shared;
                String priceText = price.getText().toString();
                // 문서 갖고오기

                imgnum[0] += 1;
                // storage에 저장할 값들 저장해두기
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();

                final StorageReference mountainImagesRef =
                        storageRef.child("closet/" + user.getUid() + "/" + imgnum[0] + ".jpg");

                // img url 저장
                imgURL = "closet/" + user.getUid() + "/" + imgnum[0] + ".jpg";

                // storage에 upload
                UploadTask uploadTask = mountainImagesRef.putBytes(bytes);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            Log.e("실패1", "실패");
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return mountainImagesRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            Log.e("성공", "성공: " + downloadUri);
                        } else {
                            // Handle failures
                            // ...
                            Log.e("실패2", "실패");
                        }
                    }
                });

                // 데이터베이스에 저장

                Log.d(TAG, "new2 data: " + imgnum[0]);
                ImageDTO imgDto = new ImageDTO(userID, imgURL, categoryText, imgNameText,
                        colorText, brandText, seasonText, sizeText, sharedText, priceText, imgnum[0]);

                //Log.d("test1", imgnum[0].toString());

                db.collection("images")
                        .document(user.getUid())
                        .collection("image")
                        .document(imgnum[0].toString()).set(imgDto)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                AlertDialog.Builder alert =
                                        new AlertDialog.Builder(MyClosetAddActivity.this);
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
                                        finish();
                                    }
                                });
                                alert.setCancelable(false);
                                alert.show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MyClosetAddActivity.this,
                                        "사진 업로드가 실패했습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                break;

            case R.id.actionbar_reset:
                itemName.setText("상품명");
                itemName.setTextColor(Color.parseColor("#aaaaaa"));
                category.setText("카테고리");
                category.setTextColor(Color.parseColor("#aaaaaa"));
                color.setText("컬러");
                color.setTextColor(Color.parseColor("#aaaaaa"));
                brand.setText("브랜드");
                brand.setTextColor(Color.parseColor("#aaaaaa"));
                season.setText("시즌");
                season.setTextColor(Color.parseColor("#aaaaaa"));
                size.setText("사이즈");
                size.setTextColor(Color.parseColor("#aaaaaa"));
                price.setText("가격");
                price.setTextColor(Color.parseColor("#aaaaaa"));


                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == -1) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            saveBitmapToJpeg(imageBitmap);
//
//            File file = new File(getCacheDir().toString());
//            File[] files = file.listFiles();
//
//            String target = null;
//            for (File tempFile : files) {
//                if (tempFile.getName().contains("temp")) {
//                    target = tempFile.getName();
//                }
//            }
//
//            String filePath = getCacheDir() + "/" + target;

            grabcut(imageBitmap);
        }

        if (requestCode == REQUEST_GET_GALLERY && resultCode == RESULT_OK) {
            // Make sure the request was successful
            try {
                // 선택한 이미지에서 비트맵 생성
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap imageBitmap = BitmapFactory.decodeStream(in);
                in.close();
//                saveBitmapToJpeg(imageBitmap);
//
//                File file = new File(getCacheDir().toString());
//                File[] files = file.listFiles();
//
//                String target = null;
//                for (File tempFile : files) {
//                    if (tempFile.getName().contains("temp")) {
//                        target = tempFile.getName();
//                    }
//                }
//
//                String filePath = getCacheDir() + "/" + target;

                grabcut(imageBitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveBitmapToJpeg(Bitmap bitmap) {
        File storage = getCacheDir();
        String fileName = "temp.jpg";
        File tempFile = new File(storage, fileName);

        try {
            tempFile.createNewFile();
            FileOutputStream out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

        } catch (FileNotFoundException e) {
            Log.e("MyTag", "FileNotFoundException : " + e.getMessage());
        } catch (IOException e) {
            Log.e("MyTag", "IOException : " + e.getMessage());
        }
    }

    private void grabcut(Bitmap bitmap) {
        if (!isOpenCvLoaded)
            return;

//        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        Scalar color = new Scalar(255, 0, 0, 255);

        //Mat dst = new Mat();
        //Grabcut part
        Mat img = new Mat(); //container
        Utils.bitmapToMat(bitmap, img);
        Log.d(TAG, "img: " + img);

        //init new Matrices
        int r = img.rows();
        int c = img.cols();

        Point p1 = new Point(25, 25);
        Point p2 = new Point(c - 64, r - 64);

        Rect rect = new Rect(25, 25, c - 64, r - 64);
        Log.d(TAG, "rect: " + rect);

        Mat background = new Mat(img.size(), CvType.CV_8UC3, new Scalar(255, 255, 255));
        Mat mask = new Mat();
        mask.setTo(new Scalar(125));
        Mat fgModel = new Mat();
        fgModel.setTo(new Scalar(0, 0, 0));
        Mat bgModel = new Mat();
        bgModel.setTo(new Scalar(0, 0, 0));

        Mat imgC3 = new Mat(bitmap.getWidth(), bitmap.getHeight(), CvType.CV_8UC4);
        Imgproc.cvtColor(img, imgC3, Imgproc.COLOR_RGBA2RGB);
        Log.d(TAG, "imgC3: " + imgC3);

        Log.d(TAG, "Grabcut begins");
        Imgproc.grabCut(imgC3, mask, rect, bgModel, fgModel, 5, 0);
        Mat source = new Mat(1, 1, CvType.CV_8UC3, new Scalar(Imgproc.GC_PR_FGD));
        Log.d(TAG, "Grabcut begins2");
        Core.compare(mask, source, mask, Core.CMP_EQ);
        Mat foreground = new Mat(img.size(), CvType.CV_8UC3, new Scalar(255, 255, 255, 255));
        img.copyTo(foreground, mask);
        Imgproc.rectangle(img, p1, p2, color);
        Log.d(TAG, "Grabcut begins3");
        Mat tmp = new Mat();
        Imgproc.resize(background, tmp, img.size());
        Log.d(TAG, "Grabcut begins4");
        background = tmp;

        Mat tempMask = new Mat(foreground.size(), CvType.CV_8UC1, new Scalar(255, 255, 255, 255));
        // convert imgae to grayscale
        Imgproc.cvtColor(foreground, tempMask, Imgproc.COLOR_BGR2GRAY);
        // threshold the bitmap to create alpha channel with complete transparency in black background region and zero transparency in foreground object region.
        // threshold는 기준 thresh 값을 정해주어서 지정된 것보다 값이 작으면 검은색으로 변환 ,높으면 흰색
        Imgproc.threshold(tempMask, tempMask, 254, 255, Imgproc.THRESH_BINARY_INV);

        // split the original bitmap into three single channel.
        List<Mat> rgb = new ArrayList<Mat>(3);
        Core.split(foreground, rgb);

        // Create the final result by merging three single channel and alpha(BGR order)
        List<Mat> rgba = new ArrayList<Mat>(4);
        rgba.add(rgb.get(0));
        rgba.add(rgb.get(1));
        rgba.add(rgb.get(2));
        rgba.add(tempMask);
        Core.merge(rgba, tmp);


        // convert matrix to output bitmap
        Log.d(TAG, "Convert to Bitmap");
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Utils.matToBitmap(foreground, output);

        final int width = image.getWidth();
        final int height = image.getHeight();
        Bitmap bm = Bitmap.createScaledBitmap(output, width, height, true);
        image.setImageBitmap(bm);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        output.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bytes = stream.toByteArray();
    }

    //그랩컷 - opencv 시작
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    Log.i(TAG, "OpenCV loaded successfully");
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            isOpenCvLoaded = true;
        }
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