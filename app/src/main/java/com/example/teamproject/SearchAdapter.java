package com.example.teamproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;
    private TextView contentTextView;

    public SearchAdapter(List<String> list, Context context){
        this.list = list;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }
    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int i){
        return list.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup){
        final int pos = position;
        final Context context = viewGroup.getContext();
        if(convertView == null){
            convertView = inflate.inflate(R.layout.row_listview,null);

            viewHolder = new ViewHolder();
            viewHolder.label = (TextView) convertView.findViewById(R.id.label);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

//        LinearLayout cmdArea = (LinearLayout)convertView.findViewById(R.id.cmdArea);
//        cmdArea.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View view){
//                // empty
//            }
//        });
        viewHolder.label.setText(list.get(position));

        return convertView;
    }

    class ViewHolder{
        public TextView label;
    }
}
