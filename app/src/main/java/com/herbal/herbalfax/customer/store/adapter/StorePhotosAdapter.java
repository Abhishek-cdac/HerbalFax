package com.herbal.herbalfax.customer.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class StorePhotosAdapter extends RecyclerView.Adapter<StorePhotosAdapter.ViewHolder>  {

    ArrayList<Interest> lst_int ;
    ArrayList<Interest> lst_intFilter ;
    Context mContext;

    public StorePhotosAdapter(ArrayList<Interest> lst_int, Context applicationContext) {


        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public StorePhotosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_photos_item, parent, false);
        mContext = parent.getContext();
        return new StorePhotosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StorePhotosAdapter.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 6;  // lst_intFilter.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_photos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_photos = itemView.findViewById(R.id.iv_photos);

        }
    }



}
