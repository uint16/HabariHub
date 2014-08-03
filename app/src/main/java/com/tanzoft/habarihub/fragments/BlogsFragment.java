package com.tanzoft.habarihub.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tanzoft.habarihub.HabariHubMainActivity;
import com.tanzoft.habarihub.R;
import com.tanzoft.habarihub.adapters.NewsAdapter;
import com.tanzoft.habarihub.database.NewsSourceDatabaseOpenHelper;
import com.tanzoft.habarihub.datamodels.NewsSource;

import java.util.ArrayList;

public class BlogsFragment extends HabariHubFragmentHandler {

    private final String LOG_TAG = BlogsFragment.class.getSimpleName();
    private ArrayList<NewsSource> newsSource;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ((HabariHubMainActivity) getActivity()).getSupportActionBar().setTitle("Blogs and Sites");

        Log.i(LOG_TAG, "Fragment Size: " + newsSource.size());

        View view = inflater.inflate(R.layout.source_list, container, false);

        listView = (ListView) view.findViewById(R.id.source_list);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Runnable() {

            @Override
            public void run() {

                newsSource = new ArrayList<NewsSource>();
                NewsSourceDatabaseOpenHelper dbHelper = new NewsSourceDatabaseOpenHelper(getActivity());
                newsSource = dbHelper.getAllBlogs();

            }
        }.run();


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        new Runnable() {

            @Override
            public void run() {

                newsSource = new ArrayList<NewsSource>();
                NewsSourceDatabaseOpenHelper dbHelper = new NewsSourceDatabaseOpenHelper(getActivity());
                newsSource = dbHelper.getAllBlogs();

            }
        }.run();
        adapter = new NewsAdapter(getActivity(), R.layout.source_list_item, newsSource);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
