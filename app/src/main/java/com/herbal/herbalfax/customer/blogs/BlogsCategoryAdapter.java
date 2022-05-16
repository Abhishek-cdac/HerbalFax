package com.herbal.herbalfax.customer.blogs;

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
import com.herbal.herbalfax.customer.blogs.blogmodel.BlogCategory;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BlogsCategoryAdapter extends RecyclerView.Adapter<BlogsCategoryAdapter.ViewHolder> {

    ArrayList<BlogCategory> lst_blogCategory;
    private final Picasso picasso;
    Context mContext;
    private final Onclick itemClick;

    public BlogsCategoryAdapter(ArrayList<BlogCategory> lst_blogCategory, Context applicationContext, Onclick itemClick) {
        picasso = Picasso.get();
        this.lst_blogCategory = lst_blogCategory;
        this.mContext = applicationContext;
        this.itemClick = itemClick;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nameArticle.setText(lst_blogCategory.get(position).getBCatTitle());
        picasso.load(lst_blogCategory.get(position).getBCatImage()).into(holder.articleImg);
        String categoryId = lst_blogCategory.get(position).getIdblogCategories();
        holder.cardview.setOnClickListener(view -> itemClick.onItemClicks(view, position, 3, categoryId));
    }

    @Override
    public int getItemCount() {
        return lst_blogCategory.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameArticle;
        ImageView articleImg;
        CardView cardview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardview = itemView.findViewById(R.id.cardview);
            nameArticle = itemView.findViewById(R.id.articleText);
            articleImg = itemView.findViewById(R.id.articleImg);
        }
    }


}
