package com.herbal.herbalfax.customer.homescreen.feed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.homescreen.homedashboard.getallcommentmodel.GetAllComments;
import com.herbal.herbalfax.customer.homescreen.homedashboard.getallcommentmodel.PostComment;
import com.herbal.herbalfax.customer.homescreen.homedashboard.getallpostmodel.Comment;
import com.herbal.herbalfax.customer.homescreen.homedashboard.getallpostmodel.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class AddCommentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private ArrayList<PostComment> lst_getComment;
    String postId;
    TextView txt_post_comment;
    ImageView back, userImg;
    EditText add_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                postId = extras.getString("post_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        userImg = findViewById(R.id.userImg);
        txt_post_comment = findViewById(R.id.txt_post_comment);
        back = findViewById(R.id.back);
        add_comment = findViewById(R.id.add_comment);
        recyclerView = findViewById(R.id.comments_list);
        callGetCommentApi();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txt_post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAddCommentApi();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void callAddCommentApi() {

        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("PostId", postId);
        hashMap.put("PostComment", add_comment.getText().toString());

        Call<CommonResponse> call = service.userPostAddComment("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new retrofit2.Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        add_comment.setText(" ");
                        add_comment.clearFocus();

                        Toast.makeText(getApplicationContext(), "Comment Added", Toast.LENGTH_SHORT).show();
                        callGetCommentApi();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void callGetCommentApi() {

        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("PostId", postId);
        hashMap.put("limit", "50");
        Log.e("postId", "" + this.postId);
        Call<GetAllComments> call = service.getPostComments("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new retrofit2.Callback<GetAllComments>() {
            @Override
            public void onResponse(@NonNull Call<GetAllComments> call, @NonNull Response<GetAllComments> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        lst_getComment = (ArrayList<PostComment>) response.body().getData().getPostComments();
                        if (lst_getComment == null) {
                            lst_getComment = new ArrayList<>();
                        }
//                        if (lst_getComment.get(0).getUProPic() != null) {
//                            if (lst_getComment.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
//
//                            } else {
//                                Picasso.get()
//                                        .load(lst_getComment.get(0).getUProPic())
//                                        .into(userImg);
//                            }
//                        }

                        RecyclerView.LayoutManager manager = new LinearLayoutManager(AddCommentActivity.this, RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(manager);
                        CommentAdapter adapter = new CommentAdapter(getApplicationContext(), lst_getComment);
                        recyclerView.setAdapter(adapter);


                        // int number = adapter.getItemCount();

//                        if (number == 0) {
//                            text_count.setText(R.string.str_no_comments);
//                        } else {
//                            text_count.setText((number + " Comments"));
//                        }
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<GetAllComments> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

    }
}