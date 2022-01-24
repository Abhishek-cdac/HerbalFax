package com.herbal.herbalfax.common_screen.landingpage.events.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.landingpage.events.eventdetailsmodel.Event;
import com.herbal.herbalfax.customer.homescreen.deals.DealsDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventsItemsMenuAdapter extends RecyclerView.Adapter<EventsItemsMenuAdapter.ViewHolder> {

    ArrayList<Event> lst_int;
    Context mContext;
    Picasso picasso;


    public EventsItemsMenuAdapter(ArrayList<Event> lst_int, Context applicationContext) {
        this.lst_int = lst_int;
        picasso = Picasso.get();
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public EventsItemsMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_menu_item, parent, false);
        mContext = parent.getContext();
        return new EventsItemsMenuAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        holder.readmorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DealsDetailsActivity.class);
                mContext.startActivity(i);
            }
        });
        holder.eventTitle.setText(lst_int.get(position).getEventName());
        holder.eventDetails.setText(lst_int.get(position).getEventDesc());
        holder.eventDate.setText("Date on : "+lst_int.get(position).getEventDate()+" at "+lst_int.get(position).getEventTime());
        if (!lst_int.get(position).getEventImage().equals("")){
        picasso.load(lst_int.get(position).getEventImage())
            .into(holder.eventItemImg);
        }

    }

    @Override
    public int getItemCount() {
        return lst_int.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView todaybtn, eventTitle, eventDate, eventDetails;
        ImageView eventItemImg;
        TextView readmorebtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventDetails = itemView.findViewById(R.id.eventdetails);
            todaybtn = itemView.findViewById(R.id.todaybtn);
            eventItemImg = itemView.findViewById(R.id.eventItemImg);
            readmorebtn = itemView.findViewById(R.id.readmorebtn);

        }
    }


}
