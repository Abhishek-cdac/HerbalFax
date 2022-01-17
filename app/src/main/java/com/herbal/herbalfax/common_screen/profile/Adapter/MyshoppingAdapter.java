package com.herbal.herbalfax.common_screen.profile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class MyshoppingAdapter extends RecyclerView.Adapter<MyshoppingAdapter.ViewHolder> {

    ArrayList<Interest> lst_int;
    ArrayList<Interest> lst_intFilter;
    Context mContext;
    public MyshoppingAdapter(ArrayList<Interest> lst_int, Context applicationContext) {
        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }

    @NonNull
    @Override
    public MyshoppingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myshopping_menu_item, parent, false);
        mContext = parent.getContext();
        return new MyshoppingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView todaybtn;
        FrameLayout dealItemImg;
        ImageView readmorebtn;
        public ViewHolder(View view) {
            super(view);
            todaybtn = view.findViewById(R.id.mydealitemtitle);
            dealItemImg = view.findViewById(R.id.mydealitemimg);
            readmorebtn = view.findViewById(R.id.mydealrepeat);
        }
    }
}
