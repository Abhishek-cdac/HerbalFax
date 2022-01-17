package com.herbal.herbalfax.common_screen.profile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.landingpage.adapter.EventsItemsMenuAdapter;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class MydealsAdapter extends RecyclerView.Adapter<MydealsAdapter.ViewHolder> {

    ArrayList<Interest> lst_int;
    ArrayList<Interest> lst_intFilter;
    Context mContext;
    public MydealsAdapter(ArrayList<Interest> lst_int, Context applicationContext) {
        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }

    @NonNull
    @Override
    public MydealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mydeals_menu_item, parent, false);
        mContext = parent.getContext();
        return new MydealsAdapter.ViewHolder(view);
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
        TextView readmorebtn;
        public ViewHolder(View view) {
            super(view);
            todaybtn = view.findViewById(R.id.mydealTitle);
            dealItemImg = view.findViewById(R.id.mydealItemImg);
            readmorebtn = view.findViewById(R.id.downloadbtn);
        }
    }
}
