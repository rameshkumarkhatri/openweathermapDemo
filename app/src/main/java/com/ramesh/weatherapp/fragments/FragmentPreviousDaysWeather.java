package com.ramesh.weatherapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramesh.weatherapp.R;

/**
 * Created by Ramesh Kumar on 8/30/17.
 */

public class FragmentPreviousDaysWeather extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, null);
        return view;
    }
}
