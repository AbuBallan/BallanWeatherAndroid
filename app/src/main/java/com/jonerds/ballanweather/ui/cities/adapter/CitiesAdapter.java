package com.jonerds.ballanweather.ui.cities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jonerds.ballanweather.R;
import com.jonerds.ballanweather.data.model.City;
import com.jonerds.ballanweather.databinding.ItemCityBinding;
import com.jonerds.ballanweather.ui.cities.CitiesMvpView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CitiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater layoutInflater;

    private Context mContext;

    private List<City> mCityList;

    private CitiesMvpView mMvpView;

    public CitiesAdapter(Context context) {
        mContext = context;
    }

    public void setCityList(List<City> cityList) {
        mCityList = cityList;
        notifyDataSetChanged();
    }

    public void setMvpView(CitiesMvpView mvpView) {
        mMvpView = mvpView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(mContext);
        return new CityViewHolder((ItemCityBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_city, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        CityViewHolder viewHolder = (CityViewHolder) holder;
        City city = mCityList.get(position);

        viewHolder.mBinding.setCity(city);

        if (mMvpView != null) viewHolder.mBinding.setView(mMvpView);

    }

    @Override
    public int getItemCount() {
        return (mCityList != null)? mCityList.size() : 0;
    }

    class CityViewHolder extends RecyclerView.ViewHolder{

        ItemCityBinding mBinding;

        public CityViewHolder(@NonNull ItemCityBinding binding) {
            super(binding.getRoot());

            mBinding = binding;

        }
    }
}
