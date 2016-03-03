package com.mobaires.regionswithdetection;

import android.view.View;

public class PixelConverter {

	public static float  getPx(float dp,View view){       
	    float scale = view.getResources().getDisplayMetrics().density;
	    return dp * scale;
	}
	
}
