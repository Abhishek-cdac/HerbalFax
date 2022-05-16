package com.herbal.herbalfax.common_screen.landingpage.adapter;

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

public class ArticlesAdapter  extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder>  {

    ArrayList<Interest> lst_int ;
    private final Picasso picasso;
    Context mContext;

    public ArticlesAdapter(ArrayList<Interest> lst_int, Context applicationContext) {
        picasso = Picasso.get();
        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_item, parent, false);
        mContext = parent.getContext();
        return new ArticlesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesAdapter.ViewHolder holder, int position) {
        if( position == 0) {
            holder.nameArticle.setText("All");
            picasso.load(R.drawable.all)
                    .into(holder.articleImg);
        }
       else if (position == 1) {

            holder.nameArticle.setText("News");
            picasso.load(R.drawable.newspaper)
                    .into(holder.articleImg);
        } else if( position == 2) {
            holder.nameArticle.setText("Fashion");
            picasso.load(R.drawable.fashion)
                    .into(holder.articleImg);
        }else if( position == 3) {
            holder.nameArticle.setText("Sports");
            picasso.load(R.drawable.football)
                    .into(holder.articleImg);
        }else if( position == 4) {
            holder.nameArticle.setText("Tech");
            picasso.load(R.drawable.technology)
                    .into(holder.articleImg);
        }else if( position == 5) {
            holder.nameArticle.setText("Biology");
            picasso.load(R.drawable.biology)
                    .into(holder.articleImg);
        }
    }

    @Override
    public int getItemCount() {
        return 6;  // lst_intFilter.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameArticle;
        ImageView articleImg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameArticle = itemView.findViewById(R.id.articleText);
            articleImg = itemView.findViewById(R.id.articleImg);

        }
    }



}
