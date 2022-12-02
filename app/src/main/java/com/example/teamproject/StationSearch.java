package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.content.Intent;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class StationSearch extends AppCompatActivity {

    Button buttonEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_search);

//        // 출발역 버튼 클릭시 액티비티 전환
//        Button start_btn = (Button) findViewById(R.id.start_station);
//        start_btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity4.class);
//                startActivity(intent);
//            }
//        });
//
//        // 환승역 버튼 클릭시 액티비티 전환
//        Button middle_btn = (Button) findViewById(R.id.middle_station);
//        middle_btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity4.class);
//                startActivity(intent);
//            }
//        });
//
//        // 도착역 버튼 클릭시 액티비티 전환
//        Button end_btn = (Button) findViewById(R.id.end_station);
//        end_btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity4.class);
//                startActivity(intent);
//            }
//        });
    }
}