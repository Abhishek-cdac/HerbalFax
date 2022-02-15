package com.herbal.herbalfax.common_screen.landingpage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyView> {

    private final List<String> list;
    Context context;

    public static class MyView extends RecyclerView.ViewHolder {

        TextView categoryTxt;
        CardView cardViewCategory;


        public MyView(View view) {
            super(view);

            cardViewCategory = view.findViewById(R.id.cardviewCategory);
            categoryTxt = (TextView) view.findViewById(R.id.categoryTxt);
        }
    }

    public CategoryAdapter(List<String> horizontalList, Context context) {
        this.list = horizontalList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyView onCreateViewHolder(ViewGroup parent,
                                     int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new MyView(itemView);
    }


    @Override
    public void onBindViewHolder(final MyView holder,final int position) {
        holder.categoryTxt.setText(list.get(position));
        holder.cardViewCategory.setOnClickListener(view -> {
            holder.cardViewCategory.setCardBackgroundColor(context.getResources().getColor(R.color.black));
            holder.categoryTxt.setTextColor(context.getResources().getColor(R.color.white));

        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}