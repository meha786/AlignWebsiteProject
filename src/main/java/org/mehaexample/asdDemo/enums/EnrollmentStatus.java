package org.mehaexample.asdDemo.enums;

public enum EnrollmentStatus {
	FULL_TIME("FULL_TIME"),
	PART_TIME("PART_TIME"),
	GRADUATED("GRADUATED"),
	DROPPED_OUT("DROPPED_OUT"),
	PLACEHOLDER("PLACEHOLDER"),
	INACTIVE("INACTIVE");
	
	private String enrollment;

	EnrollmentStatus(String enrollment) {
        this.enrollment = enrollment;
    }

    public String enrollment() {
        return enrollment;
    }
}