package org.mehaexample.asdDemo.enums;

public enum Campus {
	BOSTON("BOSTON"),
	CHARLOTTE("CHARLOTTE"),
	SEATTLE("SEATTLE"),
	PLACEHOLDER("PLACEHOLDER"),
	SILICON_VALLEY("SILICON_VALLEY");
	
	private String campus;

	Campus(String campus) {
        this.campus = campus;
    }

    public String campus() {
        return campus;
    }
}
