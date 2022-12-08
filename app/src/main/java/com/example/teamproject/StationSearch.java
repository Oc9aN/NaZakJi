package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamproject.navigation.Dijkstra;
import com.example.teamproject.navigation.Graph;
import com.example.teamproject.train.Train;
import com.example.teamproject.train.TrainList;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.Sheet;
import jxl.read.biff.BiffException;

public class StationSearch extends AppCompatActivity {

    Graph graph; //그래프 생성 -> 지하절 역
    TrainList trainlist;
    private int type;

    TextView start_station;
    TextView middle_station;
    TextView end_station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        graph = new Graph();
        graph.setGraph(excelIterator("stations.xls"));

        trainlist = new TrainList(graph);
        trainlist.setLineList(excelIterator("line.xls"));

        setTrain(excelIterator("train.xls"));

        setContentView(R.layout.activity_station_search);
        start_station = (TextView)findViewById(R.id.real_start_station);
        middle_station = (TextView)findViewById(R.id.real_middle_station);
        end_station = (TextView)findViewById(R.id.real_end_station);

        //데이터 가져오기
        Intent intent = getIntent();
        String stt = intent.getStringExtra("stt");
        String number = intent.getStringExtra("number");
        if(stt != null){
            if (stt.equals("start")) {
                start_station.setText(number);
            } else if (stt.equals("middle")) {
                middle_station.setText(number);
            } else if (stt.equals("end")) {
                end_station.setText(number);
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

        // 최단거리 버튼 클릭시 액티비티 전환
        Button distance_btn = (Button) findViewById(R.id.short_distance);
        distance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                int start = 0, end = 0, mid = 0;
                try {
                    mid = Integer.parseInt(middle_station.getText().toString());
                } catch (Exception e) {
                    mid = 0;
                }
                try {
                    start = Integer.parseInt(start_station.getText().toString());
                    end = Integer.parseInt(end_station.getText().toString());
                    getResult(start, end, mid);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "출발역과 도착역을 설정해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // 최소환승 버튼 클릭시 액티비티 전환
        Button transfer_btn = (Button) findViewById(R.id.short_transfer);
        transfer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 4;
                int start = 0, end = 0, mid = 0;
                try {
                    mid = Integer.parseInt(middle_station.getText().toString());
                } catch (Exception e) {
                    mid = 0;
                }
                try {
                    start = Integer.parseInt(start_station.getText().toString());
                    end = Integer.parseInt(end_station.getText().toString());
                    getResult(start, end, mid);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "출발역과 도착역을 설정해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // 최소시간 버튼 클릭시 액티비티 전환
        Button time_btn = (Button) findViewById(R.id.short_time);
        time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 0;
                int start = 0, end = 0, mid = 0;
                try {
                    mid = Integer.parseInt(middle_station.getText().toString());
                } catch (Exception e) {
                    mid = 0;
                }
                try {
                    start = Integer.parseInt(start_station.getText().toString());
                    end = Integer.parseInt(end_station.getText().toString());
                    getResult(start, end, mid);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "출발역과 도착역을 설정해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // 최소비용 버튼 클릭시 액티비티 전환
        Button money_btn = (Button) findViewById(R.id.short_money);
        money_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 2;
                int start = 0, end = 0, mid = 0;
                try {
                    mid = Integer.parseInt(middle_station.getText().toString());
                } catch (Exception e) {
                    mid = 0;
                }
                try {
                    start = Integer.parseInt(start_station.getText().toString());
                    end = Integer.parseInt(end_station.getText().toString());
                    getResult(start, end, mid);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "출발역과 도착역을 설정해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                start_station.setText(result);
            }
        }
        else if(requestCode == 2){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                middle_station.setText(result);
            }
        }
        else if(requestCode == 3){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                end_station.setText(result);
            }
        }
    }

    public void getResult(int start, int end, int mid) {
        int time = 0;
        int distance = 0;
        int cost = 0;
        int transfer = 0;

        StringBuilder str = new StringBuilder();

        if (mid == 0) { //경유역이 없는 경우
            Dijkstra.ResultPair route = Dijkstra.dijkstra(graph, start, end, type);
            time = route.getWeight().getTime();
            distance = route.getWeight().getDistance();
            cost = route.getWeight().getCost();
            for (int i = 0; i < route.getRoute().size(); i++) {
                str.append(route.getRoute().get(i).toString());
                if (i < route.getRoute().size() - 1) {
                    str.append("->");
                }
                if (route.getRoute().get(i).isTransfer())
                    transfer += 1;
            }
//            str.append(this.trainlist.findMinTrain(route.getRoute()));
//            str.append(" 시간: " + time + " 거리: " + distance + " 비용: " + cost);
        } else { //경유역이 있는경우
            Dijkstra.ResultPair route1 = Dijkstra.dijkstra(graph, start, mid, type);
            Dijkstra.ResultPair route2 = Dijkstra.dijkstra(graph, mid, end, type);
            time = route1.getWeight().getTime() + route2.getWeight().getTime();
            distance = route1.getWeight().getDistance() + route2.getWeight().getDistance();
            cost += route1.getWeight().getCost() + route2.getWeight().getCost();
            route1.getRoute().addAll(route2.getRoute());
            for (int i = 0; i < route1.getRoute().size(); i++) {
                str.append(route1.getRoute().get(i).toString());
                if (i < route1.getRoute().size() - 1) {
                    str.append("->");
                }
                if (route1.getRoute().get(i).isTransfer())
                    transfer += 1;
            }
//            str.append(this.trainlist.findMinTrain(route1.getRoute()));
//            str.append(" 시간: " + time + " 거리: " + distance + " 비용: " + cost);
        }

        TextView time_taken = (TextView)findViewById(R.id.time_taken);
        time_taken.setText(LocalTime.of(time/3600, (time%3600)/60, (time%3600)%60).toString());

        TextView route = (TextView)findViewById(R.id.route);
        route.setText(str);

        TextView cost_taken = (TextView)findViewById(R.id.cost_taken);
        cost_taken.setText(cost + "원");

        TextView transfer_count = (TextView)findViewById(R.id.transfer_count);
        transfer_count.setText(transfer + "회");
    }

    public void setTrain(ArrayList<ArrayList<String>> excel) {
        for (ArrayList<String> row: excel) {
            LocalTime startTime = LocalTime.of(Integer.parseInt(row.get(0)), Integer.parseInt(row.get(1)));
            String name = row.get(2);
            int lineNum = Integer.parseInt(row.get(3));
            boolean direction =row.get(4).equals("forward");
            trainlist.addTrains(new Train(trainlist, startTime, name, lineNum, direction));
        }
    }

    public ArrayList<ArrayList<String>> excelIterator(String file) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        try {
            //xlsx는 읽기 어려워서 xls로 파일 형식 바꿔서 저장후 읽음.
            InputStream is = getBaseContext().getResources().getAssets().open(file); //엑셀 파일 열기

            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {
                Sheet sheet = wb.getSheet(0); //엑셀 시트 가져오기

                if (sheet != null) {
                    int colTotal = sheet.getColumns();
                    int rowIndexStart = 1;
                    int rowTotal = sheet.getColumn(colTotal - 1).length;
                    int index = 0;
                    for (int row = rowIndexStart; row < rowTotal; row++, index++) { //행과 열을 순서대로 가져옴
                        result.add(new ArrayList<>());
                        for (int col = 0; col < colTotal; col++) {
                            result.get(index).add(sheet.getCell(col, row).getContents());
                        }
                    }
                }
            }
        } catch (IOException | BiffException e) {
            Log.d("Main", e.getMessage());
        }
        return result;
    }
}