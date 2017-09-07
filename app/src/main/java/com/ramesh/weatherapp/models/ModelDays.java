package com.ramesh.weatherapp.models;

import java.util.ArrayList;

/**
 * Created by Ramesh Kumar on 9/7/17.
 */

public class ModelDays {
    String date = "";
    ArrayList<List> list = new ArrayList<>();

    public String getDate() {
        return date;
    }

    public ArrayList<List> getList() {
        return list;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addModel(List obj) {
        list.add(obj);
    }
}
