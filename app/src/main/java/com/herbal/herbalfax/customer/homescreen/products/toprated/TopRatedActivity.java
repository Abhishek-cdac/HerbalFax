

package com.herbal.herbalfax.customer.homescreen.products.toprated;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.SpacesItemDecoration;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.products.toprated.beancmodel.TopVendorListResponse;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.util.CommonUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView topRatedRecyclerview;
    RecyclerView childItemRecyclerview;

    LinearLayoutManager RecyclerViewLayoutManager,ChildViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
     private ProgressBar progress_bar;
    private int limit=10;
    private int offset=0;
    private int pastVisiblesItems=0;
    private int visibleItemCount=0;
    private int totalItemCount=0;
    private ImageView profileImg;
    private TextView name;
    private TextView availStore;
    private LinearLayout parentView;
    private RatingBar ratingBar;
    private TopVendorListResponse.Vendor vendor;
    ArrayList<TopVendorListResponse.Vendor> lst_product;
    ArrayList<TopVendorListResponse.Vendor> localListProduct=new ArrayList<>();
    List<TopVendorListResponse.Store> storeListProduct=new ArrayList<TopVendorListResponse.Store>();
    private boolean isLoading= true;
    private Onclick itemClick;
    private TopRatedListAdapter topRatedListAdapter;
    private StoreListAdapter storeListAdapter;
    private CommonClass clsCommon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated);
        progress_bar=findViewById(R.id.progress_bar);
        profileImg=findViewById(R.id.profileImg);
        name=findViewById(R.id.name);
        availStore=findViewById(R.id.availStore);
        parentView=findViewById(R.id.parentView);
        parentView.setOnClickListener(this);
        ratingBar=findViewById(R.id.ratingBar);
        topRatedRecyclerview = findViewById(R.id.topRatedrecyclerview);
        childItemRecyclerview=findViewById(R.id.childItemRecyclerview);
        clsCommon = CommonClass.getInstance();
//        setUpRecylcerView();
        callProductAPI();

    }

    private void callProductAPI()
    {
        if (CommonUtils.isInternetOn(TopRatedActivity.this)) {
            callProductListApi();
        }else{
            Toast.makeText(TopRatedActivity.this, getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void callProductListApi() {

        SessionPref pref = SessionPref.getInstance(TopRatedActivity.this);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", limit+"");
        hashMap.put("offset", offset+"");


        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(TopRatedActivity.this);
//        pd.show();
        if(offset==0)
        {
            pd.show();
        }else{
            progress_bar.setVisibility(View.VISIBLE);
        }

        Call<TopVendorListResponse> call = service.userVendorTopList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<TopVendorListResponse>() {
            @Override
            public void onResponse(Call<TopVendorListResponse> call, Response<TopVendorListResponse> response) {
                pd.cancel();
                progress_bar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        lst_product = (ArrayList<TopVendorListResponse.Vendor>) response.body().getData().getVendors();
                        if (lst_product == null) {
                            lst_product = new ArrayList<>();
                        }
                        if(offset==0)
                        {
                            localListProduct.clear();
                        }
                        localListProduct.addAll(lst_product);
                        if(topRatedListAdapter==null) {
                            RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                            topRatedRecyclerview.setLayoutManager(RecyclerViewLayoutManager);

                            topRatedListAdapter = new TopRatedListAdapter(localListProduct, getApplicationContext(), itemClick);
                            HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            topRatedRecyclerview.setHasFixedSize(true);
                            topRatedRecyclerview.addItemDecoration(new SpacesItemDecoration(2));
                            topRatedRecyclerview.setAdapter(topRatedListAdapter);

                        }else{
                            topRatedListAdapter.notifyDataSetChanged();
                            isLoading=true;
                        }

                        topRatedListAdapter.setOnCardItemClickListener((view, position) -> {
                            vendor= localListProduct.get(position);
                            storeListProduct=vendor.getStores();
                            visibleParentView();

                        });

                        topRatedRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                                                callProductAPI();

                                            }

                                        }
                                    }

                                }
                            }
                        });

                    } else {
                        clsCommon.showDialogMsgFrag(TopRatedActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(TopRatedActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(TopRatedActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<TopVendorListResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(TopRatedActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }


        });
    }

    private void visibleParentView()
    {
        parentView.setVisibility(View.VISIBLE);
        name.setText(vendor.getUFullName());
        availStore.setText(vendor.getUActive()+" "+getString(R.string.store_available));
        Glide.with(TopRatedActivity.this).load(vendor.getUProPic())
                .dontAnimate().into(profileImg);
        String rating=vendor.getURatings();
        Float value= Float.parseFloat(rating);
        ratingBar.setRating(value);
        setStoreAdapter();
    }

    private void setStoreAdapter()
    {

        if(storeListAdapter==null)
        {
            ChildViewLayoutManager = new LinearLayoutManager(getApplicationContext());
            childItemRecyclerview.setLayoutManager(ChildViewLayoutManager);
            storeListAdapter = new StoreListAdapter(storeListProduct, getApplicationContext(), itemClick);
            HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            childItemRecyclerview.setHasFixedSize(true);
            childItemRecyclerview.addItemDecoration(new SpacesItemDecoration(2));
            childItemRecyclerview.setAdapter(storeListAdapter);
        }else{
            storeListAdapter.notifyDataSetChanged();
        }

    }

    private void setUpRecylcerView() {

        ChildViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        topRatedRecyclerview.setLayoutManager(RecyclerViewLayoutManager);

        topRatedListAdapter = new TopRatedListAdapter(lst_product, getApplicationContext(), itemClick);
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        topRatedRecyclerview.setHasFixedSize(true);
        topRatedRecyclerview.addItemDecoration(new SpacesItemDecoration(2));
        topRatedRecyclerview.setAdapter(topRatedListAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.parentView)
        {
            parentView.setOnClickListener(v1 -> {
                parentView.setVisibility(View.GONE);
            });
        }
    }
}