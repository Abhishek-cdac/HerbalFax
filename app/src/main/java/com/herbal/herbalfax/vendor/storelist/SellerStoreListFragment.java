package com.herbal.herbalfax.vendor.storelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.herbal.herbalfax.common_screen.utils.SpacesItemDecoration;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.askfax.AskFaxViewModel;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.vendor.store.AddStoreActivity;
import com.herbal.herbalfax.vendor.storedetail.SellerStoreDetailActivity;
import com.herbal.herbalfax.vendor.storelist.storelistmodel.Store;
import com.herbal.herbalfax.vendor.storelist.storelistmodel.StoreListResponse;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SellerStoreListFragment extends Fragment {

    private AskFaxViewModel askFaxViewModel;
    RecyclerView recyclerView, storeRecyclerView;
    private MyStoreViewModel myStoreViewModel;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    MyStoreListAdapter myStoreListAdapter;
    private ArrayList<Store> lst_store;
    LinearLayoutManager HorizontalLayout;
    private CommonClass clsCommon;
    private Onclick itemClick;

    Button fab;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myStoreViewModel =
                ViewModelProviders.of(this).get(MyStoreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sellermystore, container, false);
        clsCommon = CommonClass.getInstance();

        storeRecyclerView = root.findViewById(R.id.storeListrecyclerview);
        fab = root.findViewById(R.id.fab);
        initFab();
        callGetStoreListAPI();

        itemClick = new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String storeId, String s) {

            }

            @Override
            public void onItemClicks(View view, int position, int i, String storeId) {
                if (i == 5) {
                    Intent intent = new Intent(getActivity(), SellerStoreDetailActivity.class);
                    intent.putExtra("storeId", storeId);
                    startActivity(intent);

                }
            }
        };

        return root;
    }


    private void initFab() {


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), AddStoreActivity.class);
                startActivity(intent);

//                Fragment newCase=new AddPostFragment();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.nav_host_fragment,newCase); // give your fragment container id in first parameter
//                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
//                transaction.commit();

                //    ReplaceFrag(new OrdersFragment());

            }
        });

    }


    private void callGetStoreListAPI() {
        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("VendorId", pref.getStringVal(SessionPref.LoginUserID));

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Call<StoreListResponse> call = service.storeList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<StoreListResponse>() {
            @Override
            public void onResponse(Call<StoreListResponse> call, Response<StoreListResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        ArrayList<Store> lst_store = (ArrayList<Store>) response.body().getData().getStores();
                        if (lst_store == null) {
                            lst_store = new ArrayList<>();
                        }

                        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
                        storeRecyclerView.setLayoutManager(RecyclerViewLayoutManager);
                        myStoreListAdapter = new MyStoreListAdapter(lst_store, getActivity(), itemClick);
                        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        storeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        storeRecyclerView.setHasFixedSize(true);
                        storeRecyclerView.addItemDecoration(new SpacesItemDecoration(2));
                        storeRecyclerView.setAdapter(myStoreListAdapter);

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
            public void onFailure(@NotNull Call<StoreListResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

}