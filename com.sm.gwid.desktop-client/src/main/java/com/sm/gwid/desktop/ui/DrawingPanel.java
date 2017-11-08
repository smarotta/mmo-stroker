package com.sm.gwid.desktop.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.sm.gwid.domain.Sketch;
import com.sm.gwid.domain.geometry.Point2D;
import com.sm.gwid.domain.sketch.SketchSegment;

public class DrawingPanel extends JPanel implements MouseMotionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2411333407735004642L;

	public static interface Listener {
		void onNewSketch(Sketch sketch);
	}
	
	private long lastTimestamp = 0l;
	private Point2D lastPoint = null;
	private Listener listener = null;
	private BufferedImage buffer = null;
	private Color currentColor = Color.white;
	private short currentStrokeWidth = 1;
	
	public DrawingPanel(Listener listener) {
		this.listener = listener;
	}
	
	@Override
	public void paint(Graphics gg) {
		super.paint(gg);
		
		Graphics2D g = (Graphics2D)gg;
		
		//g.setColor(Color.BLACK);
		//g.fillRect(0, 0, (int)g.getClip().getBounds2D().getWidth(), (int)g.getClip().getBounds2D().getHeight());
		
		if (buffer != null) {
			g.drawImage(buffer, 0, 0, null);
		}
	}

	public void mouseDragged(MouseEvent e) {
		if (lastPoint == null) {
			lastPoint = new Point2D(e.getX(), e.getY());
			lastTimestamp = System.currentTimeMillis();
			return;
		}
		
		if (System.currentTimeMillis() - lastTimestamp > 20L) {
			lastTimestamp = System.currentTimeMillis();
			Sketch sketch = new Sketch();
			if (Math.random()*100 > 98) {
				currentColor = new Color((int)Math.round((Math.random()*0xFFFFFF)));
				currentStrokeWidth = (short)(5 + Math.round((Math.random()*10)));
			} 
			sketch.setColor(currentColor.getRGB());
			sketch.setStrokeWidth(currentStrokeWidth);
			
			Point2D newPoint = new Point2D(e.getX(), e.getY());
			sketch.addSegment(new SketchSegment(lastPoint, newPoint));
			lastPoint = newPoint;
			
			//let the listener know about this so it can broadcast to the server
			listener.onNewSketch(sketch);
		}
	}

	public void mouseMoved(MouseEvent e) {
		if (System.currentTimeMillis() - lastTimestamp > 100L) {
			lastPoint = null;
		}
	}
	
	public void addSketch(Sketch sketch) {
		if (buffer == null) {
			GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
			buffer = graphicsConfiguration.createCompatibleImage(this.getWidth(), this.getHeight());
		}
		Graphics2D g = (Graphics2D)buffer.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(new Color(sketch.getColor()));
		g.setStroke(new BasicStroke(sketch.getStrokeWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		for(SketchSegment seg: sketch.getSegments()) {
			g.drawLine(seg.getStart().getX(), seg.getStart().getY(), seg.getEnd().getX(), seg.getEnd().getY());
		}
	}

}
