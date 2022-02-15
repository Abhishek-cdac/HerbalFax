package com.herbal.herbalfax.customer.blogs;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.blogs.blogdetailmodel.Blog;
import com.herbal.herbalfax.customer.blogs.blogdetailmodel.BlogsDetailResponse;
import com.herbal.herbalfax.customer.blogs.blogdetailmodel.Para;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogDetailsActivity extends AppCompatActivity {
    String blogId;
    private CommonClass clsCommon;
    RecyclerView BlogDetailRecyclerview;
    LinearLayoutManager HorizontalLayout;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    BlogDetailsAdapter blogDetailsAdapter;
    ImageView reportImg, BlogImg, backBtn, imgProfile;
    TextView title, desc, userNameTxt, professionNameTxt;
    TextView upTxt, downTxt;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);
        clsCommon = CommonClass.getInstance();
        BlogDetailRecyclerview = findViewById(R.id.BlogDetailRecyclerview);

        reportImg = findViewById(R.id.reportImg);
        imgProfile = findViewById(R.id.imgprofile);
        userNameTxt = findViewById(R.id.userNameTxt);
        professionNameTxt = findViewById(R.id.professionNameTxt);

        upTxt = findViewById(R.id.upTxt);
        backBtn = findViewById(R.id.backBtn);
        downTxt = findViewById(R.id.downTxt);
        BlogImg = findViewById(R.id.BlogImg);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);

        backBtn.setOnClickListener(view -> onBackPressed());


        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                blogId = extras.getString("blog_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        callBlogDetailsApi(blogId);
        upTxt.setOnClickListener(view -> callLikeBlogsAPI(blogId, "1"));downTxt.setOnClickListener(view -> callLikeBlogsAPI(blogId, "0"));

    }
    private void callLikeBlogsAPI(String blogId, String status) {
        Log.e("Blog_Id....", "" + blogId);
        Log.e("status....", "" + status);
        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        Log.e("status....", "Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("Status", status);
        hashMap.put("Blog_Id", blogId);


        Call<CommonResponse> call = service.userBlogAddLike("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                callBlogDetailsApi(blogId);
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void callBlogDetailsApi(String blogId) {

        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        Log.e("LoginJwtoken..", "Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("blog_id", blogId);

        Call<BlogsDetailResponse> call = service.userGetBlogView("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<BlogsDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<BlogsDetailResponse> call, @NonNull Response<BlogsDetailResponse> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        assert response.body() != null;
                        ArrayList<Para> lst_blogsdesc = (ArrayList<Para>) response.body().getData().getBlog().getParas();
                        if (lst_blogsdesc == null) {
                            lst_blogsdesc = new ArrayList<>();
                        }

                        try {
                            final Blog blog = response.body().getData().getBlog();
                            //blog details list
                            title.setVisibility(View.VISIBLE);
                            title.setText(blog.getBlogTitle());
                            desc.setText(blog.getBlogDesc());
                            userNameTxt.setText(blog.getuFullName());
                            professionNameTxt.setText(blog.getProfTitle());
                            Picasso.get()
                                    .load(blog.getBlogImage())
                                    .into(BlogImg);
                            if (blog.getuProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                                Picasso.get().load(R.drawable.profileimg)
                                        .into(imgProfile);
                            }
                            else {
                                Picasso.get() .load(blog.getuProPic())
                                        .into(imgProfile);
                            }

                            if (blog.getIsReport().equals("1")) {
                                reportImg.setImageResource(R.drawable.ic_flag_green);
                            } else {
                               reportImg.setImageResource(R.drawable.ic_icon_report_flag);
                            }
                            upTxt.setText(blog.getBlogCountYes());
                            downTxt.setText(blog.getBlogCountNo());

                            RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                            BlogDetailRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
                            blogDetailsAdapter = new BlogDetailsAdapter(lst_blogsdesc, getApplicationContext(), blog);
                            HorizontalLayout = new LinearLayoutManager(BlogDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
                            BlogDetailRecyclerview.setLayoutManager(HorizontalLayout);
                            BlogDetailRecyclerview.setAdapter(blogDetailsAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(BlogDetailsActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<BlogsDetailResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}