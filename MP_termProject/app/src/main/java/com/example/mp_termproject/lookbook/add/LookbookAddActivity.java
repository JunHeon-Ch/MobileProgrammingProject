package com.example.mp_termproject.lookbook.add;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mp_termproject.R;

import java.util.ArrayList;

public class LookbookAddActivity extends AppCompatActivity {

    static final int REQUEST_COORDINATOR = 1;

    ImageView coordinatorImage;
    TextView occasion;
    TextView season;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit Info");
        setContentView(R.layout.activity_lookbook_add);

        coordinatorImage = findViewById(R.id.coordinator_image);
        coordinatorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CoordinatorActivity.class);
                startActivityForResult(intent, REQUEST_COORDINATOR);
            }
        });


        occasion = findViewById(R.id.lookbook_add_occation);
        occasion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LookbookAddActivity.this);
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
                        occasion.setText(select);
                        occasion.setTextColor(Color.parseColor("#000000"));
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

        season = findViewById(R.id.lookbook_add_season);
        season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LookbookAddActivity.this);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_COORDINATOR) {
            if (resultCode == RESULT_OK) {
                byte[] bytes = data.getByteArrayExtra("bytes");
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                coordinatorImage.setImageBitmap(bitmap);
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
//                코디 db에 Bitmap bitmap, TextView occasion, season에 저장된 정보 저장


                AlertDialog.Builder alert = new AlertDialog.Builder(LookbookAddActivity.this);
                alert.setMessage("저장되었습니다");

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(LookbookAddActivity.this,
                                bitmap.toString() + "\n"
                                        + occasion.getText() + "\n"
                                        + season.getText() + "\n",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

                alert.show();

        }

        return super.onOptionsItemSelected(item);
    }
}
