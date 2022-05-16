package com.herbal.herbalfax.customer.homescreen.group.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.group.addmember.model.User;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class AddPeopleListAdaptor extends RecyclerView.Adapter<AddPeopleListAdaptor.ViewHolder> {
    ArrayList<User> lst_int;
    Context mContext;
    Picasso picasso;
    private int lastSelectedPosition = -1;
    public AddPeopleListAdaptor(ArrayList<User> lst_cart_item, Context applicationContext) {
        this.lst_int = lst_cart_item;
        picasso = Picasso.get();
        this.mContext = applicationContext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_people_list, parent, false);
        mContext = parent.getContext();
        return new AddPeopleListAdaptor.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final User model = lst_int.get(position);
        holder.userName.setText(lst_int.get(position).getUFullName());
        holder.userProf.setText(lst_int.get(position).getProfTitle());
        if (lst_int.get(position).getUProPic() != null) {
            if (!lst_int.get(position).getUProPic().equals("")) {
                picasso.load(lst_int.get(position).getUProPic())
                        .into(holder.userImage);
            }
        }
        holder.cardHead.setBackgroundColor(model.isSelected() ? R.color.green : Color.WHITE);



        holder.RelativeHead.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if(lastSelectedPosition > 0) {
                    lst_int.get(lastSelectedPosition).setSelected(false);
                }

                model.setSelected(!model.isSelected());
                holder.cardHead.setBackgroundColor(model.isSelected() ? R.color.green : Color.WHITE);

                // store last selected item position

             /*   lastSelectedPosition = holder.getAdapterPosition();
                Log.e("lastSelectedPosition",""+lastSelectedPosition);
                int id = lastSelectedPosition;
                Log.e("",""+lastSelectedPosition);
                String text = "";
                for (User model : lst_int) {
                    if (model.isSelected()) {
                        text += model.getIdusers();
                    }
                }
                Log.e("lastSelectedPositions","Output : " + text);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst_int.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        RelativeLayout RelativeHead;
        TextView userName, userProf;
        CardView cardHead;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.userName);
            userProf = itemView.findViewById(R.id.userProf);
            cardHead = itemView.findViewById(R.id.cardHead);
            RelativeHead = itemView.findViewById(R.id.RelativeHead);
        }
    }
    public ArrayList<User> getSelected() {
        ArrayList<User> selected = new ArrayList<>();
        for (int i = 0; i < lst_int.size(); i++) {
            if (lst_int.get(i).isSelected()) {
                selected.add(lst_int.get(i));
            }
        }
        return selected;
    }

}

