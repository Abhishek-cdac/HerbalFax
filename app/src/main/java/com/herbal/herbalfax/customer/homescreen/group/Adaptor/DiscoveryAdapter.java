package com.herbal.herbalfax.customer.homescreen.group.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.group.discovermodel.Group;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DiscoveryAdapter extends RecyclerView.Adapter<DiscoveryAdapter.MyViewHolder> {

    private List<Group> discoverList;
    Context context;
    Picasso picasso;
    Onclick itemClick;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, joinTxt;
        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            joinTxt = (TextView) view.findViewById(R.id.joinTxt);
            icon = (ImageView) view.findViewById(R.id.icon);

        }
    }


    public DiscoveryAdapter(List<Group> discoverList, FragmentActivity activity, Onclick itemClick) {
        this.discoverList = discoverList;
        this.context = activity;
        this.itemClick = itemClick;
        picasso = Picasso.get();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_listitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(discoverList.get(position).getGrpName());

        if (!discoverList.get(position).getGrpImage().equals("")) {
            picasso.load(discoverList.get(position).getGrpImage())
                    .into(holder.icon);
        } else {

        }
        String GroupId = discoverList.get(position).getIdgroups();
        holder.joinTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClicks(view, position, 17, GroupId);
            }
        });


    }

    @Override
    public int getItemCount() {
        return discoverList.size();
    }

}
