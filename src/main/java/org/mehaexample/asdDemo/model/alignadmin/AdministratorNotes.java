package org.mehaexample.asdDemo.model.alignadmin;

public class AdministratorNotes {
    private int administratorNoteId;
    private String neuId;
    private String administratorNeuId;
    private String title;
    private String desc;

    public AdministratorNotes(String neuId, String administratorNeuId, String title, String desc) {
        this.neuId = neuId;
        this.administratorNeuId = administratorNeuId;
        this.title = title;
        this.desc = desc;
    }

    public AdministratorNotes() { }

    public int getAdministratorNoteId() {
        return administratorNoteId;
    }

    public void setAdministratorNoteId(int administratorNoteId) {
        this.administratorNoteId = administratorNoteId;
    }

    public String getNeuId() {
        return neuId;
    }

    public void setNeuId(String neuId) {
        this.neuId = neuId;
    }

    public String getAdministratorNeuId() {
        return administratorNeuId;
    }

    public void setAdministratorNeuId(String administratorId) {
        this.administratorNeuId = administratorId;
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