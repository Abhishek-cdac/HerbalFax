package com.herbal.herbalfax.customer.homescreen.group.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.group.groupdetail.GroupDetailActivity;
import com.herbal.herbalfax.customer.homescreen.group.yourgroupmodel.Group;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.squareup.picasso.Picasso;

import java.util.List;

public class YourGroupAdapter extends RecyclerView.Adapter<YourGroupAdapter.MyViewHolder> {

    private List<Group> yourGroupList;
    Context context;
    Picasso picasso;
    Onclick itemClick;




    public YourGroupAdapter(List<Group> yourGroupList, FragmentActivity activity, Onclick itemClick) {
        this.yourGroupList = yourGroupList;
        this.context = activity;
        this.itemClick = itemClick;
        picasso = Picasso.get();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.yourgroup_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(yourGroupList.get(position).getGrpName());
        if (!yourGroupList.get(position).getGrpImage().equals("")) {
            picasso.load(yourGroupList.get(position).getGrpImage())
                    .into(holder.icon);
        } else {

        }
String groupID = yourGroupList.get(position).getIdgroups();
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(context, GroupDetailActivity.class);
                intent.putExtra("groupId",groupID);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return yourGroupList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public CardView cardView;
        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            icon = view.findViewById(R.id.icon);
            cardView = view.findViewById(R.id.cardView);

        }
    }
}