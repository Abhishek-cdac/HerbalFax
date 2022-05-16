package com.herbal.herbalfax.customer.homescreen.nearbystores;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NearByStoreFragment extends Fragment {

    private NearByStoreViewModel nearByStoreViewModel;
    Button ListView, mapView;
    RelativeLayout mapLinear;
    CommonClass clsCommon;
    RecyclerView StoreListrecyclerview, StoreListHorizontalrecyclerview;
    LinearLayoutManager HorizontalLayout;
    ArrayList<Store> listStore=new ArrayList<>();
    private int pastVisiblesItems=0;
    private int visibleItemCount=0;
    private int totalItemCount=0;
    private int limit=10;
    private int offset=0;
    private boolean isLoading= true;
    LinearLayoutManager RecyclerViewLayoutManager;
    UserStoreListAdapter userStoreListAdapter;
    Onclick itemClick;
    UserStoreHorizontalListAdapter userStoreHorizontalListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        nearByStoreViewModel =
                ViewModelProviders.of(this).get(NearByStoreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_near_by_store, container, false);
        clsCommon = CommonClass.getInstance();

        StoreListrecyclerview = root.findViewById(R.id.StoreListrecyclerview);
        StoreListHorizontalrecyclerview = root.findViewById(R.id.StoreListHorizontalrecyclerview);
        ListView = root.findViewById(R.id.ListView);
        mapView = root.findViewById(R.id.mapView);
        mapLinear = root.findViewById(R.id.mapLinear);

        itemClick = new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String storeId, String s) {

            }

            @Override
            public void onItemClicks(View view, int position, int i, String storeId) {
                if (i == 6) {
                    Intent intent = new Intent(getActivity(), StoreDetailsActivity.class);
                    intent.putExtra("storeId", storeId);
                    startActivity(intent);
                }
            }
        };

        callStoreAPI();

//        callStoreListAPI();

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

                Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_list_24);
                img.setBounds(0, 0, 60, 60);
                ListView.setCompoundDrawables(img, null, null, null);

                Drawable img1 = getContext().getResources().getDrawable(R.drawable.ic_icon_menu_map_pin_black);
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
                Drawable img3 = getContext().getResources().getDrawable(R.drawable.ic_baseline_list_24_black);
                img3.setBounds(0, 0, 60, 60);
                ListView.setCompoundDrawables(img3, null, null, null);

                Drawable img4 = getContext().getResources().getDrawable(R.drawable.ic_icon_menu_map_pin_white);
                img4.setBounds(0, 0, 60, 60);
                mapView.setCompoundDrawables(img4, null, null, null);
                StoreListrecyclerview.setVisibility(View.GONE);

            }
        });
        return root;
    }

    private void callStoreAPI()
    {
        if (CommonUtils.isInternetOn(getContext())) {
            callStoreListAPI();
        }else{
            Toast.makeText(getActivity(), getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show();
        }
    }


    private void callStoreListAPI() {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("fordate", "");
        hashMap.put("limit", limit+"");
        hashMap.put("offset", offset+"");

        Call<UserStoreListResponse> call = service.userStoreList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken),hashMap);
        call.enqueue(new Callback<UserStoreListResponse>() {
            @Override
            public void onResponse(Call<UserStoreListResponse> call, Response<UserStoreListResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        Log.e("NearByPreData..", "" + response.body().getData().getStores());
                        ArrayList<Store> localStore = (ArrayList<Store>) response.body().getData().getStores();

                        if(limit==0)
                        {
                            listStore.clear();
                        }
                        listStore.addAll(localStore);
                        isLoading=true;

                        /*vertical list for map view*/
                        if(userStoreListAdapter==null)
                        {
                            RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
                            StoreListrecyclerview.setLayoutManager(RecyclerViewLayoutManager);
                            userStoreListAdapter = new UserStoreListAdapter(listStore, getActivity(), itemClick);
                            HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            StoreListrecyclerview.setLayoutManager(HorizontalLayout);
                            StoreListrecyclerview.setAdapter(userStoreListAdapter);
                        }else{
                            userStoreListAdapter.notifyDataSetChanged();
                        }


                        /*horizontal list for map view*/
                        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
                        StoreListHorizontalrecyclerview.setLayoutManager(RecyclerViewLayoutManager);
                        userStoreHorizontalListAdapter = new UserStoreHorizontalListAdapter(listStore, getActivity(), itemClick);
                        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        StoreListHorizontalrecyclerview.setLayoutManager(HorizontalLayout);
                        StoreListHorizontalrecyclerview.setAdapter(userStoreHorizontalListAdapter);


                        StoreListrecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                if (dy > 0) {
                                    visibleItemCount = HorizontalLayout.getChildCount();
                                    totalItemCount = HorizontalLayout.getItemCount();
                                    pastVisiblesItems = HorizontalLayout.findFirstVisibleItemPosition();
                                    if (isLoading) {
                                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                            if (!recyclerView.canScrollVertically(1)) {
                                                isLoading = false;
                                                offset = offset + 10;
//                                                callStoreListAPI();
                                                callStoreAPI();

                                            }

                                        }
                                    }

                                }
                            }
                        });

                    } else {
                        clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<UserStoreListResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}