package com.herbal.herbalfax.customer.blogs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.blogs.blogdetailmodel.Blog;
import com.herbal.herbalfax.customer.blogs.blogdetailmodel.Para;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class BlogDetailsAdapter extends RecyclerView.Adapter<BlogDetailsAdapter.ViewHolder> {

    ArrayList<Para> lst_int;

    Context mContext;
    private final Picasso picasso;
Blog blog;
    public BlogDetailsAdapter(ArrayList<Para> lst_int, Context applicationContext, Blog blog) {
        picasso = Picasso.get();
        this.lst_int = lst_int;
        this.mContext = applicationContext;
        this.blog = blog;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_details_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.e("BlogDesc", "" + lst_int.get(position).getBParaDesc());
        Log.e("BlogTitle", "" + blog.getBlogTitle());

//        if (position == 0) {
//            holder.title.setVisibility(View.VISIBLE);
//            holder.title.setText(blog.getBlogTitle());
//            holder.desc.setText(blog.getBlogDesc());
//            picasso.load(blog.getBlogImage())
//                    .into(holder.BlogImg);
//
//        }
//       else if (position==1){
        try{


                    holder.desc.setText(lst_int.get(position).getBParaDesc());

                if ( !lst_int.get(position).getBParaImage().equals("")) {
                    picasso.load(lst_int.get(position).getBParaImage())
                            .into(holder.BlogImg);
                    holder.BlogImg.setVisibility(View.VISIBLE);
                } else {
                    holder.BlogImg.setVisibility(View.GONE);
                }
                holder.title.setVisibility(View.GONE);




        } catch (Exception e) {
            e.printStackTrace();
        }

        //  }



    }

    @Override
    public int getItemCount() {
        return lst_int.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView BlogImg;
        TextView title, desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            BlogImg = itemView.findViewById(R.id.BlogImg);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);


        }
    }

}
