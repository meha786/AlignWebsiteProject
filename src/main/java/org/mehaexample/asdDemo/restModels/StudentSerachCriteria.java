package org.mehaexample.asdDemo.restModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentSerachCriteria {
	List<String> coops;
	List<String> undergraddegree;
	List<String> undergradschool;
	List<String> graduationyear;
	
	public List<String> getCoops() {
		return coops;
	}

	public void setCoops(List<String> coops) {
		this.coops = coops;
	}

	public List<String> getUndergraddegree() {
		return undergraddegree;
	}

	public void setUndergraddegree(List<String> undergraddegree) {
		this.undergraddegree = undergraddegree;
	}

	public List<String> getUndergradschool() {
		return undergradschool;
	}

	public void setUndergradschool(List<String> undergradschool) {
		this.undergradschool = undergradschool;
	}

	public List<String> getGraduationyear() {
		return graduationyear;
	}

	public void setGraduationyear(List<String> graduationyear) {
		this.graduationyear = graduationyear;
	}

	public StudentSerachCriteria(List<String> coops, List<String> undergraddegree, List<String> undergradschool,
			List<String> graduationyear) {
		super();
		this.coops = coops;
		this.undergraddegree = undergraddegree;
		this.undergradschool = undergradschool;
		this.graduationyear = graduationyear;
	}
	
	public StudentSerachCriteria() {
		super();
	}
}
