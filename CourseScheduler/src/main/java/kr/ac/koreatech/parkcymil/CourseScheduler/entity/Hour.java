package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

public class Hour {

	private int data;
	
	public Hour(int data) {
		this.data = data;
	}
	
	public String getDay() {
		int day = data / 100;
		
		switch (day) {
		case 0:
			return "월요일";
		case 1:
			return "화요일";
		case 2:
			return "수요일";
		case 3:
			return "목요일";
		case 4:
			return "금요일";
		default:
			throw new IllegalStateException("Invalid day: "  + day);
		}
	}
	
	public String getLabel() {
		return getHourInfo(false);
	}
	
	public String getFormat() {
		return getHourInfo(true);
	}
	
	private String getHourInfo(boolean isFormat) {
		int hour = data % 100;
		String label, format;
		
		switch (hour) {
		case 0:
			label = "01A";
			format = "09:00";
			break;
		case 1:
			label = "01B";
			format = "09:30";
			break;
		case 2:
			label = "02A";
			format = "10:00";
			break;
		case 3:
			label = "02B";
			format = "10:30";
			break;
		case 4:
			label = "03A";
			format = "11:00";
			break;
		case 5:
			label = "03B";
			format = "11:30";
			break;
		case 6:
			label = "04A";
			format = "12:00";
			break;
		case 7:
			label = "04B";
			format = "12:30";
			break;
		case 8:
			label = "05A";
			format = "13:00";
			break;
		case 9:
			label = "05B";
			format = "13:30";
			break;
		case 10:
			label = "06A";
			format = "14:00";
			break;
		case 11:
			label = "06B";
			format = "14:30";
			break;
		case 12:
			label = "07A";
			format = "15:00";
			break;
		case 13:
			label = "07B";
			format = "15:30";
			break;
		case 14:
			label = "08A";
			format = "16:00";
			break;
		case 15:
			label = "08B";
			format = "16:30";
			break;
		case 16:
			label = "09A";
			format = "17:00";
			break;
		case 17:
			label = "09B";
			format = "17:30";
			break;
		default:
			throw new IllegalStateException("Invalid hour: " + hour);
		}
		
		return isFormat ? format : label;
	}
	
}
