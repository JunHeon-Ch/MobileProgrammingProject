package com.example.mp_termproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mp_termproject.signup.MemberInitActivity;
import com.example.mp_termproject.signup.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //private final static String TAG = MainActivity.class.getClass().getSimpleName();
    private boolean isOpenCvLoaded = false;
    private boolean isTest = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //그랩컷 시작
        final ImageView imageView = findViewById(R.id.imageView);
        final ImageView imageView2 = findViewById(R.id.imageView2);


        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOpenCvLoaded)
                    return;

                // convert image to matrix
                InputStream is = null;
                try {
                    is = getAssets().open("dress13.jpg");
                    Bitmap image = BitmapFactory.decodeStream(is);
                    Mat src = new Mat(image.getWidth(), image.getHeight(), CvType.CV_8UC4);
                    Utils.bitmapToMat(image, src);

                    // init new matrices
                    //CV_8U는 8비트 unsigned integer ( 범위 0~255)
                    Mat dst = new Mat(image.getWidth(), image.getHeight(), CvType.CV_8UC4);
                    Mat tmp = new Mat(image.getWidth(), image.getHeight(), CvType.CV_8UC4);
                    Mat alpha = new Mat(image.getWidth(), image.getHeight(), CvType.CV_8UC4);

                    // convert image to grayscale
                    Imgproc.cvtColor(src, tmp, Imgproc.COLOR_BGR2GRAY);

                    // threshold the image to create alpha channel with complete transparency in black background region and zero transparency in foreground object region.
                    //threshold는 기준 thresh 값을 정해주어서 지정된 것보다 값이 작으면 검은색으로 변환 ,높으면 흰색
                    Imgproc.threshold(tmp, alpha, 110, 255, Imgproc.THRESH_BINARY);

                    // split the original image into three single channel.
                    List<Mat> rgb = new ArrayList<Mat>(3);
                    Core.split(src, rgb);

                    // Create the final result by merging three single channel and alpha(BGR order)
                    List<Mat> rgba = new ArrayList<Mat>(4);
                    rgba.add(rgb.get(0));
                    rgba.add(rgb.get(1));
                    rgba.add(rgb.get(2));
                    rgba.add(alpha);
                    Core.merge(rgba, dst);

                    // convert matrix to output bitmap
                    Bitmap output = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(dst, output);

                    imageView2.setImageBitmap(output);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
//그랩컷 끝

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//        로그인 정보가 없다면
        if (user == null) {
            myStartActivity(SignUpActivity.class);
        } else {
//            로그인 정보가 있다면
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("users").document(user.getUid());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                // 회원정보가 입력되어 있다면
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    myStartActivity(ModeActivity.class);
                                } else {
//                                회워정보가 입력되지 않았다면
                                    Log.d(TAG, "No such document");
                                    myStartActivity(MemberInitActivity.class);
                                }
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        }

        private void myStartActivity (Class c){
            Intent intent = new Intent(this, c);
            startActivity(intent);
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
        public void onResume () {
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
    }
