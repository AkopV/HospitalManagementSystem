package ua.nure.vardanian.SummaryTask4.db.entity;

public class User extends Entity {

    private static final long serialVersionUID = -5585364426201402526L;

    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private int roleId;
    private int specializationId;
    private int countOfPatients;

    public User() {
    }

    public User(Long id, String login, String password, String firstName, String lastName, int roleId, int specializationId, int countOfPatients) {
        this.setId(id);
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
        this.specializationId = specializationId;
        this.countOfPatients = countOfPatients;
    }

    public User(String login, String password, String firstName, String lastName, int roleId, int specializationId, int countOfPatients) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
        this.specializationId = specializationId;
        this.countOfPatients = countOfPatients;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(int specializationId) {
        this.specializationId = specializationId;
    }

    public int getCountOfPatients() {
        return countOfPatients;
    }

    public void setCountOfPatients(int countOfPatients) {
        this.countOfPatients = countOfPatients;
    }


    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roleId=" + roleId +
                ", specializationId=" + specializationId +
                ", countOfPatients=" + countOfPatients +
                '}';
    }
}

