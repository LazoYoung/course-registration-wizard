package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

import java.util.Objects;

public class Hour {

	private int data;
	
	public Hour(int data) {
		this.data = data;
	}
	
	public Day getDay() {
		return Day.get(data / 100);
	}
	
	public int getIndex() {
		return data % 100;
	}
	
	public String getLabel() {
		return getHourInfo(false);
	}
	
	public String getFormat() {
		return getHourInfo(true);
	}
	
	private String getHourInfo(boolean isFormat) {
		String label, format;
		
		switch (getIndex()) {
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
			throw new IllegalStateException("Invalid hour data: " + data);
		}
		
		return isFormat ? format : label;
	}

	@Override
	public int hashCode() {
		return Objects.hash(data);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Hour other = (Hour) obj;
		return (data == other.data);
	}
	
}
