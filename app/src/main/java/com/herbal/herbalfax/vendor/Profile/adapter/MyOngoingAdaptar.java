package com.herbal.herbalfax.vendor.Profile.adapter;

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

public class MyOngoingAdaptar extends RecyclerView.Adapter<MyOngoingAdaptar.ViewHolder> {
    private Context mContext;
    ArrayList<Interest> lst_int;

    public MyOngoingAdaptar(ArrayList<Interest> lst_cart_item, Context applicationContext) {
    }

    @NonNull
    @Override
    public MyOngoingAdaptar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_order_id_ongoing_list, parent, false);
        mContext = parent.getContext();
        return new MyOngoingAdaptar.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyOngoingAdaptar.ViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 2;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileDelete;
        TextView trackbtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileDelete = itemView.findViewById(R.id.pendingproductImg);
            trackbtn = itemView.findViewById(R.id.trackbtn);

        }
    }

}
