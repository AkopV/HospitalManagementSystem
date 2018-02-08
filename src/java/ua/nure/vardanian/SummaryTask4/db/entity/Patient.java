package ua.nure.vardanian.SummaryTask4.db.entity;

import java.util.Date;

public class Patient extends Entity {

    private static final long serialVersionUID = -3300114795396572539L;

    private String firstName;
    private String lastName;
    private Date birthday;
    private int doctorId;
    private int cardId;

    public Patient() {
    }

    public Patient(String firstName, String lastName, Date birthday, int doctorId, int cardId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.doctorId = doctorId;
        this.cardId = cardId;
    }

    public Patient(Long id, String firstName, String lastName, Date birthday, int doctorId, int cardId) {
        this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.doctorId = doctorId;
        this.cardId = cardId;

    }

    public Patient(Long id, String firstName, String lastName, Date birthday, int doctorId) {
        this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.doctorId = doctorId;
    }

    public Patient(String firstName, String lastName, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
