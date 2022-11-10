package kr.ac.koreatech.parkcymil.CourseScheduler.view.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

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
			public final void mouseClicked(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON1)
					return;
				
				int row = table.getSelectedRow();
				onTransfer(model.getItem(row));
			}
		};
	}
	
	public MouseAdapter getDoubleClickListener() {
		return new MouseAdapter() {
			@Override
			public final void mouseClicked(MouseEvent e) {
				int btn = e.getButton();
				int count = e.getClickCount();
				
				if (btn == MouseEvent.BUTTON1 && count > 1) {
					int row = table.getSelectedRow();
					onTransfer(model.getItem(row));
				}
			}
		};
	}
	
	public abstract void onTransfer(Course c);

}
