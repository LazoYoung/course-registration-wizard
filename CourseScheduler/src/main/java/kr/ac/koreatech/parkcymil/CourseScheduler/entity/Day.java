package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

public enum Day {

	MON("월요일", 0),
	TUE("화요일", 1),
	WED("수요일", 2),
	THU("목요일", 3),
	FRI("금요일", 4);
	
	private String label;
	private int index;
	
	Day(String label, int index) {
		this.label = label;
		this.index = index;
	}
	
	public static Day get(int index) {
		try {
			return Day.values()[index];
		} catch (Exception e) {
			throw new IndexOutOfBoundsException(index);
		}
	}
	
	public String getLabel() {
		return label;
	}

	public int getIndex() {
		return index;
	}
	
}
