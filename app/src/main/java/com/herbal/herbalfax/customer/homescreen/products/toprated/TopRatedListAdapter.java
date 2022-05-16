package com.herbal.herbalfax.customer.homescreen.products.toprated;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.homedashboard.DrawerAdapter;
import com.herbal.herbalfax.customer.homescreen.products.toprated.beancmodel.TopVendorListResponse;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.vendor.sellerproduct.productlistmodel.StoreProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopRatedListAdapter extends RecyclerView.Adapter<TopRatedListAdapter.ViewHolder> {

    ArrayList<TopVendorListResponse.Vendor> lst_product;
    Context mContext;
    private CustomItemClickListener customClickListener;
    private final Picasso picasso;
    Onclick itemClick;


    public TopRatedListAdapter(ArrayList<TopVendorListResponse.Vendor> lst_product, Context applicationContext, Onclick itemClick) {
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
        TopVendorListResponse.Vendor vendor=lst_product.get(position);
        holder.name.setText(vendor.getUFullName());
        holder.availStore.setText(vendor.getUActive()+" "+mContext.getString(R.string.store_available));
        loadImage(holder.imgProfile,vendor.getUProPic());
        String rating=vendor.getURatings();
        Float value= Float.parseFloat(rating);
        holder.ratingBar.setRating(value);

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
        return lst_product.size();
    }


    /**
     * Load image.
     *
     * @param view     the view
     * @param imageUrl the image url
     */
    public void loadImage(ImageView view, String imageUrl) {

        Glide.with(view.getContext()).load(imageUrl)
                .dontAnimate().into(view);

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgProfile;
        TextView name, availStore, categoryTxt, descTxt, PriceTxt;
        RatingBar ratingBar;
        CardView parentView;
//        FrameLayout cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProfile = itemView.findViewById(R.id.profileImg);
            name = itemView.findViewById(R.id.name);

            availStore = itemView.findViewById(R.id.availStore);
            PriceTxt = itemView.findViewById(R.id.PriceTxt);
            ratingBar=itemView.findViewById(R.id.ratingBar3);
            parentView = itemView.findViewById(R.id.parentView);
            parentView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (customClickListener != null) {
                customClickListener.onCardItemClick(v, getAdapterPosition());
            }
        }
    }

    /**
     * The interface Custom item click listener.
     */

    public interface CustomItemClickListener {
        /**
         * On card item click.
         *
         * @param view     the view
         * @param position the position
         */
        void onCardItemClick(View view, int position);
    }

    void setOnCardItemClickListener(CustomItemClickListener mItemClick) {
        this.customClickListener = mItemClick;
    }
}