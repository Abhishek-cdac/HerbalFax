package com.herbal.herbalfax.vendor.storelist;

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
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.vendor.storelist.storelistmodel.Store;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyStoreListAdapter extends RecyclerView.Adapter<MyStoreListAdapter.ViewHolder> {

    ArrayList<Store> lst_int;
    ArrayList<Store> lst_intFilter;
    Context mContext;
    private final Picasso picasso;
    Onclick itemClick;


    public MyStoreListAdapter(ArrayList<Store> lst_int, Context applicationContext, Onclick itemClick) {

        picasso = Picasso.get();
        this.lst_int = lst_int;
        this.mContext = applicationContext;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storelist_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


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
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClicks(view, position, 5, StoreId);
            }
        });



        if (lst_int.get(position).getStoreRating().equals("0")) {
            holder.ratingOne.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingTwo.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getStoreRating().equals("1")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingTwo.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getStoreRating().equals("2")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingTwo.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getStoreRating().equals("3")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingTwo.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingThree.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getStoreRating().equals("4")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingTwo.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingThree.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingFour.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getStoreRating().equals("5")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingTwo.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingThree.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingFour.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingFive.setImageResource(R.drawable.ic_icon_rating);

        }
    }

    @Override
    public int getItemCount() {
        return lst_int.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView StoreImg, ratingOne, ratingTwo, ratingThree, ratingFour, ratingFive;

        TextView storeName, StoreCategoryText, StoreAddressText;
        CardView cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardview = itemView.findViewById(R.id.cardview);
            StoreImg = itemView.findViewById(R.id.StoreImg);
            StoreCategoryText = itemView.findViewById(R.id.StorecategoryText);
            storeName = itemView.findViewById(R.id.StoreNameText);
            StoreAddressText = itemView.findViewById(R.id.StoreAddressText);
            ratingOne = itemView.findViewById(R.id.one);
            ratingTwo = itemView.findViewById(R.id.two);
            ratingThree = itemView.findViewById(R.id.three);
            ratingFour = itemView.findViewById(R.id.four);
            ratingFive = itemView.findViewById(R.id.five);

        }
    }


}
