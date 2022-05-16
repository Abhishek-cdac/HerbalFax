package com.herbal.herbalfax.customer.homescreen.products.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.homescreen.homedashboard.DrawerAdapter;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.vendor.sellerproduct.productlistmodel.StoreProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class CustomerProductListAdapter extends RecyclerView.Adapter<CustomerProductListAdapter.ViewHolder> {

    ArrayList<StoreProduct> lst_product;
    Context mContext;
    private final Picasso picasso;
    Onclick itemClick;
    private CustomItemClickListener customClickListener;


    public CustomerProductListAdapter(ArrayList<StoreProduct> lst_product, Context applicationContext, Onclick itemClick) {
        picasso = Picasso.get();
        this.lst_product = lst_product;
        this.mContext = applicationContext;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public CustomerProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_productlist_item, parent, false);
        mContext = parent.getContext();
        return new CustomerProductListAdapter.ViewHolder(view);
    }

    @SuppressLint({"RecyclerView", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull CustomerProductListAdapter.ViewHolder holder, int position) {


        if (lst_product.get(position).getSPPPath() != null) {
            if (!lst_product.get(position).getSPPPath().equals("")) {
                picasso.load(String.valueOf(lst_product.get(position).getSPPPath()))
                        .into(holder.imgProfile);
            }
        }
        holder.productName.setText(lst_product.get(position).getSPName());
        holder.PriceTxt.setText("$"+lst_product.get(position).getSPRate());
        holder.categoryTxt.setText(lst_product.get(position).getSPCTitle());
        holder.descTxt.setText(lst_product.get(position).getSPDesc());
        String productId = lst_product.get(position).getIdstoreProducts();
        holder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClicks(view, position, 10, productId);
            }
        });

        if (lst_product.get(position).getIsFav().equals("1")) {
            holder.likeImg.setImageResource(R.drawable.heart_active);
        } else {
            holder.likeImg.setImageResource(R.drawable.like_heart_grey);
        }


        holder.likeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lst_product.get(position).getIsFav().equals("1")) {
                    lst_product.get(position).setIsFav("0");
                    holder.likeImg.setImageResource(R.drawable.like_heart_grey);
                    notifyItemChanged(position);
                    callAddToFavAPI(productId);
                } else if (lst_product.get(position).getIsFav().equals("0")) {
                    holder.likeImg.setImageResource(R.drawable.heart_active);
                    lst_product.get(position).setIsFav("1");
                    notifyItemChanged(position);
                    callAddToFavAPI(productId);
                }
                //  callAddToFavAPI(postId);
            }
        });

    }

    private void callAddToFavAPI(String productId) {

        SessionPref pref = SessionPref.getInstance(mContext);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("ProductId", productId);

        Call<CommonResponse> call = service.userStoreProductAddFav("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new retrofit2.Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                Log.e("Added to Fav", "Added to Fav");
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lst_product.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProfile, likeImg;
        TextView headingTxt, productName, categoryTxt, descTxt, PriceTxt;
        Button viewBtn;
        LinearLayout cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            likeImg = itemView.findViewById(R.id.likeImg);
            headingTxt = itemView.findViewById(R.id.headingTxt);
            imgProfile = itemView.findViewById(R.id.imgprofile);
            descTxt = itemView.findViewById(R.id.descTxt);
            categoryTxt = itemView.findViewById(R.id.categoryTxt);
            productName = itemView.findViewById(R.id.subHeadingTxt);
            PriceTxt = itemView.findViewById(R.id.PriceTxt);
            cardview = itemView.findViewById(R.id.cardview);
            viewBtn = itemView.findViewById(R.id.viewBtn);

        }
    }

    void setOnCardItemClickListener(CustomItemClickListener mItemClick) {
        this.customClickListener = mItemClick;
    }

    public interface CustomItemClickListener {
        /**
         * On card item click.
         *
         * @param view     the view
         * @param position the position
         */
        void viewItemClick(View view, int position);
    }


}