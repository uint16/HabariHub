package com.tanzoft.habarihub.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tanzoft.habarihub.R;
import com.tanzoft.habarihub.fragments.HabariHubFragmentHandler;

/**
 * Created by damas on 7/31/14.
 */
public class About extends HabariHubFragmentHandler {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.about_us_fragment, container, false);
    }
}
