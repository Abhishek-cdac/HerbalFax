package com.herbal.herbalfax.customer.homescreen.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.herbal.herbalfax.common_screen.utils.SpacesItemDecoration;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.products.adapter.CustomerProductListAdapter;
import com.herbal.herbalfax.customer.homescreen.products.toprated.TopRatedActivity;
import com.herbal.herbalfax.customer.interfaces.Onclick;
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


public class ProductsFragment extends Fragment {

    private ProductsViewModel productsViewModel;
    RecyclerView productRecyclerView;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    LinearLayout topLL;
    private Onclick itemClick;
    CustomerProductListAdapter customerProductListAdapter;
    ArrayList<StoreProduct> lst_product;
    private CommonClass clsCommon;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        productsViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        clsCommon = CommonClass.getInstance();

        productRecyclerView = root.findViewById(R.id.productListrecyclerview);
        topLL = root.findViewById(R.id.topLL);
        topLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TopRatedActivity.class);
                startActivity(intent);
            }
        });
        callProductListApi();

        itemClick = new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String productId, String s) {

            }

            @Override
            public void onItemClicks(View view, int position, int i, String productId) {
                if (i == 10) {
                    Intent intent = new Intent(getActivity(), CustomerProductDetailsActivity.class);
                  intent.putExtra("productId", productId);
                    startActivity(intent);


                }
            }
        };
        return root;
    }


    private void callProductListApi() {

        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("StoreId", "0");
        hashMap.put("limit", "20");
        hashMap.put("offset", "0");
        hashMap.put("category", "0");
        hashMap.put("active", "1");
        hashMap.put("product_type", "1");
        hashMap.put("search_key", "0");

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Call<ProductListResponse> call = service.userStoreProductList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        lst_product = (ArrayList<StoreProduct>) response.body().getData().getStoreProducts();
                        if (lst_product == null) {
                            lst_product = new ArrayList<>();
                        }

                        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
                        productRecyclerView.setLayoutManager(RecyclerViewLayoutManager);
                        customerProductListAdapter = new CustomerProductListAdapter(lst_product, getActivity(), itemClick);
                        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        productRecyclerView.setHasFixedSize(true);
                        productRecyclerView.addItemDecoration(new SpacesItemDecoration(2));
                        productRecyclerView.setAdapter(customerProductListAdapter);
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