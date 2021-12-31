package com.herbal.herbalfax.customer.blogs;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.blogs.blogmodel.Blog;
import com.herbal.herbalfax.customer.blogs.blogmodel.BlogCategory;
import com.herbal.herbalfax.customer.blogs.blogmodel.BlogResponse;
import com.herbal.herbalfax.customer.interfaces.Onclick;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogsActivity extends AppCompatActivity {
    BlogsCategoryAdapter blogsCategoryAdapter;
    RecyclerView articleRecyclerview, SubHeadingRecyclerview;
    private ArrayList<BlogCategory> lst_blog;
    BlogListAdapter blogListAdapter;
    private CommonClass clsCommon;
    String categoryId;
    LinearLayoutManager HorizontalLayout;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    private Onclick itemClick;
    ImageView backImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs);
        articleRecyclerview = findViewById(R.id.articleRecyclerview);
        SubHeadingRecyclerview = findViewById(R.id.SubHeadingRecyclerview);
        backImg = findViewById(R.id.backImg);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        clsCommon = CommonClass.getInstance();
        callGetAllBlogsCategoryAPI();
        callGetAllBlogsAPI("");
        itemClick= new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String blogId, String s) {
                if (i == 1) {
                    callBlogReportApi(blogId, s);
                }
            }

            @Override
            public void onItemClicks(View view, int position, int i, String blogId) {
                if (i == 2) {
                    callBlogAddToFavApi(blogId);
                }else if (i == 3) {
                    categoryId = blogId;
                    callGetAllBlogsAPI(categoryId);
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void callBlogAddToFavApi(String blogId) {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("BlogId", blogId);

        Log.e("add to fav blogId",""+blogId);


        Call<CommonResponse> call = service.userBlogAddFav("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        assert response.body() != null;

                    } else {
                        //   clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", response.body().getMessage(), "Ok");
                    }
                }


            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callBlogReportApi(String blogId, String s) {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("Blog_Id", blogId);
        hashMap.put("Status", s);

        Log.e("report blogId",""+blogId);
        Log.e("report Status",""+s);

        Call<CommonResponse> call = service.userBlogAddReport("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        assert response.body() != null;
                        Toast.makeText(getApplicationContext(), "Reported Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        //   clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", response.body().getMessage(), "Ok");
                    }
                }


            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callGetAllBlogsCategoryAPI() {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("offset", "0");
        hashMap.put("limit", "50");

        Call<BlogResponse> call = service.userGetBlogsCategories("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<BlogResponse>() {
            @Override
            public void onResponse(@NonNull Call<BlogResponse> call, @NonNull Response<BlogResponse> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        assert response.body() != null;
                        ArrayList<BlogCategory> lst_blog = (ArrayList<BlogCategory>) response.body().getData().getBlogCategories();
                        if (lst_blog == null) {
                            lst_blog = new ArrayList<>();
                        }
                        for (int l = 0; l < lst_blog.size(); l++) {
                             categoryId = lst_blog.get(l).getIdblogCategories();
                            Log.e("categoryId", "" + categoryId);
                            callGetAllBlogsAPI("");
                        }


                    } else {
                        //   clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", response.body().getMessage(), "Ok");
                    }
                }


            }

            @Override
            public void onFailure(@NotNull Call<BlogResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callGetAllBlogsAPI(String categoryId) {

        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("offset", "0");
        hashMap.put("limit", "50");
        hashMap.put("category_id", categoryId);


        Call<BlogResponse> call = service.userGetAllBlogs("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<BlogResponse>() {
            @Override
            public void onResponse(@NonNull Call<BlogResponse> call, @NonNull Response<BlogResponse> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        assert response.body() != null;
                        ArrayList<BlogCategory> lst_blog = (ArrayList<BlogCategory>) response.body().getData().getBlogCategories();
                        if (lst_blog == null) {
                            lst_blog = new ArrayList<>();
                        }
                        ArrayList<Blog> lst_blogsdesc = (ArrayList<Blog>) response.body().getData().getBlogs();
                        if (lst_blogsdesc == null) {
                            lst_blogsdesc = new ArrayList<>();
                        }

                        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                        articleRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
                        blogsCategoryAdapter = new BlogsCategoryAdapter(lst_blog, getApplicationContext(), itemClick);
                        HorizontalLayout = new LinearLayoutManager(BlogsActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        articleRecyclerview.setLayoutManager(HorizontalLayout);
                        articleRecyclerview.setAdapter(blogsCategoryAdapter);

                        //blog list

                        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                        SubHeadingRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
                        blogListAdapter = new BlogListAdapter(lst_blogsdesc, getApplicationContext(),itemClick);
                        HorizontalLayout = new LinearLayoutManager(BlogsActivity.this, LinearLayoutManager.VERTICAL, false);
                        SubHeadingRecyclerview.setLayoutManager(HorizontalLayout);
                        SubHeadingRecyclerview.setAdapter(blogListAdapter);


                    }  //   clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", response.body().getMessage(), "Ok");

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(BlogsActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<BlogResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }


}