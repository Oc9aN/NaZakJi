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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (((WorldValue)getApplication()).isStart()) {
            startActivity(new Intent(this, Splash.class));
            ((WorldValue)getApplication()).setStart(false);
        }

        // 검색 버튼 클릭시 액티비티 전환
        ImageButton name_rule_btn = (ImageButton) findViewById(R.id.main_search_btn);
        name_rule_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FindStationMain.class);
                startActivity(intent);
            }
        });

        // 메뉴 버튼 클릭시 액티비티 전환
        ImageButton menu_btn = (ImageButton) findViewById(R.id.main_side_btn);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SideMenu.class);
                startActivity(intent);
            }
        });

    }
}