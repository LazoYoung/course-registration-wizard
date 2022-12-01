package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

import java.util.Objects;

public class HourBlock {

	private static int newId = 0;
	private Hour begin;
	private int length;
	private int id;
	
	protected HourBlock(Hour begin, int length) {
		this.begin = begin;
		this.length = length;
		this.id = newId++;
	}

	public Day getDay() {
		return begin.getDay();
	}
	
	public Hour getFirstHour() {
		return begin;
	}
	
	public int getLength() {
		return length;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		HourBlock other = (HourBlock) obj;
		return Objects.equals(id, other.id);
	}
	
}
