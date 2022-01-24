package com.herbal.herbalfax.common_screen.landingpage.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.group.Adaptor.AddPeopleListAdaptor;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class AddCommentsAdapter extends RecyclerView.Adapter<AddCommentsAdapter.ViewHolder> {


    ArrayList<Interest> lst_int;
    ArrayList<Interest> lst_intFilter;
    Context mContext;

    public AddCommentsAdapter(ArrayList<Interest> lst_cart_item, Context applicationContext) {
        this.lst_int = lst_cart_item;
        this.mContext = applicationContext;

    }

    public static void setAdapter(AddPeopleListAdaptor addPeopleListAdaptor) {
    }

    public static void setLayoutManager(RecyclerView.LayoutManager recyclerViewLayoutManager) {
    }


    @NonNull
    @Override
    public AddCommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_people_comment_list, parent, false);
        mContext = parent.getContext();
        return new AddCommentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 8;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productCategory,priceTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productCategory = itemView.findViewById(R.id.productCategory);

        }
    }


}
