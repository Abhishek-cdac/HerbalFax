package com.herbal.herbalfax.common_screen.landingpage.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder> {

    ArrayList<Interest> lst_int;

    Context mContext;
    private final Picasso picasso;

    public TrendingAdapter(ArrayList<Interest> lst_int, Context applicationContext) {
        picasso = Picasso.get();
        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public TrendingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TrendingAdapter.ViewHolder holder, int position) {

        if (position == 0) {
            holder.headingTxt.setText("Tutorials");

            holder.subHeadingTxt.setText("The 1914 translation by H. Rackham");
            picasso.load(R.drawable.profileimg)
                    .into(holder.imgProfile);
        } else if( position == 1) {
            holder.subHeadingTxt.setText("Release of Letraset sheets containing");
            holder.headingTxt.setText("Medicals");
            picasso.load(R.drawable.profileimg)
                    .into(holder.imgProfile);
        }else if( position == 2) {
            holder.headingTxt.setText("Stores");
            holder.subHeadingTxt.setText("Hampden-Sydney College in Virginia");
            picasso.load(R.drawable.profileimg)
                    .into(holder.imgProfile);
        }
    }

    @Override
    public int getItemCount() {
        return 3;  // lst_intFilter.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView headingTxt, subHeadingTxt, timeTxt, personTxt;
        ImageView imgProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProfile = itemView.findViewById(R.id.imgprofile);
            headingTxt = itemView.findViewById(R.id.headingTxt);
            subHeadingTxt = itemView.findViewById(R.id.subHeadingTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            personTxt = itemView.findViewById(R.id.personTxt);


        }
    }
}

