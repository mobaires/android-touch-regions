package com.mobaires.regionswithdetectionsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.mobaires.customviewwithregionsdetect.R;
import com.mobaires.regionswithdetection.HScroll;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		CustomRegionWithDetectionView customRegionWithDetectionView = new CustomRegionWithDetectionView(this);
		customRegionWithDetectionView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		HScroll hScroll =(HScroll)findViewById(R.id.hScroll);
		hScroll.addView(customRegionWithDetectionView);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
