package kr.ac.koreatech.parkcymil.CourseScheduler.view.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.RowSorter;

import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Course;
import kr.ac.koreatech.parkcymil.CourseScheduler.view.component.TimeTableModel;

public abstract class TransferEventHandler {
	
	private JTable table;
	private TimeTableModel model;
	
	public TransferEventHandler(JTable table, TimeTableModel model) {
		this.table = table;
		this.model = model;
	}

	public MouseAdapter getButtonListener() {
		return new MouseAdapter() {
			@Override
			public final void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)
					execute();
			}
		};
	}

	public MouseAdapter getDoubleClickListener() {
		return new MouseAdapter() {
			@Override
			public final void mousePressed(MouseEvent e) {
				int btn = e.getButton();
				int count = e.getClickCount();
				
				if (btn == MouseEvent.BUTTON1 && count > 1)
					execute();
			}
		};
	}
	
	public abstract void onTransfer(Course c);

	@SuppressWarnings("unchecked")
	private void execute() {
		int row = table.getSelectedRow();
		
		if (row > -1) {
			RowSorter<TimeTableModel> rs = (RowSorter<TimeTableModel>) table.getRowSorter();
			int index = row;
			
			if (rs != null)
				index = rs.convertRowIndexToModel(row);
			
			onTransfer(model.getItem(index));
		}
	}
	
}
