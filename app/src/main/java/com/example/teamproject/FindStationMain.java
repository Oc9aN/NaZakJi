package com.example.teamproject;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindStationMain extends AppCompatActivity {
    private List<String> list;
    private ListView listView;
    private EditText editSearch;
    private EditText etSearch;
    private EditText filename;
    private SearchAdapter adapter;  //리스트뷰에 연결할 어뎁터
    private ArrayList<String> arraylist;
    private List<String> searchlist;
    private String ret;
    private AlertDialog.Builder builder;
    String[] station;

    private final static String file = "searchlist.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_station);

        editSearch = (EditText) findViewById(R.id.editSearch);
        listView = (ListView) findViewById(R.id.listView);
//        etSearch = (EditText) findViewById(R.id.etSearch);

        //리스트 생성
        list = new ArrayList<String>();

        //검색에 사용할 데이터 저장
        settingList();

        //리스트 데이터 arraylist에 복사
        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        adapter = new SearchAdapter(list, this);

        listView.setAdapter(adapter);

        list.clear();

        search("");

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId)
                {
                    case IME_ACTION_SEARCH :
                        // 검색버튼이 눌리면 실행할 내용 구현하기
                        String text = editSearch.getText().toString();
                        if(text.length() != 0){
                            try {
                                writeToFile(file, text);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(FindStationMain.this, "검색 완료", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(FindStationMain.this, "검색어를 입력하세요", Toast.LENGTH_SHORT).show();
                        }
                        search(text);
                }
                return true;
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editSearch.getText().toString();
                search(text);
            }
        });
    }

    public void search(String charText){

        //문자 입력시마다 리스트 지우고 새로 뿌림
        list.clear();

        //문자 입력 없으면 최근 검색어 보여줌.
        if(charText.length() == 0){
            try {
                searchlist = readFromFile();
                for(int i = searchlist.size() - 1; i >= 0; i--){
                    list.add(searchlist.get(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{   //입력하면 포함된 데이터 보여줌
            for(int i = 0; i < arraylist.size(); i++){
                if (arraylist.get(i).toLowerCase().contains(charText))
                {
                    list.add(arraylist.get(i));
                }
            }
        }
        //리스트 데이터가 변경되었으므로 검색된 데이터를 화면에 보여준다
        adapter.notifyDataSetChanged();

        //리스트뷰 클릭 시 전화면으로 이동하고 선택한 역을 적용시킨다
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ret = (String) adapterView.getAdapter().getItem(i);
                if (!arraylist.contains(ret)) {
                    editSearch.setText(ret);
                    editSearch.setSelection(editSearch.getText().length());
                    editSearch.requestFocus();
                    InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    manager.showSoftInput(editSearch, InputMethodManager.SHOW_IMPLICIT);
                    return;
                }
                showDialog(ret);
            }
        });
    }

    //검색에 사용될 데이터를 리스트에 추가
    private void settingList(){
        for(int i=101;i<124;i++){
            list.add(Integer.toString(i));
        }
        for(int i=201;i<218;i++){
            list.add(Integer.toString(i));
        }
        for(int i=301;i<309;i++){
            list.add(Integer.toString(i));
        }
        for(int i=401;i<418;i++){
            list.add(Integer.toString(i));
        }
        for(int i=501;i<508;i++){
            list.add(Integer.toString(i));
        }
        for(int i=601;i<623;i++){
            list.add(Integer.toString(i));
        }
        for(int i=701;i<708;i++){
            list.add(Integer.toString(i));
        }
        for(int i=801;i<807;i++){
            list.add(Integer.toString(i));
        }
        for(int i=901;i<905;i++){
            list.add(Integer.toString(i));
        }
    }

    //파일에 검색어를 저장하는 메소드
    public void writeToFile(String file, String text) throws Exception{
        try{
            String line = null;
            ArrayList<String> arrStr = new ArrayList<>();
            if (new File(getFilesDir() + "/" + file).exists()) {
                BufferedReader buf = new BufferedReader(new FileReader(getFilesDir() + "/" + file));
                while ((line = buf.readLine()) != null) {
                    arrStr.add(line);
                }
                buf.close();
                if (arrStr.contains(text)) {
                    if (file == "Bookmark.txt") {
                        Toast.makeText(getApplicationContext(), "이미 즐겨찾기에 있는 항목입니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else { //중복 지우고 맨위로
                        arrStr.remove(arrStr.indexOf(text));
                        arrStr.add(text);
                        BufferedWriter temp = new BufferedWriter(new FileWriter(getFilesDir() + "/" + file, false));
                        temp.close();
                        for (int i = 0; i < arrStr.size(); i++) {
                            BufferedWriter writer = new BufferedWriter(new FileWriter(getFilesDir() + "/" + file, true));
                            writer.append(arrStr.get(i));
                            writer.newLine();
                            writer.close();
                        }
                        return;
                    }
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(getFilesDir() + "/" + file, true));
            writer.append(text);
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //최근 검색어 파일을 읽어오는 메소드
    public ArrayList<String> readFromFile() throws Exception{
        String line = null;
        ArrayList<String> searchlist = new ArrayList<String>();
        try{
            BufferedReader buf = new BufferedReader(new FileReader(getFilesDir() + "/" + "searchlist.txt"));
            while((line = buf.readLine()) != null){
                searchlist.add(line);
            }
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return searchlist;
    }

    public void showDialog(String ret){
        station = getResources().getStringArray(R.array.station);

        builder = new AlertDialog.Builder(FindStationMain.this);


        if(ret.equals("101"))
            builder.setTitle("관광 명소: 광화문");
        else if(ret.equals("102"))
            builder.setTitle("관광 명소 : 에버랜드");
        else if(ret.equals("103"))
            builder.setTitle("관광 명소 : 롯데월드");
        else if(ret.equals("104"))
            builder.setTitle("관광 명소 : 북촌 한옥 마을");
        else if(ret.equals("105"))
            builder.setTitle("관광 명소 : 전주 한옥 마을");
        else if(ret.equals("106"))
            builder.setTitle("관광 명소 : 남산타워");
        else if(ret.equals("107"))
            builder.setTitle("관광 명소 : 한강");
        else if(ret.equals("108"))
            builder.setTitle("관광 명소 : 한강 시민공원");
        else if(ret.equals("109"))
            builder.setTitle("관광 명소 : 북한산");
        else if(ret.equals("110"))
            builder.setTitle("관광 명소 : 한라산");
        else if(ret.equals("111"))
            builder.setTitle("관광 명소 : 청와대");
        else if(ret.equals("112"))
            builder.setTitle("관광 명소 : 박정희 기념관");
        else if(ret.equals("113"))
            builder.setTitle("관광 명소 : 속초 해수욕장");
        else if(ret.equals("114"))
            builder.setTitle("관광 명소 : 롯데타워");
        else if(ret.equals("115"))
            builder.setTitle("관광 명소 : 서울대학교");
        else if(ret.equals("116"))
            builder.setTitle("관광 명소 : 연세대학교");
        else if(ret.equals("117"))
            builder.setTitle("관광 명소 : 고려대학교");
        else if(ret.equals("118"))
            builder.setTitle("관광 명소 : 보라매공원");
        else if(ret.equals("119"))
            builder.setTitle("관광 명소 : 올림픽경기장");
        else if(ret.equals("120"))
            builder.setTitle("관광 명소 : 경주월드");
        else if(ret.equals("121"))
            builder.setTitle("관광 명소 : 사육신역사공원");
        else if(ret.equals("122"))
            builder.setTitle("관광 명소 : 노들나루공원");
        else if(ret.equals("123"))
            builder.setTitle("관광 명소 : 타임스퀘어");
        else if(ret.equals("201"))
            builder.setTitle("관광 명소 : 여의도 한강공원");
        else if(ret.equals("202"))
            builder.setTitle("관광 명소 : 국회의사당");
        else if(ret.equals("203"))
            builder.setTitle("관광 명소 : 첨성대");
        else if(ret.equals("204"))
            builder.setTitle("관광 명소 : 더 현대서울");
        else if(ret.equals("205"))
            builder.setTitle("관광 명소 : 보강산");
        else if(ret.equals("206"))
            builder.setTitle("관광 명소 : 국립중앙박물관");
        else if(ret.equals("207"))
            builder.setTitle("관광 명소 : 국립한글박물관");
        else if(ret.equals("208"))
            builder.setTitle("관광 명소 : 전쟁기념관");
        else if(ret.equals("209"))
            builder.setTitle("관광 명소 : 국립극장");
        else if(ret.equals("210"))
            builder.setTitle("관광 명소 : 명동거리");
        else if(ret.equals("211"))
            builder.setTitle("관광 명소 : 랜떡");
        else if(ret.equals("212"))
            builder.setTitle("관광 명소 : 종묘");
        else if(ret.equals("213"))
            builder.setTitle("관광 명소 : 창덕궁");
        else if(ret.equals("214"))
            builder.setTitle("관광 명소 : 창경궁");
        else if(ret.equals("215"))
            builder.setTitle("관광 명소 : 유스퀘어");
        else if(ret.equals("216"))
            builder.setTitle("관광 명소 : 경복궁");
        else if(ret.equals("217"))
            builder.setTitle("관광 명소 : 국립민속박물관");
        else if(ret.equals("218"))
            builder.setTitle("관광 명소 : 민속촉");
        else if(ret.equals("301"))
            builder.setTitle("관광 명소 : 국립현대미술관 서울");
        else if(ret.equals("302"))
            builder.setTitle("관광 명소 : 독립문");
        else if(ret.equals("303"))
            builder.setTitle("관광 명소 : 봉원소공원");
        else if(ret.equals("304"))
            builder.setTitle("관광 명소 : 궁동공원");
        else if(ret.equals("305"))
            builder.setTitle("관광 명소 : 북악산");
        else if(ret.equals("306"))
            builder.setTitle("관광 명소 : 남산");
        else if(ret.equals("307"))
            builder.setTitle("관광 명소 : 밤섬공원");
        else if(ret.equals("308"))
            builder.setTitle("관광 명소 : 쉑쉑버거");
        else if(ret.equals("401"))
            builder.setTitle("관광 명소 : 이촌한강공원");
        else if(ret.equals("402"))
            builder.setTitle("관광 명소 : 신사공원");
        else if(ret.equals("403"))
            builder.setTitle("관광 명소 : 도산공원");
        else if(ret.equals("404"))
            builder.setTitle("관광 명소 : 압구정 로데오거리");
        else if(ret.equals("405"))
            builder.setTitle("관광 명소 : 압구정 카페골목");
        else if(ret.equals("406"))
            builder.setTitle("관광 명소 : 청담동 명품거리");
        else if(ret.equals("407"))
            builder.setTitle("관광 명소 : 잠실야구장");
        else if(ret.equals("408"))
            builder.setTitle("관광 명소 : COEX");
        else if(ret.equals("409"))
            builder.setTitle("관광 명소 : 역삼공원");
        else if(ret.equals("410"))
            builder.setTitle("관광 명소 : 마로니에공원");
        else if(ret.equals("411"))
            builder.setTitle("관광 명소 : 반포한강공원");
        else if(ret.equals("412"))
            builder.setTitle("관광 명소 : 신사까치공원");
        else if(ret.equals("413"))
            builder.setTitle("관광 명소 : 경리단길");
        else if(ret.equals("414"))
            builder.setTitle("관광 명소 : 송리단길");
        else if(ret.equals("415"))
            builder.setTitle("관광 명소 : 횡리단길");
        else if(ret.equals("416"))
            builder.setTitle("관광 명소 : 마장동");
        else if(ret.equals("417"))
            builder.setTitle("관광 명소 : 광장시장");
        else if(ret.equals("501"))
            builder.setTitle("관광 명소 : 방산시장");
        else if(ret.equals("502"))
            builder.setTitle("관광 명소 : 동대문 닭한마리 골목");
        else if(ret.equals("503"))
            builder.setTitle("관광 명소 : 신장동 패션거리");
        else if(ret.equals("504"))
            builder.setTitle("관광 명소 : 창신동 네팔음식거리");
        else if(ret.equals("505"))
            builder.setTitle("관광 명소 : 낙산공원");
        else if(ret.equals("506"))
            builder.setTitle("관광 명소 : 삼선공원");
        else if(ret.equals("507"))
            builder.setTitle("관광 명소 : 대학로 자유극장");
        else if(ret.equals("601"))
            builder.setTitle("관광 명소 : 대학로 카페거리");
        else if(ret.equals("602"))
            builder.setTitle("관광 명소 : 충무아트센터");
        else if(ret.equals("603"))
            builder.setTitle("관광 명소 : 족발골목");
        else if(ret.equals("604"))
            builder.setTitle("관광 명소 : 쉼터공원");
        else if(ret.equals("605"))
            builder.setTitle("관광 명소 : 어린이대공원");
        else if(ret.equals("606"))
            builder.setTitle("관광 명소 : 건대입구거리");
        else if(ret.equals("607"))
            builder.setTitle("관광 명소 : 뚝섬유원지");
        else if(ret.equals("608"))
            builder.setTitle("관광 명소 : 석촌호수");
        else if(ret.equals("609"))
            builder.setTitle("관광 명소 : 월드컵공원");
        else if(ret.equals("610"))
            builder.setTitle("관광 명소 : 방이동 먹자골목");
        else if(ret.equals("611"))
            builder.setTitle("관광 명소 : 송이공원");
        else if(ret.equals("612"))
            builder.setTitle("관광 명소 : 가락시장");
        else if(ret.equals("613"))
            builder.setTitle("관광 명소 : 장수공원");
        else if(ret.equals("614"))
            builder.setTitle("관광 명소 : 위례중앙광장");
        else if(ret.equals("615"))
            builder.setTitle("관광 명소 : 단대공원");
        else if(ret.equals("616"))
            builder.setTitle("관광 명소 : 해운대");
        else if(ret.equals("617"))
            builder.setTitle("관광 명소 : 동성로");
        else if(ret.equals("618"))
            builder.setTitle("관광 명소 : 송도해수욕장");
        else if(ret.equals("619"))
            builder.setTitle("관광 명소 : 벡스코");
        else if(ret.equals("620"))
            builder.setTitle("관광 명소 : 센텀시티");
        else if(ret.equals("621"))
            builder.setTitle("관광 명소 : 자갈치시장");
        else if(ret.equals("622"))
            builder.setTitle("관광 명소 : 광안리해수욕장");
        else if(ret.equals("701"))
            builder.setTitle("관광 명소 : 평화공원");
        else if(ret.equals("702"))
            builder.setTitle("관광 명소 : 서면클럽");
        else if(ret.equals("703"))
            builder.setTitle("관광 명소 : 범프리카치킨");
        else if(ret.equals("704"))
            builder.setTitle("관광 명소 : 자갈치야시장");
        else if(ret.equals("705"))
            builder.setTitle("관광 명소 : 소수서원");
        else if(ret.equals("706"))
            builder.setTitle("관광 명소 : 부석사");
        else if(ret.equals("707"))
            builder.setTitle("관광 명소 : 소백산");
        else if(ret.equals("801"))
            builder.setTitle("관광 명소 : 백두산");
        else if(ret.equals("802"))
            builder.setTitle("관광 명소 : 강원시티");
        else if(ret.equals("803"))
            builder.setTitle("관광 명소 : 봉화마을");
        else if(ret.equals("804"))
            builder.setTitle("관광 명소 : 땅끝마을");
        else if(ret.equals("805"))
            builder.setTitle("관광 명소 : 신원시장");
        else if(ret.equals("806"))
            builder.setTitle("관광 명소 : 영주시민운동장");
        else if(ret.equals("901"))
            builder.setTitle("관광 명소 : 영주시민회관");
        else if(ret.equals("902"))
            builder.setTitle("관광 명소 : 송산마을");
        else if(ret.equals("903"))
            builder.setTitle("관광 명소 : 영주한우거리");
        else if(ret.equals("904"))
            builder.setTitle("관광 명소 : 관악산");
        else {
            //검색창에 들어가게끔
            //Toast.makeText(getApplicationContext(), "역이 아닙니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        builder.setItems(station, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    writeToFile(file, ret);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(i == 0){
                    Intent intent = new Intent(getApplicationContext(), StationSearch.class);
                    intent.putExtra("stt", "start");
                    intent.putExtra("number", ret);
                    startActivity(intent);
                    finish();
                }
                else if(i == 1){
                    Intent intent = new Intent(getApplicationContext(), StationSearch.class);
                    intent.putExtra("stt", "middle");
                    intent.putExtra("number", ret);
                    startActivity(intent);
                    finish();
                }
                else if(i == 2){
                    Intent intent = new Intent(getApplicationContext(), StationSearch.class);
                    intent.putExtra("stt", "end");
                    intent.putExtra("number", ret);
                    startActivity(intent);
                    finish();
                }
                else if(i == 3){
                    try {
                        writeToFile("Bookmark.txt", ret);
                        Toast.makeText(getApplicationContext(), "즐겨찾기에 추가됨", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
