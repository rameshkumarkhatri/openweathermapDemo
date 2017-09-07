package com.ramesh.weatherapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ramesh.weatherapp.FrontEngine;
import com.ramesh.weatherapp.R;
import com.ramesh.weatherapp.adapters.AdapterPreviousWeather;
import com.ramesh.weatherapp.models.List;
import com.ramesh.weatherapp.models.ModelDays;
import com.ramesh.weatherapp.models.PreviousDaysWeather;
import com.ramesh.weatherapp.retrofit.CallBackRetrofit;
import com.ramesh.weatherapp.retrofit.HttpResponse;
import com.ramesh.weatherapp.retrofit.RetrofitFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ramesh Kumar on 8/30/17.
 */

public class FragmentPreviousDaysWeather extends BaseFragment {
    @BindView(R.id.tv_no_data)
    TextView tvNoRecords;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    AdapterPreviousWeather adapter;
    private boolean isServiceRunning;
    private PreviousDaysWeather responseModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_weather_previous, container, false);
        ButterKnife.bind(this, view);
        if (mActivity.isLocationAvailable()) requestHttpCall(0, false, null);
        else erorrGPS();
        return view;
    }

    private void erorrGPS() {
        llData.setVisibility(View.GONE);
        tvNoRecords.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);
        tvNoRecords.setText(R.string.please_allow_location_click_here);
    }

    @Override
    public void onResult(int type, HttpResponse o) {
        populateData(responseModel);
        isServiceRunning = false;
    }

    private void populateData(PreviousDaysWeather responseModel) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL, false));
        adapter = new AdapterPreviousWeather(mActivity, getList(responseModel.getList()));
        recyclerView.setAdapter(adapter);
        llData.setVisibility(View.VISIBLE);
        tvNoRecords.setVisibility(View.GONE);
        pb.setVisibility(View.GONE);

    }

    private ArrayList<ModelDays> getList(java.util.List<List> list) {
        ArrayList<ModelDays> listDays = new ArrayList<>();
        Date date = null;
        ModelDays modelDays = null;
        for (List obj : list) {
            String[] dateString = obj.getDtTxt().split(" ");
            if (modelDays == null) {
                modelDays = new ModelDays();
                modelDays.setDate(dateString[0]);
            }
            if (modelDays.getDate().equalsIgnoreCase(dateString[0]))
                modelDays.addModel(obj);
            else {
                listDays.add(modelDays);
                modelDays = new ModelDays();
                modelDays.setDate(dateString[0]);
            }
        }
        return listDays;
    }

    @Override
    public void parseDataInBackground(int type, HttpResponse o) {
        if (o != null && !TextUtils.isEmpty(o.getResponseData())) {
            try {
                JsonObject jsonObject = new Gson().fromJson(o.getResponseData(), JsonObject.class);
                responseModel = new Gson().fromJson(jsonObject, PreviousDaysWeather.class);
            } catch (Exception e) {
                responseModel = null;
            }
        } else {
            responseModel = null;
        }
    }

    @Override
    public void onError(int type, HttpResponse o, Exception e) {
        isServiceRunning = false;

        tvNoRecords.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);
        tvNoRecords.setText(R.string.error_data);
    }

    @Override
    public void noInternetAccess() {
        isServiceRunning = false;
        tvNoRecords.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);
        tvNoRecords.setText(R.string.internet_connection_failed);
    }

    @Override
    public void requestHttpCall(int type, boolean isPost, String... params) {
        if (FrontEngine.LATITUDE > 0) {
            pb.setVisibility(View.VISIBLE);
            tvNoRecords.setVisibility(View.GONE);
            isServiceRunning = true;
            RetrofitFactory.getRetrofitFactory().requestService(RetrofitFactory.GET, new HashMap<>(), RetrofitFactory.MainApiUrl + "forecast?lat=" + FrontEngine.LATITUDE + "&lon=" + FrontEngine.LONGITUDE + "&APPID=" + mActivity.getResources().getString(R.string.weather_app_id), null, new CallBackRetrofit(1, this));
        } else {
            erorrGPS();
        }
    }

    @OnClick(R.id.tv_no_data)
    public void onClickNoData(View view) {
        if (mActivity.isLocationAvailable())
            requestHttpCall(0, false, null);
    }
}
