package com.example.teamproject.navigation;

import java.util.ArrayList;
import java.util.Collections;

public class Dijkstra {
    public static class ResultPair {
        protected ArrayList<Edge> route;
        protected Edge.Weight weight;

        public ResultPair(ArrayList<Edge> route, Edge.Weight weight) {
            this.route = route;
            this.weight = weight;
        }

        public ArrayList<Edge> getRoute() {
            return route;
        }

        public Edge.Weight getWeight() {
            return weight;
        }
    }

    public static ResultPair dijkstra(Graph graph_, int start, int end, int type) {
        if (type == 4) {
            return minTransfer(graph_, start, end);
        }
        ArrayList<Integer> time = new ArrayList<>();
        ArrayList<Integer> dist = new ArrayList<>(); //각 노드들의 거리를 저장함
        ArrayList<Integer> cost = new ArrayList<>();
        ArrayList<Integer> weightList = new ArrayList<>();
        ArrayList<Integer> Q = new ArrayList<>(); //방문하지 않은 노드의 인덱스 집합
        ArrayList<Integer> S = new ArrayList<>(); //방문한 노드의 인덱스 집합

        int[] tdc = {0,0,0};

        for (int i = 0; i < graph_.getSize(); i++) {
            time.add(Integer.MAX_VALUE);
            dist.add(Integer.MAX_VALUE);
            cost.add(Integer.MAX_VALUE);
            weightList.add(Integer.MAX_VALUE);
            Q.add(i);
        } //거리값을 무한값으로 초기화한다.
        time.set(graph_.hashToIndex(start), 0); //시작지점 자신은 가중치가 0임.
        dist.set(graph_.hashToIndex(start), 0);
        cost.set(graph_.hashToIndex(start), 0);
        weightList.set(graph_.hashToIndex(start), 0);

        while (!Q.isEmpty()) { //방문하지 않은 역이 없을때까지 루프
            int currentIndex = getMin(weightList, Q); //현재 방문한 역 인덱스 -> 처음엔 시작지점임, 거리가 최소인 지역부터 순서대로
            Q.remove((Object)currentIndex); //방문한 역 인덱스는 방문하지 않은 집합에서 제거
            S.add(currentIndex); //방문한 집합에 추가
            ArrayList<Edge> curStationEdge = graph_.getGraph().get(currentIndex); //현재 역의 정보 리스트

            for (int i = 1; i < curStationEdge.size(); i++) { //이웃들의 가중치 체크
                //출발점 부터 현재위치까지의 가중치 + 현재위치로부터 인접한 역까지의 가중치 = 다음으로 이동할 위치의 최소 가중치

                Edge nextStation = curStationEdge.get(i);
                int t = time.get(currentIndex) + nextStation.getWeight().getTime();
                int d = dist.get(currentIndex) + nextStation.getWeight().getDistance();
                int c = cost.get(currentIndex) + nextStation.getWeight().getCost();
                int weight = weightList.get(currentIndex);
                //설정한 가중치 기준에 따라 -> 시간, 거리, 가격, 환승
                switch (type) {
                    case 0:
                        weight += nextStation.getWeight().getTime();
                        break;
                    case 1:
                        weight += nextStation.getWeight().getDistance();
                        break;
                    case 2:
                        weight += nextStation.getWeight().getCost();
                        break;
                    case 3:
                        if ((curStationEdge.get(0).getName() / 100) != (nextStation.getName() / 100))
                            weight += 100;
                        weight += 1;
                        break;
                }

                //hash를 이용해 인접한 역의 이름으로 인덱스를 찾음
                int branchIndex = graph_.hashToIndex(nextStation.getName());
                //계산된 가중치가 기존것 보다 작은경우 바꿘준다. hash로 역이름을 인덱스로 가져옴. 그인덱스로 dist에서 비교
                if (weight < weightList.get(branchIndex)) {
                    weightList.set(graph_.hashToIndex(nextStation.getName()), weight);
                    time.set(graph_.hashToIndex(nextStation.getName()), t);
                    dist.set(graph_.hashToIndex(nextStation.getName()), d);
                    cost.set(graph_.hashToIndex(nextStation.getName()), c);
                    //경로 추적을 위해 어느역으로부터 최소값이 갱신된것인지 저장 index->current로 가는 경로가 최소이므로 index의 lastname은 현재역이 된다.
                    graph_.getGraph().get(branchIndex).get(0).setLastName(curStationEdge.get(0).getName());
                }
            }
        }
        return new ResultPair(tracking(graph_, start, end), new Edge.Weight(time.get(graph_.hashToIndex(end)), dist.get(graph_.hashToIndex(end)), cost.get(graph_.hashToIndex(end))));
    }

    private static ResultPair minTransfer(Graph graph_, int start, int end) {
        ArrayList<ResultPair> result = new ArrayList<>();
        ArrayList<Integer> count = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            count.add(0);
            result.add(Dijkstra.dijkstra(graph_, start, end, i));
            for (Edge e: result.get(i).route) {
                if (e.isTransfer()) {
                    count.set(i, count.get(i) + 1);
                }
            }
        }
        int index = count.indexOf(Collections.min(count));
        return result.get(index);
    }

    private static ArrayList<Edge> tracking(Graph graph_, int start, int end) { //최단 경로를 추적함
        ArrayList<Edge> route = new ArrayList<>();
        Edge curr;
        int currName = end;
        while (currName != start) {
            try {
                curr = graph_.getGraph().get(graph_.hashToIndex(currName)).get(0).clone();
                route.add(curr);
                currName = curr.getLastName();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        route.add(graph_.getGraph().get(graph_.hashToIndex(start)).get(0));
        Collections.reverse(route);
        setTransfer(route);
        return route;
    }

    //아직 방문하지 않는 집합인 Q에서 가장 가중치가 낮은 인덱스를 찾음
    private static int getMin(ArrayList<Integer> dist, ArrayList<Integer> Q) {
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < Q.size(); i++) {
            if (dist.get(Q.get(i)) < min) {
                min = dist.get(Q.get(i));
                minIndex = Q.get(i);
            }
        }
        return minIndex;
    }

    private static void setTransfer(ArrayList<Edge> route) {
        int curLine = 0;
        if (route.get(0).getLineNum().size() <= 1) {
            curLine = route.get(0).getLineNum().get(0);
        } else {
            curLine = route.get(0).compareLine(route.get(1).getLineNum());
        }
        for (int i = 1; i < route.size() - 1; i++) {
            //다음역이 curLine과 다른경우는 환승
            int nextLine = route.get(i).compareLine(route.get(i + 1).getLineNum());
            if (nextLine != curLine) {
                route.get(i).setTransfer(true);
                curLine = nextLine;
            }
        }
    }
}
