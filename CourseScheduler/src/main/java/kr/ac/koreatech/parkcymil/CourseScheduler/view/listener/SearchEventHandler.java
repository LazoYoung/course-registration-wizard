package kr.ac.koreatech.parkcymil.CourseScheduler.view.listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.RowFilter;

import kr.ac.koreatech.parkcymil.CourseScheduler.entity.CourseData;
import kr.ac.koreatech.parkcymil.CourseScheduler.view.component.BrowserPanel;
import kr.ac.koreatech.parkcymil.CourseScheduler.view.component.TimeTableModel;

public class SearchEventHandler {
	
	public static MouseAdapter getMouseAdapter(BrowserPanel panel) {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)
					doAction(panel);
			}
		};
	}
	
	public static KeyAdapter getKeyAdapter(BrowserPanel panel) {
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					doAction(panel);
			}
		};
	}

	private static void doAction(BrowserPanel panel) {
		String query = panel.getSearchField().getText().toLowerCase();
		RowFilter<TimeTableModel, Integer> filter= (RowFilter<TimeTableModel, Integer>) panel.getTableSorter().getRowFilter();
		
		if (!query.isBlank()) {
			filter = new RowFilter<TimeTableModel, Integer>() {
				private CourseData[] dataArray = {
						CourseData.CODE, CourseData.NAME,
						CourseData.PROFESSOR, CourseData.NOTE
				};
				
				@Override
				@SuppressWarnings("rawtypes")
				public boolean include(Entry entry) {
					for (CourseData data : dataArray) {
						int col = data.getColumn();
						String val = entry.getStringValue(col).toLowerCase();
						if (!val.isBlank() && val.startsWith(query))
							return true;
					}
					return false;
				}
			};
		}
		
		panel.getTableSorter().setRowFilter(filter);
	}

}
