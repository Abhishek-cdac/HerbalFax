package com.herbal.herbalfax.common_screen.landingpage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyView> {

    private List<String> list;
    Context context;

    public class MyView extends RecyclerView.ViewHolder {

        TextView categoryTxt;
        CardView cardviewCategory;


        public MyView(View view) {
            super(view);

            cardviewCategory = view.findViewById(R.id.cardviewCategory);
            categoryTxt = (TextView) view.findViewById(R.id.categoryTxt);
        }
    }

    public CategoryAdapter(List<String> horizontalList, Context context) {
        this.list = horizontalList;
        this.context = context;
    }


    @Override
    public MyView onCreateViewHolder(ViewGroup parent,
                                     int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new MyView(itemView);
    }


    @Override
    public void onBindViewHolder(final MyView holder,final int position) {
        holder.categoryTxt.setText(list.get(position));
        holder.cardviewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cardviewCategory.setCardBackgroundColor(context.getResources().getColor(R.color.black));
                holder.categoryTxt.setTextColor(context.getResources().getColor(R.color.white));

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}