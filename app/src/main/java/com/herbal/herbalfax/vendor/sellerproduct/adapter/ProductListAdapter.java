package com.herbal.herbalfax.vendor.sellerproduct.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    ArrayList<StoreProduct> lst_product;
    Context mContext;
    /*private final Picasso picasso;*/
    Onclick itemClick;


    public ProductListAdapter(ArrayList<StoreProduct> lst_product, Context applicationContext, Onclick itemClick) {
        /*picasso = Picasso.get();*/
        this.lst_product = lst_product;
        this.mContext = applicationContext;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productlist_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if (lst_product.get(position).getSPPPath() != null) {
            if (!lst_product.get(position).getSPPPath().equals("")) {
               /* picasso.load(String.valueOf(lst_product.get(position).getSPPPath()))
                        .into(holder.imgProfile);*/
            }
        }

        if (position == 1) {
            holder.herbalfax_choiceTxt.setVisibility(View.VISIBLE);
        } else if (position == 4) {
            holder.herbalfax_choiceTxt.setVisibility(View.VISIBLE);
        } else {
            holder.herbalfax_choiceTxt.setVisibility(View.GONE);

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

        holder.moreImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onItemClicks(v, position, 12, "");

            }
        });


        holder.deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemClick.onItemClicks(view, position, 11, productId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst_product.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProfile, moreImg;
        TextView headingTxt, productName, categoryTxt, descTxt, PriceTxt;
        CardView editCard, deleteCard, herbalfax_choiceTxt;
        FrameLayout cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            herbalfax_choiceTxt = itemView.findViewById(R.id.herbalfax_choiceTxt);
            headingTxt = itemView.findViewById(R.id.headingTxt);
            imgProfile = itemView.findViewById(R.id.imgprofile);
            descTxt = itemView.findViewById(R.id.descTxt);
            categoryTxt = itemView.findViewById(R.id.categoryTxt);
            productName = itemView.findViewById(R.id.subHeadingTxt);
            PriceTxt = itemView.findViewById(R.id.PriceTxt);
            deleteCard = itemView.findViewById(R.id.deleteCard);
            editCard = itemView.findViewById(R.id.editCard);
            cardview = itemView.findViewById(R.id.cardview);
            moreImg = itemView.findViewById(R.id.moreImg);

        }
    }


}
