package com.tanzoft.habarihub.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tanzoft.habarihub.HabariHubMainActivity;
import com.tanzoft.habarihub.R;
import com.tanzoft.habarihub.extendedviews.HabariHubTextView;

public class CreditsFragment extends HabariHubFragmentHandler {

	HabariHubTextView tv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		View view =inflater.inflate(R.layout.credits_fragment,container, false);
		tv =(HabariHubTextView)view.findViewById(R.id.code_credits);
		((HabariHubMainActivity)getActivity()).getSupportActionBar().setTitle("Credits");
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
