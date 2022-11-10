package kr.ac.koreatech.parkcymil.CourseScheduler.view.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import kr.ac.koreatech.parkcymil.CourseScheduler.view.component.TimeTableModel;

public class CoursePickListener extends MouseAdapter {

	private TimeTableModel model;

	public CoursePickListener(TimeTableModel model) {
		this.model = model;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1)
			return;
	}

}
