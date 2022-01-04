package com.herbal.herbalfax.customer.store.adapter;

import android.annotation.SuppressLint;
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
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.vendor.sellerproduct.productlistmodel.StoreProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class StoreProductsAdapter extends RecyclerView.Adapter<StoreProductsAdapter.ViewHolder> {

    ArrayList<StoreProduct> lst_product;
    Context mContext;
    private final Picasso picasso;

    public StoreProductsAdapter(ArrayList<StoreProduct> lst_product, Context applicationContext) {
        picasso = Picasso.get();
        this.lst_product = lst_product;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public StoreProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_product_item, parent, false);
        mContext = parent.getContext();
        return new StoreProductsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreProductsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        if (lst_product.get(position).getSPPPath() != null) {
            if (!lst_product.get(position).getSPPPath().equals("")) {
                picasso.load(String.valueOf(lst_product.get(position).getSPPPath()))
                        .into(holder.productImg);
            }
        }
        holder.productNameText.setText(lst_product.get(position).getSPName());
        holder.priceTxt.setText("$"+lst_product.get(position).getSPRate());
//        holder.categoryTxt.setText(lst_product.get(position).getSPCTitle());
//        holder.descTxt.setText(lst_product.get(position).getSPDesc());
        String productId = lst_product.get(position).getIdstoreProducts();


        if (lst_product.get(position).getIsFav().equals("1")) {
            holder.likeImg.setImageResource(R.drawable.like_heart);
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
                    holder.likeImg.setImageResource(R.drawable.like_heart);
                    lst_product.get(position).setIsFav("1");
                    notifyItemChanged(position);
                    callAddToFavAPI(productId);
                }
                //  callAddToFavAPI(postId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst_product.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameText, newProduct, priceTxt;
        ImageView productImg, likeImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            priceTxt = itemView.findViewById(R.id.priceTxt);
            newProduct = itemView.findViewById(R.id.newProduct);
            productImg = itemView.findViewById(R.id.productImg);
            productNameText = itemView.findViewById(R.id.productNameText);
            likeImg = itemView.findViewById(R.id.likeImg);

        }
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
}
