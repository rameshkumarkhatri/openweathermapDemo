package com.ramesh.weatherapp.retrofit;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonElement;

import org.json.JSONException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ramesh Kumar on 8/30/17.
 */

public class CallBackRetrofit implements Callback<JsonElement> {
    ServiceResponse response;
    int type;

    public CallBackRetrofit(int type, ServiceResponse response) {
        this.response = response;
        this.type = type;
    }

    @Override
    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
      Log.e("RESPONSE", response.toString());
        if (response != null) {
            HttpResponse httpResponse = new HttpResponse();
            if (response.body() != null)
                httpResponse.setResponseData(response.body().toString());
            else try {
                httpResponse.setResponsMessage(response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            httpResponse.setResponseCode(response.code());
            if (response.isSuccessful()) {
                asyncTask(httpResponse);

            } else try {
                if(response!=null)  this.response.onError(type, httpResponse, null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<JsonElement> call, Throwable t) {
        Log.e("ERROR", t.getLocalizedMessage() + "");
        t.printStackTrace();

        if(call.isCanceled()){
//            try {
//                String response = "request_cancel";
//                HttpResponse httpResponse = new HttpResponse();
//                httpResponse.setResponseData(response);
//                if(response!=null)this.response.onError(type, httpResponse, new Exception());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }else{
            try {
                if(response!=null) this.response.onError(type, null, new Exception());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    public void asyncTask(final HttpResponse res) {
        AsyncTask task = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                if(response!=null)
                    response.parseDataInBackground(type, res);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (res.getResponseCode() == 200) {
                    if(response!=null) response.onResult(type, res);
                } else try {
                    if(response!=null) response.onError(type, res, null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
