package com.herbal.herbalfax.api;

import com.herbal.herbalfax.common_screen.create_new_password.UpdatePasswordResponse;
import com.herbal.herbalfax.common_screen.login.LoginResponse;
import com.herbal.herbalfax.common_screen.terms.termmodel.TermResponse;
import com.herbal.herbalfax.common_screen.verify_email_after_signup.otpverified.SignUpOtpVerifyResponse;
import com.herbal.herbalfax.common_screen.verify_email_after_signup.resendmodel.ResendOTPModel;
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.blogs.blogdetailmodel.BlogsDetailResponse;
import com.herbal.herbalfax.customer.blogs.blogmodel.BlogResponse;
import com.herbal.herbalfax.customer.homescreen.addcard.model.AddCardModel;
import com.herbal.herbalfax.customer.homescreen.addcard.ordersubmitmodel.OrderSubmitModel;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.model.AddedCartModel;
import com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress.model.SelectDeliveryAddressModel;
import com.herbal.herbalfax.customer.homescreen.getusermodel.GetUserResponse;
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
                                 @Part("PostDesc") RequestBody postDesc, @Part("PostMediaType") RequestBody PostMediaType, @Part MultipartBody.Part filePart);

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

    @POST("user-store-list")
    Call<UserStoreListResponse> userStoreList(@Header("Authorization") String token);

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

}
