package com.herbal.herbalfax.customer.homescreen.deals.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.vendor.sellerproduct.productlistmodel.ProductCategory;

import java.util.ArrayList;

public class NewDealsCategoriesAdaptor extends RecyclerView.Adapter<NewDealsCategoriesAdaptor.ViewHolder> {

    ArrayList<ProductCategory> DealsCategory;
    Context mContext;

    private final Onclick itemClick;
    public NewDealsCategoriesAdaptor(ArrayList<ProductCategory> DealsCategory, Context applicationContext, Onclick itemClick) {
        this.DealsCategory = DealsCategory;
        this.mContext = applicationContext;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public NewDealsCategoriesAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_deals_item, parent, false);
        mContext = parent.getContext();
        return new NewDealsCategoriesAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewDealsCategoriesAdaptor.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.category.setText(DealsCategory.get(position).getSPCTitle());
        String categoryId = DealsCategory.get(position).getIdstoreProductCategories();
        holder.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClicks(view, position, 15, categoryId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DealsCategory.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView category;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category = (TextView) itemView.findViewById(R.id.categories);

        }
    }


}

