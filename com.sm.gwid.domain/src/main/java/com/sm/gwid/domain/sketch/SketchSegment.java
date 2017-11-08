package com.sm.gwid.domain.sketch;

import com.sm.gwid.domain.Domain;
import com.sm.gwid.domain.geometry.Point2D;

public class SketchSegment extends Domain {

	private Point2D start;
	private Point2D end;
	
	public SketchSegment() {
		super();
	}
	
	public SketchSegment(Point2D start, Point2D end) {
		this();
		this.start = start;
		this.end = end;
	}
	
	public Point2D getStart() {
		return start;
	}
	
	public void setStart(Point2D start) {
		this.start = start;
	}
	
	public Point2D getEnd() {
		return end;
	}
	
	public void setEnd(Point2D end) {
		this.end = end;
	}
	
}
