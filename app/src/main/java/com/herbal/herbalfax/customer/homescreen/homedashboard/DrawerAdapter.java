package com.herbal.herbalfax.customer.homescreen.homedashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import com.herbal.herbalfax.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;

import java.util.List;

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private List<String> listDrawerMain;
    private int viewItem = 1;
    private int viewProg = 0;
    private int viewProglast = 2;
    private SessionPref pref;
    private CustomItemClickListener customClickListener;
    private CancelClickListener listener;
    private LogoutClickListener logoutClickListener;

    private int[] drawerIcon = {0, R.drawable.ic_icon_menu_home, R.drawable.ic_icon_menu_map_pin, R.drawable.ic_icon_menu_social, R.drawable.ic_icon_menu_map_store, R.drawable.ic_icon_menu_explore_strain, R.drawable.ic_icon_menu_shopping_cart, R.drawable.ic_icon_menu_calculator, R.drawable.ic_icon_menu_credit_card, R.drawable.ic_icon_menu_parcel, R.drawable.ic_icon_notifications_outline, 0};


    /**
     * Instantiates a new Drawer adapter.
     *
     * @param context          the context
     * @param listDrawerMain   the list drawer main

     */
    DrawerAdapter(Context context, List listDrawerMain) {
        this.context = context;
        this.listDrawerMain = listDrawerMain;
        this.pref=SessionPref.getInstance(context);
        viewProglast = listDrawerMain.size();


    }

    /**
     * This method is used to  bind layout
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == viewItem) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_items, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);
            return myViewHolder;
        } else if (viewType == viewProg) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_header, parent, false);
            MyViewHolderHeader myViewHolderHeader = new MyViewHolderHeader(v);
            return myViewHolderHeader;
        }
        else if (viewType == viewProglast) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_bottom, parent, false);
            MyViewHolderBottomHeader myViewHolderBottomHeader = new MyViewHolderBottomHeader(v);
            return myViewHolderBottomHeader;
        } else
            return null;
    }


    /**
     * The type My view holder bottom header.
     */
    public class MyViewHolderBottomHeader extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView logoutTextView;
        private ImageView logoutArrowImage;
        private LinearLayout linearLayoutSelect;

        /**
         * Instantiates a new My view holder bottom header.
         *
         * @param itemView the item view
         */
        MyViewHolderBottomHeader(View itemView) {
            super(itemView);
            logoutTextView = itemView.findViewById(R.id.logoutTextView);
            logoutArrowImage=itemView.findViewById(R.id.logoutArrowImage);
            linearLayoutSelect=itemView.findViewById(R.id.linear_layout_select);
            linearLayoutSelect.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (logoutClickListener != null) {
                logoutClickListener.onLogoutClickListener();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolderHeader) {
            MyViewHolderHeader myViewHolderHeader = (MyViewHolderHeader) holder;
            if (position == 0) {
                myViewHolderHeader.textName.setText(pref.getStringVal(SessionPref.LoginUserfullName));
            }
        }


        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            final String name = listDrawerMain.get(position);
            if (drawerIcon.length != 0) {
                myViewHolder.drawerArrowImage.setImageResource(drawerIcon[position]);
            }
            myViewHolder.drawerTextView.setText(name);
        }


    }

    @Override
    public int getItemCount() {
        return listDrawerMain.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView drawerTextView;
        ImageView drawerArrowImage;
        RelativeLayout linearLayout;
        LinearLayout linearLayoutSelect;

        MyViewHolder(View itemView) {
            super(itemView);
            linearLayoutSelect = itemView.findViewById(R.id.linear_layout_select);
            drawerArrowImage = itemView.findViewById(R.id.drawerArrowImage);
            drawerTextView = itemView.findViewById(R.id.drawerTextView);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            linearLayoutSelect.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (customClickListener != null) {
                customClickListener.onCardItemClick(view, getAdapterPosition());
            }
        }
    }


    public class MyViewHolderHeader extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView profile_image,closeImg;
        TextView professionTv;
        TextView textName;

        LinearLayout drawerHeaderLayout;

        MyViewHolderHeader(View itemView) {
            super(itemView);
            drawerHeaderLayout = itemView.findViewById(R.id.nav_headerMAin);
            profile_image = itemView.findViewById(R.id.profile_image);
            closeImg = itemView.findViewById(R.id.closeImg);
            professionTv = itemView.findViewById(R.id.professionTv);
            textName = itemView.findViewById(R.id.userName);
            closeImg.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onCancelClickListener();
            }
        }
    }



    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return viewProg;
        } else if (position == listDrawerMain.size() - 1) {
            return viewProglast;
        } else {
            return viewItem;
        }

    }


    void setOnCardItemClickListener(CustomItemClickListener mItemClick) {
        this.customClickListener = mItemClick;
    }

    void setOnCancelListener(CancelClickListener mItemClick) {
        this.listener = mItemClick;
    }

    void setOnLogoutListener(LogoutClickListener mItemClick) {
        this.logoutClickListener = mItemClick;
    }


    public interface CustomItemClickListener {

        void onCardItemClick(View view, int position);
    }


    public interface CancelClickListener {

        void onCancelClickListener();
    }

    public interface LogoutClickListener {

        void onLogoutClickListener();
    }

}
