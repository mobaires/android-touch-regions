package com.mobaires.regionswithdetection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public abstract class RegionsWithDetectionView<S> extends ViewGroup {

	private float scaleXLookup = 0.1f;
    private float scaleYLookup = 0.1f;

	private float scaleXShow = 1f;
    private float scaleYShow = 1f;
	private Bitmap lookupBitmap;
	private float maxXOrig = 0.0f;
	private float maxYOrig = 0.0f;
    private float maxXShow = 0.0f;
    private float maxYShow = 0.0f;
    private float maxXLookup = 0.0f;
    private float maxYLookup = 0.0f;

    private float scaleDensityBitmap;

	private Collection<PartWithStatus<S>> partsArray = new ArrayList<>();
	private SparseArray<PartWithStatus<S>> partsByColor = new SparseArray<>();

	private Bitmap foregroundBitmap = null;

    Paint lookupPaint = new Paint();

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

    private void init() {
        lookupPaint.setAntiAlias(false);
        lookupPaint.setStyle(Style.FILL);
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
		
		if ( x <= maxXShow && y <= maxYShow && x >= 0 && y >= 0 ) {

            int pathIndex = lookupBitmap.getPixel(
                    (int) (x * scaleXLookup),
                    (int) (y * scaleYLookup));

			if ((pathIndex != Color.BLACK) && (partsByColor.get(pathIndex)!=null)) {
				PartWithStatus<S> partWithStatus = partsByColor.get(pathIndex);
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

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMeasured = 0;
        int heightMeasured = 0;

        foregroundBitmap = getWireframe();

        if (widthMode == MeasureSpec.AT_MOST) {
			// Considering AT_MOST as EXACTLY
            widthMeasured = widthSize;
        } else if (widthMode == MeasureSpec.EXACTLY) {
            widthMeasured = widthSize;
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            widthMeasured = foregroundBitmap.getWidth();
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightMeasured = heightSize;
        } else if (heightMode == MeasureSpec.EXACTLY) {
            heightMeasured = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightMeasured = foregroundBitmap.getHeight();
        }

        scaleDensityBitmap = (float) foregroundBitmap.getDensity() / DisplayMetrics.DENSITY_DEFAULT;

        scaleXShow = ((float) widthMeasured) / foregroundBitmap.getWidth();
        scaleYShow = ((float) heightMeasured) / foregroundBitmap.getHeight();

        foregroundBitmap = ImageUtils.scaleBitmap(foregroundBitmap, widthMeasured, heightMeasured);

        Log.d("VIEW", "id(" + getId() + ") "
                + MeasureSpec.toString(widthMeasureSpec) + " x "
                + MeasureSpec.toString(heightMeasureSpec));

        if ((maxXShow == 0.0f) || (maxYShow == 0.0f)) {
        	calculateMaxWidthAndHeight();
        }

        lookupBitmap = Bitmap.createBitmap((int) (maxXLookup), (int) (maxYLookup),
                Bitmap.Config.ARGB_8888);
        lookupBitmap.eraseColor(Color.BLACK);
        Canvas lookupCanvas = new Canvas(lookupBitmap);

        int color = Color.BLUE;

        Path path = null;
        Path bitmapPath = null;
        PartWithStatus<S> partWithStatus = null;

        Map<PartWithStatus<S>, Collection<CoordinatesRegion>> partsMap = getRegions();

        for (Map.Entry<PartWithStatus<S>, Collection<CoordinatesRegion>> entry: partsMap.entrySet()) {
            partWithStatus = entry.getKey();
            for (CoordinatesRegion coordinateRegion: entry.getValue()) {
                bitmapPath = generateLookupBitmapPath(coordinateRegion
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
		setMeasuredDimension(widthMeasured, heightMeasured);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
        for (PartWithStatus<S> partWithStatus: partsArray) {
            for (Path path: partWithStatus.getPaths()) {
                canvas.drawPath(path, partWithStatus.getPaint());
            }
        }
		canvas.drawBitmap(foregroundBitmap, 0, 0, null);
    	canvas.save();
	}

	private void calculateMaxWidthAndHeight() {
        for (Collection<CoordinatesRegion> coordinatesRegionCollection: getRegions().values()) {
            for (CoordinatesRegion coordinatesRegion: coordinatesRegionCollection) {
                float[] coordinates = coordinatesRegion.getCoordinates();
                for (int j = 0; j < coordinates.length; j += 2) {
                    maxXOrig = Math.max(coordinates[j], maxXOrig);
                    maxYOrig = Math.max(coordinates[j+1], maxYOrig);
                }
            }
        }
        maxXLookup = maxXOrig * scaleDensityBitmap * scaleXShow * scaleXLookup;
        maxYLookup = maxYOrig * scaleDensityBitmap * scaleYShow * scaleYLookup;
        maxXShow = maxXOrig * scaleDensityBitmap * scaleXShow;
        maxYShow = maxYOrig * scaleDensityBitmap * scaleYShow;
	}

	private Path generatePath(float[] coordinates) {
		Path path = new Path();
		path.moveTo(coordinates[0] * scaleDensityBitmap * scaleXShow,
                coordinates[1] * scaleDensityBitmap * scaleYShow);

		for (int i = 2; i < coordinates.length; i += 2) {
			path.lineTo(coordinates[i] * scaleDensityBitmap * scaleXShow,
                    coordinates[i + 1] * scaleDensityBitmap * scaleYShow);
		}
		path.close();

		return path;
	}

	private Path generateLookupBitmapPath(float[] coordinates) {
		Path path = new Path();
		path.moveTo(
                coordinates[0] * scaleDensityBitmap * scaleXShow * scaleXLookup,
				coordinates[1] * scaleDensityBitmap * scaleYShow * scaleYLookup);
		for (int i = 2; i < coordinates.length; i += 2) {
			path.lineTo(
                    coordinates[i] * scaleDensityBitmap * scaleXShow * scaleXLookup,
					coordinates[i + 1] * scaleDensityBitmap * scaleYShow * scaleYLookup);
		}
		path.close();
		return path;
	}

}
