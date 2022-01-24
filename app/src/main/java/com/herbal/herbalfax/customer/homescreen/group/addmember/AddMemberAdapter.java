package com.herbal.herbalfax.customer.homescreen.group.addmember;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class AddMemberAdapter extends RecyclerView.Adapter<AddMemberAdapter.ViewHolder> {

    ArrayList<Interest> lst_int;

    Context mContext;

    public AddMemberAdapter(ArrayList<Interest> lst_int, Context applicationContext) {

        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public AddMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_member_item, parent, false);
        mContext = parent.getContext();
        return new AddMemberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddMemberAdapter.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 6;  // lst_int.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        ImageView userImg;
        LinearLayout ll_head;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_head = itemView.findViewById(R.id.ll_head);
            userImg = itemView.findViewById(R.id.userImg);
            username = itemView.findViewById(R.id.username);
        }
    }
}

