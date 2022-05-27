package com.herbal.herbalfax.vendor.sellerorders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.group.yourgroupmodel.Group;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CompleteOrderAdapter extends RecyclerView.Adapter<CompleteOrderAdapter.MyViewHolder> {

    private List<Group> lst_completeOrder = new ArrayList<>();
    Context context;
    Picasso picasso;
    Onclick itemClick;

    public CompleteOrderAdapter(List<com.herbal.herbalfax.customer.homescreen.group.yourgroupmodel.Group> lst_completeOrder, FragmentActivity activity, Onclick itemClick) {

        this.lst_completeOrder = lst_completeOrder;
        this.context = activity;
        this.itemClick = itemClick;
        picasso = Picasso.get();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, joinTxt;
        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);

        }
    }


//    public CompleteOrderAdapter(List<Group> discoverList, FragmentActivity activity, Onclick itemClick) {
//
//    }

    @Override
    public CompleteOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_order_listitem, parent, false);
        return new CompleteOrderAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(CompleteOrderAdapter.MyViewHolder holder, int position) {
       /* holder.title.setText(discoverList.get(position).getGrpName());

        if (!discoverList.get(position).getGrpImage().equals("")) {
            picasso.load(discoverList.get(position).getGrpImage())
                    .into(holder.icon);
        } else {

        }
       */ //String GroupId = discoverList.get(position).getIdgroups();
/*
        holder.joinTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClicks(view, position, 17, GroupId);
            }
        });

*/

    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
