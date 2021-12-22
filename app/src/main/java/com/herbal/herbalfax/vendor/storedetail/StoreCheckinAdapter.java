package com.herbal.herbalfax.vendor.storedetail;

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

public class StoreCheckinAdapter extends
   RecyclerView.Adapter<StoreCheckinAdapter.ViewHolder>  {

        ArrayList<Interest> lst_int ;
    ArrayList<Interest> lst_intFilter ;
    Context mContext;

    public StoreCheckinAdapter(ArrayList<Interest> lst_int, Context applicationContext) {
        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public StoreCheckinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_checkin_item, parent, false);
        mContext = parent.getContext();
        return new StoreCheckinAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StoreCheckinAdapter.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 6;  // lst_intFilter.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }



}