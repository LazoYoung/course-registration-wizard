package kr.ac.koreatech.parkcymil.CourseScheduler.view.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Day;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Hour;

public class TimetablePanel extends AppPanel {

	private static final long serialVersionUID = 5702845132470848310L;
	private Dimension size = new Dimension(500, 660);
	private List<JLabel> dayLabels;
	private List<JLabel> hourLabels;
	private List<JLabel> hourFormats;
	
	public TimetablePanel() {
		dayLabels = new ArrayList<JLabel>();
		hourLabels = new ArrayList<JLabel>();
		hourFormats = new ArrayList<JLabel>();
		setMinimumSize(size);
		setPreferredSize(size);
		setBackground(Color.WHITE);
		
		for (int i = 0; i < Day.values().length; ++i) {
			String text = Day.get(i).getLabel();
			JLabel label = new JLabel(text);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			dayLabels.add(label);
			add(label);
		}
		
		for (int i = 0; i < 18; ++i) {
			Hour hour = new Hour(i);
			JLabel label = new JLabel(hour.getLabel());
			JLabel format = new JLabel(hour.getFormat());
			label.setHorizontalAlignment(SwingConstants.CENTER);
			format.setHorizontalAlignment(SwingConstants.CENTER);
			hourLabels.add(label);
			hourFormats.add(format);
			addAll(label, format);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		
		int offsetX = 5;
		int offsetY = 5;
		int width = getWidth() - 2 * offsetX;
		int height = Math.min(getHeight(), (int) size.getHeight()) - 2 * offsetY;
		int headerHeight = 40;
		int headerY = offsetY + headerHeight;
		int cellHeight = 30;
		int cellWidth = width / 6;
		int splitX = offsetX + cellWidth / 2;
		
		drawHorizon(g, offsetX, offsetY, width);
		drawHorizon(g, offsetX, headerY, width);
		drawHorizon(g, offsetX, offsetY + height, width);
		drawLine(g, splitX, headerY, splitX, headerY + cellHeight * 18);
		drawVertical(g, offsetX, offsetY, height);
		drawVertical(g, offsetX + width, offsetY, height);
		
		Point hp = new Point(offsetX, headerY);
		Point vp = new Point(offsetX, offsetY);
		
		for (int i = 0; i < 18; ++i) {
			int halfWidth = cellWidth / 2;
			hourLabels.get(i).setBounds(hp.x, hp.y, halfWidth, cellHeight);
			hourFormats.get(i).setBounds(hp.x + halfWidth, hp.y, halfWidth, cellHeight);
			hp.y += cellHeight;
			drawHorizon(g, hp.x, hp.y, width);
		}
		
		for (int i = 0; i < 5; ++i) {
			vp.x += cellWidth;
			dayLabels.get(i).setBounds(vp.x, vp.y, cellWidth, headerHeight);
			drawVertical(g, vp.x, vp.y, height);
		}
	}
	
	private void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
		g.drawLine(x1, y1, x2, y2);
	}
	
	private void drawHorizon(Graphics g, int x, int y, int length) {
		g.drawLine(x, y, x + length, y);
	}
	
	private void drawVertical(Graphics g, int x, int y, int length) {
		g.drawLine(x, y, x, y + length);
	}
	
	private void drawRect(Graphics g, Rectangle r, int pixel) {
		int offset = 2 * pixel;
		Color origin = g.getColor();
		g.fillRect(r.x, r.y, r.width, r.height);
		g.setColor(getBackground());
		g.fillRect(r.x + pixel, r.y + pixel, r.width - offset, r.height - offset);
		g.setColor(origin);
	}
	
	private void exportImage() {
		// TODO Needs test
		int width = getWidth();
		int height = getHeight();
		BufferedImage buf = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		File file = new File("exported.png");
		paintComponent(buf.createGraphics());
		try {
			ImageIO.write(buf, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
