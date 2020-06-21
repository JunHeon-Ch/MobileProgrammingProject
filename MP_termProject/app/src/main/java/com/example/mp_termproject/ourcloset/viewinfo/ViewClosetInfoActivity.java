package com.example.mp_termproject.ourcloset.viewinfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mp_termproject.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewClosetInfoActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;

    ImageView image;
    TextView itemName;
    TextView category;
    TextView color;
    TextView brand;
    TextView season;
    TextView size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("View Info");
        setContentView(R.layout.activity_view_closet_info);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        image = findViewById(R.id.my_closet_add_image);
        itemName = findViewById(R.id.my_closet_add_name);
        category = findViewById(R.id.my_closet_add_category);
        color = findViewById(R.id.my_closet_add_color);
        brand = findViewById(R.id.my_closet_add_brand);
        season = findViewById(R.id.my_closet_add_season);
        size = findViewById(R.id.my_closet_add_size);

        Intent intent = getIntent();
        StorageReference imagePath = storageRef.child(intent.getStringExtra("image"));

        Glide.with(this)
                .load(imagePath)
                .into(image);

        itemName.setText(intent.getStringExtra("name"));
        category.setText(intent.getStringExtra("category"));
        color.setText(intent.getStringExtra("color"));
        brand.setText(intent.getStringExtra("brand"));
        season.setText(intent.getStringExtra("season"));
        size.setText(intent.getStringExtra("size"));
    }
}
