package com.herbal.herbalfax.common_screen.landingpage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    ArrayList<Interest> lst_int;

    Context mContext;

    public EventsAdapter(ArrayList<Interest> lst_int, Context applicationContext) {


        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 6;  // lst_intFilter.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventPlaceName,eventGameName;
        ImageView eventImg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventGameName = itemView.findViewById(R.id.eventGameName);
            eventPlaceName = itemView.findViewById(R.id.eventPlaceName);
            eventImg = itemView.findViewById(R.id.eventImg);
        }
    }
}

