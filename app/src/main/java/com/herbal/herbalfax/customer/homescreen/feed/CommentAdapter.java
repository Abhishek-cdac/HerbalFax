package com.herbal.herbalfax.customer.homescreen.feed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.homescreen.homedashboard.getallcommentmodel.PostComment;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private ArrayList<PostComment> commentList;
    private int selected_index = -1;

    private Context mContext;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private String postId;

    public CommentAdapter(Context applicationContext, ArrayList<PostComment> lst_getComment) {
        this.commentList = lst_getComment;
        this.mContext = applicationContext;
        //   this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(commentList.get(position).getUFullName());
        holder.desc.setText(commentList.get(position).getPCComment());
        if (commentList.get(position).getUProPic() != null) {
            if (commentList.get(position).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {

            } else {
                Picasso.get()
                        .load(commentList.get(position).getUProPic())
                        .into(holder.userImg);
            }
        }
//        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//        try {
//            long time = Objects.requireNonNull(sdf.parse(commentList.get(position).getPCCreatedOn())).getTime();
//            long now = System.currentTimeMillis();
//            CharSequence ago =  DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
//
//            Log.e("timesAgo",""+ago);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


    /*    try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
           sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date past = sdf.parse(commentList.get(position).getPCCreatedOn());
            Date now = new Date();
            Log.e("timesAgo",""+now);
            System.out.println(TimeUnit.MILLISECONDS.toMillis(now.getTime() - past.getTime()) + " milliseconds ago");
            System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
            System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
            System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");
        }
        catch (Exception j){
            j.printStackTrace();
        }*/

        String text = null;
        String timeToSet = null;
        try {
            if (commentList.get(position).getPCCreatedOn() != null) {
                String timeFormat = commentList.get(position).getPCCreatedOn();
                timeFormat = timeFormat.replace("T", " ");

                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                Date date = null;
                try {
                    date = sdf.parse(timeFormat);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                sdf.setTimeZone(TimeZone.getDefault());
                String formattedDate = sdf.format(Objects.requireNonNull(date));


                Date ddd = null;
                try {
                    ddd = sdf.parse(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long millis = Objects.requireNonNull(ddd).getTime();
                text = TimeAgo.using(millis);
                timeToSet = text.toLowerCase();
                holder.time.setText(text.toLowerCase());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        String commentId = commentList.get(position).getIdpostComments();

        holder.like.setText(commentList.get(position).getPCLikes() + " Likes");
       /* holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callLikeCommentsAPI(commentId);
            }
        });*/
        if (commentList.get(position).getIsLike() != null) {
            if (commentList.get(position).getIsLike().equals("1")) {
                holder.commentLikeImg.setImageResource(R.drawable.ic_icon_heart);
            } else {
                holder.commentLikeImg.setImageResource(R.drawable.ic_heart_green_border);
            }
        } else {
            holder.commentLikeImg.setImageResource(R.drawable.ic_heart_green_border);
        }

        holder.commentLikeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (commentList.get(position).getIsLike() != null) {
                        if (commentList.get(position).getIsLike().equals("1")) {
                            commentList.get(position).setIsLike("0");
                            holder.commentLikeImg.setImageResource(R.drawable.ic_icon_heart);
                            notifyItemChanged(position);
                            callLikeCommentsAPI(commentId);
                        } else if (commentList.get(position).getIsLike().equals("0")) {
                            holder.commentLikeImg.setImageResource(R.drawable.ic_heart_green_border); //ic_heart_green_border
                            commentList.get(position).setIsLike("1");
                            notifyItemChanged(position);
                            callLikeCommentsAPI(commentId);
                        }
                    } else {
                        callLikeCommentsAPI(commentId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void callLikeCommentsAPI(String commentId) {
        SessionPref pref = SessionPref.getInstance(mContext);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("CommentId", commentId);
        Call<CommonResponse> call = service.userPostAddCommentLike("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new retrofit2.Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (mContext instanceof AddCommentActivity) {
                    ((AddCommentActivity)mContext).callGetCommentApi();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView desc;
        private final TextView like;
        private final TextView reply;
        private final TextView time;
        private final ImageView commentLikeImg, userImg;
        private final RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userImg = itemView.findViewById(R.id.userImg);
            commentLikeImg = itemView.findViewById(R.id.commentLikeImg);
            name = itemView.findViewById(R.id.comment_name);
            desc = itemView.findViewById(R.id.comment_desc);
            like = itemView.findViewById(R.id.comment_like);
            reply = itemView.findViewById(R.id.comment_reply);
            time = itemView.findViewById(R.id.comment_time);
            //delete = itemView.findViewById(R.id.dustbin);
            relativeLayout = itemView.findViewById(R.id.card_comment);
        }


    }

}
