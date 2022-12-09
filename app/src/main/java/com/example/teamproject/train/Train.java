package com.example.teamproject.train;

import android.util.Log;

import com.example.teamproject.navigation.Edge;
import com.example.teamproject.navigation.Graph;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class Train {
    private String name;
    private TrainList trainlist;
    private LocalTime startTime;
    private int lineNum;
    private boolean direction;
    private int beforeStationIndex;
    private int nextStationIndex;
    private int block;
    private int[] density;
    private Random random;
    private int minTime;

    public int getLineNum() {
        return lineNum;
    }

    public Train(TrainList trainlist, LocalTime startTime, String name, int lineNum, boolean direction, int block) {
        this.random = new Random();
        this.startTime = startTime;
        this.name = name;
        this.lineNum = lineNum;
        this.direction = direction;
        this.trainlist = trainlist;
        this.block = block;
        this.density = new int[this.block];
        for (int i = 0; i < this.block; i++) {
            this.density[i] = random.nextInt(101);
        }
    }

    public int trainPos(int target, boolean targetDirection) {
        int nowSec = convertTimeToSec(LocalTime.now());
        int startSec = convertTimeToSec(this.startTime);
        int count = (nowSec - startSec) / this.trainlist.getTotalTimeWeight(this.lineNum - 1); //왕복 횟수
        int remainderSec = (nowSec - startSec) % this.trainlist.getTotalTimeWeight(this.lineNum - 1); //왕복하고 남은 시간
        this.beforeStationIndex = 0;
        this.nextStationIndex = 0;

        int result = 0;

        switch (this.trainlist.getType(this.lineNum - 1)) {
            case 0: //역이 직선형인경우
                if ((count % 2) == 0) { //정방향
                    //열차 위치 계산
                    for (int i = 0; i < this.trainlist.getTimeWeight(this.lineNum - 1).size(); i++) {
                        remainderSec -= this.trainlist.getTimeWeight(this.lineNum - 1).get(i);
                        if (remainderSec < 0) {
                            beforeStationIndex = i;
                            nextStationIndex = i + 1;
                            break;
                        }
                    }
                    //시간 계산
                    if (nextStationIndex == this.trainlist.getLines(this.lineNum - 1).size() - 1
                    && target == this.trainlist.getLines(this.lineNum - 1).get(nextStationIndex).getName()) {
                        return -remainderSec;
                    }
                    for (int i = nextStationIndex; this.trainlist.getLines(this.lineNum - 1).get(i).getName() != target;) {
                        result += this.trainlist.getTimeWeight(this.lineNum - 1).get(i - 1);
                        if (i + 1 < this.trainlist.getLines(this.lineNum - 1).size())
                            i++;
                        else
                            return Integer.MAX_VALUE;
                    }
                } else { //역박향
                    for (int i = this.trainlist.getTimeWeight(this.lineNum - 1).size() - 1; i >= 0; i--) {
                        remainderSec -= this.trainlist.getTimeWeight(this.lineNum - 1).get(i);
                        if (remainderSec < 0) {
                            beforeStationIndex = i + 1;
                            nextStationIndex = i;
                            break;
                        }
                    }
                    if (nextStationIndex == 0
                    && target == this.trainlist.getLines(this.lineNum - 1).get(nextStationIndex).getName()) {
                        return -remainderSec;
                    }
                    for (int i = nextStationIndex; this.trainlist.getLines(this.lineNum - 1).get(i).getName() != target;) {
                        if (i - 1 >= 0) {
                            result += this.trainlist.getTimeWeight(this.lineNum - 1).get(i);
                            i--;
                        }
                        else
                            return Integer.MAX_VALUE;
                    }
                }
                break;
            case 1: //역이 루프형태인 경우
                if (targetDirection != direction)
                    return Integer.MAX_VALUE;
                if (direction == true) { //정방향
                    for (int i = 0; i < this.trainlist.getTimeWeight(this.lineNum - 1).size(); i++) {
                        remainderSec -= this.trainlist.getTimeWeight(this.lineNum - 1).get(i);
                        if (remainderSec < 0) {
                            beforeStationIndex = i;
                            nextStationIndex = i + 1;
                            break;
                        }
                    }
                    for (int i = nextStationIndex; this.trainlist.getLines(this.lineNum - 1).get(i).getName() != target;) {
                        result += this.trainlist.getTimeWeight(this.lineNum - 1).get(i - 1);
                        if (i + 1 < this.trainlist.getLines(this.lineNum - 1).size())
                            i++;
                        else
                            i = 0;
                    }
                } else { //역방향
                    for (int i = this.trainlist.getTimeWeight(this.lineNum - 1).size() - 1; i >= 0; i--) {
                        remainderSec -= this.trainlist.getTimeWeight(this.lineNum - 1).get(i);
                        if (remainderSec < 0) {
                            beforeStationIndex = i + 1;
                            nextStationIndex = i;
                            break;
                        }
                    }
                    for (int i = nextStationIndex; this.trainlist.getLines(this.lineNum - 1).get(i).getName() != target;) {
                        result += this.trainlist.getTimeWeight(this.lineNum - 1).get(i);
                        if (i - 1 >= 0)
                            i--;
                        else
                            i = this.trainlist.getLines(this.lineNum - 1).size() - 1;
                    }
                }
                break;
        }
        result -= remainderSec;
        return result;
    }

    public int convertTimeToSec(LocalTime time) {
        int result = 0;
        result += time.getHour() * 60 * 60;
        result += time.getMinute() * 60;
        result += time.getSecond();
        return result;
    }

    public int getBefore() {
        return this.trainlist.getLines(this.lineNum - 1).get(this.beforeStationIndex).getName();
    }
    public int getnext() {
        return this.trainlist.getLines(this.lineNum - 1).get(this.nextStationIndex).getName();
    }
    public int getMinTime() {
        return this.minTime;
    }
    public int[] getDensity() {
        return this.density;
    }
    public int getblock() {
        return this.block;
    }

//    public String getInfo() {
//        return this.name +
//                "/" + this.trainlist.getLines(this.lineNum - 1).get(this.beforeStationIndex) +
//                "/" + this.trainlist.getLines(this.lineNum - 1).get(this.nextStationIndex);
//    }

    public void setMinTime(int time) {
        this.minTime = time;
    }
}
