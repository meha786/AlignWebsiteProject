package org.mehaexample.asdDemo.model;

import org.mehaexample.asdDemo.enums.Term;
import org.mehaexample.asdDemo.enums.TermType;

public class Terms {

	private int termId;
	private Term term;
	private int termYear;
	private TermType termType;

	public Terms(Term term, int termYear, TermType termType){
		this.term = term;
		this.termYear = termYear;
		this.termType = termType;
	}

	public Terms() { }

	public int getTermId() {
		return termId;
	}

	public void setTermId(int termId) {
		this.termId = termId;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public int getTermYear() {
		return termYear;
	}

	public void setTermYear(int termYear) {
		this.termYear = termYear;
	}

	public TermType getTermType() {
		return termType;
	}

	public void setTermType(TermType termType) {
		this.termType = termType;
	}

	@Override
	public String toString() {
		return "Term{" +
                "term='" + termId + 
                ", termYear=" + termYear + 
                 ", termType=" + termType +
                '}';
	}
}
