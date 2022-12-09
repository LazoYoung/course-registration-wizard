package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

import java.util.HashMap;
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
	
	@Deprecated(forRemoval = true)
	public Course(String code, String name, int section,
			String professor, String program, int credit,
			String note, int capacity, int design, String department, int[] time) {
		/*
		this.code = code;
		this.name = name;
		this.section = section;
		this.program = program;
		this.department = department;
		this.professor = professor;
		this.credit = credit;
		this.design = design;
		this.capacity = capacity;
		this.note = note;
		this.time = time;
		*/
		this.id = newId++;
		this.map = new HashMap<>();
	}
	
	/**
	 * Reserved for internal use
	 * @param data Type of data required
	 * @return The actual value
	 */
	@Deprecated
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
