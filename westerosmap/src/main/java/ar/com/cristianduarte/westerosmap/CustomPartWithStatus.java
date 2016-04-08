package ar.com.cristianduarte.westerosmap;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.mobaires.regionswithdetection.PartWithStatus;

import java.util.ArrayList;
import java.util.List;

public class CustomPartWithStatus implements PartWithStatus<Boolean> {
	private Boolean status = false;
	private ArrayList<Path> paths = new ArrayList<Path>();
	@Override
	public Boolean getStatus() {
		
		return status;
	}

	@Override
	public void setStatus(Boolean status) {
		this.status = status;

	}

	@Override
	public List<Boolean> getAvailableStatuses() {
		List<Boolean> statusses = new ArrayList<Boolean>();
		return statusses;
	}

	@Override
	public void addPath(Path path) {
		paths.add(path);

	}

	@Override
	public ArrayList<Path> getPaths() {
		return paths;
	}

	public Paint getPaint() {
		Paint paint = new Paint();
		if (status)
			paint.setColor(Color.GREEN);
		else paint.setColor(Color.GRAY);
		return paint;
	}
	
}
