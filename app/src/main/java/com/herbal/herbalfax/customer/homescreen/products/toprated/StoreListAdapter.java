package com.herbal.herbalfax.customer.homescreen.products.toprated;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.products.toprated.beancmodel.TopVendorListResponse;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.squareup.picasso.Picasso;
import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder> {

    List<TopVendorListResponse.Store> lst_product;
    Context mContext;
    private CustomItemClickListener customClickListener;
    private final Picasso picasso;
    Onclick itemClick;


    public StoreListAdapter(List<TopVendorListResponse.Store> lst_product, Context applicationContext, Onclick itemClick) {
        picasso = Picasso.get();
        this.lst_product = lst_product;
        this.mContext = applicationContext;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public StoreListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topstore_list_item, parent, false);
        mContext = parent.getContext();
        return new StoreListAdapter.ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull StoreListAdapter.ViewHolder holder, int position) {

        TopVendorListResponse.Store store=lst_product.get(position);

        holder.name.setText(store.getStoreName());
        holder.availStore.setText(store.getStoreActive()+" "+mContext.getString(R.string.store_available));
        Float ratingValue= Float.parseFloat(store.getStoreRating());
        holder.ratingBar.setRating( ratingValue);
        holder.locationTxt.setText(store.getStoreLocation());
        loadImage(holder.productImg,store.getStoreLogo());

        if(position==lst_product.size()-1)
        {
            holder.view.setVisibility(View.GONE);
        }


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


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImg;
        TextView name, availStore,locationTxt;
        RatingBar ratingBar;
        View view;
        CardView cardview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            productImg = itemView.findViewById(R.id.profile);
            view=itemView.findViewById(R.id.itemDivideLine);
            availStore = itemView.findViewById(R.id.availStore);
            locationTxt=itemView.findViewById(R.id.locationTxt);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            cardview = itemView.findViewById(R.id.parentCard);
            view=itemView.findViewById(R.id.itemDivideLine);
            cardview.setOnClickListener(this);

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

    /**
     * Load image.
     *
     * @param view     the view
     * @param imageUrl the image url
     */
    public static void loadImage(ImageView view, String imageUrl) {

        Glide.with(view.getContext()).load(imageUrl)
                .dontAnimate().into(view);

    }

}