package com.herbal.herbalfax.vendor.sellerdeals.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class SelllerDealsDetailsAdapter extends RecyclerView.Adapter<SelllerDealsDetailsAdapter.ViewHolder> {
        ArrayList<Interest> lst_int;
        Context mContext;

public SelllerDealsDetailsAdapter(ArrayList<Interest> lst_cart_item, Context activity) {
        this.lst_int = lst_cart_item;
        this.mContext = activity;
        }

@NonNull
@Override
public SelllerDealsDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_seller_deals_details_list, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

@Override
public int getItemCount() {
        return 5;
        }


public static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView profileDelete;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        profileDelete = itemView.findViewById(R.id.profiledatadelete);

    }
}
}