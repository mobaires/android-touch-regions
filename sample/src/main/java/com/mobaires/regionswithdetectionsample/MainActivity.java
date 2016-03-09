package com.mobaires.regionswithdetectionsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.mobaires.customviewwithregionsdetect.R;
import com.mobaires.regionswithdetection.HScroll;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		CustomRegionWithDetectionView customRegionWithDetectionView = new CustomRegionWithDetectionView(this);
        customRegionWithDetectionView.setId(R.id.view1);
		customRegionWithDetectionView.setLayoutParams(new ViewGroup.LayoutParams(500, 500));

		FrameLayout container = (FrameLayout) findViewById(R.id.container);
		container.addView(customRegionWithDetectionView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
