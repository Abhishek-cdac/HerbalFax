package com.herbal.herbalfax.vendor.sellernotification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class SellerNotificationAdapter extends RecyclerView.Adapter<SellerNotificationAdapter.ViewHolder> {
    ArrayList<Interest> lst_notification;
    Context mContext;

    public SellerNotificationAdapter(ArrayList<Interest> lst_cart_item, FragmentActivity activity) {
        this.lst_notification = lst_cart_item;
        this.mContext = activity;
    }

    @NonNull
    @Override
    public SellerNotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_notification_listitem, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerNotificationAdapter.ViewHolder holder, int position) {
       /* holder.profileDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DeleteDialog deleteDialog = new DeleteDialog(mContext);
                    deleteDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return 5;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
