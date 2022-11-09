package kr.ac.koreatech.parkcymil.CourseScheduler.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import kr.ac.koreatech.parkcymil.CourseScheduler.entity.CourseData;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Department;
import kr.ac.koreatech.parkcymil.CourseScheduler.view.component.TimeTableModel;

public class DepartmentSelectListener implements ActionListener {

	private TableRowSorter<TimeTableModel> sorter;
	
	public DepartmentSelectListener(TableRowSorter<TimeTableModel> sorter) {
		this.sorter = sorter;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> source = (JComboBox<String>) e.getSource();
		String department = (String) source.getSelectedItem();
		RowFilter<TimeTableModel, Integer> filter = (RowFilter<TimeTableModel, Integer>) sorter.getRowFilter();
		
		if (Department.getByText(department) != null) {
			filter = new RowFilter<TimeTableModel, Integer>() {
				private int col = CourseData.DEPARTMENT.getColumn();
				
				@Override
				@SuppressWarnings("rawtypes")
				public boolean include(Entry entry) {
					String str = entry.getStringValue(col);
					return department.equals(str);
				}
			};
		}
		
		sorter.setRowFilter(filter);
	}

}
