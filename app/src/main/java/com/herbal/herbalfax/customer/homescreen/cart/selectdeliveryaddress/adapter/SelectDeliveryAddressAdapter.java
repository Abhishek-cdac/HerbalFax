package com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress.model.UserAddress;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SelectDeliveryAddressAdapter extends RecyclerView.Adapter<SelectDeliveryAddressAdapter.ViewHolder> {

    ArrayList<UserAddress> lst_int;
    ArrayList<UserAddress> lst_intFilter;
    Context mContext;
    private final Picasso picasso;
    private final Onclick itemClick;

    public SelectDeliveryAddressAdapter(ArrayList<UserAddress> lst_cart_item, Context applicationContext, Onclick itemClick) {

        picasso = Picasso.get();
        this.lst_int = lst_cart_item;
        this.mContext = applicationContext;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public SelectDeliveryAddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_list_item, parent, false);
        mContext = parent.getContext();
        return new SelectDeliveryAddressAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectDeliveryAddressAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.placeName.setText(lst_int.get(position).getAddTypeTitle());
        holder.userName.setText(lst_int.get(position).getUAddFullName());
        holder.phoneNumber.setText(lst_int.get(position).getUserPhone());
        holder.address_txt.setText(lst_int.get(position).getUAddAddress() + ", " + lst_int.get(position).getUAddCity() + ", " + lst_int.get(position).getUAddCountry() + ", Zip Code: " + lst_int.get(position).getUAddPincode());
        if (lst_int.get(position).getUAddType().equals("1")) {
            holder.placeImage.setImageResource(R.drawable.ic_icon_work_briefcase);


        } else {
            holder.placeImage.setImageResource(R.drawable.ic_icon_menu_home);
        }


        if (lst_int.get(position).getUAddDefault().equals("1")) {
            holder.DefaultTxt.setVisibility(View.VISIBLE);
           // holder.checkImg.setVisibility(View.VISIBLE);

        } else {
            holder.checkImg.setVisibility(View.GONE);

            holder.DefaultTxt.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return lst_int.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView placeImage, checkImg;
        LinearLayout parentLl;
        TextView placeName, userName, phoneNumber, address_txt, DefaultTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = itemView.findViewById(R.id.placeImage);
            placeName = itemView.findViewById(R.id.placeName);
            userName = itemView.findViewById(R.id.userName);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            address_txt = itemView.findViewById(R.id.address_txt);
            checkImg = itemView.findViewById(R.id.checkImg);
            parentLl = itemView.findViewById(R.id.parentLl);
            DefaultTxt = itemView.findViewById(R.id.DefaultTxt);
        }
    }


}
