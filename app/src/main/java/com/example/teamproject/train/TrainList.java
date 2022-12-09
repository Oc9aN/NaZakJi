package com.example.teamproject.train;

import android.util.Log;

import com.example.teamproject.navigation.Edge;
import com.example.teamproject.navigation.Graph;

import java.time.LocalTime;
import java.util.ArrayList;

public class TrainList {
    public class LineInfo {
        protected ArrayList<Edge> lines; //기차가 다니는 경로
        protected int totalTimeWeight;
        protected ArrayList<Integer> timeWeight;
        protected int type;

        LineInfo() {
            lines = new ArrayList<>();
            timeWeight = new ArrayList<>();
        }

        public ArrayList<Edge> getLines() {
            return lines;
        }
    }

    private ArrayList<LineInfo> lineList; //호선 배열
    private ArrayList<ArrayList<Train>> trains;

    private Graph graph;

    public TrainList(Graph graph) {
        lineList = new ArrayList<>();
        trains = new ArrayList<>();
        this.graph = graph;
    }

    public void addTrains(Train train) {
        if (this.trains.size() < train.getLineNum()) {
            this.trains.add(new ArrayList<>());
        }
        this.trains.get(train.getLineNum() - 1).add(train);
    }

    public void setLineList(ArrayList<ArrayList<String>> excel) {
        for (ArrayList<String> row: excel) {
            int[] int_col = {0,0,0,0};
            int i = 0;
            for (String col: row) {
                int_col[i++] = Integer.parseInt(col);
            }
            addLine(int_col[0], int_col[1], int_col[2], int_col[3]);
        }
    }

    //노선을 시작역, 끝역, 호선입력하면 만들어주는 함수
    public void addLine(int startStation, int endStation, int lineNum, int type) {
        this.lineList.add(new LineInfo());
        this.lineList.get(lineNum - 1).type = type;
        ArrayList<Integer> S = new ArrayList<>(); //방문한 노드의 인덱스 집합
        int currentIndex = graph.hashToIndex(startStation); //경로 시작부분 인덱스

        this.lineList.get(lineNum - 1).lines.add(graph.getGraph().get(currentIndex).get(0));
        graph.getGraph().get(currentIndex).get(0).addLineNum(lineNum);
        while(true) {
            int finalIndex = 0; //다음에 갈 인덱스
            int i;
            for (i = 1; i < graph.getGraph().get(currentIndex).size(); i++) { //이웃체크
                int nextName = graph.getGraph().get(currentIndex).get(i).getName();
                int nextIndex = graph.hashToIndex(nextName);
                if (!S.contains(nextIndex) || (S.size() > 3 && startStation == nextName)) { //이미 방문한 노드가 아니면 //노선 형식이 루프인경우는 || 옆쪽으로 구분한다.
                    if (lineNum == (nextName / 100)) { //호선 우선
                        finalIndex = nextIndex;
                        break;
                    } else { //호선말고 다음 우선
                        finalIndex = nextIndex;
                        if (endStation == nextName) { //끝역이라면 이웃역 찾는 반복 종료
                            break;
                        }
                    }
                }
            }
            i = graph.getGraph().get(currentIndex).size() == i? i - 1: i;
            graph.getGraph().get(finalIndex).get(0).addLineNum(lineNum);
            this.lineList.get(lineNum - 1).lines.add(graph.getGraph().get(finalIndex).get(0));
            this.lineList.get(lineNum - 1).timeWeight.add(graph.getGraph().get(currentIndex).get(i).getWeight().getTime());
            S.add(currentIndex);
            currentIndex = finalIndex;
            //끝인경우
            if (endStation == graph.getGraph().get(finalIndex).get(0).getName()) {
                break;
            }
        }
        for (int data : this.lineList.get(lineNum - 1).timeWeight) {
            this.lineList.get(lineNum - 1).totalTimeWeight += data;
        }
    }

    public Train findMinTrain(ArrayList<Edge> route) {
        Train minTrain = null;
        int minTime = Integer.MAX_VALUE;
        int curLineNum = route.get(0).compareLine(route.get(1).getLineNum());
        boolean targetDirection = false;
        if (curLineNum != -1) {
            for (int i = 0; i < this.lineList.get(curLineNum - 1).lines.size() - 1; i++) {
                if (this.lineList.get(curLineNum - 1).lines.get(i).getName() == route.get(0).getName()
                && this.lineList.get(curLineNum - 1).lines.get(i + 1).getName() == route.get(1).getName()) {
                    targetDirection = true;
                }
            }
            ArrayList<Train> curTrainList = this.trains.get(curLineNum - 1);
            int minIndex = 0;
            for (int j = 0; j < curTrainList.size(); j++) {
                int time = curTrainList.get(j).trainPos(route.get(0).getName(), targetDirection);
                if (time < minTime) {
                    minTime = time;
                    minIndex = j;
                    minTrain = curTrainList.get(minIndex);
                }
            }
        }
        minTrain.setMinTime(minTime);
        return minTrain;
    }

    public Graph getGraph() {
        return graph;
    }

    public ArrayList<ArrayList<Train>> getTrains() {
        return trains;
    }

    public ArrayList<LineInfo> getLineList() {
        return lineList;
    }

    public ArrayList<Edge> getLines(int lineNum) {
        return lineList.get(lineNum).lines;
    }

    public int getTotalTimeWeight(int lineNum) {
        return lineList.get(lineNum).totalTimeWeight;
    }

    public ArrayList<Integer> getTimeWeight(int lineNum) {
        return lineList.get(lineNum).timeWeight;
    }

    public int getType(int lineNum) {
        return lineList.get(lineNum).type;
    }
}
