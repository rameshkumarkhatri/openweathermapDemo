package com.ramesh.weatherapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.google.gson.JsonObject;
import com.ramesh.weatherapp.MainActivity;
import com.ramesh.weatherapp.retrofit.HttpResponse;
import com.ramesh.weatherapp.retrofit.ServiceResponse;

import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by Personal on 8/30/17.
 */

public class BaseFragment extends Fragment implements ServiceResponse {

    MainActivity mActivity;
    View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }

    @Override
    public void onResult(int type, HttpResponse o) {

    }

    @Override
    public void parseDataInBackground(int type, HttpResponse o) {

    }

    @Override
    public void onError(int type, HttpResponse o, Exception e) throws JSONException {

    }

    @Override
    public void noInternetAccess() {

    }

    @Override
    public void requestHttpCall(int type, boolean isPost, String... params) {
    }
}
