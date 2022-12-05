package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.view.KeyEvent;
import android.os.Handler;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;

import org.w3c.dom.Text;

import java.util.concurrent.ScheduledExecutorService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FindStation extends AppCompatActivity {

    private List<String> list;
    private ListView listView;
    private EditText editSearch;
    private EditText etSearch;
    private EditText filename;
    private SearchAdapter adapter;  //리스트뷰에 연결할 어뎁터
    private ArrayList<String> arraylist;
    private List<String> searchlist;

    private final static String file = "searchlist.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_station);

        editSearch = (EditText) findViewById(R.id.editSearch);
        listView = (ListView) findViewById(R.id.listView);
        etSearch = (EditText) findViewById(R.id.etSearch);

        //리스트 생성
        list = new ArrayList<String>();

        //검색에 사용할 데이터 저장
        settingList();

        //리스트 데이터 arraylist에 복사
        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        //searchlist = new ArrayList<String>();

        adapter = new SearchAdapter(list, this);

        // !!
//        listView = (ListView)findViewById(R.id.listView);

        listView.setAdapter(adapter);



        list.clear();

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this, "성공", Toast.LENGTH_SHORT).show();
//            }
//        });

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId)
                {
                    case IME_ACTION_SEARCH :
                        Toast.makeText(FindStation.this, "검색 완료", Toast.LENGTH_SHORT).show();
                        // 검색버튼이 눌리면 실행할 내용 구현하기
                        String text = editSearch.getText().toString();
                        if(text == null)
                            return true;
                        try{
                            writeToFile(file, text);
                        } catch (Exception e) {
                            e.printStackTrace();
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



//        listView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    public void search(String charText){

        //문자 입력시마다 리스트 지우고 새로 뿌림
        list.clear();

        //문자 입력 없으면 최근 검색어 보여줌.
        if(charText.length() == 0){
            try {
                searchlist = readFromFile(file);
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
                //if(arraylist.get(i).substring(0,charText.length()) == charText)
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
//                Object lst = (Object) adapterView.getAdapter().getItem(i);
                String rtu = String.valueOf((Object) adapterView.getAdapter().getItem(i));
                Toast.makeText(FindStation.this, "성공", Toast.LENGTH_SHORT).show();
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(getFilesDir() + "/" + file, true));
            writer.append(text);
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //최근 검색어 파일을 읽어오는 메소드
    public ArrayList<String> readFromFile(String file) throws Exception{
        String line = null;
        ArrayList<String> searchlist = new ArrayList<String>();
        try{
            BufferedReader buf = new BufferedReader(new FileReader(getFilesDir() + "/" + file));
            while((line = buf.readLine()) != null){
                searchlist.add(line);
            }
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return searchlist;
    }

}