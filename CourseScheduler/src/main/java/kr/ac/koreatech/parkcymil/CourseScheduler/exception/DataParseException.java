package kr.ac.koreatech.parkcymil.CourseScheduler.exception;

public class DataParseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public DataParseException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

}
