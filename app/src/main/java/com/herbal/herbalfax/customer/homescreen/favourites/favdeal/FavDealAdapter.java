package com.herbal.herbalfax.customer.homescreen.favourites.favdeal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.homescreen.deals.DealsDetailsActivity;
import com.herbal.herbalfax.customer.homescreen.favourites.favdeal.model.StoreProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class FavDealAdapter extends RecyclerView.Adapter<FavDealAdapter.ViewHolder> {

    ArrayList<StoreProduct> lst_deal;
    Context mContext;
    private final Picasso picasso;

    public FavDealAdapter(ArrayList<StoreProduct> lst_deal, Context applicationContext) {
        picasso = Picasso.get();
        this.lst_deal = lst_deal;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public FavDealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_deals_item, parent, false);
        mContext = parent.getContext();
        return new FavDealAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FavDealAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.productNameText.setText(lst_deal.get(position).getSPName());
        holder.priceTxt.setText("$" + lst_deal.get(position).getSPRate());
        picasso.load(String.valueOf(lst_deal.get(position).getSPPPath()))
                .into(holder.productImg);
        if(lst_deal.get(position).getSPExpiry() != null){
            holder.validity_txt.setText("Validity Till: " + lst_deal.get(position).getSPExpiry());
        }
        if (lst_deal.get(position).getSPLocation() == null) {
            holder.location.setText("No Address Found");
        }else {
            holder.location.setText(""+ lst_deal.get(position).getSPLocation());

        }

        String productId = lst_deal.get(position).getIdstoreProducts();

        holder.buyBtn.setOnClickListener(view -> {
            Intent i = new Intent(mContext, DealsDetailsActivity.class);
            i.putExtra("productId",productId);
            mContext.startActivity(i);
        });

        if (lst_deal.get(position).getIsFav().equals("1")) {
            holder.dealFav.setImageResource(R.drawable.like_heart);
        } else {
            holder.dealFav.setImageResource(R.drawable.like_heart_grey);
        }

        holder.dealFav.setOnClickListener(view -> {
            if (lst_deal.get(position).getIsFav().equals("1")) {
                lst_deal.get(position).setIsFav("0");
                holder.dealFav.setImageResource(R.drawable.like_heart_grey);
                notifyItemChanged(position);
                callAddToFavAPI(productId);
            } else if (lst_deal.get(position).getIsFav().equals("0")) {
                holder.dealFav.setImageResource(R.drawable.like_heart);
                lst_deal.get(position).setIsFav("1");
                notifyItemChanged(position);
                callAddToFavAPI(productId);
            }
            //  callAddToFavAPI(postId);
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
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {

                Log.e("Added to Fav", "Added to Fav");
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

    }
    @Override
    public int getItemCount() {
        return lst_deal.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView expiredTxt, productNameText, priceTxt, location, validity_txt;
        FrameLayout BuyTxt;
        Button buyBtn;
        ImageView productImg,dealFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dealFav = itemView.findViewById(R.id.dealFav);
            productImg = itemView.findViewById(R.id.productImg);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            validity_txt = itemView.findViewById(R.id.validity_txt);
            location = itemView.findViewById(R.id.location);
            productNameText = itemView.findViewById(R.id.productNameText);
            expiredTxt = itemView.findViewById(R.id.expiredTxt);
            BuyTxt = itemView.findViewById(R.id.buyTxt);
            buyBtn = itemView.findViewById(R.id.buyBtn);

        }
    }


}
