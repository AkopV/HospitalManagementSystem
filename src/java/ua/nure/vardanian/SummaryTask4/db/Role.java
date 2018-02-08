package ua.nure.vardanian.SummaryTask4.db;


import ua.nure.vardanian.SummaryTask4.db.entity.User;

public enum Role {

    ADMIN, DOCTOR, NURSE;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}