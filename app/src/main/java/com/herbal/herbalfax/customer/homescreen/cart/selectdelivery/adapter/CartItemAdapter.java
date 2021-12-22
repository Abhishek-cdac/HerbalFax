package com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.MyView> {

    private List<Interest> list;
    Context context;
    Onclick itemClick;

    public class MyView extends RecyclerView.ViewHolder {

        TextView categoryTxt;
        CardView deleteCard;


        public MyView(View view) {
            super(view);

            deleteCard = view.findViewById(R.id.deleteCard);
//            categoryTxt = (TextView) view.findViewById(R.id.categoryTxt);
        }
    }

    public CartItemAdapter(List<Interest> lst_cart_item, Context context, Onclick itemClick) {
        this.list = lst_cart_item;
        this.context = context;
        this.itemClick = itemClick;
    }


    @Override
    public CartItemAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartItemAdapter.MyView(itemView);
    }


    @Override
    public void onBindViewHolder(final CartItemAdapter.MyView holder, @SuppressLint("RecyclerView") final int position) {
//        holder.deleteCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//              //  itemClick.onItemClicks(view, position, 11, productId);
//
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return 8;
    }
}