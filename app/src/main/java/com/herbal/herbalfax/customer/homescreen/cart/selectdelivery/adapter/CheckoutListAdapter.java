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
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.model.CartList;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CheckoutListAdapter extends RecyclerView.Adapter<CheckoutListAdapter.ViewHolder> {

    ArrayList<CartList> lst_int;

    Context mContext;
    private final Picasso picasso;

    public CheckoutListAdapter(ArrayList<CartList> lst_cart_item, Context applicationContext, Onclick itemClick) {
        picasso = Picasso.get();
        this.lst_int = lst_cart_item;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public CheckoutListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CheckoutListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.productName.setText(lst_int.get(position).getSPName());
        holder.productCategory.setText(lst_int.get(position).getSPCTitle());
        holder.priceTxt.setText("$" + lst_int.get(position).getCartRate());
        picasso.load(lst_int.get(position).getSPPPath())
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return lst_int.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productCategory, priceTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productCategory = itemView.findViewById(R.id.productCategory);
            priceTxt = itemView.findViewById(R.id.priceTxt);
        }
    }


}
