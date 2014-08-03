package com.tanzoft.habarihub.fragments;

import java.util.ArrayList;

import com.tanzoft.habarihub.R;
import com.tanzoft.habarihub.adapters.NewsAdapter;
import com.tanzoft.habarihub.database.NewsSourceDatabaseOpenHelper;
import com.tanzoft.habarihub.datamodels.NewsSource;
import com.tanzoft.habarihub.HabariHubMainActivity;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class NewsPapersFragment extends HabariHubFragmentHandler {

		NewsAdapter adapter;
		ArrayList<NewsSource> newsSource;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {


			((HabariHubMainActivity)getActivity()).getSupportActionBar().setTitle("NewsPapers");

			Log.i("BlogsFra on create view ", " "+newsSource.size());

			View view = inflater.inflate(R.layout.source_list, container, false);
			adapter = new NewsAdapter(getActivity(), R.layout.source_item,newsSource);

			ListView listView=(ListView) view.findViewById(R.id.source_list);
			listView.setAdapter(adapter);

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
					newsSource=dbHelper.getAllNews();

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


