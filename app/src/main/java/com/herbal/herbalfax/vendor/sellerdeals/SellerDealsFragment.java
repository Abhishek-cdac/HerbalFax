package com.herbal.herbalfax.vendor.sellerdeals;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.util.CommonUtils;
import com.herbal.herbalfax.vendor.sellerdeals.adapter.SellerProductDealsAdapter;
import com.herbal.herbalfax.vendor.sellerdeals.adddeal.AddDealsActivity;
import com.herbal.herbalfax.vendor.sellerproduct.productcategorymodel.ProductCategory;
import com.herbal.herbalfax.vendor.sellerproduct.productcategorymodel.ProductCategoryResponse;
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


public class SellerDealsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    LinearLayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    private ProgressBar progress_bar;
    Button addDeals;
    RecyclerView dealsRecyclerView;
    Onclick itemClick;
    ArrayList<StoreProduct> lst_productDeals;
    ArrayList<StoreProduct> localProductDeals=new ArrayList<>();
    CommonClass clsCommon;
    Spinner storeCategorySpinner;
    private ArrayList<ProductCategory> lst_store_category;
    SellerProductDealsAdapter sellerProductDealsAdapter;
    private String IdStoreCategories="0";
    EditText searchEt;
    private int pastVisiblesItems=0;
    private int visibleItemCount=0;
    private int totalItemCount=0;
    private int limit=10;
    private int offset=0;
    private boolean isLoading= true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SellerDealsViewModel sellerDealsViewModel = ViewModelProviders.of(this).get(SellerDealsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sellerdeals, container, false);
        clsCommon = CommonClass.getInstance();
        addDeals = root.findViewById(R.id.addDeals);
        progress_bar=root.findViewById(R.id.progress_bar);
        storeCategorySpinner = root.findViewById(R.id.storeCategorySpinner);
        searchEt = root.findViewById(R.id.searchEt);
        addDeals.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AddDealsActivity.class);
            startActivity(intent);
        });
        dealsRecyclerView = root.findViewById(R.id.productDealsListRecyclerview);
        callStorePreDataAPI();
//        callProductDealsListApi("0");

        return root;
    }

    private void callStorePreDataAPI() {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ProductCategoryResponse> call = service.userStoreProductCategoryList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<ProductCategoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductCategoryResponse> call, @NonNull Response<ProductCategoryResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        try {
                            ProductCategory productCategory=new ProductCategory();
                            productCategory.setIdstoreProductCategories("0");
                            productCategory.setSPCTitle("Select Category");
                            lst_store_category = (ArrayList<ProductCategory>) response.body().getData().getProductCategories();
                            lst_store_category.add(0,productCategory);

                            if (lst_store_category != null && lst_store_category.size() > 0) {
                                String[] storeCategory = new String[lst_store_category.size()];

                                for (int i = 0; i < lst_store_category.size(); i++) {
                                    storeCategory[i] = lst_store_category.get(i).getSPCTitle();
                                    storeCategorySpinner.setOnItemSelectedListener(SellerDealsFragment.this);
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(),
                                            android.R.layout.simple_spinner_item,
                                            storeCategory);
                                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                    storeCategorySpinner.setAdapter(spinnerArrayAdapter);
                                }
                            }
                            assert lst_store_category != null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
            public void onFailure(@NotNull Call<ProductCategoryResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callProductDealsAPI(String CategoryId)
    {
        if (CommonUtils.isInternetOn(getContext())) {
            callProductDealsListApi(CategoryId);
        }else{
            Toast.makeText(getActivity(), getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void callProductDealsListApi(String CategoryId) {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("StoreId", "0");
        hashMap.put("limit", limit+"");
        hashMap.put("offset", offset+"");
        hashMap.put("category", CategoryId);
        hashMap.put("active", "1");
        hashMap.put("product_type", "2");
        hashMap.put("search_key", "0");

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
//        pd.show();
        if(offset==0)
        {
            pd.show();
        }else{
            progress_bar.setVisibility(View.VISIBLE);
        }
        Call<ProductListResponse> call = service.userStoreProductList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<ProductListResponse>() {
            @Override

            public void onResponse(@NonNull Call<ProductListResponse> call, @NonNull Response<ProductListResponse> response) {
                pd.cancel();
                progress_bar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        lst_productDeals = (ArrayList<StoreProduct>) response.body().getData().getStoreProducts();
                        if (lst_productDeals == null) {
                            lst_productDeals = new ArrayList<>();
                        }
                        if(offset==0)
                        {
                            localProductDeals.clear();
                        }
                        localProductDeals.addAll(lst_productDeals);

                        if(sellerProductDealsAdapter==null)
                        {
                            RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
                            dealsRecyclerView.setLayoutManager(RecyclerViewLayoutManager);
                            sellerProductDealsAdapter = new SellerProductDealsAdapter(localProductDeals, getActivity(), itemClick);
//                            HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                            dealsRecyclerView.setHasFixedSize(true);
                            dealsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                            dealsRecyclerView.setAdapter(sellerProductDealsAdapter);
                            isLoading=true;
                        }else{
                            sellerProductDealsAdapter.notifyDataSetChanged();
                            isLoading=true;
                        }

                        dealsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                                                callProductDealsAPI(IdStoreCategories);

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
                        pd.cancel();
                        progress_bar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ProductListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                progress_bar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            IdStoreCategories = lst_store_category.get(i).getIdstoreProductCategories();
            if(IdStoreCategories==null)
            {
                IdStoreCategories="0";
            }
            offset=0;
            callProductDealsAPI(IdStoreCategories);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onResume() {
        super.onResume();
//        callProductDealsAPI(IdStoreCategories);
//        callProductDealsListApi("0");
    }

}