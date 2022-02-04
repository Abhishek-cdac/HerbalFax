package com.herbal.herbalfax.vendor.sellerdeals.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.herbal.herbalfax.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

/**
 * Author : Codiant- A Yash Technologies Company.
 * Date   : March 2019
 * Description : This class is used for show and add images list.
 */
public class AddImagesAdapter extends RecyclerView.Adapter<AddImagesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    private ItemInterFace itemClick;
    private AddImagesAdapter addImagesAdapter;
    /**
     * The Images count.
     */
    public boolean imagesCount = true;

    /**
     * Instantiates a  Add images adapter.
     *
     * @param context      the context
     * @param bitmapArrayList the uri array list
     */
    public AddImagesAdapter(Context context, ArrayList<Bitmap> bitmapArrayList) {
        this.context = context;
        this.bitmapArrayList = bitmapArrayList;
    }

    /**
     * This method is used to bind layout
     */
    @NonNull
    @Override
    public AddImagesAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.row_add_image, null);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /**
     * This method is used to set a value of variable
     */
    @Override
    public void onBindViewHolder( AddImagesAdapter.ViewHolder viewHolder, int i) {

        Bitmap uri = bitmapArrayList.get(i);
        viewHolder.imageViewCloud.setScaleType(ImageView.ScaleType.FIT_XY);
        viewHolder.imageViewCloud.setImageBitmap(uri);
        viewHolder.imageViewCloud.setBackground(null);
        viewHolder.crossIV.setVisibility(View.VISIBLE);


    }

    @Override
    public int getItemCount() {
        return bitmapArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * The Image view cloud.
         */
        ImageView imageViewCloud;
        /**
         * The Cross iv.
         */
        ImageView crossIV;
        /**
         * The Frame layout.
         */
        FrameLayout frameLayout;

        /**
         * Instantiates a  View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder( View itemView) {
            super(itemView);
            imageViewCloud = itemView.findViewById(R.id.imageViewCloud);
            crossIV = itemView.findViewById(R.id.crossIV);
            frameLayout = itemView.findViewById(R.id.frameLayout);
            crossIV.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClick != null) {
                itemClick.onItemClick(v, getAdapterPosition());
            }

        }
    }

    /**
     * On item click method.
     *
     * @param itemClick the item click
     */
    public void onItemClickMethod(ItemInterFace itemClick) {
        this.itemClick = itemClick;

    }

    /**
     * The interface Item inter face.
     */
    public interface ItemInterFace {
        /**
         * On item click.
         *
         * @param view     the view
         * @param position the position
         */
        void onItemClick(View view, int position);
    }


}
