package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HourBlock {

	private Hour begin;
	private int length;
	
	private HourBlock(Hour begin, int length) {
		this.begin = begin;
		this.length = length;
	}
	
	public static List<HourBlock> getBlocks(List<Hour> hours) {
		List<HourBlock> result = new ArrayList<>();
		
		for (Day day : Day.values()) {
			List<Hour> list = hours.stream()
				.filter(e -> day == e.getDay())
				.sorted((h1, h2) -> h1.getIndex() - h2.getIndex())
				.toList();
			
			if (!list.isEmpty()) {
				Hour begin = list.get(0);
				Hour last = list.get(list.size() - 1);
				int length = last.getIndex() - begin.getIndex() + 1;
				result.add(new HourBlock(begin, length));
			}
		}
		return result;
	}
	
	public static List<HourBlock> getBlocks(Hour[] hours) {
		List<Hour> list = new ArrayList<>();
		Collections.addAll(list, hours);
		return getBlocks(list);
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
	
	public boolean isInConflict(HourBlock other) {
		if (this.equals(other))
			return true;
		
		int thisHead = begin.getIndex();
		int thisTail = thisHead + length;
		int otherHead = other.getFirstHour().getIndex();
		int otherTail = otherHead + other.getLength();
		
		return (thisTail > otherHead) && (thisHead < otherTail); 
	}

	@Override
	public int hashCode() {
		return Objects.hash(begin, length);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HourBlock other = (HourBlock) obj;
		return Objects.equals(begin, other.begin) && length == other.length;
	}
	
}
