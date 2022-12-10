package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

import java.util.List;
import java.util.Map;

public class Course {
	
	private static int newId = 0;
	private Map<CourseData, Object> map;
	private int id;
	
	public Course(Map<CourseData, Object> map) {
		this.id = newId++;
		this.map = map;
	}
	
	public Object getData(CourseData data) {		
		return map.get(data);
	}

	public int getUniqueID() {
		return id;
	}
	
	public String getCode() {
		return (String) map.get(CourseData.CODE);
	}

	public String getName() {
		return (String) map.get(CourseData.NAME);
	}

	public int getSection() {
		return (int) map.get(CourseData.SECTION);
	}

	public String getProgram() {
		return (String) map.get(CourseData.PROGRAM);
	}

	public String getDepartment() {
		return (String) map.get(CourseData.DEPARTMENT);
	}

	public String getProfessor() {
		return (String) map.get(CourseData.PROFESSOR);
	}

	public int getCredit() {
		return (int) map.get(CourseData.CREDIT);
	}

	public int getDesign() {
		return (int) map.get(CourseData.DESIGN);
	}
	
	public int getCapacity() {
		return (int) map.get(CourseData.CAPACITY);
	}

	public String getNote() {
		return (String) map.get(CourseData.NOTE);
	}
	
	public Hour[] getHours() {
		return (Hour[]) map.get(CourseData.HOURS);
	}
	
	public List<HourBlock> getHourBlocks() {
		return HourBlock.getBlocks(getHours());
	}
	
	public boolean isMajor() {
		return !isLiberalArts();
	}
	
	public boolean isLiberalArts() {		
		switch (getDepartment()) {
		case "교양학부":
		case "HRD학과":
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Course) && (id == ((Course) obj).id);
	}
	
}
