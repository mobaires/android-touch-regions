package ar.com.cristianduarte.westerosmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        WesterosRegionsWithDetectionView customRegionWithDetectionView = new WesterosRegionsWithDetectionView(this);
        customRegionWithDetectionView.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        FrameLayout container = (FrameLayout) findViewById(R.id.container);
        container.addView(customRegionWithDetectionView);

    }
}
