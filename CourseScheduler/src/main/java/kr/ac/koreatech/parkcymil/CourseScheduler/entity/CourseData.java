package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

public enum CourseData {

	CODE(0, "과목코드", String.class),
	NAME(1, "과목명", String.class),
	SECTION(2, "분반", Integer.class),
	PROFESSOR(3, "교수", String.class),
	PROGRAM(4, "대상", String.class),
	CREDIT(5, "학점", Integer.class),
	NOTE(6, "비고", String.class),
	CAPACITY(7, "정원", Integer.class),
	DESIGN(8, "설계", Integer.class),
	DEPARTMENT(9, "개설학부", String.class);
	
	private String text;
	private Class<?> type;
	private int column;
	
	CourseData(int column, String text, Class<? extends Object> type) {
		this.column = column;
		this.text = text;
		this.type = type;
	}
	
	public int getColumn() {
		return column;
	}
	
	public String getText() {
		return text;
	}
	
	public Class<?> getType() {
		return type;
	}
	
	/**
	 * Returns the matching CourseData instance if found. Otherwise null is returned.
	 * @param text Text to match
	 * @return CourseData instance
	 */
	public static CourseData getByText(String text) {
		for (CourseData data : values()) {
			if (data.text.equals(text))
				return data;
		}
		return null;
	}
	
	/**
	 * Returns the matching CourseData instance if found. Otherwise null is returned.
	 * @param column Column to match
	 * @return CourseData instance
	 */
	public static CourseData getByColumn(int column) {
		for (CourseData data : values()) {
			if (data.column == column)
				return data;
		}
		return null;
	}
	
}
