package ua.nure.vardanian.SummaryTask4.db.entity;

public class Treatment extends Entity {

    private static final long serialVersionUID = -1129599243375972946L;

    private int typeOfTreatmentId;
    private int hospitalCardId;
    private String pills;
    private boolean done;

    public Treatment() {
    }

    public Treatment(Long id, int typeOfTreatmentId, int hospitalCardId, String pills, boolean done) {
        this.setId(id);
        this.typeOfTreatmentId = typeOfTreatmentId;
        this.hospitalCardId = hospitalCardId;
        this.pills = pills;
        this.done = done;
    }

    public Treatment(int typeOfTreatmentId, int hospitalCardId, String pills) {
        this.typeOfTreatmentId = typeOfTreatmentId;
        this.hospitalCardId = hospitalCardId;
        this.pills = pills;
    }

    public int getTypeOfTreatmentId() {
        return typeOfTreatmentId;
    }

    public void setTypeOfTreatmentId(int typeOfTreatmentId) {
        this.typeOfTreatmentId = typeOfTreatmentId;
    }

    public int getHospitalCardId() {
        return hospitalCardId;
    }

    public void setHospitalCardId(int hospitalCardId) {
        this.hospitalCardId = hospitalCardId;
    }

    public String getPills() {
        return pills;
    }

    public void setPills(String pills) {
        this.pills = pills;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}