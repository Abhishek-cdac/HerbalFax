package com.herbal.herbalfax.customer.myorders.Adaptor;

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

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {

    ArrayList<Interest> lst_int;
    ArrayList<Interest> lst_intFilter;
    Context mContext;
    public MyOrdersAdapter(ArrayList<Interest> lst_int, Context applicationContext) {
        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }

    @NonNull
    @Override
    public MyOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorders_menu_item, parent, false);
        mContext = parent.getContext();
        return new MyOrdersAdapter.ViewHolder(view);
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
            todaybtn = view.findViewById(R.id.myorderTitle);
            dealItemImg = view.findViewById(R.id.myordersItemImg);
            readmorebtn = view.findViewById(R.id.orderpricemoney);
        }
    }
}
