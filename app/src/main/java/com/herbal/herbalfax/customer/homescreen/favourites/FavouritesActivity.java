package com.herbal.herbalfax.customer.homescreen.favourites;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.SpacesItemDecoration;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.blogs.BlogListAdapter;
import com.herbal.herbalfax.customer.blogs.blogmodel.Blog;
import com.herbal.herbalfax.customer.blogs.blogmodel.BlogResponse;
import com.herbal.herbalfax.customer.homescreen.askfax.AskFaxAdapter;
import com.herbal.herbalfax.customer.homescreen.askfax.aflist.askfaxlistmodel.AfQuestion;
import com.herbal.herbalfax.customer.homescreen.favourites.favdeal.FavDealAdapter;
import com.herbal.herbalfax.customer.homescreen.favourites.favdeal.model.FavDealResponse;
import com.herbal.herbalfax.customer.homescreen.favourites.favdeal.model.StoreProduct;
import com.herbal.herbalfax.customer.homescreen.favourites.favproduct.FavProductAdapter;
import com.herbal.herbalfax.customer.homescreen.favourites.favproduct.model.FavProductResponse;
import com.herbal.herbalfax.customer.homescreen.favourites.favquesmodel.FavAskFaxQueSResponse;
import com.herbal.herbalfax.customer.homescreen.favourites.favstore.FavStoreAdapter;
import com.herbal.herbalfax.customer.homescreen.favourites.favstore.model.FavStoreResponse;
import com.herbal.herbalfax.customer.homescreen.favourites.favstore.model.StoreAddToFav;
import com.herbal.herbalfax.customer.homescreen.feed.FeedAdapter;
import com.herbal.herbalfax.customer.homescreen.homedashboard.getallpostmodel.GetAllPostResponse;
import com.herbal.herbalfax.customer.homescreen.homedashboard.getallpostmodel.Post;
import com.herbal.herbalfax.customer.interfaces.Onclick;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouritesActivity extends AppCompatActivity {

    RecyclerView postRecyclerview,askFaxRecyclerview, blogRecyclerview, dealRecyclerview, productRecyclerview, storeRecyclerview;
    LinearLayoutManager HorizontalLayout;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    FeedAdapter feedAdapter;
    Onclick itemClick;
    CommonClass clsCommon;
    BlogListAdapter blogListAdapter;
    TextView postTxt, blogTxt, dealsTxt, storeTxt, productTxt, askFaxTxt;
    FavDealAdapter favDealAdapter;
    ImageView back;
    AskFaxAdapter askFaxAdapter;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        clsCommon = CommonClass.getInstance();

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        askFaxTxt = findViewById(R.id.askFaxTxt);
        postTxt = findViewById(R.id.postTxt);
        blogTxt = findViewById(R.id.blogTxt);
        dealsTxt = findViewById(R.id.dealsTxt);
        storeTxt = findViewById(R.id.storeTxt);
        productTxt = findViewById(R.id.productTxt);

        postRecyclerview = findViewById(R.id.postRecyclerview);
        blogRecyclerview = findViewById(R.id.BlogRecyclerview);
        dealRecyclerview = findViewById(R.id.dealRecyclerview);
        productRecyclerview = findViewById(R.id.storeRecyclerview);
        storeRecyclerview = findViewById(R.id.productRecyclerview);
        askFaxRecyclerview = findViewById(R.id.askFaxRecyclerview);

        callFavPostAPI();
        callGetFavDealsAPI();
        callGetFavBlogsAPI();
        callFavStoreAPI();
        callFavProductAPI();
        callFavAskFaxQuesAPI();

        askFaxTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askFaxTxt.setTextColor(getResources().getColor(R.color.white));
                postTxt.setTextColor(getResources().getColor(R.color.black));
                blogTxt.setTextColor(getResources().getColor(R.color.black));
                storeTxt.setTextColor(getResources().getColor(R.color.black));
                dealsTxt.setTextColor(getResources().getColor(R.color.black));
                productTxt.setTextColor(getResources().getColor(R.color.black));

                askFaxTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
                postTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                blogTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                storeTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                dealsTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                productTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));

                askFaxRecyclerview.setVisibility(View.VISIBLE);
                postRecyclerview.setVisibility(View.GONE);
                blogRecyclerview.setVisibility(View.GONE);
                dealRecyclerview.setVisibility(View.GONE);
                storeRecyclerview.setVisibility(View.GONE);
                productRecyclerview.setVisibility(View.GONE);

                Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_post_black);
                img.setBounds(0, 0, 60, 60);
                postTxt.setCompoundDrawables(img, null, null, null);

                Drawable img1 = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_blog_black);
                img1.setBounds(0, 0, 60, 60);
                blogTxt.setCompoundDrawables(img1, null, null, null);

                Drawable img2 = getApplicationContext().getResources().getDrawable(R.drawable.deal_black);
                img2.setBounds(0, 0, 60, 60);
                dealsTxt.setCompoundDrawables(img2, null, null, null);

                Drawable img3 = getApplicationContext().getResources().getDrawable(R.drawable.store_black);
                img3.setBounds(0, 0, 60, 60);
                storeTxt.setCompoundDrawables(img3, null, null, null);

                Drawable img4 = getApplicationContext().getResources().getDrawable(R.drawable.product_blacks);
                img4.setBounds(0, 0, 60, 60);
                productTxt.setCompoundDrawables(img4, null, null, null);

                Drawable img5 = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_post_white);
                img5.setBounds(0, 0, 60, 60);
                askFaxTxt.setCompoundDrawables(img5, null, null, null);

            }
        });
        postTxt.setOnClickListener(view -> {
            postTxt.setTextColor(getResources().getColor(R.color.white));
            blogTxt.setTextColor(getResources().getColor(R.color.black));
            storeTxt.setTextColor(getResources().getColor(R.color.black));
            dealsTxt.setTextColor(getResources().getColor(R.color.black));
            productTxt.setTextColor(getResources().getColor(R.color.black));
            askFaxTxt.setTextColor(getResources().getColor(R.color.black));

            postTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
            blogTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            storeTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            dealsTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            productTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            askFaxTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));

            postRecyclerview.setVisibility(View.VISIBLE);
            blogRecyclerview.setVisibility(View.GONE);
            dealRecyclerview.setVisibility(View.GONE);
            storeRecyclerview.setVisibility(View.GONE);
            productRecyclerview.setVisibility(View.GONE);
            askFaxRecyclerview.setVisibility(View.GONE);

            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_post_white);
            img.setBounds(0, 0, 60, 60);
            postTxt.setCompoundDrawables(img, null, null, null);

            Drawable img1 = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_blog_black);
            img1.setBounds(0, 0, 60, 60);
            blogTxt.setCompoundDrawables(img1, null, null, null);

            Drawable img2 = getApplicationContext().getResources().getDrawable(R.drawable.deal_black);
            img2.setBounds(0, 0, 60, 60);
            dealsTxt.setCompoundDrawables(img2, null, null, null);

            Drawable img3 = getApplicationContext().getResources().getDrawable(R.drawable.store_black);
            img3.setBounds(0, 0, 60, 60);
            storeTxt.setCompoundDrawables(img3, null, null, null);

            Drawable img4 = getApplicationContext().getResources().getDrawable(R.drawable.product_blacks);
            img4.setBounds(0, 0, 60, 60);
            productTxt.setCompoundDrawables(img4, null, null, null);

            Drawable img5 = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_post_black);
            img5.setBounds(0, 0, 60, 60);
            askFaxTxt.setCompoundDrawables(img5, null, null, null);

        });
        blogTxt.setOnClickListener(view -> {
            postRecyclerview.setVisibility(View.GONE);
            blogRecyclerview.setVisibility(View.VISIBLE);
            dealRecyclerview.setVisibility(View.GONE);
            storeRecyclerview.setVisibility(View.GONE);
            productRecyclerview.setVisibility(View.GONE);
            askFaxRecyclerview.setVisibility(View.GONE);

            blogTxt.setTextColor(getResources().getColor(R.color.white));
            postTxt.setTextColor(getResources().getColor(R.color.black));
            storeTxt.setTextColor(getResources().getColor(R.color.black));
            dealsTxt.setTextColor(getResources().getColor(R.color.black));
            productTxt.setTextColor(getResources().getColor(R.color.black));
            askFaxTxt.setTextColor(getResources().getColor(R.color.black));

            blogTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
            postTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            storeTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            dealsTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            productTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            askFaxTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));

            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_post_black);
            img.setBounds(0, 0, 60, 60);
            postTxt.setCompoundDrawables(img, null, null, null);

            Drawable img1 = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_blog_white);
            img1.setBounds(0, 0, 60, 60);
            blogTxt.setCompoundDrawables(img1, null, null, null);

            Drawable img2 = getApplicationContext().getResources().getDrawable(R.drawable.deal_black);
            img2.setBounds(0, 0, 60, 60);
            dealsTxt.setCompoundDrawables(img2, null, null, null);

            Drawable img3 = getApplicationContext().getResources().getDrawable(R.drawable.store_black);
            img3.setBounds(0, 0, 60, 60);
            storeTxt.setCompoundDrawables(img3, null, null, null);

            Drawable img4 = getApplicationContext().getResources().getDrawable(R.drawable.product_blacks);
            img4.setBounds(0, 0, 60, 60);
            productTxt.setCompoundDrawables(img4, null, null, null);

            Drawable img5 = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_post_black);
            img5.setBounds(0, 0, 60, 60);
            askFaxTxt.setCompoundDrawables(img5, null, null, null);

        });
        dealsTxt.setOnClickListener(view -> {

            postRecyclerview.setVisibility(View.GONE);
            blogRecyclerview.setVisibility(View.GONE);
            dealRecyclerview.setVisibility(View.VISIBLE);
            storeRecyclerview.setVisibility(View.GONE);
            productRecyclerview.setVisibility(View.GONE);
            askFaxRecyclerview.setVisibility(View.GONE);

            dealsTxt.setTextColor(getResources().getColor(R.color.white));
            postTxt.setTextColor(getResources().getColor(R.color.black));
            storeTxt.setTextColor(getResources().getColor(R.color.black));
            blogTxt.setTextColor(getResources().getColor(R.color.black));
            productTxt.setTextColor(getResources().getColor(R.color.black));
            askFaxTxt.setTextColor(getResources().getColor(R.color.black));

            dealsTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
            postTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            storeTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            blogTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            productTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            askFaxTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));

            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_post_black);
            img.setBounds(0, 0, 60, 60);
            postTxt.setCompoundDrawables(img, null, null, null);

            Drawable img2 = getApplicationContext().getResources().getDrawable(R.drawable.deal_white);
            img2.setBounds(0, 0, 60, 60);
            dealsTxt.setCompoundDrawables(img2, null, null, null);

            Drawable img1 = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_blog_black);
            img1.setBounds(0, 0, 60, 60);
            blogTxt.setCompoundDrawables(img1, null, null, null);

            Drawable img3 = getApplicationContext().getResources().getDrawable(R.drawable.store_black);
            img3.setBounds(0, 0, 60, 60);
            storeTxt.setCompoundDrawables(img3, null, null, null);

            Drawable img4 = getApplicationContext().getResources().getDrawable(R.drawable.product_blacks);
            img4.setBounds(0, 0, 60, 60);
            productTxt.setCompoundDrawables(img4, null, null, null);

            Drawable img5 = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_post_black);
            img5.setBounds(0, 0, 60, 60);
            askFaxTxt.setCompoundDrawables(img5, null, null, null);

        });
        storeTxt.setOnClickListener(view -> {
            storeTxt.setTextColor(getResources().getColor(R.color.white));
            postTxt.setTextColor(getResources().getColor(R.color.black));
            dealsTxt.setTextColor(getResources().getColor(R.color.black));
            blogTxt.setTextColor(getResources().getColor(R.color.black));
            productTxt.setTextColor(getResources().getColor(R.color.black));
            askFaxTxt.setTextColor(getResources().getColor(R.color.black));

            storeTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
            postTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            dealsTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            blogTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            productTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            askFaxTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));

            postRecyclerview.setVisibility(View.GONE);
            blogRecyclerview.setVisibility(View.GONE);
            dealRecyclerview.setVisibility(View.GONE);
            storeRecyclerview.setVisibility(View.VISIBLE);
            productRecyclerview.setVisibility(View.GONE);
            askFaxRecyclerview.setVisibility(View.GONE);


            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_post_black);
            img.setBounds(0, 0, 60, 60);
            postTxt.setCompoundDrawables(img, null, null, null);

            Drawable img1 = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_blog_black);
            img1.setBounds(0, 0, 60, 60);
            blogTxt.setCompoundDrawables(img1, null, null, null);

            Drawable img2 = getApplicationContext().getResources().getDrawable(R.drawable.deal_black);
            img2.setBounds(0, 0, 60, 60);
            dealsTxt.setCompoundDrawables(img2, null, null, null);

            Drawable img3 = getApplicationContext().getResources().getDrawable(R.drawable.store_white);
            img3.setBounds(0, 0, 60, 60);
            storeTxt.setCompoundDrawables(img3, null, null, null);

            Drawable img4 = getApplicationContext().getResources().getDrawable(R.drawable.product_blacks);
            img4.setBounds(0, 0, 60, 60);
            productTxt.setCompoundDrawables(img4, null, null, null);

            Drawable img5 = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_post_black);
            img5.setBounds(0, 0, 60, 60);
            askFaxTxt.setCompoundDrawables(img5, null, null, null);

        });
        productTxt.setOnClickListener(view -> {
            productTxt.setTextColor(getResources().getColor(R.color.white));
            postTxt.setTextColor(getResources().getColor(R.color.black));
            dealsTxt.setTextColor(getResources().getColor(R.color.black));
            blogTxt.setTextColor(getResources().getColor(R.color.black));
            storeTxt.setTextColor(getResources().getColor(R.color.black));
            askFaxTxt.setTextColor(getResources().getColor(R.color.black));

            productTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
            postTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            dealsTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            blogTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            storeTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            askFaxTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));

            postRecyclerview.setVisibility(View.GONE);
            blogRecyclerview.setVisibility(View.GONE);
            dealRecyclerview.setVisibility(View.GONE);
            storeRecyclerview.setVisibility(View.GONE);
            productRecyclerview.setVisibility(View.VISIBLE);
            askFaxRecyclerview.setVisibility(View.GONE);


            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_post_black);
            img.setBounds(0, 0, 60, 60);
            postTxt.setCompoundDrawables(img, null, null, null);

            Drawable img1 = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_blog_black);
            img1.setBounds(0, 0, 60, 60);
            blogTxt.setCompoundDrawables(img1, null, null, null);

            Drawable img2 = getApplicationContext().getResources().getDrawable(R.drawable.deal_black);
            img2.setBounds(0, 0, 60, 60);
            dealsTxt.setCompoundDrawables(img2, null, null, null);

            Drawable img3 = getApplicationContext().getResources().getDrawable(R.drawable.store_black);
            img3.setBounds(0, 0, 60, 60);
            storeTxt.setCompoundDrawables(img3, null, null, null);

            Drawable img4 = getApplicationContext().getResources().getDrawable(R.drawable.product_white);
            img4.setBounds(0, 0, 60, 60);
            productTxt.setCompoundDrawables(img4, null, null, null);

            Drawable img5 = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_post_black);
            img5.setBounds(0, 0, 60, 60);
            askFaxTxt.setCompoundDrawables(img5, null, null, null);

        });


    }

    private void callFavAskFaxQuesAPI() {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<FavAskFaxQueSResponse> call = service.userFavAskFaxQuestions("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<FavAskFaxQueSResponse>() {
            @Override
            public void onResponse(@NonNull Call<FavAskFaxQueSResponse> call, @NonNull Response<FavAskFaxQueSResponse> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        assert response.body() != null;
                        ArrayList<AfQuestion> lst_Ques = (ArrayList<AfQuestion>) response.body().getData().getAfQuestionFavs();
                        if (lst_Ques == null) {
                            lst_Ques = new ArrayList<>();
                        }

                        RecyclerViewLayoutManager = new LinearLayoutManager(FavouritesActivity.this);
                        askFaxRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
                        askFaxAdapter = new AskFaxAdapter(lst_Ques, FavouritesActivity.this);
                        HorizontalLayout = new LinearLayoutManager(FavouritesActivity.this, LinearLayoutManager.VERTICAL, false);
                        askFaxRecyclerview.setLayoutManager(HorizontalLayout);
                        askFaxRecyclerview.setAdapter(askFaxAdapter);


                    }  //   clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", response.body().getMessage(), "Ok");

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(FavouritesActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<FavAskFaxQueSResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void callFavPostAPI() {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<GetAllPostResponse> call = service.userFavPosts("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<GetAllPostResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetAllPostResponse> call, @NonNull Response<GetAllPostResponse> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        assert response.body() != null;
                        ArrayList<Post> lst_Post = (ArrayList<Post>) response.body().getData().getPosts();
                        if (lst_Post == null) {
                            lst_Post = new ArrayList<>();
                        }


                        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                        postRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
                        feedAdapter = new FeedAdapter(lst_Post, getApplicationContext(), itemClick);
                        HorizontalLayout = new LinearLayoutManager(FavouritesActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        postRecyclerview.setHasFixedSize(true);
                        postRecyclerview.addItemDecoration(new SpacesItemDecoration(2));
                        postRecyclerview.setAdapter(feedAdapter);


                    }  //   clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", response.body().getMessage(), "Ok");

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(FavouritesActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<GetAllPostResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callFavProductAPI() {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<FavProductResponse> call = service.userFavProducts("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<FavProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<FavProductResponse> call, @NonNull Response<FavProductResponse> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        assert response.body() != null;
                        ArrayList<com.herbal.herbalfax.customer.homescreen.favourites.favproduct.model.StoreProduct> lst_Product = (ArrayList<com.herbal.herbalfax.customer.homescreen.favourites.favproduct.model.StoreProduct>) response.body().getData().getStoreProduct();
                        if (lst_Product == null) {
                            lst_Product = new ArrayList<>();
                        }

                        FavProductAdapter favProductAdapter;
                        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                        productRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
                        favProductAdapter = new FavProductAdapter(lst_Product, getApplicationContext());
                        HorizontalLayout = new LinearLayoutManager(FavouritesActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        productRecyclerview.setHasFixedSize(true);
                        productRecyclerview.addItemDecoration(new SpacesItemDecoration(2));
                        productRecyclerview.setAdapter(favProductAdapter);


                    }  //   clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", response.body().getMessage(), "Ok");

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(FavouritesActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<FavProductResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callGetFavBlogsAPI() {

        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);


        Call<BlogResponse> call = service.userFavBlogs("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<BlogResponse>() {
            @Override
            public void onResponse(@NonNull Call<BlogResponse> call, @NonNull Response<BlogResponse> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        assert response.body() != null;

                        ArrayList<Blog> lst_blogsdesc = (ArrayList<Blog>) response.body().getData().getBlogs();
                        if (lst_blogsdesc == null) {
                            lst_blogsdesc = new ArrayList<>();
                        }


                        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                        blogRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
                        blogListAdapter = new BlogListAdapter(lst_blogsdesc, getApplicationContext(), itemClick);
                        HorizontalLayout = new LinearLayoutManager(FavouritesActivity.this, LinearLayoutManager.VERTICAL, false);
                        blogRecyclerview.setLayoutManager(HorizontalLayout);
                        blogRecyclerview.setAdapter(blogListAdapter);


                    }  //   clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", response.body().getMessage(), "Ok");

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(FavouritesActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<BlogResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void callGetFavDealsAPI() {

        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);


        Call<FavDealResponse> call = service.userFavDeals("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<FavDealResponse>() {
            @Override
            public void onResponse(@NonNull Call<FavDealResponse> call, @NonNull Response<FavDealResponse> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        assert response.body() != null;
                        ArrayList<StoreProduct> lst_deals = (ArrayList<StoreProduct>) response.body().getData().getStoreProduct();
                        if (lst_deals == null) {
                            lst_deals = new ArrayList<>();
                        }


                        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                        dealRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
                        favDealAdapter = new FavDealAdapter(lst_deals, getApplicationContext());
                        HorizontalLayout = new LinearLayoutManager(FavouritesActivity.this, LinearLayoutManager.VERTICAL, false);
                        dealRecyclerview.setHasFixedSize(true);
                        dealRecyclerview.addItemDecoration(new SpacesItemDecoration(2));
                        dealRecyclerview.setAdapter(favDealAdapter);

                    }  //   clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", response.body().getMessage(), "Ok");

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(FavouritesActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<FavDealResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void callFavStoreAPI() {
        SessionPref pref = SessionPref.getInstance(this);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("fordate", "");


        Call<FavStoreResponse> call = service.userFavStores("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<FavStoreResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<FavStoreResponse> call, @NonNull Response<FavStoreResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        List<StoreAddToFav> lst_store = response.body().getData().getStoreAddToFav();

                        FavStoreAdapter favStoreAdapter;
                        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                        storeRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
                        favStoreAdapter = new FavStoreAdapter(lst_store, getApplicationContext());
                        HorizontalLayout = new LinearLayoutManager(FavouritesActivity.this, LinearLayoutManager.VERTICAL, false);
                        storeRecyclerview.setHasFixedSize(true);
                        storeRecyclerview.addItemDecoration(new SpacesItemDecoration(2));
                        storeRecyclerview.setAdapter(favStoreAdapter);

                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<FavStoreResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}