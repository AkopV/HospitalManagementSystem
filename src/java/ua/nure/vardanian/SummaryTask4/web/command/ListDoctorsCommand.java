package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.Specialization;
import ua.nure.vardanian.SummaryTask4.db.entity.User;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.db.exception.DBException;
import ua.nure.vardanian.SummaryTask4.db.service.UserService;
import ua.nure.vardanian.SummaryTask4.db.service.impl.UserServiceImpl;
import ua.nure.vardanian.SummaryTask4.web.ActionType;
import ua.nure.vardanian.SummaryTask4.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * List of doctors command.
 *
 * @author Akop Vardanian
 */
public class ListDoctorsCommand extends Command {

    private static final long serialVersionUID = -8532142094810921407L;

    private static final Logger LOG = Logger.getLogger(ListDoctorsCommand.class);

    /**
     * Serializable comparator used with TreeMap container. When the servlet
     * container tries to serialize the session it may fail because the session
     * can contain TreeMap object with not serializable comparator.
     *
     * @author Akop Vardanian
     */
    private static class CompareById implements Comparator<User>, Serializable {
        @Override
        public int compare(User o1, User o2) {
            if (o1.getId() > o2.getId()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static class CompareByCountOfPatients implements Comparator<User>, Serializable {
        @Override
        public int compare(User o1, User o2) {
            if (o1.getCountOfPatients() > o2.getCountOfPatients()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static class CompareBySpecializations implements Comparator<User>, Serializable {
        @Override
        public int compare(User o1, User o2) {
            if (o1.getSpecializationId() > o2.getSpecializationId()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static class CompareByUserFirstName implements Comparator<User>, Serializable {
        @Override
        public int compare(User o1, User o2) {
           return o1.getFirstName().compareTo(o2.getFirstName());
        }
    }

    private static Comparator<User> compareById = new CompareById();
    private static Comparator<User> compareByCountOfPatients = new CompareByCountOfPatients();
    private static Comparator<User> compareBySpecializations = new CompareBySpecializations();
    private static Comparator<User> compareByUserFirstName = new CompareBySpecializations();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) throws IOException, ServletException, AppException {
        LOG.debug("Command starts ");

        String result = null;

        if (ActionType.GET == actionType) {
            result = doGet(request, response);
        }

        LOG.debug("Command finished ");
        return result;
    }

    /**
     * Forward user to page of all doctors.
     *
     * @return path to view of all doctors page.
     */
    private String doGet(HttpServletRequest req, HttpServletResponse resp) throws DBException {

        UserService manager = new UserServiceImpl();

        Collection<User> doctors = manager.getDoctors();
        LOG.trace("Found in DB: doctors --> " + doctors);

        Collection<Specialization> specializations = manager.getSpecializations();
        LOG.trace("Found in DB: specializations --> " + specializations);

        req.setAttribute("doctors", doctors);
        req.getSession().setAttribute("specializations", specializations);

        return Path.FORWARD_VIEW_ALL_DOCTORS;
    }
}
