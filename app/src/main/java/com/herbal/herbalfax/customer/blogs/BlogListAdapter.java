package com.herbal.herbalfax.customer.blogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.blogs.blogmodel.Blog;
import com.herbal.herbalfax.customer.bottomsheet.BlogsBottomSheet;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BlogListAdapter extends RecyclerView.Adapter<BlogListAdapter.ViewHolder> {

    ArrayList<Blog> lst_int;

    Context mContext;
    private final Picasso picasso;
    private final Onclick itemClick;

    public BlogListAdapter(ArrayList<Blog> lst_int, Context applicationContext, Onclick itemClick) {
        picasso = Picasso.get();
        this.lst_int = lst_int;
        this.mContext = applicationContext;
        this.itemClick = itemClick;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newslist_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String blogId = lst_int.get(position).getIdblogs();
        holder.readMoreCardview.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, BlogDetailsActivity.class);
            intent.putExtra("blog_id", blogId);
            mContext.startActivity(intent);

        });
        holder.headingTxt.setText(lst_int.get(position).getBlogTitle());
        holder.subHeadingTxt.setText(lst_int.get(position).getBlogDesc());
      try
      {
          if (lst_int.get(position).getBlogImage()!= null){
              picasso.load(lst_int.get(position).getBlogImage())
                      .into(holder.imgProfile);
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
        if (lst_int.get(position).getIsReport().equals("1")) {
            holder.reportImg.setImageResource(R.drawable.ic_flag_green);
        } else {
            holder.reportImg.setImageResource(R.drawable.ic_icon_report_flag);
        }


        holder.reportLl.setOnClickListener(view -> {
            if (lst_int.get(position).getIsReport().equals("1")) {
                lst_int.get(position).setIsReport("0");
                holder.reportImg.setImageResource(R.drawable.ic_icon_report_flag);
                notifyItemChanged(position);
                itemClick.onItemClicks(view, position, 1, blogId, "0");
                //  callAddToFavAPI(postId);
            } else if (lst_int.get(position).getIsReport().equals("0")) {
                holder.reportImg.setImageResource(R.drawable.ic_flag_green); //ic_icon_report_flag
                lst_int.get(position).setIsReport("1");
                notifyItemChanged(position);
                itemClick.onItemClicks(view, position, 1, blogId, "1");

                //callAddToFavAPI(postId);
            }

            //  itemClick.onItemClicks(view, position, 1, blogId, "1");
        });
        if (lst_int.get(position).getIsFav().equals("1")) {
            holder.likeHeart.setImageResource(R.drawable.ic_icon_heart);
        } else {
            holder.likeHeart.setImageResource(R.drawable.ic_icon_heart_border);
        }

        holder.likeLl.setOnClickListener(view -> {
            if (lst_int.get(position).getIsFav().equals("1")) {
                lst_int.get(position).setIsFav("0");
                holder.likeHeart.setImageResource(R.drawable.ic_icon_heart_border);
                notifyItemChanged(position);
                itemClick.onItemClicks(view, position, 2, blogId );
                //  callAddToFavAPI(postId);
            } else if (lst_int.get(position).getIsFav().equals("0")) {
                holder.likeHeart.setImageResource(R.drawable.ic_icon_heart);
                lst_int.get(position).setIsFav("1");
                notifyItemChanged(position);
                itemClick.onItemClicks(view, position, 2, blogId );

            }


        });



    }

    @Override
    public int getItemCount() {
        return lst_int.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView headingTxt, subHeadingTxt, timeTxt, personTxt;
        ImageView imgProfile, reportImg, likeHeart;
        CardView readMoreCardview;
        LinearLayout likeLl, reportLl, ll_newsrecylcer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            likeHeart = itemView.findViewById(R.id.likeheart);
            reportImg = itemView.findViewById(R.id.reportImg);
            imgProfile = itemView.findViewById(R.id.imgprofile);
            readMoreCardview = itemView.findViewById(R.id.readMoreCardview);
            headingTxt = itemView.findViewById(R.id.HeadingTxt);

            likeLl = itemView.findViewById(R.id.likeLl);
            reportLl = itemView.findViewById(R.id.reportLl);
            subHeadingTxt = itemView.findViewById(R.id.subHeadingTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            personTxt = itemView.findViewById(R.id.personTxt);
            ll_newsrecylcer = itemView.findViewById(R.id.ll_newsrecylcer);
            ll_newsrecylcer.setOnClickListener(v -> showBottomSheet(getAdapterPosition()));

        }
    }

    private void showBottomSheet(int adapterPosition) {
        // boolean notification = lst.get(adapterPosition).getNotifyStatus().equals("On");

        FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
        BlogsBottomSheet sheet = new BlogsBottomSheet(this, adapterPosition);
        sheet.show(fragmentManager, "comment bottom sheet");
    }
}
