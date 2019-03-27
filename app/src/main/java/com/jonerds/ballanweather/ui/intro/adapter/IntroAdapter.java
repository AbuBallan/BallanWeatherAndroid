package com.jonerds.ballanweather.ui.intro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jonerds.ballanweather.R;
import com.jonerds.ballanweather.ui.intro.IntroMvpView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class IntroAdapter extends PagerAdapter {

    private static final int layouts [] = {
            R.layout.layout_intro_welcome,
            R.layout.layout_intro_permissions,
            R.layout.layout_intro_location
    };

    private Context mContext;
    private IntroMvpView mMvpView;

    public IntroAdapter(Context context) {
        mContext = context;
    }

    public void setMvpView(IntroMvpView mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(layouts[position], collection, false);

        if (position == 0){
            layout.findViewById(R.id.lets_go_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMvpView != null)
                        mMvpView.onNext();
                }
            });
        }else if (position == 2){
            layout.findViewById(R.id.automatic_location_group).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMvpView != null)
                        mMvpView.onSelectAutomaticLocation();
                }
            });

            layout.findViewById(R.id.manual_location_group).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMvpView != null)
                        mMvpView.onSelectManualLocation();
                }
            });
        }

        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
