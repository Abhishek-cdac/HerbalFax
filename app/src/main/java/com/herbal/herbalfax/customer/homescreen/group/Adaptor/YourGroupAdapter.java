package com.herbal.herbalfax.customer.homescreen.group.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import java.util.List;

public class YourGroupAdapter extends RecyclerView.Adapter<YourGroupAdapter.MyViewHolder> {

    private List<Interest> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public ImageView icon;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            icon = (ImageView) view.findViewById(R.id.icon);

        }
    }


    public YourGroupAdapter(List<Interest> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.yourgroup_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}