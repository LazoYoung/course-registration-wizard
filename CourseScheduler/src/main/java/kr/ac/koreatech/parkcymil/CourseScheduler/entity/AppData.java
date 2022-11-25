package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

import java.util.ArrayList;
import java.util.List;

public class AppData {
	
	private static AppData instance = null;
	public List<Course> courseList = new ArrayList<>();
	
	private AppData() {}
	
	public static AppData get() {
		if (instance == null)
			instance = new AppData();
		
		return instance;
	}
	
}
