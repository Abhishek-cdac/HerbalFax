package com.herbal.herbalfax.common_screen.landingpage.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.landingpage.EventReadMoreActivity;
import com.herbal.herbalfax.customer.homescreen.deals.DealsDetailsActivity;
import com.herbal.herbalfax.customer.homescreen.deals.DealsFragment;
import com.herbal.herbalfax.customer.homescreen.deals.adaptor.NewDealsAdaptor;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class EventsItemsMenuAdapter extends RecyclerView.Adapter<EventsItemsMenuAdapter.ViewHolder> {

    ArrayList<Interest> lst_int;
    ArrayList<Interest> lst_intFilter;
    Context mContext;

    public EventsItemsMenuAdapter(ArrayList<Interest> lst_int, Context applicationContext) {
        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public EventsItemsMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_menu_item, parent, false);
        mContext = parent.getContext();
        return new EventsItemsMenuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

/*        if (position == 2 || position == 3 ) {
            holder.todaybtn.setVisibility(View.VISIBLE);
            holder.dealItemImg.setVisibility(View.GONE);
        } else {
            holder.todaybtn.setVisibility(View.GONE);
            holder.readmorebtn.setVisibility(View.VISIBLE);
        }*/

        holder.readmorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, EventReadMoreActivity.class);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;  // lst_intFilter.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView todaybtn;
        FrameLayout dealItemImg;
        TextView readmorebtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            todaybtn = itemView.findViewById(R.id.todaybtn);
            dealItemImg = itemView.findViewById(R.id.dealItemImg);
            readmorebtn = itemView.findViewById(R.id.readmorebtn);

        }
    }


}
