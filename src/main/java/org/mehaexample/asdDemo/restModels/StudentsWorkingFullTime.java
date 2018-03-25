package org.mehaexample.asdDemo.restModels;

import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.utils.StringUtils;

public class StudentsWorkingFullTime {
	private String campus;
	private int year;
	
	public StudentsWorkingFullTime(String campus, int year) {
		super();
		this.campus = campus;
		this.year = year;
	}

	public StudentsWorkingFullTime() {
		super();
	}
	
	public Campus getCampusAsEnum(){
		if(StringUtils.isNullOrEmpty(campus)) {
			return null;
		}	
		
		return Campus.valueOf(campus.toUpperCase());
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
