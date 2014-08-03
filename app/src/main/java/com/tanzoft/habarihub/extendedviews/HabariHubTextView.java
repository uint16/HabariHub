package com.tanzoft.habarihub.extendedviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class HabariHubTextView extends TextView {

	/*
	 * Extending the text view class to provide ubuntu font*/
	public HabariHubTextView(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
		setCustomFont();

	}



	public HabariHubTextView(Context context, AttributeSet attrs) {

		super(context, attrs);

		setCustomFont();
	}

	public HabariHubTextView(Context context) {
		super(context);
		setCustomFont();

	}

	protected void setCustomFont() {

		Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/ubuntumonor.ttf");
		setTypeface(tf);


	}

}
