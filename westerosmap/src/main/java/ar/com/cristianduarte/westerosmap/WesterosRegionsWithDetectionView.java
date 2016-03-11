package ar.com.cristianduarte.westerosmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.Display;

import com.mobaires.regionswithdetection.CoordinatesRegion;
import com.mobaires.regionswithdetection.PartWithStatus;
import com.mobaires.regionswithdetection.RegionsWithDetectionView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cduarte on 3/10/16.
 */
public class WesterosRegionsWithDetectionView extends RegionsWithDetectionView<Boolean> {

    private static final float[] beyondTheWallCoordinates = new float[] {
            82,76, 83,242,
            195,307, 289,304, 325,275, 361,277, 395,272,
            441,274, 461,245, 460,202,
            361,78
    };
    private static final float[] theNorthCoordinates = new float[] {
            195,307, 289,304, 325,275, 361,277, 395,272,
            441,274, 461,245, 460,202,
            558,201,
            542, 614, 346,630, 331,675, 278,698, 232,725,
            80,705, 80,319
    };
    private static final float[] ironIslandsCoordinates = new float[] {
            232,725, 80,705, 89,806, 151,806, 191,783, 191,740
    };
    private static final float[] theValeCoordinates = new float[] {
            331,675, 346,630, 542,614, 553,771, 539,804, 499,819,
            461,846, 432,818, 406,799, 380,779, 362,756, 350,741, 338,706, 328,685
    };
    private static final float[] riverrunCoordinates = new float[] {
            461,846, 432,818, 406,799, 380,779, 362,756, 350,741, 338,706, 328,685,
            312,682, 278,698, 232,725, 191,740, 191,783,
            201,804, 188,835, 201,843, 208,853, 208,861, 216,871, 221,899, 227,910, 228,917, 235,929, 249,938,
            262,930, 270,917, 278,917,
            351,937, 348,917, 345,913, 346,888, 371,884, 389,883, 414,870, 427,850
    };
    private static final float[] crownlandsCoordinates = new float[] {
            351,937, 348,917, 345,913, 346,888, 371,884, 389,883, 414,870, 427,850,
            461,846, 499,819, 539,804,
            534,954, 487,974, 450,977, 436,986, 406,1004, 394,1009, 363,1012,
            352,988, 350,975, 344,954, 351,945
    };
    private static final float[] westerlandsCoordinates = new float[] {
            201,804, 188,835, 201,843, 208,853, 208,861, 216,871, 221,899, 227,910, 228,917, 235,929, 249,938,
            231,968, 203,981, 189,988, 183,988, 161,995, 110,1008,
            73,861, 151,806, 191,783
    };
    private static final float[] stormlandsCoordinates = new float[] {
            534,954, 487,974, 450,977, 436,986, 406,1004, 394,1009, 363,1012,
            371,1021, 372,1035, 369,1041, 358,1048, 323,1081, 312,1087, 270,1121,
            251,1130, 269,1130, 293,1131, 314,1134, 340,1134, 354,1125, 365,1125, 418,1155, 520,1155,
            559,1073
    };
    private static final float[] dorneCoordinates = new float[] {
            251,1130, 269,1130, 293,1131, 314,1134, 340,1134, 354,1125, 365,1125, 418,1155, 520,1155,
            539,1142, 550,1160, 563,1202, 483,1273, 365,1280,
            207,1289, 204,1238, 212,1163, 220,1151, 238,1143, 245,1143
    };
    private static final float[] theReachCoordinates = new float[] {
            207,1289, 204,1238, 212,1163, 220,1151, 238,1143, 245,1143,
            251,1130, 270,1121, 312,1087, 323,1081, 358,1048, 369,1041, 372,1055, 371,1021, 363,1012, 352,988, 350,975, 344,954, 351,937, 278,917, 270,917, 262,930, 249,938,
            231,968, 203,981, 189,988, 183,988, 161,995, 110,1008,
            75,1163, 90,1295
    };

    private Bitmap bitmap = null;

    public WesterosRegionsWithDetectionView(Context context) {
        super(context);
        //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.got);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDensity = DisplayMetrics.DENSITY_DEFAULT;
        options.inTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.got, options);
    }

    @Override
    public Bitmap getWireframe() {
        if (bitmap == null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDensity = DisplayMetrics.DENSITY_DEFAULT;
            options.inTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.got, options);
        }

        return bitmap;
    }

    @Override
    public Map<PartWithStatus<Boolean>, Collection<CoordinatesRegion>> getRegions() {
        Map<PartWithStatus<Boolean>, Collection<CoordinatesRegion>> partsWithStatusStaticMap = new HashMap<>();

        CoordinatesRegion coordinatesRegion1 = new CoordinatesRegion(beyondTheWallCoordinates);
        ArrayList<CoordinatesRegion> coordinatesRegions = new ArrayList<>();
        coordinatesRegions.add(coordinatesRegion1);
        partsWithStatusStaticMap.put(new CustomPartWithStatus(), coordinatesRegions);

        coordinatesRegion1 = new CoordinatesRegion(theNorthCoordinates);
        coordinatesRegions = new ArrayList<>();
        coordinatesRegions.add(coordinatesRegion1);
        partsWithStatusStaticMap.put(new CustomPartWithStatus(), coordinatesRegions);

        coordinatesRegion1 = new CoordinatesRegion(ironIslandsCoordinates);
        coordinatesRegions = new ArrayList<>();
        coordinatesRegions.add(coordinatesRegion1);
        partsWithStatusStaticMap.put(new CustomPartWithStatus(), coordinatesRegions);

        coordinatesRegion1 = new CoordinatesRegion(theValeCoordinates);
        coordinatesRegions = new ArrayList<>();
        coordinatesRegions.add(coordinatesRegion1);
        partsWithStatusStaticMap.put(new CustomPartWithStatus(), coordinatesRegions);

        coordinatesRegion1 = new CoordinatesRegion(riverrunCoordinates);
        coordinatesRegions = new ArrayList<>();
        coordinatesRegions.add(coordinatesRegion1);
        partsWithStatusStaticMap.put(new CustomPartWithStatus(), coordinatesRegions);

        coordinatesRegion1 = new CoordinatesRegion(crownlandsCoordinates);
        coordinatesRegions = new ArrayList<>();
        coordinatesRegions.add(coordinatesRegion1);
        partsWithStatusStaticMap.put(new CustomPartWithStatus(), coordinatesRegions);

        coordinatesRegion1 = new CoordinatesRegion(westerlandsCoordinates);
        coordinatesRegions = new ArrayList<>();
        coordinatesRegions.add(coordinatesRegion1);
        partsWithStatusStaticMap.put(new CustomPartWithStatus(), coordinatesRegions);

        coordinatesRegion1 = new CoordinatesRegion(stormlandsCoordinates);
        coordinatesRegions = new ArrayList<>();
        coordinatesRegions.add(coordinatesRegion1);
        partsWithStatusStaticMap.put(new CustomPartWithStatus(), coordinatesRegions);

        coordinatesRegion1 = new CoordinatesRegion(dorneCoordinates);
        coordinatesRegions = new ArrayList<>();
        coordinatesRegions.add(coordinatesRegion1);
        partsWithStatusStaticMap.put(new CustomPartWithStatus(), coordinatesRegions);

        coordinatesRegion1 = new CoordinatesRegion(theReachCoordinates);
        coordinatesRegions = new ArrayList<>();
        coordinatesRegions.add(coordinatesRegion1);
        partsWithStatusStaticMap.put(new CustomPartWithStatus(), coordinatesRegions);

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
