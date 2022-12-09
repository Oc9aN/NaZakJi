package com.example.teamproject.navigation;

import java.util.ArrayList;

public class Edge implements Cloneable { //역에 대한 정보를 저장하는 클래스
    public static class Weight {
        private int time;
        private int distance;
        private int cost;

        Weight(int t, int d, int c) {
            time = t;
            distance = d;
            cost = c;
        }

        public String getString() {
            return Integer.toString(time) + " " + Integer.toString(distance) + " " + Integer.toString(cost);
        }

        public int getTime() {
            return time;
        }

        public int getDistance() {
            return distance;
        }

        public int getCost() {
            return cost;
        }
    }

    private Weight weight;
    private int name;

    private ArrayList<Integer> lineNum;

    private int lastName; //이전 역을 저장 -> 경로추적을 위함. (경로추적은 도착지부터 뒤로가면서 경로를 파익함)

    private boolean isTransfer;

    Edge(int name_, int t, int d, int c) {
        name = name_;
        weight = new Weight(t, d, c);
        isTransfer = false;
        lineNum = new ArrayList<>();
    }

    //밑으로 다 getter, setter
    public boolean isTransfer() {
        return isTransfer;
    }

    public void setTransfer(boolean transfer) {
        isTransfer = transfer;
    }

    public int getName() {
        return name;
    }

    public String getWeightToString() {
        return weight.getString();
    }

    public Weight getWeight() {
        return weight;
    }

    public int getLastName() {
        return lastName;
    }

    public void setLastName(int lastName) {
        this.lastName = lastName;
    }

    public ArrayList<Integer> getLineNum() {
        return lineNum;
    }

    public void addLineNum(Integer lineNum) {
        if (!this.lineNum.contains(lineNum)) {  //같은 호선 빼고
            this.lineNum.add(lineNum);
        }
    }

    public int compareLine(ArrayList<Integer> com) {
        for (Integer num: com) {
            if (this.lineNum.contains(num))
                return num;
        }
        return -1;
    }

    @Override
    public String toString() {
        return this.name + "";
    }

    @Override
    protected Edge clone() throws CloneNotSupportedException {
        return (Edge) super.clone();
    }
}
