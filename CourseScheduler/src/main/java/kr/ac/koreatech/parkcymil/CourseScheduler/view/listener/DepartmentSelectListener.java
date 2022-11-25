package kr.ac.koreatech.parkcymil.CourseScheduler.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.RowFilter;

import kr.ac.koreatech.parkcymil.CourseScheduler.entity.CourseData;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Department;
import kr.ac.koreatech.parkcymil.CourseScheduler.view.component.BrowserPanel;
import kr.ac.koreatech.parkcymil.CourseScheduler.view.component.TimeTableModel;

public class DepartmentSelectListener implements ActionListener {

	private BrowserPanel panel;
	
	public DepartmentSelectListener(BrowserPanel panel) {
		this.panel = panel;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> source = (JComboBox<String>) e.getSource();
		String department = (String) source.getSelectedItem();
		RowFilter<TimeTableModel, Integer> filter = null;
		
		if (Department.getByText(department) != null) {
			filter = new RowFilter<>() {
				private int col = CourseData.DEPARTMENT.getColumn();
				
				@Override
				@SuppressWarnings("rawtypes")
				public boolean include(Entry entry) {
					String str = entry.getStringValue(col);
					return department.equals(str);
				}
			};
		}
		
		panel.setDepartmentFilter(filter);
	}

}
