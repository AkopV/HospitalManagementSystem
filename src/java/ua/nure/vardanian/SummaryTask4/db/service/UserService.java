package ua.nure.vardanian.SummaryTask4.db.service;

import ua.nure.vardanian.SummaryTask4.db.Role;
import ua.nure.vardanian.SummaryTask4.db.Specialization;
import ua.nure.vardanian.SummaryTask4.db.entity.User;

import java.util.List;

/**
 * interface which is responsible for business login for get parameters
 *
 * @author Akop Vardanian
 **/
public interface UserService {

    List<User> getDoctors();

    List<User> getDoctorsBySpecialization(long specializationId);

    void addUser(User user);

    User getUserByLogin(String login);

    User getUserById(long id);

    List<Role> getRoles();

    List<Specialization> getSpecializations();
}