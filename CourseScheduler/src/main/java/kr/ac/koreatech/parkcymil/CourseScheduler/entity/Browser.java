package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

import java.util.ArrayList;
import java.util.List;

public class Browser {
	
	private Course peek;
	private List<Course> courseList;
	private List<Course> otherSections;
	private List<Runnable> peekListeners;
	private List<Runnable> eraseListeners;
	
	public Browser() {
		courseList = AppData.get().courseList;
		otherSections = null;
		peekListeners = new ArrayList<>();
		eraseListeners = new ArrayList<>();
	}
	
	public void peek(Course course) {
		String code = course.getCode();
		int sect = course.getSection();
		peek = course;
		otherSections = courseList.stream().filter(c -> {
			return code.equals(c.getCode()) && sect != c.getSection();
		}).toList();
		peekListeners.forEach(Runnable::run);
	}
	
	public void erase() {
		if (peek == null) return;
		peek = null;
		otherSections = null;
		eraseListeners.forEach(Runnable::run);
	}
	
	public Course getPeek() {
		return peek;
	}
	
	public List<Course> getOtherSections() {
		return (otherSections == null) ? List.of() : otherSections;
	}
	
	/**
	 * Registers a listener for this {@code Browser}.<br>
	 * Event occurs when {@link #peek} is invoked.
	 * @param c Callback to run when the event occurs.
	 */
	public void addPeekListener(Runnable r) {
		peekListeners.add(r);
	}
	
	/**
	 * Registers a listener for this {@code Browser}.<br>
	 * Event occurs when {@link #erase} is successfully invoked.
	 * @param c Callback to run when the event occurs.
	 */
	public void addEraseListener(Runnable r) {
		eraseListeners.add(r);
	}
	
}
