package com.herbal.herbalfax.vendor.sellerproduct;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.DeleteDialog;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.SpacesItemDecoration;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.vendor.sellerproduct.adapter.ProductListAdapter;
import com.herbal.herbalfax.vendor.sellerproduct.addproduct.AddProductActivity;
import com.herbal.herbalfax.vendor.sellerproduct.productdetail.ProductDetailsActivity;
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


public class SellerProductFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    RecyclerView productRecyclerView;
    private SellerProductViewModel sellerProductViewModel;
    Button addProduct;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    private Onclick itemClick;
    ProductListAdapter productListAdapter;
    private ArrayList<StoreProduct> lst_product;
    private CommonClass clsCommon;
    private Spinner storeSpinner, storeCategorySpinner;
    private List<ProductCategory> lst_store_category;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sellerProductViewModel =
                ViewModelProviders.of(this).get(SellerProductViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sellerproduct, container, false);
        clsCommon = CommonClass.getInstance();
        storeSpinner = root.findViewById(R.id.storeSpinner);
        storeCategorySpinner = root.findViewById(R.id.storeCategorySpinner);
        addProduct = root.findViewById(R.id.addProduct);

        itemClick = new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String productId, String s) {

            }

            @Override
            public void onItemClicks(View view, int position, int i, String productId) {
                if (i == 10) {
                    Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                    intent.putExtra("ProductId", productId);
                    startActivity(intent);
                } else if (i == 11) {
                    try {
                        DeleteDialog deleteDialog = new DeleteDialog(getActivity(), productId);
                        deleteDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        productRecyclerView = root.findViewById(R.id.productListrecyclerview);
        initAddProduct();
        callProductListApi();
        return root;
    }

    private void callProductListApi() {

        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("StoreId", "0");
        hashMap.put("limit", "10");
        hashMap.put("offset", "0");
        hashMap.put("category", "0");
        hashMap.put("active", "1");
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

                        lst_store_category =  response.body().getData().getProductCategories();

                        if (lst_store_category != null && lst_store_category.size() > 0) {
                            String[] storeCategory = new String[lst_store_category.size()];

                            for (int i = 0; i < lst_store_category.size(); i++) {
                                storeCategory[i] = lst_store_category.get(i).getSPCTitle();
                                storeCategorySpinner.setOnItemSelectedListener(SellerProductFragment.this);

                                Log.e("storeCategory frg", "" + storeCategory[i]);
                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),
                                        android.R.layout.simple_spinner_item,
                                        storeCategory);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                storeCategorySpinner.setAdapter(spinnerArrayAdapter);

                            }
                        }
                   /*     lst_store =  response.body().getData().getVendorStores();

                        if (lst_store != null && lst_store.size() > 0) {
                            String[] storeList = new String[lst_store.size()];

                            for (int i = 0; i < lst_store.size(); i++) {
                                storeCategory[i] = lst_store.get(i).getSPCTitle();
                                storeSpinner.setOnItemSelectedListener(SellerProductFragment.this);

                                Log.e("storeCategory frg", "" + storeCategory[i]);
                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),
                                        android.R.layout.simple_spinner_item,
                                        storeCategory);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                storeSpinner.setAdapter(spinnerArrayAdapter);

                            }
                        }*/

                        lst_product = (ArrayList<StoreProduct>) response.body().getData().getStoreProducts();
                        if (lst_product == null) {
                            lst_product = new ArrayList<>();
                        }

                        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
                        productRecyclerView.setLayoutManager(RecyclerViewLayoutManager);
                        productListAdapter = new ProductListAdapter(lst_product, getActivity(), itemClick);
                        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        productRecyclerView.setHasFixedSize(true);
                        productRecyclerView.addItemDecoration(new SpacesItemDecoration(2));
                        productRecyclerView.setAdapter(productListAdapter);
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


    private void initAddProduct() {
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddProductActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}