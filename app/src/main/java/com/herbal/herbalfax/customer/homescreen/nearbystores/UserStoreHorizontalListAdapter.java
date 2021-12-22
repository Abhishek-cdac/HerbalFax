package com.herbal.herbalfax.customer.homescreen.nearbystores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.nearbystores.userstoremodel.Store;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserStoreHorizontalListAdapter extends RecyclerView.Adapter<UserStoreHorizontalListAdapter.ViewHolder> {

    ArrayList<Store> lst_int;
    private final Picasso picasso;
    Context mContext;
    Onclick itemClick;

    public UserStoreHorizontalListAdapter(ArrayList<Store> lst_int, Context applicationContext, Onclick itemClick) {
        picasso = Picasso.get();
        this.lst_int = lst_int;
        this.mContext = applicationContext;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public UserStoreHorizontalListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_horizontal_list_item, parent, false);
        mContext = parent.getContext();
        return new UserStoreHorizontalListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserStoreHorizontalListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (lst_int.get(position).getStoreLogo() != null) {
            if (!lst_int.get(position).getStoreLogo().equals("")) {
                picasso.load(lst_int.get(position).getStoreLogo())
                        .into(holder.StoreImg);
            }
        }
        holder.storeName.setText(lst_int.get(position).getStoreName());
        holder.StoreAddressText.setText(lst_int.get(position).getStoreLocation());
        holder.StoreCategoryText.setText(lst_int.get(position).getSCatTitle());
        holder.storeName.setText(lst_int.get(position).getStoreName());
        String StoreId = lst_int.get(position).getIdstores();
        holder.cardview.setOnClickListener(view -> itemClick.onItemClicks(view, position, 6, StoreId));
    }

    @Override
    public int getItemCount() {
        return lst_int.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView StoreImg;
        TextView storeName, StoreCategoryText, StoreAddressText, timeTxt;
        CardView cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardview = itemView.findViewById(R.id.cardview);
            StoreImg = itemView.findViewById(R.id.StoreImg);
            StoreCategoryText = itemView.findViewById(R.id.StorecategoryText);
            storeName = itemView.findViewById(R.id.StoreNameText);
            StoreAddressText = itemView.findViewById(R.id.StoreAddressText);
            timeTxt = itemView.findViewById(R.id.timeTxt);
        }
    }

}
