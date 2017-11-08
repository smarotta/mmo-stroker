package com.sm.gwid.command.to.input;

import java.util.Iterator;

import com.sm.gwid.command.to.CodecHelper;
import com.sm.gwid.command.to.Command;
import com.sm.gwid.domain.Sketch;
import com.sm.gwid.domain.geometry.Point2D;
import com.sm.gwid.domain.sketch.SketchSegment;
import com.sm.gwid.domain.sketch.SketchStroke;

public class SketchCommandInput extends Command {

	private Sketch sketch;

	public Sketch getSketch() {
		return sketch;
	}

	public void setSketch(Sketch sketch) {
		this.sketch = sketch;
	}

	@Override
	public Type getType() {
		return Type.C1;
	}

	@Override
	public SubType getSubType() {
		return SubType.D0;
	}
	
	@Override
	public void deserialize(byte[] packet) {
		// C1 xx xx D0 [CC CC CC CC] [SS SS] [SW SW] { [XX XX XX XX] [YY YY YY YY] [XX XX XX XX] [YY YY YY YY] }
		setSketch(new Sketch());
		
		//color
		sketch.setColor(CodecHelper.readInt(packet, 4));
		
		//stroke
		sketch.setStroke(SketchStroke.fromValue(CodecHelper.readShort(packet, 8)));
		
		//stroke width
		sketch.setStrokeWidth(CodecHelper.readShort(packet, 10));
		
		//sketch segments
		int sketchTotalBytesCount = packet.length - 10;
		int totalNumberOfSketches = sketchTotalBytesCount / 16;
		
		for(int i=0; i < totalNumberOfSketches; i++) {
			Point2D start = new Point2D();
			Point2D end = new Point2D();
			getSketch().addSegment(new SketchSegment(start, end));
			
			start.setX(CodecHelper.readInt(packet, 4 + 4 + 2 + 2 + i * 16));
			start.setY(CodecHelper.readInt(packet, 4 + 4 + 2 + 2 + i * 16 + 4));
			end.setX(CodecHelper.readInt(packet, 4 + 4 + 2 + 2 + i * 16 + 4 + 4));
			end.setY(CodecHelper.readInt(packet, 4 + 4 + 2 + 2 + i * 16 + 4 + 4 + 4));
		}
	}
	
	@Override
	public byte[] serialize() {
		// C1 xx xx D0 [CC CC CC CC] [SS SS] [SW SW] { [XX XX XX XX] [YY YY YY YY] [XX XX XX XX] [YY YY YY YY] }
		byte [] data = null;
		
		if (getSketch() != null && getSketch().getSegments() != null && !getSketch().getSegments().isEmpty()) {
			data = new byte [4 + 4 + 2 + 2 + 16 * getSketch().getSegments().size()];
			
			//header
			data[0] = getType().getByteValue();
			CodecHelper.writeShort((short)data.length, data, 1);
			data[3] = getSubType().getByteValue();
			
			//color
			CodecHelper.writeInt(sketch.getColor(), data, 4);
			
			//stroke
			CodecHelper.writeShort(sketch.getStroke().getValue(), data, 8);
			
			//stroke width
			CodecHelper.writeShort(sketch.getStrokeWidth(), data, 10);
			
			//sketch segments
			Iterator<SketchSegment> it = getSketch().getSegments().iterator();
			for(int i=0; it.hasNext(); i++) {
				SketchSegment segment = it.next();
				CodecHelper.writeInt(segment.getStart().getX(), data, 4 + 4 + 2 + 2 + i * 16);
				CodecHelper.writeInt(segment.getStart().getY(), data, 4 + 4 + 2 + 2 + i * 16 + 4);
				CodecHelper.writeInt(segment.getEnd().getX(), data, 4 + 4 + 2 + 2 + i * 16 + 4 + 4);
				CodecHelper.writeInt(segment.getEnd().getY(), data, 4 + 4 + 2 + 2 + i * 16 + 4 + 4 + 4);
			}
		}
		
		return data;
	}
}
