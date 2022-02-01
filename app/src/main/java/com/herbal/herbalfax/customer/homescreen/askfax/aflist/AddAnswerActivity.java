package com.herbal.herbalfax.customer.homescreen.askfax.aflist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.askfax.aflist.addanswermodel.AddAnswerResponse;
import com.herbal.herbalfax.customer.homescreen.askfax.aflist.addanswermodel.QueAnswer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class AddAnswerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private ArrayList<QueAnswer> lst_getComment;
    String question_id;
    TextView txt_post_comment;
    ImageView back, userImg;
    EditText addAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_answer);

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                question_id = extras.getString("question_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        userImg = findViewById(R.id.userImg);
        txt_post_comment = findViewById(R.id.txt_post_comment);
        back = findViewById(R.id.back);
        addAnswer = findViewById(R.id.add_comment);
        recyclerView = findViewById(R.id.comments_list);
        callGetAnswerApi();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txt_post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAddAnswerApi();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void callAddAnswerApi() {

        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("question_id", question_id);
        hashMap.put("answer", addAnswer.getText().toString());

        Call<AddAnswerResponse> call = service.userAskFaxAddAnswers("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new retrofit2.Callback<AddAnswerResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddAnswerResponse> call, @NonNull Response<AddAnswerResponse> response) {

                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        addAnswer.setText(" ");
                        addAnswer.clearFocus();

                        Toast.makeText(getApplicationContext(), "Comment Added", Toast.LENGTH_SHORT).show();
                        callGetAnswerApi();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddAnswerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void callGetAnswerApi() {

        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("question_id", question_id);
        hashMap.put("limit", "50");
        hashMap.put("offset", "");

        Call<AddAnswerResponse> call = service.userAskFaxQuestionAnswer("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new retrofit2.Callback<AddAnswerResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AddAnswerResponse> call, @NonNull Response<AddAnswerResponse> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        lst_getComment = (ArrayList<QueAnswer>) response.body().getData().getQueAnswers();
                        if (lst_getComment == null) {
                            lst_getComment = new ArrayList<>();
                        }


                        RecyclerView.LayoutManager manager = new LinearLayoutManager(AddAnswerActivity.this, RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(manager);
                        AnswerAdapter adapter = new AnswerAdapter(getApplicationContext(), lst_getComment);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

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
            public void onFailure(@NonNull Call<AddAnswerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

    }
}