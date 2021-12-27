package com.herbal.herbalfax.customer.homescreen.deals.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.deals.NewDealsCategorys;
import com.herbal.herbalfax.customer.selectInterest.Interest;


import java.util.ArrayList;
import java.util.List;

public class NewDealsAdaptor  extends RecyclerView.Adapter<NewDealsAdaptor.ViewHolder> {

    ArrayList<Interest> DealsCategory;
   /* ArrayList<Interest> lst_intFilter;*/
    Context mContext;

    public NewDealsAdaptor(ArrayList<Interest> DealsCategory, Context applicationContext) {
        this.DealsCategory = DealsCategory;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public NewDealsAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_deals_item, parent, false);
        mContext = parent.getContext();
        return new NewDealsAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewDealsAdaptor.ViewHolder holder, int position) {
      /*  NewDealsCategorys deals = DealsCategory.get(position);
        holder.category.setText(deals.getCategory());*/
        if(position==0){
            holder.category.setText("All New");
        }
        else if(position==1) {
            holder.category.setText("Flowers");
        }
        else if(position==2)
        {
            holder.category.setText("Edibles");
        } else if(position==3)
        {
            holder.category.setText("Vape Pens");
        }

    }

    @Override
    public int getItemCount() {
        return 4;  // lst_intFilter.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView category;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category = (TextView)itemView.findViewById(R.id.categories);

        }
    }


}

