package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// TODO Clean up deprecated codes
@Deprecated(forRemoval = true)
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
	
	public List<Hour> getHours() {
		return hours;
	}

	@Override
	public Iterator<Hour> iterator() {
		return hours.iterator();
	}
	
}
