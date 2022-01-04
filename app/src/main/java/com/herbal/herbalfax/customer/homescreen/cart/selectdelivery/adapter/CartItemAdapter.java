package com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.model.CartList;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.MyView> {

    private List<CartList> lst_cart_item;
    Context context;
    Onclick itemClick;
    private final Picasso picasso;

    public class MyView extends RecyclerView.ViewHolder {

        TextView categoryTxt, cart_head, cart_price, cart_count;
        CardView deleteCard;
        ImageView productImg;


        public MyView(View view) {
            super(view);
            deleteCard = view.findViewById(R.id.deleteCard);
            cart_count = view.findViewById(R.id.cart_count);
            productImg = view.findViewById(R.id.productImg);
            cart_head = view.findViewById(R.id.cart_head);
            cart_price = view.findViewById(R.id.cart_price);
            categoryTxt = view.findViewById(R.id.car_subhead);
        }
    }

    public CartItemAdapter(List<CartList> lst_cart_item, Context context, Onclick itemClick) {
        picasso = Picasso.get();

        this.lst_cart_item = lst_cart_item;
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
        try {
            holder.cart_head.setText(lst_cart_item.get(position).getSPName());
            holder.categoryTxt.setText(lst_cart_item.get(position).getSPCTitle());
            holder.cart_price.setText("$"+lst_cart_item.get(position).getCartRate());
            holder.cart_count.setText(lst_cart_item.get(position).getCartQty());
            picasso.load(lst_cart_item.get(position).getSPPPath())
                    .into(holder.productImg);
            String idcart = lst_cart_item.get(position).getIdcart();
            holder.deleteCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    itemClick.onItemClicks(view, position, 11, idcart);

                }
            });


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {

        return lst_cart_item.size();
    }
}