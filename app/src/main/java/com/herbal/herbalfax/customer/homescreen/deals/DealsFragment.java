package com.herbal.herbalfax.customer.homescreen.deals;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.deals.adaptor.NewDealsAdapter;
import com.herbal.herbalfax.customer.homescreen.deals.adaptor.NewDealsCategoriesAdaptor;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.customer.store.adapter.StoreDealsAdapter;
import com.herbal.herbalfax.vendor.sellerproduct.productlistmodel.ProductCategory;
import com.herbal.herbalfax.vendor.sellerproduct.productlistmodel.ProductListResponse;
import com.herbal.herbalfax.vendor.sellerproduct.productlistmodel.StoreProduct;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DealsFragment extends Fragment implements View.OnClickListener {
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    private List<ProductCategory> DealsCategory = new ArrayList<>();
    private List<StoreProduct> lst_deals = new ArrayList<>();
    RecyclerView storeDealsRecycler;
    LinearLayoutManager HorizontalLayout;
    NewDealsAdapter newDealsAdapter;
    RecyclerView dealsheadertxtrecycler;
    NewDealsCategoriesAdaptor newDealsAdaptor;
    Button buyBtn;
    CommonClass clsCommon;
    private Onclick itemClick;
    private DealsViewModel dealsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dealsViewModel =
                ViewModelProviders.of(this).get(DealsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_deals, container, false);
        clsCommon = CommonClass.getInstance();
        storeDealsRecycler = root.findViewById(R.id.storeDealsRecycler);
        dealsheadertxtrecycler = root.findViewById(R.id.dealsheadertxtrecycler);
        /*        buyBtn =  (Button) root.findViewById(R.id.fbLogin);*/

        callProductDealsListApi("0");
        itemClick = new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String categoryId, String s) {

            }

            @Override
            public void onItemClicks(View view, int position, int i, String categoryId) {
                if (i == 15) {
                    callProductDealsListApi(categoryId);
                }
            }
        };

        return root;
    }

    private void callProductDealsListApi(String categoryId) {

        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("StoreId", "0");
        hashMap.put("limit", "20");
        hashMap.put("offset", "0");
        hashMap.put("category", categoryId);
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

                        DealsCategory = (ArrayList<ProductCategory>) response.body().getData().getProductCategories();
                        if (DealsCategory == null) {
                            DealsCategory = new ArrayList<>();
                        }

                        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
                        storeDealsRecycler.setLayoutManager(RecyclerViewLayoutManager);
                        newDealsAdapter = new NewDealsAdapter((ArrayList<StoreProduct>) lst_deals, getActivity());
                        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        storeDealsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        storeDealsRecycler.setAdapter(newDealsAdapter);

                        /*Category deals*/
                        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
                        dealsheadertxtrecycler.setLayoutManager(RecyclerViewLayoutManager);
                        newDealsAdaptor = new NewDealsCategoriesAdaptor((ArrayList<ProductCategory>) DealsCategory, getActivity(),itemClick);
                        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        dealsheadertxtrecycler.setLayoutManager(HorizontalLayout);
                        dealsheadertxtrecycler.setAdapter(newDealsAdaptor);
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



    @Override
    public void onClick(View view) {

    }
}