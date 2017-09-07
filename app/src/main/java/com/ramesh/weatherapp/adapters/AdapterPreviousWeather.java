package com.ramesh.weatherapp.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ramesh.weatherapp.FrontEngine;
import com.ramesh.weatherapp.R;
import com.ramesh.weatherapp.models.ModelDays;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Ramesh Kumar com on 9/7/2017.
 */

public class AdapterPreviousWeather extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_DATA = 0;
    public final int TYPE_LOAD = 2;
    private ArrayList<ModelDays> arrayList;
    private Activity activity;
    private OnRecyclerItemClick onItemClick;

    public AdapterPreviousWeather(Activity activity, ArrayList<ModelDays> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    public void setOnItemClick(OnRecyclerItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = null;

        v = LayoutInflater.from(activity).inflate(R.layout.row_parent_weather, parent, false);


        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        final MyViewHolder holder = (MyViewHolder) viewHolder;

        ModelDays item = arrayList.get(position);
        holder.tvName.setText(FrontEngine.getInstance().dateFormat(item.getDate(), "yyyy-MM-dd", "dd MMM, yyyy"));

        holder.rv.setAdapter(new AdapterRestaurantMenuItem(activity, item.getList(), position, onItemClick));


    }

    @Override
    public int getItemViewType(int position) {

        if (arrayList.get(position) != null && arrayList.size() > 0) {
            return TYPE_DATA;
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getItemCount() {

        return arrayList == null ? 0 : arrayList.size();
    }


    public interface OnRecyclerItemClick {
        public void onItemclicked(int position, View view, Object object);

        public void onLocationClicked(int position, View view, Object object);
    }
    //baseview holder close..

    //base view holder start
    class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView rv;
        private TextView tvName;

        MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            rv = (RecyclerView) view.findViewById(R.id.recycler_view);
            rv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        }


    }

    public class AdapterRestaurantMenuItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public final int TYPE_DATA = 0;
        public final int TYPE_LOAD = 2;
        private final OnRecyclerItemClick onItemClick;
        int position;
        private ArrayList<com.ramesh.weatherapp.models.List> arrayList;
        private Activity activity;

        public AdapterRestaurantMenuItem(Activity activity, ArrayList<com.ramesh.weatherapp.models.List> arrayList, int position, OnRecyclerItemClick onItemClick) {
            //Passing UserName in constructor for showing username on Single Genre Activity
            this.arrayList = arrayList;
            this.activity = activity;
            this.position = position;
            this.onItemClick = onItemClick;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = null;
            if (viewType == TYPE_DATA) {// create a new view
                v = LayoutInflater.from(activity).inflate(R.layout.row_forcast, parent, false);
            }
            // set the view's size, margins, paddings and layout parameters
            MyViewHolderItem vh = new MyViewHolderItem(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

            if (getItemViewType(position) == TYPE_DATA) {

                final MyViewHolderItem holder = (MyViewHolderItem) viewHolder;

                com.ramesh.weatherapp.models.List item = arrayList.get(position);

                DecimalFormat df = new DecimalFormat("#.##");
                Double degree = Double.valueOf(df.format((item.getMain().getTempMin() - 273.15)));

                holder.tvTempMin.setText("Min. " + String.valueOf(degree) + " °C");
                degree = Double.valueOf(df.format((item.getMain().getTempMax() - 273.15)));
                holder.tvTempMax.setText("Max. " + String.valueOf(degree) + " °C");

                holder.tvTime.setText(FrontEngine.getInstance().dateFormat(item.getDtTxt(), "yyyy-MM-DD HH:mm:ss", "hh:mm a"));
                if (item.getWeather() != null && item.getWeather().size() > 0)
                    holder.tvType.setText(item.getWeather().get(0).getDescription());
                else holder.tvType.setText("--");
                if (onItemClick != null)
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onItemClick.onItemclicked(position, view, arrayList.get(position));
                        }
                    });

            }

        }

        @Override
        public int getItemViewType(int position) {

            if (arrayList.get(position) != null && arrayList.size() > 0) {
                return TYPE_DATA;
            } else {
                return 0;
            }
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public int getItemCount() {

            return arrayList == null ? 0 : arrayList.size();
        }

        //base view holder start
        class MyViewHolderItem extends RecyclerView.ViewHolder {

            private TextView tvTime, tvType, tvTempMin, tvTempMax;

            MyViewHolderItem(View view) {
                super(view);
                tvTime = (TextView) view.findViewById(R.id.tv_time);
                tvType = (TextView) view.findViewById(R.id.tv_type);
                tvTempMin = (TextView) view.findViewById(R.id.tv_temperature_min);
                tvTempMax = (TextView) view.findViewById(R.id.tv_temperature_max);

            }


        }
        //baseview holder close..


    }

}