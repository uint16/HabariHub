package com.tanzoft.habarihub.adapters;

import java.util.ArrayList;

import com.tanzoft.habarihub.R;
import com.tanzoft.habarihub.datamodels.NewsSource;
import com.tanzoft.habarihub.extendedviews.HabariHubTextView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class NewsAdapter extends ArrayAdapter<NewsSource> {
	
	/*
	 * Adapts news sources from an array list to a list view*/

	private Context context;
	private ArrayList<NewsSource> sourceList;

	public NewsAdapter(Context context, int resourceId,ArrayList<NewsSource> sourceList) {

		super(context, resourceId, sourceList);
		this.context = context;	
		this.sourceList=sourceList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;

		ViewHolder holder = null;

		if(row == null)
		{
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.source_item, parent, false);
			holder = new ViewHolder();

			holder.sourceName = (HabariHubTextView)row.findViewById(R.id.source_title);
			
			row.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)row.getTag();
		
		}

		NewsSource source = sourceList.get(position);
		holder.sourceName.setText(source.getDisplayName());
		return row;
	}

	private static class ViewHolder{

		/*
		 * A ViewHolder class to enable views recycling.
		 * Improves scrolling performance by avoiding findViewById(int t) 
		 * each time the view goes away from the screen
		 * */
		HabariHubTextView sourceName;
	
	}

}
