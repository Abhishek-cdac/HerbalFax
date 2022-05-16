package com.herbal.herbalfax.common_screen.landingpage.events.eventdetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.landingpage.adapter.AddCommentsAdapter;
import com.herbal.herbalfax.common_screen.landingpage.events.eventdetail.commentlstmodel.EventComment;
import com.herbal.herbalfax.common_screen.landingpage.events.eventdetail.commentlstmodel.EventCommentsResponse;
import com.herbal.herbalfax.common_screen.landingpage.events.eventdetail.detaileventsmodel.DetailedEventResponse;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventReadMoreActivity extends AppCompatActivity {
    ImageView addComments, eventImg, back;
    RecyclerView RecyclerAddPeopleComment;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    AddCommentsAdapter addPeoplecommentAdaptor;
    private LinearLayoutManager HorizontalLayout;
    private String IdEvent;
    CardView cardviewComment;
    TextView headerTxt, eventsViews, dayTxt, descTxt, eventName, eventLocation, eventDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_read_more);
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                IdEvent = extras.getString("IdEvents");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cardviewComment = findViewById(R.id.cardviewComment);
        back = findViewById(R.id.back);
        eventImg = findViewById(R.id.eventImg);
        headerTxt = findViewById(R.id.headerTxt);
        eventsViews = findViewById(R.id.eventsViews);
        eventDate = findViewById(R.id.eventDate);
        eventName = findViewById(R.id.eventNameTxt);
        eventLocation = findViewById(R.id.locationTxt);
        descTxt = findViewById(R.id.descTxt);
        addComments = findViewById(R.id.addcomments);
        dayTxt = findViewById(R.id.dayTxt);
        RecyclerAddPeopleComment = findViewById(R.id.addcommentsprofile);


        addComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(IdEvent);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

        callEventDetailAPI(IdEvent);
        callEventCommentAPI(IdEvent);
    }

    public void callEventCommentAPI(String idEvent) {
        SessionPref pref = SessionPref.getInstance(this);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("IdEvent", idEvent);
        hashMap.put("limit", "");
        hashMap.put("offset", "");

        Call<EventCommentsResponse> call = service.userGetEventComments("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<EventCommentsResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<EventCommentsResponse> call, Response<EventCommentsResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        List<EventComment> lst_comments = response.body().getData().getEventComments();

                        if (lst_comments.size() <= 0) {
                            cardviewComment.setVisibility(View.GONE);

                        } else {
                            cardviewComment.setVisibility(View.VISIBLE);
                            RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                            RecyclerAddPeopleComment.setLayoutManager(RecyclerViewLayoutManager);
                            addPeoplecommentAdaptor = new AddCommentsAdapter(lst_comments, getApplicationContext());
                            HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            RecyclerAddPeopleComment.setAdapter(addPeoplecommentAdaptor);
                        }
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        //  clsCommon.showDialogMsgFrag(c, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<EventCommentsResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callEventDetailAPI(String idEvent) {
        SessionPref pref = SessionPref.getInstance(this);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("IdEvent", idEvent);

        Call<DetailedEventResponse> call = service.userEventDetails("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<DetailedEventResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<DetailedEventResponse> call, Response<DetailedEventResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        headerTxt.setText(response.body().getData().getEvent().getEventName());
                        eventName.setText(response.body().getData().getEvent().getEventName());
                        eventDate.setText("Date On : " + response.body().getData().getEvent().getEventDate() + " at " + response.body().getData().getEvent().getEventTime());
                        eventLocation.setText(response.body().getData().getEvent().getEventAddress());
                        descTxt.setText(response.body().getData().getEvent().getEventDesc());
                        eventsViews.setText(response.body().getData().getEvent().getEventViews() + "+ Views");

                        if (response.body().getData().getEvent().getEventImage().equals("")) {
                            Picasso.get().load(R.drawable.readmoredetail)
                                    .into(eventImg);
                        } else {
                            Picasso.get().load(response.body().getData().getEvent().getEventImage())
                                    .into(eventImg);
                        }
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        //  clsCommon.showDialogMsgFrag(c, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<DetailedEventResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBottomSheet(String adapterPosition) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddCommentbottomsheet sheet = new AddCommentbottomsheet(EventReadMoreActivity.this, adapterPosition);
        sheet.show(fragmentManager, "comment bottom sheet");
    }


}