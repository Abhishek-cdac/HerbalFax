package com.herbal.herbalfax.customer.homescreen.group.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.group.yourgroupmodel.Group;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.squareup.picasso.Picasso;

import java.util.List;

public class YourGroupAdapter extends RecyclerView.Adapter<YourGroupAdapter.MyViewHolder> {

    private List<Group> yourGroupList;
    Context context;
Picasso picasso;
Onclick itemClick;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            icon = (ImageView) view.findViewById(R.id.icon);

        }
    }


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

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(yourGroupList.get(position).getGrpName());
        if (!yourGroupList.get(position).getGrpImage().equals("")){
            picasso.load(yourGroupList.get(position).getGrpImage())
                    .into(holder.icon);
        }else{

        }

    }

    @Override
    public int getItemCount() {
        return yourGroupList.size();
    }
}