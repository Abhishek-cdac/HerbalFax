package com.herbal.herbalfax.common_screen.landingpage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.deals.NewDealsCategorys;
import com.herbal.herbalfax.customer.selectInterest.Interest;


import java.util.ArrayList;
import java.util.List;

public class  EventsMenuAdapter extends RecyclerView.Adapter<EventsMenuAdapter.ViewHolder> {

    ArrayList<Interest> DealsCategory;
    /* ArrayList<Interest> lst_intFilter;*/
    Context mContext;

    public EventsMenuAdapter(ArrayList<Interest> DealsCategory, Context applicationContext) {
        this.DealsCategory = DealsCategory;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public EventsMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_deals_item, parent, false);
        mContext = parent.getContext();
        return new EventsMenuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsMenuAdapter.ViewHolder holder, int position) {
      /*  NewDealsCategorys deals = DealsCategory.get(position);
        holder.category.setText(deals.getCategory());*/
        if(position==0){
            holder.category.setText("All Events");
        }
        else if(position==1) {
            holder.category.setText("Today");
        }
        else if(position==2)
        {
            holder.category.setText("Tomorrow");
        } else if(position==3)
        {
            holder.category.setText("This Week");
        }

    }

    @Override
    public int getItemCount() {
        return 4;  // lst_intFilter.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView category;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category = (TextView)itemView.findViewById(R.id.categories);

        }
    }


}

