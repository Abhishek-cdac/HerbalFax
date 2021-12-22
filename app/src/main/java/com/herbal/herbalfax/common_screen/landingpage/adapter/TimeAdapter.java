package com.herbal.herbalfax.common_screen.landingpage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.MyView> {

    private List<String> list;
Context context;

    public class MyView extends RecyclerView.ViewHolder {

        TextView textView;
        View line;



        public MyView(View view) {
            super(view);


            line =  view.findViewById(R.id.line);
            textView = (TextView) view.findViewById(R.id.timetxt);
        }
    }

    public TimeAdapter(List<String> horizontalList, Context context) {
        this.list = horizontalList;
        this.context = context;
    }


    @Override
    public MyView onCreateViewHolder(ViewGroup parent,
                                     int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_item,parent,false);
        return new MyView(itemView);
    }


    @Override
    public void onBindViewHolder(final MyView holder,
                                 final int position) {
        holder.textView.setText(list.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.line.setVisibility(View.VISIBLE);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}