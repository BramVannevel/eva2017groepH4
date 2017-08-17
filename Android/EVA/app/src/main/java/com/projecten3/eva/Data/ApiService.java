package com.projecten3.eva.Data;

import android.media.Image;

import com.projecten3.eva.Model.Authenticate;
import com.projecten3.eva.Model.Challenges;
import com.projecten3.eva.Model.Post;
import com.projecten3.eva.Model.Posts;
import com.projecten3.eva.Model.Restaurant;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * retrofit service class that contains all the calls in one place
 */
public interface ApiService {

    /**
     * login network call, post a username and password to retrieve the token
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("/user/authenticate")
    Observable<Authenticate> authenticate(@Field("name") String username, @Field("password") String password);

    /**
     * get call to return all the restaurants
     * @return
     */
    @GET("/restaurants/list")
    Observable<ArrayList<Restaurant>> getRestaurants();

    /**
     * get challenges from the backend
     */
    @GET("/challenges/list")
    Observable<Challenges> getChallenges();

    /**
     * Gets & Posts for Vegagram
     */

    @Multipart
    @POST("/vegagram/")
    Call<Void> uploadVegagramPost(@Part MultipartBody.Part fileToUpload, @Part("isPublic") boolean isPublic, @Part("likes") int likes, @Part("posted") Date date);

    @GET("/vegagram/useruploads")
    Observable<Posts> getUserPosts();

    @GET("/vegagram/uploads")
    Observable<Posts> getAllPosts();

    @GET("uploads/")
    Observable<Image> getImage();
}
