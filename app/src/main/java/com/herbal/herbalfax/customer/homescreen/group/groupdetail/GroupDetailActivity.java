package com.herbal.herbalfax.customer.homescreen.group.groupdetail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.homescreen.feed.FeedAdapter;
import com.herbal.herbalfax.customer.homescreen.feed.videoplay.AAH_CustomRecyclerView;
import com.herbal.herbalfax.customer.homescreen.group.groupdetail.model.GroupDetailResponse;
import com.herbal.herbalfax.customer.homescreen.homedashboard.getallpostmodel.GetAllPostResponse;
import com.herbal.herbalfax.customer.homescreen.homedashboard.getallpostmodel.Post;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.customer.post.AddPostActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GroupDetailActivity extends AppCompatActivity {
    TextView placeholder,headerTxt, groupTitle, groupCategory, memberCount, addPostTxt;
    ImageView back, img1, img2, img3, groupImg;
    private String GroupId;
    CommonClass clsCommon;
    FeedAdapter feedAdapter;
    Onclick itemClick;

    AAH_CustomRecyclerView feedrecyclerview;
    private ArrayList<Post> lst = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        clsCommon = CommonClass.getInstance();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            GroupId = getIntent().getExtras().getString("groupId");
            Log.e("GroupId detail ", "" + GroupId);
        }


        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);

        placeholder = findViewById(R.id.placeholder);

        addPostTxt = findViewById(R.id.addPostTxt);
        memberCount = findViewById(R.id.memberCount);
        groupCategory = findViewById(R.id.groupType);
        groupTitle = findViewById(R.id.groupTitle);
        groupImg = findViewById(R.id.groupImg);
        feedrecyclerview = findViewById(R.id.feedrecyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        feedrecyclerview.setLayoutManager(manager);

        headerTxt = findViewById(R.id.headerTxt);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        addPostTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AddPostActivity
                Intent intent = new Intent(GroupDetailActivity.this, AddPostActivity.class);
                intent.putExtra("PostGroupId", GroupId);
                startActivity(intent);
            }
        });

        callGroupDetailAPI(GroupId);
        callGetAllPostAPI();
        itemClick = new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String postId, String s) {

                if (i ==4){
                    callLikePostAPI(postId, s);

                }
            }

            @Override
            public void onItemClicks(View view, int position, int i, String postId) {

            }
        };
    }
    private void callLikePostAPI(String postId, String status) {

        SessionPref pref = SessionPref.getInstance(getApplicationContext());


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("Status", status);
        hashMap.put("PostId", postId);


        Call<CommonResponse> call = service.userPostAddLike("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new retrofit2.Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                callGetAllPostAPI();
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        callGetAllPostAPI();

        try {
            feedrecyclerview.playAvailableVideos(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void callGetAllPostAPI() {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("offset", "0");
        hashMap.put("limit", "50");
        hashMap.put("search_key", "");
        hashMap.put("GroupId", GroupId);



        Call<GetAllPostResponse> call = service.userGetAllPosts("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<GetAllPostResponse>() {
            @Override
            public void onResponse(Call<GetAllPostResponse> call, Response<GetAllPostResponse> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        placeholder.setVisibility(View.GONE);
                        assert response.body() != null;
                        ArrayList<Post> lst_feed = (ArrayList<Post>) response.body().getData().getPosts();
                        if (lst_feed == null) {
                            lst_feed = new ArrayList<>();
                        }


                        lst = lst_feed;
                        feedAdapter = new FeedAdapter(lst_feed, getApplicationContext(), itemClick);
                        feedAdapter.setRef(GroupDetailActivity.this);

                        feedrecyclerview.setItemAnimator(new DefaultItemAnimator());
                        feedrecyclerview.setActivity(GroupDetailActivity.this);
                        feedrecyclerview.setCheckForMp4(false);

                        feedrecyclerview.setDownloadPath(Environment.getExternalStorageDirectory() + "/HerbalFax/Video");
                        feedrecyclerview.setDownloadVideos(true); // false by default
                        List<String> urls = null;
                        try {
                            urls = new ArrayList<>();
                            for (Post object : lst) {
                                if (object.getPostMediaLink().toLowerCase().contains(".mp4"))
                                    urls.add(object.getPostMediaLink());
                            }

                            feedrecyclerview.preDownload(urls);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        feedrecyclerview.setVisiblePercent(70);
                        feedrecyclerview.setPlayOnlyFirstVideo(true);
                        feedrecyclerview.setAdapter(feedAdapter);
                        feedrecyclerview.smoothScrollBy(0, 1);
                        feedrecyclerview.smoothScrollBy(0, -1);
                    } else {
                        placeholder.setVisibility(View.VISIBLE);
                        //   clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(GroupDetailActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }





                }

            }

            @Override
            public void onFailure(@NotNull Call<GetAllPostResponse> call, Throwable t) {
                t.printStackTrace();

                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void callGroupDetailAPI(String groupId) {
        SessionPref pref = SessionPref.getInstance(this);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("GroupId", groupId);


        Call<GroupDetailResponse> call = service.userGroupDetails("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<GroupDetailResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<GroupDetailResponse> call, @NonNull Response<GroupDetailResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        try {
                            groupTitle.setText(response.body().getData().getGroup().getGrpName());
                            headerTxt.setText(response.body().getData().getGroup().getGrpName());
                            groupCategory.setText(response.body().getData().getGroup().getGrpTypeTitle());
                            memberCount.setText("" + response.body().getData().getGroupUsersCount());
                          if(!response.body().getData().getGroup().getGrpImage().equals("")){
                                Picasso.get().load(response.body().getData().getGroup().getGrpImage())
                                      .into(groupImg);
                          }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<GroupDetailResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
