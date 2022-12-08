package com.example.teamproject.navigation;

import android.util.Log;

import java.util.ArrayList;

public class Graph { 
//리스트 예시
//Start
//101 0 0 0
//102 200 500 200
//123 480 400 200
//201 1000 500 300
//End -> 101번역이 102, 123, 201과 이어져있음, 102가는 시간은 200, 거리는 500, 비용은 200인셈
    private ArrayList<ArrayList<Edge>> graph; //자료구조 그래프 2차원 리스트로 각 리스트의 첫번째 값은 역 그자체임. 인접리스트 방식으로 표현한 그래프

    private ArrayList<Integer> stationInfo; //호선마다 역의 수를 저장함. 해시를 이용하기 위함(나중에 역을 추가하면 빠르게 문제해결 가능)
    private static int stationCount = 9; //9호선까지있음

    public Graph() {
        this.graph = new ArrayList<>();
        this.stationInfo = new ArrayList<>();
        for (int i = 0; i < this.stationCount; i++) {
            this.stationInfo.add(0); //모든 호선은 처음에 역이 0개
        }
    }

    public void setGraph(ArrayList<ArrayList<String>> excel) {
        for (ArrayList<String> row: excel) {
            int[] int_col = {0,0,0,0,0};
            int i = 0;
            for (String col: row) {
                int_col[i++] = Integer.parseInt(col);
            }
            pushEdge(int_col[0], int_col[1], int_col[2], int_col[3],int_col[4]);
        }
    }

    public int hashToIndex(int name) { //그래프가 역 순서대로 정렬되어있으므로 그것을 이용해 역 이름으로 인덱스를 찾음
        int line = name / 100; //호선
        int station = name % 100; //역 번호
        int index = 0;
        for (int i = 0; i < line - 1; i++)
            index += this.stationInfo.get(i);
        index += station - 1;
        return index;
    }

    //start로 시작해 정보가 나오고 End가 나오는데 정보의 첫번째값은 자기자신. 두번째부터가 어디와 이어져있는지 나타냄(디버그용)
    //맨위 예시 주석처럼 나옴.
    public void PrintGraph() {
        for (int i = 0; i < this.graph.size(); i++) {
            for (int j = 0; j < this.graph.get(i).size(); j++) {
                if (j == 0) Log.d("Main", "Start:");
                Log.d("Main", this.graph.get(i).get(j).getName() + " "
                        + graph.get(i).get(j).getWeightToString());
            }
            Log.d("Main", "End");
        }
        for (int i = 0; i < this.graph.size(); i++) {
            Log.d("Main", this.graph.get(i).get(0).getName() + " " + i);
        }
    }

    public void pushEdge(int x, int y, int t, int d, int c) { //매개변수: 역이름 1, 역이름 2, 시간, 거리, 비용
        //역이름으로 간선을 생성
        Edge x_ = new Edge(x, 0, 0, 0); //자기자신은 가중치가 없음
        Edge y_ = new Edge(y, 0, 0, 0);

        //역이름 1번에 대해서 검사. 이미 존재하는지
        int xIndex = getIndex(x);
        //없으면 노드를 생성해준다.
        if (xIndex == this.graph.size()) {
            this.graph.add(new ArrayList<>());
            this.graph.get(xIndex).add(x_); //0번째 값이 곧 자기자신. 즉 x_의 노드를 만든것과 같음
            this.stationInfo.set((x / 100) - 1, this.stationInfo.get((x / 100) - 1) + 1); //set(호선, 역의 수)이다.
            x_.addLineNum(x/100);
        }

        //위와 같지만 역이름 2번에 대한 검사
        int yIndex = getIndex(y);
        if (yIndex == this.graph.size()) {
            this.graph.add(new ArrayList<>());
            this.graph.get(yIndex).add(y_);
            this.stationInfo.set((y / 100) - 1, this.stationInfo.get((y / 100) - 1) + 1);
            y_.addLineNum(y/100);
        }

        this.graph.get(xIndex).add(new Edge(y, t, d, c));
        this.graph.get(yIndex).add(new Edge(x, t, d, c));
    }

    public int getIndex(int name) { //필요한 인덱스값을 구하는 함수.
        int index = this.graph.size();
        for (int i = 0; i < this.graph.size(); i++) {
            if (this.graph.get(i).get(0).getName() == name) { //이름을 확인해서 이미 만들어진 노드인지 확인한다.
                index = i; //이미 만들어진 노드인경우 그 인덱스를 반환한다.
                break;
            }
        }
        return index; //없는 노드라면 값이 안변하고 size가 반환되어 노드를 새로 만들라는 뜻이고
        //이미 만들어진 노드라면 값이 변해서 이미 만들어진 노드의 인덱스를 반환하게 하여 그위치를 파악한다.
    }

    public ArrayList<ArrayList<Edge>> getGraph() {
        return this.graph;
    }
    public int getSize() { return this.graph.size(); }
}
