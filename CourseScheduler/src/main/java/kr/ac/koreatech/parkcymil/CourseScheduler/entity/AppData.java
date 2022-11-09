package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

import java.util.ArrayList;
import java.util.List;

public class AppData {
	
	private static AppData instance = new AppData();
	private List<Course> courseList;
	
	private AppData() {
		courseList = new ArrayList<Course>();
	}
	
	public static AppData get() {
		return instance;
	}
	
	public List<Course> getCourseList() {
		return courseList;
	}
	
}
