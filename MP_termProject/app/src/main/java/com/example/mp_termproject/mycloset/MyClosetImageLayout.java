package com.example.mp_termproject.mycloset;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.ScriptGroup;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.mp_termproject.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

public class MyClosetImageLayout extends LinearLayout  {
    private StorageReference pathReference1;
    private StorageReference pathReference2;
    Context context;
    ImageView imageView1;
    ImageView imageView2;

    public MyClosetImageLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyClosetImageLayout(Context context) {
        super(context);
        this.context = context;
    }

    public void setPathReference1(StorageReference pathReference1) {
        this.pathReference1 = pathReference1;
    }

    public void setPathReference2(StorageReference pathReference2) {
        this.pathReference2 = pathReference2;
    }

    public void init() {
        imageView1 = findViewById(R.id.imageView1);
        Glide.with(context)
                .load(pathReference1)
                .into(imageView1);

        imageView2 = findViewById(R.id.imageView2);
        Glide.with(context)
                .load(pathReference2)
                .into(imageView2);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.image_layout, this, true);
    }
}