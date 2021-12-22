package com.herbal.herbalfax.vendor.storedetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.vendor.storedetail.storedetailmodel.StoreRatings;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class SellerStoreReviewAdapter extends RecyclerView.Adapter<SellerStoreReviewAdapter.ViewHolder> {

    ArrayList<StoreRatings> lst_int;
    Context mContext;
    private final Picasso picasso;

    /**
     * @param lst_int
     * @param applicationContext
     */
    public SellerStoreReviewAdapter(ArrayList<StoreRatings> lst_int, Context applicationContext) {
        picasso = Picasso.get();
        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public SellerStoreReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_store_reviews_item, parent, false);
        mContext = parent.getContext();
        return new SellerStoreReviewAdapter.ViewHolder(view);
    }

    /**
     * @param holder
     * @param position
     */
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull SellerStoreReviewAdapter.ViewHolder holder, int position) {

        holder.HeadingTxt.setText(lst_int.get(position).getuFullName());
        holder.subHeadingTxt.setText(lst_int.get(position).getsRateReview());
        holder.ratingDate.setText(lst_int.get(position).getsRateStar() + " out of 5");
        picasso.load(lst_int.get(position).getuProPic())
                .into(holder.imgprofile);


        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(lst_int.get(position).getStoreRatingTime(), inputFormatter);
        String formattedDate = outputFormatter.format(date);

        Log.e("formattedDate", "" + formattedDate);

        holder.date.setText(formattedDate);
        if (lst_int.get(position).getsRateStar().equals("0")) {
            holder.ratingOne.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingTwo.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getsRateStar().equals("1")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingTwo.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getsRateStar().equals("2")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingTwo.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getsRateStar().equals("3")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingTwo.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingThree.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getsRateStar().equals("4")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingTwo.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingThree.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingFour.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getsRateStar().equals("5")) {
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
        TextView HeadingTxt, subHeadingTxt, ratingDate, date;
        ImageView imgprofile, ratingOne, ratingTwo, ratingThree, ratingFour, ratingFive;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            HeadingTxt = itemView.findViewById(R.id.HeadingTxt);
            subHeadingTxt = itemView.findViewById(R.id.subHeadingTxt);
            ratingDate = itemView.findViewById(R.id.ratingDate);
            date = itemView.findViewById(R.id.date);
            imgprofile = itemView.findViewById(R.id.imgprofile);
            ratingOne = itemView.findViewById(R.id.one);
            ratingTwo = itemView.findViewById(R.id.two);
            ratingThree = itemView.findViewById(R.id.three);
            ratingFour = itemView.findViewById(R.id.four);
            ratingFive = itemView.findViewById(R.id.five);

        }
    }


}