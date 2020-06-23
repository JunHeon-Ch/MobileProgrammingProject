package com.example.mp_termproject.lookbook.add;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.mp_termproject.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SelectClosetActivity extends AppCompatActivity {

    LinearLayout imageLayout;

    FirebaseStorage storage;
    StorageReference storageRef;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Coordinator");
        setContentView(R.layout.activity_select_closet);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final ArrayList<String> imageUrlList = extras.getStringArrayList("urlList");

        imageLayout = findViewById(R.id.imageLayout);

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

            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url = imageUrlList.get(finalI);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);

                    finish();
                }
            });

            i++;
        }
    }
}
