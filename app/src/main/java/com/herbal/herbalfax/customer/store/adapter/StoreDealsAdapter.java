package com.herbal.herbalfax.customer.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class StoreDealsAdapter extends RecyclerView.Adapter<StoreDealsAdapter.ViewHolder> {

    ArrayList<Interest> lst_int;
    ArrayList<Interest> lst_intFilter;
    Context mContext;

    public StoreDealsAdapter(ArrayList<Interest> lst_int, Context applicationContext) {
        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public StoreDealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_deals_item, parent, false);
        mContext = parent.getContext();
        return new StoreDealsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreDealsAdapter.ViewHolder holder, int position) {

        if (position == 2 || position == 3 ) {
            holder.expiredTxt.setVisibility(View.VISIBLE);
            holder.BuyTxt.setVisibility(View.GONE);
        } else {
            holder.expiredTxt.setVisibility(View.GONE);
            holder.BuyTxt.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return 4;  // lst_intFilter.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView expiredTxt;
        FrameLayout BuyTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            expiredTxt = itemView.findViewById(R.id.expiredTxt);
            BuyTxt = itemView.findViewById(R.id.buyTxt);

        }
    }


}
