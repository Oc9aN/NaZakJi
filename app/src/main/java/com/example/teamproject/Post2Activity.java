package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Post2Activity extends AppCompatActivity {
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private TextView mTitleText, mContentsText, mNameText,mTimeText;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post2);


        mTitleText = findViewById(R.id.post2_title);
        mContentsText = findViewById(R.id.post2_contents);
        mNameText = findViewById(R.id.post2_name);
        mTimeText = findViewById(R.id.post2_time);

        Intent getIntent = getIntent();
        id = getIntent().getStringExtra(FirebaseID.documentId);
        Log.e("ITEM DOCUMENT ID",id);

        mStore.collection(FirebaseID.post).document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().exists()) {
                                if (task.getResult() != null) {
                                    Map<String, Object> snap = task.getResult().getData();
                                    String title = String.valueOf(snap.get(FirebaseID.title));
                                    String contents = String.valueOf(snap.get(FirebaseID.contents));
                                    String name = String.valueOf(snap.get(FirebaseID.name));
                                    String time = String.valueOf(snap.get(FirebaseID.time));

                                    mTitleText.setText(title);
                                    mContentsText.setText(contents);
                                    mNameText.setText(name);
                                    mTimeText.setText(time);
                                }
                            } else{
                            Toast.makeText(Post2Activity.this, "삭제된 문서입니다.", Toast.LENGTH_SHORT).show();
                        }
                            }

                    }
                });
    }
}