package com.herbal.herbalfax.customer.selectInterest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.getintrestmodel.AllInterest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder>  {

    ArrayList<AllInterest> lst_int ;
    ArrayList<AllInterest> lst_intFilter ;
    Context mContext;

    private final Picasso picasso;

    public InterestAdapter(ArrayList<AllInterest> lst_int) {


        this.lst_int = lst_int;
        this.lst_intFilter = lst_int;
        picasso = Picasso.get();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_interest, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     holder.txt_intresrt.setText(lst_int.get(position).getIntTitle());
     picasso.load(lst_int.get(position).getIntImg())

                .into(holder.iv_tech);
        if (lst_int.get(position).isSelected()) {
            holder.iv_cross.setVisibility(View.VISIBLE);
//            Animation fadeInAnimation = AnimationUtils.loadAnimation(mContext, R.anim.shake);
//            holder.itemView.startAnimation(fadeInAnimation);
        } else {
            holder.iv_cross.setVisibility(View.GONE);
        }

        holder.iv_cross.setOnClickListener(view -> {
            lst_intFilter.get(position).setSelected(!lst_intFilter.get(position).isSelected());
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return  lst_int.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_intresrt;
        ImageView iv_cross, iv_tech;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_intresrt = itemView.findViewById(R.id.txt_intresrt);
            iv_cross = itemView.findViewById(R.id.iv_cross);
            iv_tech = itemView.findViewById(R.id.iv_tech);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 //  iv_cross.setVisibility(View.VISIBLE);
                    lst_int.get(getAdapterPosition()).setSelected(true);
                    notifyDataSetChanged();
                }
            });
        }
    }



}
