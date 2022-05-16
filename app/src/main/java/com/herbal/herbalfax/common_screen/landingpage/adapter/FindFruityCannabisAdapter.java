package com.herbal.herbalfax.common_screen.landingpage.adapter;

import android.annotation.SuppressLint;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FindFruityCannabisAdapter extends RecyclerView.Adapter<FindFruityCannabisAdapter.ViewHolder> {

    ArrayList<Interest> lst_int;

    Context mContext;
    private final Picasso picasso;
    public FindFruityCannabisAdapter(ArrayList<Interest> lst_int, Context applicationContext) {

        picasso = Picasso.get();
        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public FindFruityCannabisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cannabis_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FindFruityCannabisAdapter.ViewHolder holder, int position) {
        if (position == 0) {

            holder.CannabisTxt.setText("Community");
            picasso.load(R.drawable.community)
                    .into(holder.CannabisImg);
        } else if( position == 1) {
            holder.CannabisTxt.setText("Find Cannabis");
            picasso.load(R.drawable.find)
                    .into(holder.CannabisImg);
        }else if( position == 2) {
            holder.CannabisTxt.setText("Trending Post");
            picasso.load(R.drawable.trending)
                    .into(holder.CannabisImg);
        }else if( position == 3) {
            holder.CannabisTxt.setText("Top Dispensaries");
            picasso.load(R.drawable.drugstore)
                    .into(holder.CannabisImg);
        }else if( position == 4) {
            holder.CannabisTxt.setText("Compare \n" +
                    "Deals");
            picasso.load(R.drawable.analytics)
                    .into(holder.CannabisImg);
        }else if( position == 5) {
            holder.CannabisTxt.setText("Brands & \n" +
                    "Strains");
            picasso.load(R.drawable.brand)
                    .into(holder.CannabisImg);
        }
    }

    @Override
    public int getItemCount() {
        return 6;  // lst_intFilter.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView CannabisImg;
        TextView CannabisTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CannabisImg = itemView.findViewById(R.id.CannabisImg);
            CannabisTxt = itemView.findViewById(R.id.CannabisText);

        }
    }


}
