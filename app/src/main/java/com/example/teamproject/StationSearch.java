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

    TextView start;
    TextView middle;
    TextView end;
    Button buttonEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_search);
        start = (TextView)findViewById(R.id.real_start_station);
        middle = (TextView)findViewById(R.id.real_middle_station);
        end = (TextView)findViewById(R.id.real_end_station);

        //데이터 가져오기
        Intent intent = getIntent();
        String stt = intent.getStringExtra("stt");
        String number = intent.getStringExtra("number");
        if(stt != null){
            if (stt.equals("start")) {
                start.setText(number);
            } else if (stt.equals("middle")) {
                middle.setText(number);
            } else if (stt.equals("end")) {
                end.setText(number);
            }
        }

        // 출발역 버튼 클릭시 액티비티 전환
        Button start_btn = (Button) findViewById(R.id.start_station);
        start_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindStation.class);
                intent.putExtra("station", "start");
                startActivityForResult(intent, 1);
            }
        });

        // 환승역 버튼 클릭시 액티비티 전환
        Button middle_btn = (Button) findViewById(R.id.middle_station);
        middle_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindStation.class);
                intent.putExtra("station", "middle");
                startActivityForResult(intent, 2);
            }
        });

        // 도착역 버튼 클릭시 액티비티 전환
        Button end_btn = (Button) findViewById(R.id.end_station);
        end_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindStation.class);
                intent.putExtra("station", "end");
                startActivityForResult(intent, 3);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                start.setText(result);
            }
        }
        else if(requestCode == 2){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                middle.setText(result);
            }
        }
        else if(requestCode == 3){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                end.setText(result);
            }
        }
    }
}