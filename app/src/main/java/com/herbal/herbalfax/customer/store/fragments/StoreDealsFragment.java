package com.herbal.herbalfax.customer.store.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.store.adapter.StoreDealsAdapter;
import com.herbal.herbalfax.vendor.sellerproduct.productlistmodel.ProductListResponse;
import com.herbal.herbalfax.vendor.sellerproduct.productlistmodel.StoreProduct;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreDealsFragment extends Fragment {
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerView storeDealsRecycler;
    LinearLayoutManager HorizontalLayout;
    StoreDealsAdapter storeDealsAdapter;
    private CommonClass clsCommon;
    ArrayList<StoreProduct> lst_deals;
    String storeId;
TextView totalCount;
    public StoreDealsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_store_deals_fragment, container, false);
        storeDealsRecycler = root.findViewById(R.id.storeDealsRecycler);
        totalCount = root.findViewById(R.id.totalCount);
        Bundle arguments = getArguments();
        if (arguments != null) {
            storeId = arguments.get("storeId").toString();
            Log.e("StoreProductFragment", "" + storeId);
        }
        clsCommon = CommonClass.getInstance();
        callProductListApi();

        return root;
    }


    private void callProductListApi() {

        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("StoreId", storeId);
        hashMap.put("limit", "20");
        hashMap.put("offset", "0");
        hashMap.put("category", "0");
        hashMap.put("active", "1");
        hashMap.put("product_type", "2");
        hashMap.put("search_key", "0");

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Call<ProductListResponse> call = service.userStoreProductList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<ProductListResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        lst_deals = (ArrayList<StoreProduct>) response.body().getData().getStoreProducts();
                        if (lst_deals == null) {
                            lst_deals = new ArrayList<>();
                        }
                        totalCount.setText("Showing all " + Math.toIntExact(response.body().getData().getStoreProductCount()) + " deals");

                        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
                        storeDealsRecycler.setLayoutManager(RecyclerViewLayoutManager);
                        storeDealsAdapter = new StoreDealsAdapter(lst_deals, getActivity());
                        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        storeDealsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));

                        storeDealsRecycler.setAdapter(storeDealsAdapter);
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
            public void onFailure(@NotNull Call<ProductListResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}