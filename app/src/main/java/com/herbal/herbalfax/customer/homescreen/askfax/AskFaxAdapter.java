package com.herbal.herbalfax.customer.homescreen.askfax;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.homescreen.askfax.aflist.AddAnswerActivity;
import com.herbal.herbalfax.customer.homescreen.askfax.aflist.askfaxlistmodel.AfQuestion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class AskFaxAdapter extends RecyclerView.Adapter<AskFaxAdapter.ViewHolder> {

    ArrayList<AfQuestion> lst_int;

    Context mContext;
    Picasso picasso;

    public AskFaxAdapter(ArrayList<AfQuestion> lst_int, Context applicationContext) {
        picasso = Picasso.get();
        this.lst_int = lst_int;
        this.mContext = applicationContext;
    }


    @NonNull
    @Override
    public AskFaxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.askfax_item, parent, false);
        mContext = parent.getContext();
        return new AskFaxAdapter.ViewHolder(view);
    }

    @SuppressLint({"RecyclerView", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull AskFaxAdapter.ViewHolder holder, int position) {
        if (lst_int.get(position).getAFQPrivacyType().equals("1")) {
            holder.privacyTxt.setText("Public");
        } else {
            holder.privacyTxt.setText("Private");
        }

        String questionId = lst_int.get(position).getIdafQuestions();
        holder.questionTxt.setText(lst_int.get(position).getAFQTitle());
        holder.userNameTxt.setText(lst_int.get(position).getUFullName());
        holder.answerTxt.setText(lst_int.get(position).getAFQAnswers() + " Answers");
        holder.timeTxt.setText("Added on : " + lst_int.get(position).getAFQCOn() + " at " + lst_int.get(position).getAFQCAt());


        if (lst_int.get(position).getUProPic() != null) {
            if (lst_int.get(position).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {

            } else {
                Picasso.get()
                        .load(lst_int.get(position).getUProPic())
                        .into(holder.profile_image);
            }
        }

        holder.answerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddAnswerActivity.class);
                intent.putExtra("question_id", lst_int.get(position).getIdafQuestions());
                mContext.startActivity(intent);
            }
        });

/*
        if (lst_int.get(position).getIsLike() != null) {
            if (lst_int.get(position).getIsLike().equals("1")) {
                holder.likeImg.setImageResource(R.drawable.ic_like_filled);
            } else {
                holder.likeImg.setImageResource(R.drawable.ic_like);
            }
        } else {
            holder.likeImg.setImageResource(R.drawable.ic_like);
        }

        holder.likeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (lst_int.get(position).getIsLike() != null) {
                        if (lst_int.get(position).getIsLike().equals("1")) {
                            lst_int.get(position).setIsLike("0");
                            holder.likeImg.setImageResource(R.drawable.ic_like_filled);
                            notifyItemChanged(position);
                            callLikeQuestionAPI(questionId, "0");
                        } else if (lst_int.get(position).getIsLike().equals("0")) {
                            holder.likeImg.setImageResource(R.drawable.ic_like); //ic_heart_green_border
                            lst_int.get(position).setIsLike("1");
                            notifyItemChanged(position);
                            callLikeQuestionAPI(questionId, "1");
                        }
                    }
//                    else {
//                        callLikeQuestionAPI(commentId);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/


      /*  if (lst_int.get(position).getIsFav().equals("1")) {
            holder.favImg.setImageResource(R.drawable.ic_icon_heart);
        } else {
            holder.favImg.setImageResource(R.drawable.ic_heart_green_border);
        }
        holder.favTxt.setOnClickListener(view -> {
            if (lst_int.get(position).getIsFav().equals("1")) {
                lst_int.get(position).setIsFav("0");
                holder.favImg.setImageResource(R.drawable.ic_heart_green_border);
                notifyItemChanged(position);
                callAddToFavAPI(questionId);
            } else if (lst_int.get(position).getIsFav().equals("0")) {
                holder.favImg.setImageResource(R.drawable.ic_icon_heart);
                lst_int.get(position).setIsFav("1");
                notifyItemChanged(position);
                callAddToFavAPI(questionId);
            }
        });*/
    }

    private void callLikeQuestionAPI(String questionId, String status) {
        SessionPref pref = SessionPref.getInstance(mContext);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("IdAFQuestion", questionId);
        hashMap.put("Status", status);
        Call<CommonResponse> call = service.userAskFaxQuestionLike("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new retrofit2.Callback<CommonResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                notifyDataSetChanged();
                Log.e("questionId askfax", "" + questionId);
                Log.e("status askfax", "" + status);
//                if (mContext instanceof AskFaxFragment) {
//                    ((AskFaxFragment)mContext).callAskFaxQuestionListApi();
//                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    @Override
    public int getItemCount() {
        return lst_int.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView favTxt, likeTxt, answerTxt, timeTxt, userNameTxt, privacyTxt, questionTxt;
        ImageView profile_image, likeImg, favImg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            favImg = itemView.findViewById(R.id.favImg);
            likeImg = itemView.findViewById(R.id.likeImg);
            favTxt = itemView.findViewById(R.id.favTxt);
            likeTxt = itemView.findViewById(R.id.likeTxt);
            answerTxt = itemView.findViewById(R.id.answerTxt);
            questionTxt = itemView.findViewById(R.id.questionTxt);
            privacyTxt = itemView.findViewById(R.id.privacyTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            userNameTxt = itemView.findViewById(R.id.userNameTxt);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }

    private void callAddToFavAPI(String IdAFQuestion) {

        SessionPref pref = SessionPref.getInstance(mContext);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("IdAFQuestion", IdAFQuestion);

        Call<CommonResponse> call = service.userAskFaxQuestionAddFav("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new retrofit2.Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {

                Log.e("Added to Fav", "Added to Fav");
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

    }


}

