package com.herbal.herbalfax.common_screen.landingpage.adapter;

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

public class PhotosAdapter1 extends RecyclerView.Adapter<PhotosAdapter1.ViewHolder>  {

    ArrayList<Interest> lst_int ;

    Context mContext;

    public PhotosAdapter1(ArrayList<Interest> lst_int, Context applicationContext) {


        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public PhotosAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photos_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 6;  // lst_intFilter.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_photos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_photos = itemView.findViewById(R.id.iv_photos);

        }
    }



}
