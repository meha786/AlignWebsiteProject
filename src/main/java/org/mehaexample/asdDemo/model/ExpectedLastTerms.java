package org.mehaexample.asdDemo.model;

public class ExpectedLastTerms {
	private int expectedLastTermId;
	private Students student;
	private Terms term;
	
	public ExpectedLastTerms(Students student, Terms term) {
		this.student = student;
		this.term = term;
	}

	public ExpectedLastTerms() {
		super();
	}
	
	public int getExpectedLastTermId() {
		return expectedLastTermId;
	}

	public void setExpectedLastTermId(int expectedLastTermId) {
		this.expectedLastTermId = expectedLastTermId;
	}

	public Students getStudent() {
		return student;
	}

	public void setStudent(Students student) {
		this.student = student;
	}

	public Terms getTerm() {
		return term;
	}

	public void setTerm(Terms term) {
		this.term = term;
	}

}
