package ua.nure.vardanian.SummaryTask4.db.dao.impl;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.*;
import ua.nure.vardanian.SummaryTask4.db.dao.UserDAO;
import ua.nure.vardanian.SummaryTask4.db.entity.User;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.db.exception.DBException;
import ua.nure.vardanian.SummaryTask4.db.exception.Messages;
import ua.nure.vardanian.SummaryTask4.db.utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ua.nure.vardanian.SummaryTask4.db.utils.Utils.close;
import static ua.nure.vardanian.SummaryTask4.db.utils.Utils.extractUser;

public class UserDAOImpl implements UserDAO {

    private static final Logger LOG = Logger.getLogger(UserDAOImpl.class);

    @Override
    public List<User> getDoctors() {
        List<User> users = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            try {
                con = ConnectionPool.getConnection();
            } catch (DBException e) {
                e.printStackTrace();
            }
            stmt = con.createStatement();
            rs = stmt.executeQuery(Query.SQL_GET_ALL_DOCTORS);
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DOCTORS, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_DOCTORS, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } finally {
            close(con, stmt, rs);
        }
        return users;
    }

    @Override
    public List<User> getDoctorsBySpecialization(long specializationId) {
        List<User> users = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            try {
                con = ConnectionPool.getConnection();
            } catch (DBException e) {
                e.printStackTrace();
            }
            pstmt = con.prepareStatement(Query.SQL_GET_DOCTORS_BY_SPEC);
            pstmt.setLong(1, specializationId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DOCTORS_BY_SPECIALIZATION);
            try {
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_DOCTORS_BY_SPECIALIZATION, e);
            } catch (DBException e1) {
                e1.printStackTrace();
            }
        } finally {
            close(con, pstmt, rs);
        }
        return users;
    }

    @Override
    public void addUser(User user) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            try {
                con = ConnectionPool.getConnection();
            } catch (DBException e) {
                e.printStackTrace();
            }
            pstmt = con.prepareStatement(Query.INSERT_USER);
            int k = 1;
            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getFirstName());
            pstmt.setString(k++, user.getLastName());
            pstmt.setInt(k++, user.getRoleId());
            if (user.getSpecializationId() == 0) {
                pstmt.setNull(k++, Types.INTEGER);
                pstmt.setNull(k++, Types.INTEGER);
            } else {
                pstmt.setInt(k++, user.getSpecializationId());
                pstmt.setInt(k++, user.getCountOfPatients());
            }
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_CREATE_USER, ex);
            // check exception if true found duplicate
            if (ex instanceof SQLIntegrityConstraintViolationException) {
                LOG.error(Messages.ERR_USER_IS_ALREADY_CREATED);
                try {
                    throw new AppException();
                } catch (AppException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            close(con);
        }
    }

    @Override
    public User getUserByLogin(String login) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            try {
                con = ConnectionPool.getConnection();
            } catch (DBException e) {
                e.printStackTrace();
            }
            pstmt = con.prepareStatement(Query.SQL_FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            pstmt.execute();

            rs = pstmt.getResultSet();
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } finally {
            close(con, pstmt, rs);
        }
        return user;
    }

    @Override
    public User getUserById(long id) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
            pstmt = con.prepareStatement(Query.SQL_FIND_USER_BY_ID);
            pstmt.setLong(1, id);
            pstmt.execute();
            rs = pstmt.getResultSet();
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (DBException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return user;
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(Query.SQL_GET_ALL_ROLES);
            while (rs.next()) {
                for (Role role : Role.values()) {
                    if (rs.getString(Fields.ROLE_NAME).toUpperCase().equals(role.toString())) {
                        role.setId(rs.getLong(Fields.ROLE_ID));
                        roles.add(role);
                        break;
                    }
                }
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ROLES, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_ROLES, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (DBException e) {
            e.printStackTrace();
        } finally {
            close(con, stmt, rs);
        }
        return roles;
    }

    @Override
    public List<Specialization> getSpecializations() {
        List<Specialization> specializations = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(Query.SQL_GET_ALL_SPECIALIZATIONS);
            while (rs.next()) {
                for (Specialization specialization : Specialization.values()) {
                    if (rs.getString(Fields.SPECIALIZATION_NAME).toUpperCase().equals(specialization.toString())) {
                        specialization.setId(rs.getLong(Fields.SPECIALIZATION_ID));
                        specializations.add(specialization);
                        break;
                    }
                }
            }

        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_SPECIALIZATION, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_SPECIALIZATION, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (DBException e) {
            e.printStackTrace();
        } finally {
            close(con, stmt, rs);
        }
        return specializations;
    }
}