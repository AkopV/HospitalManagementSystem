package ua.nure.vardanian.SummaryTask4.db;

import ua.nure.vardanian.SummaryTask4.db.entity.Treatment;

public enum TypeOfTreatment {

    PROCEDURE, MEDICINE, SURGERY;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static TypeOfTreatment getTypeOfTreatment(Treatment treatment) {
        int typeOfTreatmentId = treatment.getTypeOfTreatmentId();
        return TypeOfTreatment.values()[typeOfTreatmentId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
