package com.example.teamproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.teamproject.adapters.BookmarkAdapter;
import com.example.teamproject.models.Bookmark;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity implements RecyclerViewItemClickListener.OnItemClickListener {

    private RecyclerView recyclerView;
    private BookmarkAdapter adapter;
    ArrayList<Bookmark> bookmarkArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        recyclerView = findViewById(R.id.main_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(this,recyclerView,this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        bookmarkArrayList = new ArrayList<>();
        try {
            ArrayList<String> arrStr = readFromFile();
            for (int i = 0; i < arrStr.size(); i++) {
                bookmarkArrayList.add(new Bookmark(arrStr.get(i)));
            }
            adapter = new BookmarkAdapter(bookmarkArrayList);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String file, String text) throws Exception{
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(getFilesDir() + "/" + file, false));
            writer.append(text);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> readFromFile() throws Exception{
        String line = null;
        ArrayList<String> searchlist = new ArrayList<String>();
        try{
            BufferedReader buf = new BufferedReader(new FileReader(getFilesDir() + "/" + "Bookmark.txt"));
            while((line = buf.readLine()) != null){
                searchlist.add(line);
            }
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return searchlist;
    }

    @Override
    public void onItemClick(View view, int position) {
        showDialog(bookmarkArrayList.get(position).getBookmark(), position);
    }

    public void showDialog(String ret, int pos){
        String[] station = getResources().getStringArray(R.array.bookmark);

        AlertDialog.Builder builder = new AlertDialog.Builder(BookmarkActivity.this);


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
        builder.setItems(station, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
                    bookmarkArrayList.remove(pos);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int j = 0; j < bookmarkArrayList.size(); j++) {
                        stringBuilder.append(bookmarkArrayList.get(j).getBookmark() + System.lineSeparator());
                    } //배열에서 지울거 지우고 배열내용으로 txt다시 써줌
                    try {
                        writeToFile("Bookmark.txt", stringBuilder.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    adapter = new BookmarkAdapter(bookmarkArrayList);
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(), "즐겨찾기에서 제거됨", Toast.LENGTH_LONG).show();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}