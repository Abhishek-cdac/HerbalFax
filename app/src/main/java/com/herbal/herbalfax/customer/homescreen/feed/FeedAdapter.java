package com.herbal.herbalfax.customer.homescreen.feed;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.LottieAnimationView;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.bottomsheet.MoreBottomSheet;
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.dialog.ShareDialogClass;
import com.herbal.herbalfax.customer.homescreen.feed.videoplay.AAH_CustomViewHolder;
import com.herbal.herbalfax.customer.homescreen.feed.videoplay.AAH_VideoImage;
import com.herbal.herbalfax.customer.homescreen.feed.videoplay.AAH_VideosAdapter;
import com.herbal.herbalfax.customer.homescreen.group.groupdetail.GroupDetailActivity;
import com.herbal.herbalfax.customer.homescreen.homedashboard.getallpostmodel.Post;
import com.herbal.herbalfax.customer.homescreen.homedashboard.getallpostmodel.ViewUser;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class FeedAdapter extends AAH_VideosAdapter {

    ArrayList<Post> lst_feed;

    private final Picasso picasso;
    Context mContext;
    Onclick itemClick;

    public FeedAdapter(ArrayList<Post> lst_feed, Context applicationContext, Onclick itemClick) {

        picasso = Picasso.get();
        this.lst_feed = lst_feed;
        this.mContext = applicationContext;
        this.itemClick = itemClick;

    }

    @Override
    public AAH_CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        AAH_CustomViewHolder viewHolder;
        mContext = parent.getContext();
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
            viewHolder = new ViewHolder(view);
        } else if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_type_video, parent, false);
            viewHolder = new ViewHolderUserVideo(view);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_type_question, parent, false);
            viewHolder = new ViewHolderQuestion(view);
        }


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final AAH_CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String postId = lst_feed.get(position).getIdposts();

        ArrayList<ViewUser> lst_views = (ArrayList<ViewUser>) lst_feed.get(position).getViewUser();
        if (lst_views == null) {
            lst_views = new ArrayList<>();
        }


        if (holder.getItemViewType() == 0) {
            ViewHolder userViewHolder;
            userViewHolder = (ViewHolder) holder;
            userViewHolder.userName.setText(lst_feed.get(position).getUFullName());
            userViewHolder.descHead.setText(lst_feed.get(position).getPostDesc());
            userViewHolder.descTxt.setText(lst_feed.get(position).getPostDesc());
            userViewHolder.PersonNameTxt.setText(lst_feed.get(position).getUFullName());
            userViewHolder.profDetail.setText(lst_feed.get(position).getmProfTitle());
            userViewHolder.commentcount.setText(lst_feed.get(position).getPostComments());
            userViewHolder.viewsCounttxt.setText(lst_feed.get(position).getPostViews() + " Views");

            if (lst_feed.get(position).getUProPic() != null) {
                if (lst_feed.get(position).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    // userViewHolder.userNameTxt.setVisibility(View.VISIBLE);

                } else {
                    picasso.load(lst_feed.get(position).getUProPic())
                            .into(userViewHolder.profileImg);
                }
            }

            if (lst_views.size() == 0) {
                userViewHolder.img1.setVisibility(View.GONE);
                userViewHolder.img2.setVisibility(View.GONE);
                userViewHolder.img3.setVisibility(View.GONE);
                userViewHolder.img4.setVisibility(View.GONE);
                userViewHolder.img5.setVisibility(View.GONE);

            }
            else if (lst_views.size() == 1) {
                userViewHolder.img1.setVisibility(View.VISIBLE);
                userViewHolder.img2.setVisibility(View.GONE);
                userViewHolder.img3.setVisibility(View.GONE);
                userViewHolder.img4.setVisibility(View.GONE);
                userViewHolder.img5.setVisibility(View.GONE);
                if (lst_views.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(userViewHolder.img1);
                }
                else
                {
                    picasso.load(lst_views.get(0).getUProPic())
                            .into(userViewHolder.img1);
                }

            }
            else if (lst_views.size() == 2) {
                userViewHolder.img1.setVisibility(View.VISIBLE);
                userViewHolder.img2.setVisibility(View.VISIBLE);
                userViewHolder.img3.setVisibility(View.GONE);
                userViewHolder.img4.setVisibility(View.GONE);
                userViewHolder.img5.setVisibility(View.GONE);
                if (lst_views.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(userViewHolder.img1);
                }else
                {
                    picasso.load(lst_views.get(0).getUProPic())
                            .into(userViewHolder.img1);
                }
                picasso.load(lst_views.get(1).getUProPic())
                        .into(userViewHolder.img2);
            }
            else if (lst_views.size() == 3) {
                userViewHolder.img1.setVisibility(View.VISIBLE);
                userViewHolder.img2.setVisibility(View.VISIBLE);
                userViewHolder.img3.setVisibility(View.VISIBLE);
                userViewHolder.img4.setVisibility(View.GONE);
                userViewHolder.img5.setVisibility(View.GONE);
                if (lst_views.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(userViewHolder.img1);
                }else

                {
                    picasso.load(lst_views.get(0).getUProPic())
                            .into(userViewHolder.img1);
                }
                picasso.load(lst_views.get(1).getUProPic())
                        .into(userViewHolder.img2);
                picasso.load(lst_views.get(2).getUProPic())
                        .into(userViewHolder.img3);
            }
            else if (lst_views.size() == 4) {
                userViewHolder.img1.setVisibility(View.VISIBLE);
                userViewHolder.img2.setVisibility(View.VISIBLE);
                userViewHolder.img3.setVisibility(View.VISIBLE);
                userViewHolder.img4.setVisibility(View.VISIBLE);
                userViewHolder.img5.setVisibility(View.GONE);
                if (lst_views.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(userViewHolder.img1);
                } else
                {
                    picasso.load(lst_views.get(0).getUProPic())
                            .into(userViewHolder.img1);
                }
                picasso.load(lst_views.get(1).getUProPic())
                        .into(userViewHolder.img2);
                picasso.load(lst_views.get(2).getUProPic())
                        .into(userViewHolder.img3);
                picasso.load(lst_views.get(3).getUProPic())
                        .into(userViewHolder.img4);
            }
            else if (lst_views.size() == 5) {
                userViewHolder.img1.setVisibility(View.VISIBLE);
                userViewHolder.img2.setVisibility(View.VISIBLE);
                userViewHolder.img3.setVisibility(View.VISIBLE);
                userViewHolder.img4.setVisibility(View.VISIBLE);
                userViewHolder.img5.setVisibility(View.VISIBLE);
                if (lst_views.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(userViewHolder.img1);
                }else
                   {
                    picasso.load(lst_views.get(0).getUProPic())
                            .into(userViewHolder.img1);
                }
                if (lst_views.get(1).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(userViewHolder.img1);
                }else{
                    picasso.load(lst_views.get(1).getUProPic())
                            .into(userViewHolder.img2);
                }

                if (lst_views.get(2).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(userViewHolder.img1);
                }else{
                    picasso.load(lst_views.get(2).getUProPic())
                            .into(userViewHolder.img3);
                }

                if (lst_views.get(3).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(userViewHolder.img1);
                }else {
                    picasso.load(lst_views.get(3).getUProPic())
                            .into(userViewHolder.img4);
                }

                if (lst_views.get(4).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(userViewHolder.img1);
                }else {
                    picasso.load(lst_views.get(4).getUProPic())
                            .into(userViewHolder.img5);
                }
            }


            try {

                picasso.load(lst_feed.get(position).getPostMediaLink())
                        .into(userViewHolder.feedPostImg);
//                if (lst_feed.get(position).getPostMediaLink() != null) {
//                    if(lst_feed.get(position).getPostMediaLink().equals("")){
//                        picasso.load(lst_feed.get(position).getPostMediaLink())
//                                .into(userViewHolder.feedPostImg);
//                    }
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            userViewHolder.likeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lst_feed.get(position).getIsFav().equals("1")) {
                        lst_feed.get(position).setIsFav("0");
                        userViewHolder.likeImg.setImageResource(R.drawable.like_heart_grey);
                        notifyItemChanged(position);
                        callAddToFavAPI(postId);
                    } else if (lst_feed.get(position).getIsFav().equals("0")) {
                        userViewHolder.likeImg.setImageResource(R.drawable.like_heart);
                        lst_feed.get(position).setIsFav("1");
                        notifyItemChanged(position);
                        callAddToFavAPI(postId);
                    }
                }
            });
            userViewHolder.commentll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext, AddCommentActivity.class);
                    i.putExtra("post_id", postId);
                    mContext.startActivity(i);
                }
            });
            userViewHolder.upTxt.setText(lst_feed.get(position).getPostLikes());

            userViewHolder.downTxt.setText(lst_feed.get(position).getPostDislikes());

            userViewHolder.upTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.onItemClicks(view, position, 4, postId, "1");

                }
            });
            userViewHolder.downTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.onItemClicks(view, position, 4, postId, "0");


                }
            });
            if (lst_feed.get(position).getIsFav().equals("1")) {
                userViewHolder.likeImg.setImageResource(R.drawable.like_heart);
            } else {
                userViewHolder.likeImg.setImageResource(R.drawable.like_heart_grey);
            }

            if (lst_feed.get(position).getmIsLike() != null) {
                userViewHolder.likesCountTxt.setText(lst_feed.get(position).getmIsLike() + " Likes");
            } else {
                userViewHolder.likesCountTxt.setText("0" + " Likes");
            }

        }
        else if (holder.getItemViewType() == 1) {
            ViewHolderUserVideo videoHolder;
            //  Uri uriVideo = Uri.parse(lst_feed.get(position).getPostMediaLink());

            holder.setVideoUrl(lst_feed.get(position).getPostMediaLink());
            holder.setLooping(false); //optional - true by default

            videoHolder = (ViewHolderUserVideo) holder;

            videoHolder.userName.setText(lst_feed.get(position).getUFullName());
            videoHolder.descHead.setText(lst_feed.get(position).getPostDesc());
            videoHolder.descTxt.setText(lst_feed.get(position).getPostDesc());
            videoHolder.PersonNameTxt.setText(lst_feed.get(position).getUFullName());
            videoHolder.profDetail.setText(lst_feed.get(position).getmProfTitle());
            videoHolder.commentcount.setText(lst_feed.get(position).getPostComments());
            videoHolder.viewsCounttxt.setText(lst_feed.get(position).getPostViews() + " Views");

            if (lst_views.size() == 0) {
                videoHolder.img1.setVisibility(View.GONE);
                videoHolder.img2.setVisibility(View.GONE);
                videoHolder.img3.setVisibility(View.GONE);
                videoHolder.img4.setVisibility(View.GONE);
                videoHolder.img5.setVisibility(View.GONE);

            }
            else if (lst_views.size() == 1) {
                videoHolder.img1.setVisibility(View.VISIBLE);
                videoHolder.img2.setVisibility(View.GONE);
                videoHolder.img3.setVisibility(View.GONE);
                videoHolder.img4.setVisibility(View.GONE);
                videoHolder.img5.setVisibility(View.GONE);
                if (lst_views.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(videoHolder.img1);
                }
                else
                {
                    picasso.load(lst_views.get(0).getUProPic())
                            .into(videoHolder.img1);
                }

            }
            else if (lst_views.size() == 2) {
                videoHolder.img1.setVisibility(View.VISIBLE);
                videoHolder.img2.setVisibility(View.VISIBLE);
                videoHolder.img3.setVisibility(View.GONE);
                videoHolder.img4.setVisibility(View.GONE);
                videoHolder.img5.setVisibility(View.GONE);
                if (lst_views.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(videoHolder.img1);
                }else
                {
                    picasso.load(lst_views.get(0).getUProPic())
                            .into(videoHolder.img1);
                }
                picasso.load(lst_views.get(1).getUProPic())
                        .into(videoHolder.img2);
            }
            else if (lst_views.size() == 3) {
                videoHolder.img1.setVisibility(View.VISIBLE);
                videoHolder.img2.setVisibility(View.VISIBLE);
                videoHolder.img3.setVisibility(View.VISIBLE);
                videoHolder.img4.setVisibility(View.GONE);
                videoHolder.img5.setVisibility(View.GONE);
                if (lst_views.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(videoHolder.img1);
                }else

                {
                    picasso.load(lst_views.get(0).getUProPic())
                            .into(videoHolder.img1);
                }
                picasso.load(lst_views.get(1).getUProPic())
                        .into(videoHolder.img2);
                picasso.load(lst_views.get(2).getUProPic())
                        .into(videoHolder.img3);
            }
            else if (lst_views.size() == 4) {
                videoHolder.img1.setVisibility(View.VISIBLE);
                videoHolder.img2.setVisibility(View.VISIBLE);
                videoHolder.img3.setVisibility(View.VISIBLE);
                videoHolder.img4.setVisibility(View.VISIBLE);
                videoHolder.img5.setVisibility(View.GONE);
                if (lst_views.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(videoHolder.img1);
                } else
                {
                    picasso.load(lst_views.get(0).getUProPic())
                            .into(videoHolder.img1);
                }
                picasso.load(lst_views.get(1).getUProPic())
                        .into(videoHolder.img2);
                picasso.load(lst_views.get(2).getUProPic())
                        .into(videoHolder.img3);
                picasso.load(lst_views.get(3).getUProPic())
                        .into(videoHolder.img4);
            }
            else if (lst_views.size() == 5) {
                videoHolder.img1.setVisibility(View.VISIBLE);
                videoHolder.img2.setVisibility(View.VISIBLE);
                videoHolder.img3.setVisibility(View.VISIBLE);
                videoHolder.img4.setVisibility(View.VISIBLE);
                videoHolder.img5.setVisibility(View.VISIBLE);
                if (lst_views.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(videoHolder.img1);
                }else
                {
                    picasso.load(lst_views.get(0).getUProPic())
                            .into(videoHolder.img1);
                }
                if (lst_views.get(1).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(videoHolder.img1);
                }else{
                    picasso.load(lst_views.get(1).getUProPic())
                            .into(videoHolder.img2);
                }

                if (lst_views.get(2).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(videoHolder.img1);
                }else{
                    picasso.load(lst_views.get(2).getUProPic())
                            .into(videoHolder.img3);
                }

                if (lst_views.get(3).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(videoHolder.img1);
                }else {
                    picasso.load(lst_views.get(3).getUProPic())
                            .into(videoHolder.img4);
                }

                if (lst_views.get(4).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(videoHolder.img1);
                }else {
                    picasso.load(lst_views.get(4).getUProPic())
                            .into(videoHolder.img5);
                }
            }



            if (lst_feed.get(position).getUProPic() != null) {
                if (lst_feed.get(position).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {

                    //  videoHolder.userNameTxt.setVisibility(View.VISIBLE);
//                picasso.load(R.color.green)
//                        .into(videoHolder.iv_profile);
                } else {
                    picasso.load(lst_feed.get(position).getUProPic())
                            .into(videoHolder.iv_profile);
                }
            }
            videoHolder.commentll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext, AddCommentActivity.class);
                    i.putExtra("post_id", postId);
                    mContext.startActivity(i);
                }
            });

            videoHolder.upTxt.setText(lst_feed.get(position).getPostLikes());
            if (lst_feed.get(position).getmIsLike() != null) {
                videoHolder.likesCountTxt.setText(lst_feed.get(position).getmIsLike() + " Likes");
            } else {
                videoHolder.likesCountTxt.setText("0" + " Likes");
            }
            if (lst_feed.get(position).getIsFav().equals("1")) {
                videoHolder.likeImg.setImageResource(R.drawable.like_heart);
            } else {
                videoHolder.likeImg.setImageResource(R.drawable.like_heart_grey);
            }
            videoHolder.downTxt.setText(lst_feed.get(position).getPostDislikes());

            videoHolder.likeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lst_feed.get(position).getIsFav().equals("1")) {
                        lst_feed.get(position).setIsFav("0");
                        videoHolder.likeImg.setImageResource(R.drawable.like_heart_grey);
                        notifyItemChanged(position);
                        callAddToFavAPI(postId);
                    } else if (lst_feed.get(position).getIsFav().equals("0")) {
                        videoHolder.likeImg.setImageResource(R.drawable.like_heart);
                        lst_feed.get(position).setIsFav("1");
                        notifyItemChanged(position);
                        callAddToFavAPI(postId);
                    }
                }
            });
            videoHolder.upTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.onItemClicks(view, position, 4, postId, "1");

                }
            });
            videoHolder.downTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.onItemClicks(view, position, 4, postId, "0");

                }
            });

            picasso.load(lst_feed.get(position).getPostThumbnail())
                    .into(videoHolder.videoImg.getImageView());

//       if (null != lst_feed.get(position).getPostThumbnail()) {
//       //     if (!lst_feed.get(position).getPostMediaType().equals("2")) {
//            picasso.load(lst_feed.get(position).getPostThumbnail())
//                        .into(videoHolder.iv_post_image, new ImageLoadedCallback(videoHolder.animationView) {
//                            @Override
//                            public void onSuccess() {
//                                if (this.iv_loader != null) {
//                                    this.iv_loader.setVisibility(View.GONE);
//                                }
//                            }
//                        });
//            }
//                else {
//
//                    // video
//
//                }
            //  }
        }

        else if (holder.getItemViewType() == 2) {
            ViewHolderQuestion viewHolderQuestion;
            viewHolderQuestion = (ViewHolderQuestion) holder;
            viewHolderQuestion.userName.setText(lst_feed.get(position).getUFullName());
            viewHolderQuestion.descHead.setText(lst_feed.get(position).getPostDesc());
            viewHolderQuestion.questionTxt.setText(lst_feed.get(position).getPostDesc());

            viewHolderQuestion.profDetail.setText(lst_feed.get(position).getmProfTitle());
            viewHolderQuestion.commentcount.setText(lst_feed.get(position).getPostComments());
            viewHolderQuestion.viewsCounttxt.setText(lst_feed.get(position).getPostViews() + " Views");
            if (lst_views.size() == 0) {
                viewHolderQuestion.img1.setVisibility(View.GONE);
                viewHolderQuestion.img2.setVisibility(View.GONE);
                viewHolderQuestion.img3.setVisibility(View.GONE);
                viewHolderQuestion.img4.setVisibility(View.GONE);
                viewHolderQuestion.img5.setVisibility(View.GONE);

            }
            else if (lst_views.size() == 1) {
                viewHolderQuestion.img1.setVisibility(View.VISIBLE);
                viewHolderQuestion.img2.setVisibility(View.GONE);
                viewHolderQuestion.img3.setVisibility(View.GONE);
                viewHolderQuestion.img4.setVisibility(View.GONE);
                viewHolderQuestion.img5.setVisibility(View.GONE);
                if (lst_views.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(viewHolderQuestion.img1);
                }
                else
                {
                    picasso.load(lst_views.get(0).getUProPic())
                            .into(viewHolderQuestion.img1);
                }

            }
            else if (lst_views.size() == 2) {
                viewHolderQuestion.img1.setVisibility(View.VISIBLE);
                viewHolderQuestion.img2.setVisibility(View.VISIBLE);
                viewHolderQuestion.img3.setVisibility(View.GONE);
                viewHolderQuestion.img4.setVisibility(View.GONE);
                viewHolderQuestion.img5.setVisibility(View.GONE);
                if (lst_views.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(viewHolderQuestion.img1);
                }else
                {
                    picasso.load(lst_views.get(0).getUProPic())
                            .into(viewHolderQuestion.img1);
                }
                picasso.load(lst_views.get(1).getUProPic())
                        .into(viewHolderQuestion.img2);
            }
            else if (lst_views.size() == 3) {
                viewHolderQuestion.img1.setVisibility(View.VISIBLE);
                viewHolderQuestion.img2.setVisibility(View.VISIBLE);
                viewHolderQuestion.img3.setVisibility(View.VISIBLE);
                viewHolderQuestion.img4.setVisibility(View.GONE);
                viewHolderQuestion.img5.setVisibility(View.GONE);
                if (lst_views.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(viewHolderQuestion.img1);
                }else

                {
                    picasso.load(lst_views.get(0).getUProPic())
                            .into(viewHolderQuestion.img1);
                }
                picasso.load(lst_views.get(1).getUProPic())
                        .into(viewHolderQuestion.img2);
                picasso.load(lst_views.get(2).getUProPic())
                        .into(viewHolderQuestion.img3);
            }
            else if (lst_views.size() == 4) {
                viewHolderQuestion.img1.setVisibility(View.VISIBLE);
                viewHolderQuestion.img2.setVisibility(View.VISIBLE);
                viewHolderQuestion.img3.setVisibility(View.VISIBLE);
                viewHolderQuestion.img4.setVisibility(View.VISIBLE);
                viewHolderQuestion.img5.setVisibility(View.GONE);
                if (lst_views.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(viewHolderQuestion.img1);
                } else
                {
                    picasso.load(lst_views.get(0).getUProPic())
                            .into(viewHolderQuestion.img1);
                }
                picasso.load(lst_views.get(1).getUProPic())
                        .into(viewHolderQuestion.img2);
                picasso.load(lst_views.get(2).getUProPic())
                        .into(viewHolderQuestion.img3);
                picasso.load(lst_views.get(3).getUProPic())
                        .into(viewHolderQuestion.img4);
            }
            else if (lst_views.size() == 5) {
                viewHolderQuestion.img1.setVisibility(View.VISIBLE);
                viewHolderQuestion.img2.setVisibility(View.VISIBLE);
                viewHolderQuestion.img3.setVisibility(View.VISIBLE);
                viewHolderQuestion.img4.setVisibility(View.VISIBLE);
                viewHolderQuestion.img5.setVisibility(View.VISIBLE);
                if (lst_views.get(0).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(viewHolderQuestion.img1);
                }else
                {
                    picasso.load(lst_views.get(0).getUProPic())
                            .into(viewHolderQuestion.img1);
                }
                if (lst_views.get(1).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(viewHolderQuestion.img1);
                }else{
                    picasso.load(lst_views.get(1).getUProPic())
                            .into(viewHolderQuestion.img2);
                }

                if (lst_views.get(2).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(viewHolderQuestion.img1);
                }else{
                    picasso.load(lst_views.get(2).getUProPic())
                            .into(viewHolderQuestion.img3);
                }

                if (lst_views.get(3).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(viewHolderQuestion.img1);
                }else {
                    picasso.load(lst_views.get(3).getUProPic())
                            .into(viewHolderQuestion.img4);
                }

                if (lst_views.get(4).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {
                    picasso.load(R.drawable.profileimg)
                            .into(viewHolderQuestion.img1);
                }else {
                    picasso.load(lst_views.get(4).getUProPic())
                            .into(viewHolderQuestion.img5);
                }
            }


            if (lst_feed.get(position).getUProPic() != null) {
                if (lst_feed.get(position).getUProPic().equals("http://herbalfax.nectarinfotel.com/upload/user_pro/")) {

//                picasso.load(R.color.green)
//                        .into(viewHolderQuestion.profileImg);
                    //  viewHolderQuestion.userNameTxt.setVisibility(View.VISIBLE);
                } else {
                    picasso.load(lst_feed.get(position).getUProPic())
                            .into(viewHolderQuestion.profileImg);
                }
            }
            if (lst_feed.get(position).getIsFav().equals("1")) {
                viewHolderQuestion.likeImg.setImageResource(R.drawable.like_heart);
            } else {
                viewHolderQuestion.likeImg.setImageResource(R.drawable.like_heart_grey);
            }


            viewHolderQuestion.likeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lst_feed.get(position).getIsFav().equals("1")) {
                        lst_feed.get(position).setIsFav("0");
                        viewHolderQuestion.likeImg.setImageResource(R.drawable.like_heart_grey);
                        notifyItemChanged(position);
                        callAddToFavAPI(postId);
                    } else if (lst_feed.get(position).getIsFav().equals("0")) {
                        viewHolderQuestion.likeImg.setImageResource(R.drawable.like_heart);
                        lst_feed.get(position).setIsFav("1");
                        notifyItemChanged(position);
                        callAddToFavAPI(postId);
                    }
                    //  callAddToFavAPI(postId);
                }
            });

//            viewHolderQuestion.moreImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
            viewHolderQuestion.upTxt.setText(lst_feed.get(position).getPostLikes());

            viewHolderQuestion.downTxt.setText(lst_feed.get(position).getPostDislikes());

            viewHolderQuestion.upTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.onItemClicks(view, position, 4, postId, "1");

                }
            });
            viewHolderQuestion.downTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.onItemClicks(view, position, 4, postId, "0");

                }
            });

        }
    }

    private void callAddToFavAPI(String postId) {


        Log.e("postId....", "" + postId);

        SessionPref pref = SessionPref.getInstance(mContext);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("PostId", postId);

        Call<CommonResponse> call = service.userPostAddFav("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new retrofit2.Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                Log.e("Added to Fav", "Added to Fav");
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (lst_feed == null) {
            return 0;
        }
        return lst_feed.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (lst_feed == null) {
            return 0;
        } else if (lst_feed.size() == 0) {
            return 0;
        } else if (lst_feed.get(position).getPostMediaType().equals("1")) {
            return 0;
        } else if (lst_feed.get(position).getPostMediaType().equals("2")) {
            return 1;
        } else if (lst_feed.get(position).getPostMediaType().equals("0")) {
            return 2;
        }
        return 0;
    }

    FeedFragment feedFragment;
    GroupDetailActivity groupDetailActivity;
    public void setRef(FeedFragment feedFragment) {
        this.feedFragment = feedFragment;
    }
 public void setRef(GroupDetailActivity groupDetailActivity) {
        this.groupDetailActivity = groupDetailActivity;
    }


    public  class ViewHolderQuestion extends AAH_CustomViewHolder {
        TextView userName, questionTxt, userNameTxt, profDetail, commentcount;
        ImageView profileImg, moreImg;
        ImageView likeImg;
        TextView shareImg, descHead, viewsCounttxt;
        LinearLayout commentll;
        TextView upTxt, downTxt, viewAnswerTxt, viewFollowTxt, viewPassTxt;
        ImageView img1, img2, img3, img4, img5;

        public ViewHolderQuestion(@NonNull View itemView) {
            super(itemView);

            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            img4 = itemView.findViewById(R.id.img4);
            img5 = itemView.findViewById(R.id.img5);
            viewsCounttxt = itemView.findViewById(R.id.viewsCounttxt);
            commentcount = itemView.findViewById(R.id.commentcount);
            profDetail = itemView.findViewById(R.id.detail);
            commentll = itemView.findViewById(R.id.commentll);
            descHead = itemView.findViewById(R.id.descHead);
            userName = itemView.findViewById(R.id.userName);
            userNameTxt = itemView.findViewById(R.id.userNameTxt);
            profileImg = itemView.findViewById(R.id.profileImg);
            moreImg = itemView.findViewById(R.id.moreImg);
            questionTxt = itemView.findViewById(R.id.questionTxt);
            upTxt = itemView.findViewById(R.id.upTxt);
            downTxt = itemView.findViewById(R.id.downTxt);
            likeImg = itemView.findViewById(R.id.likeImg);
            shareImg = itemView.findViewById(R.id.shareImg);
            viewAnswerTxt = itemView.findViewById(R.id.viewAnswerTxt);
            viewFollowTxt = itemView.findViewById(R.id.viewFollowTxt);
            viewPassTxt = itemView.findViewById(R.id.viewPassTxt);

            moreImg.setOnClickListener(v -> showBottomSheet(getAdapterPosition()));

            commentll.setOnClickListener(v -> {
                Intent mIntent = new Intent(v.getContext(), AddCommentActivity.class);
                mIntent.putExtra("post_id", lst_feed.get(getAdapterPosition()).getIdposts());
                mContext.startActivity(mIntent);
            });

        }


    }

    public class ViewHolderUserVideo extends AAH_CustomViewHolder {
        ImageView iv_heart_red;
        ImageView iv_profile;
        ImageView likeImg;
        TextView shareImg, profDetail, commentcount;
        LottieAnimationView animationView;
        ImageView iv_post_image, iv_more_options;
        CardView card_image;
        TextView userName, PersonNameTxt, descTxt, AddComment, viewsCounttxt;
        TextView upTxt, descHead, downTxt, likesCountTxt, viewCommentTxt, userNameTxt;
        ImageView iv_mute_unmute;
        ImageView img_playback;
        AAH_VideoImage videoImg;
        boolean isMuted = false;
        boolean isPlaying = true;
        LinearLayout commentll;

        ImageView img1, img2, img3, img4, img5;
        public ViewHolderUserVideo(@NonNull View itemView) {
            super(itemView);
            profDetail = itemView.findViewById(R.id.detail);
            commentcount = itemView.findViewById(R.id.commentcount);
            viewsCounttxt = itemView.findViewById(R.id.viewsCounttxt);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            img4 = itemView.findViewById(R.id.img4);
            img5 = itemView.findViewById(R.id.img5);
            commentll = itemView.findViewById(R.id.commentll);
            descHead = itemView.findViewById(R.id.descHead);
            userNameTxt = itemView.findViewById(R.id.userNameTxt);
            upTxt = itemView.findViewById(R.id.upTxt);
            descTxt = itemView.findViewById(R.id.descTxt);
            PersonNameTxt = itemView.findViewById(R.id.PersonNameTxt);
            downTxt = itemView.findViewById(R.id.downTxt);
            iv_mute_unmute = itemView.findViewById(R.id.iv_mute_unmute);
            videoImg = itemView.findViewById(R.id.videoImg);
            img_playback = itemView.findViewById(R.id.img_playback);
            likesCountTxt = itemView.findViewById(R.id.likesCountTxt);
            iv_profile = itemView.findViewById(R.id.profileImg);
            likeImg = itemView.findViewById(R.id.likeImg);
            iv_post_image = itemView.findViewById(R.id.iv_post_image);
            card_image = itemView.findViewById(R.id.card_image);
            userName = itemView.findViewById(R.id.userName);
            viewCommentTxt = itemView.findViewById(R.id.viewCommentTxt);
            animationView = itemView.findViewById(R.id.animationView);
//            txt_heart_count = itemView.findViewById(R.id.txt_heart_count);
            AddComment = itemView.findViewById(R.id.AddCommentV);
            shareImg = itemView.findViewById(R.id.shareImg);
            iv_more_options = itemView.findViewById(R.id.moreImg);
            iv_more_options.setOnClickListener(v -> showBottomSheet(getAdapterPosition()));

            viewCommentTxt.setOnClickListener(v -> {
                Intent mIntent = new Intent(v.getContext(), AddCommentActivity.class);
                mIntent.putExtra("post_id", lst_feed.get(getAdapterPosition()).getIdposts());
                mContext.startActivity(mIntent);
            });
            AddComment.setOnClickListener(v -> {
                Intent mIntent = new Intent(v.getContext(), AddCommentActivity.class);
                mIntent.putExtra("post_id", lst_feed.get(getAdapterPosition()).getIdposts());
                mContext.startActivity(mIntent);
            });
//            iv_mute_unmute.setOnClickListener(v -> {
//                if (!isMuted) {
//                    isMuted = true;
//                    muteVideo();
//                    iv_mute_unmute.setImageResource(R.drawable.ic_mute);
//                } else {
//                    isMuted = false;
//                    unmuteVideo();
//                    iv_mute_unmute.setImageResource(R.drawable.ic_unmute);
//                }
//            });
            img_playback.setOnClickListener(v -> {
                if (isPlaying) {
                    isPlaying = false;
                    pauseVideo();
                    img_playback.setImageResource(R.drawable.play_circle);

                } else {
                    isPlaying = true;
                    playVideo();
                    img_playback.setImageResource(R.drawable.ic_pause);
                }

            });
        }


        @Override
        public void videoStarted() {
            super.videoStarted();
            img_playback.setImageResource(R.drawable.ic_pause);
//            if (isMuted) {
//                muteVideo();
//                iv_mute_unmute.setImageResource(R.drawable.ic_mute);
//            } else {
//                unmuteVideo();
//                iv_mute_unmute.setImageResource(R.drawable.ic_unmute);
//            }
        }

        @Override
        public void pauseVideo() {
            super.pauseVideo();
            img_playback.setImageResource(R.drawable.play_circle);
        }
    }


    public class ViewHolder extends AAH_CustomViewHolder {
        TextView commentcount, viewsCounttxt, profDetail, userName, upTxt, downTxt, likesCountTxt, PersonNameTxt, descTxt, viewCommentTxt, AddComment, userNameTxt;
        ImageView profileImg, moreImg, feedPostImg, likeImg, AddCommentProfileImg;
        TextView shareImg, descHead;
        LinearLayout commentll;

        ImageView img1, img2, img3, img4, img5;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            img4 = itemView.findViewById(R.id.img4);
            img5 = itemView.findViewById(R.id.img5);
            viewsCounttxt = itemView.findViewById(R.id.viewsCounttxt);
            commentcount = itemView.findViewById(R.id.commentcount);
            profDetail = itemView.findViewById(R.id.detail);
            commentll = itemView.findViewById(R.id.commentll);
            descHead = itemView.findViewById(R.id.descHead);
            userNameTxt = itemView.findViewById(R.id.userNameTxt);
            PersonNameTxt = itemView.findViewById(R.id.PersonNameTxt);
            AddCommentProfileImg = itemView.findViewById(R.id.AddCommentProfileImg);
            AddComment = itemView.findViewById(R.id.AddComment);
            viewCommentTxt = itemView.findViewById(R.id.viewCommentTxt);
            descTxt = itemView.findViewById(R.id.descTxt);
            likesCountTxt = itemView.findViewById(R.id.likesCountTxt);
            shareImg = itemView.findViewById(R.id.shareImg);
            likeImg = itemView.findViewById(R.id.likeImg);
            downTxt = itemView.findViewById(R.id.downTxt);
            upTxt = itemView.findViewById(R.id.upTxt);
            userName = itemView.findViewById(R.id.userName);
            profileImg = itemView.findViewById(R.id.profileImg);
            moreImg = itemView.findViewById(R.id.moreImg);
            feedPostImg = itemView.findViewById(R.id.feedPostImg);
            moreImg.setOnClickListener(v -> showBottomSheet(getAdapterPosition()));
            shareImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ShareDialogClass cdd = new ShareDialogClass((Activity) mContext);
                        cdd.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            viewCommentTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mIntent = new Intent(view.getContext(), AddCommentActivity.class);
                    mIntent.putExtra("post_id", lst_feed.get(getAdapterPosition()).getIdposts());
                    mContext.startActivity(mIntent);
                }
            });
            AddComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mIntent = new Intent(view.getContext(), AddCommentActivity.class);
                    mIntent.putExtra("post_id", lst_feed.get(getAdapterPosition()).getIdposts());
                    mContext.startActivity(mIntent);
                }
            });
        }
    }

    private void showBottomSheet(int adapterPosition) {
        FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
        MoreBottomSheet sheet = new MoreBottomSheet(this, adapterPosition);
        sheet.show(fragmentManager, "comment bottom sheet");
    }


    class ImageLoadedCallback implements Callback {
        LottieAnimationView iv_loader;

        public ImageLoadedCallback(LottieAnimationView view) {
            iv_loader = view;
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(Exception e) {

        }


    }
}
