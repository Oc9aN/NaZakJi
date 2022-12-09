package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import org.apache.log4j.chainsaw.Main;

public class SideMenu extends AppCompatActivity {
    //뒤로가기
    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if(keycode == android.view.KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);

        // 공지사항 버튼 클릭시 액티비티 전환
        Button notice_btn = (Button) findViewById(R.id.menu_notice);
        notice_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
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
                finish();
            }
        });

        // 분실물 버튼 클릭시 액티비티 전환
        Button lost_btn = (Button) findViewById(R.id.menu_lost);
        lost_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.lost112.go.kr/"));
                startActivity(intent);
            }
        });

        // 불편신고 버튼 클릭시 액티비티 전환
        Button uncomfortable_btn = (Button) findViewById(R.id.menu_uncomfortable);
        uncomfortable_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), uncomfortable_menu.class);
                startActivity(intent);
            }
        });

        // 즐겨찾기 버튼 클릭시 액티비티 전환
        Button favorites_btn = (Button) findViewById(R.id.menu_favorites);
        favorites_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookmarkActivity.class);
                startActivity(intent);
            }
        });
    }
}