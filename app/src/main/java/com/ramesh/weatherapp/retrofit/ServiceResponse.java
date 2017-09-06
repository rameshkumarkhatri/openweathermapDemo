package com.ramesh.weatherapp.retrofit;


import org.json.JSONException;

/**
 * Created by Ramesh Kumar on 8/30/17.
 */


public interface ServiceResponse {
    void onResult(int type, HttpResponse o);
    void parseDataInBackground(int type, HttpResponse o);
    void onError(int type, HttpResponse o, Exception e) throws JSONException;
    void noInternetAccess();
    void requestHttpCall(int type, boolean isPost, String... params);


}
