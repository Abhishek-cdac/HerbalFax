package com.herbal.herbalfax.customer.blogs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    ArrayList<Interest> lst_int;
    ArrayList<Interest> lst_intFilter;
    Context mContext;

    public NewsAdapter(ArrayList<Interest> lst_int, Context applicationContext) {


        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newslist_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.readMoreCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(mContext, BlogDetailsActivity.class);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return 6;  // lst_intFilter.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView headingTxt, newsHeadingTxt, subHeadingTxt, timeTxt, personTxt;
        ImageView imgProfile;
        CardView readMoreCardview;
        LinearLayout likeLl, reportLl, ll_newsrecylcer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProfile = itemView.findViewById(R.id.imgprofile);
            readMoreCardview = itemView.findViewById(R.id.readMoreCardview);
            headingTxt = itemView.findViewById(R.id.rmheadingTxt);
            newsHeadingTxt = itemView.findViewById(R.id.HeadingTxt);
            likeLl = itemView.findViewById(R.id.likeLl);
            reportLl = itemView.findViewById(R.id.reportLl);
            subHeadingTxt = itemView.findViewById(R.id.subHeadingTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            personTxt = itemView.findViewById(R.id.personTxt);
            ll_newsrecylcer = itemView.findViewById(R.id.ll_newsrecylcer);

          //  ll_newsrecylcer.setOnClickListener(v -> showBottomSheet(getAdapterPosition()));

        }
    }

}
