package com.ramesh.weatherapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.ramesh.weatherapp.FrontEngine;
import com.ramesh.weatherapp.R;
import com.ramesh.weatherapp.retrofit.CallBackRetrofit;
import com.ramesh.weatherapp.retrofit.HttpResponse;
import com.ramesh.weatherapp.retrofit.RetrofitFactory;

import org.json.JSONException;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ramesh Kumar on 8/30/17.
 */

public class FragmentWeather extends BaseFragment {

    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindView(R.id.tv_temperature)
    TextView tvTemprature;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_description_heading)
    TextView tvDescription;
    @BindView(R.id.tv_pressure)
    TextView tvPressure;
    @BindView(R.id.tv_humidity)
    TextView tvHumidity;
    @BindView(R.id.tv_temp_min)
    TextView tvTempMin;
    @BindView(R.id.tv_temp_max)
    TextView tvTempMax;
    @BindView(R.id.tv_sea_level)
    TextView tvSeaLevel;
    @BindView(R.id.tv_grnd_level)
    TextView tvGroundLevel;
    @BindView(R.id.tv_no_data)
    TextView tvNoRecords;
    @BindView(R.id.pb)
    TextView pb;
    private boolean isServiceRunning;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, null);
        ButterKnife.bind(view);  if (mActivity.isLocationAvailable()) requestHttpCall(0, false, null);
        return view;
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
            if (FrontEngine.LATITUDE > 0) {

                    pb.setVisibility(View.VISIBLE);
                isServiceRunning = true;
                RetrofitFactory.getRetrofitFactory().requestService(RetrofitFactory.GET, new HashMap<>(), RetrofitFactory.MainApiUrl + "weather?lat="+FrontEngine.LATITUDE+"&lon="+FrontEngine.LONGITUDE+"APPID="+mActivity.getResources().getString(R.string.weather_app_id), null, new CallBackRetrofit(1, this));
            } else {

                    erorrGPS();
            }
    }

    private void erorrGPS() {
        tvNoRecords.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);
        tvNoRecords.setText(R.string.please_allow_location_click_here);
    }
}
