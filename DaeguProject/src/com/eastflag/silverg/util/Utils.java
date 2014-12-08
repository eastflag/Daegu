package com.eastflag.silver.util;

import android.app.Activity;
import android.util.DisplayMetrics;

public class Utils {
	public static int getDisplayWidth(Activity act) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		//int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		
		return width;
	}
}
