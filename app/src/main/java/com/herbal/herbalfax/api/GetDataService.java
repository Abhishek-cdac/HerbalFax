package com.herbal.herbalfax.api;

import com.herbal.herbalfax.common_screen.create_new_password.UpdatePasswordResponse;
import com.herbal.herbalfax.common_screen.landingpage.events.addevent.model.AddEventResponse;
import com.herbal.herbalfax.common_screen.landingpage.events.eventdetail.commentlstmodel.EventCommentsResponse;
import com.herbal.herbalfax.common_screen.landingpage.events.eventdetail.detaileventsmodel.DetailedEventResponse;
import com.herbal.herbalfax.common_screen.landingpage.events.eventlist.eventdetailsmodel.EventListResponse;
import com.herbal.herbalfax.common_screen.login.LoginResponse;
import com.herbal.herbalfax.common_screen.terms.termmodel.TermResponse;
import com.herbal.herbalfax.common_screen.verify_email_after_signup.otpverified.SignUpOtpVerifyResponse;
import com.herbal.herbalfax.common_screen.verify_email_after_signup.resendmodel.ResendOTPModel;
import com.herbal.herbalfax.customer.blogs.blogdetailmodel.BlogsDetailResponse;
import com.herbal.herbalfax.customer.blogs.blogmodel.BlogResponse;
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.homescreen.addcard.model.AddCardModel;
import com.herbal.herbalfax.customer.homescreen.addcard.ordersubmitmodel.OrderSubmitModel;
import com.herbal.herbalfax.customer.homescreen.askfax.addaf.addaskfaxmodel.AddAskFaxResponse;
import com.herbal.herbalfax.customer.homescreen.askfax.addaf.predatamodel.AskFaxPreDataResponse;
import com.herbal.herbalfax.customer.homescreen.askfax.aflist.addanswermodel.AddAnswerResponse;
import com.herbal.herbalfax.customer.homescreen.askfax.aflist.askfaxlistmodel.AskFaxListResponse;
import com.herbal.herbalfax.customer.homescreen.askfax.sharestorypredatamodel.ShareStoryPreData;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.model.AddedCartModel;
import com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress.model.SelectDeliveryAddressModel;
import com.herbal.herbalfax.customer.homescreen.favourites.favdeal.model.FavDealResponse;
import com.herbal.herbalfax.customer.homescreen.favourites.favproduct.model.FavProductResponse;
import com.herbal.herbalfax.customer.homescreen.favourites.favstore.model.FavStoreResponse;
import com.herbal.herbalfax.customer.homescreen.getusermodel.GetUserResponse;
import com.herbal.herbalfax.customer.homescreen.group.addmember.model.UserListModel;
import com.herbal.herbalfax.customer.homescreen.group.creategroup.creategrpmodel.CreateGroupModel;
import com.herbal.herbalfax.customer.homescreen.group.creategroup.model.GroupPreData;
import com.herbal.herbalfax.customer.homescreen.group.discovermodel.DiscoverResponse;
import com.herbal.herbalfax.customer.homescreen.group.groupdetail.model.GroupDetailResponse;
import com.herbal.herbalfax.customer.homescreen.group.yourgroupmodel.YourGroupResponse;
import com.herbal.herbalfax.customer.homescreen.homedashboard.getallcommentmodel.GetAllComments;
import com.herbal.herbalfax.customer.homescreen.homedashboard.getallpostmodel.GetAllPostResponse;
import com.herbal.herbalfax.customer.homescreen.nearbystores.userstoremodel.UserStoreListResponse;
import com.herbal.herbalfax.customer.homescreen.products.model.AddToCartModel;
import com.herbal.herbalfax.customer.selectInterest.getintrestmodel.GetInterestResponse;
import com.herbal.herbalfax.customer.selectInterest.saveintrestmodel.SaveInterestResponse;
import com.herbal.herbalfax.customer.signup.presignupmodel.PreSignUpData;
import com.herbal.herbalfax.customer.signup.signupmodel.RegisterResult;
import com.herbal.herbalfax.customer.store.storeratingmodel.UserStoreRatingList;
import com.herbal.herbalfax.vendor.sellerproduct.productcategorymodel.ProductCategoryResponse;
import com.herbal.herbalfax.vendor.sellerproduct.productdetail.productdetailsmodel.ProductDetailsResponse;
import com.herbal.herbalfax.vendor.sellerproduct.productlistmodel.ProductListResponse;
import com.herbal.herbalfax.vendor.store.storecategory.StoreAddPreData;
import com.herbal.herbalfax.vendor.storedetail.storedetailmodel.StoreDetailResponse;
import com.herbal.herbalfax.vendor.storelist.storelistmodel.StoreListResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface GetDataService {


    /* Sign up & login API */
    @Multipart
    @POST("signup")
    Call<RegisterResult> signUp(@PartMap Map<String, RequestBody> map, @Part MultipartBody.Part filePart);


    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("get-tnc")
    Call<TermResponse> getTnc(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("get-interests")
    Call<GetInterestResponse> getInterests(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("save-interests")
    Call<SaveInterestResponse> saveInterests(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("signup-resend-otp")
    Call<ResendOTPModel> signupResendOtp(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("update-password")
    Call<UpdatePasswordResponse> updatePassword(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("signup-otp-verify")
    Call<SignUpOtpVerifyResponse> signupOtpVerify(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("signup-pre-data")
    Call<PreSignUpData> signupPreData(@FieldMap Map<String, String> param);


    /* Post API*/

    @Multipart
    @POST("add-post")
    Call<CommonResponse> addPost(@Header("Authorization") String token, @Part("PostIsMedia") RequestBody postIsMedia,
                                 @Part("PostDesc") RequestBody postDesc, @Part("PostMediaType") RequestBody PostMediaType, @Part("PostGroupId") RequestBody PostGroupId, @Part MultipartBody.Part filePart);

    @POST("get-userprofile")
    Call<GetUserResponse> getUserprofile(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("user-get-all-posts")
    Call<GetAllPostResponse> userGetAllPosts(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-post-add-like")
    Call<CommonResponse> userPostAddLike(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-post-add-fav")
    Call<CommonResponse> userPostAddFav(@Header("Authorization") String token, @FieldMap Map<String, String> param);


    /*Comments API */
    @FormUrlEncoded
    @POST("get-post-comments")
    Call<GetAllComments> getPostComments(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-post-add-comment")
    Call<CommonResponse> userPostAddComment(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-post-add-comment-like")
    Call<CommonResponse> userPostAddCommentLike(@Header("Authorization") String token, @FieldMap Map<String, String> param);


    /*Blog API*/
    @FormUrlEncoded
    @POST("user-get-all-blogs")
    Call<BlogResponse> userGetAllBlogs(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-get-blog-view")
    Call<BlogsDetailResponse> userGetBlogView(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-get-blogs-categories")
    Call<BlogResponse> userGetBlogsCategories(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-blog-add-like")
    Call<CommonResponse> userBlogAddLike(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-blog-add-report")
    Call<CommonResponse> userBlogAddReport(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-blog-add-fav")
    Call<CommonResponse> userBlogAddFav(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    /*User store add rating*/
    @FormUrlEncoded
    @POST("user-store-add-rating")
    Call<CommonResponse> userStoreAddRating(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-store-details")
    Call<StoreDetailResponse> userStoreDetails(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-store-rating-list")
    Call<UserStoreRatingList> userStoreRatingList(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-store-list")
    Call<UserStoreListResponse> userStoreList(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @POST("user-store-product-category-list")
    Call<ProductCategoryResponse> userStoreProductCategoryList(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("add-to-cart")
    Call<AddToCartModel> addToCart(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-cart-add-delivery-address")
    Call<CommonResponse> userCartAddDeliveryAddress(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-cart-add-card")
    Call<AddCardModel> userCartAddCard(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-cart-order-submit")
    Call<OrderSubmitModel> userCartOrderSubmit(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-get-user-list")
    Call<UserListModel> userGetUserList(@Header("Authorization") String token, @FieldMap Map<String, String> param);


    @POST("user-cart-delivery-address")
    Call<SelectDeliveryAddressModel> userCartDeliveryAddress(@Header("Authorization") String token);


    @POST("user-cart-list")
    Call<AddedCartModel> userCartList(@Header("Authorization") String token);

    /*Seller API*/

    @Multipart
    @POST("vendor/store-add")
    Call<CommonResponse> storeAdd(@Header("Authorization") String token, @PartMap Map<String, RequestBody> map, @Part MultipartBody.Part filePart, @Part MultipartBody.Part StorePhotos, @Part MultipartBody.Part StoreLogo);

    @POST("vendor/store-add-pre-data")
    Call<StoreAddPreData> storeAddPreData(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("vendor/store-list")
    Call<StoreListResponse> storeList(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("vendor/store-details")
    Call<StoreDetailResponse> storeDetails(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @POST("user-group-create-data")
    Call<GroupPreData> userGroupCreateData(@Header("Authorization") String token);

    @Multipart
    @POST("vendor/store-update")
    Call<CommonResponse> storeUpdate(@Header("Authorization") String token, @PartMap Map<String, RequestBody> map, @Part MultipartBody.Part filePart, @Part MultipartBody.Part StorePhotos, @Part MultipartBody.Part StoreLogo);


    /*not completed */


    @Multipart
    @POST("vendor/product-add")
    Call<CommonResponse> vendorProductAdd(@Header("Authorization") String token, @PartMap Map<String, RequestBody> map, @Part MultipartBody.Part filePart);


    @FormUrlEncoded
    @POST("user-store-product-list")
    Call<ProductListResponse> userStoreProductList(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-store-product-details")
    Call<ProductDetailsResponse> userStoreProductDetails(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("vendor/product-delete")
    Call<CommonResponse> vendorProductDelete(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @Multipart
    @POST("vendor/product-update")
    Call<CommonResponse> vendorProductUpdate(@Header("Authorization") String token, @Part MultipartBody.Part filePart, @PartMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-store-product-add-fav")
    Call<CommonResponse> userStoreProductAddFav(@Header("Authorization") String token, @FieldMap Map<String, String> param);


    /*Groups*/

    @Multipart
    @POST("user-group-create")
    Call<CreateGroupModel> userGroupCreate(@Header("Authorization") String token, @PartMap Map<String, RequestBody> map, @Part MultipartBody.Part filePart);

    @POST("user-group-list")
    Call<YourGroupResponse> userGroupList(@Header("Authorization") String token);

    @POST("user-all-group-list")
    Call<DiscoverResponse> userAllGroupList(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("user-group-join")
    Call<CommonResponse> userGroupJoin(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-group-details")
    Call<GroupDetailResponse> userGroupDetails(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    /*Events*/

    @Multipart
    @POST("user-event-create")
    Call<AddEventResponse> userEventCreate(@Header("Authorization") String token, @PartMap Map<String, RequestBody> param, @Part MultipartBody.Part filePart);

    @POST("user-event-list")
    Call<EventListResponse> userEventList(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("user-event-details")
    Call<DetailedEventResponse> userEventDetails(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-event-add-comment")
    Call<CommonResponse> userEventAddComment(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-get-event-comments")
    Call<EventCommentsResponse> userGetEventComments(@Header("Authorization") String token, @FieldMap Map<String, String> param);


    /*Favourites */

    @POST("user-fav-posts")
    Call<GetAllPostResponse> userFavPosts(@Header("Authorization") String token);

    @POST("user-fav-blogs")
    Call<BlogResponse> userFavBlogs(@Header("Authorization") String token);

    @POST("user-fav-deals")
    Call<FavDealResponse> userFavDeals(@Header("Authorization") String token);

    @POST("user-fav-products")
    Call<FavProductResponse> userFavProducts(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("user-fav-stores")
    Call<FavStoreResponse> userFavStores(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    /*ask fax*/

    @POST("user-ask-fax-add-question-data")
    Call<AskFaxPreDataResponse> userAskFaxAddQuestionData(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("user-ask-fax-add-questions")
    Call<AddAskFaxResponse> userAskFaxAddQuestions(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-ask-fax-add-answer")
    Call<AddAnswerResponse> userAskFaxAddAnswers(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-ask-fax-question-list")
    Call<AskFaxListResponse> userAskFaxQuestionsList(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user-ask-fax-question-answer")
    Call<AddAnswerResponse> userAskFaxQuestionAnswer(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    /*share story*/
    @POST("user-add-blog-predata")
    Call<ShareStoryPreData> userAddBlogPredata(@Header("Authorization") String token);

    @Multipart
    @POST("user-add-blog")
    Call<CommonResponse> userAddBlog(@Header("Authorization") String token, @Part("BlogCategory") RequestBody blogCategory,
                                 @Part("BlogTitle") RequestBody blogTitle, @Part("BlogDesc") RequestBody blogDesc, @Part("BlogURL") RequestBody blogURL, @Part MultipartBody.Part filePart);

}
