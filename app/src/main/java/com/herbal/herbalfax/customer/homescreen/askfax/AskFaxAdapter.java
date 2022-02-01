package com.herbal.herbalfax.customer.homescreen.askfax;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.askfax.aflist.AddAnswerActivity;
import com.herbal.herbalfax.customer.homescreen.askfax.aflist.askfaxlistmodel.AfQuestion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull AskFaxAdapter.ViewHolder holder, int position) {
        if (lst_int.get(position).getAFQPrivacyType().equals("1")) {
            holder.privacyTxt.setText("Public");
        } else {
            holder.privacyTxt.setText("Private");
        }
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
                intent.putExtra("question_id",lst_int.get(position).getIdafQuestions());
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return lst_int.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView answerTxt,timeTxt, userNameTxt, privacyTxt, questionTxt;
        ImageView profile_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            answerTxt = itemView.findViewById(R.id.answerTxt);
            questionTxt = itemView.findViewById(R.id.questionTxt);
            privacyTxt = itemView.findViewById(R.id.privacyTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            userNameTxt = itemView.findViewById(R.id.userNameTxt);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }
}

