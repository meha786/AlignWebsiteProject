package org.mehaexample.asdDemo.model;

public class AdministratorNotes {
    private int administratorNoteId;
    private Students student;
    private Administrators admin;
    private String title;
    private String desc;

    public AdministratorNotes(Students student, Administrators admin, String title, String desc) {
        this.student = student;
        this.admin = admin;
        this.title = title;
        this.desc = desc;
    }

    public AdministratorNotes() {
        super();
    }

    public int getAdministratorNoteId() {
        return administratorNoteId;
    }

    public void setAdministratorNoteId(int administratorNoteId) {
        this.administratorNoteId = administratorNoteId;
    }

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    public Administrators getAdmin() {
        return admin;
    }

    public void setAdmin(Administrators admin) {
        this.admin = admin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
