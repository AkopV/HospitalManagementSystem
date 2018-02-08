package ua.nure.vardanian.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.entity.Patient;
import ua.nure.vardanian.SummaryTask4.db.entity.User;
import ua.nure.vardanian.SummaryTask4.db.exception.AppException;
import ua.nure.vardanian.SummaryTask4.db.exception.DBException;
import ua.nure.vardanian.SummaryTask4.db.service.PatientService;
import ua.nure.vardanian.SummaryTask4.db.service.impl.PatientServiceImpl;
import ua.nure.vardanian.SummaryTask4.web.ActionType;
import ua.nure.vardanian.SummaryTask4.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * List patients by doctor id command.
 *
 * @author Akop Vardanian
 */
public class ListPatientsByDoctorCommand extends Command {

    private static final long serialVersionUID = -3471176542354326877L;

    private static final Logger LOG = Logger.getLogger(ListPatientsByDoctorCommand.class);

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
     * Forward to list of patients by doctor id page.
     *
     * @return path to list of patients page.
     */
    private String doGet(HttpServletRequest request, HttpServletResponse response) throws DBException {
        User doctor = (User) request.getSession().getAttribute("user");
        long doctorId = doctor.getId();
        LOG.trace("Doctor id: " + doctorId);
        PatientService manager = new PatientServiceImpl();

        List<Patient> patients = manager.getPatientsByDoctorId((int) doctorId);

        LOG.trace("Found in db: patients by doctor id --> " + patients);

        request.setAttribute("patients", patients);

        return Path.FORWARD_VIEW_ALL_PATIENTS;
    }
}