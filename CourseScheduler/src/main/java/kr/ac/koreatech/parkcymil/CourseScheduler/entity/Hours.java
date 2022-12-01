package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Hours implements Iterable<Hour> {

	protected List<Hour> hours;
	
	public Hours(List<Hour> hours) {
		this.hours = hours;
	}
	
	public Hours(int[] data) {	
		hours = new ArrayList<>();
		
		for (int e : data) {
			hours.add(new Hour(e));
		}
	}
	
	public List<HourBlock> getBlocks() {
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
	
	public List<Hour> getHours() {
		return hours;
	}

	@Override
	public Iterator<Hour> iterator() {
		return hours.iterator();
	}
	
}
