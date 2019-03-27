package com.jonerds.ballanweather.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jonerds.ballanweather.R;
import com.jonerds.ballanweather.data.model.HourWeather;
import com.jonerds.ballanweather.databinding.ItemHourlyForecastBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class HourlyForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<HourWeather> mHourWeatherList;

    private LayoutInflater layoutInflater;

    public HourlyForecastAdapter(Context context) {
        mContext = context;
    }

    public void setHourWeatherList(List<HourWeather> hourWeatherList) {
        mHourWeatherList = hourWeatherList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(mContext);
        }
        return new HourlyForecastViewHolder((ItemHourlyForecastBinding) DataBindingUtil.inflate(layoutInflater ,R.layout.item_hourly_forecast, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        HourlyForecastViewHolder viewHolder = (HourlyForecastViewHolder) holder;
        HourWeather hourWeather = mHourWeatherList.get(position);

        viewHolder.mBinding.setHourWeather(hourWeather);

    }

    @Override
    public int getItemCount() {
        return (mHourWeatherList != null)? mHourWeatherList.size() : 0;
    }

    static class HourlyForecastViewHolder extends RecyclerView.ViewHolder{

        ItemHourlyForecastBinding mBinding;

        public HourlyForecastViewHolder(@NonNull ItemHourlyForecastBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

        }
    }
}
