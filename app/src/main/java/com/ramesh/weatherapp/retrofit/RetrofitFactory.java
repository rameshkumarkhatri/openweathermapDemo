package com.ramesh.weatherapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ramesh Kumar on8/30/17.
 */

public class RetrofitFactory {
    public static final int POST = 1, GET = 2, PUT = 3, DELETE = 4;
    public static String MainApiUrl = "http://api.openweathermap.org/data/2.5/";
    private static RetrofitFactory retrofitFactory;
    Retrofit retrofit;
    Call<JsonElement> j = null;
    HttpBinService service;

    public static RetrofitFactory getRetrofitFactory() {
        if (retrofitFactory == null)
            retrofitFactory = new RetrofitFactory();
        return retrofitFactory;
    }

//    public void requestService(int methodType, String[] map, String postURL, JsonObject jsonElement, CallBackRetrofit callback) {
//        HttpBinService service = getServiceInstance(postURL+"/");
//        Call<JsonElement> j = null;
//        if (methodType == POST)
//           j= service.postData( "", jsonElement);
//        else if (methodType == PUT)
//            j=   service.putData( "", jsonElement);
//        else if (methodType == GET)
//            j=  service.getData_(map[0], map[1], map[2],postURL);
//        j.enqueue(callback);
//    }

    public void requestService(int methodType, HashMap map, String postURL, JsonObject jsonElement, CallBackRetrofit callback) {
        HttpBinService service = getServiceInstance(postURL + "/");

        if (methodType == POST)
            j = service.postData(postURL, map, jsonElement);
        else if (methodType == PUT)
            j = service.putData(postURL, map, jsonElement);
        else if (methodType == GET)
            j = service.getData(postURL, map);
        else if (methodType == DELETE)
            j = service.deleteData(postURL, map, jsonElement);

        j.enqueue(callback);
    }

    public void cancelRequest() {
        j.cancel();
    }

    public boolean isExceutedCall() {
        if (j.isExecuted()) {
            return true;
        } else {
            return false;
        }
    }

    public HttpBinService getServiceInstance(String postURL) {
//        if (retrofit == null) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
        okHttpClient.connectTimeout(2, TimeUnit.MINUTES);
        okHttpClient.readTimeout(2, TimeUnit.MINUTES);
        okHttpClient.writeTimeout(2, TimeUnit.MINUTES);
        okHttpClient.followRedirects(false); // on instruction of Usaama
        okHttpClient.followSslRedirects(false); // on instruction of Usaama
        OkHttpClient okHttpClient_ = okHttpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(MainApiUrl)
                .client(okHttpClient_)
//                .addCallAdapterFactory(new ErrorHandlingCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(HttpBinService.class);
//        }


        return service;
    }
}
