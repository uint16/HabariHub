package com.tanzoft.habarihub.adapters;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.tanzoft.habarihub.HabariHubMainActivity;
import com.tanzoft.habarihub.R;
import com.tanzoft.habarihub.extendedviews.HabariHubTextView;

public class HabariHubDrawerAdapter extends SimpleAdapter {
	
	Context context;
	
	/*
	 * This one puts the options on the drawer layout*/

	public HabariHubDrawerAdapter(HabariHubMainActivity context,List<HashMap<String, String>> mList, int drawerLayout,String[] from, int[] to) {
		super(context, mList, drawerLayout, from, to);
		this.context=context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		
		//Instatiating a view that will be returned
		View v = super.getView(position, convertView, parent);
		//referencing the text view that hold the option name
		HabariHubTextView tv = (HabariHubTextView) v.findViewById(R.id.drawerOption);
		//setting the text color to white
		tv.setTextColor(context.getResources().getColor(android.R.color.black));

		return v;
	}



}
