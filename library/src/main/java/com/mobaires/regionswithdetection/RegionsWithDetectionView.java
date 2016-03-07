package com.mobaires.regionswithdetection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

public abstract class RegionsWithDetectionView<S> extends ViewGroup {

	private static final float MIN_DP = 48; // review this (master->branch2)
	private static float factorEscalaLookupBitmap = 0.1f;
	private static float factorForScreen = 1.0f; // This thing enlarges or reduces the bitmap!
	private Bitmap lookupBitmap = Bitmap.createBitmap(300, 300,
			Bitmap.Config.ALPHA_8);
	private float maxWidth = 0.0f;
	private float maxHeight = 0.0f;
	private Collection<PartWithStatus<S>> partsArray = new ArrayList<PartWithStatus<S>>();
	private Map<Integer, PartWithStatus<S>> partsByColor = new HashMap<Integer, PartWithStatus<S>>();

	private Bitmap foregroundBitmap = null;

	public RegionsWithDetectionView(Context context) {
		super(context);
		init();
		setWillNotDraw(false);
	}

	public RegionsWithDetectionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		setWillNotDraw(false);
	}

	public RegionsWithDetectionView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
		setWillNotDraw(false);
	}

	public abstract Bitmap getWireframe();

	public abstract Map<PartWithStatus<S>, Collection<CoordinatesRegion>> getRegions();

	public abstract ArrayList<S> getAvailableStatus();

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN) {
			return false;
		}

		// Let the GestureDetector interpret this event

		float x = event.getX();
		float y = event.getY();
		
		if ((x < ((1 / factorEscalaLookupBitmap) * factorForScreen * lookupBitmap
				.getWidth()))
				&& (y < ((1 / factorEscalaLookupBitmap) * factorForScreen * lookupBitmap
						.getHeight())) && (x >= 0) && (y >= 0)) {

			int pathIndex = lookupBitmap.getPixel(
					(int) ((factorEscalaLookupBitmap / factorForScreen) * x),
					(int) ((factorEscalaLookupBitmap / factorForScreen) * y));

			if ((pathIndex != Color.BLACK)
					&& (partsByColor.containsKey(pathIndex))) {
				PartWithStatus<S> partWithStatus = partsByColor
						.get(pathIndex);
				int pos = getAvailableStatus().indexOf(
						partWithStatus.getStatus());
				pos = (pos + 1) % getAvailableStatus().size();
				partWithStatus.setStatus(getAvailableStatus().get(pos));

			}

			invalidate();

		}
		return false;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		setMeasuredDimension((int) maxWidth, (int) maxHeight);

	}


	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);

		Iterator<PartWithStatus<S>> iterator = partsArray.iterator();

		while (iterator.hasNext()) {
			PartWithStatus<S> partWithStatus = iterator.next();
			ArrayList<Path> paths = partWithStatus.getPaths();
			Iterator<Path> iteratorPaths = paths.iterator();
			while (iteratorPaths.hasNext()) {
				Path path = iteratorPaths.next();
				Paint paint = partWithStatus.getPaint();
				canvas.drawPath(path, paint);
			}

		}
		canvas.drawBitmap(foregroundBitmap, 0, 0, null);

		canvas.save();

	}

	private void calculateMaxWidthAndHeight() {
		Iterator<Collection<CoordinatesRegion>> coordinatesRegionsIterator = getRegions()
				.values().iterator();
		while (coordinatesRegionsIterator.hasNext()) {
			Collection<CoordinatesRegion> coordinatesRegionCollection = coordinatesRegionsIterator
					.next();
			Iterator<CoordinatesRegion> iteratorCoordinatesRegion = coordinatesRegionCollection
					.iterator();
			while (iteratorCoordinatesRegion.hasNext()) {
				CoordinatesRegion coordinatesRegion = iteratorCoordinatesRegion
						.next();
				float[] coordinates = coordinatesRegion.getCoordinates();
				for (int j = 0; j < coordinates.length; j += 2) {
					if (factorForScreen * coordinates[j] >= maxWidth)
						maxWidth = coordinates[j] * factorForScreen;
					if (factorForScreen * coordinates[j + 1] >= maxHeight)
						maxHeight = coordinates[j + 1] * factorForScreen;

				}
			}
		}

	}

	private Path generatePath(float[] coordss) {
		Path path = new Path();
		path.moveTo(factorForScreen * coordss[0], factorForScreen * coordss[1]);

		for (int i = 2; i < coordss.length; i += 2) {

			path.lineTo(factorForScreen * coordss[i], factorForScreen
					* coordss[i + 1]);

		}
		path.close();

		return path;
	}

	private Path generateLookupBitmapPath(float[] coordss) {
		Path path = new Path();
		path.moveTo(coordss[0] * factorEscalaLookupBitmap,
				factorEscalaLookupBitmap * coordss[1]);

		for (int i = 2; i < coordss.length; i += 2) {

			path.lineTo(factorEscalaLookupBitmap * coordss[i],
					factorEscalaLookupBitmap * coordss[i + 1]);

		}
		path.close();

		return path;
	}

	private void init() {

		int color = Color.GREEN;

		float minimumPx = PixelConverter.getPx(MIN_DP, this);
		int screenSize = getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK;

		switch (screenSize) {
		case 4:

			factorForScreen = minimumPx / 20;
			break;
		case Configuration.SCREENLAYOUT_SIZE_LARGE:
			factorForScreen = 1.5f;
			break;
		case Configuration.SCREENLAYOUT_SIZE_SMALL:
			factorForScreen = 0.5f;
			break;
		default:

		}

		if ((maxWidth == 0.0f) || (maxHeight == 0.0f)) {
			calculateMaxWidthAndHeight();
		}

		foregroundBitmap = getWireframe();
		Bitmap auxBitmap = ImageUtils.scaleBitmap(foregroundBitmap,
				(int) maxWidth, (int) maxHeight);
		foregroundBitmap = auxBitmap;

		lookupBitmap = Bitmap.createBitmap((int) maxWidth, (int) maxHeight,
				Bitmap.Config.ARGB_8888);
		lookupBitmap.eraseColor(Color.BLACK);

		Paint lookupPaint = new Paint();
		Canvas lookupCanvas = new Canvas(lookupBitmap);
		lookupPaint.setAntiAlias(false);
		lookupPaint.setStyle(Style.FILL);

		Path path = null;

		Map<PartWithStatus<S>, Collection<CoordinatesRegion>> partsMap = getRegions();

		Iterator<PartWithStatus<S>> partsWithStatusIterator = partsMap.keySet()
				.iterator();

		while (partsWithStatusIterator.hasNext()) {

			PartWithStatus<S> partWithStatus = partsWithStatusIterator.next();
			Collection<CoordinatesRegion> coordinatesRegion = partsMap
					.get(partWithStatus);
			Iterator<CoordinatesRegion> regionIterator = coordinatesRegion
					.iterator();

			while (regionIterator.hasNext()) {
				CoordinatesRegion coordinateRegion = regionIterator.next();
				Path bitmapPath = generateLookupBitmapPath(coordinateRegion
						.getCoordinates());
				lookupPaint.setColor(color);
				lookupCanvas.drawPath(bitmapPath, lookupPaint);

				path = generatePath(coordinateRegion.getCoordinates());
				partWithStatus.addPath(path);
			}

			partsArray.add(partWithStatus);
			partsByColor.put(color, partWithStatus);
			color++;
		}

	}

}
