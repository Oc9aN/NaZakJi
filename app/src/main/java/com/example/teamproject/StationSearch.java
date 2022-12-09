package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamproject.navigation.Dijkstra;
import com.example.teamproject.navigation.Graph;
import com.example.teamproject.train.Train;
import com.example.teamproject.train.TrainList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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

    //뒤로가기
    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if(keycode == android.view.KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(), SideMenu.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

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
        //출발역 취소
        Button start_cancel = (Button) findViewById(R.id.start_cancel);
        start_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                start_station.setText("start station");
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
        //환승역 취소
        Button middle_cancel = (Button) findViewById(R.id.middle_cancel);
        middle_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                middle_station.setText("middle station");
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
        //도착역 취소
        Button end_cancel = (Button) findViewById(R.id.end_cancel);
        end_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                end_station.setText("end station");
            }
        });

        // 최단거리 버튼 클릭시
        Button distance_btn = (Button) findViewById(R.id.short_distance);
        distance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                checkStations();
            }
        });

        // 최소환승 버튼 클릭시
        Button transfer_btn = (Button) findViewById(R.id.short_transfer);
        transfer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 4;
                checkStations();
            }
        });

        // 최소시간 버튼 클릭시
        Button time_btn = (Button) findViewById(R.id.short_time);
        time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 0;
                checkStations();
            }
        });

        // 최소비용 버튼 클릭시
        Button money_btn = (Button) findViewById(R.id.short_money);
        money_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 2;
                checkStations();
            }
        });

        Button bookmark_route = (Button) findViewById(R.id.bookmark_route);
        bookmark_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder route = new StringBuilder();
                int start = 0;
                int end = 0;
                try {
                    start = Integer.parseInt(start_station.getText().toString());
                    end = Integer.parseInt(end_station.getText().toString());
                    route.append(start + "->");
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "출발역과 도착역을 설정해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int mid = Integer.parseInt(middle_station.getText().toString());
                    route.append(mid + "->" + end);
                } catch (Exception e) {
                    route.append(end);
                }
                try{ //txt에 ""->""형식으로 저장
                    String line = null;
                    ArrayList<String> arrStr = new ArrayList<>();
                    BufferedReader buf = new BufferedReader(new FileReader(getFilesDir() + "/" + "Bookmark.txt"));
                    while((line = buf.readLine()) != null){
                        arrStr.add(line);
                    }
                    buf.close();
                    if (arrStr.contains(route.toString())) {
                        Toast.makeText(getApplicationContext(), "이미 즐겨찾기에 있는 항목입니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    BufferedWriter writer = new BufferedWriter(new FileWriter(getFilesDir() + "/" + "Bookmark.txt", true));
                    writer.append(route);
                    writer.newLine();
                    writer.close();
                    Toast.makeText(getApplicationContext(), "즐겨찾기에 추가됨.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
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
                return;
            }
        }
        else if(requestCode == 2){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                middle_station.setText(result);
                return;
            }
        }
        else if(requestCode == 3){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                end_station.setText(result);
                return;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent.getStringExtra("start") != null) {
            start_station.setText(intent.getStringExtra("start"));
            middle_station.setText(intent.getStringExtra("mid"));
            end_station.setText(intent.getStringExtra("end"));
        }
    }

    public void checkStations() { //역입력 정확한지 체크
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
            Toast.makeText(getApplicationContext(), "출발역과 도착역을 설정해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    //길찾기알고리즘 돌리고 결과 셋팅
    public void getResult(int start, int end, int mid) {
        int time = 0;
        int distance = 0;
        int cost = 0;
        int transfer = 0;
        ArrayList<String> transferStation = new ArrayList<>();
        Train trainInfo; //이름/이전역/다음역/남은시간/혼잡도

        StringBuilder str = new StringBuilder();

        if (mid == 0) { //경유역이 없는 경우
            Dijkstra.ResultPair route = Dijkstra.dijkstra(graph, start, end, type);

            time = route.getWeight().getTime();
            distance = route.getWeight().getDistance();
            cost = route.getWeight().getCost();

            trainInfo = this.trainlist.findMinTrain(route.getRoute());

            for (int i = 0; i < route.getRoute().size(); i++) {
                str.append(route.getRoute().get(i).toString());
                if (i < route.getRoute().size() - 1) {
                    str.append("->");
                }
                if (route.getRoute().get(i).isTransfer()) { //환승 횟수 체크
                    transfer += 1;
                    transferStation.add(route.getRoute().get(i).toString());
                }
            }
        } else { //경유역이 있는경우
            Dijkstra.ResultPair route1 = Dijkstra.dijkstra(graph, start, mid, type);
            Dijkstra.ResultPair route2 = Dijkstra.dijkstra(graph, mid, end, type);

            time = route1.getWeight().getTime() + route2.getWeight().getTime();
            distance = route1.getWeight().getDistance() + route2.getWeight().getDistance();
            cost += route1.getWeight().getCost() + route2.getWeight().getCost();

            route2.getRoute().remove(0);
            route1.getRoute().addAll(route2.getRoute());

            trainInfo = this.trainlist.findMinTrain(route1.getRoute());
            for (int i = 0; i < route1.getRoute().size(); i++) {
                str.append(route1.getRoute().get(i).toString());
                if (i < route1.getRoute().size() - 1) {
                    str.append("->");
                }
                if (route1.getRoute().get(i).isTransfer()) { //환승 횟수 체크
                    transfer += 1;
                    transferStation.add(route1.getRoute().get(i).toString());
                }
            }
        }

        TextView time_taken = (TextView)findViewById(R.id.time_taken);
        TextView route = (TextView)findViewById(R.id.route);
        TextView cost_taken = (TextView)findViewById(R.id.cost_taken);
        TextView transfer_count = (TextView)findViewById(R.id.transfer_count);
        TextView distance_taken = (TextView)findViewById(R.id.distance_taken);
        TextView previous_station = (TextView)findViewById(R.id.previous_station);
        TextView next_station = (TextView)findViewById(R.id.next_station);
        TextView time_left = (TextView)findViewById(R.id.time_left);
        ImageView[] train_density = new ImageView[5];
        train_density[0] = (ImageView)findViewById(R.id.train1);
        train_density[1] = (ImageView)findViewById(R.id.train2);
        train_density[2] = (ImageView)findViewById(R.id.train3);
        train_density[3] = (ImageView)findViewById(R.id.train4);
        train_density[4] = (ImageView)findViewById(R.id.train5);

        time_taken.setText(LocalTime.of(time/3600, (time%3600)/60, (time%3600)%60).toString());

        SpannableString spannableString = new SpannableString(str);
        for (int i = 0; i < transferStation.size(); i++) {
            int start_index = str.indexOf(transferStation.get(i));
            int end_index = start_index + transferStation.get(i).length();
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#000088")), start_index, end_index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        route.setText(spannableString);

        cost_taken.setText(cost + "원");

        transfer_count.setText(transfer + "회");

        distance_taken.setText(distance + "m");

        previous_station.setText(trainInfo.getBefore() + "역");

        next_station.setText(trainInfo.getnext() + "역");

        time_left.setText(LocalTime.of(trainInfo.getMinTime()/3600, (trainInfo.getMinTime()%3600)/60, (trainInfo.getMinTime()%3600)%60).toString());

        for (int i = 0; i < trainInfo.getblock(); i++) {
            int drawableID = 0;
            if (trainInfo.getDensity()[i] < 30) { //원활
                drawableID = R.drawable.greentrain;
            } else if (trainInfo.getDensity()[i] < 60) { //보통
                drawableID = R.drawable.orangetrain;
            } else { //혼잡
                drawableID = R.drawable.redtrain;
            }
            train_density[i].setImageResource(drawableID);
        }
    }

    //열차 셋팅
    public void setTrain(ArrayList<ArrayList<String>> excel) {
        for (ArrayList<String> row: excel) {
            LocalTime startTime = LocalTime.of(Integer.parseInt(row.get(0)), Integer.parseInt(row.get(1)));
            String name = row.get(2);
            int lineNum = Integer.parseInt(row.get(3));
            boolean direction = row.get(4).equals("forward");
            int block = Integer.parseInt(row.get(5));
            trainlist.addTrains(new Train(trainlist, startTime, name, lineNum, direction, block));
        }
    }

    //엑셀 반복문
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