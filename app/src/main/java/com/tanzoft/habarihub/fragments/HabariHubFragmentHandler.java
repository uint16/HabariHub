package com.tanzoft.habarihub.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tanzoft.habarihub.HabariHubMainActivity;
import com.tanzoft.habarihub.R;
import com.tanzoft.habarihub.adapters.NewsAdapter;

public abstract class HabariHubFragmentHandler extends Fragment {


    protected NewsAdapter adapter;

    public static void startBackStackedFragment(HabariHubFragmentHandler fragment, HabariHubMainActivity activity) {

        // Creating a Bundle object
        Bundle data = new Bundle();

        fragment.setArguments(data);

        // Getting reference to the FragmentManager
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        // Creating a fragment transaction
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.addToBackStack(null);
        // Adding a fragment to the fragment transaction
        ft.replace(R.id.content_frame, fragment);

        fragmentManager.executePendingTransactions();
        // Committing the transaction
        ft.commit();

    }

    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
