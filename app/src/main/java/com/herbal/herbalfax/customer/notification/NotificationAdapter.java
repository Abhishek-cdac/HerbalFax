package com.herbal.herbalfax.customer.notification;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;

import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;
import java.util.Objects;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Interest> lst_int;
    ArrayList<Interest> lst_intFilter;
    Context mContext;
    public static final int LIKED = 0;
    public static final int FOLLOW = 1;
    public static final int COMMENTED = 2;
    private final NotificationData[] notification_list;

    public NotificationAdapter(NotificationData[] notification_list, Context applicationContext) {


        this.notification_list = notification_list;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == LIKED) {
            view = inflater.inflate(R.layout.notification_item_liked, parent, false);
            viewHolder = new ViewHolderLiked(view);
        } else if (viewType == COMMENTED) {
            view = inflater.inflate(R.layout.notification_item_commented, parent, false);
            viewHolder = new ViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.notification_item_follow, parent, false);
            viewHolder = new ViewHolderFollow(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == LIKED) {

            ViewHolderLiked viewHolderLiked = (ViewHolderLiked) holder;
           viewHolderLiked.tv_date.setText("52 second ago");

            String first = "Liked your ";
            String next = "<font color='#96C93D'>photo</font>";
            viewHolderLiked.tv_desc.setText(Html.fromHtml(first + next));
        /*    picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath())
                    .placeholder(R.drawable.profile)
                    .into(viewHolderLiked.profile_image_3);

            if (Objects.equals(timeToSet, timeToSetOld)) {
                viewHolderLiked.tv_date.setVisibility(View.GONE);
            } else {
                viewHolderLiked.tv_date.setVisibility(View.VISIBLE);

            }
            timeToSetOld = Objects.requireNonNull(text).toLowerCase();

            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();

            String sourceString = "<b>" + name + "</b> " + desc;
            viewHolderLiked.tv_name.setText(Html.fromHtml(sourceString));

            if (notification_list.get(position).getmPostInfo().get(0).getMedia().get(0).getMediaType().equals("Image")) {
                picasso.load(notification_list.get(position).getmPostInfo().get(0).getMedia().get(0).getMediaFullPath())
                        .fit()
                        .centerCrop()
                        .into(viewHolderLiked.iv_icon_3);
            } else if (notification_list.get(position).getmPostInfo().get(0).getMedia().get(0).getMediaType().equals("Video")) {
                picasso.load(notification_list.get(position).getmPostInfo().get(0).getMedia().get(0).getMediaThumbName())
                        .fit()
                        .centerCrop()
                        .into(viewHolderLiked.iv_icon_3);
            }

            if (notification_list.get(position).isSelected) {
                viewHolderLiked.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
            } else {
                viewHolderLiked.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));
            }

            viewHolderLiked.rl_notification.setOnLongClickListener(v -> {
                if (!notification_list.get(position).isSelected) {

                    for (int i = 0; i < notification_list.size(); i++) {
                        if (position != i) {
                            notification_list.get(i).setSelected(false);
                        } else {
                            notification_list.get(position).setSelected(true);
                            itemClick.onItemClicks(v, position, 11, notification_list.get(position).getNotificationId(), notification_list.get(position).getUserID());
                        }
                    }

                    notifyItemChanged(position);


                } else {
                    notification_list.get(position).setSelected(false);
                    notifyDataSetChanged();
                }
                return true;
            });*/
//// LIKED
        }
        else if (holder.getItemViewType() == COMMENTED) {

            ViewHolder viewHolderComments = (ViewHolder) holder;
            viewHolderComments.tv_date.setText("52 second ago");
            String first = "Commented on your ";
            String next = "<font color='#96C93D'>photo</font>";
            viewHolderComments.tv_desc.setText(Html.fromHtml(first + next));
        }
        else {
//            ViewHolder viewHolderComments = (ViewHolder) holder;
//            viewHolderComments.tv_date.setText("52 second ago");
            ViewHolderFollow viewHolderFollow = (ViewHolderFollow) holder;
            viewHolderFollow.tv_date.setText("1 hour ago");
        }
    }

    @Override
    public int getItemCount() {
        return  6;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgProfile;
        private final ImageView iv_icon_3;
        private final TextView tv_name;
        private TextView tv_desc;
        private final LinearLayout main_ll;

        private final TextView tv_date;

        public ViewHolder(View view) {
            super(view);
            imgProfile = itemView.findViewById(R.id.imgprofile);
            iv_icon_3 = itemView.findViewById(R.id.iv_icon_3);

            tv_name = itemView.findViewById(R.id.userNameTxt);
            tv_desc = itemView.findViewById(R.id.subHeadingTxt);
            main_ll = itemView.findViewById(R.id.main_ll);
            tv_date = itemView.findViewById(R.id.timeTxt);

        }
    }

    public class ViewHolderFollow extends RecyclerView.ViewHolder {

        private final ImageView imgProfile;
        private final ImageView iv_icon_3;
        private final TextView tv_name;
        private TextView tv_desc;
        private final LinearLayout main_ll;

        private final TextView tv_date;

        public ViewHolderFollow(View view) {
            super(view);
            imgProfile = itemView.findViewById(R.id.imgprofile);
            iv_icon_3 = itemView.findViewById(R.id.iv_icon_3);

            tv_name = itemView.findViewById(R.id.userNameTxt);
            tv_desc = itemView.findViewById(R.id.subHeadingTxt);
            main_ll = itemView.findViewById(R.id.main_ll);
            tv_date = itemView.findViewById(R.id.timeTxt);

        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (notification_list[position].getmPatternID()) {
            case "Like":
                return LIKED;
            case "Comment":
                return COMMENTED;
            case "Follow":
                return FOLLOW;
            default:
                return -1;

        }



    }

    public static class ViewHolderLiked extends RecyclerView.ViewHolder {
        private final ImageView imgProfile;
        private final ImageView iv_icon_3;
        private final TextView tv_name;
        private TextView tv_desc;
        private final LinearLayout main_ll;

        private final TextView tv_date;

        public ViewHolderLiked(View view) {
            super(view);
            imgProfile = itemView.findViewById(R.id.imgprofile);
            iv_icon_3 = itemView.findViewById(R.id.iv_icon_3);

            tv_name = itemView.findViewById(R.id.userNameTxt);
            tv_desc = itemView.findViewById(R.id.subHeadingTxt);
            main_ll = itemView.findViewById(R.id.main_ll);
            tv_date = itemView.findViewById(R.id.timeTxt);

        }
    }


}
