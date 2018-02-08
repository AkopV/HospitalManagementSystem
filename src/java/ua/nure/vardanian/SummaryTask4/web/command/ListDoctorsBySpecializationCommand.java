package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.entity.User;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.db.exception.DBException;
import ua.nure.vardanian.SummaryTask4.db.service.PatientService;
import ua.nure.vardanian.SummaryTask4.db.service.UserService;
import ua.nure.vardanian.SummaryTask4.db.service.impl.PatientServiceImpl;
import ua.nure.vardanian.SummaryTask4.db.service.impl.UserServiceImpl;
import ua.nure.vardanian.SummaryTask4.web.ActionType;
import ua.nure.vardanian.SummaryTask4.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * List doctors by specialization command.
 *
 * @author Akop Vardanian
 *
 */
public class ListDoctorsBySpecializationCommand extends Command {

    private static final long serialVersionUID = 3695588606558105117L;

    private static final Logger LOG = Logger.getLogger(ListDoctorsBySpecializationCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");

        String result = null;
        if (ActionType.GET == actionType) {
            result = doGet(request, response);
        }

        LOG.debug("Command executed");
        return result;
    }

    /**
     * Forward user to page of all doctors. View type depends on the user id.
     *
     * @return to view of all doctors by specialization.
     */
    private String doGet(HttpServletRequest req, HttpServletResponse resp) throws DBException {

        long specializationId = Long.parseLong(req.getParameter("specializationId"));

        UserService manager = new UserServiceImpl();

        Collection<User> doctors = manager.getDoctorsBySpecialization(specializationId);
        LOG.trace("Found in DB: doctors by specialization --> " + doctors);

        req.setAttribute("doctors", doctors);

        return Path.FORWARD_VIEW_ALL_DOCTORS;
    }
}