package com.example.teamproject.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.teamproject.R;
import com.example.teamproject.models.Bookmark;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Bookmark> listData = new ArrayList<>();

    public BookmarkAdapter(ArrayList<Bookmark> list) {
        this.listData = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
//        holder.onBind(listData.get(position));
        Bookmark data = listData.get(position);
        holder.title.setText(data.getBookmark()+"역");
        holder.delete.setText("X");
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Bookmark data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView delete;

        ItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_bookmark_title);
            delete = itemView.findViewById(R.id.item_bookmark_delete);
        }

        void onBind(Bookmark data) {
            title.setText(data.getBookmark());
            delete.setText("X");
        }
    }
}