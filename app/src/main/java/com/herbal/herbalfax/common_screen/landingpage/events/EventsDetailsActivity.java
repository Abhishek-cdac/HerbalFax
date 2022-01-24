package com.herbal.herbalfax.common_screen.landingpage.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.landingpage.events.adapter.EventsItemsMenuAdapter;
import com.herbal.herbalfax.common_screen.landingpage.events.adapter.EventsMenuAdapter;
import com.herbal.herbalfax.common_screen.landingpage.events.addevent.AddEventActivity;
import com.herbal.herbalfax.common_screen.landingpage.events.eventdetailsmodel.Event;
import com.herbal.herbalfax.common_screen.landingpage.events.eventdetailsmodel.EventListResponse;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsDetailsActivity extends AppCompatActivity {

    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    private List<Interest> DealsCategory = new ArrayList<>();
    LinearLayoutManager HorizontalLayout;
    EventsMenuAdapter eventsMenuAdapter;
    RecyclerView   eventsviewRecycler;
    RecyclerView eventsitemsbarRecycler;
    TextView addEvents;
    CommonClass clsCommon;
    EventsItemsMenuAdapter eventsItemsMenuAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_details);
        clsCommon = CommonClass.getInstance();
        eventsviewRecycler = findViewById(R.id.eventsmenubar);
        eventsitemsbarRecycler = findViewById(R.id.eventsitemsbar);
        addEvents = findViewById(R.id.addeventdetails);

        addEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddEventActivity.class);
                startActivity(intent);
            }
        });
        setEventsMenuBar();

        callEventListAPI();
    }

    private void callEventListAPI() {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
     
        Call<EventListResponse> call = service.userEventList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<EventListResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventListResponse> call, @NonNull Response<EventListResponse> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        assert response.body() != null;
                        ArrayList<Event> lst_blog = (ArrayList<Event>) response.body().getData().getEvents();
                        if (lst_blog == null) {
                            lst_blog = new ArrayList<>();
                        }



                        //blog list
                        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                        eventsitemsbarRecycler.setLayoutManager(RecyclerViewLayoutManager);
                        eventsItemsMenuAdapter = new EventsItemsMenuAdapter((ArrayList<Event>) lst_blog,getApplicationContext());
                        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        eventsitemsbarRecycler.setAdapter(eventsItemsMenuAdapter);


                    }  //   clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", response.body().getMessage(), "Ok");

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(EventsDetailsActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<EventListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setEventsMenuBar() {
        DealsCategory = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        eventsviewRecycler.setLayoutManager(RecyclerViewLayoutManager);
        eventsMenuAdapter = new EventsMenuAdapter((ArrayList<Interest>) DealsCategory, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        eventsviewRecycler.setLayoutManager(HorizontalLayout);
//      recyclerView.setHasFixedSize(true);
//      recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        eventsviewRecycler.setAdapter(eventsMenuAdapter);
    }


}