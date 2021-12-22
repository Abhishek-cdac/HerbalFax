package com.herbal.herbalfax.customer.homescreen.products.toprated;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.vendor.sellerproduct.productlistmodel.StoreProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopRatedListAdapter extends RecyclerView.Adapter<TopRatedListAdapter.ViewHolder> {

    ArrayList<StoreProduct> lst_product;
    Context mContext;
    private final Picasso picasso;
    Onclick itemClick;


    public TopRatedListAdapter(ArrayList<StoreProduct> lst_product, Context applicationContext, Onclick itemClick) {
        picasso = Picasso.get();
        this.lst_product = lst_product;
        this.mContext = applicationContext;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public TopRatedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.toprated_list_item, parent, false);
        mContext = parent.getContext();
        return new TopRatedListAdapter.ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull TopRatedListAdapter.ViewHolder holder, int position) {


   /*     if (lst_product.get(position).getSPPPath() != null) {
            if (!lst_product.get(position).getSPPPath().equals("")) {
                picasso.load(String.valueOf(lst_product.get(position).getSPPPath()))
                        .into(holder.imgProfile);
            }
        }
        holder.productName.setText(lst_product.get(position).getSPName());
        holder.PriceTxt.setText(" : " + lst_product.get(position).getSPRate());
        holder.categoryTxt.setText(lst_product.get(position).getSPCTitle());
        holder.descTxt.setText(lst_product.get(position).getSPDesc());
        String productId = lst_product.get(position).getIdstoreProducts();
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClicks(view, position, 10, productId);


            }
        });


        holder.deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemClick.onItemClicks(view, position, 11, productId);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return    7;//lst_product.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProfile;
        TextView headingTxt, productName, categoryTxt, descTxt, PriceTxt;
        CardView editCard, deleteCard;
        FrameLayout cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            headingTxt = itemView.findViewById(R.id.headingTxt);
            imgProfile = itemView.findViewById(R.id.imgprofile);
            descTxt = itemView.findViewById(R.id.descTxt);
            categoryTxt = itemView.findViewById(R.id.categoryTxt);
            productName = itemView.findViewById(R.id.subHeadingTxt);
            PriceTxt = itemView.findViewById(R.id.PriceTxt);
            cardview = itemView.findViewById(R.id.cardview);

        }
    }


}