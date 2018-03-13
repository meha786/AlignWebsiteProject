package org.mehaexample.asdDemo.model;

public class Experiences {

	private int experienceId;
	private Students student;
	private String title;
	private String description;

	public Experiences(Students student, String title, String description) {
		this.student = student;
		this.title = title;
		this.description = description;
	}

	public Experiences() {
		super();
	}

	public int getExperienceId() {
		return experienceId;
	}

	public void setExperienceId(int experienceId) {
		this.experienceId = experienceId;
	}

	public Students getStudent() {
		return student;
	}

	public void setStudent(Students student) {
		this.student = student;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}