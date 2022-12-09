package com.example.teamproject;

import android.app.Application;

public class WorldValue extends Application {

    private boolean isStart = true;

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }
}

