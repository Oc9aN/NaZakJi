package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SideMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);

        // 공지사항 버튼 클릭시 액티비티 전환
        Button notice_btn = (Button) findViewById(R.id.menu_notice);
        notice_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StationSearch.class);
                startActivity(intent);
            }
        });

        // 역 검색 버튼 클릭시 액티비티 전환
        Button name_rule_btn = (Button) findViewById(R.id.menu_search);
        name_rule_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StationSearch.class);
                startActivity(intent);
            }
        });

        // 분실물 버튼 클릭시 액티비티 전환
        Button lost_btn = (Button) findViewById(R.id.menu_lost);
        lost_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StationSearch.class);
                startActivity(intent);
            }
        });

        // 불편신고 버튼 클릭시 액티비티 전환
        Button uncomfortable_btn = (Button) findViewById(R.id.menu_uncomfortable);
        uncomfortable_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StationSearch.class);
                startActivity(intent);
            }
        });

        // 즐겨찾기 버튼 클릭시 액티비티 전환
        Button favorites_btn = (Button) findViewById(R.id.menu_favorites);
        favorites_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StationSearch.class);
                startActivity(intent);
            }
        });

        // 설정 버튼 클릭시 액티비티 전환
        Button settings_btn = (Button) findViewById(R.id.menu_settings);
        settings_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StationSearch.class);
                startActivity(intent);
            }
        });
    }
}