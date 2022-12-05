package com.example.teamproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class Splash  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();   // 3초 후 이미지 닫기
            }
        }, 1500);
    }
}
