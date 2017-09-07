package com.ramesh.weatherapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.ramesh.weatherapp.models.ModelWeather;
import com.ramesh.weatherapp.retrofit.CallBackRetrofit;
import com.ramesh.weatherapp.retrofit.HttpResponse;
import com.ramesh.weatherapp.retrofit.RetrofitFactory;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.level;

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
    ProgressBar pb;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    private boolean isServiceRunning;
    private ModelWeather responseModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this,view);
        if (mActivity.isLocationAvailable()) requestHttpCall(0, false, null);
        else erorrGPS();
        return view;
    }

    /**
     * this will show the error of GPS
     */
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

    private void populateData(ModelWeather responseModel) {
        tvCityName.setText(responseModel.getName() + "");
        if (responseModel.getWeather().size() > 0 && responseModel.getWeather().get(0) != null)
            tvDescription.setText(mActivity.getString(R.string.description));
        tvType.setText(" "+responseModel.getWeather().get(0).getDescription() + "");
//        tvGroundLevel.setText(mActivity.getString(R.string.ground_level)+" "+responseModel.getMain().getGrndLevel() + "");
        tvHumidity.setText(mActivity.getString(R.string.humidity)+" "+responseModel.getMain().getHumidity() + "");
        tvPressure.setText(mActivity.getString(R.string.pressure)+" "+responseModel.getMain().getPressure() + "");
//        tvSeaLevel.setText(mActivity.getString(R.string.sea_level)+" "+responseModel.getMain().getSeaLevel() + "");
        tvTempMax.setText(mActivity.getString(R.string.max_temp)+" "+(responseModel.getMain().getTempMax()-273.15) + " °C");
        tvTempMin.setText(mActivity.getString(R.string.min_temp)+" "+(responseModel.getMain().getTempMin()-273.15) + " °C ");
        tvTemprature.setText((responseModel.getMain().getTemp()-273.15) + " °C");
        llData.setVisibility(View.VISIBLE);
        tvNoRecords.setVisibility(View.GONE);
        pb.setVisibility(View.GONE);

    }

    @Override
    public void parseDataInBackground(int type, HttpResponse o) {
        if (o != null && !TextUtils.isEmpty(o.getResponseData())) {
            try {
                JsonObject jsonObject = new Gson().fromJson(o.getResponseData(), JsonObject.class);
                responseModel = new Gson().fromJson(jsonObject, ModelWeather.class);
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
            RetrofitFactory.getRetrofitFactory().requestService(RetrofitFactory.GET, new HashMap<>(), RetrofitFactory.MainApiUrl + "weather?lat=" + FrontEngine.LATITUDE + "&lon=" + FrontEngine.LONGITUDE + "&APPID=" + mActivity.getResources().getString(R.string.weather_app_id), null, new CallBackRetrofit(1, this));
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
