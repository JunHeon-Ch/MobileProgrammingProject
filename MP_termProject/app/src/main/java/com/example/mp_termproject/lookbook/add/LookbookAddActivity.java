package com.example.mp_termproject.lookbook.add;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.mp_termproject.lookbook.LookbookDTO;
import com.example.mp_termproject.mycloset.ImageDTO;
import com.example.mp_termproject.mycloset.add.MyClosetAddActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Map;

public class LookbookAddActivity extends AppCompatActivity {

    private static final String TAG = "LookbookAddActivity";

    static final int REQUEST_COORDINATOR = 1;

    ImageView coordinatorImage;
    TextView occasion;
    TextView season;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final DocumentReference docRefUserInfo = db.collection("users").document(user.getUid());

    static Double[] imgnum = new Double[1];

    byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit Info");
        setContentView(R.layout.activity_lookbook_add);

        Intent intent = getIntent();
        imgnum[0] = intent.getDoubleExtra("lookNum", 0.0);

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
                bytes = data.getByteArrayExtra("bytes");
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

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
//                코디 db에 Bitmap bitmap, TextView occasion, season에 저장된 정보 저장
                String userID = user.getUid();
                String imgURL;
                String occasionText = occasion.getText().toString();
                String seasonText = season.getText().toString();

                // 문서 갖고오기

                Log.d("CheckTest", "1");


                imgnum[0] += 1;
                // storage에 저장할 값들 저장해두기
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();

                final StorageReference mountainImagesRef = storageRef.child("lookbook/" + user.getUid() + "/" + imgnum[0] + ".jpg");

                // img url 저장
                imgURL = "lookbook/" + user.getUid() + "/" + imgnum[0] + ".jpg";

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
                LookbookDTO imgDto = new LookbookDTO(userID, imgURL, occasionText, seasonText);
                //Log.d("test1", imgnum[0].toString());
                db.collection("lookbook").document(user.getUid()).collection("looks").document(imgnum[0].toString()).set(imgDto)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(LookbookAddActivity.this);
                                alert.setMessage("저장되었습니다");

                                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast.makeText(LookbookAddActivity.this,
                                                occasion.getText() + "\n"
                                                        + season.getText() + "\n",
                                                Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });

                                alert.show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LookbookAddActivity.this, "사진 업로드가 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
        }

        return super.onOptionsItemSelected(item);
    }
}
