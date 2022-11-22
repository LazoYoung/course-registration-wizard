package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DataParser {

	public static List<Course> parse(File file) throws FileNotFoundException, IOException {
		FileReader fileReader = new FileReader(file);
		BufferedReader reader = new BufferedReader(fileReader);
		CourseData[] column = new CourseData[] {
				CourseData.CODE,
				CourseData.NAME,
				CourseData.SECTION,
				CourseData.PROGRAM,
				CourseData.CREDIT,
				CourseData.DESIGN,
				CourseData.DEPARTMENT,
				CourseData.PROFESSOR,
				CourseData.CAPACITY,
				CourseData.TIME
		};
		String line;
		
		while ((line = reader.readLine()) != null) {
			StringTokenizer tokenizer = new StringTokenizer(line, ",");
			
			for (CourseData data : column) {
				if (!tokenizer.hasMoreTokens())
					throw new RuntimeException("File corrupted: " + file.getName());
				
				String token = tokenizer.nextToken();
				
			}
		}
		
		reader.close();
		return null;
	}
	
}
