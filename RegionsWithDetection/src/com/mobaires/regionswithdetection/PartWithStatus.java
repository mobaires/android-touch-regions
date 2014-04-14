package com.mobaires.regionswithdetection;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Paint;
import android.graphics.Path;

public interface PartWithStatus<S> {
	
	
	public S getStatus();
	
	public void setStatus(S status);
	
	public List<S> getAvailableStatuses();

	public void addPath(Path path);

	public ArrayList<Path> getPaths();

	public Paint getPaint();
	
}
