package kr.ac.koreatech.parkcymil.CourseScheduler.entity;

public enum Department {
	COMP("컴퓨터공학부"),
	ARCH("디자인ㆍ건축공학부"),
	MECH("기계공학부"),
	ELEC("전기ㆍ전자ㆍ통신공학부"),
	ENER("에너지신소재화학공학부"),
	INDU("산업경영학부"),
	MECA("메카트로닉스학부"),
	ARTS("교양학부"),
	HRDV("HRD학과"),
	FUTR("융합학과");
	
	private String text;

	Department(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	/**
	 * Returns the matching Department instance if found. Otherwise null is returned.
	 * @param text Text to match
	 * @return Department instance
	 */
	public static Department getByText(String text) {
		for (Department m : values()) {
			if (m.text.equals(text)) {
				return m;
			}
		}
		return null;
	}
}