package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

public class Course {
	
	private static int newId = 0;
	private int id;
	private String code;
	private String name;
	private int section;
	private String program;
	private String department;
	private String professor;
	private int credit;
	private int design;
	private int capacity;
	private String note;
	private int[] time;
	
	public Course(String code, String name, int section,
			String professor, String program, int credit,
			String note, int capacity, int design, String department, int[] time) {
		this.id = newId++;
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
	}
	
	public Object getData(CourseData data) {
		switch (data) {
		case CODE:
			return code;
		case NAME:
			return name;
		case SECTION:
			return section;
		case PROGRAM:
			return program;
		case DEPARTMENT:
			return department;
		case PROFESSOR:
			return professor;
		case CREDIT:
			return credit;
		case DESIGN:
			return design;
		case CAPACITY:
			return capacity;
		case NOTE:
			return note;
		}
		throw new IllegalArgumentException("Undefined type: " + data.toString());
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Course) && (id == ((Course) obj).id);
	}

	public int getUniqueID() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public int getSection() {
		return section;
	}

	public String getProgram() {
		return program;
	}

	public String getDepartment() {
		return department;
	}

	public String getProfessor() {
		return professor;
	}

	public int getCredit() {
		return credit;
	}

	public int getDesign() {
		return design;
	}
	
	public int getCapacity() {
		return capacity;
	}

	public String getNote() {
		return note;
	}
	
	public int[] getTime() {
		return time;
	}
	
}
