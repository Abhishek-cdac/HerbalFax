package com.herbal.herbalfax;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.customer.blogs.BlogDetailsActivity;
import com.herbal.herbalfax.customer.homescreen.deals.DealsDetailsActivity;
import com.herbal.herbalfax.customer.homescreen.deals.adaptor.NewDealsAdapter;

import java.util.ArrayList;

public class DailyNewsAdapter1  extends RecyclerView.Adapter {
    ArrayList personNames;
    Context context;
    public DailyNewsAdapter1(Context context, ArrayList personNames) {
        this.context = context;
        this.personNames = personNames;
    }
    @Override
    public DailyNewsAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_news_recycler_adapter, parent, false);
        // set the view's size, margins, paddings and layout parameters
        DailyNewsAdapter1.MyViewHolder vh = new DailyNewsAdapter1.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        /*holder.news_recycler.setOnClickListener(view -> {
            Intent i = new Intent(context, BlogDetailsActivity.class);
            context.startActivity(i);
        });*/

    }
    /*
        @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
            // set the data in items
            holder.name.setText(personNames.get(position));
            // implement setOnClickListener event on item view.
            holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
            // display a toast with person name on item click
            Toast.makeText(context, personNames.get(position), Toast.LENGTH_SHORT).show();
            }
            });
            }*/
    @Override
    public int getItemCount() {
        return 5;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        LinearLayout news_recycler;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);
            news_recycler = itemView.findViewById(R.id.news_recycler);
        }
    }
}