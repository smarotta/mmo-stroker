package com.sm.gwid.domain;

import java.util.Set;
import java.util.TreeSet;

import com.sm.gwid.domain.sketch.SketchSegment;
import com.sm.gwid.domain.sketch.SketchStroke;

public class Sketch extends Domain {

	private Set<SketchSegment> segments;
	private int color;
	private short strokeWidth = 1;
	private SketchStroke stroke = SketchStroke.SIMPLE;
	
	public Sketch() {
		super();
	}
	
	public Sketch(Set<SketchSegment> segments) {
		this();
		this.segments = segments;
	}
	
	public void addSegment(SketchSegment segment) {
		if (this.segments == null) {
			this.segments = new TreeSet<SketchSegment>();
		}
		this.segments.add(segment);
	}
	
	public Set<SketchSegment> getSegments() {
		return segments;
	}

	public void setSegments(Set<SketchSegment> segments) {
		this.segments = segments;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public short getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(short strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	public SketchStroke getStroke() {
		return stroke;
	}

	public void setStroke(SketchStroke stroke) {
		this.stroke = stroke;
	}
}
