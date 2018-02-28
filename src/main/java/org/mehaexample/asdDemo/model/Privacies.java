package org.mehaexample.asdDemo.model;

public class Privacies {
    private int privacyId;
    private String neuId;
    private boolean neuIdShown;
    private boolean phoneShown;
    private boolean addressShown;
    private boolean stateShown;
    private boolean zipShown;
    private boolean emailShown;
    private boolean enrollmentStatusShown;
    private boolean priorEducationShown;
    private boolean experienceShown;
    private boolean workExperienceShown;
    private boolean entryTermShown;
    private boolean expectedLastTermShown;
    private boolean scholarshipShown;

    public Privacies(String neuId, boolean neuIdShown, boolean phoneShown, boolean addressShown,
                     boolean stateShown, boolean zipShown, boolean emailShown, boolean enrollmentStatusShown,
                     boolean priorEducationShown, boolean experienceShown, boolean workExperienceShown,
                     boolean entryTermShown, boolean expectedLastTermShown, boolean scholarshipShown) {
        this.neuId = neuId;
        this.neuIdShown = neuIdShown;
        this.phoneShown = phoneShown;
        this.addressShown = addressShown;
        this.stateShown = stateShown;
        this.zipShown = zipShown;
        this.emailShown = emailShown;
        this.enrollmentStatusShown = enrollmentStatusShown;
        this.priorEducationShown = priorEducationShown;
        this.experienceShown = experienceShown;
        this.workExperienceShown = workExperienceShown;
        this.entryTermShown = entryTermShown;
        this.expectedLastTermShown = expectedLastTermShown;
        this.scholarshipShown = scholarshipShown;
    }

    public Privacies () {
        super();
    }

    public int getPrivacyId() {
        return privacyId;
    }

    public void setPrivacyId(int privacyId) {
        this.privacyId = privacyId;
    }

    public String getNeuId() {
        return neuId;
    }

    public void setNeuId(String neuId) {
        this.neuId = neuId;
    }

    public boolean isNeuIdShown() {
        return neuIdShown;
    }

    public void setNeuIdShown(boolean neuIdShown) {
        this.neuIdShown = neuIdShown;
    }

    public boolean isPhoneShown() {
        return phoneShown;
    }

    public void setPhoneShown(boolean phoneShown) {
        this.phoneShown = phoneShown;
    }

    public boolean isAddressShown() {
        return addressShown;
    }

    public void setAddressShown(boolean addressShown) {
        this.addressShown = addressShown;
    }

    public boolean isStateShown() {
        return stateShown;
    }

    public void setStateShown(boolean stateShown) {
        this.stateShown = stateShown;
    }

    public boolean isZipShown() {
        return zipShown;
    }

    public void setZipShown(boolean zipShown) {
        this.zipShown = zipShown;
    }

    public boolean isEmailShown() {
        return emailShown;
    }

    public void setEmailShown(boolean emailShown) {
        this.emailShown = emailShown;
    }

    public boolean isEnrollmentStatusShown() {
        return enrollmentStatusShown;
    }

    public void setEnrollmentStatusShown(boolean enrollmentStatusShown) {
        this.enrollmentStatusShown = enrollmentStatusShown;
    }

    public boolean isPriorEducationShown() {
        return priorEducationShown;
    }

    public void setPriorEducationShown(boolean priorEducationShown) {
        this.priorEducationShown = priorEducationShown;
    }

    public boolean isExperienceShown() {
        return experienceShown;
    }

    public void setExperienceShown(boolean experienceShown) {
        this.experienceShown = experienceShown;
    }

    public boolean isWorkExperienceShown() {
        return workExperienceShown;
    }

    public void setWorkExperienceShown(boolean workExperienceShown) {
        this.workExperienceShown = workExperienceShown;
    }

    public boolean isEntryTermShown() {
        return entryTermShown;
    }

    public void setEntryTermShown(boolean entryTermShown) {
        this.entryTermShown = entryTermShown;
    }

    public boolean isExpectedLastTermShown() {
        return expectedLastTermShown;
    }

    public void setExpectedLastTermShown(boolean expectedLastTermShown) {
        this.expectedLastTermShown = expectedLastTermShown;
    }

    public boolean isScholarshipShown() {
        return scholarshipShown;
    }

    public void setScholarshipShown(boolean scholarshipShown) {
        this.scholarshipShown = scholarshipShown;
    }
}