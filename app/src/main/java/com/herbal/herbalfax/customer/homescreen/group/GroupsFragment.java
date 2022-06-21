package com.herbal.herbalfax.customer.homescreen.group;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.homescreen.group.Adaptor.DiscoveryAdapter;
import com.herbal.herbalfax.customer.homescreen.group.Adaptor.YourGroupAdapter;
import com.herbal.herbalfax.customer.homescreen.group.creategroup.CreateGroupActivity;
import com.herbal.herbalfax.customer.homescreen.group.discovermodel.DiscoverResponse;
import com.herbal.herbalfax.customer.homescreen.group.yourgroupmodel.Group;
import com.herbal.herbalfax.customer.homescreen.group.yourgroupmodel.YourGroupResponse;
import com.herbal.herbalfax.customer.interfaces.Onclick;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GroupsFragment extends Fragment {
    private List<com.herbal.herbalfax.customer.homescreen.group.discovermodel.Group> lst_discoverGroup = new ArrayList<>();
    private List<Group> lst_yourGroup = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewdiscovery;
    private TextView yourgroup;
    private TextView discovery;
    private YourGroupAdapter mAdapter;
    private DiscoveryAdapter mAdaptery;
    private TextView creategroup;
    private GroupsViewModel groupsViewModel;
    CommonClass clsCommon;

    Onclick itemClick;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //   groupsViewModel = ViewModelProviders.of(this).get(GroupsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        clsCommon = CommonClass.getInstance();
        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerViewdiscovery = root.findViewById(R.id.recycler_view_discover);
        yourgroup = root.findViewById(R.id.yourgroup);
        discovery = root.findViewById(R.id.discovery);
        creategroup = root.findViewById(R.id.creategroup);


        yourgroup.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
            @Override
            public void onClick(View view) {
                //  mapLinear.setVisibility(View.GONE);
                discovery.setTextColor(getResources().getColor(R.color.black));
                yourgroup.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
                discovery.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                yourgroup.setTextColor(getResources().getColor(R.color.white));
                recyclerView.setVisibility(View.VISIBLE);
                recyclerViewdiscovery.setVisibility(View.GONE);

//                Drawable img = getContext().getResources().getDrawable(R.drawable.ic_icon_your_group_white);
//                img.setBounds(0, 0, 80, 60);
//                yourgroup.setCompoundDrawables(img, null, null, null);
//
//                Drawable img1 = getContext().getResources().getDrawable(R.drawable.ic_icon_discover);
//                img1.setBounds(0, 0, 80, 60);
//                discovery.setCompoundDrawables(img1, null, null, null);

            }
        });

        discovery.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
            @Override
            public void onClick(View view) {
                // mapLinear.setVisibility(View.VISIBLE);

                discovery.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
                yourgroup.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                discovery.setTextColor(getResources().getColor(R.color.white));
                yourgroup.setTextColor(getResources().getColor(R.color.black));
//                Drawable img3 = getContext().getResources().getDrawable(R.drawable.ic_icon_your_groups);
//                img3.setBounds(0, 0, 80, 60);
//                yourgroup.setCompoundDrawables(img3, null, null, null);
//
//                Drawable img4 = getContext().getResources().getDrawable(R.drawable.ic_icon_discover_white);
//                img4.setBounds(0, 0, 80, 60);
//                discovery.setCompoundDrawables(img4, null, null, null);

                recyclerViewdiscovery.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                callDiscoverGroupAPI();
            }
        });
        creategroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
                startActivity(intent);
            }
        });
        callYourGroupAPI();
        itemClick = new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String groupId, String s) {

            }

            @Override
            public void onItemClicks(View view, int position, int i, String groupId) {
                if (i == 17) {
                    callJoinGroupAPI(groupId);
                }
            }
        };

        return root;
    }

    private void callJoinGroupAPI(String groupId) {
        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("IdGroup", groupId);

        Call<CommonResponse> call = service.userGroupJoin("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        Toast.makeText(getActivity(), "Group joined successfully", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        //  clsCommon.showDialogMsgFrag(c, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callYourGroupAPI() {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<YourGroupResponse> call = service.userGroupList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<YourGroupResponse>() {
            @Override
            public void onResponse(@NonNull Call<YourGroupResponse> call, @NonNull Response<YourGroupResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        try {
                            lst_yourGroup = (ArrayList<Group>) response.body().getData().getGroups();
                            mAdapter = new YourGroupAdapter(lst_yourGroup, getActivity(), itemClick);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);
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
            public void onFailure(@NotNull Call<YourGroupResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callDiscoverGroupAPI() {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<DiscoverResponse> call = service.userAllGroupList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<DiscoverResponse>() {
            @Override
            public void onResponse(@NonNull Call<DiscoverResponse> call, @NonNull Response<DiscoverResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        lst_discoverGroup = (ArrayList<com.herbal.herbalfax.customer.homescreen.group.discovermodel.Group>) response.body().getData().getGroups();

                        try {
                            mAdaptery = new DiscoveryAdapter(lst_discoverGroup, getActivity(), itemClick);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerViewdiscovery.setLayoutManager(mLayoutManager);
                            recyclerViewdiscovery.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewdiscovery.setAdapter(mAdaptery);
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
            public void onFailure(@NotNull Call<DiscoverResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}