package com.example.dopewallpaper.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dopewallpaper.Fragment.CategoryFragment;
import com.example.dopewallpaper.Fragment.RecentFragment;
import com.example.dopewallpaper.Fragment.TrendingFragment;

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private Context context;

    public MyFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return CategoryFragment.getInstance();

        }
        else if (position == 1){
            return TrendingFragment.getInstance();

        }

//        else if (position == 2){
//            return RecentFragment.getInstance(context);
//
//        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Category";
            case 1:
                return "Trending";
//            case 2:
//                return "Recents";
        }
        return "";
    }
}
