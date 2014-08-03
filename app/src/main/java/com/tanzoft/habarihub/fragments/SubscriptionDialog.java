package com.tanzoft.habarihub.fragments;

import com.tanzoft.habarihub.HabariHubApplication;
import com.tanzoft.habarihub.R;
import com.tanzoft.habarihub.datamodels.NewsSource;
import com.tanzoft.habarihub.extendedviews.HabariHubEditText;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SubscriptionDialog extends DialogFragment {

	private String source;
	private String url;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View view=inflater.inflate(R.layout.subscription_dialog, null);
		final HabariHubEditText sourceName = (HabariHubEditText)view.findViewById(R.id.display_name);
		final HabariHubEditText sourceUrl = (HabariHubEditText) view.findViewById(R.id.source_address);

		builder.setView(view)
		.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				try{

					source =sourceName.getText().toString();
					url=sourceUrl.getText().toString();
					final NewsSource newsSource = new NewsSource(source,url);
					

					//Toast.makeText(getActivity(), source + url, Toast.LENGTH_SHORT).show();

					new Runnable() {

						@Override
						public void run() {

							

							HabariHubApplication.dbHelper.openDatabase();

							HabariHubApplication.dbHelper.addBlog(newsSource);
							Log.i("runnable  ","added");

							HabariHubApplication.dbHelper.close();
						}

					}.run();

				}catch(Exception e){

					Toast.makeText(getActivity(), "No Source Was Added", Toast.LENGTH_SHORT).show();

					

				}

			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				SubscriptionDialog.this.getDialog().cancel();
			}
		});

		

		
		return builder.create();
	}




}
