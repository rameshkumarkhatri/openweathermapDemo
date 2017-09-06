package com.ramesh.weatherapp.retrofit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by Ramesh Kumar on 8/30/17.
 */
public interface HttpBinService {


    @POST("{url}")

    Call<JsonElement> postData(@Path(value = "url", encoded = true) String url, @HeaderMap HashMap<String, String> header, @Body JsonObject jsonObject);


    @PUT
    Call<JsonElement> putData(@Url String url, @HeaderMap HashMap<String, String> header, @Body JsonObject jsonObject);

    @GET
    Call<JsonElement> getData(@Url String url, @HeaderMap HashMap<String, String> header);


    //@DELETE
    @HTTP(method = "DELETE", /*path = "post/delete",*/ hasBody = true)
    Call<JsonElement> deleteData(@Url String url, @HeaderMap HashMap<String, String> header, @Body JsonObject jsonObject);


}
