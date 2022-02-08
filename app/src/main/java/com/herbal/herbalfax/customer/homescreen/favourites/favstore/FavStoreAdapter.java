package com.herbal.herbalfax.customer.homescreen.favourites.favstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.favourites.favstore.model.StoreAddToFav;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavStoreAdapter  extends RecyclerView.Adapter<FavStoreAdapter.ViewHolder> {

    ArrayList<StoreAddToFav> lst_int;
    Context mContext;
    private final Picasso picasso;
    Onclick itemClick;


    public FavStoreAdapter(List<StoreAddToFav> lst_int, Context applicationContext) {

        picasso = Picasso.get();
        this.lst_int = (ArrayList<StoreAddToFav>) lst_int;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public FavStoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_storelist_item, parent, false);
        mContext = parent.getContext();
        return new FavStoreAdapter.ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull FavStoreAdapter.ViewHolder holder, int position) {

        if (lst_int.get(position).getStoreLogo() != null) {
            if (!lst_int.get(position).getStoreLogo().equals("")) {
                picasso.load(lst_int.get(position).getStoreLogo())
                        .into(holder.StoreImg);
            }
        }
        holder.storeName.setText(lst_int.get(position).getStoreName());
        holder.StoreAddressText.setText(lst_int.get(position).getStoreLocation());
        holder.StoreCategoryText.setText(lst_int.get(position).getSCatTitle());
        holder.storeName.setText(lst_int.get(position).getStoreName());
        String StoreId = lst_int.get(position).getIdstores();


        if (lst_int.get(position).getStoreRating().equals("0")) {
            holder.ratingOne.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingTwo.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getStoreRating().equals("1")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
            holder.ratingTwo.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getStoreRating().equals("2")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
            holder.ratingTwo.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
            holder.ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getStoreRating().equals("3")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
            holder.ratingTwo.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
            holder.ratingThree.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getStoreRating().equals("4")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
            holder.ratingTwo.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
            holder.ratingThree.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
            holder.ratingFour.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getStoreRating().equals("5")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
            holder.ratingTwo.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
            holder.ratingThree.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
            holder.ratingFour.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
            holder.ratingFive.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);

        }
    }

    @Override
    public int getItemCount() {
        return lst_int.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView StoreImg, ratingOne, ratingTwo, ratingThree, ratingFour, ratingFive;
        TextView storeName, StoreCategoryText, StoreAddressText, timeTxt;
        CardView cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardview = itemView.findViewById(R.id.cardview);
            StoreImg = itemView.findViewById(R.id.StoreImg);
            StoreCategoryText = itemView.findViewById(R.id.StorecategoryText);
            storeName = itemView.findViewById(R.id.StoreNameText);
            StoreAddressText = itemView.findViewById(R.id.StoreAddressText);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            ratingOne = itemView.findViewById(R.id.one);
            ratingTwo = itemView.findViewById(R.id.two);
            ratingThree = itemView.findViewById(R.id.three);
            ratingFour = itemView.findViewById(R.id.four);
            ratingFive = itemView.findViewById(R.id.five);

        }
    }


}

