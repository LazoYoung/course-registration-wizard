package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.ac.koreatech.parkcymil.CourseScheduler.exception.DataParseException;

public class DataParser {

	private String line;
	private CourseData[] column = {
			CourseData.CODE,
			CourseData.NAME,
			CourseData.SECTION,
			CourseData.PROGRAM,
			CourseData.CREDIT,
			CourseData.DESIGN,
			CourseData.DEPARTMENT,
			CourseData.PROFESSOR,
			CourseData.CAPACITY,
			CourseData.HOURS
	};
	
	private DataParser(String line) {
		this.line = line;
	}
	
	public static List<Course> extractCourse(InputStream is) {
		List<Course> output = new ArrayList<>();
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new InputStreamReader(is));
			String line = reader.readLine();
			
			while (line != null) {
				DataParser parser = new DataParser(line);
				Course course = parser.deserialize();
				output.add(course);
				line = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataParseException("Data file corrupted.");
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return output;
	}
	
	private Course deserialize() {
		Map<CourseData, Object> map = new HashMap<>();
		String[] tokens = parseLine();
		
		for (int c = 0; c < column.length; ++c) {
			CourseData data = column[c];
			String token = tokens[c];
			
			if (token.startsWith("\"")) {
				token = token.substring(1, token.length() - 1);
			}
			
			if (data == CourseData.HOURS) {
				map.put(data, parseHours(token));
			}
			else if (data.getType() == Integer.class) {
				int value = (token.isBlank()) ? 0 : Integer.parseInt(token);
				map.put(data, value);
			}
			else {
				map.put(data, token);
			}
		}
		
		return new Course(map);
	}

	private Hour[] parseHours(String token) {
		int len = token.length();
		
		if (len <= 2) return new Hour[0];
		
		token = token.substring(1, len - 1);
		int[] arr = parseIntArray(token);
		Hour[] hours = new Hour[arr.length];
		
		for (int i = 0; i < arr.length; ++i) {
			hours[i] = new Hour(arr[i]);
		}
		
		return hours;
	}

	private String[] parseLine() {
		String[] lines = new String[column.length];
		String line_ = line.concat(",");
		boolean inner = false;
		int lastDelim = -1;
		int i = 0;
		
		for (int cursor = 0; cursor < line_.length(); ++cursor) {
			char c = line_.charAt(cursor);
			
			switch (c) {
			case '\"':
				inner = !inner;
				break;
			case ',':
				if (!inner) {
					lines[i++] = line_.substring(lastDelim + 1, cursor);
					lastDelim = cursor;
				}
				break;
			}
		}
		
		return lines;
	}
	
	private int[] parseIntArray(String str) {
		String[] arr = str.split(",");
		int[] result = new int[arr.length];
		
		for (int i = 0; i < result.length; ++i) {
			String token = arr[i].strip();
			result[i] = Integer.parseInt(token);
		}
		
		return result;
	}
	
}
