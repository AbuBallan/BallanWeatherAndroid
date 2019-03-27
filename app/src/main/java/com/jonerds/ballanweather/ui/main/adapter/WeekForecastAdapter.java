package com.jonerds.ballanweather.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jonerds.ballanweather.R;
import com.jonerds.ballanweather.data.model.DayWeather;
import com.jonerds.ballanweather.databinding.ItemWeekForecastBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class WeekForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<DayWeather> mDayWeatherList;

    private LayoutInflater layoutInflater;

    public WeekForecastAdapter(Context context) {
        mContext = context;
    }

    public void setDayWeatherList(List<DayWeather> dayWeatherList) {
        mDayWeatherList = dayWeatherList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(mContext);
        }
        return new WeekForecastViewHolder((ItemWeekForecastBinding) DataBindingUtil.inflate(layoutInflater ,R.layout.item_week_forecast, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        WeekForecastViewHolder viewHolder =  (WeekForecastViewHolder) holder;
        DayWeather dayWeather = mDayWeatherList.get(position);

        if (viewHolder.mBinding.expandForecastConstraintLayout.isExpanded()){
            viewHolder.mBinding.expandedForecastImageView.setImageResource(R.drawable.ic_expand_less);
        }else{
            viewHolder.mBinding.expandedForecastImageView.setImageResource(R.drawable.ic_expand_more);
        }

        viewHolder.mBinding.setDayWeather(dayWeather);

    }

    @Override
    public int getItemCount() {
        return (mDayWeatherList != null)? mDayWeatherList.size():0;
    }

     class WeekForecastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemWeekForecastBinding mBinding;

        public WeekForecastViewHolder(@NonNull ItemWeekForecastBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            itemView.setOnClickListener(this);
        }

         @Override
         public void onClick(View v) {
             if (mBinding.expandForecastConstraintLayout.isExpanded()) {
                 mBinding.expandForecastConstraintLayout.setExpanded(false);
                 mBinding.expandForecastConstraintLayout.toggle();
                 mBinding.expandedForecastImageView.setImageResource(R.drawable.ic_expand_more);
             } else {
                 mBinding.expandForecastConstraintLayout.setExpanded(true);
                 mBinding.expandForecastConstraintLayout.toggle();
                 mBinding.expandedForecastImageView.setImageResource(R.drawable.ic_expand_less);
             }
         }
     }
}
