package com.herbal.herbalfax.vendor.sellerdeals.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.vendor.sellerdeals.booster.SellerBoosterDealActivity;
import com.herbal.herbalfax.vendor.sellerproduct.productlistmodel.StoreProduct;
/*import com.squareup.picasso.Picasso;*/

import java.util.ArrayList;

public class SellerProductDealsAdapter extends RecyclerView.Adapter<SellerProductDealsAdapter.ViewHolder> {

    ArrayList<StoreProduct> lst_deal;
    Context mContext;
 /*   private final Picasso picasso;*/
Onclick itemClick;
    public SellerProductDealsAdapter(ArrayList<StoreProduct> lst_deal, Context applicationContext, Onclick itemClick) {
      /*  picasso = Picasso.get();*/
        this.lst_deal = lst_deal;
        this.itemClick = itemClick;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public SellerProductDealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_store_deals_item, parent, false);
        mContext = parent.getContext();
        return new SellerProductDealsAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SellerProductDealsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.productNameText.setText(lst_deal.get(position).getSPName());

        if(lst_deal.get(position).getsP_Expiry() != null){
            holder.validity_txt.setText("Validity Till: " + lst_deal.get(position).getsP_Expiry());
        }
        if (lst_deal.get(position).getsP_Location() == null) {
            holder.location.setText("No Address Found");
        }else {
            holder.location.setText(lst_deal.get(position).getsP_Location());

        }

        holder.priceTxt.setText("$" + lst_deal.get(position).getSPRate());
   /*     picasso.load(String.valueOf(lst_deal.get(position).getSPPPath()))
                .into(holder.productImg);*/

        holder.buyBtn.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, SellerBoosterDealActivity.class);
            intent.putExtra("dealId", lst_deal.get(position).getIdstoreProducts());
            intent.putExtra("dealName", lst_deal.get(position).getSPName());
            intent.putExtra("dealExpiry", lst_deal.get(position).getsP_Expiry());
            intent.putExtra("dealPrice", lst_deal.get(position).getSPRate());
            intent.putExtra("dealLocation", lst_deal.get(position).getsP_Location());
            mContext.startActivity(intent);
        });
            }




    @Override
    public int getItemCount() {
        return lst_deal.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView expiredTxt, productNameText, priceTxt, location, validity_txt;
        FrameLayout BuyTxt;
        Button buyBtn;
        ImageView productImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           productImg = itemView.findViewById(R.id.productImg);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            validity_txt = itemView.findViewById(R.id.validity_txt);
            location = itemView.findViewById(R.id.location);
            productNameText = itemView.findViewById(R.id.productNameText);
            expiredTxt = itemView.findViewById(R.id.expiredTxt);
            BuyTxt = itemView.findViewById(R.id.buyTxt);
            buyBtn = itemView.findViewById(R.id.buyBtn);

        }
    }


}
