package org.mehaexample.asdDemo.enums;

public enum DegreeCandidacy {
	ASSOCIATE("ASSOCIATE"),
	BACHELORS("BACHELORS"),
	MASTERS("MASTERS"),
	PLACEHOLDER("PLACEHOLDER"),
	PHD("PHD");
	
	private String degree;

	DegreeCandidacy(String degree) {
        this.degree = degree;
    }

    public String degree() {
        return degree;
    }
}
