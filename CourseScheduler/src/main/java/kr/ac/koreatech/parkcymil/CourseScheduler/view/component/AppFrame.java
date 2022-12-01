package kr.ac.koreatech.parkcymil.CourseScheduler.view.component;

import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import kr.ac.koreatech.parkcymil.CourseScheduler.entity.AppData;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.DataParser;

public class AppFrame extends JFrame {

	public static final Dimension d = new Dimension(1200, 800);
	private static final long serialVersionUID = 6874643613523999600L;

	private AppFrame() {
		super("Course Scheduler");
		
		Container pane = getContentPane();
		JSplitPane ovSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JSplitPane hSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JSplitPane ivSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		InfoPanel infoPanel = new InfoPanel();
		BasketPanel basketPanel = new BasketPanel();
		BrowserPanel browserPanel = new BrowserPanel(basketPanel);
		TimetablePanel timetablePanel = new TimetablePanel(basketPanel);
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
			URL url = AppFrame.class.getClassLoader().getResource("data.csv");
			File file = new File(url.getFile());
			AppData data = AppData.get();
			data.courseList = DataParser.extractCourse(file);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to load data.");
		}
	}
	
}
