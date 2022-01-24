package com.herbal.herbalfax.vendor.sellerdrivers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.dialog.DeleteDialog;
import com.herbal.herbalfax.common_screen.landingpage.AddCommentbottomsheet;
import com.herbal.herbalfax.common_screen.landingpage.EventReadMoreActivity;
import com.herbal.herbalfax.common_screen.landingpage.adapter.EventsItemsMenuAdapter;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.AddToCartActivity;
import com.herbal.herbalfax.customer.homescreen.group.Adaptor.AddPeopleListAdaptor;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class SellerMyDriverAdapter extends RecyclerView.Adapter<SellerMyDriverAdapter.ViewHolder> {
    ArrayList<Interest> lst_int;
    ArrayList<Interest> lst_intFilter;
    Context mContext;

    public SellerMyDriverAdapter(ArrayList<Interest> lst_cart_item, FragmentActivity activity) {
        this.lst_int = lst_cart_item;
        this.mContext = activity;
    }

    @NonNull
    @Override
    public SellerMyDriverAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_seller_driver_list, parent, false);
        mContext = parent.getContext();
        return new SellerMyDriverAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerMyDriverAdapter.ViewHolder holder, int position) {
        holder.profileDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DeleteDialog deleteDialog = new DeleteDialog(mContext);
                    deleteDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

/*
                showBottomSheets(0);
*/
            }
        });

    }


    @Override
    public int getItemCount() {
        return 5;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileDelete;
        TextView productName, productCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileDelete = itemView.findViewById(R.id.profiledatadelete);
            productName = itemView.findViewById(R.id.productName);
            productCategory = itemView.findViewById(R.id.productCategory);
        }
    }
}
