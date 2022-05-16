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
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddCommentsAdapter extends RecyclerView.Adapter<AddCommentsAdapter.ViewHolder> {


    List<com.herbal.herbalfax.common_screen.landingpage.events.eventdetail.commentlstmodel.EventComment> lst_int;
    Picasso picasso;

    Context mContext;

    public AddCommentsAdapter(List<com.herbal.herbalfax.common_screen.landingpage.events.eventdetail.commentlstmodel.EventComment> lst_cart_item, Context applicationContext) {
        this.lst_int = lst_cart_item;
        this.mContext = applicationContext;
        picasso = Picasso.get();
    }



    @NonNull
    @Override
    public AddCommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_people_comment_list, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userComments.setText(lst_int.get(position).getECComments());
        holder.userName.setText(lst_int.get(position).getUFullName());
        if (lst_int.get(position).getUProPic().equals("http://localhost/NIS/Herbalfax/upload/user_pro/"))
        {
            picasso.load(R.drawable.addpeople)
                    .into(holder.userImg);
        } else {
            picasso.load(lst_int.get(position).getUProPic())
                    .into(holder.userImg);
        }
    }


    @Override
    public int getItemCount() {
        return lst_int.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userImg;
        TextView userName, userComments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImg = itemView.findViewById(R.id.userImg);
            userName = itemView.findViewById(R.id.userName);
            userComments = itemView.findViewById(R.id.userComments);

        }
    }


}
