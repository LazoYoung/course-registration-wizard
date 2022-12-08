package kr.ac.koreatech.parkcymil.CourseScheduler.view.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Basket;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Browser;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Course;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.CourseData;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Day;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Hour;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.HourBlock;

public class TimetablePanel extends AppPanel {

	private static final long serialVersionUID = 5702845132470848310L;
	private List<JLabel> dayLabels;
	private List<JLabel> hourLabels;
	private List<JLabel> hourFormats;
	private Map<HourBlock, JLabel> courseLabels;
	private JLabel extraLabel;
	private JButton exportBtn;
	private Browser browser;
	private Basket basket;
	private Dimension size;
	private int offsetX;
	private int offsetY;
	private int cellHeight;
	private int cellWidth;
	private int headerHeight;
	
	public TimetablePanel(Browser browser, Basket basket) {
		dayLabels = new ArrayList<>();
		hourLabels = new ArrayList<>();
		hourFormats = new ArrayList<>();
		courseLabels = new HashMap<>();
		extraLabel = new JLabel("이후");
		exportBtn = new JButton("이미지 저장");
		this.browser = browser;
		this.basket = basket;
		size = new Dimension(490, 660);
		
		setMinimumSize(size);
		setPreferredSize(size);
		setBackground(Color.WHITE);
		setLayout(null);
		browser.addPeekListener(this::onItemPeek);
		browser.addEraseListener(this::onPeekErased);
		basket.addPickListener(this::onItemPick);
		basket.addDropListener(this::onItemDrop);
		
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
		
		exportBtn.setBounds(5, 10, 100, 30);
		exportBtn.addActionListener(this::onExport);
		extraLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addAll(exportBtn, extraLabel);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		
		offsetX = 5;
		offsetY = exportBtn.isVisible() ? 50 : 5;
		headerHeight = 40;
		int width = (int) size.getWidth() - 2 * offsetX;
		int headerY = offsetY + headerHeight;
		cellHeight = 30;
		cellWidth = width / 6;
		int splitX = offsetX + cellWidth / 2;
		
		drawHorizon(g, offsetX, offsetY, width);
		drawHorizon(g, offsetX, headerY, width);
		drawLine(g, splitX, headerY, splitX, headerY + cellHeight * 18);
		
		Point hp = new Point(offsetX, headerY);
		Point vp = new Point(offsetX, offsetY);
		
		for (int i = 0; i < 18; ++i) {
			int halfWidth = cellWidth / 2;
			hourLabels.get(i).setBounds(hp.x, hp.y, halfWidth, cellHeight);
			hourFormats.get(i).setBounds(hp.x + halfWidth, hp.y, halfWidth, cellHeight);
			hp.y += cellHeight;
			drawHorizon(g, hp.x, hp.y, width);
		}
		
		extraLabel.setBounds(hp.x, hp.y, cellWidth, 2 * cellHeight);
		hp.y += 2 * cellHeight;
		int height = hp.y - offsetY;
		drawHorizon(g, hp.x, hp.y, width);
		drawVertical(g, offsetX, offsetY, height);
		drawVertical(g, offsetX + width, offsetY, height);
		
		for (int i = 0; i < 5; ++i) {
			vp.x += cellWidth;
			dayLabels.get(i).setBounds(vp.x, vp.y, cellWidth, headerHeight);
			drawVertical(g, vp.x, vp.y, height);
		}
		
		drawPeekItems(g);
		drawBasketItems(g);
	}

	private void drawBasketItems(Graphics g) {
		int x0 = offsetX + cellWidth;
		int y0 = offsetY + headerHeight;
		Color pColor = g.getColor(); 
		
		for (HourBlock block : basket.getHourBlocks()) {
			Course course = basket.getCourse(block);
			Day day = block.getDay();
			int firstIndex = block.getFirstHour().getIndex();
			int length = block.getLength();
			int offset = 1;
			int x = x0 + day.getIndex() * cellWidth + offset;
			int y = y0 + firstIndex * cellHeight + offset;
			int width = cellWidth - offset;
			int height = length * cellHeight - offset;
			g.setColor(getColor(course));
			g.fillRect(x, y, width, height);
			JLabel label = courseLabels.get(block);
			label.setBounds(x, y, width, height);
		}
		
		g.setColor(pColor);
	}
	
	private void drawPeekItems(Graphics g) {
		Course peek = browser.getPeek();
		
		if (peek == null) return;
				
		for (Course course : browser.getOtherSections()) {
			drawBlockOutlines(g, course, 1);
		}
		
		drawBlockOutlines(g, peek, 5);
	}
	
	private void drawBlockOutlines(Graphics g, Course course, int pixel) {
		Color pColor = g.getColor();
		g.setColor(Color.RED);
		
		for (HourBlock block : course.getHourBlocks()) {
			Day day = block.getDay();
			int firstIndex = block.getFirstHour().getIndex();
			int length = block.getLength();
			int offset = 1;
			int x0 = offsetX + cellWidth;
			int y0 = offsetY + headerHeight;
			int x = x0 + day.getIndex() * cellWidth + offset;
			int y = y0 + firstIndex * cellHeight + offset;
			int width = cellWidth - offset;
			int height = length * cellHeight - offset;
			drawHollowRect(g, new Rectangle(x, y, width, height), pixel);
		}
		
		g.setColor(pColor);
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
	
	private void drawHollowRect(Graphics g, Rectangle r, int pixel) {
		int offset = 2 * pixel;
		Color origin = g.getColor();
		g.fillRect(r.x, r.y, r.width, r.height);
		g.setColor(getBackground());
		g.fillRect(r.x + pixel, r.y + pixel, r.width - offset, r.height - offset);
		g.setColor(origin);
	}
	
	private JLabel createCourseLabel(Course course) {
		JLabel label = new JLabel();
		String name = (String) course.getData(CourseData.NAME);
		int sect = (Integer) course.getData(CourseData.SECTION);
		String prof = (String) course.getData(CourseData.PROFESSOR);
		label.setText("<html>" + name + "<br>" + sect + " " + prof + "</html>");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		Color color = getColor(course);
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		double brightness = (r / 255.0) * 0.2126 + (g / 255.0) * 0.7152 + (b / 255.0) * 0.0722;
		
		if (brightness < 0.4)
			label.setForeground(Color.WHITE);
		
		return label;
	}
	
	private Color getColor(Course course) {
		Random rand = new Random(course.getUniqueID());
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		return new Color(r, g, b);
	}
	
	private void onExport(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		int result = fc.showSaveDialog(null);
		
		if (result != JFileChooser.APPROVE_OPTION) return;
		
		File file = fc.getSelectedFile();
		String fileName = file.getName();
		
		if (!fileName.toLowerCase().endsWith(".png")) {
			fileName = fileName.concat(".png");
			file = new File(file.getParentFile(), fileName);
		}
		
		int width = getWidth();
		int height = getHeight();
		BufferedImage buf = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		exportBtn.setVisible(false);
		paintAll(buf.createGraphics());
		
		try {
			ImageIO.write(buf, "png", file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		exportBtn.setVisible(true);
		repaint();
		
		String title = "저장 완료";
		String msg = "시간표를 아래 경로에 저장했습니다.\n" + file.getAbsolutePath();
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}

	private void onItemPick(Course course) {
		for (HourBlock block : course.getHourBlocks()) {
			JLabel label = createCourseLabel(course);
			courseLabels.put(block, label);
			add(label);
		}
		repaint();
	}
	
	private void onItemDrop(Course course) {
		List<HourBlock> blocks = course.getHourBlocks();
		Iterator<HourBlock> iter = courseLabels.keySet().iterator();
		
		while (iter.hasNext()) {
			HourBlock next = iter.next();
			
			if (blocks.contains(next)) {
				remove(courseLabels.get(next));
				iter.remove();
			}
		}
		repaint();
	}

	private void onItemPeek() {
		repaint();
	}

	private void onPeekErased() {
		repaint();
	}
	
}
