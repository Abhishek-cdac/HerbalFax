package com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.adapter;

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
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CheckoutListAdapter extends RecyclerView.Adapter<CheckoutListAdapter.ViewHolder> {

    ArrayList<Interest> lst_int;
    ArrayList<Interest> lst_intFilter;
    Context mContext;
    private final Picasso picasso;
    private final Onclick itemClick;

    public CheckoutListAdapter(ArrayList<Interest> lst_cart_item, Context applicationContext, Onclick itemClick) {

        picasso = Picasso.get();
        this.lst_int = lst_cart_item;
        this.mContext = applicationContext;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public CheckoutListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_item, parent, false);
        mContext = parent.getContext();
        return new CheckoutListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


    }

    @Override
    public int getItemCount() {
        return 8;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productCategory,priceTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productCategory = itemView.findViewById(R.id.productCategory);
            priceTxt = itemView.findViewById(R.id.priceTxt);


        }
    }


}
