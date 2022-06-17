package com.herbal.herbalfax;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.vendor.sellerproduct.productlistmodel.StoreProduct;

import java.util.ArrayList;

public class DailyBlogAdapterTab extends RecyclerView.Adapter<DailyBlogAdapterTab.ViewHolder> {

    ArrayList<StoreProduct> lst_product;
    Context mContext;
    ArrayList personNames; /*private final Picasso picasso;*/
    Onclick itemClick;


    public DailyBlogAdapterTab(ArrayList personNames, Context applicationContext, Onclick itemClick) {
        /*picasso = Picasso.get();*/
        this.personNames = personNames;
        this.mContext = applicationContext;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public DailyBlogAdapterTab.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_blog_recycler_adapter, parent, false);
        mContext = parent.getContext();
        return new DailyBlogAdapterTab.ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull DailyBlogAdapterTab.ViewHolder holder, int position) {

        holder.cardParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClicks(view, position, 20, "");


            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cardParent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardParent = itemView.findViewById(R.id.cardParent);

        }
    }


}
