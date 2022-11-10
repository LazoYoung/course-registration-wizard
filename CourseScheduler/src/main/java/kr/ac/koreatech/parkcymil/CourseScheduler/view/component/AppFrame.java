package kr.ac.koreatech.parkcymil.CourseScheduler.view.component;

import java.awt.Container;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import kr.ac.koreatech.parkcymil.CourseScheduler.entity.AppData;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Course;

public class AppFrame extends JFrame {

	public static final Dimension d = new Dimension(1400, 800);
	
	private static final long serialVersionUID = 6874643613523999600L;

	private AppFrame() {
		super("Course Scheduler");
		
		Container pane = getContentPane();
		JSplitPane ovSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JSplitPane hSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JSplitPane ivSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		InfoPanel infoPanel = new InfoPanel();
		TimetablePanel timetablePanel = new TimetablePanel();
		BasketPanel basketPanel = new BasketPanel();
		BrowserPanel browserPanel = new BrowserPanel();
		ovSplit.setTopComponent(infoPanel);
		ovSplit.setBottomComponent(hSplit);
		ovSplit.setResizeWeight(0.0);
		ovSplit.setDividerSize(0);
		ovSplit.setEnabled(false);
		hSplit.setLeftComponent(ivSplit);
		hSplit.setRightComponent(timetablePanel);
		hSplit.setResizeWeight(1.0);
		hSplit.setDividerSize(0);
		hSplit.setEnabled(false);
		ivSplit.setTopComponent(browserPanel);
		ivSplit.setBottomComponent(basketPanel);
		ivSplit.setResizeWeight(1.0);
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
		supplyTestData();
		
		AppFrame frame = new AppFrame();
		frame.setVisible(true);
	}

	private static void supplyTestData() {
		List<Course> list = AppData.get().getCourseList();
		Course[] array = {
				new Course("CSE110", "창의적공학설계(AD)", 1, "문성태", "컴부전체", 3, null, 50, 3, "컴퓨터공학부"),
				new Course("CSE110", "창의적공학설계(AD)", 2, "문성태", "컴부전체", 3, null, 50, 3, "컴퓨터공학부"),
				new Course("CSE110", "창의적공학설계(AD)", 3, "김덕수", "컴부전체", 3, null, 50, 3, "컴퓨터공학부"),
				new Course("CSE110", "창의적공학설계(AD)", 4, "윤한경", "컴부전체", 3, null, 50, 3, "컴퓨터공학부"),
				new Course("CSE110", "창의적공학설계(AD)", 1, "라문우", "기공전체", 2, null, 50, 2, "기계공학부"),
				new Course("CSE110", "창의적공학설계(AD)", 2, "유형민", "기공전체", 2, null, 50, 2, "기계공학부"),
				new Course("CSE110", "창의적공학설계(AD)", 3, "채석병", "기공전체", 2, null, 50, 2, "기계공학부")
		};
		
		for (Course c : array)
			list.add(c);
	}
	
}
