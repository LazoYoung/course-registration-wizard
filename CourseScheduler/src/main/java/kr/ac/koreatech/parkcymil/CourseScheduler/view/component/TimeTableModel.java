package kr.ac.koreatech.parkcymil.CourseScheduler.view.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Course;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.CourseData;

public class TimeTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -3765469254570687920L;
	private List<Course> items;
	
	public TimeTableModel() {
		this(new ArrayList<Course>());
	}
	
	public TimeTableModel(List<Course> items) {
		this.items = items;
	}

	public int getRowCount() {
		return items.size();
	}

	public int getColumnCount() {
		return CourseData.values().length;
	}
	
	@Override
	public String getColumnName(int col) {
		return CourseData.getByColumn(col).getText();
	}

	public Object getValueAt(int row, int col) {
		Course c = items.get(row);
		CourseData data = CourseData.getByColumn(col);
		return c.get(data);
	}
	
	public Class<?> getColumnClass(int col) {
		return CourseData.getByColumn(col).getType();
	}
	
	public void addItem(Course course) {
		if (items.stream().anyMatch(c -> c.getUniqueID() == course.getUniqueID()))
			return;
		
		items.add(course);
		fireTableDataChanged();
	}
	
	public Course getItem(int index) {
		return items.get(index);
	}
	
	public void removeItem(int index) {
		items.remove(index);
		fireTableDataChanged();
	}

}
