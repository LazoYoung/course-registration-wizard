package kr.ac.koreatech.parkcymil.CourseScheduler.view.component;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import kr.ac.koreatech.parkcymil.CourseScheduler.entity.AppData;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Basket;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Browser;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.DataParser;

public class AppFrame extends JFrame {

	public static final Dimension d = new Dimension(1200, 800);
	private static final long serialVersionUID = 6874643613523999600L;

	private AppFrame() {
		super("Course Scheduler");
		
		var pane = getContentPane();
		var basket = new Basket();
		var browser = new Browser();
		var ovSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		var hSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		var ivSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		var infoPanel = new InfoPanel();
		var basketPanel = new BasketPanel(basket);
		var browserPanel = new BrowserPanel(browser, basket);
		var timetablePanel = new TimetablePanel(browser, basket);
		ovSplit.setTopComponent(infoPanel);
		ovSplit.setBottomComponent(hSplit);
		ovSplit.setResizeWeight(0);
		ovSplit.resetToPreferredSizes();
		ovSplit.setDividerSize(0);
		ovSplit.setEnabled(false);
		hSplit.setLeftComponent(ivSplit);
		hSplit.setRightComponent(timetablePanel);
		hSplit.setResizeWeight(1.0);
		hSplit.resetToPreferredSizes();
		hSplit.setDividerSize(0);
		hSplit.setEnabled(false);
		ivSplit.setTopComponent(browserPanel);
		ivSplit.setBottomComponent(basketPanel);
		ivSplit.setResizeWeight(1.0);
		ivSplit.resetToPreferredSizes();
		ivSplit.setDividerSize(0);
		ivSplit.setEnabled(false);
		pane.add(ovSplit);
		
		int minWidth = ovSplit.getMinimumSize().width;
		int minHeight = ovSplit.getMinimumSize().height;
		setSize(d);
		setMinimumSize(new Dimension(minWidth, minHeight));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		loadData();
		AppFrame frame = new AppFrame();
		frame.setVisible(true);
	}
	
	private static void loadData() {
		try {
			var is = AppFrame.class.getResourceAsStream("/data.csv");
			var data = AppData.get();
			data.courseList = DataParser.extractCourse(is);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to load data.");
		}
	}
	
}
