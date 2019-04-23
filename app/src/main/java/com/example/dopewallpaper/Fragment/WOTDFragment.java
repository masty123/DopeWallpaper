package com.example.dopewallpaper.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dopewallpaper.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WOTDFragment extends Fragment {

    private static WOTDFragment    INSTANCE=null;


    public WOTDFragment() {
        // Required empty public constructor
    }

    public static WOTDFragment getInstance(){
        if (INSTANCE == null)
                INSTANCE = new WOTDFragment();
        return INSTANCE;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wotd, container, false);
    }

}
