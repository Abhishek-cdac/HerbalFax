package com.herbal.herbalfax.customer.homescreen.products.adapter;

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
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class CustProductReviewAdapter  extends RecyclerView.Adapter<CustProductReviewAdapter.ViewHolder> {

    ArrayList<Interest> lst_int;
    Context mContext;
    private final Picasso picasso;

    public CustProductReviewAdapter(ArrayList<Interest> lst_int, Context applicationContext) {
        picasso = Picasso.get();
        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public CustProductReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_product_reviews_item, parent, false);
        mContext = parent.getContext();
        return new CustProductReviewAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CustProductReviewAdapter.ViewHolder holder, int position) {

  /*      holder.HeadingTxt.setText(lst_int.get(position).getUFullName());
        holder.subHeadingTxt.setText(lst_int.get(position).getSRateReview());
        holder.ratingDate.setText(lst_int.get(position).getSRateStar() + " out of 5");
        picasso.load(lst_int.get(position).getUProPic())
                .into(holder.imgprofile);

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(lst_int.get(position).getStoreRatingTime(), inputFormatter);
        String formattedDate = outputFormatter.format(date);

        Log.e("formattedDate", "" + formattedDate);

        holder.date.setText(formattedDate);

        if (lst_int.get(position).getSRateStar().equals("0")) {
            holder.ratingOne.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingTwo.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getSRateStar().equals("1")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingTwo.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getSRateStar().equals("2")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingTwo.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getSRateStar().equals("3")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingTwo.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingThree.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getSRateStar().equals("4")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingTwo.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingThree.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingFour.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

        } else if (lst_int.get(position).getSRateStar().equals("5")) {
            holder.ratingOne.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingTwo.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingThree.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingFour.setImageResource(R.drawable.ic_icon_rating);
            holder.ratingFive.setImageResource(R.drawable.ic_icon_rating);

        }
*/

    }

    @Override
    public int getItemCount() {
        return 6;
    } // lst_int.size();


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
