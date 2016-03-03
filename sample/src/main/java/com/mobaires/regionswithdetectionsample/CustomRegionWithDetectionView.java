package com.mobaires.regionswithdetectionsample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.mobaires.customviewwithregionsdetect.R;
import com.mobaires.regionswithdetection.CoordinatesRegion;
import com.mobaires.regionswithdetection.PartWithStatus;
import com.mobaires.regionswithdetection.RegionsWithDetectionView;

public class CustomRegionWithDetectionView extends
		RegionsWithDetectionView<Boolean> {
	private Bitmap bitmap = null;

	public CustomRegionWithDetectionView(Context context) {
		super(context);
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flag2);

	}

	@Override
	public Bitmap getWireframe() {
		if (bitmap == null)
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.flag2);

		return bitmap;
	}

	@Override
	public Map<PartWithStatus<Boolean>, Collection<CoordinatesRegion>> getRegions() {
		HashMap<PartWithStatus<Boolean>, Collection<CoordinatesRegion>> partsWithStatusStaticMap = new HashMap<PartWithStatus<Boolean>, Collection<CoordinatesRegion>>();
		CoordinatesRegion coordinatesRegion1 = new CoordinatesRegion(
				new float[] { 32, 33, 200, 150, 32, 262 });
		CoordinatesRegion coordinatesRegion2 = new CoordinatesRegion(
				new float[] { 32, 262, 200, 150, 370, 262 });
		CoordinatesRegion coordinatesRegion3 = new CoordinatesRegion(
				new float[] { 32, 33, 370, 31, 200, 150 });
		CoordinatesRegion coordinatesRegion4 = new CoordinatesRegion(
				new float[] { 200, 150, 370, 31, 370, 262 });
		ArrayList<CoordinatesRegion> coordinatesRegionCollection1 = new ArrayList<CoordinatesRegion>();
		coordinatesRegionCollection1.add(coordinatesRegion1);
		ArrayList<CoordinatesRegion> coordinatesRegionCollection2 = new ArrayList<CoordinatesRegion>();
		coordinatesRegionCollection2.add(coordinatesRegion2);
		ArrayList<CoordinatesRegion> coordinatesRegionCollection3 = new ArrayList<CoordinatesRegion>();
		coordinatesRegionCollection3.add(coordinatesRegion3);
		coordinatesRegionCollection3.add(coordinatesRegion4);

		partsWithStatusStaticMap.put(new CustomPartWithStatus(),
				coordinatesRegionCollection1);
		partsWithStatusStaticMap.put(new CustomPartWithStatus(),
				coordinatesRegionCollection2);
		partsWithStatusStaticMap.put(new CustomPartWithStatus(),
				coordinatesRegionCollection3);
		return partsWithStatusStaticMap;
	}

	@Override
	public ArrayList<Boolean> getAvailableStatus() {
		ArrayList<Boolean> availableStatus = new ArrayList<Boolean>();
		availableStatus.add(false);
		availableStatus.add(true);
		return availableStatus;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
	}

}
