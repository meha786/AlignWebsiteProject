package org.mehaexample.asdDemo.restModels;

import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.utils.StringUtils;

public class StudentsInCompany {
	private String campus;
	private int year;
	private String company;
	
	public StudentsInCompany(String campus, int year, String company) {
		super();
		this.campus = campus;
		this.year = year;
		this.company = company;
	}

	public StudentsInCompany() {
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}