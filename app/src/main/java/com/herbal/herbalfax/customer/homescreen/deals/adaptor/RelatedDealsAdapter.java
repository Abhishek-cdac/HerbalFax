package com.herbal.herbalfax.customer.homescreen.deals.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class RelatedDealsAdapter extends RecyclerView.Adapter<RelatedDealsAdapter.ViewHolder> {

    ArrayList<Interest> lst_deal;
    Context mContext;
    private final Picasso picasso;

    public RelatedDealsAdapter(ArrayList<Interest> lst_deal, Context applicationContext) {
        picasso = Picasso.get();
        this.lst_deal = lst_deal;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public RelatedDealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_deals_item, parent, false);
        mContext = parent.getContext();
        return new RelatedDealsAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RelatedDealsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


     /*   holder.productNameText.setText(lst_deal.get(position).getSPName());
        holder.validity_txt.setText("Validity Till: " + lst_deal.get(position).getsP_Expiry());
        holder.priceTxt.setText("$" + lst_deal.get(position).getSPRate());
        holder.location.setText(lst_deal.get(position).getsP_Location());
        picasso.load(String.valueOf(lst_deal.get(position).getSPPPath()))
                .into(holder.productImg);

        if (lst_deal.get(position).getIsFav().equals("1")) {
            holder.dealFav.setImageResource(R.drawable.like_heart);
        } else {
            holder.dealFav.setImageResource(R.drawable.like_heart_grey);
        }

        String productId = lst_deal.get(position).getIdstoreProducts();

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

        });*/
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
        return 6;
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
