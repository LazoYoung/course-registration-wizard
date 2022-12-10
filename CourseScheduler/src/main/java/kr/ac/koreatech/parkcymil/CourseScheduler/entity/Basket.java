package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class Basket {

	private Map<HourBlock, Course> map;
	private List<Course> hollowCourses;
	private List<Consumer<Course>> pickListeners;
	private List<Consumer<Course>> dropListeners;
	
	public Basket() {
		map = new HashMap<>();
		hollowCourses = new ArrayList<>();
		pickListeners = new ArrayList<>();
		dropListeners = new ArrayList<>();
	}
	
	public void pick(Course course) {
		var blocks = course.getHourBlocks();
		
		if (blocks.isEmpty()) {
			hollowCourses.add(course);
		} else {
			for (HourBlock block : course.getHourBlocks()) {
				map.put(block, course);
			}
		}
		pickListeners.forEach(c -> c.accept(course));
	}
	
	public void drop(Course course) {
		if (course.getHours().length == 0) {
			hollowCourses.remove(course);
		} else {
			Iterator<HourBlock> iter = map.keySet().iterator();
			
			while (iter.hasNext()) {
				if (map.get(iter.next()) == course) {
					iter.remove();
				}
			}
		}
		dropListeners.forEach(c -> c.accept(course));
	}
	
	/**
	 * This method checks if the course induces time conflicts.<br>
	 * It's required to check time conflicts before invoking
	 * {@link #pick(Course)} to prevent issues.
	 * @param course The course to be checked
	 * @return Existing course in this {@code Basket}
	 * that is in conflict with given argument.
	 * This returns <b>null</b> if no conflict were found.
	 */
	public Course getCourseInConflict(Course course) {
		for (HourBlock blockA : course.getHourBlocks()) {
			for (HourBlock blockB : getHourBlocks()) {
				if (blockB.isInConflict(blockA))
					return map.get(blockB);
			}
		}
		return null;
	}
	
	public List<HourBlock> getHourBlocks() {
		var arr = map.keySet().toArray(new HourBlock[0]);
		List<HourBlock> ret = new ArrayList<>();
		Collections.addAll(ret, arr);
		return ret;
	}
	
	public Course getCourse(HourBlock block) {
		return map.get(block);
	}
	
	public Set<Course> getCourses() {
		var set = new HashSet<Course>();
		set.addAll(hollowCourses);
		set.addAll(map.values());
		return set;
	}
	
	public boolean contains(Course c) {
		return (map.containsValue(c) || hollowCourses.contains(c));
	}
	
	/**
	 * Registers a listener for this {@code Basket}.<br>
	 * Event occurs when user decides to pick a course into this basket.
	 * @param c Callback to run when the event occurs.
	 */
	public void addPickListener(Consumer<Course> c) {
		pickListeners.add(c);
	}
	
	/**
	 * Registers a listener for this {@code Basket}.<br>
	 * Event occurs when user decides to drop a course out of this basket.
	 * @param c Callback to run when the event occurs.
	 */
	public void addDropListener(Consumer<Course> c) {
		dropListeners.add(c);
	}
	
}
