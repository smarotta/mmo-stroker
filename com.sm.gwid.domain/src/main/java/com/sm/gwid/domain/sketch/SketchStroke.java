package com.sm.gwid.domain.sketch;

public enum SketchStroke {

	SIMPLE((short)0x0001);
	
	short v;
	
	SketchStroke(short v) {
		this.v = v;
	}
	
	public static SketchStroke fromValue(short v) {
		for(SketchStroke stroke : SketchStroke.values()) {
			if (stroke.v == v) {
				return stroke;
			}
		}
		return SIMPLE;
	}
	
	public short getValue() {
		return v;
	}
	
}
