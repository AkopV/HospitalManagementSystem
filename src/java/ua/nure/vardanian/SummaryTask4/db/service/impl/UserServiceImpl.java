package ua.nure.vardanian.SummaryTask4.db.service.impl;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.Role;
import ua.nure.vardanian.SummaryTask4.db.Specialization;
import ua.nure.vardanian.SummaryTask4.db.dao.UserDAO;
import ua.nure.vardanian.SummaryTask4.db.entity.User;
import ua.nure.vardanian.SummaryTask4.db.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    private UserDAO userDAO;

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getDoctors() {
        return userDAO.getDoctors();
    }

    @Override
    public List<User> getDoctorsBySpecialization(long specializationId) {
        return userDAO.getDoctorsBySpecialization(specializationId);
    }

    @Override
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    @Override
    public User getUserByLogin(String login) {
        return userDAO.getUserByLogin(login);
    }

    @Override
    public User getUserById(long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public List<Role> getRoles() {
        return userDAO.getRoles();
    }

    @Override
    public List<Specialization> getSpecializations() {
        return userDAO.getSpecializations();
    }
}