package ua.nure.vardanian.SummaryTask4.db.entity;

public class HospitalCard extends Entity {

    private static final long serialVersionUID = -6487425509873014124L;

    private String diagnosis;

    public HospitalCard() {
    }

    public HospitalCard(Long id, String diagnosis) {
        this.setId(id);
        this.diagnosis = diagnosis;
    }

    public HospitalCard(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
