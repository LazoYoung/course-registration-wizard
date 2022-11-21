package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

public enum Day {

	MON("월요일"),
	TUE("화요일"),
	WED("수요일"),
	THU("목요일"),
	FRI("금요일");
	
	private String label;
	
	Day(String label) {
		this.label = label;
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
	
}
