package ua.nure.vardanian.SummaryTask4.db;

import ua.nure.vardanian.SummaryTask4.db.entity.User;

public enum Specialization {

    PEDIATRICIAN, TRAUMATOLOGIST, SURGEON;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static Specialization getSpecialization(User user) {
        int specializationId = user.getSpecializationId();
        return Specialization.values()[specializationId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}