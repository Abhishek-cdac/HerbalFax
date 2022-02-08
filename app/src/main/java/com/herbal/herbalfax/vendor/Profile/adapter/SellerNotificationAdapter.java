package com.herbal.herbalfax.vendor.Profile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class SellerNotificationAdapter extends RecyclerView.Adapter<SellerNotificationAdapter.ViewHolder> {
    ArrayList<Interest> lst_int;
    Context mContext;

    public SellerNotificationAdapter(ArrayList<Interest> lst_cart_item, Context activity) {
        this.lst_int = lst_cart_item;
        this.mContext = activity;
    }

    @NonNull
    @Override
    public SellerNotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_notification_adapter, parent, false);
        mContext = parent.getContext();
        return new SellerNotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 4;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView notification_id;
        TextView notificationdate;
        TextView notification_detail;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notification_id = itemView.findViewById(R.id.notification_id);
            notificationdate = itemView.findViewById(R.id.notificationdate);
            notification_detail = itemView.findViewById(R.id.notification_detail);

        }
    }
}