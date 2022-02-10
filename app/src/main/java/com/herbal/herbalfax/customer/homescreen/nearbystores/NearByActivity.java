package com.herbal.herbalfax.customer.homescreen.nearbystores;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.nearbystores.userstoremodel.Store;
import com.herbal.herbalfax.customer.homescreen.nearbystores.userstoremodel.UserStoreListResponse;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.customer.store.StoreDetailsActivity;
import com.herbal.herbalfax.util.CommonUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearByActivity extends AppCompatActivity {
    private NearByStoreViewModel nearByStoreViewModel;
    Button ListView, mapView;
    RelativeLayout mapLinear;
    CommonClass clsCommon;
    RecyclerView StoreListrecyclerview, StoreListHorizontalrecyclerview;
    LinearLayoutManager HorizontalLayout;
    LinearLayoutManager RecyclerViewLayoutManager;
    UserStoreListAdapter userStoreListAdapter;
    private ArrayList<Store> listStore=new ArrayList<>();
    Onclick itemClick;
    UserStoreHorizontalListAdapter userStoreHorizontalListAdapter;
    ImageView back;
    private int pastVisiblesItems=0;
    private int visibleItemCount=0;
    private int totalItemCount=0;
    private int limit=10;
    private int offset=0;
    private boolean isLoading= true;
    private String dateFormatted="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_near_by_store);
        clsCommon = CommonClass.getInstance();

        back = findViewById(R.id.back);
        StoreListrecyclerview = findViewById(R.id.StoreListrecyclerview);
        StoreListHorizontalrecyclerview = findViewById(R.id.StoreListHorizontalrecyclerview);
        ListView = findViewById(R.id.ListView);
        mapView = findViewById(R.id.mapView);
        mapLinear = findViewById(R.id.mapLinear);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        itemClick = new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String storeId, String s) {

            }

            @Override
            public void onItemClicks(View view, int position, int i, String storeId) {
                if (i == 6) {
                    Intent intent = new Intent(getApplicationContext(), StoreDetailsActivity.class);
                    intent.putExtra("storeId", storeId);
                    startActivity(intent);
                }
            }
        };
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        // SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        Log.e("formattedDate", "" + formattedDate);
        dateFormatted=formattedDate;
        callStoreList(dateFormatted);
//        callStoreListAPI(formattedDate);

        ListView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
            @Override
            public void onClick(View view) {
                mapLinear.setVisibility(View.GONE);
                mapView.setTextColor(getResources().getColor(R.color.black));
                ListView.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
                mapView.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                ListView.setTextColor(getResources().getColor(R.color.white));
                StoreListrecyclerview.setVisibility(View.VISIBLE);

                Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_baseline_list_24);
                img.setBounds(0, 0, 60, 60);
                ListView.setCompoundDrawables(img, null, null, null);

                Drawable img1 = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_menu_map_pin_black);
                img1.setBounds(0, 0, 60, 60);
                mapView.setCompoundDrawables(img1, null, null, null);

            }
        });

        mapView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
            @Override
            public void onClick(View view) {
                mapLinear.setVisibility(View.VISIBLE);

                mapView.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
                ListView.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                mapView.setTextColor(getResources().getColor(R.color.white));
                ListView.setTextColor(getResources().getColor(R.color.black));
                Drawable img3 = getApplicationContext().getResources().getDrawable(R.drawable.ic_baseline_list_24_black);
                img3.setBounds(0, 0, 60, 60);
                ListView.setCompoundDrawables(img3, null, null, null);

                Drawable img4 = getApplicationContext().getResources().getDrawable(R.drawable.ic_icon_menu_map_pin_white);
                img4.setBounds(0, 0, 60, 60);
                mapView.setCompoundDrawables(img4, null, null, null);
                StoreListrecyclerview.setVisibility(View.GONE);

            }
        });

    }


    private void callStoreList(String formattedDate)
    {
        if (CommonUtils.isInternetOn(NearByActivity.this)) {
            callStoreListAPI(formattedDate);
        }else{
            Toast.makeText(NearByActivity.this, getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show();
        }
    }


    private void callStoreListAPI(String formattedDate) {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(NearByActivity.this);
        pd.show();
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("fordate", formattedDate);
        hashMap.put("limit", limit+"");
        hashMap.put("offset", offset+"");

        Call<UserStoreListResponse> call = service.userStoreList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<UserStoreListResponse>() {
            @Override
            public void onResponse(Call<UserStoreListResponse> call, Response<UserStoreListResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        Log.e("NearByPreData..", "" + response.body().getData().getStores());
                        ArrayList<Store> lst_store = (ArrayList<Store>) response.body().getData().getStores();

                        /*vertical list for map view*/

                        if(offset==0)
                        {
                            listStore.clear();
                        }
                        listStore.addAll(lst_store);
                        if(userStoreListAdapter==null)
                        {
                            RecyclerViewLayoutManager = new LinearLayoutManager(NearByActivity.this,LinearLayoutManager.VERTICAL, false);
                            StoreListrecyclerview.setLayoutManager(RecyclerViewLayoutManager);
                            userStoreListAdapter = new UserStoreListAdapter(listStore, getApplicationContext(), itemClick);
                            HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//                        StoreListrecyclerview.setLayoutManager(HorizontalLayout);
                            StoreListrecyclerview.setAdapter(userStoreListAdapter);
                            isLoading=true;
                        }else{
                            userStoreListAdapter.notifyDataSetChanged();
                            isLoading=true;
                        }
                        StoreListrecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                if (dy > 0) {
                                    visibleItemCount = RecyclerViewLayoutManager.getChildCount();
                                    totalItemCount = RecyclerViewLayoutManager.getItemCount();
                                    pastVisiblesItems = RecyclerViewLayoutManager.findFirstVisibleItemPosition();
                                    if (isLoading) {
                                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                            if (!recyclerView.canScrollVertically(1)) {
                                                isLoading = false;
                                                offset = offset + 10;
                                                callStoreList(dateFormatted);

                                            }

                                        }
                                    }

                                }
                            }
                        });


                        /*horizontal list for map view*/
//                        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
//                        StoreListHorizontalrecyclerview.setLayoutManager(RecyclerViewLayoutManager);
                        userStoreHorizontalListAdapter = new UserStoreHorizontalListAdapter(lst_store, getApplicationContext(), itemClick);
                        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        StoreListHorizontalrecyclerview.setLayoutManager(HorizontalLayout);
                        StoreListHorizontalrecyclerview.setAdapter(userStoreHorizontalListAdapter);

                    } else {
                        clsCommon.showDialogMsg(NearByActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(NearByActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<UserStoreListResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}