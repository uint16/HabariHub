package com.tanzoft.habarihub.extendedviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class HabariHubEditText extends EditText {

	public HabariHubEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setCustomFont();

	}

	public HabariHubEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		setCustomFont();

	}

	public HabariHubEditText(Context context) {
		super(context);
		setCustomFont();

	}

	protected void setCustomFont() {

		Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/ubuntumonor.ttf");
		setTypeface(tf);


	}
}
