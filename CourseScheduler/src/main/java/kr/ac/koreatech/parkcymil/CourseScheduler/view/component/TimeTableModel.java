package kr.ac.koreatech.parkcymil.CourseScheduler.view.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Course;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.CourseData;

public class TimeTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -3765469254570687920L;
	private List<Course> items;
	private CourseData[] headerItems = {
			CourseData.CODE,
			CourseData.NAME,
			CourseData.SECTION,
			CourseData.PROFESSOR,
			CourseData.PROGRAM,
			CourseData.CREDIT,
			CourseData.NOTE,
			CourseData.CAPACITY,
			CourseData.DESIGN,
			CourseData.DEPARTMENT
	};
	
	public TimeTableModel() {
		this(new ArrayList<>());
	}
	
	public TimeTableModel(List<Course> items) {
		this.items = items; 
	}

	public int getRowCount() {
		return items.size();
	}

	public int getColumnCount() {
		return headerItems.length;
	}
	
	@Override
	public String getColumnName(int col) {
		return headerItems[col].getText();
	}

	public Object getValueAt(int row, int col) {
		Course course = items.get(row);
		return course.getData(headerItems[col]);
	}
	
	public Class<?> getColumnClass(int col) {
		return headerItems[col].getType();
	}
	
	public void addItem(Course course) {
		if (items.stream().anyMatch(c -> c.getUniqueID() == course.getUniqueID()))
			return;
		
		items.add(course);
		fireTableDataChanged();
	}
	
	public void removeItem(Course course) {
		if (items.remove(course))
			fireTableDataChanged();
	}
	
	public void clearItems() {
		if (items.isEmpty())
			return;
		
		items.clear();
		fireTableDataChanged();
	}
	
	public Course getItem(int index) {
		return items.get(index);
	}

}
