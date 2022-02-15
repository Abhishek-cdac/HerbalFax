package com.herbal.herbalfax.customer.store.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.herbal.herbalfax.util.CommonUtils;
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
    LinearLayoutManager RecyclerViewLayoutManager;
    RecyclerView storeDealsRecycler;
    LinearLayoutManager HorizontalLayout;
    StoreDealsAdapter storeDealsAdapter;
    private ProgressBar progress_bar;
    private CommonClass clsCommon;
    ArrayList<StoreProduct> lst_deals;
    ArrayList<StoreProduct> localDeals=new ArrayList<>();
    String storeId;
    TextView totalCount;
    private int pastVisiblesItems=0;
    private int visibleItemCount=0;
    private int totalItemCount=0;
    private int limit=10;
    private int offset=0;
    private boolean isLoading= true;
    public StoreDealsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_store_deals_fragment, container, false);
        storeDealsRecycler = root.findViewById(R.id.storeDealsRecycler);
        progress_bar=root.findViewById(R.id.progress_bar);
        totalCount = root.findViewById(R.id.totalCount);
        Bundle arguments = getArguments();
        if (arguments != null) {
            storeId = arguments.get("storeId").toString();
            Log.e("StoreProductFragment", "" + storeId);
        }
        clsCommon = CommonClass.getInstance();
//        callProductListApi();
        callProductApi();

        return root;
    }

    private void callProductApi()
    {
        if (CommonUtils.isInternetOn(getContext())) {
            callProductListApi();
        }else{
            Toast.makeText(getContext(), getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show();
        }
    }


    private void callProductListApi() {

        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("StoreId", storeId);
        hashMap.put("limit", limit+"");
        hashMap.put("offset", offset+"");
        hashMap.put("category", "0");
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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                pd.cancel();
                progress_bar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        lst_deals = (ArrayList<StoreProduct>) response.body().getData().getStoreProducts();
                        if (lst_deals == null) {
                            lst_deals = new ArrayList<>();
                        }

                        if(offset==0)
                        {
                            localDeals.clear();
                        }
                        localDeals.addAll(lst_deals);
                        totalCount.setText("Showing all " + Math.toIntExact(response.body().getData().getStoreProductCount()) + " deals");

                        if(storeDealsAdapter==null)
                        {
                            RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
                            storeDealsRecycler.setLayoutManager(RecyclerViewLayoutManager);
                            storeDealsAdapter = new StoreDealsAdapter(localDeals, getActivity());
                            HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                            storeDealsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));

                            storeDealsRecycler.setAdapter(storeDealsAdapter);
                        }else{
                            storeDealsAdapter.notifyDataSetChanged();
                        }
                        storeDealsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
//                                                callStoreListAPI();
                                                callProductApi();

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
            public void onFailure(@NotNull Call<ProductListResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}