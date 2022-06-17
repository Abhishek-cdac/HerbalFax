package com.herbal.herbalfax.vendor.sellerorders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.group.yourgroupmodel.Group;
import com.herbal.herbalfax.customer.interfaces.Onclick;

import java.util.ArrayList;
import java.util.List;

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.MyViewHolder> {

    private List<Group> lst_completeOrder = new ArrayList<>();
    Context context;
    //  Picasso picasso;
    Onclick itemClick;

    public PendingOrderAdapter(List<Group> lst_completeOrder, FragmentActivity activity, Onclick itemClick) {

        this.lst_completeOrder = lst_completeOrder;
        this.context = activity;
        this.itemClick = itemClick;
        //    picasso = Picasso.get();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryTxt, viewBtn;
        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            categoryTxt = view.findViewById(R.id.categoryTxt);
            viewBtn = view.findViewById(R.id.viewBtn);

        }
    }


//    public CompleteOrderAdapter(List<Group> discoverList, FragmentActivity activity, Onclick itemClick) {
//
//    }

    @NonNull
    @Override
    public PendingOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_order_listitem, parent, false);
        return new PendingOrderAdapter.MyViewHolder(itemView);
    }

    @SuppressLint({"RecyclerView", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull PendingOrderAdapter.MyViewHolder holder, int position) {

        if (position == 0) {
            holder.viewBtn.setText("Track");
            holder.viewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SellerTrackActivity.class);
                    context.startActivity(intent);
                }
            });
        } else {
            holder.viewBtn.setText("Pick up order");
        }
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
        return 2;
    }

}
