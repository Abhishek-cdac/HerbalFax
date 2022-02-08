package com.herbal.herbalfax.customer.homescreen.feed;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.blogs.BlogsActivity;
import com.herbal.herbalfax.customer.homescreen.feed.videoplay.AAH_CustomRecyclerView;
import com.herbal.herbalfax.customer.homescreen.homedashboard.getallpostmodel.GetAllPostResponse;
import com.herbal.herbalfax.customer.homescreen.homedashboard.getallpostmodel.Post;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.util.CommonUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeedFragment extends Fragment {
    LinearLayoutManager RecyclerViewLayoutManager;
    private FeedViewModel feedViewModel;
    private int pastVisiblesItems=0;
    private int visibleItemCount=0;
    private int totalItemCount=0;
    private int limit=10;
    private int offset=0;
    private boolean isLoading= true;
    private AAH_CustomRecyclerView feedrecyclerview;
    private ArrayList<Post> lst = new ArrayList<>();
    private ArrayList<Post> localList = new ArrayList<>();
    FeedAdapter feedAdapter;
    LinearLayout dailyBlogLl;
    private CommonClass clsCommon;
    TextView placeholder;
     Onclick itemClick;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        clsCommon = CommonClass.getInstance();

        final TextView textView = root.findViewById(R.id.text_home);
        feedrecyclerview = root.findViewById(R.id.feedrecyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        feedrecyclerview.setLayoutManager(manager);
        placeholder = root.findViewById(R.id.placeholder);
        dailyBlogLl = root.findViewById(R.id.dailyBlogLl);

//        callGetAllPostAPI();
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

        dailyBlogLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BlogsActivity.class);
                startActivity(intent);
            }
        });

        setUpfeedRecyclerView();
//        feedViewModel.getText().observe(getActivity(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    private void callLikePostAPI(String postId, String status) {

        SessionPref pref = SessionPref.getInstance(getActivity());


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("Status", status);
        hashMap.put("PostId", postId);


        Call<CommonResponse> call = service.userPostAddLike("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new retrofit2.Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                callPostAPI();

            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void callPostAPI()
    {
        if (CommonUtils.isInternetOn(getContext())) {
            callGetAllPostAPI();
        }else{
            Toast.makeText(getActivity(), getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show();
        }
    }


    private void callGetAllPostAPI() {
        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("offset", offset+"");
        hashMap.put("limit", limit+"");
        hashMap.put("search_key", "");

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Call<GetAllPostResponse> call = service.userGetAllPosts("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<GetAllPostResponse>() {
            @Override
            public void onResponse(Call<GetAllPostResponse> call, Response<GetAllPostResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        placeholder.setVisibility(View.GONE);
                        assert response.body() != null;
                        ArrayList<Post> lst_feed = (ArrayList<Post>) response.body().getData().getPosts();
                        if (lst_feed == null) {
                            lst_feed = new ArrayList<>();
                        }
                        if(limit==0)
                        {
                            localList.clear();
                        }
                        localList.addAll(lst_feed);
                        isLoading=true;
                        lst = localList;

                        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
                        feedrecyclerview.setLayoutManager(RecyclerViewLayoutManager);
                        feedAdapter = new FeedAdapter(localList, getActivity(), itemClick);
                        feedAdapter.setRef(FeedFragment.this);

                        feedrecyclerview.setItemAnimator(new DefaultItemAnimator());
                        feedrecyclerview.setActivity(getActivity());
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

                        feedrecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                                                callPostAPI();

                                            }

                                        }
                                    }

                                }
                            }
                        });

                    } else {
                        placeholder.setVisibility(View.VISIBLE);
                        //   clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", response.body().getMessage(), "Ok");
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
            public void onFailure(@NotNull Call<GetAllPostResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        offset=0;
        callPostAPI();

        try {
            feedrecyclerview.playAvailableVideos(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpfeedRecyclerView() {


    }
}