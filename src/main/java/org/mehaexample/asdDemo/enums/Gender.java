package org.mehaexample.asdDemo.enums;

public enum Gender {
	M("M"),
	F("F"),
	PLACEHOLDER("PLACEHOLDER");
	
	private String gender;

	Gender(String gender) {
        this.gender = gender;
    }

    public String gender() {
        return gender;
    }
}