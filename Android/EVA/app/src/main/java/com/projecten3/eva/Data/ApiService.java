package com.projecten3.eva.Data;

import com.projecten3.eva.Model.Authenticate;
import com.projecten3.eva.Model.Challenges;
import com.projecten3.eva.Model.Restaurant;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
}
