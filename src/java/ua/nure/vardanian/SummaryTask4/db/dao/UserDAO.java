package ua.nure.vardanian.SummaryTask4.db.dao;

import ua.nure.vardanian.SummaryTask4.db.Role;
import ua.nure.vardanian.SummaryTask4.db.Specialization;
import ua.nure.vardanian.SummaryTask4.db.entity.User;

import java.util.List;

public interface UserDAO {

    /**
     * Get all doctors from database.
     *
     * @return All doctors from database.
     */
    List<User> getDoctors();

    /**
     * Get doctors by speciality
     *
     * @param specializationId specified specialization id.
     * @return list of users.
     */
    List<User> getDoctorsBySpecialization(long specializationId);

    /**
     * Add user to database.
     *
     * @param user specified user.
     */
    void addUser(User user);

    /**
     * Get user by login
     *
     * @param login specified login.
     * @return user who was found in database.
     */
    User getUserByLogin(String login);

    /**
     * Get user by id.
     *
     * @param id specified id.
     * @return user entity.
     */
    User getUserById(long id);


    /**
     * Get all roles that can be used.
     *
     * @return list of roles.
     */
    List<Role> getRoles();

    /**
     * Get all specializations that can be used.
     *
     * @return list of specializations.
     */
    List<Specialization> getSpecializations();
}